package com.project.projectnew.ordercreation;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.project.projectnew.R;

import java.util.concurrent.TimeUnit;

public class RincianPesananActivity extends AppCompatActivity {

    private TextView tvTotalPembayaran, tvBayarDalam;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        tvTotalPembayaran = findViewById(R.id.tvtotalpembayaran);
        tvBayarDalam = findViewById(R.id.tvBayarDalam);
        ImageButton imgbtnBack = findViewById(R.id.imgbtnBack);
        Button btnMenungguPembayaran = findViewById(R.id.btnMenungguPembayaran); // pastikan ID ini ada di XML

        imgbtnBack.setOnClickListener(v -> finish());

        String totalHarga = getIntent().getStringExtra("total_harga");
        long checkoutTimestamp = getIntent().getLongExtra("waktu_pembayaran", 0);

        tvTotalPembayaran.setText(totalHarga);

        long now = System.currentTimeMillis();
        long diff = (checkoutTimestamp + 24 * 60 * 60 * 1000) - now;

        if (diff > 0) {
            startCountdown(diff);
        } else {
            tvBayarDalam.setText("Waktu pembayaran telah habis");
        }

        btnMenungguPembayaran.setOnClickListener(v -> {
            Intent intent = new Intent(RincianPesananActivity.this, PesananActivity.class);
            intent.putExtra("fragment", "belum_bayar");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    private void startCountdown(long millisUntilFinished) {
        countDownTimer = new CountDownTimer(millisUntilFinished, 1000) {
            public void onTick(long millisUntilFinished) {
                long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60;
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60;

                String waktu = String.format("%02d jam %02d menit %02d detik", hours, minutes, seconds);
                tvBayarDalam.setText(waktu);
            }

            public void onFinish() {
                tvBayarDalam.setText("Waktu pembayaran telah habis");
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        super.onDestroy();
    }
}
