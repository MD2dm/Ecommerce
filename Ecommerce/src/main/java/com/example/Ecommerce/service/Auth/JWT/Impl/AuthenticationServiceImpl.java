package com.example.Ecommerce.service.Auth.JWT.Impl;

import com.example.Ecommerce.common.enums.Role;
import com.example.Ecommerce.common.exceptionHandling.DuplicateEntryException;
import com.example.Ecommerce.dto.UsersDto.JwtAuthResponseDTO;
import com.example.Ecommerce.dto.UsersDto.LoginRequestDTO;
import com.example.Ecommerce.dto.UsersDto.RefreshTokenRequestDTO;
import com.example.Ecommerce.dto.UsersDto.RegisterRequestDTO;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

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

    private final Map<String, RegisterRequestDTO> temporaryRegistrationStorage = new ConcurrentHashMap<>();
    private final Map<String, String> otpStorage = new ConcurrentHashMap<>();


    @Override
    public void register(RegisterRequestDTO request){

        String password = request.getPassword();
        if (password.length() < 8 || !password.matches(".*[A-Z].*")) {
            throw new RuntimeException("Password must be at least 8 characters long and contain at least one uppercase letter");
        }

        User user = new User();

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateEntryException("Username already exists");
        } else {
            user.setUsername(request.getUsername());
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEntryException("Email already exists");
        } else {
            user.setEmail(request.getEmail());
        }

        if (userRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateEntryException("Phone already exists");
        } else {
            user.setPhone(request.getPhone());
        }

        String otp = generateOtp();
        temporaryRegistrationStorage.put(request.getEmail(), request);
        otpStorage.put(request.getEmail(), otp);
        emailService.sendEmail(request.getEmail(), "Verify OTP", "Your OTP is: " + otp);
    }

    public User verifyOtp(String email, String otp) {
        String storedOtp = otpStorage.get(email);
        if (storedOtp != null && storedOtp.equals(otp)) {
            RegisterRequestDTO request = temporaryRegistrationStorage.get(email);
            if (request != null) {
                User user = new User();
                user.setUsername(request.getUsername());
                user.setEmail(request.getEmail());
                user.setPhone(request.getPhone());
                user.setGender(request.getGender());
                user.setFirstName(request.getFirstname());
                user.setLastName(request.getLastname());
                user.setRole(Role.CUSTOMER);
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                userRepository.save(user);

                //Delete temporary data
                temporaryRegistrationStorage.remove(email);
                otpStorage.remove(email);
                return user;
            }
        }
        throw new RuntimeException("Invalid OTP");
    }

    @Override
    public JwtAuthResponseDTO login (LoginRequestDTO request){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            var user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new IllegalArgumentException("Username or password is incorrect"));


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

    private String generateOtp() {
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }
}
