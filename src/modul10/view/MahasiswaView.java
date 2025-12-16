/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.modul10.view;

import id.ac.unpas.modul10.model.Mahasiswa;
import java.util.List;

/**
 *
 * @author Zeina_qn
 */
import id.ac.unpas.modul10.controller.MahasiswaController;
import id.ac.unpas.modul10.model.Mahasiswa; // Asumsi import Model
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class MahasiswaView extends JFrame {

    private MahasiswaController controller;

    // Komponen GUI
    private JTextField txtNama, txtNIM, txtJurusan;
    private JTextField txtCari;
    private JButton btnSimpan, btnEdit, btnHapus, btnClear, btnCari;
    private JTable tableMahasiswa;
    private DefaultTableModel model;

    public MahasiswaView() throws SQLException {
        // Setup Frame
        setTitle("Aplikasi CRUD Mahasiswa JDBC (MVC)");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Inisialisasi Model dan Controller
        model = new DefaultTableModel();
        controller = new MahasiswaController(this);

        // --- 1. Panel Form (Input Data) ---
        JPanel panelForm = new JPanel(new GridLayout(4, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelForm.add(new JLabel("Nama:"));
        txtNama = new JTextField();
        panelForm.add(txtNama);

        panelForm.add(new JLabel("NIM:"));
        txtNIM = new JTextField();
        panelForm.add(txtNIM);

        panelForm.add(new JLabel("Jurusan:"));
        txtJurusan = new JTextField();
        panelForm.add(txtJurusan);

        // Field Pencarian (Latihan 3)
        panelForm.add(new JLabel("Cari Nama:"));
        txtCari = new JTextField();
        JPanel panelCari = new JPanel(new BorderLayout());
        panelCari.add(txtCari, BorderLayout.CENTER);
        btnCari = new JButton("Cari");
        panelCari.add(btnCari, BorderLayout.EAST);
        panelForm.add(panelCari);

        // Panel Tombol
        JPanel panelTombol = new JPanel(new FlowLayout());
        btnSimpan = new JButton("Simpan");
        btnEdit = new JButton("Edit");
        btnHapus = new JButton("Hapus");
        btnClear = new JButton("Clear");

        panelTombol.add(btnSimpan);
        panelTombol.add(btnEdit);
        panelTombol.add(btnHapus);
        panelTombol.add(btnClear);

        // Gabungkan Panel Atas
        JPanel panelAtas = new JPanel(new BorderLayout());
        panelAtas.add(panelForm, BorderLayout.CENTER);
        panelAtas.add(panelTombol, BorderLayout.SOUTH);
        add(panelAtas, BorderLayout.NORTH);

        // --- 2. Tabel Data ---
        model.addColumn("No");
        model.addColumn("Nama");
        model.addColumn("NIM");
        model.addColumn("Jurusan");
        tableMahasiswa = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tableMahasiswa);
        add(scrollPane, BorderLayout.CENTER);

        // Event Listeners (Memanggil method di Controller)
        tableMahasiswa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.handleTableClick();
            }
        });

        btnSimpan.addActionListener(e -> {
            controller.handleSimpan();
        });
        btnEdit.addActionListener(e -> {
            controller.handleUbah();
        });
        btnHapus.addActionListener(e -> {
            controller.handleHapus();
        });
        btnClear.addActionListener(e -> controller.handleClear());
        btnCari.addActionListener(e -> {
            controller.handleCari();
        });

        // Load data saat startup
        controller.loadData();
    }

    // --- Getters untuk Input dari Controller ---
    public String getNamaInput() { return txtNama.getText(); }
    public String getNIMInput() { return txtNIM.getText(); }
    public String getJurusanInput() { return txtJurusan.getText(); }
    public String getCariKeyword() { return txtCari.getText(); } // Mengganti getTxtCari().getText()
    public JTable getTableMahasiswa() { return tableMahasiswa; }
    public DefaultTableModel getModel() { return model; }

    // --- Setters/Utilities untuk Output ke View ---

    public void setForm(String nama, String nim, String jurusan) {
        txtNama.setText(nama);
        txtNIM.setText(nim);
        txtJurusan.setText(jurusan);
    }

    public void kosongkanForm() {
        txtNama.setText(null);
        txtNIM.setText(null);
        txtJurusan.setText(null);
    }

    public void updateTable(List<Mahasiswa> data) {
        model.setRowCount(0); // Reset tabel
        int no = 1;
        for (Mahasiswa mhs : data) {
            model.addRow(new Object[]{
                    no++,
                    mhs.getNama(),
                    mhs.getNim(),
                    mhs.getJurusan()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new MahasiswaView().setVisible(true);
            } catch (SQLException ex) {
                System.getLogger(MahasiswaView.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        });
    }
}
