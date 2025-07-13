package com.cognizant.springlearn.controller;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class AuthenticationController {

    @GetMapping("/authenticate")
    public Map<String, String> authenticate(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        Map<String, String> response = new HashMap<>();

        // Defensive check
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            response.put("token", "Missing or invalid Authorization header");
            return response;
        }

        try {
            // Decode Basic auth header
            String base64Credentials = authHeader.substring("Basic ".length());
            byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
            String decodedCredentials = new String(decodedBytes);

            // Expecting format "user:pwd"
            if (!decodedCredentials.contains(":")) {
                response.put("token", "Invalid credentials format");
                return response;
            }

            String user = decodedCredentials.split(":")[0];

            // Generate JWT token
            String token = Jwts.builder()
                    .setSubject(user)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 20 * 60 * 1000))
                    .signWith(SignatureAlgorithm.HS256, "secretkey")
                    .compact();

            response.put("token", token);
            return response;

        } catch (Exception e) {
            // Catch all exceptions to avoid 500
            response.put("token", "Error: " + e.getMessage());
            return response;
        }
    }
}
