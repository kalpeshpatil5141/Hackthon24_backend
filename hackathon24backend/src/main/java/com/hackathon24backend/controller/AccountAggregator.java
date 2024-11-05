package com.hackathon24backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hackathon24backend.config.TokenStorage;
import com.hackathon24backend.response.ApiResponse;
import com.hackathon24backend.response.FinvuCheckStatusResponse;
import com.hackathon24backend.response.FinvuConsentPlusResponse;
import com.hackathon24backend.response.KeyResponse;
import com.hackathon24backend.service.AccountAggregatorService;
import com.hackathon24backend.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        public ResponseEntity<?> checkConsentStatus() throws IOException, InterruptedException {

                String respon = accountAggregatorService.checkConsentStatus();
                if(respon  != null && !respon.isEmpty()){
                        FinvuCheckStatusResponse response =new FinvuCheckStatusResponse();
                        response.setData(respon);
                        return ResponseEntity.ok( new ApiResponse(true,"Success",response));
                }
                return ResponseEntity.ok( new ApiResponse(false,"Failed", HttpStatus.NOT_FOUND));
//                String temp ="{\"id\":\"msg_015TcVU1PxXHzmBw2SM21Kzo\",\"type\":\"message\",\"role\":\"assistant\",\"model\":\"claude-3-5-sonnet-20240620\",\"content\":[{\"type\":\"text\",\"text\":\"{\\n  \\\"bankName\\\": \\\"Finvu Bank Ltd.\\\",\\n  \\\"accountNumber\\\": \\\"XXXXX0254\\\",\\n  \\\"salary\\\": {\\n    \\\"lastSalary\\\": 30000,\\n    \\\"averageSalaryInLast3Months\\\": 30000,\\n    \\\"numberOfSalariesInLast6Months\\\": 6\\n  },\\n  \\\"emi\\\": {\\n    \\\"totalEmifromLastMonth\\\": 2500,\\n    \\\"numberOfEmiPaidInLastMonth\\\": 1,\\n    \\\"averageMonthlyEmiInLast6Months\\\": 2500\\n  },\\n  \\\"accountHolderDetails\\\": {\\n    \\\"period\\\": \\\"12 months\\\",\\n    \\\"endDate\\\": \\\"2024-10-24\\\",\\n    \\\"bankName\\\": \\\"Finvu Bank Ltd.\\\",\\n    \\\"currency\\\": \\\"INR\\\",\\n    \\\"accountNo\\\": \\\"XXXXX0254\\\",\\n    \\\"startDate\\\": \\\"2023-10-24\\\",\\n    \\\"clientName\\\": \\\"fName mName lName\\\",\\n    \\\"accountType\\\": \\\"SAVINGS\\\",\\n    \\\"closingBalance\\\": 86560,\\n    \\\"openingBalance\\\": 102020\\n  },\\n  \\\"monthlySummary\\\": [\\n    {\\n      \\\"month\\\": \\\"October 2024\\\",\\n      \\\"totalDebitAmount\\\": 15000,\\n      \\\"totalCreditAmount\\\": 30000,\\n      \\\"averageMonthlyBalance\\\": 93960\\n    },\\n    {\\n      \\\"month\\\": \\\"September 2024\\\",\\n      \\\"totalDebitAmount\\\": 15150,\\n      \\\"totalCreditAmount\\\": 30000,\\n      \\\"averageMonthlyBalance\\\": 77635\\n    },\\n    {\\n      \\\"month\\\": \\\"August 2024\\\",\\n      \\\"totalDebitAmount\\\": 18730,\\n      \\\"totalCreditAmount\\\": 30350,\\n      \\\"averageMonthlyBalance\\\": 63940\\n    },\\n    {\\n      \\\"month\\\": \\\"July 2024\\\",\\n      \\\"totalDebitAmount\\\": 16300,\\n      \\\"totalCreditAmount\\\": 30000,\\n      \\\"averageMonthlyBalance\\\": 52590\\n    },\\n    {\\n      \\\"month\\\": \\\"June 2024\\\",\\n      \\\"totalDebitAmount\\\": 62330,\\n      \\\"totalCreditAmount\\\": 30200,\\n      \\\"averageMonthlyBalance\\\": 51395\\n    },\\n    {\\n      \\\"month\\\": \\\"May 2024\\\",\\n      \\\"totalDebitAmount\\\": 39300,\\n      \\\"totalCreditAmount\\\": 30000,\\n      \\\"averageMonthlyBalance\\\": 77870\\n    },\\n    {\\n      \\\"month\\\": \\\"April 2024\\\",\\n      \\\"totalDebitAmount\\\": 79180,\\n      \\\"totalCreditAmount\\\": 30150,\\n      \\\"averageMonthlyBalance\\\": 97585\\n    },\\n    {\\n      \\\"month\\\": \\\"March 2024\\\",\\n      \\\"totalDebitAmount\\\": 51800,\\n      \\\"totalCreditAmount\\\": 30000,\\n      \\\"averageMonthlyBalance\\\": 107585\\n    },\\n    {\\n      \\\"month\\\": \\\"February 2024\\\",\\n      \\\"totalDebitAmount\\\": 49650,\\n      \\\"totalCreditAmount\\\": 60000,\\n      \\\"averageMonthlyBalance\\\": 100250\\n    },\\n    {\\n      \\\"month\\\": \\\"January 2024\\\",\\n      \\\"totalDebitAmount\\\": 24900,\\n      \\\"totalCreditAmount\\\": 30900,\\n      \\\"averageMonthlyBalance\\\": 88100\\n    },\\n    {\\n      \\\"month\\\": \\\"December 2023\\\",\\n      \\\"totalDebitAmount\\\": 55720,\\n      \\\"totalCreditAmount\\\": 39100,\\n      \\\"averageMonthlyBalance\\\": 68210\\n    },\\n    {\\n      \\\"month\\\": \\\"November 2023\\\",\\n      \\\"totalDebitAmount\\\": 30040,\\n      \\\"totalCreditAmount\\\": 30200,\\n      \\\"averageMonthlyBalance\\\": 109690\\n    }\\n  ],\\n  \\\"insights\\\": {\\n    \\\"authenticityCheck\\\": true,\\n    \\\"inwardBouncesCount\\\": 1,\\n    \\\"regularIncomeCheck\\\": true,\\n    \\\"outwardBouncesCount\\\": 0,\\n    \\\"negativeBalanceCheck\\\": false\\n  }\\n}\"}],\"stop_reason\":\"end_turn\",\"stop_sequence\":null,\"usage\":{\"input_tokens\":11834,\"output_tokens\":1002}}";
//                FinvuCheckStatusResponse response =new FinvuCheckStatusResponse();
//                response.setData(temp);
//                return ResponseEntity.ok( new ApiResponse(true,"Success",response));
        }

        @GetMapping("/get-finvu-token")
        public ResponseEntity<?> getFinvuToken() {
                return ResponseEntity.ok( new ApiResponse(true,"Finvu Token Fetch Success",tokenStorage.getToken("token")));
        }

        @PostMapping("/encrypt-key/{key}")
        public ResponseEntity<?> encryptionKey(@PathVariable String key) throws Exception {
                KeyResponse keyResponse = new KeyResponse();
                String secretKey = EncryptionUtil.generateSecretKey();
                String encryptedKey = EncryptionUtil.encrypt(key, secretKey);
                keyResponse.setSecretKey(secretKey);
                keyResponse.setEncryptedKey(encryptedKey);
                return ResponseEntity.ok(new ApiResponse(true, "Encryption Successful", keyResponse));
        }
}


