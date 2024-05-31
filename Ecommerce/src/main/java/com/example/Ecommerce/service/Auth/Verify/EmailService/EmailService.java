package com.example.Ecommerce.service.Auth.Verify.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtp(String to, String otp) {

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

    public void sendPasswordResetEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset OTP");
        message.setText("Your OTP for password reset is: " + otp);
        mailSender.send(message);
    }
}
