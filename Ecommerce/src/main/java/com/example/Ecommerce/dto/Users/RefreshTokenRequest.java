package com.example.Ecommerce.dto.Users;

import lombok.Data;

import java.io.Serializable;

@Data
public class RefreshTokenRequest implements Serializable {

    private String token;
}
