package com.project.projectnew.ordercreation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DummyDataGenerator {

    public static List<Product> generateDummyProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("p001", "Kopi Kapal Api Special", "1 renceng", "Rp 13.000", 10, 0, "Makanan & Minuman"));
        products.add(new Product("p002", "Indomie Goreng", "5 pcs", "Rp 15.000", 20, 0, "Makanan & Minuman"));
        products.add(new Product("p003", "Sabun Lifebuoy", "4 batang", "Rp 18.000", 15, 0, "Perlengkapan Mandi"));
        products.add(new Product("p004", "Beras Rojolele Super", "Karung 5kg", "Rp 68.000", 10, 0, "Makanan & Minuman"));
        products.add(new Product("p005", "Minyak Goreng Sania", "Pouch 2L", "Rp 35.000", 15, 0, "Makanan & Minuman"));
        products.add(new Product("p006", "Kecap Bango Manis", "Botol 520ml", "Rp 21.000", 50, 0, "Makanan & Minuman"));
        products.add(new Product("p007", "Teh Celup Sariwangi", "Box 50", "Rp 10.000", 100, 0, "Makanan & Minuman"));
        products.add(new Product("p008", "Deterjen Rinso", "Bag 770g", "Rp 25.000", 30, 0, "Perawatan Rumah"));
        products.add(new Product("p009", "Sampo Pantene", "Botol 135ml", "Rp 22.000", 25, 0, "Perlengkapan Mandi"));
        products.add(new Product("p010", "Gas LPG 3kg", "Tabung", "Rp 22.000", 5, 0, "Gas & Air"));
        products.add(new Product("p011", "Air Mineral Aqua", "Galon 19L", "Rp 19.000", 12, 0, "Gas & Air"));
        products.add(new Product("p012", "Lampu Philips LED", "12 Watt", "Rp 45.000", 40, 0, "Perlengkapan Listrik"));
        return products;
    }

    public static Order createOrderDalamProses() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("p003", "Sabun Lifebuoy", "4 batang", "Rp 18.000", 15, 1, "Perlengkapan Mandi"));
        return new Order(getDummyNoOrder(2), productList, "Rp 18.000", System.currentTimeMillis() - TimeUnit.HOURS.toMillis(2), getFormattedDate(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(2)), "Menunggu Konfirmasi", "QRIS");
    }

    public static Order createOrderDikirim() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("p004", "Beras Rojolele Super", "Karung 5kg", "Rp 68.000", 10, 1, "Makanan & Minuman"));
        productList.add(new Product("p005", "Minyak Goreng Sania", "Pouch 2L", "Rp 35.000", 15, 2, "Makanan & Minuman"));
        Order order = new Order(getDummyNoOrder(3), productList, "Rp 138.000", System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1), getFormattedDate(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1)), "Pesanan Dikirim", "BCA Virtual Account");
        order.setNoTracking("JNE-TGR-2500184");
        order.setTanggalPengiriman("25 Jun 2025");
        order.setEstimasiTiba("26 - 27 Jun 2025");
        order.setPembeli("Toko Kelontong Berkah");
        List<ShippingStatus> statusList = new ArrayList<>();
        statusList.add(new ShippingStatus("26 Jun 2025", "09:15 WIB", "Pesanan sedang diantar oleh kurir ke alamat tujuan.", true));
        statusList.add(new ShippingStatus("25 Jun 2025", "18:45 WIB", "Pesanan telah tiba di gudang sortir.", false));
        order.setShippingStatusList(statusList);
        return order;
    }

    public static Order createOrderSelesai() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("p006", "Kecap Bango Manis", "Botol 520ml", "Rp 21.000", 50, 3, "Makanan & Minuman"));
        // Pastikan produk kedua ini ada
        productList.add(new Product("p007", "Teh Celup Sariwangi", "Box 50", "Rp 10.000", 100, 1, "Makanan & Minuman"));
        return new Order(getDummyNoOrder(4), productList, "Rp 73.000", System.currentTimeMillis() - TimeUnit.DAYS.toMillis(3), getFormattedDate(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(3)), "Pesanan Diterima", "Bayar di Tempat (COD)");
    }

    private static String getDummyNoOrder(int sequence) {
        return String.format("DUMMY-ORDER-%d", sequence);
    }

    private static String getFormattedDate(long timestamp) {
        return new SimpleDateFormat("dd MMMM yyyy, HH.mm.ss", new Locale("in", "ID")).format(new Date(timestamp));
    }
}