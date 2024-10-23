package com.hackathon24backend.config;

import com.hackathon24backend.service.AccountAggregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup {

    @Autowired
    private AccountAggregatorService accountAggregatorService;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationEvent() {
        accountAggregatorService.callFinvuLoginApi();
        System.out.println("Method called when Spring Boot application is ready.");
    }
}
