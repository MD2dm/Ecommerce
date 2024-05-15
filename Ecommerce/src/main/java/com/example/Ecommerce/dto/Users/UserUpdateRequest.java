package com.example.Ecommerce.dto.Users;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
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
