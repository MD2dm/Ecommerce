package com.example.Ecommerce.service.Auth.JWT;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface JWTUserService {

    UserDetailsService userDetailsService();

}
