package com.hackathon24backend.payload;

import lombok.Data;

@Data
public class KycPayload {
    private String panNumber;
    private String adharNumber;
}
