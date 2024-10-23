package com.hackathon24backend.service;

import com.hackathon24backend.response.FinvuConsentPlusResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface AccountAggregatorService {
    public void callFinvuLoginApi();

    public FinvuConsentPlusResponse consentRquestPlus(String mobileNumber);

    public void checkConsentStatus() throws IOException, InterruptedException;
}
