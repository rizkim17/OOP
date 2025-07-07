import java.util.List;

class PerpustakaanManager {
    public DatabaseManager dbManager;
    
    public PerpustakaanManager() {
        dbManager = new DatabaseManager();
    }
    
    public boolean tambahBuku(String judul, String penulis, int tahunTerbit, String isbn, double harga, int kuantitas) {
        System.out.println("Menambahkan buku...");
        boolean success = dbManager.tambahBuku(judul, penulis, tahunTerbit, isbn, harga, kuantitas);
        
        if (success) {
            System.out.println("Buku berhasil ditambahkan!");
            System.out.println("Judul: " + judul);
            System.out.println("Penulis: " + penulis);
            System.out.println("Tahun Terbit: " + tahunTerbit);
            System.out.println("ISBN: " + isbn);
            System.out.println("Harga: Rp" + String.format("%.2f", harga));
            System.out.println("Kuantitas: " + kuantitas);
        } else {
            System.out.println("Gagal menambahkan buku. Periksa kembali data yang dimasukkan.");
        }
        
        return success;
    }
    
    public void lihatSemuaBuku() {
        System.out.println("\n=== DAFTAR SEMUA BUKU ===");
        List<Buku> daftarBuku = dbManager.getAllBuku();
        
        if (daftarBuku.isEmpty()) {
            System.out.println("Tidak ada buku dalam database.");
        } else {
            System.out.println("Total buku: " + daftarBuku.size());
            System.out.println("-".repeat(100));
            
            for (Buku buku : daftarBuku) {
                System.out.println(buku);
            }
            
            System.out.println("-".repeat(100));
        }
    }
    
    public void cariBukuById(int id) {
        System.out.println("\n=== MENCARI BUKU BERDASARKAN ID ===");
        Buku buku = dbManager.getBukuById(id);
        
        if (buku != null) {
            System.out.println("Buku ditemukan:");
            System.out.println(buku);
        } else {
            System.out.println("Buku dengan ID " + id + " tidak ditemukan.");
        }
    }
    
    public void cariBukuByIsbn(String isbn) {
        System.out.println("\n=== MENCARI BUKU BERDASARKAN ISBN ===");
        Buku buku = dbManager.getBukuByIsbn(isbn);
        
        if (buku != null) {
            System.out.println("Buku ditemukan:");
            System.out.println(buku);
        } else {
            System.out.println("Buku dengan ISBN " + isbn + " tidak ditemukan.");
        }
    }
    
    public void cariBukuByJudul(String judul) {
        System.out.println("\n=== MENCARI BUKU BERDASARKAN JUDUL ===");
        List<Buku> hasil = dbManager.cariBukuByJudul(judul);
        
        if (!hasil.isEmpty()) {
            System.out.println("Ditemukan " + hasil.size() + " buku:");
            System.out.println("-".repeat(100));
            
            for (Buku buku : hasil) {
                System.out.println(buku);
            }
            
            System.out.println("-".repeat(100));
        } else {
            System.out.println("Tidak ada buku yang ditemukan dengan judul yang mengandung: " + judul);
        }
    }
    
    public void cariBukuByPenulis(String penulis) {
        System.out.println("\n=== MENCARI BUKU BERDASARKAN PENULIS ===");
        List<Buku> hasil = dbManager.cariBukuByPenulis(penulis);
        
        if (!hasil.isEmpty()) {
            System.out.println("Ditemukan " + hasil.size() + " buku:");
            System.out.println("-".repeat(100));
            
            for (Buku buku : hasil) {
                System.out.println(buku);
            }
            
            System.out.println("-".repeat(100));
        } else {
            System.out.println("Tidak ada buku yang ditemukan dengan penulis yang mengandung: " + penulis);
        }
    }
    
    public boolean perbaruiBukuById(int id, String judul, String penulis, int tahunTerbit, double harga, int kuantitas) {
        System.out.println("\n=== MEMPERBARUI BUKU (ID: " + id + ") ===");
        
        // Check if book exists
        Buku existingBook = dbManager.getBukuById(id);
        if (existingBook == null) {
            System.out.println("Buku dengan ID " + id + " tidak ditemukan.");
            return false;
        }
        
        // Show current book info
        System.out.println("Data buku saat ini:");
        System.out.println(existingBook);
        
        // Update the book
        boolean success = dbManager.updateBuku(id, judul, penulis, tahunTerbit, harga, kuantitas);
        
        if (success) {
            System.out.println("Buku berhasil diperbarui!");
            
            // Show updated book info
            Buku updatedBook = dbManager.getBukuById(id);
            System.out.println("Data buku setelah diperbarui:");
            System.out.println(updatedBook);
        } else {
            System.out.println("Gagal memperbarui buku.");
        }
        
        return success;
    }
    
    public boolean updateHargaDanKuantitas(int id, double harga, int kuantitas) {
        System.out.println("\n=== UPDATE HARGA DAN KUANTITAS (ID: " + id + ") ===");
        
        // Check if book exists
        Buku existingBook = dbManager.getBukuById(id);
        if (existingBook == null) {
            System.out.println("Buku dengan ID " + id + " tidak ditemukan.");
            return false;
        }
        
        // Show current book info
        System.out.println("Data buku saat ini:");
        System.out.println(existingBook);
        
        // Update price and quantity
        boolean success = dbManager.updateHargaDanKuantitas(id, harga, kuantitas);
        
        if (success) {
            System.out.println("Harga dan kuantitas berhasil diperbarui!");
            
            // Show updated book info
            Buku updatedBook = dbManager.getBukuById(id);
            System.out.println("Data buku setelah diperbarui:");
            System.out.println(updatedBook);
        } else {
            System.out.println("Gagal memperbarui harga dan kuantitas.");
        }
        
        return success;
    }
    
    public boolean hapusBukuById(int id) {
        System.out.println("\n=== MENGHAPUS BUKU (ID: " + id + ") ===");
        
        // Check if book exists and show details
        Buku existingBook = dbManager.getBukuById(id);
        if (existingBook == null) {
            System.out.println("Buku dengan ID " + id + " tidak ditemukan.");
            return false;
        }
        
        System.out.println("Menghapus buku:");
        System.out.println(existingBook);
        
        // Delete the book
        boolean success = dbManager.deleteBuku(id);
        
        if (success) {
            System.out.println("Buku berhasil dihapus!");
        } else {
            System.out.println("Gagal menghapus buku.");
        }
        
        return success;
    }
    
    public boolean hapusBukuByIsbn(String isbn) {
        System.out.println("\n=== MENGHAPUS BUKU (ISBN: " + isbn + ") ===");
        
        // Check if book exists and show details
        Buku existingBook = dbManager.getBukuByIsbn(isbn);
        if (existingBook == null) {
            System.out.println("Buku dengan ISBN " + isbn + " tidak ditemukan.");
            return false;
        }
        
        System.out.println("Menghapus buku:");
        System.out.println(existingBook);
        
        // Delete the book
        boolean success = dbManager.deleteBukuByIsbn(isbn);
        
        if (success) {
            System.out.println("Buku berhasil dihapus!");
        } else {
            System.out.println("Gagal menghapus buku.");
        }
        
        return success;
    }
    
    public Buku getBuku(String identifier, boolean isId) {
        if (isId) {
            try {
                int id = Integer.parseInt(identifier);
                return dbManager.getBukuById(id);
            } catch (NumberFormatException e) {
                System.out.println("Format ID tidak valid: " + identifier);
                return null;
            }
        } else {
            return dbManager.getBukuByIsbn(identifier);
        }
    }
    
    public void tampilkanStatistik() {
        System.out.println("\n=== STATISTIK PERPUSTAKAAN ===");
        List<Buku> daftarBuku = dbManager.getAllBuku();
        
        if (daftarBuku.isEmpty()) {
            System.out.println("Tidak ada buku dalam database.");
            return;
        }
        
        int totalBuku = daftarBuku.size();
        int totalStok = 0;
        double totalNilai = 0.0;
        
        for (Buku buku : daftarBuku) {
            totalStok += buku.getKuantitas();
            totalNilai += buku.getHarga() * buku.getKuantitas();
        }
        
        System.out.println("Total judul buku: " + totalBuku);
        System.out.println("Total stok buku: " + totalStok);
        System.out.println("Total nilai inventaris: Rp" + String.format("%.2f", totalNilai));
        System.out.println("Rata-rata harga per buku: Rp" + String.format("%.2f", totalNilai / totalStok));
    }
    
    public void closeDatabase() {
        System.out.println("Menutup koneksi database...");
        dbManager.closeConnection();
    }
}