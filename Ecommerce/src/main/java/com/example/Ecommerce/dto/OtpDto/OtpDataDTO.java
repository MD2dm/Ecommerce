package com.example.Ecommerce.dto.OtpDto;


import lombok.Getter;

@Getter
public class OtpDataDTO {

    private String otp;

    private long expiryTime;

    public OtpDataDTO(String otp, long expiryTime) {
        this.otp = otp;
        this.expiryTime = expiryTime;
    }

}
