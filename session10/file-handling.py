import os
import sys

class FileReader:
    """
    Kelas untuk membaca berbagai jenis file teks dengan penanganan exception yang tepat
    """
    
    def __init__(self):
        self.supported_extensions = ['.txt', '.csv']
        self.content = None
        self.filepath = None
    
    def read_file(self, filepath):
        """
        Membaca file dan menangani berbagai exception yang mungkin terjadi
        """
        self.filepath = filepath
        self.content = None
        
        # Periksa apakah file ada
        if not os.path.exists(filepath):
            raise FileNotFoundError(f"File '{filepath}' tidak ditemukan.")
        
        # Periksa ekstensi file
        _, ext = os.path.splitext(filepath.lower())
        if ext not in self.supported_extensions:
            raise ValueError(f"Format file '{ext}' tidak didukung. Format yang didukung: {', '.join(self.supported_extensions)}")
        
        try:
            # Coba membuka dan membaca file
            with open(filepath, 'r', encoding='utf-8') as file:
                self.content = file.read()
            return self.content
        except PermissionError:
            raise PermissionError(f"Tidak memiliki izin untuk membaca file '{filepath}'")
        except UnicodeDecodeError:
            raise UnicodeDecodeError("utf-8", b"", 0, 1, f"File '{filepath}' tidak dapat didekode sebagai teks. File mungkin biner atau menggunakan encoding yang tidak didukung.")
        except Exception as e:
            raise Exception(f"Terjadi kesalahan saat membaca file '{filepath}': {str(e)}")
    
    def validate_txt_format(self, expected_format):
        """
        Memvalidasi format file teks berdasarkan kriteria yang ditentukan
        expected_format: fungsi yang menerima konten file dan mengembalikan True jika valid
        """
        if self.content is None:
            raise ValueError("Tidak ada konten file yang dimuat. Panggil read_file terlebih dahulu.")
        
        valid = expected_format(self.content)
        if not valid:
            raise ValueError(f"Format data dalam file '{self.filepath}' tidak sesuai dengan yang diharapkan.")
        
        return True
    
    def read_csv_as_list(self, delimiter=','):
        """
        Membaca file CSV dan mengembalikan dalam bentuk list of lists
        """
        if self.content is None:
            raise ValueError("Tidak ada konten file yang dimuat. Panggil read_file terlebih dahulu.")
        
        lines = self.content.strip().split('\n')
        data = []
        
        try:
            for i, line in enumerate(lines, 1):
                if not line.strip():  # Lewati baris kosong
                    continue
                    
                row = line.split(delimiter)
                data.append(row)
            
            return data
        except Exception as e:
            raise ValueError(f"Gagal memproses baris {i} dari file CSV: {str(e)}")


def menu():
    """Menampilkan menu program"""
    print("\n===== PROGRAM PEMBACA FILE =====")
    print("1. Baca File Teks (.txt)")
    print("2. Baca File CSV (.csv)")
    print("3. Validasi Format File")
    print("0. Keluar")
    
    try:
        choice = int(input("Pilih menu (0-3): "))
        if choice < 0 or choice > 3:
            raise ValueError()
        return choice
    except ValueError:
        print("Error: Masukkan angka 0-3")
        return -1


def main():
    reader = FileReader()
    
    while True:
        choice = menu()
        
        if choice == 0:
            print("Terima kasih telah menggunakan program!")
            break
            
        elif choice == 1:  # Baca file teks
            try:
                filepath = input("Masukkan path file teks (.txt): ")
                content = reader.read_file(filepath)
                print("\n===== ISI FILE =====")
                print(content[:500])  # Tampilkan 500 karakter pertama
                if len(content) > 500:
                    print("...(terpotong)...")
                print(f"\nBerhasil membaca file '{filepath}' ({len(content)} karakter)")
            except Exception as e:
                print(f"Error: {str(e)}")
        
        elif choice == 2:  # Baca file CSV
            try:
                filepath = input("Masukkan path file CSV (.csv): ")
                reader.read_file(filepath)
                
                # Tanyakan tentang delimiter
                delimiter = input("Masukkan karakter delimiter (default ','): ") or ','
                
                data = reader.read_csv_as_list(delimiter)
                
                # Tampilkan data dalam format tabel sederhana
                print("\n===== ISI FILE CSV =====")
                max_rows_to_show = 10
                
                for i, row in enumerate(data[:max_rows_to_show]):
                    print(f"Baris {i+1}: {row}")
                
                if len(data) > max_rows_to_show:
                    print(f"...(dan {len(data) - max_rows_to_show} baris lainnya)")
                
                print(f"\nBerhasil membaca file CSV '{filepath}' ({len(data)} baris)")
            except Exception as e:
                print(f"Error: {str(e)}")
        
        elif choice == 3:  # Validasi format file
            try:
                filepath = input("Masukkan path file untuk divalidasi: ")
                reader.read_file(filepath)
                
                print("\nPilih jenis validasi:")
                print("1. File tidak kosong")
                print("2. Berisi kata kunci tertentu")
                print("3. Validasi jumlah baris")
                
                validation_type = int(input("Pilih jenis validasi (1-3): "))
                
                if validation_type == 1:
                    # Validasi file tidak kosong
                    def not_empty(content):
                        return len(content.strip()) > 0
                    
                    reader.validate_txt_format(not_empty)
                    print("Validasi berhasil: File tidak kosong")
                
                elif validation_type == 2:
                    # Validasi berisi kata kunci
                    keyword = input("Masukkan kata kunci yang harus ada: ")
                    
                    def contains_keyword(content):
                        return keyword.lower() in content.lower()
                    
                    if reader.validate_txt_format(contains_keyword):
                        print(f"Validasi berhasil: File berisi kata kunci '{keyword}'")
                
                elif validation_type == 3:
                    # Validasi jumlah baris
                    try:
                        min_lines = int(input("Masukkan jumlah baris minimum: "))
                        
                        def has_min_lines(content):
                            lines = content.split('\n')
                            return len(lines) >= min_lines
                        
                        if reader.validate_txt_format(has_min_lines):
                            print(f"Validasi berhasil: File memiliki minimal {min_lines} baris")
                    except ValueError:
                        print("Error: Masukkan angka untuk jumlah baris")
                
                else:
                    print("Jenis validasi tidak valid")
            
            except Exception as e:
                print(f"Error: {str(e)}")


if __name__ == "__main__":
    try:
        main()
    except KeyboardInterrupt:
        print("\nProgram dihentikan oleh pengguna")
    except Exception as e:
        print(f"Terjadi kesalahan yang tidak terduga: {str(e)}")
        print("Program dihentikan")