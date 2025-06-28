package com.project.projectnew.ordercreation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import com.project.projectnew.MainActivity;
import com.project.projectnew.R;

public class PesananSuksesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan_sukses);

        // Menggunakan ID dari layout XML
        Button btnLanjutPembayaran = findViewById(R.id.btnlanjutpembayaran);
        ImageButton btnClose = findViewById(R.id.btnClose);

        // Mengambil data yang dikirim dari CheckoutActivity untuk diteruskan
        String totalHarga = getIntent().getStringExtra("total_harga");
        // Gunakan waktu saat ini sebagai waktu checkout untuk hitung mundur
        long waktuBayar = System.currentTimeMillis();

        // --- PERBAIKAN LOGIKA UTAMA DI SINI ---
        btnLanjutPembayaran.setOnClickListener(v -> {
            // Intent lama yang salah (mengarah ke MainActivity) dihapus.

            // Intent baru yang benar, mengarah ke PembayaranActivity
            Intent intent = new Intent(PesananSuksesActivity.this, PembayaranActivity.class);

            // Kirim data yang dibutuhkan oleh PembayaranActivity
            intent.putExtra("total_harga", totalHarga);
            intent.putExtra("waktu_pembayaran", waktuBayar);

            startActivity(intent);
        });

        // Tombol close akan mengarahkan ke Beranda (MainActivity)
        btnClose.setOnClickListener(v -> {
            Intent intent = new Intent(PesananSuksesActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        // Jika tombol back ditekan, kembali ke Beranda (MainActivity)
        Intent intent = new Intent(PesananSuksesActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        super.onBackPressed();
    }
}