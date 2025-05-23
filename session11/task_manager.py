from tugas import Tugas

def tambah_tugas(daftar_tugas, deskripsi):
    """Menambahkan tugas baru ke dalam daftar_tugas"""
    tugas_baru = Tugas(deskripsi)
    daftar_tugas.append(tugas_baru)
    print(f"✓ Tugas '{deskripsi}' berhasil ditambahkan!")
    return True

def lihat_tugas(daftar_tugas):
    """Menampilkan semua tugas dalam daftar_tugas dengan nomor urut dan status"""
    if not daftar_tugas:
        print("📝 Daftar tugas kosong!")
        return
    
    print("\n" + "="*50)
    print("           DAFTAR TUGAS")
    print("="*50)
    
    for i, tugas in enumerate(daftar_tugas, 1):
        print(f"{i:2d}. {tugas}")
    
    print("="*50)
    print(f"Total tugas: {len(daftar_tugas)}")
    selesai = sum(1 for tugas in daftar_tugas if tugas.selesai)
    belum_selesai = len(daftar_tugas) - selesai
    print(f"Selesai: {selesai} | Belum selesai: {belum_selesai}")
    print("="*50)

def tandai_selesai(daftar_tugas, nomor_tugas):
    """Mengubah status tugas menjadi selesai berdasarkan nomor_tugas"""
    if not daftar_tugas:
        print("❌ Daftar tugas kosong!")
        return False
    
    if nomor_tugas < 1 or nomor_tugas > len(daftar_tugas):
        print(f"❌ Nomor tugas tidak valid! Masukkan nomor antara 1-{len(daftar_tugas)}")
        return False
    
    tugas = daftar_tugas[nomor_tugas - 1]
    
    if tugas.selesai:
        print(f"⚠️  Tugas '{tugas.deskripsi}' sudah ditandai selesai sebelumnya!")
        return False
    
    tugas.selesai = True
    print(f"✅ Tugas '{tugas.deskripsi}' berhasil ditandai selesai!")
    return True

def hapus_tugas(daftar_tugas, nomor_tugas):
    """Menghapus tugas dari daftar_tugas berdasarkan nomor_tugas"""
    if not daftar_tugas:
        print("❌ Daftar tugas kosong!")
        return False
    
    if nomor_tugas < 1 or nomor_tugas > len(daftar_tugas):
        print(f"❌ Nomor tugas tidak valid! Masukkan nomor antara 1-{len(daftar_tugas)}")
        return False
    
    tugas_dihapus = daftar_tugas.pop(nomor_tugas - 1)
    print(f"🗑️  Tugas '{tugas_dihapus.deskripsi}' berhasil dihapus!")
    return True

def dapatkan_statistik(daftar_tugas):
    """Mengembalikan statistik tugas"""
    total = len(daftar_tugas)
    selesai = sum(1 for tugas in daftar_tugas if tugas.selesai)
    belum_selesai = total - selesai
    
    return {
        'total': total,
        'selesai': selesai,
        'belum_selesai': belum_selesai,
        'persentase_selesai': (selesai / total * 100) if total > 0 else 0
    }
