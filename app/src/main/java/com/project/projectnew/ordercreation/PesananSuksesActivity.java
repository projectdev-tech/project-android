package com.project.projectnew.ordercreation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.projectnew.R;

import java.lang.reflect.Type;
import java.util.List;

public class PesananSuksesActivity extends AppCompatActivity {

    private Button btnLanjutPembayaran;
    private ImageButton btnClose;
    private String totalHarga;
    private String waktuBayar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan_sukses);

        btnLanjutPembayaran = findViewById(R.id.btnlanjutpembayaran);
        btnClose = findViewById(R.id.btnClose);

        btnClose.setOnClickListener(v -> {
            Intent intent = new Intent(PesananSuksesActivity.this, BerandaActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        // Ambil data pesanan terakhir dari SharedPreferences
        SharedPreferences prefs = getSharedPreferences("checkout_data", MODE_PRIVATE);
        String json = prefs.getString("order_history", null);
        if (json != null) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Order>>() {}.getType();
            List<Order> orderHistory = gson.fromJson(json, listType);

            if (!orderHistory.isEmpty()) {
                Order lastOrder = orderHistory.get(orderHistory.size() - 1); // Ambil pesanan terakhir
                totalHarga = lastOrder.getTotalHarga();
                waktuBayar = lastOrder.getWaktuPembayaran();

                btnLanjutPembayaran.setOnClickListener(v -> {
                    Intent intent = new Intent(PesananSuksesActivity.this, PembayaranActivity.class);
                    intent.putExtra("total_harga", totalHarga);
                    intent.putExtra("waktu_pembayaran", waktuBayar);
                    startActivity(intent);
                });
            }
        }
    }
}
