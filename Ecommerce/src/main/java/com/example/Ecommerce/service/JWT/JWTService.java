package com.example.Ecommerce.service.JWT;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JWTService {

    String extractUsername(String token);

    String generateToken(UserDetails userDetails);

    String generateRefreshTokenToken(Map<String, Object> extraClaims , UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);
}
