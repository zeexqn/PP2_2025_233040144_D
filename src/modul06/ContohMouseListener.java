
package id.ac.unpas.modul06;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 * @author wdgus
 */
public class ContohMouseListener {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Contoh MouseListener");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);

        // 1. Buat komponen (Event Source)
        JPanel panel = new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setPreferredSize(new Dimension(200, 200));

        // 2. Buat Event Listener (menggunakan MouseAdapter)
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Saat mouse masuk, ubah warna jadi biru
                panel.setBackground(Color.CYAN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Saat mouse keluar, kembalikan warna
                panel.setBackground(Color.LIGHT_GRAY);
            }
            
        };

        // 3. Daftarkan listener ke panel
        panel.addMouseListener(adapter);

        // 4. Tambahkan panel ke frame
        frame.add(panel);

        // 5. Tampilkan frame
        frame.setVisible(true);
        
    }
}

