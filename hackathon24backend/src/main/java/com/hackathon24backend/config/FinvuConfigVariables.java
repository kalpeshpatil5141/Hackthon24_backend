package com.hackathon24backend.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class FinvuConfigVariables {
    @Value("${app.rid}")
    private String rid;

    @Value("${app.ts}")
    private String ts;

    @Value("${app.channelId}")
    private String channelId;

    @Value("${app.userId}")
    private String userId;

    @Value("${app.password}")
    private String password;

    @Value("${app.loginuri}")
    private String loginuri;
}
