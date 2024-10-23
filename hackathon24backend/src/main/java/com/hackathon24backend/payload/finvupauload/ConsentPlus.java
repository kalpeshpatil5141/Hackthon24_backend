package com.hackathon24backend.payload.finvupauload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class ConsentPlus {
    private String custId;
    private String consentDescription;
    private String templateName;
    private String userSessionId;
    private String redirectUrl;
    private List<String> fip;
    @JsonProperty("ConsentDetails")
    private ConsentDetails ConsentDetails;
    private String aaId;
}
