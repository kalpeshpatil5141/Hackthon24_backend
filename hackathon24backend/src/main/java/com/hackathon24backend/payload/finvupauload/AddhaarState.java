package com.hackathon24backend.payload.finvupauload;

import lombok.Data;

@Data
public class AddhaarState {
    private String fullName;
    private String adhaarNumber;
    private String birthDate;
    private String address;
    private String gender;
    private String profileImage;
}
