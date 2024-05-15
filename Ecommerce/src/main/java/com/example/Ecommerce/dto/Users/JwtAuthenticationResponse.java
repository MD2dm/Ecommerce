package com.example.Ecommerce.dto.Users;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtAuthenticationResponse implements Serializable {

    private String token;

    private String refreshToken;

}
