package com.hackathon24backend.config;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenStorage {
    // ConcurrentHashMap for thread-safe key-value storage
    private final ConcurrentHashMap<String, String> tokenMap = new ConcurrentHashMap<>();

    // Store a token with a specified key
    public void storeToken(String key, String token) {
        tokenMap.put(key, token);
    }

    // Retrieve a token by its key
    public String getToken(String key) {
        return tokenMap.get(key);
    }

    // Check if a token exists for the specified key
    public boolean hasToken(String key) {
        return tokenMap.containsKey(key);
    }

    // Remove a token by its key (if needed)
    public void removeToken(String key) {
        tokenMap.remove(key);
    }
}
