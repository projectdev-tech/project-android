package com.project.projectnew.ordercreation;

import java.io.Serializable;

// Tambahkan "implements Serializable" agar bisa dikirim via Intent
public class Notifikasi implements Serializable {
    private String tipe; // "Transaksi", "Promo", "Info"
    private String judul;
    private String isi;
    private String tanggal;
    private int ikonResId;
    private String orderId; // <-- FIELD BARU untuk menghubungkan ke Order

    public Notifikasi(String tipe, String judul, String isi, String tanggal, int ikonResId, String orderId) {
        this.tipe = tipe;
        this.judul = judul;
        this.isi = isi;
        this.tanggal = tanggal;
        this.ikonResId = ikonResId;
        this.orderId = orderId; // <-- PENAMBAHAN
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

    public String getOrderId() { return orderId; } // <-- GETTER BARU
}