package main.service;

import main.model.User;
import main.model.JWTToken;

public class AuthService {
    private JWTService jwtService;
    
    public AuthService() {
        this.jwtService = new JWTService();
    }
    
    public JWTToken authenticate(String username, String password) {
        if ("admin".equals(username) && "password123".equals(password)) {
            return jwtService.generateToken(username);
        }
        return null;
    }
    
    public boolean validateToken(JWTToken token) {
        return jwtService.validateToken(token);
    }
    
    public User getCurrentUser(JWTToken token) {
        if (validateToken(token)) {
            String username = jwtService.extractUsername(token.getToken());
            return new User(username, "", "admin");
        }
        return null;
    }
}