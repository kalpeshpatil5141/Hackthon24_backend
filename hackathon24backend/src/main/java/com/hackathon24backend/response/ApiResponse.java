package com.hackathon24backend.response;

import lombok.Data;

@Data
public class ApiResponse {
    private Object data;
    private String message;
    private boolean status;

    public ApiResponse(boolean status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
