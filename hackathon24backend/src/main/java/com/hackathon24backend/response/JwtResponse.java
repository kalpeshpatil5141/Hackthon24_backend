package com.hackathon24backend.response;

import lombok.Data;

@Data
public class JwtResponse {
    private Token tokens;
    public JwtResponse(Token token) {
        this.tokens = token;
    }
}
