package com.project.projectnew.ordercreation;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// PERHATIKAN: Semua metode untuk generate data dummy sudah hilang dari sini.
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
                ProductDao productDao = INSTANCE.productDao();
                OrderDao orderDao = INSTANCE.orderDao();

                // PERUBAHAN: Memanggil metode dari kelas DummyDataGenerator
                productDao.insertAll(DummyDataGenerator.generateDummyProducts());
                orderDao.insertOrder(DummyDataGenerator.createOrderDalamProses());
                orderDao.insertOrder(DummyDataGenerator.createOrderDikirim());
                orderDao.insertOrder(DummyDataGenerator.createOrderSelesai());
            });
        }
    };
}