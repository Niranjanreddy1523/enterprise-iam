package com.IAM.config;

import org.springframework.stereotype.Component;

@Component
public class OtpSender {
    public void sendOtp(String destination, String otpCode) {
        // Integrate with email/SMS service
        System.out.println("Sending OTP " + otpCode + " to " + destination);
    }
}

