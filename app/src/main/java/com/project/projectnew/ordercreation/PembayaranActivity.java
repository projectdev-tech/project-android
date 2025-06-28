package com.project.projectnew.ordercreation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

// PENAMBAHAN: Import MainActivity yang baru
import com.project.projectnew.MainActivity;
import com.project.projectnew.R;

import java.util.concurrent.TimeUnit;

public class PembayaranActivity extends AppCompatActivity {

    private TextView tvTotalHargaPembayaran, tvBayarDalam;
    private Button btnMenungguPembayaran;
    private ImageView imgbtnBack;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        tvTotalHargaPembayaran = findViewById(R.id.tvtotalpembayaran);
        tvBayarDalam = findViewById(R.id.tvBayarDalam);
        btnMenungguPembayaran = findViewById(R.id.btnMenungguPembayaran);
        imgbtnBack = findViewById(R.id.imgbtnBack);

        String totalHarga = getIntent().getStringExtra("total_harga");
        if (totalHarga != null) {
            tvTotalHargaPembayaran.setText(totalHarga);
        }

        // Mengambil waktu mulai dari Intent PesananSuksesActivity, bukan SharedPreferences
        long startTime = getIntent().getLongExtra("waktu_pembayaran", 0);

        if (startTime != 0) {
            long endTime = startTime + TimeUnit.HOURS.toMillis(24);
            long remainingTime = endTime - System.currentTimeMillis();

            if (remainingTime > 0) {
                startCountdown(remainingTime);
            } else {
                tvBayarDalam.setText("Waktu pembayaran habis");
            }
        } else {
            // Fallback jika waktu tidak terkirim
            long checkoutTimestamp = getIntent().getLongExtra("waktu_pembayaran", System.currentTimeMillis());
            long diff = (checkoutTimestamp + 24 * 60 * 60 * 1000) - System.currentTimeMillis();
            if (diff > 0) {
                startCountdown(diff);
            } else {
                tvBayarDalam.setText("Waktu pembayaran telah habis");
            }
        }

        // Tombol kembali: Cukup tutup halaman ini
        imgbtnBack.setOnClickListener(v -> finish());

        // --- PERUBAHAN UTAMA DI SINI ---
        // Tombol ini sekarang mengarah ke MainActivity
        btnMenungguPembayaran.setOnClickListener(v -> {
            // Intent lama yang menyebabkan error dihapus:
            // Intent intent = new Intent(PembayaranActivity.this, PesananActivity.class);

            // Intent baru yang benar:
            Intent intent = new Intent(PembayaranActivity.this, MainActivity.class);
            intent.putExtra("FRAGMENT_TO_LOAD", "PESANAN");
            intent.putExtra("TAB_INDEX", 0); // 0 = Tab "Belum Bayar"
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    private void startCountdown(long millisUntilFinished) {
        countDownTimer = new CountDownTimer(millisUntilFinished, 1000) {
            @Override
            public void onTick(long millisLeft) {
                long hours = TimeUnit.MILLISECONDS.toHours(millisLeft);
                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisLeft) % 60;
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisLeft) % 60;

                String timeText = String.format("%02d jam %02d menit %02d detik", hours, minutes, seconds);
                tvBayarDalam.setText(timeText);
            }

            @Override
            public void onFinish() {
                tvBayarDalam.setText("Waktu pembayaran habis");
            }
        };
        countDownTimer.start();
    }

    @Override
    protected void onDestroy() {
        if (countDownTimer != null) countDownTimer.cancel();
        super.onDestroy();
    }
}