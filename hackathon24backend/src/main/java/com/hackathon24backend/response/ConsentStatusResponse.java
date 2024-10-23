package com.hackathon24backend.response;

import com.hackathon24backend.payload.Header;
import lombok.Data;

@Data
public class ConsentStatusResponse {
    private Header header;
    private ConsentStatusBody body;

    public ConsentStatusResponse() {
    }
}
