def tampilkan_menu():
    """Menampilkan menu pilihan untuk pengguna"""
    print("\n" + "="*40)
    print("    APLIKASI PENCATAT TUGAS")
    print("="*40)
    print("1. 📝 Tambah tugas baru")
    print("2. 👀 Lihat daftar tugas")
    print("3. ✅ Tandai tugas selesai")
    print("4. 🗑️  Hapus tugas")
    print("5. 📊 Lihat statistik")
    print("6. 💾 Simpan ke file")
    print("7. 🚪 Keluar")
    print("="*40)

def tampilkan_header(judul):
    """Menampilkan header untuk setiap operasi"""
    print(f"\n{judul}")
    print("-" * len(judul))

def tampilkan_statistik(stats):
    """Menampilkan statistik tugas dengan format yang menarik"""
    print("\n" + "="*40)
    print("         STATISTIK TUGAS")
    print("="*40)
    print(f"📊 Total tugas        : {stats['total']}")
    print(f"✅ Tugas selesai      : {stats['selesai']}")
    print(f"⏳ Tugas belum selesai: {stats['belum_selesai']}")
    print(f"📈 Persentase selesai : {stats['persentase_selesai']:.1f}%")
    print("="*40)
    
    # Progress bar sederhana
    if stats['total'] > 0:
        progress = int(stats['persentase_selesai'] / 10)
        bar = "█" * progress + "░" * (10 - progress)
        print(f"Progress: [{bar}] {stats['persentase_selesai']:.1f}%")
        print("="*40)

def dapatkan_input_angka(prompt, min_val=None, max_val=None):
    """Helper function untuk mendapatkan input angka yang valid"""
    while True:
        try:
            nilai = int(input(prompt))
            if min_val is not None and nilai < min_val:
                print(f"❌ Nilai harus minimal {min_val}")
                continue
            if max_val is not None and nilai > max_val:
                print(f"❌ Nilai harus maksimal {max_val}")
                continue
            return nilai
        except ValueError:
            print("❌ Masukkan angka yang valid!")

def konfirmasi_simpan():
    """Meminta konfirmasi untuk menyimpan perubahan"""
    konfirmasi = input("Apakah Anda ingin menyimpan perubahan sebelum keluar? (y/n): ").lower()
    return konfirmasi in ['y', 'yes', 'ya']

def tampilkan_pesan_selamat_datang():
    """Menampilkan pesan selamat datang"""
    print("🎉 Selamat datang di Aplikasi Pencatat Tugas!")

def tampilkan_pesan_perpisahan():
    """Menampilkan pesan perpisahan"""
    print("👋 Terima kasih telah menggunakan Aplikasi Pencatat Tugas!")
    print("📝 Semoga produktivitas Anda meningkat!")