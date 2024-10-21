package com.hackathon24backend.response;

import lombok.Data;

@Data
public class ApiResponse {
    private String data;
    private String message;
    private boolean status;

    public ApiResponse(boolean status, String message, String data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
