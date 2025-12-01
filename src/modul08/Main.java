package id.ac.unpas.modul08;

import id.ac.unpas.modul08.controller.PersegiPanjangController;
import id.ac.unpas.modul08.model.PersegiPanjangModel;
import id.ac.unpas.modul08.view.PersegiPanjangView;

public class Main {
    public static void main(String[] args) {
        
        PersegiPanjangModel model = new PersegiPanjangModel();
        
        // Inisialisasi View
        PersegiPanjangView view = new PersegiPanjangView();
        
        // Inisialisasi Controller (Menghubungkan Model & View)
        PersegiPanjangController controller = new PersegiPanjangController(model, view);
        
        // Tampilkan View
        view.setVisible(true);
    }
}
