package com.project.projectnew.ordercreation;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
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

        executorService.execute(() -> {
            List<Order> allOrders = db.orderDao().getCompletedOrders();
            mainThreadHandler.post(() -> {
                DaftarTransaksiAdapter adapter = new DaftarTransaksiAdapter(allOrders);
                rvDaftarTransaksi.setAdapter(adapter);
            });
        });

        btnBack.setOnClickListener(v -> finish());
        btnFilter.setOnClickListener(v -> showFilterDialog());
    }

    private void showFilterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_filter_transaksi, null);
        builder.setView(dialogView);

        TextView tvTanggalDari = dialogView.findViewById(R.id.tvTanggalDari);
        TextView tvTanggalSampai = dialogView.findViewById(R.id.tvTanggalSampai);
        Button btnBatal = dialogView.findViewById(R.id.btnBatal);
        Button btnLihat = dialogView.findViewById(R.id.btnLihat);

        AlertDialog dialog = builder.create();

        // --- PERUBAHAN UTAMA DI SINI ---
        // Membuat latar belakang jendela dialog transparan
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        tvTanggalDari.setOnClickListener(v -> {
            Toast.makeText(this, "Buka kalender Tanggal Dari...", Toast.LENGTH_SHORT).show();
        });

        tvTanggalSampai.setOnClickListener(v -> {
            Toast.makeText(this, "Buka kalender Tanggal Sampai...", Toast.LENGTH_SHORT).show();
        });

        btnBatal.setOnClickListener(v -> {
            dialog.dismiss();
        });

        btnLihat.setOnClickListener(v -> {
            Toast.makeText(this, "Menerapkan filter...", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        dialog.show();
    }
}