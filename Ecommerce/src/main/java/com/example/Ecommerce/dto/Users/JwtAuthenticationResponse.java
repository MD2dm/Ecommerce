package com.example.Ecommerce.dto.Users;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter@Setter
@NoArgsConstructor
public class JwtAuthenticationResponse implements Serializable {

    private String token;

    private String refreshToken;
}
