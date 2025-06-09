package com.project.projectnew.ordercreation;

public class TransaksiModel {
    private String judul;
    private String deskripsi;
    private String waktu;

    public TransaksiModel(String judul, String deskripsi, String waktu) {
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.waktu = waktu;
    }

    public String getJudul() {
        return judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getWaktu() {
        return waktu;
    }
}
