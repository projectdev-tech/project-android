package com.project.projectnew.ordercreation;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Database(entities = {Product.class, Order.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ProductDao productDao();
    public abstract OrderDao orderDao();

    private static volatile AppDatabase INSTANCE;
    private static final ExecutorService databaseWriteExecutor = Executors.newSingleThreadExecutor();

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "app_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                // Isi database dengan data dummy saat pertama kali dibuat
                ProductDao productDao = INSTANCE.productDao();
                OrderDao orderDao = INSTANCE.orderDao();

                // Masukkan data dummy
                productDao.insertAll(generateDummyProducts());
                orderDao.insertOrder(createOrderDalamProses());
                orderDao.insertOrder(createOrderDikirim());
                orderDao.insertOrder(createOrderSelesai());
            });
        }
    };

    // --- Logika Pembuatan Data Dummy ---

    private static List<Product> generateDummyProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("p001", "Kopi Kapal Api Special", "1 renceng", "Rp 13.000", 10, 0));
        products.add(new Product("p002", "Indomie Goreng", "5 pcs", "Rp 15.000", 20, 0));
        products.add(new Product("p003", "Sabun Lifebuoy", "4 batang", "Rp 18.000", 15, 0));
        products.add(new Product("p004", "Beras Rojolele Super", "Karung 5kg", "Rp 68.000", 10, 0));
        products.add(new Product("p005", "Minyak Goreng Sania", "Pouch 2L", "Rp 35.000", 15, 0));
        products.add(new Product("p006", "Kecap Bango Manis", "Botol 520ml", "Rp 21.000", 50, 0));
        products.add(new Product("p007", "Teh Celup Sariwangi", "Box 50", "Rp 10.000", 100, 0));
        return products;
    }

    private static Order createOrderDalamProses() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("p003", "Sabun Lifebuoy", "4 batang", "Rp 18.000", 15, 1));
        return new Order(getDummyNoOrder(2), productList, "Rp 18.000", System.currentTimeMillis() - TimeUnit.HOURS.toMillis(2), getFormattedDate(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(2)), "Menunggu Konfirmasi");
    }

    private static Order createOrderDikirim() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("p004", "Beras Rojolele Super", "Karung 5kg", "Rp 68.000", 10, 1));
        productList.add(new Product("p005", "Minyak Goreng Sania", "Pouch 2L", "Rp 35.000", 15, 2));
        Order order = new Order(getDummyNoOrder(3), productList, "Rp 138.000", System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1), getFormattedDate(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1)), "Pesanan Dikirim");
        order.setNoTracking("JNE-TGR-2500184");
        order.setTanggalPengiriman("17 Jun 2025");
        order.setEstimasiTiba("18 - 19 Jun 2025");
        order.setPembeli("Toko Kelontong Berkah");
        List<ShippingStatus> statusList = new ArrayList<>();
        statusList.add(new ShippingStatus("18 Jun 2025", "09:15 WIB", "Pesanan sedang diantar oleh kurir ke alamat tujuan.", true));
        statusList.add(new ShippingStatus("17 Jun 2025", "18:45 WIB", "Pesanan telah tiba di gudang sortir.", false));
        order.setShippingStatusList(statusList);
        return order;
    }

    private static Order createOrderSelesai() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("p006", "Kecap Bango Manis", "Botol 520ml", "Rp 21.000", 50, 3));
        return new Order(getDummyNoOrder(4), productList, "Rp 63.000", System.currentTimeMillis() - TimeUnit.DAYS.toMillis(3), getFormattedDate(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(3)), "Pesanan Diterima");
    }

    // --- PERBAIKAN: Helper method dipindahkan ke sini ---
    private static String getDummyNoOrder(int sequence) {
        String datePart = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        return String.format("306-%s-D%04d", datePart, sequence);
    }

    private static String getFormattedDate(long timestamp) {
        return new SimpleDateFormat("dd MMMM yyyy, HH.mm.ss", new Locale("in", "ID")).format(new Date(timestamp));
    }
}