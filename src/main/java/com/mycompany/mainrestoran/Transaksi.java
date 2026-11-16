package com.mycompany.mainrestoran;

import java.io.Serializable;

public class Transaksi implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idPelanggan;
    private String kodeMenu;
    private int jumlah;

    public Transaksi(String idPelanggan, String kodeMenu, int jumlah) {
        this.idPelanggan = idPelanggan;
        this.kodeMenu = kodeMenu;
        this.jumlah = jumlah;
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public String getKodeMenu() {
        return kodeMenu;
    }

    public int getJumlah() {
        return jumlah;
    }
}