package com.hackathon24backend.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtUtil {

    private String SECRET_KEY = "secret";

    public String generateToken(String mobileNumber) {
        return Jwts.builder()
                .setSubject(mobileNumber)  // Use mobile number as subject
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractMobileNumber(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();  // Extract mobile number (subject)
    }

    public Boolean validateToken(String token, String mobileNumber) {
        final String extractedMobileNumber = extractMobileNumber(token);
        return (extractedMobileNumber.equals(mobileNumber) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration().before(new Date());
    }
}

