package id.ac.unpas.modul09;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class AplikasiFileIO extends JFrame {

    // Komponen UI
    private JTextArea textArea;
    private JButton btnOpenText, btnSaveText, btnAppendText;
    private JButton btnSaveBinary, btnLoadBinary;
    private JButton btnSaveObject, btnLoadObject;
    private JFileChooser fileChooser;
    


    public AplikasiFileIO() {
        super("Tutorial File IO & Exception Handling");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inisialisasi Komponen
        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        fileChooser = new JFileChooser();

        // Panel Tombol (2 baris)
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1));
        
        // Baris pertama
        JPanel row1 = new JPanel();
        btnOpenText   = new JButton("Buka Text");
        btnSaveText   = new JButton("Simpan Text");
        btnAppendText = new JButton("Tambah Text (Append)");
        row1.add(btnOpenText);
        row1.add(btnSaveText);
        row1.add(btnAppendText);
        
        // Baris kedua
        JPanel row2 = new JPanel();
        btnSaveBinary = new JButton("Simpan Config (Binary)");
        btnLoadBinary = new JButton("Muat Config (Binary)");
        btnSaveObject = new JButton("Simpan Object");
        btnLoadObject = new JButton("Muat Object");
        row2.add(btnSaveBinary);
        row2.add(btnLoadBinary);
        row2.add(btnSaveObject);
        row2.add(btnLoadObject);

        buttonPanel.add(row1);
        buttonPanel.add(row2);

        // Layout
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // --- Event Handling ---

        // 1. MEMBACA FILE TEKS (Text Stream)
        btnOpenText.addActionListener(e -> bukaFileTeks());

        // 2. MENULIS FILE TEKS (Text Stream)
        btnSaveText.addActionListener(e -> simpanFileTeks());

        // 3. MENAMBAH TEXT KE FILE (Append)
        btnAppendText.addActionListener(e -> appendFileTeks());

        // 4. MENULIS FILE BINARY (Byte Stream)
        btnSaveBinary.addActionListener(e -> simpanConfigBinary());

        // 5. MEMBACA FILE BINARY (Byte Stream)
        btnLoadBinary.addActionListener(e -> muatConfigBinary());

        // 6. MENYIMPAN OBJECT (Object Serialization)
        btnSaveObject.addActionListener(e -> simpanObject());

        // 7. MEMBACA OBJECT (Object Deserialization)
        btnLoadObject.addActionListener(e -> muatObject());

        // AUTO LOAD: Membaca file last_notes.txt saat aplikasi dibuka
        muatLastNotes();
    }


    // Method baru: Membaca file last_notes.txt otomatis saat aplikasi dibuka
    private void muatLastNotes() {
        File file = new File("last_notes.txt");
        
        // Cek apakah file ada
        if (!file.exists()) {
            return; // Jika tidak ada, diam saja (tidak error)
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            textArea.setText(""); // Kosongkan area
            String line;
            
            // Baca baris demi baris
            while ((line = reader.readLine()) != null) {
                textArea.append(line + "\n");
            }
            
        } catch (FileNotFoundException ex) {
            // File tidak ditemukan, diam saja
        } catch (IOException ex) {
            // Gagal membaca, diam saja
        }
    }


    //Contoh: Membaca File Teks dengan Try-Catch-Finally Konvensional
    private void bukaFileTeks() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            BufferedReader reader = null; // Deklarasi di luar try agar bisa diakses di finally

            try {
                // Membuka stream
                reader = new BufferedReader(new FileReader(file));
                textArea.setText(""); // Kosongkan area

                String line;
                // Baca baris demi baris
                while ((line = reader.readLine()) != null) {
                    textArea.append(line + "\n");
                }

                JOptionPane.showMessageDialog(this, "File berhasil dimuat!");

            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "File tidak ditemukan: " + ex.getMessage());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Gagal membaca file: " + ex.getMessage());
            } finally {
                // Blok Finally: Selalu dijalankan untuk menutup resource
                try {
                    if (reader != null) {
                        reader.close(); // PENTING: Menutup stream
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    //Contoh: Menulis File Teks menggunakan Try-With-Resources (Lebih Modern)
    private void simpanFileTeks() {
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            // Try-with-resources otomatis menutup stream tanpa blok finally manual
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(textArea.getText());
                JOptionPane.showMessageDialog(this, "File berhasil disimpan!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan file: " + ex.getMessage());
            }
        }
    }


    // Method baru: Menambahkan teks ke file yang sudah ada (Append Mode)
    private void appendFileTeks() {
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            // FileWriter dengan parameter append=true untuk menambah, bukan menimpa
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(textArea.getText());
                JOptionPane.showMessageDialog(this, "Teks berhasil ditambahkan ke file!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Gagal menambahkan teks: " + ex.getMessage());
            }
        }
    }


    //Contoh: Menulis Binary (Menyimpan ukuran font saat ini ke file .bin)
    private void simpanConfigBinary() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("config.bin"))) {

            // Kita simpan ukuran font saat ini (Integer)
            int fontSize = textArea.getFont().getSize();
            dos.writeInt(fontSize);

            JOptionPane.showMessageDialog(this,
                    "Ukuran font (" + fontSize + ") disimpan ke config.bin");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Gagal menyimpan binary: " + ex.getMessage());
        }
    }


    //Contoh: Membaca Binary (Mengambil ukuran font dari file .bin)
    private void muatConfigBinary() {
        try (DataInputStream dis = new DataInputStream(new FileInputStream("config.bin"))) {

            // Membaca data Integer mentah
            int fontSize = dis.readInt();

            // Terapkan ke aplikasi
            textArea.setFont(new Font("Monospaced", Font.PLAIN, fontSize));
            JOptionPane.showMessageDialog(this,
                    "Font diubah menjadi ukuran: " + fontSize);

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this,
                    "File config.bin belum dibuat!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Gagal membaca binary: " + ex.getMessage());
        }
    }


    // Contoh: Menyimpan Objek menggunakan ObjectOutputStream
    private void simpanObject() {
        // Minta input username
        String username = JOptionPane.showInputDialog(this, "Masukkan username:");
        if (username == null || username.trim().isEmpty()) {
            return; // Jika cancel atau kosong, batalkan
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("userconfig.dat"))) {
            
            // Buat objek UserConfig
            UserConfig config = new UserConfig();
            config.setUsername(username.trim());
            config.setFontSize(textArea.getFont().getSize());

            // Simpan objek ke file
            oos.writeObject(config);

            JOptionPane.showMessageDialog(this,
                    "Object berhasil disimpan!\nUsername: " + config.getUsername() +
                    "\nFont Size: " + config.getFontSize());

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Gagal menyimpan object: " + ex.getMessage());
        }
    }


    // Contoh: Membaca Objek menggunakan ObjectInputStream
    private void muatObject() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("userconfig.dat"))) {

            // Membaca objek dan melakukan casting ke UserConfig
            UserConfig config = (UserConfig) ois.readObject();

            // Terapkan ke aplikasi
            textArea.setFont(new Font("Monospaced", Font.PLAIN, config.getFontSize()));

            JOptionPane.showMessageDialog(this,
                    "Object berhasil dimuat!\nUsername: " + config.getUsername() +
                    "\nFont Size: " + config.getFontSize());

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this,
                    "File userconfig.dat belum dibuat!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Gagal membaca object: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this,
                    "Class UserConfig tidak ditemukan: " + ex.getMessage());
        }
    }


    //Main method aplikasi
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AplikasiFileIO().setVisible(true);
        });
    }
}