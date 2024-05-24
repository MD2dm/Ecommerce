package com.example.Ecommerce.dto.UsersDto;

import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
public class UserUpdateRequestDTO implements Serializable {

    private String email;

    private String avatar;

    private Date birthday;

    private String firstName;

    private String lastName;

    private String address;

    private String phone;
}
