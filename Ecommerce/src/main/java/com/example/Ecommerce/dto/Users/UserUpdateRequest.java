package com.example.Ecommerce.dto.Users;

import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
public class UserUpdateRequest implements Serializable  {


    private String email;

    private String avatar;

    private Date birthday;

    private String firstName;

    private String lastName;

    private String address;

    private String phone;
}
