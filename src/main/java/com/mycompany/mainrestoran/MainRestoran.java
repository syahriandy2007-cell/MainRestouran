package com.mycompany.mainrestoran;

public class MainRestoran {

    public static void main(String[] args) {

        SistemRestoran resto =
                new SistemRestoran("Resto Rasa Nusantara");

        resto.tambahPelanggan("ACT-01", "Iwan");
        resto.tambahPelanggan("ACT-02", "Jeni");

        resto.tambahMenu("OBJ-A", "Paket Steak Premium", 10);
        resto.tambahMenu("OBJ-B", "Nasi Goreng Reguler", 2);
        resto.tambahMenu("OBJ-C", "Es Teh Ekonomis", 50);

        System.out.println("--- MEMULAI PEMBACAAN FILE TRANSAKSI ---");

        try {
            resto.jalankanTransaksiDariFile("input_transaksi.txt");

        } catch (Exception e) {
            System.out.println(
                    "[SISTEM FATAL ERROR] File gagal dibaca/diproses: "
                    + e.getMessage()
            );
        }

        resto.simpanLaporanAkhir();
    }
}