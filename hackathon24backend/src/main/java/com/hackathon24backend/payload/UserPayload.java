package com.hackathon24backend.payload;

import lombok.Data;

@Data
public class UserPayload {
    private String mobileNumber;
    private String otp;
}
