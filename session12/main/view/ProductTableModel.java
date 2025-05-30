package main.view;

import main.model.Product;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.ArrayList;

public class ProductTableModel extends AbstractTableModel {
    private String[] columnNames = {"ID", "Name", "Price", "Stock", "Category"};
    private List<Product> products;
    
    public ProductTableModel() {
        this.products = new ArrayList<>();
    }
    
    public void setProducts(List<Product> products) {
        this.products = products;
        fireTableDataChanged();
    }
    
    @Override
    public int getRowCount() {
        return products.size();
    }
    
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Product product = products.get(rowIndex);
        switch (columnIndex) {
            case 0: return product.getId();
            case 1: return product.getName();
            case 2: return String.format("Rp %.0f", product.getPrice());
            case 3: return product.getStock();
            case 4: return product.getCategory();
            default: return null;
        }
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0: case 3: return Integer.class;
            case 2: return String.class;
            default: return String.class;
        }
    }
}