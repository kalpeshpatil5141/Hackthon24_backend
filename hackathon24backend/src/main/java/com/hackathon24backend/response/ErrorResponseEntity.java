package com.hackathon24backend.response;

import com.hackathon24backend.payload.Header;
import lombok.Data;

import java.util.List;

@Data
public class ErrorResponseEntity {
    private Header header;
    private List<Error> errors;
}
