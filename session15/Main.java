import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static PerpustakaanManager perpustakaan = new PerpustakaanManager();
    
    public static void main(String[] args) {
        System.out.println("=== SISTEM MANAJEMEN INVENTARIS PERPUSTAKAAN ===");
        System.out.println("Selamat datang di sistem inventaris buku dengan database!");
        
        // Add some sample data
        perpustakaan.tambahBuku("Harry Potter dan Batu Bertuah", "J.K. Rowling", 1997, "978-0747532699", 125000.0, 15);
        perpustakaan.tambahBuku("Laskar Pelangi", "Andrea Hirata", 2005, "978-979-3062-79-2", 85000.0, 20);
        perpustakaan.tambahBuku("Bumi Manusia", "Pramoedya Ananta Toer", 1980, "978-979-3062-15-0", 95000.0, 10);
        
        boolean running = true;
        
        while (running) {
            tampilkanMenu();
            int pilihan = getMenuChoice();
            
            switch (pilihan) {
                case 1:
                    tambahBuku();
                    break;
                case 2:
                    perpustakaan.lihatSemuaBuku();
                    break;
                case 3:
                    cariBuku();
                    break;
                case 4:
                    perbaruiBuku();
                    break;
                case 5:
                    updateHargaDanStok();
                    break;
                case 6:
                    hapusBuku();
                    break;
                case 7:
                    System.out.println("Menutup koneksi database...");
                    perpustakaan.closeDatabase();
                    System.out.println("Terima kasih telah menggunakan Sistem Manajemen Inventaris Perpustakaan!");
                    System.out.println("Sampai jumpa!");
                    running = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid! Silakan pilih 1-7.");
            }
            
            if (running) {
                System.out.println("\nTekan Enter untuk melanjutkan...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    private static void tampilkanMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                    MENU UTAMA");
        System.out.println("=".repeat(60));
        System.out.println("1. Tambah Buku Baru");
        System.out.println("2. Lihat Semua Buku");
        System.out.println("3. Cari Buku (ID/ISBN/Judul/Penulis)");
        System.out.println("4. Perbarui Informasi Buku Lengkap");
        System.out.println("5. Update Harga dan Stok");
        System.out.println("6. Hapus Buku");
        System.out.println("7. Keluar");
        System.out.println("=".repeat(60));
        System.out.print("Pilih menu (1-7): ");
    }
    
    private static int getMenuChoice() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            return choice;
        } catch (InputMismatchException e) {
            scanner.nextLine(); // Clear wrong input
            return -1;
        }
    }
    
    private static void tambahBuku() {
        System.out.println("\n=== TAMBAH BUKU BARU ===");
        
        try {
            System.out.print("Masukkan judul buku: ");
            String judul = scanner.nextLine().trim();
            
            if (judul.isEmpty()) {
                System.out.println("Judul tidak boleh kosong!");
                return;
            }
            
            System.out.print("Masukkan nama penulis: ");
            String penulis = scanner.nextLine().trim();
            
            if (penulis.isEmpty()) {
                System.out.println("Nama penulis tidak boleh kosong!");
                return;
            }
            
            System.out.print("Masukkan tahun terbit: ");
            int tahunTerbit = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            // Validate publication year
            int tahunSekarang = Calendar.getInstance().get(Calendar.YEAR);
            if (tahunTerbit < 1000 || tahunTerbit > tahunSekarang) {
                System.out.println("Tahun terbit tidak valid! Masukkan tahun antara 1000 dan " + tahunSekarang);
                return;
            }
            
            System.out.print("Masukkan ISBN: ");
            String isbn = scanner.nextLine().trim();
            
            if (isbn.isEmpty()) {
                System.out.println("ISBN tidak boleh kosong!");
                return;
            }
            
            System.out.print("Masukkan harga (Rp): ");
            double harga = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            
            if (harga < 0) {
                System.out.println("Harga tidak boleh negatif!");
                return;
            }
            
            System.out.print("Masukkan kuantitas stok: ");
            int kuantitas = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            if (kuantitas < 0) {
                System.out.println("Kuantitas tidak boleh negatif!");
                return;
            }
            
            perpustakaan.tambahBuku(judul, penulis, tahunTerbit, isbn, harga, kuantitas);
            
        } catch (InputMismatchException e) {
            System.out.println("Input tidak valid! Pastikan format input sesuai.");
            scanner.nextLine(); // Clear wrong input
        }
    }
    
    private static void cariBuku() {
        System.out.println("\n=== CARI BUKU ===");
        System.out.println("1. Cari berdasarkan ID");
        System.out.println("2. Cari berdasarkan ISBN");
        System.out.println("3. Cari berdasarkan Judul");
        System.out.println("4. Cari berdasarkan Penulis");
        System.out.print("Pilih metode pencarian (1-4): ");
        
        try {
            int pilihan = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan ID buku: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    perpustakaan.cariBukuById(id);
                    break;
                case 2:
                    System.out.print("Masukkan ISBN buku: ");
                    String isbn = scanner.nextLine().trim();
                    if (!isbn.isEmpty()) {
                        perpustakaan.cariBukuByIsbn(isbn);
                    } else {
                        System.out.println("ISBN tidak boleh kosong!");
                    }
                    break;
                case 3:
                    System.out.print("Masukkan judul buku (atau kata kunci): ");
                    String judul = scanner.nextLine().trim();
                    if (!judul.isEmpty()) {
                        perpustakaan.cariBukuByJudul(judul);
                    } else {
                        System.out.println("Judul tidak boleh kosong!");
                    }
                    break;
                case 4:
                    System.out.print("Masukkan nama penulis (atau kata kunci): ");
                    String penulis = scanner.nextLine().trim();
                    if (!penulis.isEmpty()) {
                        perpustakaan.cariBukuByPenulis(penulis);
                    } else {
                        System.out.println("Nama penulis tidak boleh kosong!");
                    }
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
            
        } catch (InputMismatchException e) {
            System.out.println("Input tidak valid!");
            scanner.nextLine();
        }
    }
    
    private static void perbaruiBuku() {
        System.out.println("\n=== PERBARUI INFORMASI BUKU LENGKAP ===");
        System.out.println("1. Cari berdasarkan ID");
        System.out.println("2. Cari berdasarkan ISBN");
        System.out.print("Pilih metode pencarian (1-2): ");
        
        try {
            int pilihan = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            int targetId = 0;
            String targetIsbn = "";
            boolean useId = false;
            
            // Get target book identifier
            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan ID buku yang akan diperbarui: ");
                    targetId = scanner.nextInt();
                    scanner.nextLine();
                    useId = true;
                    break;
                case 2:
                    System.out.print("Masukkan ISBN buku yang akan diperbarui: ");
                    targetIsbn = scanner.nextLine().trim();
                    if (targetIsbn.isEmpty()) {
                        System.out.println("ISBN tidak boleh kosong!");
                        return;
                    }
                    useId = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
                    return;
            }
            
            // Get new information
            System.out.print("Masukkan judul baru: ");
            String judulBaru = scanner.nextLine().trim();
            
            if (judulBaru.isEmpty()) {
                System.out.println("Judul tidak boleh kosong!");
                return;
            }
            
            System.out.print("Masukkan penulis baru: ");
            String penulisBaru = scanner.nextLine().trim();
            
            if (penulisBaru.isEmpty()) {
                System.out.println("Nama penulis tidak boleh kosong!");
                return;
            }
            
            System.out.print("Masukkan tahun terbit baru: ");
            int tahunTerbitBaru = scanner.nextInt();
            scanner.nextLine();
            
            // Validate publication year
            int tahunSekarang = Calendar.getInstance().get(Calendar.YEAR);
            if (tahunTerbitBaru < 1000 || tahunTerbitBaru > tahunSekarang) {
                System.out.println("Tahun terbit tidak valid! Masukkan tahun antara 1000 dan " + tahunSekarang);
                return;
            }
            
            System.out.print("Masukkan harga baru (Rp): ");
            double hargaBaru = scanner.nextDouble();
            scanner.nextLine();
            
            if (hargaBaru < 0) {
                System.out.println("Harga tidak boleh negatif!");
                return;
            }
            
            System.out.print("Masukkan kuantitas stok baru: ");
            int kuantitasBaru = scanner.nextInt();
            scanner.nextLine();
            
            if (kuantitasBaru < 0) {
                System.out.println("Kuantitas tidak boleh negatif!");
                return;
            }
            
            // Update the book
            if (useId) {
                perpustakaan.perbaruiBukuById(targetId, judulBaru, penulisBaru, tahunTerbitBaru, hargaBaru, kuantitasBaru);
            } else {
                // For ISBN-based update, we need to get the ID first
                Buku buku = perpustakaan.getBuku(targetIsbn, false);
                if (buku != null) {
                    perpustakaan.perbaruiBukuById(buku.getId(), judulBaru, penulisBaru, tahunTerbitBaru, hargaBaru, kuantitasBaru);
                } else {
                    System.out.println("Buku dengan ISBN " + targetIsbn + " tidak ditemukan.");
                }
            }
            
        } catch (InputMismatchException e) {
            System.out.println("Input tidak valid!");
            scanner.nextLine();
        }
    }
    
    private static void updateHargaDanStok() {
        System.out.println("\n=== UPDATE HARGA DAN STOK ===");
        System.out.print("Masukkan ID buku yang akan diupdate: ");
        
        try {
            int id = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            System.out.print("Masukkan harga baru (Rp): ");
            double hargaBaru = scanner.nextDouble();
            scanner.nextLine();
            
            if (hargaBaru < 0) {
                System.out.println("Harga tidak boleh negatif!");
                return;
            }
            
            System.out.print("Masukkan kuantitas stok baru: ");
            int kuantitasBaru = scanner.nextInt();
            scanner.nextLine();
            
            if (kuantitasBaru < 0) {
                System.out.println("Kuantitas tidak boleh negatif!");
                return;
            }
            
            perpustakaan.updateHargaDanKuantitas(id, hargaBaru, kuantitasBaru);
            
        } catch (InputMismatchException e) {
            System.out.println("Input tidak valid!");
            scanner.nextLine();
        }
    }
    
    private static void hapusBuku() {
        System.out.println("\n=== HAPUS BUKU ===");
        System.out.println("1. Hapus berdasarkan ID");
        System.out.println("2. Hapus berdasarkan ISBN");
        System.out.print("Pilih metode penghapusan (1-2): ");
        
        try {
            int pilihan = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            String identifier = "";
            boolean isId = false;
            
            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan ID buku yang akan dihapus: ");
                    identifier = String.valueOf(scanner.nextInt());
                    scanner.nextLine();
                    isId = true;
                    break;
                case 2:
                    System.out.print("Masukkan ISBN buku yang akan dihapus: ");
                    identifier = scanner.nextLine().trim();
                    if (identifier.isEmpty()) {
                        System.out.println("ISBN tidak boleh kosong!");
                        return;
                    }
                    isId = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
                    return;
            }
            
            // Check if book exists
            Buku buku = perpustakaan.getBuku(identifier, isId);
            if (buku == null) {
                if (isId) {
                    System.out.println("Buku dengan ID " + identifier + " tidak ditemukan.");
                } else {
                    System.out.println("Buku dengan ISBN " + identifier + " tidak ditemukan.");
                }
                return;
            }
            
            // Display book details that will be deleted
            System.out.println("\nBuku yang akan dihapus:");
            System.out.println(buku);
            
            // Ask for confirmation
            System.out.print("Apakah Anda yakin ingin menghapus buku ini? (Y/N): ");
            String konfirmasi = scanner.nextLine().trim().toLowerCase();
            
            if (konfirmasi.equals("y") || konfirmasi.equals("yes")) {
                if (isId) {
                    perpustakaan.hapusBukuById(Integer.parseInt(identifier));
                } else {
                    perpustakaan.hapusBukuByIsbn(identifier);
                }
            } else {
                System.out.println("Penghapusan dibatalkan.");
            }
            
        } catch (InputMismatchException e) {
            System.out.println("Input tidak valid!");
            scanner.nextLine();
        }
    }
}