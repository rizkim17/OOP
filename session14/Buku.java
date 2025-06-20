class Buku {
    private int id;
    private String judul;
    private String penulis;
    private int tahunTerbit;
    private String isbn;
    
    // Constructor
    public Buku(int id, String judul, String penulis, int tahunTerbit, String isbn) {
        this.id = id;
        this.judul = judul;
        this.penulis = penulis;
        this.tahunTerbit = tahunTerbit;
        this.isbn = isbn;
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
    
    @Override
    public String toString() {
        return String.format("ID: %d | Judul: %s | Penulis: %s | Tahun: %d | ISBN: %s", 
                           id, judul, penulis, tahunTerbit, isbn);
    }
}