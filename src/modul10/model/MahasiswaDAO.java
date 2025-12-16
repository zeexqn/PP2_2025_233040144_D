/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.modul10.model;

/**
 *
 * @author Zeina_qn
 */
import id.ac.unpas.modul10.KoneksiDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MahasiswaDAO {

    public List<Mahasiswa> getAllMahasiswa(String keyword) throws SQLException {
        List<Mahasiswa> mahasiswaList = new ArrayList<>();
        Connection conn = KoneksiDB.configDB();

        String sql = "SELECT id, nama, nim, jurusan FROM mahasiswa";
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql += " WHERE nama LIKE ?";
        }
        sql += " ORDER BY id";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            if (keyword != null && !keyword.trim().isEmpty()) {
                pst.setString(1, "%" + keyword + "%");
            }
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    Mahasiswa mhs = new Mahasiswa(
                            res.getInt("id"), // Menggunakan constructor dengan ID
                            res.getString("nama"),
                            res.getString("nim"),
                            res.getString("jurusan")
                    );
                    mahasiswaList.add(mhs);
                }
            }
        }
        return mahasiswaList;
    }

    public boolean save(Mahasiswa mhs) throws SQLException {
        String sql = "INSERT INTO mahasiswa (nama, nim, jurusan) VALUES (?, ?, ?)";
        Connection conn = KoneksiDB.configDB();
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, mhs.getNama());
            pst.setString(2, mhs.getNim());
            pst.setString(3, mhs.getJurusan());
            return pst.executeUpdate() > 0;
        }
    }

    public boolean isNimExist(String nim) throws SQLException {
        String sql = "SELECT COUNT(*) FROM mahasiswa WHERE nim = ?";
        Connection conn = KoneksiDB.configDB();
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, nim);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    return res.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    /** * Method untuk mengubah data mahasiswa.
     * Memperbaiki error: Cannot resolve method 'update' in 'MahasiswaDAO'
     */
    public boolean update(Mahasiswa mhs) throws SQLException {
        String sql = "UPDATE mahasiswa SET nama=?, jurusan=? WHERE nim=?";
        Connection conn = KoneksiDB.configDB();
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, mhs.getNama());
            pst.setString(2, mhs.getJurusan());
            pst.setString(3, mhs.getNim());
            return pst.executeUpdate() > 0;
        }
    }

    /** * Method untuk menghapus data mahasiswa berdasarkan NIM.
     * Memperbaiki error: Cannot resolve method 'delete' in 'MahasiswaDAO'
     */
    public boolean delete(String nim) throws SQLException {
        String sql = "DELETE FROM mahasiswa WHERE nim=?";
        Connection conn = KoneksiDB.configDB();
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, nim);
            return pst.executeUpdate() > 0;
        }
    }
}
