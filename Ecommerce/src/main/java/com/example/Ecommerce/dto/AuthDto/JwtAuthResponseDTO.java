package com.example.Ecommerce.dto.AuthDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter@Setter
@NoArgsConstructor
public class JwtAuthResponseDTO implements Serializable {

    private String token;

    private String refreshToken;
}
