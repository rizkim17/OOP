import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static PerpustakaanManager perpustakaan = new PerpustakaanManager();
    
    public static void main(String[] args) {
        System.out.println("=== SISTEM MANAJEMEN PERPUSTAKAAN SEDERHANA ===");
        System.out.println("Selamat datang!");
        
        // Menambahkan beberapa data contoh
        perpustakaan.tambahBuku("Harry Potter dan Batu Bertuah", "J.K. Rowling", 1997, "978-0747532699");
        perpustakaan.tambahBuku("Laskar Pelangi", "Andrea Hirata", 2005, "978-979-3062-79-2");
        perpustakaan.tambahBuku("Bumi Manusia", "Pramoedya Ananta Toer", 1980, "978-979-3062-15-0");
        
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
                    hapusBuku();
                    break;
                case 6:
                    System.out.println("Terima kasih telah menggunakan Sistem Manajemen Perpustakaan!");
                    System.out.println("Sampai jumpa!");
                    running = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid! Silakan pilih 1-6.");
            }
            
            if (running) {
                System.out.println("\nTekan Enter untuk melanjutkan...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    private static void tampilkanMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("            MENU UTAMA");
        System.out.println("=".repeat(50));
        System.out.println("1. Tambah Buku");
        System.out.println("2. Lihat Semua Buku");
        System.out.println("3. Cari Buku (berdasarkan ID atau ISBN)");
        System.out.println("4. Perbarui Informasi Buku");
        System.out.println("5. Hapus Buku");
        System.out.println("6. Keluar");
        System.out.println("=".repeat(50));
        System.out.print("Pilih menu (1-6): ");
    }
    
    private static int getMenuChoice() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // Konsumsi newline
            return choice;
        } catch (InputMismatchException e) {
            scanner.nextLine(); // Bersihkan input yang salah
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
            scanner.nextLine(); // Konsumsi newline
            
            // Validasi tahun terbit
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
            
            perpustakaan.tambahBuku(judul, penulis, tahunTerbit, isbn);
            
        } catch (InputMismatchException e) {
            System.out.println("Input tidak valid! Tahun terbit harus berupa angka.");
            scanner.nextLine(); // Bersihkan input yang salah
        }
    }
    
    private static void cariBuku() {
        System.out.println("\n=== CARI BUKU ===");
        System.out.println("1. Cari berdasarkan ID");
        System.out.println("2. Cari berdasarkan ISBN");
        System.out.print("Pilih metode pencarian (1-2): ");
        
        try {
            int pilihan = scanner.nextInt();
            scanner.nextLine(); // Konsumsi newline
            
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
                default:
                    System.out.println("Pilihan tidak valid!");
            }
            
        } catch (InputMismatchException e) {
            System.out.println("Input tidak valid!");
            scanner.nextLine();
        }
    }
    
    private static void perbaruiBuku() {
        System.out.println("\n=== PERBARUI BUKU ===");
        System.out.println("1. Cari berdasarkan ID");
        System.out.println("2. Cari berdasarkan ISBN");
        System.out.print("Pilih metode pencarian (1-2): ");
        
        try {
            int pilihan = scanner.nextInt();
            scanner.nextLine(); // Konsumsi newline
            
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
            
            // Validasi tahun terbit
            int tahunSekarang = Calendar.getInstance().get(Calendar.YEAR);
            if (tahunTerbitBaru < 1000 || tahunTerbitBaru > tahunSekarang) {
                System.out.println("Tahun terbit tidak valid! Masukkan tahun antara 1000 dan " + tahunSekarang);
                return;
            }
            
            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan ID buku yang akan diperbarui: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    perpustakaan.perbaruiBukuById(id, judulBaru, penulisBaru, tahunTerbitBaru);
                    break;
                case 2:
                    System.out.print("Masukkan ISBN buku yang akan diperbarui: ");
                    String isbn = scanner.nextLine().trim();
                    if (!isbn.isEmpty()) {
                        perpustakaan.perbaruiBukuByIsbn(isbn, judulBaru, penulisBaru, tahunTerbitBaru);
                    } else {
                        System.out.println("ISBN tidak boleh kosong!");
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
    
    private static void hapusBuku() {
        System.out.println("\n=== HAPUS BUKU ===");
        System.out.println("1. Hapus berdasarkan ID");
        System.out.println("2. Hapus berdasarkan ISBN");
        System.out.print("Pilih metode penghapusan (1-2): ");
        
        try {
            int pilihan = scanner.nextInt();
            scanner.nextLine(); // Konsumsi newline
            
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
            
            // Cek apakah buku ada
            Buku buku = perpustakaan.getBuku(identifier, isId);
            if (buku == null) {
                if (isId) {
                    System.out.println("Buku dengan ID " + identifier + " tidak ditemukan.");
                } else {
                    System.out.println("Buku dengan ISBN " + identifier + " tidak ditemukan.");
                }
                return;
            }
            
            // Tampilkan detail buku yang akan dihapus
            System.out.println("\nBuku yang akan dihapus:");
            System.out.println(buku);
            
            // Minta konfirmasi
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