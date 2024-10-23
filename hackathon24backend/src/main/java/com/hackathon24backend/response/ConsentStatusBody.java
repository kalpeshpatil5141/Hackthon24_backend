package com.hackathon24backend.response;

import lombok.Data;

@Data
public class ConsentStatusBody {
    private String consentStatus;
    private String consentId;

    public ConsentStatusBody() {
    }
}
