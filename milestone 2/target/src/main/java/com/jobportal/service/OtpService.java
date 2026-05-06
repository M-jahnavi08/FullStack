package com.jobportal.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {

    // Store OTPs in memory (Email -> OTP)
    // In a production environment, use Redis or a Database table with expiration.
    private final Map<String, String> otpStorage = new ConcurrentHashMap<>();
    private final SecureRandom secureRandom = new SecureRandom();

    public String generateOtp(String email) {
        // Generate a 6-digit OTP
        int otp = 100000 + secureRandom.nextInt(900000);
        String otpString = String.valueOf(otp);
        
        otpStorage.put(email, otpString);
        return otpString;
    }

    public boolean validateOtp(String email, String otp) {
        if (email == null || otp == null) {
            return false;
        }
        
        String storedOtp = otpStorage.get(email);
        if (storedOtp != null && storedOtp.equals(otp)) {
            // OTP is valid, remove it so it can't be reused
            otpStorage.remove(email);
            return true;
        }
        
        return false;
    }

    public void clearOtp(String email) {
        otpStorage.remove(email);
    }
}
