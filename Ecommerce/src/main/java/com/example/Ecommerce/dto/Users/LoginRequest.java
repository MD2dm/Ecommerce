package com.example.Ecommerce.dto.Users;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
public class LoginRequest implements Serializable {

    private String username;

    private String password;
}
