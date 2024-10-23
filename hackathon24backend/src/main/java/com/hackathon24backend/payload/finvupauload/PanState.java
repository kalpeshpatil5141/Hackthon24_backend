package com.hackathon24backend.payload.finvupauload;

import lombok.Data;

@Data
public class PanState {
    private String address;
    private String gender;
    private String aadhaarLinked;
    private String birthDate;
    private String fullName;
    private String maskedAadhaar;
    private String panNumber;

}
