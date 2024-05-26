package com.example.Ecommerce.controller.auth;


import com.example.Ecommerce.common.responseStatus.ResponseData;
import com.example.Ecommerce.dto.UsersDto.JwtAuthResponseDTO;
import com.example.Ecommerce.dto.UsersDto.LoginRequestDTO;
import com.example.Ecommerce.dto.UsersDto.RegisterRequestDTO;
import com.example.Ecommerce.model.User;
import com.example.Ecommerce.repository.UserRepository;
import com.example.Ecommerce.service.Auth.JWT.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller")
public class AuthController {

    @Autowired
    private final AuthenticationService authenticationService;

    @Autowired
    private final UserRepository userRepository;

    @Operation(summary = "Register Account User")
    @PostMapping("/register")
    public ResponseEntity<ResponseData<Void>> register(@RequestBody RegisterRequestDTO request) {
        // Check username, email, phone, password
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(new ResponseData<>(400, "Username already exists", null));
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(new ResponseData<>(400, "Email already exists", null));
        }

        if (userRepository.existsByPhone(request.getPhone())) {
            return ResponseEntity.badRequest()
                    .body(new ResponseData<>(400, "Phone already exists", null));
        }

        String password = request.getPassword();
        if (password.length() < 8 || !password.matches(".*[A-Z].*")) {
            return ResponseEntity.badRequest()
                    .body(new ResponseData<>(400, "Password must be at least 8 characters long and contain at least one uppercase letter", null));
        }

        authenticationService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseData<>(201, "OTP sent to email. Please verify.", null));
    }

    @Operation(summary = "Verify OTP and Complete Registration")
    @PostMapping("/verify-otp")
    public ResponseEntity<ResponseData<User>> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        try {
            User user = authenticationService.verifyOtp(email, otp);
            return ResponseEntity.ok(new ResponseData<>(201, "User registered successfully", user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseData<>(401, "Invalid OTP", null));
        }
    }


    @Operation(summary = "Login Account User")
    @PostMapping("/login")
    public ResponseEntity<ResponseData<JwtAuthResponseDTO>> login(@RequestBody LoginRequestDTO request) {
        try {
            JwtAuthResponseDTO jwt = authenticationService.login(request);
            return ResponseEntity.ok(new ResponseData<>(201, "Login successful", jwt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseData<>(401, "Invalid credentials"));
        }
    }


    @Operation(summary = "Log Out Account User")
    @PostMapping("/logout")
    public ResponseData<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextHolder.clearContext();
        return new ResponseData<>(204, "Logout successful");
    }
}
