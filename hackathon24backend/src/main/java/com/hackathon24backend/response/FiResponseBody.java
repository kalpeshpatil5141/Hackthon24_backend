package com.hackathon24backend.response;

import lombok.Data;

@Data
public class FiResponseBody {
    private String ver;
    private String timestamp;
    private String txnid;
    private String consentId;
    private String sessionId;
    private String consentHandleId;
}
