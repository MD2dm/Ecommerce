package com.example.Ecommerce.service.Auth.Verify.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpRegister(String to, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Account Registration Confirmation");

        //content email
        String emailMessage = "We are processing your account registration request on our website.\n" +
                "Your registration OTP is: " + otp + "\n" +
                "Please check and enter this OTP code on the confirmation page to complete your account registration.\n" +
                "\n" +
                "This OTP is valid for 5 minutes from the time you receive this email. It will expire after the time limit.\n" +
                "\n" +
                "Thank you for registering with us. We hope you enjoy your experience!";

        message.setText(emailMessage);
        mailSender.send(message);
    }

    public void sendOtpForgotPassword(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset OTP");

        String emailMessage = "Dear User,\n\n" +
                "We've received a request to reset the password for your account. To proceed with the password reset process, please use the following OTP (One Time Password):\n\n" +
                otp + "\n\n" +
                "Please enter this OTP on the password reset page within the next 5 minutes to complete the process. This OTP is valid only for a single use and will expire after the mentioned time limit.\n\n" +
                "If you did not initiate this password reset request, please ignore this email. Your account remains secure, and no changes have been made.\n\n" +
                "Thank you for choosing our service.";

        message.setText(emailMessage);
        mailSender.send(message);
    }
}
