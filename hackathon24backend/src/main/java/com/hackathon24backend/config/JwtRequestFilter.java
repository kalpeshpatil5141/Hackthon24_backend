package com.hackathon24backend.config;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String token = null;
        String mobileNumber = null;
        final String requestPath = request.getRequestURI();
        if (requestPath.equals("/api/otp/authenticate")) {
            chain.doFilter(request, response);  // Skip JWT filtering for this endpoint
            return;
        }
try {
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
        token = authorizationHeader.substring(7);
        mobileNumber = jwtUtil.extractMobileNumber(token);  // Extract mobile number from token
    }

    if (mobileNumber != null && SecurityContextHolder.getContext().getAuthentication() == null) {

        if (jwtUtil.validateToken(token, mobileNumber)) {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(mobileNumber, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }
}catch (io.jsonwebtoken.ExpiredJwtException e) {
    // Handle expired token
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.getWriter().write("Token has expired. Please log in again.");
    response.getWriter().flush();
    return;
} catch (io.jsonwebtoken.SignatureException e) {
    // Handle invalid token signature
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.getWriter().write("Invalid token signature.");
    response.getWriter().flush();
    return;
} catch (Exception e) {
    // Handle other exceptions related to JWT
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.getWriter().write("Invalid token.");
    response.getWriter().flush();
    return;
}

        chain.doFilter(request, response);
    }
}

