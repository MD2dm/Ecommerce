package com.example.Ecommerce.dto.OtpDto;

import lombok.Getter;

@Getter
public class ResetPasswordRequestDTO {

    private String email;

    private String newPassword;

    private String reEnterPassword;

}
