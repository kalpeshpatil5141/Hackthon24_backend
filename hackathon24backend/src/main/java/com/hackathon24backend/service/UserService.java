package com.hackathon24backend.service;

import com.hackathon24backend.entity.UserOtp;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public String generateOtp(String mobileNumber);
    public boolean validateOtp(String mobileNumber, String otp);
    public void storeOtp(String mobile, String otp);
    public UserOtp getStoredOTP(String mobileNumber);
    public boolean deleteStoredOTP(UserOtp otp);
}