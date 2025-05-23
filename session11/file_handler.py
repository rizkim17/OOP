import os
import json
from tugas import Tugas

def simpan_tugas(daftar_tugas, nama_file):
    """Menyimpan daftar_tugas ke dalam file dengan nama_file"""
    try:
        # Mengubah daftar tugas menjadi format yang bisa disimpan
        data_tugas = [tugas.to_dict() for tugas in daftar_tugas]
        
        with open(nama_file, 'w', encoding='utf-8') as file:
            json.dump(data_tugas, file, indent=2, ensure_ascii=False)
        
        print(f"💾 Daftar tugas berhasil disimpan ke file '{nama_file}'!")
        return True
    
    except Exception as e:
        print(f"❌ Gagal menyimpan file: {e}")
        return False

def muat_tugas(nama_file):
    """Memuat daftar tugas dari file dengan nama_file dan mengembalikannya"""
    try:
        if not os.path.exists(nama_file):
            print(f"📂 File '{nama_file}' tidak ditemukan. Memulai dengan daftar tugas kosong.")
            return []
        
        with open(nama_file, 'r', encoding='utf-8') as file:
            data_tugas = json.load(file)
        
        # Mengubah data dictionary menjadi objek Tugas
        daftar_tugas = [Tugas.from_dict(data) for data in data_tugas]
        
        print(f"📂 Berhasil memuat {len(daftar_tugas)} tugas dari file '{nama_file}'!")
        return daftar_tugas
    
    except json.JSONDecodeError:
        print(f"❌ File '{nama_file}' rusak atau format tidak valid. Memulai dengan daftar tugas kosong.")
        return []
    
    except Exception as e:
        print(f"❌ Gagal memuat file: {e}. Memulai dengan daftar tugas kosong.")
        return []