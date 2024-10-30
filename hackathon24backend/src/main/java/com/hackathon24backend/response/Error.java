package com.hackathon24backend.response;

import lombok.Data;

@Data
public class Error {
    private int errorCode;
    private String errorMsg;
}
