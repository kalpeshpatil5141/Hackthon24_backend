package com.hackathon24backend.payload.finvupauload;

import com.hackathon24backend.payload.Header;
import lombok.Data;

@Data
public class FiRequestPayload {
    private Header header;
    private FiRequestBody body;
}
