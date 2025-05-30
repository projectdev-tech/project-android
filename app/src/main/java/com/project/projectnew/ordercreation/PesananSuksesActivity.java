package com.project.projectnew.ordercreation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.project.projectnew.R;

public class PesananSuksesActivity extends AppCompatActivity {

    private Button btnlanjutpembayaran;
    private String totalHarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan_sukses);

        // Ambil data total harga dari intent
        totalHarga = getIntent().getStringExtra("total_harga");

//         Inisialisasi tombol
        btnlanjutpembayaran = findViewById(R.id.btnlanjutpembayaran);

//         Aksi saat tombol diklik
        btnlanjutpembayaran.setOnClickListener(v -> {
            Intent intent = new Intent(PesananSuksesActivity.this, PembayaranActivity.class);
            intent.putExtra("total_harga", totalHarga);
            startActivity(intent);
        });
    }
}
