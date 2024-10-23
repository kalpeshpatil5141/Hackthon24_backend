package com.hackathon24backend.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ConsentBody {
    private String encryptedRequest;
    private String requestDate;
    private String encryptedFiuId;
    @JsonProperty("ConsentHandle")
    private String ConsentHandle;
    private String url;
}
