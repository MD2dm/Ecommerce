package com.example.Ecommerce.dto.Users;

import lombok.Data;

@Data
public class SignUpRequest {

    private String username;

    private String password;

    private String email;

    private String firstname;

    private String lastname;
}
