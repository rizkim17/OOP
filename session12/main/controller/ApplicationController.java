package main.controller;

import main.model.Product;
import main.model.JWTToken;
import main.service.AuthService;
import main.service.ProductService;
import main.view.LoginFrame;
import main.view.MainFrame;
import java.util.List;

public class ApplicationController {
    private AuthService authService;
    private ProductService productService;
    private JWTToken currentToken;
    private LoginFrame loginFrame;
    private MainFrame mainFrame;
    
    public ApplicationController() {
        this.authService = new AuthService();
        this.productService = new ProductService(authService);
        initialize();
    }
    
    private void initialize() {
        showLoginFrame();
    }
    
    public boolean login(String username, String password) {
        try {
            currentToken = authService.authenticate(username, password);
            if (currentToken != null) {
                showMainFrame();
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
            return false;
        }
    }
    
    public void logout() {
        currentToken = null;
        if (mainFrame != null) {
            mainFrame.dispose();
            mainFrame = null;
        }
        showLoginFrame();
    }
    
    public List<Product> getProducts() {
        if (currentToken == null || !authService.validateToken(currentToken)) {
            throw new SecurityException("No valid authentication token");
        }
        return productService.getAllProducts(currentToken);
    }
    
    private void showLoginFrame() {
        if (loginFrame == null) {
            loginFrame = new LoginFrame(this);
        } else {
            loginFrame.resetForm();
        }
        loginFrame.setVisible(true);
    }
    
    private void showMainFrame() {
        if (loginFrame != null) {
            loginFrame.setVisible(false);
        }
        
        mainFrame = new MainFrame(this);
        mainFrame.setVisible(true);
    }
    
    public JWTToken getCurrentToken() {
        return currentToken;
    }
    
    public boolean isAuthenticated() {
        return currentToken != null && authService.validateToken(currentToken);
    }
}
