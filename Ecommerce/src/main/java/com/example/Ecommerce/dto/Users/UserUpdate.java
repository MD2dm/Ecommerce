package com.example.Ecommerce.dto.Users;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserUpdate {

    private String email;

    private String avatar;

    private LocalDate birthday;

    private String firstName;

    private String lastName;

    private String address;

    private String phone;
}
