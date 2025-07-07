import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class DatabaseManager {
    private static final String DATABASE_URL = "jdbc:sqlite:perpustakaan.db";
    private Connection connection;
    
    public DatabaseManager() {
        try {
            // Load SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DATABASE_URL);
            createTable();
            System.out.println("Database connection established successfully.");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        }
    }
    
    private void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS buku (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                judul TEXT NOT NULL,
                penulis TEXT NOT NULL,
                tahun_terbit INTEGER NOT NULL,
                isbn TEXT UNIQUE NOT NULL,
                harga REAL NOT NULL,
                kuantitas INTEGER NOT NULL
            )
        """;
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table 'buku' created or already exists.");
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }
    
    public boolean tambahBuku(String judul, String penulis, int tahunTerbit, String isbn, double harga, int kuantitas) {
        String sql = "INSERT INTO buku (judul, penulis, tahun_terbit, isbn, harga, kuantitas) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, judul);
            pstmt.setString(2, penulis);
            pstmt.setInt(3, tahunTerbit);
            pstmt.setString(4, isbn);
            pstmt.setDouble(5, harga);
            pstmt.setInt(6, kuantitas);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                System.err.println("Error: ISBN sudah ada! Silakan gunakan ISBN yang berbeda.");
            } else {
                System.err.println("Error adding book: " + e.getMessage());
            }
            return false;
        }
    }
    
    public List<Buku> getAllBuku() {
        List<Buku> daftarBuku = new ArrayList<>();
        String sql = "SELECT * FROM buku ORDER BY id";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Buku buku = new Buku(
                    rs.getInt("id"),
                    rs.getString("judul"),
                    rs.getString("penulis"),
                    rs.getInt("tahun_terbit"),
                    rs.getString("isbn"),
                    rs.getDouble("harga"),
                    rs.getInt("kuantitas")
                );
                daftarBuku.add(buku);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving books: " + e.getMessage());
        }
        
        return daftarBuku;
    }
    
    public Buku getBukuById(int id) {
        String sql = "SELECT * FROM buku WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Buku(
                    rs.getInt("id"),
                    rs.getString("judul"),
                    rs.getString("penulis"),
                    rs.getInt("tahun_terbit"),
                    rs.getString("isbn"),
                    rs.getDouble("harga"),
                    rs.getInt("kuantitas")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving book by ID: " + e.getMessage());
        }
        
        return null;
    }
    
    public Buku getBukuByIsbn(String isbn) {
        String sql = "SELECT * FROM buku WHERE isbn = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, isbn);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Buku(
                    rs.getInt("id"),
                    rs.getString("judul"),
                    rs.getString("penulis"),
                    rs.getInt("tahun_terbit"),
                    rs.getString("isbn"),
                    rs.getDouble("harga"),
                    rs.getInt("kuantitas")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving book by ISBN: " + e.getMessage());
        }
        
        return null;
    }
    
    public List<Buku> cariBukuByJudul(String judul) {
        List<Buku> hasil = new ArrayList<>();
        String sql = "SELECT * FROM buku WHERE judul LIKE ? ORDER BY id";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + judul + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Buku buku = new Buku(
                    rs.getInt("id"),
                    rs.getString("judul"),
                    rs.getString("penulis"),
                    rs.getInt("tahun_terbit"),
                    rs.getString("isbn"),
                    rs.getDouble("harga"),
                    rs.getInt("kuantitas")
                );
                hasil.add(buku);
            }
        } catch (SQLException e) {
            System.err.println("Error searching books by title: " + e.getMessage());
        }
        
        return hasil;
    }
    
    public List<Buku> cariBukuByPenulis(String penulis) {
        List<Buku> hasil = new ArrayList<>();
        String sql = "SELECT * FROM buku WHERE penulis LIKE ? ORDER BY id";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + penulis + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Buku buku = new Buku(
                    rs.getInt("id"),
                    rs.getString("judul"),
                    rs.getString("penulis"),
                    rs.getInt("tahun_terbit"),
                    rs.getString("isbn"),
                    rs.getDouble("harga"),
                    rs.getInt("kuantitas")
                );
                hasil.add(buku);
            }
        } catch (SQLException e) {
            System.err.println("Error searching books by author: " + e.getMessage());
        }
        
        return hasil;
    }
    
    public boolean updateBuku(int id, String judul, String penulis, int tahunTerbit, double harga, int kuantitas) {
        String sql = "UPDATE buku SET judul = ?, penulis = ?, tahun_terbit = ?, harga = ?, kuantitas = ? WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, judul);
            pstmt.setString(2, penulis);
            pstmt.setInt(3, tahunTerbit);
            pstmt.setDouble(4, harga);
            pstmt.setInt(5, kuantitas);
            pstmt.setInt(6, id);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating book: " + e.getMessage());
            return false;
        }
    }
    
    public boolean updateHargaDanKuantitas(int id, double harga, int kuantitas) {
        String sql = "UPDATE buku SET harga = ?, kuantitas = ? WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDouble(1, harga);
            pstmt.setInt(2, kuantitas);
            pstmt.setInt(3, id);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating price and quantity: " + e.getMessage());
            return false;
        }
    }
    
    public boolean deleteBuku(int id) {
        String sql = "DELETE FROM buku WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting book: " + e.getMessage());
            return false;
        }
    }
    
    public boolean deleteBukuByIsbn(String isbn) {
        String sql = "DELETE FROM buku WHERE isbn = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, isbn);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting book by ISBN: " + e.getMessage());
            return false;
        }
    }
    
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
}