import java.util.*;

class PerpustakaanManager {
    private ArrayList<Buku> daftarBuku;
    private int nextId;
    
    public PerpustakaanManager() {
        this.daftarBuku = new ArrayList<>();
        this.nextId = 1;
    }
    
    // Helper method untuk mengecek keunikan ISBN
    private boolean isIsbnUnik(String isbn) {
        for (Buku buku : daftarBuku) {
            if (buku.getIsbn().equals(isbn)) {
                return false;
            }
        }
        return true;
    }
    
    // Helper method untuk mencari buku berdasarkan ID
    private Buku getBukuById(int id) {
        for (Buku buku : daftarBuku) {
            if (buku.getId() == id) {
                return buku;
            }
        }
        return null;
    }
    
    // Helper method untuk mencari buku berdasarkan ISBN
    private Buku getBukuByIsbn(String isbn) {
        for (Buku buku : daftarBuku) {
            if (buku.getIsbn().equals(isbn)) {
                return buku;
            }
        }
        return null;
    }
    
    // 1. Tambah Buku (Create)
    public boolean tambahBuku(String judul, String penulis, int tahunTerbit, String isbn) {
        if (!isIsbnUnik(isbn)) {
            System.out.println("Error: ISBN sudah ada! Silakan gunakan ISBN yang berbeda.");
            return false;
        }
        
        Buku bukuBaru = new Buku(nextId++, judul, penulis, tahunTerbit, isbn);
        daftarBuku.add(bukuBaru);
        System.out.println("Buku berhasil ditambahkan!");
        System.out.println("Detail buku: " + bukuBaru);
        return true;
    }
    
    // 2. Lihat Semua Buku (Read All)
    public void lihatSemuaBuku() {
        if (daftarBuku.isEmpty()) {
            System.out.println("Tidak ada buku di perpustakaan.");
            return;
        }
        
        System.out.println("\n=== DAFTAR SEMUA BUKU ===");
        System.out.println(String.format("%-5s %-30s %-20s %-6s %-15s", 
                          "ID", "JUDUL", "PENULIS", "TAHUN", "ISBN"));
        System.out.println("-".repeat(80));
        
        for (Buku buku : daftarBuku) {
            System.out.println(String.format("%-5d %-30s %-20s %-6d %-15s",
                              buku.getId(),
                              buku.getJudul().length() > 30 ? buku.getJudul().substring(0, 27) + "..." : buku.getJudul(),
                              buku.getPenulis().length() > 20 ? buku.getPenulis().substring(0, 17) + "..." : buku.getPenulis(),
                              buku.getTahunTerbit(),
                              buku.getIsbn()));
        }
        System.out.println("-".repeat(80));
        System.out.println("Total buku: " + daftarBuku.size());
    }
    
    // 3. Cari Buku berdasarkan ID atau ISBN
    public void cariBukuById(int id) {
        Buku buku = getBukuById(id);
        if (buku != null) {
            System.out.println("Buku ditemukan:");
            System.out.println(buku);
        } else {
            System.out.println("Buku dengan ID " + id + " tidak ditemukan.");
        }
    }
    
    public void cariBukuByIsbn(String isbn) {
        Buku buku = getBukuByIsbn(isbn);
        if (buku != null) {
            System.out.println("Buku ditemukan:");
            System.out.println(buku);
        } else {
            System.out.println("Buku dengan ISBN " + isbn + " tidak ditemukan.");
        }
    }
    
    // 4. Perbarui Informasi Buku (Update)
    public boolean perbaruiBukuById(int id, String judulBaru, String penulisBaru, int tahunTerbitBaru) {
        Buku buku = getBukuById(id);
        if (buku != null) {
            System.out.println("Data buku saat ini:");
            System.out.println(buku);
            
            buku.setJudul(judulBaru);
            buku.setPenulis(penulisBaru);
            buku.setTahunTerbit(tahunTerbitBaru);
            
            System.out.println("Buku berhasil diperbarui!");
            System.out.println("Data buku setelah diperbarui:");
            System.out.println(buku);
            return true;
        } else {
            System.out.println("Buku dengan ID " + id + " tidak ditemukan.");
            return false;
        }
    }
    
    public boolean perbaruiBukuByIsbn(String isbn, String judulBaru, String penulisBaru, int tahunTerbitBaru) {
        Buku buku = getBukuByIsbn(isbn);
        if (buku != null) {
            System.out.println("Data buku saat ini:");
            System.out.println(buku);
            
            buku.setJudul(judulBaru);
            buku.setPenulis(penulisBaru);
            buku.setTahunTerbit(tahunTerbitBaru);
            
            System.out.println("Buku berhasil diperbarui!");
            System.out.println("Data buku setelah diperbarui:");
            System.out.println(buku);
            return true;
        } else {
            System.out.println("Buku dengan ISBN " + isbn + " tidak ditemukan.");
            return false;
        }
    }
    
    // 5. Hapus Buku (Delete)
    public boolean hapusBukuById(int id) {
        Buku buku = getBukuById(id);
        if (buku != null) {
            daftarBuku.remove(buku);
            System.out.println("Buku berhasil dihapus!");
            System.out.println("Detail buku yang dihapus: " + buku);
            return true;
        } else {
            System.out.println("Buku dengan ID " + id + " tidak ditemukan.");
            return false;
        }
    }
    
    public boolean hapusBukuByIsbn(String isbn) {
        Buku buku = getBukuByIsbn(isbn);
        if (buku != null) {
            daftarBuku.remove(buku);
            System.out.println("Buku berhasil dihapus!");
            System.out.println("Detail buku yang dihapus: " + buku);
            return true;
        } else {
            System.out.println("Buku dengan ISBN " + isbn + " tidak ditemukan.");
            return false;
        }
    }
    
    // Method untuk mendapatkan buku berdasarkan ID atau ISBN (untuk konfirmasi hapus)
    public Buku getBuku(String identifier, boolean isId) {
        if (isId) {
            try {
                int id = Integer.parseInt(identifier);
                return getBukuById(id);
            } catch (NumberFormatException e) {
                return null;
            }
        } else {
            return getBukuByIsbn(identifier);
        }
    }
}