package id.ac.unpas.modul05;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 *
 * @author wdgus
 */
public class Latihan4 {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Contoh BorderLayout dengan Aksi Tombol");
				frame.setSize(400, 300);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				// 1. Atur Layout Manager ke BorderLayout
				frame.setLayout(new BorderLayout());

				// 2. Buat komponen
				JLabel label = new JLabel("Label ada di Atas (NORTH)");
				JButton buttonSouth = new JButton("Tombol di Bawah (SOUTH)");
				JButton buttonWest = new JButton("WEST");
				JButton buttonEast = new JButton("EAST");
				JButton buttonCenter = new JButton("CENTER");

				// 3. Tambahkan aksi pada setiap tombol
				buttonSouth.addActionListener(e -> label.setText("Tombol di SOUTH diklik!"));

				// ======== TUGAS ==========
				// Tambahkan aksi untuk tombol lain selain SOUTH
				buttonWest.addActionListener(e -> label.setText("Tombol di WEST diklik!")); // TUGAS
				buttonEast.addActionListener(e -> label.setText("Tombol di EAST diklik!")); // TUGAS
				buttonCenter.addActionListener(e -> label.setText("Tombol di CENTER diklik!"));// TUGAS

				// 4. Tambahkan komponen ke frame DENGAN POSISI
				frame.add(label, BorderLayout.NORTH);
				frame.add(buttonSouth, BorderLayout.SOUTH);
				frame.add(buttonWest, BorderLayout.WEST);
				frame.add(buttonEast, BorderLayout.EAST);
				frame.add(buttonCenter, BorderLayout.CENTER);

				frame.setVisible(true);
			}
		});
	}
}