class Buku:
    def __init__(self, judul, penulis, isbn):
        self.__judul = judul
        self.__penulis = penulis
        self.__isbn = isbn
        self.__tersedia = True
        self.__peminjam = None

    # Getter methods
    def get_judul(self):
        return self.__judul
    
    def get_penulis(self):
        return self.__penulis
    
    def get_isbn(self):
        return self.__isbn
    
    def is_tersedia(self):
        return self.__tersedia
    
    def get_peminjam(self):
        return self.__peminjam
    
    # Setter methods
    def set_judul(self, judul):
        self.__judul = judul
    
    def set_penulis(self, penulis):
        self.__penulis = penulis
    
    def set_isbn(self, isbn):
        self.__isbn = isbn
    
    def set_tersedia(self, tersedia):
        self.__tersedia = tersedia
    
    def set_peminjam(self, peminjam):
        self.__peminjam = peminjam
    
    def info(self):
        status = "Tersedia" if self.__tersedia else f"Dipinjam oleh {self.__peminjam}"
        return f"Judul: {self.__judul}, Penulis: {self.__penulis}, ISBN: {self.__isbn}, Status: {status}"


class Anggota:
    def __init__(self, nama, nomor_anggota, alamat):
        self.__nama = nama
        self.__nomor_anggota = nomor_anggota
        self.__alamat = alamat
        self.__buku_dipinjam = []
    
    # Getter methods
    def get_nama(self):
        return self.__nama
    
    def get_nomor_anggota(self):
        return self.__nomor_anggota
    
    def get_alamat(self):
        return self.__alamat
    
    def get_buku_dipinjam(self):
        return self.__buku_dipinjam
    
    # Setter methods
    def set_nama(self, nama):
        self.__nama = nama
    
    def set_nomor_anggota(self, nomor_anggota):
        self.__nomor_anggota = nomor_anggota
    
    def set_alamat(self, alamat):
        self.__alamat = alamat
    
    def pinjam_buku(self, buku):
        if buku not in self.__buku_dipinjam:
            self.__buku_dipinjam.append(buku)
    
    def kembalikan_buku(self, buku):
        if buku in self.__buku_dipinjam:
            self.__buku_dipinjam.remove(buku)
    
    def info(self):
        info_anggota = f"Nama: {self.__nama}, Nomor Anggota: {self.__nomor_anggota}, Alamat: {self.__alamat}"
        
        if len(self.__buku_dipinjam) > 0:
            info_anggota += "\nBuku yang dipinjam:"
            for buku in self.__buku_dipinjam:
                info_anggota += f"\n- {buku.get_judul()} oleh {buku.get_penulis()}"
        else:
            info_anggota += "\nTidak ada buku yang dipinjam"
        
        return info_anggota


class Perpustakaan:
    def __init__(self, nama):
        self.__nama = nama
        self.__daftar_buku = []
        self.__daftar_anggota = []
    
    def get_nama(self):
        return self.__nama
    
    def tambah_buku(self, buku):
        self.__daftar_buku.append(buku)
        return f"Buku '{buku.get_judul()}' berhasil ditambahkan"
    
    def tambah_anggota(self, anggota):
        self.__daftar_anggota.append(anggota)
        return f"Anggota '{anggota.get_nama()}' berhasil didaftarkan"
    
    def cari_buku_by_judul(self, judul):
        hasil = []
        for buku in self.__daftar_buku:
            if judul.lower() in buku.get_judul().lower():
                hasil.append(buku)
        return hasil
    
    def cari_buku_by_penulis(self, penulis):
        hasil = []
        for buku in self.__daftar_buku:
            if penulis.lower() in buku.get_penulis().lower():
                hasil.append(buku)
        return hasil
    
    def cari_anggota_by_nomor(self, nomor_anggota):
        for anggota in self.__daftar_anggota:
            if anggota.get_nomor_anggota() == nomor_anggota:
                return anggota
        return None
    
    def daftar_buku_tersedia(self):
        buku_tersedia = []
        for buku in self.__daftar_buku:
            if buku.is_tersedia():
                buku_tersedia.append(buku)
        return buku_tersedia
    
    def pinjam_buku(self, isbn, nomor_anggota):
        anggota = self.cari_anggota_by_nomor(nomor_anggota)
        if not anggota:
            return "Anggota tidak ditemukan"
        
        for buku in self.__daftar_buku:
            if buku.get_isbn() == isbn:
                if buku.is_tersedia():
                    buku.set_tersedia(False)
                    buku.set_peminjam(anggota.get_nama())
                    anggota.pinjam_buku(buku)
                    return f"Buku '{buku.get_judul()}' berhasil dipinjam oleh {anggota.get_nama()}"
                else:
                    return f"Buku '{buku.get_judul()}' sedang tidak tersedia"
        
        return "Buku dengan ISBN tersebut tidak ditemukan"
    
    def kembalikan_buku(self, isbn, nomor_anggota):
        anggota = self.cari_anggota_by_nomor(nomor_anggota)
        if not anggota:
            return "Anggota tidak ditemukan"
        
        for buku in self.__daftar_buku:
            if buku.get_isbn() == isbn:
                if not buku.is_tersedia() and buku in anggota.get_buku_dipinjam():
                    buku.set_tersedia(True)
                    buku.set_peminjam(None)
                    anggota.kembalikan_buku(buku)
                    return f"Buku '{buku.get_judul()}' berhasil dikembalikan oleh {anggota.get_nama()}"
                elif buku.is_tersedia():
                    return f"Buku '{buku.get_judul()}' tidak sedang dipinjam"
                else:
                    return f"Buku '{buku.get_judul()}' sedang dipinjam oleh anggota lain"
        
        return "Buku dengan ISBN tersebut tidak ditemukan"
    
    def tampilkan_semua_buku(self):
        return self.__daftar_buku
    
    def tampilkan_semua_anggota(self):
        return self.__daftar_anggota


# Program utama untuk pengujian
def main():
    # Inisialisasi perpustakaan
    perpus = Perpustakaan("Perpustakaan Kota")
    print(f"Selamat datang di {perpus.get_nama()}")
    print("-" * 50)
    
    # Menambahkan beberapa buku
    buku1 = Buku("Laskar Pelangi", "Andrea Hirata", "978-602-8519-20-8")
    buku2 = Buku("Bumi Manusia", "Pramoedya Ananta Toer", "978-979-418-209-8")
    buku3 = Buku("Pulang", "Leila S. Chudori", "978-602-291-045-0")
    buku4 = Buku("Filosofi Teras", "Henry Manampiring", "978-602-385-524-0")
    buku5 = Buku("Bumi", "Tere Liye", "978-602-03-0112-8")
    
    perpus.tambah_buku(buku1)
    perpus.tambah_buku(buku2)
    perpus.tambah_buku(buku3)
    perpus.tambah_buku(buku4)
    perpus.tambah_buku(buku5)
    
    # Menambahkan anggota
    anggota1 = Anggota("Budi Santoso", "A001", "Jl. Merdeka No. 10")
    anggota2 = Anggota("Ani Wijaya", "A002", "Jl. Sudirman No. 25")
    anggota3 = Anggota("Rini Puteri", "A003", "Jl. Gatot Subroto No. 15")
    
    perpus.tambah_anggota(anggota1)
    perpus.tambah_anggota(anggota2)
    perpus.tambah_anggota(anggota3)
    
    # Menampilkan daftar buku tersedia
    print("Daftar Buku Tersedia:")
    for buku in perpus.tampilkan_semua_buku():
        print(buku.info())
    print("-" * 50)
    
    # Meminjam buku
    print(perpus.pinjam_buku("978-602-8519-20-8", "A001"))  # Budi meminjam Laskar Pelangi
    print(perpus.pinjam_buku("978-979-418-209-8", "A002"))  # Ani meminjam Bumi Manusia
    print(perpus.pinjam_buku("978-602-291-045-0", "A001"))  # Budi meminjam Pulang
    print("-" * 50)
    
    # Menampilkan informasi buku setelah dipinjam
    print("Informasi Buku Setelah Dipinjam:")
    for buku in perpus.tampilkan_semua_buku():
        print(buku.info())
    print("-" * 50)
    
    # Menampilkan informasi anggota
    print("Informasi Anggota:")
    for anggota in perpus.tampilkan_semua_anggota():
        print(anggota.info())
        print()
    print("-" * 50)
    
    # Mencari buku berdasarkan judul
    print("Hasil Pencarian Buku dengan Judul 'Bumi':")
    hasil_pencarian = perpus.cari_buku_by_judul("Bumi")
    for buku in hasil_pencarian:
        print(buku.info())
    print("-" * 50)
    
    # Mengembalikan buku
    print(perpus.kembalikan_buku("978-602-8519-20-8", "A001"))  # Budi mengembalikan Laskar Pelangi
    print("-" * 50)
    
    # Menampilkan informasi setelah pengembalian
    print("Informasi Buku Setelah Pengembalian:")
    for buku in perpus.tampilkan_semua_buku():
        print(buku.info())
    print("-" * 50)
    
    print("Informasi Anggota Setelah Pengembalian:")
    for anggota in perpus.tampilkan_semua_anggota():
        print(anggota.info())
        print()
    print("-" * 50)
    
    # Mencoba mengakses variabel private dari luar class
    try:
        print(buku1.__judul)  # Ini akan menyebabkan error karena __judul adalah private
    except AttributeError as e:
        print(f"Error: {e}")
        print("Tidak bisa mengakses variabel private secara langsung dari luar class!")
    print("-" * 50)


if __name__ == "__main__":
    main()