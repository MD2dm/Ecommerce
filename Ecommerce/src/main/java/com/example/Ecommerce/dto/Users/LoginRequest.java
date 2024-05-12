package com.example.Ecommerce.dto.Users;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoginRequest {

    private String username;

    private String password;
}
