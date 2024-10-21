package com.hackathon24backend.service.serviceImpl;

import com.hackathon24backend.entity.User;
import com.hackathon24backend.entity.UserOtp;
import com.hackathon24backend.repository.UserOtpRepository;
import com.hackathon24backend.repository.UserRepository;
import com.hackathon24backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
   private UserRepository userRepository;

    @Autowired
    private UserOtpRepository userOtpRepository;

    private Map<String, String> otpData = new HashMap<>();
    private static final long OTP_VALID_DURATION = 5 * 60 * 1000;

    public String generateOtp(String mobileNumber) {
        String otp = String.valueOf((int) (Math.random() * 9000) + 1000); // 4 digit OTP
        otpData.put(mobileNumber, otp);
        return otp;
    }

    public boolean validateOtp(String mobileNumber, String otp) {
        User byMobileNumber = userRepository.findByMobileNumber(mobileNumber);
        UserOtp otpOb = userOtpRepository.findByUser(byMobileNumber);
        if (otpOb.getOtp() != null && otpOb.getOtp().equals(otp)) {
            return true;
        }
        return false;
    }

    @Override
    public void storeOtp(String mobile, String otp) {
        User user = userRepository.findByMobileNumber(mobile);
        if(user != null){
        }else{
             user = new User();
        }
        user.setIs_validate(false);
        user.setMobileNumber(mobile);

        User usrt = userRepository.save(user);
        UserOtp uotp = new UserOtp();
        uotp.setOtp(otp);
        uotp.setUser(usrt);
        userOtpRepository.save(uotp);
    }

    public UserOtp getStoredOTP(String mobileNumber) {
        User byMobileNumber = userRepository.findByMobileNumber(mobileNumber);
        UserOtp otpOb = userOtpRepository.findByUser(byMobileNumber);
        if (otpOb != null) {
            return otpOb;
        }
        return null;
    }

    public boolean deleteStoredOTP(UserOtp otp) {
        try {
            userOtpRepository.delete(otp);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
}
