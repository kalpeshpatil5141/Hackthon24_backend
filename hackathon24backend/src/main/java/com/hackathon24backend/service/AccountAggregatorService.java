package com.hackathon24backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

@Service
public interface AccountAggregatorService {
    public void callFinvuLoginApi();
}
