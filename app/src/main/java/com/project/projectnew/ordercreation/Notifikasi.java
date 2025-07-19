package com.project.projectnew.ordercreation;

public class Notifikasi {
    private String tipe; // "Transaksi", "Promo", "Info"
    private String judul;
    private String isi;
    private String tanggal;
    private int ikonResId;

    public Notifikasi(String tipe, String judul, String isi, String tanggal, int ikonResId) {
        this.tipe = tipe;
        this.judul = judul;
        this.isi = isi;
        this.tanggal = tanggal;
        this.ikonResId = ikonResId;
    }

    public String getTipe() {
        return tipe;
    }

    public String getJudul() {
        return judul;
    }

    public String getIsi() {
        return isi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public int getIkonResId() {
        return ikonResId;
    }
}