package com.hackathon24backend.response;

import lombok.Data;

import java.util.List;
@Data
public class FiStatusBody {
    private String fiRequestStatus;
    private List<FiStatusAccount> accounts;

}
