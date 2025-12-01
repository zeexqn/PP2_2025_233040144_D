package id.ac.unpas.modul08.view;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class PersegiPanjangView extends JFrame {
    // Komponen UI sebagai atribut
    private JTextField txtPanjang = new JTextField(10);
    private JTextField txtLebar = new JTextField(10);
    private JLabel lblHasil = new JLabel("--");
    private JLabel lblKeliling = new JLabel("--");
    private JButton btnHitung = new JButton("Hitung luas");
    private JButton btnKeliling = new JButton("Hitung keliling");
    private JButton btnReset = new JButton("Reset");
    
    public PersegiPanjangView() {
        // Inisialisasi JFrame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 250);
        this.setLayout(new GridLayout(7, 2, 10, 10)); // Grid 7 baris
        this.setTitle("MVC Kalkulator");
        
        this.add(new JLabel("Panjang:"));
        this.add(txtPanjang);
        this.add(new JLabel("Lebar:"));
        this.add(txtLebar);
        this.add(new JLabel("Total luas:"));
        this.add(lblHasil);
        this.add(new JLabel("Total keliling:"));
        this.add(lblKeliling);
        this.add(btnHitung);
        this.add(btnKeliling);
        this.add(btnReset);
        this.add(new JLabel(""));
    }
    
    // Mengambil nilai dari Text TextField
    public double getPanjang() {
        return Double.parseDouble(txtPanjang.getText());
    }
    
    // Mengambil nilai dari Text TextField
    public double getLebar() {
        return Double.parseDouble(txtLebar.getText());
    }
    
    // Mengatur hasil luas
    public void setHasil(double hasil) {
        lblHasil.setText(String.valueOf(hasil));
    }
    
    // Mengatur hasil keliling
    public void setKeliling(double keliling) {
        lblKeliling.setText(String.valueOf(keliling));
    }
    
    // Reset semua inputan dan hasil
    public void resetForm() {
        txtPanjang.setText("");
        txtLebar.setText("");
        lblHasil.setText("--");
        lblKeliling.setText("--");
    }
    
    // Menampilkan pesan error jika input tidak valid
    public void tampilkanPesanError(String pesan) {
        JOptionPane.showMessageDialog(this, pesan);
    }
    
    // Mendaftarkan listener untuk tombol (Controller yang akan membuat actionnya)
    public void addHitungListener(ActionListener listener) {
        btnHitung.addActionListener(listener);
    }
    
    public void addKelilingListener(ActionListener listener) {
        btnKeliling.addActionListener(listener);
    }
    
    public void addResetListener(ActionListener listener) {
        btnReset.addActionListener(listener);
    }
}