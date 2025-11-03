package id.ac.unpas.modul06;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * 
 * @author wdgus
 */
public class KonverterSuhu {
    public static void main(String[] args) {
        // 1. Buat frame utama
        JFrame frame = new JFrame("Konverter Suhu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new FlowLayout());

        // 2. Buat komponen
        JLabel labelCelcius = new JLabel("Celcius:");
        JTextField textCelcius = new JTextField(10);
        JButton buttonKonversi = new JButton("Konversi");
        JLabel labelFahrenheit = new JLabel("Fahrenheit:");
        JLabel labelHasil = new JLabel(""); // Label kosong untuk hasil

        // 3. Tambahkan komponen ke frame
        frame.add(labelCelcius);
        frame.add(textCelcius);
        frame.add(buttonKonversi);
        frame.add(labelFahrenheit);
        frame.add(labelHasil);

        // 4. Tambahkan event listener pada tombol
        buttonKonversi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Ambil nilai dari JTextField
                    double celcius = Double.parseDouble(textCelcius.getText());
                    
                    // Konversi Celcius ke Fahrenheit
                    double fahrenheit = (celcius * 9 / 5) + 32;

                    // Tampilkan hasil
                    labelHasil.setText(String.format("%.2f", fahrenheit));
                } catch (NumberFormatException ex) {
                    // Jika input bukan angka
                    labelHasil.setText("Input tidak valid!");
                }
            }
        });

        // 5. Tampilkan frame
        frame.setVisible(true);
    }
}
