package id.ac.unpas.modul07;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class ManajemenNilaiSiswaApp extends JFrame {

    private JTextField txtNama;
    private JTextField txtNilai;
    private JComboBox<String> cmbMatkul;
    private JTable tableData;
    private DefaultTableModel tableModel;
    private JTabbedPane tabbedPane;

    // PANEL INPUT 
    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Nama Siswa:"));
        txtNama = new JTextField();
        panel.add(txtNama);

        panel.add(new JLabel("Mata Pelajaran:"));
        String[] matkul = {
            "Matematika Dasar", "Bahasa Indonesia",
            "Algoritma dan Pemrograman I", "Praktikum Pemrograman II"
        };
        cmbMatkul = new JComboBox<>(matkul);
        panel.add(cmbMatkul);

        panel.add(new JLabel("Nilai (0-100):"));
        txtNilai = new JTextField();
        panel.add(txtNilai);

        JButton btnSimpan = new JButton("Simpan Data");
        JButton btnReset = new JButton("Reset");

        panel.add(btnSimpan);
        panel.add(btnReset);

        btnSimpan.addActionListener(e -> prosesSimpan());

        // TOMBOL RESET (TUGAS)
        btnReset.addActionListener(e -> {
            txtNama.setText("");
            txtNilai.setText("");
            cmbMatkul.setSelectedIndex(0);
        });

        return panel;
    }

    // PANEL TABEL (SUDAH ADA TOMBOL HAPUS)

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] kolom = {"Nama Siswa", "Mata Pelajaran", "Nilai", "Grade"};
        tableModel = new DefaultTableModel(kolom, 0);
        tableData = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(tableData);
        panel.add(scrollPane, BorderLayout.CENTER);

        // TOMBOL HAPUS (TUGAS)
        JButton btnHapus = new JButton("Hapus Data");

        btnHapus.addActionListener(e -> {
            int row = tableData.getSelectedRow();
            if (row > -1) {
                tableModel.removeRow(row);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Pilih baris yang ingin dihapus!",
                        "Peringatan",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        JPanel panelBawah = new JPanel();
        panelBawah.add(btnHapus);
        panel.add(panelBawah, BorderLayout.SOUTH);

        return panel;
    }

 
    // PROSES SIMPAN (VALIDASI + SWITCH CASE)
    private void prosesSimpan() {
        String nama = txtNama.getText();
        String matkul = (String) cmbMatkul.getSelectedItem();
        String strNilai = txtNilai.getText();

        // VALIDASI NAMA KOSONG
        if (nama.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama tidak boleh kosong!",
                    "Error Validasi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // VALIDASI MINIMAL 3 KARAKTER (TUGAS)
        if (nama.trim().length() < 3) {
            JOptionPane.showMessageDialog(this, "Nama minimal terdiri dari 3 karakter!",
                    "Error Validasi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // VALIDASI NILAI ANGKA
        int nilai;
        try {
            nilai = Integer.parseInt(strNilai);
            if (nilai < 0 || nilai > 100) {
                JOptionPane.showMessageDialog(this, "Nilai harus antara 0 - 100!",
                        "Error Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Nilai harus berupa angka!",
                    "Error Validasi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // LOGIKA GRADE MENGGUNAKAN SWITCH CASE
        String grade;
        switch (nilai / 10) {
            case 10:
            case 9:
            case 8:
                grade = "A";
                break;
            case 7:
                grade = "AB";
                break;
            case 6:
                grade = "B";
                break;
            case 5:
                grade = "BC";
                break;
            case 4:
                grade = "C";
                break;
            case 3:
                grade = "D";
                break;
            default:
                grade = "E";
        }

        Object[] data = {nama, matkul, nilai, grade};
        tableModel.addRow(data);

        txtNama.setText("");
        txtNilai.setText("");
        cmbMatkul.setSelectedIndex(0);

        JOptionPane.showMessageDialog(this, "Data Berhasil Disimpan!");
        tabbedPane.setSelectedIndex(1);
    }

    // KONSTRUKTOR

    public ManajemenNilaiSiswaApp() {
        setTitle("Aplikasi Manajemen Nilai Siswa");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Input Data", createInputPanel());
        tabbedPane.addTab("Daftar Nilai", createTablePanel());

        add(tabbedPane);
    }

 
    // MAIN
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ManajemenNilaiSiswaApp().setVisible(true);
        });
    }
}



