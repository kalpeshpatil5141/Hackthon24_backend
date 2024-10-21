package com.hackathon24backend.controller;

import com.hackathon24backend.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/getusers")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(new ApiResponse(false,"User get Successfully",null));
    }
}
