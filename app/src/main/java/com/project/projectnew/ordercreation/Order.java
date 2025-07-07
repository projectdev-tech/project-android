package com.project.projectnew.ordercreation;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import java.io.Serializable;
import java.util.List;

@Entity(tableName = "orders")
@TypeConverters({Converters.class})
public class Order implements Serializable {

    @PrimaryKey
    @NonNull
    private String noOrder;

    private List<Product> productList;
    private String totalHarga;
    private long waktuPembayaran;
    private String tanggalPembelian;
    private String status;
    private String metodePembayaran; // <-- FIELD BARU

    // Properti untuk detail pengiriman
    private String noTracking;
    private String tanggalPengiriman;
    private String estimasiTiba;
    private String pembeli;
    private List<ShippingStatus> shippingStatusList;

    // Konstruktor diperbarui
    public Order(@NonNull String noOrder, List<Product> productList, String totalHarga, long waktuPembayaran, String tanggalPembelian, String status, String metodePembayaran) {
        this.noOrder = noOrder;
        this.productList = productList;
        this.totalHarga = totalHarga;
        this.waktuPembayaran = waktuPembayaran;
        this.tanggalPembelian = tanggalPembelian;
        this.status = status;
        this.metodePembayaran = metodePembayaran; // <-- PENAMBAHAN
    }

    // Getters & Setters
    @NonNull
    public String getNoOrder() { return noOrder; }
    public void setNoOrder(@NonNull String noOrder) { this.noOrder = noOrder; }
    public List<Product> getProductList() { return productList; }
    public void setProductList(List<Product> productList) { this.productList = productList; }
    public String getTotalHarga() { return totalHarga; }
    public void setTotalHarga(String totalHarga) { this.totalHarga = totalHarga; }
    public long getWaktuPembayaran() { return waktuPembayaran; }
    public void setWaktuPembayaran(long waktuPembayaran) { this.waktuPembayaran = waktuPembayaran; }
    public String getTanggalPembelian() { return tanggalPembelian; }
    public void setTanggalPembelian(String tanggalPembelian) { this.tanggalPembelian = tanggalPembelian; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getMetodePembayaran() { return metodePembayaran; } // <-- GETTER BARU
    public void setMetodePembayaran(String metodePembayaran) { this.metodePembayaran = metodePembayaran; } // <-- SETTER BARU
    public String getNoTracking() { return noTracking; }
    public void setNoTracking(String noTracking) { this.noTracking = noTracking; }
    public String getTanggalPengiriman() { return tanggalPengiriman; }
    public void setTanggalPengiriman(String tanggalPengiriman) { this.tanggalPengiriman = tanggalPengiriman; }
    public String getEstimasiTiba() { return estimasiTiba; }
    public void setEstimasiTiba(String estimasiTiba) { this.estimasiTiba = estimasiTiba; }
    public String getPembeli() { return pembeli; }
    public void setPembeli(String pembeli) { this.pembeli = pembeli; }
    public List<ShippingStatus> getShippingStatusList() { return shippingStatusList; }
    public void setShippingStatusList(List<ShippingStatus> shippingStatusList) { this.shippingStatusList = shippingStatusList; }
}