package com.hackathon24backend.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginBody {
    private String userId;
    private String password;
}
