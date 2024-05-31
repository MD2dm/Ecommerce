package com.example.Ecommerce.dto.AuthDto;


import com.example.Ecommerce.common.enums.Gender;
import com.example.Ecommerce.common.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

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

    private Role role;

}
