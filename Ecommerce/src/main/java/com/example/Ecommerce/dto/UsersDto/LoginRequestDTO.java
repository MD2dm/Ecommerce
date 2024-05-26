package com.example.Ecommerce.dto.UsersDto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class LoginRequestDTO implements Serializable{

    private String username;

    private String password;

    private String otp;
}
