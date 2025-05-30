package main.service;

import main.model.JWTToken;
import java.time.LocalDateTime;
import java.util.Base64;

public class JWTService {
    private static final String SECRET_KEY = "mySecretKey123";
    private static final int EXPIRATION_HOURS = 24;
    
    public JWTToken generateToken(String username) {
        String payload = "{\"username\":\"" + username + "\",\"iat\":" + 
                        System.currentTimeMillis() + "}";
        String encodedPayload = Base64.getEncoder().encodeToString(payload.getBytes());
        String token = "Bearer." + encodedPayload + ".signature";
        
        LocalDateTime issuedAt = LocalDateTime.now();
        LocalDateTime expiresAt = issuedAt.plusHours(EXPIRATION_HOURS);
        
        return new JWTToken(token, username, issuedAt, expiresAt);
    }
    
    public boolean validateToken(JWTToken jwtToken) {
        if (jwtToken == null) return false;
        
        try {
            return jwtToken.isValid() && jwtToken.getToken().startsWith("Bearer.");
        } catch (Exception e) {
            return false;
        }
    }
    
    public String extractUsername(String token) {
        try {
            if (token.startsWith("Bearer.")) {
                String[] parts = token.split("\\.");
                if (parts.length >= 2) {
                    String payload = new String(Base64.getDecoder().decode(parts[1]));
                    int start = payload.indexOf("\"username\":\"") + 12;
                    int end = payload.indexOf("\"", start);
                    return payload.substring(start, end);
                }
            }
        } catch (Exception e) {
            System.err.println("Error extracting username: " + e.getMessage());
        }
        return null;
    }
}