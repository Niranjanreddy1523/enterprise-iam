package com.IAM.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.IAM.entity.User;
import com.IAM.service.OtpService;

@RestController
@RequestMapping("/api/auth")
public class OtpController {

    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestParam Long userId, @RequestParam String otpCode) {
        User user = new User(); // Replace with actual user lookup from DB
        user.setId(userId);

        boolean valid = otpService.validateOtp(user, otpCode);
        if (valid) {
            // TODO: Issue JWT here
            return ResponseEntity.ok("OTP verified successfully. JWT issued.");
        } else {
            return ResponseEntity.status(400).body("Invalid or expired OTP.");
        }
    }
}
