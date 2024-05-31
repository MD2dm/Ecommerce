package com.example.Ecommerce.dto.AuthDto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class RefreshTokenRequestDTO implements Serializable {

    private String token;
}
