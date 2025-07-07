class Buku {
    private int id;
    private String judul;
    private String penulis;
    private int tahunTerbit;
    private String isbn;
    private double harga;
    private int kuantitas;
    
    // Constructor
    public Buku(int id, String judul, String penulis, int tahunTerbit, String isbn, double harga, int kuantitas) {
        this.id = id;
        this.judul = judul;
        this.penulis = penulis;
        this.tahunTerbit = tahunTerbit;
        this.isbn = isbn;
        this.harga = harga;
        this.kuantitas = kuantitas;
    }
    
    // Getter methods
    public int getId() {
        return id;
    }
    
    public String getJudul() {
        return judul;
    }
    
    public String getPenulis() {
        return penulis;
    }
    
    public int getTahunTerbit() {
        return tahunTerbit;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public double getHarga() {
        return harga;
    }
    
    public int getKuantitas() {
        return kuantitas;
    }
    
    // Setter methods (kecuali id dan isbn yang tidak boleh diubah)
    public void setJudul(String judul) {
        this.judul = judul;
    }
    
    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }
    
    public void setTahunTerbit(int tahunTerbit) {
        this.tahunTerbit = tahunTerbit;
    }
    
    public void setHarga(double harga) {
        this.harga = harga;
    }
    
    public void setKuantitas(int kuantitas) {
        this.kuantitas = kuantitas;
    }
    
    @Override
    public String toString() {
        return String.format("ID: %d | Judul: %s | Penulis: %s | Tahun: %d | ISBN: %s | Harga: Rp%.2f | Stok: %d", 
                           id, judul, penulis, tahunTerbit, isbn, harga, kuantitas);
    }
}