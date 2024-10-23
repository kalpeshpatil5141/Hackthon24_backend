package com.hackathon24backend;

import com.hackathon24backend.service.AccountAggregatorService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class Hackathon24backendApplication {

	public static void main(String[] args) {
		SpringApplication.run(Hackathon24backendApplication.class, args);
	}
}
