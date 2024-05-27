package com.example.Ecommerce.service.Auth.JWT;

import com.example.Ecommerce.dto.UsersDto.JwtAuthResponseDTO;
import com.example.Ecommerce.dto.UsersDto.LoginRequestDTO;
import com.example.Ecommerce.dto.UsersDto.RefreshTokenRequestDTO;
import com.example.Ecommerce.dto.UsersDto.RegisterRequestDTO;
import com.example.Ecommerce.model.User;

public interface AuthenticationService {

    void register(RegisterRequestDTO request);

    JwtAuthResponseDTO login (LoginRequestDTO request);

    JwtAuthResponseDTO refreshToken(RefreshTokenRequestDTO request);

    User verifyOtp(String email, String otp);

}