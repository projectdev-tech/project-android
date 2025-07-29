package com.project.projectnew.ordercreation;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.project.projectnew.R;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DaftarTransaksiActivity extends AppCompatActivity {

    private AppDatabase db;
    private ExecutorService executorService;
    private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_transaksi);

        db = AppDatabase.getDatabase(this);
        executorService = Executors.newSingleThreadExecutor();

        ImageButton btnBack = findViewById(R.id.btnBack);
        ImageButton btnFilter = findViewById(R.id.btnFilter);
        RecyclerView rvDaftarTransaksi = findViewById(R.id.rvDaftarTransaksi);
        rvDaftarTransaksi.setLayoutManager(new LinearLayoutManager(this));

        // --- PERUBAHAN UTAMA DI SINI ---
        executorService.execute(() -> {
            // Panggil metode baru untuk mengambil hanya transaksi yang sudah selesai
            List<Order> completedOrders = db.orderDao().getCompletedOrders();
            mainThreadHandler.post(() -> {
                DaftarTransaksiAdapter adapter = new DaftarTransaksiAdapter(completedOrders);
                rvDaftarTransaksi.setAdapter(adapter);
            });
        });

        btnBack.setOnClickListener(v -> finish());
        btnFilter.setOnClickListener(v -> {
            Toast.makeText(this, "Fitur filter akan datang", Toast.LENGTH_SHORT).show();
        });
    }
}