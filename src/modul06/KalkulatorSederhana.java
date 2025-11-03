package id.ac.unpas.modul06;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * @author wdgus
 */
public class KalkulatorSederhana {
    public static void main(String[] args) {
        // 1. Buat frame utama
        JFrame frame = new JFrame("Kalkulator Sederhana");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);
        frame.setLayout(new BorderLayout());

        // 2. Tambahkan layar di bagian atas (NORTH)
        JTextField layar = new JTextField();
        frame.add(layar, BorderLayout.NORTH);

        // 3. Panel tombol dengan GridLayout (4x4)
        JPanel panelTombol = new JPanel();
        panelTombol.setLayout(new GridLayout(4, 4, 5, 5));

        // 4. Tambahkan tombol-tombol (0-9 dan operator)
        String[] tombol = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };

        for (String text : tombol) {
            panelTombol.add(new JButton(text));
        }

        // 5. Tambahkan panel tombol ke frame (CENTER)
        frame.add(panelTombol, BorderLayout.CENTER);

        // 6. Tampilkan frame
        frame.setVisible(true);
    }
}

