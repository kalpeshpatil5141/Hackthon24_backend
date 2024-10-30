package com.hackathon24backend.response;

import lombok.Data;

@Data
public class KeyResponse {
    private String encryptedKey;
    private String secretKey;
}
