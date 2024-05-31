package com.example.Ecommerce.dto.OtpDto;

import lombok.Getter;

@Getter
public class VerifyOtpRequestDTO {

    private String email;

    private String otp;
}
