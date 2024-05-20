package com.example.Ecommerce.controller.JWT;

import com.example.Ecommerce.dto.ResponseStatus.ResponseData;
import com.example.Ecommerce.dto.ResponseStatus.ResponseError;
import com.example.Ecommerce.dto.Users.JwtAuthenticationResponse;
import com.example.Ecommerce.dto.Users.LoginRequest;
import com.example.Ecommerce.dto.Users.RefreshTokenRequest;
import com.example.Ecommerce.dto.Users.SignUpRequest;
import com.example.Ecommerce.model.User;
import com.example.Ecommerce.repository.UserRepository;
import com.example.Ecommerce.service.JWT.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final UserRepository userRepository;

    @Operation(summary = "Register Account User")
    @PostMapping("/register")
    public ResponseEntity<ResponseData<User>> register(@RequestBody SignUpRequest signUpRequest) {

        //Check username
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(new ResponseData<>(400, "Username already exists", null));
        }

        //Check email
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(new ResponseData<>(400, "Email already exists", null));
        }

        //Check password
        String password = signUpRequest.getPassword();
        if (password.length() < 8 || !password.matches(".*[A-Z].*")) {
            return ResponseEntity.badRequest()
                    .body(new ResponseData<>(400, "Password must be at least 8 characters long and contain at least one uppercase letter", null));
        }

        User user = authenticationService.signUp(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseData<>(201, "User registered successfully", user));
    }


    @Operation(summary = "Login Account User")
    @PostMapping("/login")
    public ResponseEntity<ResponseData<JwtAuthenticationResponse>> login(@RequestBody LoginRequest loginRequest) {
        try {
            JwtAuthenticationResponse jwt = authenticationService.login(loginRequest);
            return ResponseEntity.ok(new ResponseData<>(201, "Login successful", jwt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseData<>(401, "Invalid credentials", null));
        }
    }

    @Operation(summary = "Refresher Token User's")
    @PostMapping("/refresh")
    public ResponseData<JwtAuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        JwtAuthenticationResponse jwtRefreshToken = authenticationService.refreshToken(refreshTokenRequest);
        return new ResponseData<>(202, "RefreshToken successful", jwtRefreshToken);
    }

    @Operation(summary = "Log Out Account User")
    @PostMapping("/logout")
    public ResponseData<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextHolder.clearContext();
        return new ResponseData<>(204, "Logout successful");
    }
}
