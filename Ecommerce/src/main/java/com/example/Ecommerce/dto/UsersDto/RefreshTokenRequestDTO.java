package com.example.Ecommerce.dto.UsersDto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class RefreshTokenRequestDTO implements Serializable {

    private String token;
}
