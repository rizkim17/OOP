package main.service;

import main.model.Product;
import main.model.JWTToken;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private AuthService authService;
    private List<Product> products;
    
    public ProductService(AuthService authService) {
        this.authService = authService;
        initializeProducts();
    }
    
    private void initializeProducts() {
        products = new ArrayList<>();
        products.add(new Product(1, "Laptop Dell XPS", 15000000, 10, "Electronics"));
        products.add(new Product(2, "Mouse Wireless", 250000, 25, "Electronics"));
        products.add(new Product(3, "Keyboard Mechanical", 800000, 15, "Electronics"));
        products.add(new Product(4, "Monitor 24 inch", 2500000, 8, "Electronics"));
        products.add(new Product(5, "Headphone Gaming", 1200000, 12, "Electronics"));
        products.add(new Product(6, "Smartphone Samsung", 8000000, 20, "Electronics"));
        products.add(new Product(7, "Tablet iPad", 12000000, 5, "Electronics"));
        products.add(new Product(8, "Webcam HD", 450000, 18, "Electronics"));
    }
    
    public List<Product> getAllProducts(JWTToken token) {
        if (authService.validateToken(token)) {
            return new ArrayList<>(products);
        }
        throw new SecurityException("Invalid or expired token");
    }
    
    public Product getProductById(int id, JWTToken token) {
        if (authService.validateToken(token)) {
            return products.stream()
                    .filter(p -> p.getId() == id)
                    .findFirst()
                    .orElse(null);
        }
        throw new SecurityException("Invalid or expired token");
    }
}