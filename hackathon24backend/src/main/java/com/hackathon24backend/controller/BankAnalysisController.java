package com.hackathon24backend.controller;

import com.hackathon24backend.service.serviceImpl.ClaudeApiService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/bank-analysis")
public class BankAnalysisController {

    private final ClaudeApiService claudeApiService;

    public BankAnalysisController(ClaudeApiService claudeApiService) {
        this.claudeApiService = claudeApiService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<String> analyzeBankStatement(@RequestBody String bankData) {
        String analysis = claudeApiService.analyzeBankStatement(bankData);
        return ResponseEntity.ok(analysis);
    }
}
