/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.ac.unpas.modul10;

/**
 *
 * @author Zeina_qn
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class MahasiswaApp extends JFrame {

    // Komponen GUI
    JTextField txtNama, txtNIM, txtJurusan;
    JTextField txtCari; // *[Tambahan Latihan 3]
    JButton btnSimpan, btnEdit, btnHapus, btnClear;
    JButton btnCari; // *[Tambahan Latihan 3]
    JTable tableMahasiswa;
    DefaultTableModel model;

    public MahasiswaApp() {
        // Setup Frame
        setTitle("Aplikasi CRUD Mahasiswa JDBC");
        setSize(800, 500); // Ukuran lebih besar untuk field pencarian
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. Panel Form (Input Data)
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

        // *[Tambahan Latihan 3] Field Pencarian
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

        // Gabungkan Panel Form dan Tombol di bagian Atas (NORTH)
        JPanel panelAtas = new JPanel(new BorderLayout());
        panelAtas.add(panelForm, BorderLayout.CENTER);
        panelAtas.add(panelTombol, BorderLayout.SOUTH);
        add(panelAtas, BorderLayout.NORTH);

        // 2. Tabel Data (Menampilkan Data)
        model = new DefaultTableModel();
        model.addColumn("No");
        model.addColumn("Nama");
        model.addColumn("NIM");
        model.addColumn("Jurusan");
        tableMahasiswa = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tableMahasiswa);
        add(scrollPane, BorderLayout.CENTER);

        // Event Listeners
        // Listener Klik Tabel
        tableMahasiswa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tableMahasiswa.getSelectedRow();
                txtNama.setText(model.getValueAt(row, 1).toString());
                txtNIM.setText(model.getValueAt(row, 2).toString());
                txtJurusan.setText(model.getValueAt(row, 3).toString());
            }
        });

        // Aksi Tombol CRUD
        btnSimpan.addActionListener(e -> tambahData());
        btnEdit.addActionListener(e -> ubahData());
        btnHapus.addActionListener(e -> hapusData());
        btnClear.addActionListener(e -> kosongkanForm());

        // *[Tambahan Latihan 3] Aksi Tombol Cari
        btnCari.addActionListener(e -> cariData());

        // Load data saat aplikasi pertama kali jalan
        loadData(null); // Load semua data saat startup
    }

    // LOGIKA CRUD

    /** 1. READ (Menampilkan Data) */
    private void loadData(String searchKeyword) { // *Diubah agar bisa menerima kata kunci pencarian
        model.setRowCount(0); // Reset tabel

        try {
            // Asumsi kelas KoneksiDB sudah diimport atau berada di package yang sama
            Connection conn = KoneksiDB.configDB();
            Statement stm = conn.createStatement();

            // *[Latihan 3] Modifikasi query untuk pencarian
            String sql = "SELECT * FROM mahasiswa";
            if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
                sql += " WHERE nama LIKE '%" + searchKeyword + "%'";
            }

            ResultSet res = stm.executeQuery(sql);

            int no = 1;
            while (res.next()) {
                model.addRow(new Object[]{
                        no++,
                        res.getString("nama"),
                        res.getString("nim"),
                        res.getString("jurusan")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal Load Data: " + e.getMessage());
        }
    }

    /** 2. CREATE (Menambah Data) */
    private void tambahData() {

        // **[Latihan 2] Validasi Input Kosong
        if (txtNama.getText().trim().isEmpty() || txtNIM.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Data tidak boleh kosong!");
            return;
        }

        // **[Latihan 4] Pengecekan NIM Unik
        if (isNimExist(txtNIM.getText())) {
            JOptionPane.showMessageDialog(this, "Gagal Simpan: NIM sudah ada di database!");
            return;
        }

        try {
            String sql = "INSERT INTO mahasiswa (nama, nim, jurusan) VALUES (?, ?, ?)";
            // Asumsi kelas KoneksiDB sudah diimport atau berada di package yang sama
            Connection conn = KoneksiDB.configDB();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, txtNama.getText());
            pst.setString(2, txtNIM.getText());
            pst.setString(3, txtJurusan.getText());
            pst.execute();

            JOptionPane.showMessageDialog(this, "Data Berhasil Disimpan");
            loadData(null);
            kosongkanForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal Simpan:" + e.getMessage());
        }
    }

    /** **[Latihan 4] Method Pengecekan NIM Unik */
    private boolean isNimExist(String nim) {
        try {
            String sql = "SELECT COUNT(*) FROM mahasiswa WHERE nim = ?";
            // Asumsi kelas KoneksiDB sudah diimport atau berada di package yang sama
            Connection conn = KoneksiDB.configDB();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, nim);
            ResultSet res = pst.executeQuery();
            if (res.next()) {
                return res.getInt(1) > 0;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error Cek NIM: " + e.getMessage());
        }
        return false;
    }

    /** 3. UPDATE (Mengubah Data berdasarkan NIM) */
    private void ubahData() {

        // **[Latihan 2] Validasi Input Kosong
        if (txtNama.getText().trim().isEmpty() || txtNIM.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Data tidak boleh kosong!");
            return;
        }

        try {
            String sql = "UPDATE mahasiswa SET nama=?, jurusan=? WHERE nim=?";
            // Asumsi kelas KoneksiDB sudah diimport atau berada di package yang sama
            Connection conn = KoneksiDB.configDB();
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, txtNama.getText());
            pst.setString(2, txtJurusan.getText());
            pst.setString(3, txtNIM.getText()); // Kunci update

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data Berhasil Diubah");
            loadData(null);
            kosongkanForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal Edit: " + e.getMessage());
        }
    }

    /** 4. DELETE (Menghapus Data) */
    private void hapusData() {
        try {
            String sql = "DELETE FROM mahasiswa WHERE nim=?";
            // Asumsi kelas KoneksiDB sudah diimport atau berada di package yang sama
            Connection conn = KoneksiDB.configDB();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, txtNIM.getText());
            pst.execute();

            JOptionPane.showMessageDialog(this, "Data Berhasil Dihapus");
            loadData(null);
            kosongkanForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal Hapus: " + e.getMessage());
        }
    }

    /** **[Latihan 3] Method Pencarian Data */
    private void cariData() {
        String keyword = txtCari.getText();
        loadData(keyword); // Memuat data dengan kata kunci pencarian
    }


    private void kosongkanForm() {
        txtNama.setText(null);
        txtNIM.setText(null);
        txtJurusan.setText(null);
    }

    public static void main(String[] args) {
        // Menjalankan Aplikasi
        SwingUtilities.invokeLater(() -> new MahasiswaApp().setVisible(true));
    }
}
