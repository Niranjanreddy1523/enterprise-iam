package com.IAM.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.IAM.entity.Otp;
import com.IAM.entity.User;
import com.IAM.repository.OtpRepository;

@Service
public class OtpServiceImpl implements OtpService {

    private final OtpRepository otpRepository;

    public OtpServiceImpl(OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    @Override
    public String generateOtp(User user) {
        String otpCode = String.format("%06d", new Random().nextInt(999999));
        Otp otp = new Otp();
        otp.setUser(user);
        otp.setCode(otpCode);
        otp.setExpiry(LocalDateTime.now().plusMinutes(5));
        otpRepository.save(otp);
        return otpCode;
    }

    @Override
    public boolean validateOtp(User user, String otpCode) {
        return otpRepository.findAll().stream()
                .filter(o -> o.getUser().getId().equals(user.getId()))
                .filter(o -> o.getCode().equals(otpCode))
                .filter(o -> o.getExpiry().isAfter(LocalDateTime.now()))
                .findFirst()
                .map(o -> {
                    otpRepository.delete(o); 
                    return true;
                })
                .orElse(false);
    }

    @Override
    public void invalidateOtp(User user) {
        otpRepository.findAll().stream()
                .filter(o -> o.getUser().getId().equals(user.getId()))
                .forEach(otpRepository::delete);
    }
}

