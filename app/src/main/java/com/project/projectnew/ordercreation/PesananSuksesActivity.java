package com.project.projectnew.ordercreation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import com.project.projectnew.R;

public class PesananSuksesActivity extends AppCompatActivity {

    private Button btnLanjutPembayaran;
    private ImageButton btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan_sukses);

        btnLanjutPembayaran = findViewById(R.id.btnlanjutpembayaran);
        btnClose = findViewById(R.id.btnClose);

        // Ambil total harga dari intent yang dikirim CheckoutActivity
        String totalHarga = getIntent().getStringExtra("total_harga");
        long waktuBayar = System.currentTimeMillis(); // Gunakan waktu saat ini

        btnLanjutPembayaran.setOnClickListener(v -> {
            Intent intent = new Intent(PesananSuksesActivity.this, PembayaranActivity.class);
            intent.putExtra("total_harga", totalHarga);
            intent.putExtra("waktu_pembayaran", waktuBayar); // Kirim data dummy/real-time
            startActivity(intent);
        });

        btnClose.setOnClickListener(v -> {
            Intent intent = new Intent(PesananSuksesActivity.this, BerandaActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PesananSuksesActivity.this, BerandaActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}