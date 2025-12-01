package id.ac.unpas.modul08.controller;

import id.ac.unpas.modul08.model.PersegiPanjangModel;
import id.ac.unpas.modul08.view.PersegiPanjangView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PersegiPanjangController {
    private PersegiPanjangModel model;
    private PersegiPanjangView view;
    
    public PersegiPanjangController(PersegiPanjangModel model, PersegiPanjangView view) {
        this.model = model;
        this.view = view;
        
        // Menghubungkan View dengan Controller
        this.view.addHitungListener(new HitungListener());
        this.view.addKelilingListener(new KelilingListener());
        this.view.addResetListener(new ResetListener());
    }
    
    // Inner class untuk menangani event dari tombol Hitung Luas
    class HitungListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double p = view.getPanjang();
                double l = view.getLebar();
                
                // 2. Kirim data ke Model
                model.setPanjang(p);
                model.setLebar(l);
                
                // 3. Jalankan logika Bisnis di Model
                model.hitungLuas();
                
                // 4. Ambil hasil dari Model dan tampilkan kembali di View
                double hasil = model.getLuas();
                view.setHasil(hasil);
                
            } catch (NumberFormatException ex) {
                view.tampilkanPesanError("Masukkan angka yang valid!");
            }
        }
    }
    
    // Inner class untuk menangani event dari tombol Hitung Keliling
    class KelilingListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double p = view.getPanjang();
                double l = view.getLebar();
                
                // Kirim data ke Model
                model.setPanjang(p);
                model.setLebar(l);
                
                // Jalankan logika Bisnis di Model
                model.hitungKeliling();
                
                // Ambil hasil dari Model dan tampilkan kembali di View
                double keliling = model.getKeliling();
                view.setKeliling(keliling);
                
            } catch (NumberFormatException ex) {
                view.tampilkanPesanError("Masukkan angka yang valid!");
            }
        }
    }
    
    // Inner class untuk menangani event dari tombol Reset
    class ResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.resetForm();
        }
    }
}