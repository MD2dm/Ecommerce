package com.example.Ecommerce.service.Auth.JWT.Impl;

import com.example.Ecommerce.common.exceptionHandling.DuplicateEntryException;
import com.example.Ecommerce.common.otp.TemporaryStorage;
import com.example.Ecommerce.dto.OtpDto.OtpDataDTO;
import com.example.Ecommerce.dto.AuthDto.JwtAuthResponseDTO;
import com.example.Ecommerce.dto.AuthDto.LoginRequestDTO;
import com.example.Ecommerce.dto.AuthDto.RefreshTokenRequestDTO;
import com.example.Ecommerce.dto.AuthDto.RegisterRequestDTO;
import com.example.Ecommerce.model.User;
import com.example.Ecommerce.repository.UserRepository;
import com.example.Ecommerce.service.Auth.JWT.AuthenticationService;
import com.example.Ecommerce.service.Auth.JWT.JWTService;
import com.example.Ecommerce.service.Auth.Verify.EmailService.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final JWTService jwtService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TemporaryStorage temporaryStorage;



    @Override
    public void register(RegisterRequestDTO request) {
        String password = request.getPassword();
        if (password.length() < 8 || !password.matches(".*[A-Z].*")) {
            throw new RuntimeException("Password must be at least 8 characters long and contain at least one uppercase letter");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateEntryException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEntryException("Email already exists");
        }

        if (userRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateEntryException("Phone already exists");
        }

        String otp = generateOtp();
        long expiryTime = System.currentTimeMillis() + (5 * 60 * 1000);
        temporaryStorage.storeRegistration(request.getEmail(), request);
        temporaryStorage.storeOtp(request.getEmail(), otp, expiryTime);
        emailService.sendOtp(request.getEmail(), otp);
    }

    @Override
    public User verifyOtp(String email, String otp) {
        OtpDataDTO otpData = temporaryStorage.getOtpData(email);
        if (otpData != null) {
            if (otpData.getExpiryTime() < System.currentTimeMillis()) {
                temporaryStorage.removeOtp(email);
                throw new RuntimeException("OTP has expired");
            }
            if (otpData.getOtp().equals(otp)) {
                RegisterRequestDTO request = temporaryStorage.getRegistration(email);
                if (request != null) {
                    User user = new User();
                    user.setUsername(request.getUsername());
                    user.setEmail(request.getEmail());
                    user.setPhone(request.getPhone());
                    user.setGender(request.getGender());
                    user.setFirstName(request.getFirstname());
                    user.setLastName(request.getLastname());
                    user.setRole(request.getRole());
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    user.setVerified(true);

                    userRepository.save(user);

                    // Delete temporary data
                    temporaryStorage.removeRegistration(email);
                    temporaryStorage.removeOtp(email);
                    return user;
                }
            }
        }
        throw new RuntimeException("Invalid OTP");
    }

    @Override
    public JwtAuthResponseDTO login (LoginRequestDTO request){
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            var jwt = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshTokenToken(new HashMap<>(), user);

            JwtAuthResponseDTO jwtAuthenticationResponse = new JwtAuthResponseDTO();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshToken);

            return jwtAuthenticationResponse;
        } catch (BadCredentialsException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password is incorrect");
        }
    }


    @Override
    public JwtAuthResponseDTO refreshToken(RefreshTokenRequestDTO request){
        String userName = jwtService.extractUsername(request.getToken());
        User user = userRepository.findByUsername(userName).orElseThrow();
        if(jwtService.isTokenValid(request.getToken(), user)){
            var jwt = jwtService.generateToken(user);

            JwtAuthResponseDTO jwtAuthenticationResponse = new JwtAuthResponseDTO();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(request.getToken());

            return  jwtAuthenticationResponse;
        }
        return null;
    }

    @Override
    public void requestPasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        String otp = generateOtp();
        long expiryTime = System.currentTimeMillis() + (5 * 60 * 1000); // 5 minutes expiry
        temporaryStorage.storeOtp(email, otp, expiryTime);

        emailService.sendPasswordResetEmail(email, otp);
    }

    @Override
    public void verifyOtpReset(String email, String otp) {
        OtpDataDTO otpData = temporaryStorage.getOtpData(email);
        if (otpData == null || otpData.getExpiryTime() < System.currentTimeMillis()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid or expired OTP");
        }
        if (!otpData.getOtp().equals(otp)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid OTP");
        }

        // Remove OTP from temporary storage after successful verification
        temporaryStorage.removeOtp(email);
    }

    @Override
    public void resetPassword(String email, String newPassword, String reEnterPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    private String generateOtp() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(999999));
    }
}
