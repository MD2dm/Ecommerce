package com.example.Ecommerce.service.JWT;

import com.example.Ecommerce.dto.Users.JwtAuthenticationResponse;
import com.example.Ecommerce.dto.Users.LoginRequest;
import com.example.Ecommerce.dto.Users.RefreshTokenRequest;
import com.example.Ecommerce.dto.Users.SignUpRequest;
import com.example.Ecommerce.model.User;

public interface AuthenticationService {

    User signUp(SignUpRequest signUpRequest);

    JwtAuthenticationResponse login (LoginRequest loginRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

}
