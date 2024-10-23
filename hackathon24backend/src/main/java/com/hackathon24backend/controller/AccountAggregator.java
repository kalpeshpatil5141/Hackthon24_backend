package com.hackathon24backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hackathon24backend.config.TokenStorage;
import com.hackathon24backend.response.ApiResponse;
import com.hackathon24backend.response.FinvuConsentPlusResponse;
import com.hackathon24backend.service.AccountAggregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/api/aa")
public class AccountAggregator {

        @Autowired
        private AccountAggregatorService accountAggregatorService;

        @Autowired
        private TokenStorage tokenStorage;

        @PostMapping("/call")
        public void callThirdPartyApi() throws JsonProcessingException {

             accountAggregatorService.callFinvuLoginApi();
        }

        @PostMapping("/consent-details/{mobileNumber}")
        public ResponseEntity<?> consentRequestPlus(@PathVariable String mobileNumber){

                FinvuConsentPlusResponse finvuConsentPlusResponseMono = accountAggregatorService.consentRquestPlus(mobileNumber);
                if(finvuConsentPlusResponseMono != null){
                        return ResponseEntity.ok(new ApiResponse(true, "Consent Request Successful", finvuConsentPlusResponseMono));
                }else{
                        return ResponseEntity.ok( new ApiResponse(false,"Consent Request Failed",null));
                }
        }

        @GetMapping("/check-consent-status")
        public void checkConsentStatus() throws IOException, InterruptedException {
                accountAggregatorService.checkConsentStatus();
        }

        @GetMapping("/get-finvu-token")
        public ResponseEntity<?> getFinvuToken() {
                return ResponseEntity.ok( new ApiResponse(true,"Finvu Token Fetch Success",tokenStorage.getToken("token")));
        }
}


