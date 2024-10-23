package com.hackathon24backend.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FinvuLoginPayload {
    private Header header;
    private LoginBody body;

    public FinvuLoginPayload(){

    }

    public FinvuLoginPayload(Header header, LoginBody body) {
        this.header = header;
        this.body = body;
    }

}
