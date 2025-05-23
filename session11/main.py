import sys
import os

# Menambahkan path modul jika diperlukan
sys.path.append(os.path.dirname(os.path.abspath(__file__)))

from task_manager import tambah_tugas, lihat_tugas, tandai_selesai, hapus_tugas, dapatkan_statistik
from file_handler import simpan_tugas, muat_tugas
from ui import (tampilkan_menu, tampilkan_header, tampilkan_statistik, 
                dapatkan_input_angka, konfirmasi_simpan, 
                tampilkan_pesan_selamat_datang, tampilkan_pesan_perpisahan)
from config import NAMA_FILE_DEFAULT

class AplikasiTugas:
    """Kelas utama untuk menjalankan aplikasi"""
    
    def __init__(self):
        self.nama_file = NAMA_FILE_DEFAULT
        self.daftar_tugas = []
        self.running = True
    
    def inisialisasi(self):
        """Inisialisasi aplikasi"""
        tampilkan_pesan_selamat_datang()
        self.daftar_tugas = muat_tugas(self.nama_file)
    
    def handle_tambah_tugas(self):
        """Menangani penambahan tugas baru"""
        tampilkan_header("📝 TAMBAH TUGAS BARU")
        deskripsi = input("Masukkan deskripsi tugas: ").strip()
        if deskripsi:
            tambah_tugas(self.daftar_tugas, deskripsi)
        else:
            print("❌ Deskripsi tugas tidak boleh kosong!")
    
    def handle_lihat_tugas(self):
        """Menangani tampilan daftar tugas"""
        lihat_tugas(self.daftar_tugas)
    
    def handle_tandai_selesai(self):
        """Menangani penandaan tugas selesai"""
        if not self.daftar_tugas:
            print("❌ Tidak ada tugas yang bisa ditandai selesai!")
            return
        
        tampilkan_header("✅ TANDAI TUGAS SELESAI")
        lihat_tugas(self.daftar_tugas)
        nomor = dapatkan_input_angka(
            f"Masukkan nomor tugas yang selesai (1-{len(self.daftar_tugas)}): ", 
            1, len(self.daftar_tugas)
        )
        tandai_selesai(self.daftar_tugas, nomor)
    
    def handle_hapus_tugas(self):
        """Menangani penghapusan tugas"""
        if not self.daftar_tugas:
            print("❌ Tidak ada tugas yang bisa dihapus!")
            return
        
        tampilkan_header("🗑️  HAPUS TUGAS")
        lihat_tugas(self.daftar_tugas)
        nomor = dapatkan_input_angka(
            f"Masukkan nomor tugas yang akan dihapus (1-{len(self.daftar_tugas)}): ", 
            1, len(self.daftar_tugas)
        )
        hapus_tugas(self.daftar_tugas, nomor)
    
    def handle_lihat_statistik(self):
        """Menangani tampilan statistik"""
        stats = dapatkan_statistik(self.daftar_tugas)
        tampilkan_statistik(stats)
    
    def handle_simpan(self):
        """Menangani penyimpanan ke file"""
        tampilkan_header("💾 SIMPAN KE FILE")
        simpan_tugas(self.daftar_tugas, self.nama_file)
    
    def handle_keluar(self):
        """Menangani keluar dari aplikasi"""
        tampilkan_header("🚪 KELUAR APLIKASI")
        if konfirmasi_simpan():
            simpan_tugas(self.daftar_tugas, self.nama_file)
        
        tampilkan_pesan_perpisahan()
        self.running = False
    
    def proses_pilihan(self, pilihan):
        """Memproses pilihan menu pengguna"""
        menu_handlers = {
            1: self.handle_tambah_tugas,
            2: self.handle_lihat_tugas,
            3: self.handle_tandai_selesai,
            4: self.handle_hapus_tugas,
            5: self.handle_lihat_statistik,
            6: self.handle_simpan,
            7: self.handle_keluar
        }
        
        handler = menu_handlers.get(pilihan)
        if handler:
            handler()
        else:
            print("❌ Pilihan tidak valid!")
    
    def jalankan(self):
        """Menjalankan loop utama aplikasi"""
        self.inisialisasi()
        
        while self.running:
            try:
                tampilkan_menu()
                pilihan = dapatkan_input_angka("Pilih menu (1-7): ", 1, 7)
                self.proses_pilihan(pilihan)
                
            except KeyboardInterrupt:
                print("\n\n⚠️  Program dihentikan oleh pengguna.")
                if konfirmasi_simpan():
                    simpan_tugas(self.daftar_tugas, self.nama_file)
                break
                
            except Exception as e:
                print(f"❌ Terjadi kesalahan: {e}")
                print("🔄 Silakan coba lagi.")

def main():
    """Fungsi utama untuk menjalankan aplikasi"""
    app = AplikasiTugas()
    app.jalankan()

if __name__ == "__main__":
    main()