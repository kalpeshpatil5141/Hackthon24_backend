package com.hackathon24backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hackathon24backend.service.AccountAggregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/api/t")
public class AccountAggregator {

        @Autowired
        private AccountAggregatorService accountAggregatorService;

        @PostMapping("/call")
        public void callThirdPartyApi() throws JsonProcessingException {

             accountAggregatorService.callFinvuLoginApi();
        }
}


