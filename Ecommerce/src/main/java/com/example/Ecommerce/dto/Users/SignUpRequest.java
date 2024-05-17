package com.example.Ecommerce.dto.Users;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class SignUpRequest implements Serializable {

    private String username;

    private String password;

    private String email;

    private String firstname;

    private String lastname;
}
