package com.project.projectnew.ordercreation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.project.projectnew.R;

public class PembayaranActivity extends AppCompatActivity {

    private TextView tvTotalHargaPembayaran;
    private Button btnMenungguPembayaran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        tvTotalHargaPembayaran = findViewById(R.id.tvtotalpembayaran);
        btnMenungguPembayaran = findViewById(R.id.btnMenungguPembayaran);

        // Ambil total harga dari intent
        String totalHarga = getIntent().getStringExtra("total_harga");
        if (totalHarga != null) {
            tvTotalHargaPembayaran.setText(totalHarga);
        }

        // Saat tombol diklik, pindah ke Keranjang2Activity
        btnMenungguPembayaran.setOnClickListener(v -> {
            Intent intent = new Intent(PembayaranActivity.this, Keranjang2Activity.class);
            startActivity(intent);
            finish(); // Opsional, agar halaman pembayaran tidak bisa dikembali
        });
    }
}
