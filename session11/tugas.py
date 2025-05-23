class Tugas:
    """Kelas untuk merepresentasikan sebuah tugas"""
    def __init__(self, deskripsi, selesai=False):
        self.deskripsi = deskripsi
        self.selesai = selesai
    
    def __str__(self):
        status = "[X]" if self.selesai else "[ ]"
        return f"{status} {self.deskripsi}"
    
    def to_dict(self):
        """Mengubah objek tugas menjadi dictionary untuk penyimpanan"""
        return {
            'deskripsi': self.deskripsi,
            'selesai': self.selesai
        }
    
    @classmethod
    def from_dict(cls, data):
        """Membuat objek tugas dari dictionary"""
        return cls(data['deskripsi'], data['selesai'])