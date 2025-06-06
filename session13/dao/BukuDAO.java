package dao;

import config.DatabaseConfig;
import model.Buku;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BukuDAO {
    public void tambahBuku(Buku buku) {
        String sql = "INSERT INTO buku (judul, penulis, tahunTerbit, isbn) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, buku.getJudul());
            ps.setString(2, buku.getPenulis());
            ps.setInt(3, buku.getTahunTerbit());
            ps.setString(4, buku.getIsbn());
            ps.executeUpdate();
            System.out.println("Buku berhasil ditambahkan!");
        } catch (SQLException e) {
            System.err.println("Gagal menambahkan buku: " + e.getMessage());
        }
    }

    public List<Buku> getSemuaBuku() {
        List<Buku> daftarBuku = new ArrayList<>();
        String sql = "SELECT * FROM buku";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Buku b = new Buku(
                    rs.getInt("id"),
                    rs.getString("judul"),
                    rs.getString("penulis"),
                    rs.getInt("tahunTerbit"),
                    rs.getString("isbn")
                );
                daftarBuku.add(b);
            }
        } catch (SQLException e) {
            System.err.println("Gagal mengambil data buku: " + e.getMessage());
        }
        return daftarBuku;
    }

    public List<Buku> cariBuku(String keyword) {
        List<Buku> hasil = new ArrayList<>();
        String sql = "SELECT * FROM buku WHERE judul LIKE ? OR penulis LIKE ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    hasil.add(new Buku(
                        rs.getInt("id"),
                        rs.getString("judul"),
                        rs.getString("penulis"),
                        rs.getInt("tahunTerbit"),
                        rs.getString("isbn")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal mencari buku: " + e.getMessage());
        }
        return hasil;
    }

    public void updateBuku(int id, Buku buku) {
        String sql = "UPDATE buku SET judul = ?, penulis = ?, tahunTerbit = ?, isbn = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, buku.getJudul());
            ps.setString(2, buku.getPenulis());
            ps.setInt(3, buku.getTahunTerbit());
            ps.setString(4, buku.getIsbn());
            ps.setInt(5, id);
            int updated = ps.executeUpdate();
            if (updated > 0) {
                System.out.println("Buku berhasil diperbarui!");
            } else {
                System.out.println("Buku tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.err.println("Gagal memperbarui buku: " + e.getMessage());
        }
    }

    public void hapusBuku(int id) {
        String sql = "DELETE FROM buku WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int deleted = ps.executeUpdate();
            if (deleted > 0) {
                System.out.println("Buku berhasil dihapus.");
            } else {
                System.out.println("Buku tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.err.println("Gagal menghapus buku: " + e.getMessage());
        }
    }
}
