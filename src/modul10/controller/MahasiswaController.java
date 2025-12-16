/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.modul10.controller;

import id.ac.unpas.modul10.model.Mahasiswa;
import id.ac.unpas.modul10.model.MahasiswaDAO;
import id.ac.unpas.modul10.view.MahasiswaView;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.List;

public class MahasiswaController {
    private MahasiswaView view;
    private MahasiswaDAO dao;

    public MahasiswaController(MahasiswaView view) {
        this.view = view;
        this.dao = new MahasiswaDAO();
    }

    public void loadData() {
        try {
            String keyword = view.getCariKeyword(); // Menggunakan getter yang sudah diperbaiki
            List<Mahasiswa> data = dao.getAllMahasiswa(keyword);
            view.updateTable(data);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Gagal Load Data: " + e.getMessage());
        }
    }

    public void handleSimpan() {
        String nama = view.getNamaInput();
        String nim = view.getNIMInput();
        String jurusan = view.getJurusanInput();

        // Validasi (Latihan 2)
        if (nama.trim().isEmpty() || nim.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Data tidak boleh kosong!");
            return;
        }

        try {
            // Pengecekan NIM Unik (Latihan 4)
            if (dao.isNimExist(nim)) {
                JOptionPane.showMessageDialog(view, "Gagal Simpan: NIM sudah ada di database!");
                return;
            }

            Mahasiswa mhs = new Mahasiswa(nama, nim, jurusan);
            dao.save(mhs);
            JOptionPane.showMessageDialog(view, "Data Berhasil Disimpan");
            loadData();
            view.kosongkanForm();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Gagal Simpan: " + e.getMessage());
        }
    }

    public void handleTableClick() {
        int row = view.getTableMahasiswa().getSelectedRow();
        if (row != -1) {
            String nama = view.getModel().getValueAt(row, 1).toString();
            String nim = view.getModel().getValueAt(row, 2).toString();
            String jurusan = view.getModel().getValueAt(row, 3).toString();
            view.setForm(nama, nim, jurusan);
        }
    }

    public void handleUbah() {
        String nama = view.getNamaInput();
        String nim = view.getNIMInput();
        String jurusan = view.getJurusanInput();

        if (nim.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Pilih data yang akan diubah!");
            return;
        }

        try {
            Mahasiswa mhs = new Mahasiswa(nama, nim, jurusan);
            dao.update(mhs); // Asumsi method update(Mahasiswa) ada di DAO
            JOptionPane.showMessageDialog(view, "Data Berhasil Diubah");
            loadData();
            view.kosongkanForm();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Gagal Edit: " + e.getMessage());
        }
    }

    public void handleHapus() {
        String nim = view.getNIMInput();

        if (nim.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Pilih data yang akan dihapus!");
            return;
        }

        try {
            dao.delete(nim); // Asumsi method delete(String nim) ada di DAO
            JOptionPane.showMessageDialog(view, "Data Berhasil Dihapus");
            loadData();
            view.kosongkanForm();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Gagal Hapus: " + e.getMessage());
        }
    }

    public void handleCari() {
        // Logika pencarian hanya memanggil ulang loadData dengan keyword baru
        loadData();
    }

    public void handleClear() {
        view.kosongkanForm();
    }
}