package com.hackathon24backend.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackathon24backend.payload.Header;
import lombok.Data;

@Data
public class FinvuConsentPlusResponse {
    private Header header;
    private  ConsentBody body;
}
