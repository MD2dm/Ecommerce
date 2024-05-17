package com.example.Ecommerce.dto.Users;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class LoginRequest implements Serializable {

    private String username;

    private String password;
}
