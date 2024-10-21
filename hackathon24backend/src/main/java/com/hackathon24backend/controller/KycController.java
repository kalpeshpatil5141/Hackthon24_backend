package com.hackathon24backend.controller;

import com.hackathon24backend.payload.KycPayload;
import com.hackathon24backend.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/kyc")
public class KycController {

    @PostMapping("/pannumber")
    public ResponseEntity<?> validatePanNumber(@RequestBody KycPayload request) {
        return ResponseEntity.ok(new ApiResponse(true,"Pan Number Verified Successfully",null));
    }

    @PostMapping("/adhar")
    public ResponseEntity<?> validateAdhar(@RequestBody KycPayload request) {
        return ResponseEntity.ok(new ApiResponse(true,"Adhar Number Verified Successfully",null));
    }
}
