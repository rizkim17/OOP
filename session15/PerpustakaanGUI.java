import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;

public class PerpustakaanGUI extends JFrame {
    private PerpustakaanManager perpustakaan;
    private JTable bukuTable;
    private DefaultTableModel tableModel;
    private JTextField judulField, penulisField, tahunField, isbnField, hargaField, kuantitasField;
    private JTextField searchField;
    private JComboBox<String> searchTypeCombo;
    
    public PerpustakaanGUI() {
        perpustakaan = new PerpustakaanManager();
        initializeComponents();
        setupLayout();
        loadData();
        
        // Add sample data
        perpustakaan.tambahBuku("Harry Potter dan Batu Bertuah", "J.K. Rowling", 1997, "978-0747532699", 125000.0, 15);
        perpustakaan.tambahBuku("Laskar Pelangi", "Andrea Hirata", 2005, "978-979-3062-79-2", 85000.0, 20);
        perpustakaan.tambahBuku("Bumi Manusia", "Pramoedya Ananta Toer", 1980, "978-979-3062-15-0", 95000.0, 10);
        
        refreshTable();
    }
    
    private void initializeComponents() {
        setTitle("Sistem Manajemen Inventaris Perpustakaan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        // Table setup
        String[] columnNames = {"ID", "Judul", "Penulis", "Tahun", "ISBN", "Harga", "Stok"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        bukuTable = new JTable(tableModel);
        bukuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bukuTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedBookData();
            }
        });
        
        // Form fields
        judulField = new JTextField(20);
        penulisField = new JTextField(20);
        tahunField = new JTextField(20);
        isbnField = new JTextField(20);
        hargaField = new JTextField(20);
        kuantitasField = new JTextField(20);
        
        // Search components
        searchField = new JTextField(20);
        searchTypeCombo = new JComboBox<>(new String[]{"ID", "ISBN", "Judul", "Penulis"});
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Top panel - Search
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder("Pencarian"));
        searchPanel.add(new JLabel("Cari berdasarkan:"));
        searchPanel.add(searchTypeCombo);
        searchPanel.add(new JLabel("Kata kunci:"));
        searchPanel.add(searchField);
        
        JButton searchButton = new JButton("Cari");
        searchButton.addActionListener(e -> searchBooks());
        searchPanel.add(searchButton);
        
        JButton showAllButton = new JButton("Tampilkan Semua");
        showAllButton.addActionListener(e -> refreshTable());
        searchPanel.add(showAllButton);
        
        add(searchPanel, BorderLayout.NORTH);
        
        // Center panel - Table
        JScrollPane scrollPane = new JScrollPane(bukuTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Daftar Buku"));
        add(scrollPane, BorderLayout.CENTER);
        
        // Bottom panel - Form and buttons
        JPanel bottomPanel = new JPanel(new BorderLayout());
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Informasi Buku"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Form fields
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(new JLabel("Judul:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(judulField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Penulis:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(penulisField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Tahun Terbit:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(tahunField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(isbnField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Harga (Rp):"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(hargaField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Kuantitas:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(kuantitasField, gbc);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton addButton = new JButton("Tambah Buku");
        addButton.addActionListener(e -> addBook());
        buttonPanel.add(addButton);
        
        JButton updateButton = new JButton("Update Buku");
        updateButton.addActionListener(e -> updateBook());
        buttonPanel.add(updateButton);
        
        JButton deleteButton = new JButton("Hapus Buku");
        deleteButton.addActionListener(e -> deleteBook());
        buttonPanel.add(deleteButton);
        
        JButton clearButton = new JButton("Bersihkan Form");
        clearButton.addActionListener(e -> clearForm());
        buttonPanel.add(clearButton);
        
        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(bottomPanel, BorderLayout.SOUTH);
        
        // Window closing event
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                perpustakaan.closeDatabase();
                System.exit(0);
            }
        });
    }
    
    private void loadData() {
        refreshTable();
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Buku> books = perpustakaan.dbManager.getAllBuku();
        
        for (Buku book : books) {
            Object[] row = {
                book.getId(),
                book.getJudul(),
                book.getPenulis(),
                book.getTahunTerbit(),
                book.getIsbn(),
                String.format("Rp%.2f", book.getHarga()),
                book.getKuantitas()
            };
            tableModel.addRow(row);
        }
    }
    
    private void loadSelectedBookData() {
        int selectedRow = bukuTable.getSelectedRow();
        if (selectedRow >= 0) {
            judulField.setText(tableModel.getValueAt(selectedRow, 1).toString());
            penulisField.setText(tableModel.getValueAt(selectedRow, 2).toString());
            tahunField.setText(tableModel.getValueAt(selectedRow, 3).toString());
            isbnField.setText(tableModel.getValueAt(selectedRow, 4).toString());
            
            // Remove "Rp" prefix from price
            String harga = tableModel.getValueAt(selectedRow, 5).toString();
            harga = harga.replace("Rp", "").replace(",", "");
            hargaField.setText(harga);
            
            kuantitasField.setText(tableModel.getValueAt(selectedRow, 6).toString());
        }
    }
    
    private void addBook() {
        if (validateForm()) {
            try {
                String judul = judulField.getText().trim();
                String penulis = penulisField.getText().trim();
                int tahun = Integer.parseInt(tahunField.getText().trim());
                String isbn = isbnField.getText().trim();
                double harga = Double.parseDouble(hargaField.getText().trim());
                int kuantitas = Integer.parseInt(kuantitasField.getText().trim());
                
                boolean success = perpustakaan.tambahBuku(judul, penulis, tahun, isbn, harga, kuantitas);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Buku berhasil ditambahkan!");
                    clearForm();
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menambahkan buku. Periksa kembali data yang dimasukkan.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Format angka tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void updateBook() {
        int selectedRow = bukuTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Pilih buku yang akan diupdate!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (validateForm()) {
            try {
                int id = (Integer) tableModel.getValueAt(selectedRow, 0);
                String judul = judulField.getText().trim();
                String penulis = penulisField.getText().trim();
                int tahun = Integer.parseInt(tahunField.getText().trim());
                double harga = Double.parseDouble(hargaField.getText().trim());
                int kuantitas = Integer.parseInt(kuantitasField.getText().trim());
                
                boolean success = perpustakaan.perbaruiBukuById(id, judul, penulis, tahun, harga, kuantitas);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Buku berhasil diupdate!");
                    clearForm();
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal mengupdate buku!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Format angka tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void deleteBook() {
        int selectedRow = bukuTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Pilih buku yang akan dihapus!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int id = (Integer) tableModel.getValueAt(selectedRow, 0);
        String judul = tableModel.getValueAt(selectedRow, 1).toString();
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Apakah Anda yakin ingin menghapus buku:\n" + judul + "?", 
            "Konfirmasi Hapus", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = perpustakaan.hapusBukuById(id);
            if (success) {
                JOptionPane.showMessageDialog(this, "Buku berhasil dihapus!");
                clearForm();
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus buku!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void searchBooks() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Masukkan kata kunci pencarian!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String searchType = (String) searchTypeCombo.getSelectedItem();
        tableModel.setRowCount(0);
        
        try {
            List<Buku> results = null;
            
            switch (searchType) {
                case "ID":
                    int id = Integer.parseInt(searchTerm);
                    Buku book = perpustakaan.dbManager.getBukuById(id);
                    if (book != null) {
                        results = List.of(book);
                    }
                    break;
                case "ISBN":
                    book = perpustakaan.dbManager.getBukuByIsbn(searchTerm);
                    if (book != null) {
                        results = List.of(book);
                    }
                    break;
                case "Judul":
                    results = perpustakaan.dbManager.cariBukuByJudul(searchTerm);
                    break;
                case "Penulis":
                    results = perpustakaan.dbManager.cariBukuByPenulis(searchTerm);
                    break;
            }
            
            if (results != null && !results.isEmpty()) {
                for (Buku book : results) {
                    Object[] row = {
                        book.getId(),
                        book.getJudul(),
                        book.getPenulis(),
                        book.getTahunTerbit(),
                        book.getIsbn(),
                        String.format("Rp%.2f", book.getHarga()),
                        book.getKuantitas()
                    };
                    tableModel.addRow(row);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Tidak ada buku yang ditemukan!", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Format ID tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validateForm() {
        if (judulField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Judul tidak boleh kosong!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (penulisField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Penulis tidak boleh kosong!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (isbnField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ISBN tidak boleh kosong!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            int tahun = Integer.parseInt(tahunField.getText().trim());
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            if (tahun < 1000 || tahun > currentYear) {
                JOptionPane.showMessageDialog(this, "Tahun terbit tidak valid! Masukkan tahun antara 1000 dan " + currentYear, "Validation Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Format tahun tidak valid!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            double harga = Double.parseDouble(hargaField.getText().trim());
            if (harga < 0) {
                JOptionPane.showMessageDialog(this, "Harga tidak boleh negatif!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Format harga tidak valid!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            int kuantitas = Integer.parseInt(kuantitasField.getText().trim());
            if (kuantitas < 0) {
                JOptionPane.showMessageDialog(this, "Kuantitas tidak boleh negatif!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Format kuantitas tidak valid!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private void clearForm() {
        judulField.setText("");
        penulisField.setText("");
        tahunField.setText("");
        isbnField.setText("");
        hargaField.setText("");
        kuantitasField.setText("");
        bukuTable.clearSelection();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            new PerpustakaanGUI().setVisible(true);
        });
    }
}