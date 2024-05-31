package com.example.Ecommerce.service.Auth.JWT;

import com.example.Ecommerce.dto.AuthDto.JwtAuthResponseDTO;
import com.example.Ecommerce.dto.AuthDto.LoginRequestDTO;
import com.example.Ecommerce.dto.AuthDto.RefreshTokenRequestDTO;
import com.example.Ecommerce.dto.AuthDto.RegisterRequestDTO;
import com.example.Ecommerce.model.User;

public interface AuthenticationService {

    void register(RegisterRequestDTO request);

    JwtAuthResponseDTO login (LoginRequestDTO request);

    JwtAuthResponseDTO refreshToken(RefreshTokenRequestDTO request);

    User verifyOtp(String email, String otp);

    User getUserByUsername(String username);

    void requestPasswordReset(String email);

    void resetPassword(String email, String newPassword, String reEnterPassword);

    void verifyOtpReset(String email, String otp);
}