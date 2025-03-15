class Buku:
    def __init__(self, id, judul, penulis, stok=1):
        self.id = id
        self.judul = judul
        self.penulis = penulis
        self.stok = stok
        self.tersedia = stok
    
    def __str__(self):
        return f"ID: {self.id}, Judul: {self.judul}, Penulis: {self.penulis}, Stok: {self.stok}, Tersedia: {self.tersedia}"
    
    def apakah_tersedia(self):
        return self.tersedia > 0
    
    def pinjam(self):
        if self.apakah_tersedia():
            self.tersedia -= 1
            return True
        return False
    
    def kembalikan(self):
        if self.tersedia < self.stok:
            self.tersedia += 1
            return True
        return False


class Anggota:
    def __init__(self, id, nama, email):
        self.id = id
        self.nama = nama
        self.email = email
        self.buku_dipinjam = []
    
    def __str__(self):
        return f"ID: {self.id}, Nama: {self.nama}, Email: {self.email}, Buku Dipinjam: {len(self.buku_dipinjam)}"
    
    def pinjam_buku(self, buku):
        if buku.pinjam():
            self.buku_dipinjam.append(buku)
            return True
        return False
    
    def kembalikan_buku(self, buku):
        if buku in self.buku_dipinjam and buku.kembalikan():
            self.buku_dipinjam.remove(buku)
            return True
        return False
    
    def daftar_buku_dipinjam(self):
        return self.buku_dipinjam


class Transaksi:
    def __init__(self, id, anggota, buku, tanggal_pinjam, tanggal_kembali=None):
        self.id = id
        self.anggota = anggota
        self.buku = buku
        self.tanggal_pinjam = tanggal_pinjam
        self.tanggal_kembali = tanggal_kembali
    
    def __str__(self):
        status = "Dikembalikan" if self.tanggal_kembali else "Dipinjam"
        return f"ID: {self.id}, Anggota: {self.anggota.nama}, Buku: {self.buku.judul}, Tanggal Pinjam: {self.tanggal_pinjam}, Status: {status}"


class Perpustakaan:
    def __init__(self, nama):
        self.nama = nama
        self.daftar_buku = {}
        self.daftar_anggota = {}
        self.daftar_transaksi = []
        self.penghitung_id_transaksi = 1
    
    def tambah_buku(self, buku):
        self.daftar_buku[buku.id] = buku
    
    def tambah_anggota(self, anggota):
        self.daftar_anggota[anggota.id] = anggota
    
    def pinjam_buku(self, id_anggota, id_buku, tanggal_pinjam):
        if id_anggota not in self.daftar_anggota or id_buku not in self.daftar_buku:
            return False
        
        anggota = self.daftar_anggota[id_anggota]
        buku = self.daftar_buku[id_buku]
        
        if anggota.pinjam_buku(buku):
            transaksi = Transaksi(self.penghitung_id_transaksi, anggota, buku, tanggal_pinjam)
            self.daftar_transaksi.append(transaksi)
            self.penghitung_id_transaksi += 1
            return True
        return False
    
    def kembalikan_buku(self, id_anggota, id_buku, tanggal_kembali):
        if id_anggota not in self.daftar_anggota or id_buku not in self.daftar_buku:
            return False
        
        anggota = self.daftar_anggota[id_anggota]
        buku = self.daftar_buku[id_buku]
        
        if anggota.kembalikan_buku(buku):
            # Mencari transaksi aktif dan memperbarui tanggal pengembalian
            for transaksi in self.daftar_transaksi:
                if transaksi.anggota.id == id_anggota and transaksi.buku.id == id_buku and not transaksi.tanggal_kembali:
                    transaksi.tanggal_kembali = tanggal_kembali
                    return True
        return False
    
    def ambil_daftar_buku(self):
        return list(self.daftar_buku.values())
    
    def ambil_daftar_anggota(self):
        return list(self.daftar_anggota.values())
    
    def ambil_daftar_transaksi(self):
        return self.daftar_transaksi
    
    def ambil_transaksi_aktif(self):
        return [t for t in self.daftar_transaksi if not t.tanggal_kembali]
    
    def cari_buku(self, kata_kunci):
        return [buku for buku in self.daftar_buku.values() if kata_kunci.lower() in buku.judul.lower() or kata_kunci.lower() in buku.penulis.lower()]
    
    def cari_anggota(self, kata_kunci):
        return [anggota for anggota in self.daftar_anggota.values() if kata_kunci.lower() in anggota.nama.lower() or kata_kunci.lower() in anggota.email.lower()]


# Contoh penggunaan
if __name__ == "__main__":
    perpustakaan = Perpustakaan("Perpustakaan Kota")
    
    # Menambahkan buku
    buku1 = Buku(1, "Harry Potter dan Batu Bertuah", "J.K. Rowling", 3)
    buku2 = Buku(2, "Laskar Pelangi", "Andrea Hirata", 2)
    buku3 = Buku(3, "Bumi Manusia", "Pramoedya Ananta Toer", 1)
    
    perpustakaan.tambah_buku(buku1)
    perpustakaan.tambah_buku(buku2)
    perpustakaan.tambah_buku(buku3)
    
    # Menambahkan anggota
    anggota1 = Anggota(101, "Budi Santoso", "budi@example.com")
    anggota2 = Anggota(102, "Dewi Lestari", "dewi@example.com")
    
    perpustakaan.tambah_anggota(anggota1)
    perpustakaan.tambah_anggota(anggota2)
    
    # Transaksi peminjaman
    perpustakaan.pinjam_buku(101, 1, "2025-03-15")
    perpustakaan.pinjam_buku(102, 2, "2025-03-15")
    perpustakaan.pinjam_buku(101, 3, "2025-03-15")
    
    # Menampilkan data
    print("Daftar Buku:")
    for buku in perpustakaan.ambil_daftar_buku():
        print(buku)
    
    print("\nDaftar Anggota:")
    for anggota in perpustakaan.ambil_daftar_anggota():
        print(anggota)
        print("  Buku dipinjam:")
        for buku in anggota.daftar_buku_dipinjam():
            print(f"  - {buku.judul}")
    
    print("\nDaftar Transaksi Aktif:")
    for transaksi in perpustakaan.ambil_transaksi_aktif():
        print(transaksi)
    
    # Pengembalian buku
    perpustakaan.kembalikan_buku(101, 1, "2025-03-18")
    
    print("\nDaftar Transaksi Setelah Pengembalian:")
    for transaksi in perpustakaan.ambil_daftar_transaksi():
        print(transaksi)
    
    # Pencarian
    print("\nHasil Pencarian Buku dengan kata kunci 'Harry':")
    for buku in perpustakaan.cari_buku("Harry"):
        print(buku)