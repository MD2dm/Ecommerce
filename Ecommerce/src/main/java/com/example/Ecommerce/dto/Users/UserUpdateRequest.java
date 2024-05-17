package com.example.Ecommerce.dto.Users;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Getter@Setter
public class UserUpdateRequest implements Serializable  {


    private String email;

    private String avatar;

    private Date birthday;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private String firstName;

    private String lastName;

    private String address;

    private String phone;
}
