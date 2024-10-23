package com.hackathon24backend.payload.finvupauload;

import lombok.Data;

@Data
public class FiRequestBody {
    private String custId;
    private String consentHandleId;
    private String consentId;
    private String dateTimeRangeFrom;
    private String dateTimeRangeTo;
}
