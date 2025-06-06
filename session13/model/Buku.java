package model;

public class Buku {
    private int id;
    private String judul;
    private String penulis;
    private int tahunTerbit;
    private String isbn;

    // Constructor, Getter, Setter
    public Buku(String judul, String penulis, int tahunTerbit, String isbn) {
        this.judul = judul;
        this.penulis = penulis;
        this.tahunTerbit = tahunTerbit;
        this.isbn = isbn;
    }

    public Buku(int id, String judul, String penulis, int tahunTerbit, String isbn) {
        this(judul, penulis, tahunTerbit, isbn);
        this.id = id;
    }

    public int getId() { return id; }
    public String getJudul() { return judul; }
    public String getPenulis() { return penulis; }
    public int getTahunTerbit() { return tahunTerbit; }
    public String getIsbn() { return isbn; }

    public void setJudul(String judul) { this.judul = judul; }
    public void setPenulis(String penulis) { this.penulis = penulis; }
    public void setTahunTerbit(int tahunTerbit) { this.tahunTerbit = tahunTerbit; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
}
