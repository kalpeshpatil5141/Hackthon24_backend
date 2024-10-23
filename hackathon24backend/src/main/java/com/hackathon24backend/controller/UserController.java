package com.hackathon24backend.controller;

import com.hackathon24backend.config.TokenStorage;
import com.hackathon24backend.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private TokenStorage tokenStorage;

    @GetMapping("/getusers")
    public ResponseEntity<?> getUsers() {
        System.out.println(tokenStorage.getToken("token"));
        return ResponseEntity.ok(new ApiResponse(false,"User get Successfully",null));
    }
}
