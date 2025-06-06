package main;

import dao.BukuDAO;
import model.Buku;
import util.InputUtil;

import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        BukuDAO dao = new BukuDAO();
        while (true) {
            System.out.println("\n--- Menu Perpustakaan ---");
            System.out.println("1. Tambah Buku");
            System.out.println("2. Tampilkan Semua Buku");
            System.out.println("3. Cari Buku");
            System.out.println("4. Update Buku");
            System.out.println("5. Hapus Buku");
            System.out.println("6. Keluar");
            int pilihan = InputUtil.inputInt("Pilih menu: ");

            switch (pilihan) {
                case 1 -> {
                    String judul = InputUtil.inputString("Judul: ");
                    String penulis = InputUtil.inputString("Penulis: ");
                    int tahun = InputUtil.inputInt("Tahun Terbit: ");
                    String isbn = InputUtil.inputString("ISBN: ");
                    dao.tambahBuku(new Buku(judul, penulis, tahun, isbn));
                }
                case 2 -> {
                    List<Buku> daftar = dao.getSemuaBuku();
                    for (Buku b : daftar) {
                        System.out.printf("%d | %s | %s | %d | %s\n",
                            b.getId(), b.getJudul(), b.getPenulis(), b.getTahunTerbit(), b.getIsbn());
                    }
                }
                case 3 -> {
                    String keyword = InputUtil.inputString("Kata kunci judul/penulis: ");
                    List<Buku> hasil = dao.cariBuku(keyword);
                    for (Buku b : hasil) {
                        System.out.printf("%d | %s | %s | %d | %s\n",
                            b.getId(), b.getJudul(), b.getPenulis(), b.getTahunTerbit(), b.getIsbn());
                    }
                }
                case 4 -> {
                    int id = InputUtil.inputInt("ID buku yang ingin diubah: ");
                    String judul = InputUtil.inputString("Judul baru: ");
                    String penulis = InputUtil.inputString("Penulis baru: ");
                    int tahun = InputUtil.inputInt("Tahun Terbit baru: ");
                    String isbn = InputUtil.inputString("ISBN baru: ");
                    dao.updateBuku(id, new Buku(judul, penulis, tahun, isbn));
                }
                case 5 -> {
                    int id = InputUtil.inputInt("ID buku yang ingin dihapus: ");
                    dao.hapusBuku(id);
                }
                case 6 -> {
                    System.out.println("Terima kasih!");
                    return;
                }
                default -> System.out.println("Pilihan tidak valid!");
            }
        }
    }
}