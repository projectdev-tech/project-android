package com.project.projectnew.ordercreation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DummyDataGenerator {

    // LIST STATIS UNTUK MENYIMPAN DATA SELAMA APLIKASI BERJALAN
    private static List<Order> runtimeOrderList;

    /**
     * Metode ini sekarang berfungsi untuk mengambil daftar order.
     * Jika daftar belum dibuat, ia akan membuatnya.
     * Jika sudah ada, ia akan mengembalikan daftar yang sudah ada (termasuk order baru).
     */
    public static List<Order> getOrders() {
        if (runtimeOrderList == null) {
            runtimeOrderList = new ArrayList<>();
            runtimeOrderList.add(createOrderMenungguPembayaran());
            runtimeOrderList.add(createOrderDalamProses());
            runtimeOrderList.addAll(createOrdersDikirim());
            runtimeOrderList.addAll(createOrdersSelesai());
        }
        return runtimeOrderList;
    }

    /**
     * Metode baru untuk menambahkan order ke daftar runtime.
     * @param newOrder Pesanan baru yang dibuat dari halaman Checkout.
     */
    public static void addOrder(Order newOrder) {
        if (runtimeOrderList == null) {
            getOrders(); // Pastikan list diinisialisasi
        }
        // Tambahkan order baru di posisi paling atas (indeks 0)
        runtimeOrderList.add(0, newOrder);
    }


    // --- Pembuatan Data untuk Setiap Status ---

    private static Order createOrderMenungguPembayaran() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("p001", "Kopi Kapal Api Special", "1 renceng", "Rp 13.000", 10, 2));
        productList.add(new Product("p002", "Indomie Goreng", "5 pcs", "Rp 15.000", 20, 1));

        return new Order(
                getDummyNoOrder(1),
                productList,
                "Rp 43.000",
                System.currentTimeMillis(), // Waktu checkout adalah sekarang
                getFormattedDate(System.currentTimeMillis()),
                "Menunggu Pembayaran"
        );
    }

    private static Order createOrderDalamProses() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("p003", "Sabun Lifebuoy", "4 batang", "Rp 18.000", 15, 1));

        return new Order(
                getDummyNoOrder(2),
                productList,
                "Rp 18.000",
                System.currentTimeMillis() - TimeUnit.HOURS.toMillis(2), // 2 jam yang lalu
                getFormattedDate(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(2)),
                "Menunggu Konfirmasi"
        );
    }

    private static List<Order> createOrdersDikirim() {
        List<Order> shippedOrders = new ArrayList<>();

        // Order Dikirim 1
        List<Product> productList1 = new ArrayList<>();
        productList1.add(new Product("p004", "Beras Rojolele Super", "Karung 5kg", "Rp 68.000", 10, 1));
        productList1.add(new Product("p005", "Minyak Goreng Sania", "Pouch 2L", "Rp 35.000", 15, 2));

        Order order1 = new Order(
                getDummyNoOrder(3),
                productList1,
                "Rp 138.000",
                System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1), // Kemarin
                getFormattedDate(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1)),
                "Pesanan Dikirim"
        );
        order1.setNoTracking("JNE-TGR-2500184");
        order1.setTanggalPengiriman("16 Juni 2025");
        order1.setEstimasiTiba("17 - 18 Juni 2025");
        order1.setPembeli("Toko Kelontong Berkah");
        // Status Pengiriman
        List<ShippingStatus> statusList1 = new ArrayList<>();
        statusList1.add(new ShippingStatus("17 Juni 2025", "09:15 WIB", "Pesanan sedang diantar oleh kurir ke alamat tujuan.", true));
        statusList1.add(new ShippingStatus("16 Juni 2025", "18:45 WIB", "Pesanan telah tiba di gudang sortir JNE Tangerang.", false));
        order1.setShippingStatusList(statusList1);
        shippedOrders.add(order1);

        return shippedOrders;
    }

    private static List<Order> createOrdersSelesai() {
        List<Order> completedOrders = new ArrayList<>();

        // Order Selesai 1
        List<Product> productList1 = new ArrayList<>();
        productList1.add(new Product("p006", "Kecap Bango Manis", "Botol 520ml", "Rp 21.000", 50, 3));
        Order order1 = new Order(
                getDummyNoOrder(4),
                productList1,
                "Rp 63.000",
                System.currentTimeMillis() - TimeUnit.DAYS.toMillis(3), // 3 hari yang lalu
                getFormattedDate(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(3)),
                "Pesanan Diterima"
        );
        completedOrders.add(order1);

        return completedOrders;
    }

    // --- Helper Methods ---

    private static String getDummyNoOrder(int sequence) {
        String datePart = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        return String.format("306-%s-D%04d", datePart, sequence);
    }

    private static String getFormattedDate(long timestamp) {
        // PERBAIKAN: "Tinder" diubah menjadi "yyyy"
        return new SimpleDateFormat("dd MMMM yyyy, HH.mm.ss", new Locale("in", "ID")).format(new Date(timestamp));
    }
}