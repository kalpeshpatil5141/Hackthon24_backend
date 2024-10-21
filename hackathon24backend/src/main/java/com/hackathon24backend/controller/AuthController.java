package com.hackathon24backend.controller;

import com.hackathon24backend.config.JwtUtil;
import com.hackathon24backend.entity.UserOtp;
import com.hackathon24backend.payload.GenerateOtpPayload;
import com.hackathon24backend.payload.UserPayload;
import com.hackathon24backend.response.ApiResponse;
import com.hackathon24backend.response.JwtResponse;
import com.hackathon24backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/otp")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createToken(@RequestBody UserPayload otpRequest) {
        // Generate JWT for the mobile number
        System.out.println(otpRequest.getOtp());
        boolean result = userService.validateOtp(otpRequest.getMobileNumber(), otpRequest.getOtp());
       if(result) {
           String token = jwtUtil.generateToken(otpRequest.getMobileNumber());
           return ResponseEntity.ok(new JwtResponse(token));
       }
        return ResponseEntity.ok( new ApiResponse(false,"Otp is invalid",null));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/generateOtp")
    public ResponseEntity<String> generateOtp(@RequestBody GenerateOtpPayload request) {
        String mobileNumber = request.getMobileNumber();
        UserOtp storedOTP = userService.getStoredOTP(request.getMobileNumber());
        if(storedOTP != null){
            boolean isDeleted = userService.deleteStoredOTP(storedOTP);
            if(isDeleted) {
                String otp = userService.generateOtp(mobileNumber);
                userService.storeOtp(mobileNumber, otp);
                return ResponseEntity.ok("OTP sent to " + otp);
            }
        }else{
            String otp = userService.generateOtp(mobileNumber);
            userService.storeOtp(mobileNumber,otp);
            return ResponseEntity.ok("OTP sent to " + otp);
        }

        return ResponseEntity.ok("OTP Can't be generate ");
    }
}
