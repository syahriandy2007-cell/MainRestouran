package com.mycompany.mainrestoran;

import java.io.*;
import java.util.*;

public class SistemRestoran implements Serializable {

    private static final long serialVersionUID = 1L;

    private String namaRestoran;

    private HashMap<String, Pelanggan> daftarPelanggan;
    private HashMap<String, Menu> daftarMenu;
    private ArrayList<Transaksi> daftarTransaksi;

    // Constructor
    public SistemRestoran(String namaRestoran) {
        this.namaRestoran = namaRestoran;

        daftarPelanggan = new HashMap<>();
        daftarMenu = new HashMap<>();
        daftarTransaksi = new ArrayList<>();
    }

    // Menambahkan pelanggan
    public void tambahPelanggan(String id, String nama) {
        Pelanggan pelanggan = new Pelanggan(id, nama);
        daftarPelanggan.put(id, pelanggan);
    }

    // Menambahkan menu
    public void tambahMenu(String kode, String nama, int stok) {
        Menu menu = new Menu(kode, nama, stok);
        daftarMenu.put(kode, menu);
    }

    // Memproses transaksi
    public void prosesTransaksi(String id, String kode, int jumlah)
            throws TransaksiException {

        if (!daftarPelanggan.containsKey(id)) {
            throw new TransaksiException(
                    "Pelanggan dengan ID " + id + " tidak ditemukan."
            );
        }

        if (!daftarMenu.containsKey(kode)) {
            throw new TransaksiException(
                    "Menu dengan kode " + kode + " tidak ditemukan."
            );
        }

        if (jumlah <= 0) {
            throw new TransaksiException(
                    "Jumlah transaksi harus lebih dari 0."
            );
        }

        Menu menu = daftarMenu.get(kode);

        if (menu.getStok() < jumlah) {
            throw new TransaksiException(
                    "Stok " + menu.getNama() + " tidak mencukupi."
            );
        }

        menu.kurangiStok(jumlah);

        Transaksi transaksi = new Transaksi(id, kode, jumlah);
        daftarTransaksi.add(transaksi);

        System.out.println(
                "Transaksi berhasil: " + id
                + " membeli " + menu.getNama()
                + " sebanyak " + jumlah
        );
    }

    // Membaca file transaksi
    public void jalankanTransaksiDariFile(String namaFile)
            throws IOException {

        try (BufferedReader reader =
                new BufferedReader(new FileReader(namaFile))) {

            String baris;

            while ((baris = reader.readLine()) != null) {

                baris = baris.trim();

                if (baris.isEmpty()) {
                    continue;
                }

                try {
                    String[] bagian = baris.split("\\.");

                    if (bagian.length != 3) {
                        throw new IllegalArgumentException(
                                "Format transaksi tidak sesuai."
                        );
                    }

                    String id = bagian[0].split(">")[1];
                    String kode = bagian[1].split(">")[1];

                    int jumlah = Integer.parseInt(
                            bagian[2].split(">")[1]
                    );

                    prosesTransaksi(id, kode, jumlah);

                } catch (TransaksiException e) {

                    System.out.println(
                            "[TRANSAKSI DITOLAK] " + e.getMessage()
                    );

                } catch (Exception e) {

                    System.out.println(
                            "[FORMAT SALAH] " + baris
                    );
                }
            }
        }
    }

    // Menyimpan laporan akhir
    public void simpanLaporanAkhir() {

        // Object Serialization ke state.bak
        try (ObjectOutputStream output =
                new ObjectOutputStream(
                        new FileOutputStream("state.bak"))) {

            output.writeObject(this);

            System.out.println(
                    "\nSerialization berhasil disimpan ke state.bak"
            );

        } catch (IOException e) {

            System.out.println(
                    "Gagal menyimpan serialization: " + e.getMessage()
            );
        }

        // Laporan menggunakan HashMap.keySet()
        try (FileWriter writer =
                new FileWriter("laporan.txt")) {

            writer.write("DAFTAR KODE ID PELANGGAN\n");
            writer.write("========================\n");

            for (String id : daftarPelanggan.keySet()) {
                writer.write(id + "\n");
            }

            System.out.println(
                    "Laporan berhasil disimpan ke laporan.txt"
            );

        } catch (IOException e) {

            System.out.println(
                    "Gagal membuat laporan: " + e.getMessage()
            );
        }
    }
}