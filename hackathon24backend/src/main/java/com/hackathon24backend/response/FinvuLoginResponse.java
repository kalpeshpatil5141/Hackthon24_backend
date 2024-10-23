package com.hackathon24backend.response;

import com.hackathon24backend.payload.Header;
import lombok.Data;

@Data
public class FinvuLoginResponse {
    private Header header;
    private FinvuResponseBody body;
}
