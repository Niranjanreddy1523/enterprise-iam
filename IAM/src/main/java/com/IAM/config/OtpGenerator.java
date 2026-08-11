package com.IAM.config;

import java.util.Random;

public class OtpGenerator {
    public static String generateOtp(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
