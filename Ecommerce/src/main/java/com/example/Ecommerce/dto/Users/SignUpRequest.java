package com.example.Ecommerce.dto.Users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SignUpRequest implements Serializable {

    private String username;

    @Size(min = 8, message = "Password must be at least 8 characters long and contain at least one uppercase letter")
    private String password;

    @Email
    private String email;

    private String firstname;

    private String lastname;
}
