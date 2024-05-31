package com.example.Ecommerce.common.otp;

import com.example.Ecommerce.dto.OtpDto.OtpDataDTO;
import com.example.Ecommerce.dto.AuthDto.RegisterRequestDTO;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TemporaryStorage {

    private final Map<String, RegisterRequestDTO> registrationStorage = new ConcurrentHashMap<>();
    private final Map<String, OtpDataDTO> otpStorage = new ConcurrentHashMap<>();

    public void storeRegistration(String email, RegisterRequestDTO request) {
        registrationStorage.put(email, request);
    }

    public RegisterRequestDTO getRegistration(String email) {
        return registrationStorage.get(email);
    }

    public void removeRegistration(String email) {
        registrationStorage.remove(email);
    }

    public void storeOtp(String email, String otp, long expiryTime) {
        otpStorage.put(email, new OtpDataDTO(otp, expiryTime));
    }

    public OtpDataDTO getOtpData(String email) {
        return otpStorage.get(email);
    }

    public void removeOtp(String email) {
        otpStorage.remove(email);
    }
}
