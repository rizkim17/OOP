
class Buku:
    def __init__(self, id, judul, penulis, tahun_terbit):
        self.id = id
        self.judul = judul
        self.penulis = penulis
        self.tahun_terbit = tahun_terbit
        self.dipinjam = False

    def __str__(self):
        status = "Dipinjam" if self.dipinjam else "Tersedia"
        return f"ID: {self.id}, Judul: {self.judul}, Penulis: {self.penulis}, Tahun: {self.tahun_terbit}, Status: {status}"


class Perpustakaan:
    def __init__(self):
        self.daftar_buku = []
    
    def tambah_buku(self, buku):
        self.daftar_buku.append(buku)
        print(f"Buku '{buku.judul}' berhasil ditambahkan.")
    
    def cari_buku(self, id_buku):
        for buku in self.daftar_buku:
            if buku.id == id_buku:
                return buku
        raise ValueError(f"Buku dengan ID {id_buku} tidak ditemukan")
    
    def pinjam_buku(self, id_buku):
        try:
            buku = self.cari_buku(id_buku)
            if buku.dipinjam:
                raise Exception(f"Buku '{buku.judul}' sudah dipinjam")
            buku.dipinjam = True
            print(f"Buku '{buku.judul}' berhasil dipinjam")
        except ValueError as e:
            print(f"Error: {e}")
        except Exception as e:
            print(f"Error: {e}")
    
    def kembalikan_buku(self, id_buku):
        try:
            buku = self.cari_buku(id_buku)
            if not buku.dipinjam:
                raise Exception(f"Buku '{buku.judul}' tidak sedang dipinjam")
            buku.dipinjam = False
            print(f"Buku '{buku.judul}' berhasil dikembalikan")
        except ValueError as e:
            print(f"Error: {e}")
        except Exception as e:
            print(f"Error: {e}")
    
    def tampilkan_semua_buku(self):
        if not self.daftar_buku:
            print("Tidak ada buku dalam perpustakaan")
            return
        
        print("\nDaftar Buku dalam Perpustakaan:")
        for buku in self.daftar_buku:
            print(buku)
    
    def simpan_ke_file(self, nama_file):
        try:
            with open(nama_file, 'w') as file:
                for buku in self.daftar_buku:
                    status = "1" if buku.dipinjam else "0"
                    file.write(f"{buku.id},{buku.judul},{buku.penulis},{buku.tahun_terbit},{status}\n")
            print(f"Data berhasil disimpan ke {nama_file}")
        except PermissionError:
            print(f"Error: Tidak memiliki izin untuk menulis ke file {nama_file}")
        except Exception as e:
            print(f"Error saat menyimpan data: {e}")
    
    def muat_dari_file(self, nama_file):
        try:
            self.daftar_buku = []  # Reset daftar buku
            with open(nama_file, 'r') as file:
                for baris in file:
                    try:
                        data = baris.strip().split(',')
                        if len(data) != 5:
                            raise ValueError(f"Format data tidak valid: {baris}")
                        
                        id_buku = data[0]
                        judul = data[1]
                        penulis = data[2]
                        
                        try:
                            tahun_terbit = int(data[3])
                        except ValueError:
                            print(f"Warning: Tahun terbit tidak valid untuk buku '{judul}', menggunakan 0")
                            tahun_terbit = 0
                        
                        buku = Buku(id_buku, judul, penulis, tahun_terbit)
                        buku.dipinjam = (data[4] == "1")
                        self.daftar_buku.append(buku)
                    except ValueError as e:
                        print(f"Error saat membaca baris: {e}")
                        continue
            
            print(f"Data berhasil dimuat dari {nama_file}")
            print(f"Jumlah buku dimuat: {len(self.daftar_buku)}")
        except FileNotFoundError:
            print(f"Error: File {nama_file} tidak ditemukan")
        except PermissionError:
            print(f"Error: Tidak memiliki izin untuk membaca file {nama_file}")
        except Exception as e:
            print(f"Error saat memuat data: {e}")


def menu():
    print("\n==== Sistem Perpustakaan ====")
    print("1. Tambah Buku")
    print("2. Tampilkan Semua Buku")
    print("3. Pinjam Buku")
    print("4. Kembalikan Buku")
    print("5. Simpan Data ke File")
    print("6. Muat Data dari File")
    print("0. Keluar")
    
    try:
        pilihan = int(input("Pilih menu (0-6): "))
        if pilihan < 0 or pilihan > 6:
            raise ValueError("Menu tidak valid")
        return pilihan
    except ValueError as e:
        print(f"Error: Masukan harus berupa angka 0-6")
        return -1


def main():
    perpustakaan = Perpustakaan()
    
    # Tambahkan beberapa buku contoh
    perpustakaan.tambah_buku(Buku("B001", "Python Programming", "John Smith", 2020))
    perpustakaan.tambah_buku(Buku("B002", "Java Fundamentals", "Jane Doe", 2019))
    perpustakaan.tambah_buku(Buku("B003", "Data Structures and Algorithms", "Robert Johnson", 2021))
    
    while True:
        pilihan = menu()
        
        if pilihan == 0:
            print("Terima kasih telah menggunakan Sistem Perpustakaan!")
            break
        elif pilihan == 1:
            try:
                id_buku = input("Masukkan ID Buku: ")
                judul = input("Masukkan Judul Buku: ")
                penulis = input("Masukkan Nama Penulis: ")
                
                try:
                    tahun_terbit = int(input("Masukkan Tahun Terbit: "))
                    if tahun_terbit < 0 or tahun_terbit > 2030:
                        raise ValueError("Tahun terbit tidak valid")
                except ValueError:
                    print("Error: Tahun terbit harus berupa angka yang valid")
                    continue
                
                buku_baru = Buku(id_buku, judul, penulis, tahun_terbit)
                perpustakaan.tambah_buku(buku_baru)
            except Exception as e:
                print(f"Error saat menambahkan buku: {e}")
        
        elif pilihan == 2:
            perpustakaan.tampilkan_semua_buku()
        
        elif pilihan == 3:
            try:
                id_buku = input("Masukkan ID Buku untuk dipinjam: ")
                perpustakaan.pinjam_buku(id_buku)
            except Exception as e:
                print(f"Error saat meminjam buku: {e}")
        
        elif pilihan == 4:
            try:
                id_buku = input("Masukkan ID Buku untuk dikembalikan: ")
                perpustakaan.kembalikan_buku(id_buku)
            except Exception as e:
                print(f"Error saat mengembalikan buku: {e}")
        
        elif pilihan == 5:
            try:
                nama_file = input("Masukkan nama file untuk menyimpan data: ")
                perpustakaan.simpan_ke_file(nama_file)
            except Exception as e:
                print(f"Error: {e}")
        
        elif pilihan == 6:
            try:
                nama_file = input("Masukkan nama file untuk memuat data: ")
                perpustakaan.muat_dari_file(nama_file)
            except Exception as e:
                print(f"Error: {e}")


if __name__ == "__main__":
    try:
        main()
    except KeyboardInterrupt:
        print("\nProgram dihentikan oleh pengguna")
    except Exception as e:
        print(f"Terjadi kesalahan yang tidak terduga: {e}")
        print("Program dihentikan")