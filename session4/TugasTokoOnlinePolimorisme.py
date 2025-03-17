class Produk:
    def __init__(self, nama, harga, stok):
        self.nama = nama
        self.harga = harga
        self.stok = stok
    
    def tampilkanInfo(self):
        print(f"Nama: {self.nama}")
        print(f"Harga: Rp {self.harga}")
        print(f"Stok: {self.stok}")


class Buku(Produk):
    def __init__(self, nama, harga, stok, penulis, penerbit):
        super().__init__(nama, harga, stok)
        self.penulis = penulis
        self.penerbit = penerbit
    
    def tampilkanInfo(self):
        super().tampilkanInfo()
        print(f"Penulis: {self.penulis}")
        print(f"Penerbit: {self.penerbit}")


class Elektronik(Produk):
    def __init__(self, nama, harga, stok, merek, garansi):
        super().__init__(nama, harga, stok)
        self.merek = merek
        self.garansi = garansi
    
    def tampilkanInfo(self):
        super().tampilkanInfo()
        print(f"Merek: {self.merek}")
        print(f"Garansi: {self.garansi} bulan")


class Pakaian(Produk):
    def __init__(self, nama, harga, stok, ukuran, warna):
        super().__init__(nama, harga, stok)
        self.ukuran = ukuran
        self.warna = warna
    
    def tampilkanInfo(self):
        super().tampilkanInfo()
        print(f"Ukuran: {self.ukuran}")
        print(f"Warna: {self.warna}")


class Inventaris:
    def __init__(self):
        self.daftar_produk = []
    
    def tambahProduk(self, produk):
        self.daftar_produk.append(produk)
        print(f"Produk '{produk.nama}' berhasil ditambahkan ke inventaris.")
    
    def hapusProduk(self, nama_produk):
        for i, produk in enumerate(self.daftar_produk):
            if produk.nama == nama_produk:
                del self.daftar_produk[i]
                print(f"Produk '{nama_produk}' berhasil dihapus dari inventaris.")
                return
        print(f"Produk '{nama_produk}' tidak ditemukan dalam inventaris.")
    
    def cariProduk(self, nama_produk):
        for produk in self.daftar_produk:
            if produk.nama == nama_produk:
                print(f"Produk '{nama_produk}' ditemukan:")
                produk.tampilkanInfo()
                return
        print(f"Produk '{nama_produk}' tidak ditemukan dalam inventaris.")
    
    def tampilkanSemuaProduk(self):
        if not self.daftar_produk:
            print("Inventaris kosong.")
            return
        
        print("\nDaftar Semua Produk dalam Inventaris:")
        for i, produk in enumerate(self.daftar_produk, 1):
            print(f"\nProduk #{i}")
            produk.tampilkanInfo()
            print("-" * 30)


def main():
    inventaris = Inventaris()
    
    while True:
        print("\n==== SISTEM MANAJEMEN INVENTARIS TOKO ONLINE ====")
        print("1. Tambah Produk")
        print("2. Hapus Produk")
        print("3. Cari Produk")
        print("4. Tampilkan Semua Produk")
        print("5. Keluar")
        
        pilihan = input("\nPilih menu (1-5): ")
        
        if pilihan == "1":
            print("\n-- TAMBAH PRODUK --")
            print("Jenis Produk:")
            print("1. Buku")
            print("2. Elektronik")
            print("3. Pakaian")
            
            jenis = input("Pilih jenis produk (1-3): ")
            
            nama = input("Nama produk: ")
            harga = float(input("Harga produk (Rp): "))
            stok = int(input("Jumlah stok: "))
            
            if jenis == "1":
                penulis = input("Penulis: ")
                penerbit = input("Penerbit: ")
                produk = Buku(nama, harga, stok, penulis, penerbit)
                inventaris.tambahProduk(produk)
            
            elif jenis == "2":
                merek = input("Merek: ")
                garansi = int(input("Lama garansi (bulan): "))
                produk = Elektronik(nama, harga, stok, merek, garansi)
                inventaris.tambahProduk(produk)
            
            elif jenis == "3":
                ukuran = input("Ukuran (S/M/L/XL): ")
                warna = input("Warna: ")
                produk = Pakaian(nama, harga, stok, ukuran, warna)
                inventaris.tambahProduk(produk)
            
            else:
                print("Pilihan tidak valid.")
        
        elif pilihan == "2":
            nama_produk = input("\nMasukkan nama produk yang akan dihapus: ")
            inventaris.hapusProduk(nama_produk)
        
        elif pilihan == "3":
            nama_produk = input("\nMasukkan nama produk yang akan dicari: ")
            inventaris.cariProduk(nama_produk)
        
        elif pilihan == "4":
            inventaris.tampilkanSemuaProduk()
        
        elif pilihan == "5":
            print("\nTerima kasih telah menggunakan Sistem Manajemen Inventaris Toko Online.")
            break
        
        else:
            print("\nPilihan tidak valid. Silakan pilih menu 1-5.")


if __name__ == "__main__":
    main()