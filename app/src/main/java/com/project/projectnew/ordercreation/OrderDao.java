package com.project.projectnew.ordercreation;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrder(Order order);

    @Query("SELECT * FROM orders ORDER BY waktuPembayaran DESC")
    List<Order> getAllOrders();

    // --- PERBAIKAN DI SINI ---
    // Kembalikan metode ini agar bisa digunakan oleh PesananFragment
    @Query("SELECT * FROM orders WHERE status = :status ORDER BY waktuPembayaran DESC")
    List<Order> getOrdersByStatus(String status);

    @Query("SELECT * FROM orders WHERE status = 'Pesanan Diterima' ORDER BY waktuPembayaran DESC")
    List<Order> getCompletedOrders();

    @Query("SELECT * FROM orders WHERE noOrder = :noOrder LIMIT 1")
    Order getOrderByNoOrder(String noOrder);
}