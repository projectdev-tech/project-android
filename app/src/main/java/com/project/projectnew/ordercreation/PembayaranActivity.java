package com.project.projectnew.ordercreation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

        // Inisialisasi view
        tvTotalHargaPembayaran = findViewById(R.id.tvtotalpembayaran);
        tvBayarDalam = findViewById(R.id.tvBayarDalam);
        btnMenungguPembayaran = findViewById(R.id.btnMenungguPembayaran);
        imgbtnBack = findViewById(R.id.imgbtnBack);

        // Ambil total harga dari intent
        String totalHarga = getIntent().getStringExtra("total_harga");
        if (totalHarga != null) {
            tvTotalHargaPembayaran.setText(totalHarga);
        }

        // Ambil waktu mulai dari SharedPreferences
        SharedPreferences prefs = getSharedPreferences("checkout_data", MODE_PRIVATE);
        long startTime = prefs.getLong("start_time_millis", 0);

        if (startTime != 0) {
            long endTime = startTime + TimeUnit.HOURS.toMillis(24);
            long remainingTime = endTime - System.currentTimeMillis();

            if (remainingTime > 0) {
                startCountdown(remainingTime);
            } else {
                tvBayarDalam.setText("Waktu pembayaran habis");
            }
        } else {
            tvBayarDalam.setText("Waktu tidak tersedia");
        }

        // Tombol kembali: hanya kembali ke PesananSuksesActivity
        imgbtnBack.setOnClickListener(v -> {
            Intent intent = new Intent(PembayaranActivity.this, PesananSuksesActivity.class);
            startActivity(intent);
            finish();
        });

        // Tombol lanjutkan: ke PesananActivity, dan hapus semua back stack (tidak bisa kembali lagi)
        btnMenungguPembayaran.setOnClickListener(v -> {
            Intent intent = new Intent(PembayaranActivity.this, PesananActivity.class);
            intent.putExtra("fragment", "belum_bayar"); // buka fragment belum bayar
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // hapus backstack
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
