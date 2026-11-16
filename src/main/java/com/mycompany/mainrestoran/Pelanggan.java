package com.mycompany.mainrestoran;

public class Pelanggan extends Aktor {

    private static final long serialVersionUID = 1L;

    public Pelanggan(String id, String nama) {
        super(id, nama);
    }

    @Override
    public String getJenisAktor() {
        return "Pelanggan";
    }
}