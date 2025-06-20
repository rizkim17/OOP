package main.view;

import main.controller.ApplicationController;
import main.model.Product;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainFrame extends JFrame {
    private ApplicationController controller;
    private JTable productTable;
    private ProductTableModel tableModel;
    private JButton logoutButton;
    private JButton refreshButton;
    private JLabel statusLabel;
    
    public MainFrame(ApplicationController controller) {
        this.controller = controller;
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        loadProducts();
    }
    
    private void initializeComponents() {
        setTitle("Product Management - Main Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Table setup
        tableModel = new ProductTableModel();
        productTable = new JTable(tableModel);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productTable.getTableHeader().setReorderingAllowed(false);
        
        // Buttons
        logoutButton = new JButton("Logout");
        refreshButton = new JButton("Refresh");
        
        // Status label
        statusLabel = new JLabel("Ready");
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        
        JLabel titleLabel = new JLabel("Product Management Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(refreshButton);
        buttonPanel.add(logoutButton);
        headerPanel.add(buttonPanel, BorderLayout.EAST);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Main content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        // Table with scroll pane
        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Product List"));
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(contentPanel, BorderLayout.CENTER);
        
        // Status bar
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusPanel.add(statusLabel);
        add(statusPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                    MainFrame.this,
                    "Are you sure you want to logout?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION
                );
                
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.logout();
                    dispose();
                }
            }
        });
        
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadProducts();
            }
        });
    }
    
    private void loadProducts() {
        refreshButton.setEnabled(false);
        statusLabel.setText("Loading products...");
        
        SwingUtilities.invokeLater(() -> {
            try {
                List<Product> products = controller.getProducts();
                tableModel.setProducts(products);
                statusLabel.setText("Loaded " + products.size() + " products");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                    this,
                    "Error loading products: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                statusLabel.setText("Error loading products");
            } finally {
                refreshButton.setEnabled(true);
            }
        });
    }
    
    public void updateProductList(List<Product> products) {
        tableModel.setProducts(products);
        statusLabel.setText("Products updated - Total: " + products.size());
    }
}