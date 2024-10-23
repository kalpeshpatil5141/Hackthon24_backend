package com.hackathon24backend.response;

import com.hackathon24backend.payload.Header;
import lombok.Data;

@Data
public class FiResponse {
    private Header header;
    private FiResponseBody body;
}
