package com.example.Ecommerce.dto.Users;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class RefreshTokenRequest implements Serializable {

    private String token;
}
