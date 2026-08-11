package com.IAM.service;


import com.IAM.entity.User;

public interface OtpService {
    String generateOtp(User user);
    boolean validateOtp(User user, String otpCode);
    void invalidateOtp(User user);
}


