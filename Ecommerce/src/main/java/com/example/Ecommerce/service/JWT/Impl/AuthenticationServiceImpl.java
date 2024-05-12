package com.example.Ecommerce.service.JWT.Impl;

import com.example.Ecommerce.dto.Users.JwtAuthenticationResponse;
import com.example.Ecommerce.dto.Users.LoginRequest;
import com.example.Ecommerce.dto.Users.RefreshTokenRequest;
import com.example.Ecommerce.dto.Users.SignUpRequest;
import com.example.Ecommerce.model.Role;
import com.example.Ecommerce.model.User;
import com.example.Ecommerce.repository.UserRepository;
import com.example.Ecommerce.service.JWT.AuthenticationService;
import com.example.Ecommerce.service.JWT.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    @Override
    public User signUp(SignUpRequest signUpRequest){

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();

        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setFirstName(signUpRequest.getFirstname());
        user.setLastName(signUpRequest.getLastname());
        user.setRole(Role.CUSTOMER);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public JwtAuthenticationResponse login (LoginRequest loginRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));

        var user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshTokenToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);

        return jwtAuthenticationResponse;
    }

    @Override
    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String userName = jwtService.extractUsername(refreshTokenRequest.getToken());
        User user = userRepository.findByUsername(userName).orElseThrow();
        if(jwtService.isTokenValid(refreshTokenRequest.getToken(), user)){
            var jwt = jwtService.generateToken(user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());

            return  jwtAuthenticationResponse;
        }
        return null;
    }
}
