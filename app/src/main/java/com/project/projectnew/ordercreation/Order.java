package com.project.projectnew.ordercreation;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    private String noOrder;
    private List<Product> productList;
    private String totalHarga;
    private long waktuPembayaran;
    private String tanggalPembelian;
    private String status;

    // Properti untuk detail pengiriman
    private String noTracking;
    private String tanggalPengiriman;
    private String estimasiTiba;
    private String pembeli;
    private List<ShippingStatus> shippingStatusList; // Untuk riwayat pengiriman

    public Order(String noOrder, List<Product> productList, String totalHarga, long waktuPembayaran, String tanggalPembelian, String status) {
        this.noOrder = noOrder;
        this.productList = productList;
        this.totalHarga = totalHarga;
        this.waktuPembayaran = waktuPembayaran;
        this.tanggalPembelian = tanggalPembelian;
        this.status = status;
    }

    // Getters
    public String getNoOrder() { return noOrder; }
    public List<Product> getProductList() { return productList; }
    public String getTotalHarga() { return totalHarga; }
    public long getWaktuPembayaran() { return waktuPembayaran; }
    public String getTanggalPembelian() { return tanggalPembelian; }
    public String getStatus() { return status; }
    public String getNoTracking() { return noTracking; }
    public String getTanggalPengiriman() { return tanggalPengiriman; }
    public String getEstimasiTiba() { return estimasiTiba; }
    public String getPembeli() { return pembeli; }
    public List<ShippingStatus> getShippingStatusList() { return shippingStatusList; }

    // Setters
    public void setNoOrder(String noOrder) { this.noOrder = noOrder; }
    public void setProductList(List<Product> productList) { this.productList = productList; }
    public void setTotalHarga(String totalHarga) { this.totalHarga = totalHarga; }
    public void setWaktuPembayaran(long waktuPembayaran) { this.waktuPembayaran = waktuPembayaran; }
    public void setTanggalPembelian(String tanggalPembelian) { this.tanggalPembelian = tanggalPembelian; }
    public void setStatus(String status) { this.status = status; }
    public void setNoTracking(String noTracking) { this.noTracking = noTracking; }
    public void setTanggalPengiriman(String tanggalPengiriman) { this.tanggalPengiriman = tanggalPengiriman; }
    public void setEstimasiTiba(String estimasiTiba) { this.estimasiTiba = estimasiTiba; }
    public void setPembeli(String pembeli) { this.pembeli = pembeli; }
    public void setShippingStatusList(List<ShippingStatus> shippingStatusList) { this.shippingStatusList = shippingStatusList; }
}
