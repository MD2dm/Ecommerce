package com.example.Ecommerce.dto.Users;

import lombok.Data;

import java.io.Serializable;

@Data
public class SignUpRequest implements Serializable {

    private String username;

    private String password;

    private String email;

    private String firstname;

    private String lastname;
}
