package com.example.Ecommerce.dto.UsersDto;


import com.example.Ecommerce.common.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class RegisterRequestDTO implements Serializable {

    private String username;

    private String password;

    private String phone;

    private Gender gender;

    private String email;

    private String firstname;

    private String lastname;

}
