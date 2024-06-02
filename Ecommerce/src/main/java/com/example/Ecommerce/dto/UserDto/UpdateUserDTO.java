package com.example.Ecommerce.dto.UserDto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

@Getter@Setter
public class UpdateUserDTO implements Serializable {

    private Long id;

    private String email;

    private MultipartFile avatar;

    private Date birthday;

    private String firstName;

    private String lastName;

    private String address;

    private String phone;
}
