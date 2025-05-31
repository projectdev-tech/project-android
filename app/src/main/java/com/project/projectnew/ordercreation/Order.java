package com.project.projectnew.ordercreation;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    private String noOrder;
    private List<Product> productList;
    private String totalHarga;
    private String waktuPembayaran;
    private String tanggalPembelian;
    private String status;

    public Order(String noOrder, List<Product> productList, String totalHarga, String waktuPembayaran, String tanggalPembelian, String status) {
        this.noOrder = noOrder;
        this.productList = productList;
        this.totalHarga = totalHarga;
        this.waktuPembayaran = waktuPembayaran;
        this.tanggalPembelian = tanggalPembelian;
        this.status = status;
    }

    // Getter dan Setter

    public String getNoOrder() {
        return noOrder;
    }

    public void setNoOrder(String noOrder) {
        this.noOrder = noOrder;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public String getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(String totalHarga) {
        this.totalHarga = totalHarga;
    }

    public String getWaktuPembayaran() {
        return waktuPembayaran;
    }

    public void setWaktuPembayaran(String waktuPembayaran) {
        this.waktuPembayaran = waktuPembayaran;
    }

    public String getTanggalPembelian() {
        return tanggalPembelian;
    }

    public void setTanggalPembelian(String tanggalPembelian) {
        this.tanggalPembelian = tanggalPembelian;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
