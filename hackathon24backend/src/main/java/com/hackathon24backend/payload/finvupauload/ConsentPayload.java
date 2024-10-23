package com.hackathon24backend.payload.finvupauload;

import com.hackathon24backend.payload.Header;
import com.hackathon24backend.payload.LoginBody;
import lombok.Data;

@Data
public class ConsentPayload {
    private Header header;
    private ConsentPlus body;
}
