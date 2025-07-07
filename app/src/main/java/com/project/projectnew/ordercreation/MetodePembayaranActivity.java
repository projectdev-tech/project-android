package com.project.projectnew.ordercreation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import com.project.projectnew.R;

public class MetodePembayaranActivity extends AppCompatActivity {

    private RadioGroup radioGroupPayment;
    private Button btnPilih;
    private RadioButton radioQris, radioBca, radioCod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metode_pembayaran);

        // Inisialisasi Views
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnPilih = findViewById(R.id.btnPilih);
        radioGroupPayment = findViewById(R.id.radioGroupPayment);
        radioQris = findViewById(R.id.radioQris);
        radioBca = findViewById(R.id.radioBca);
        radioCod = findViewById(R.id.radioCod);
        LinearLayout layoutQris = findViewById(R.id.layoutQris);
        LinearLayout layoutBca = findViewById(R.id.layoutBca);
        LinearLayout layoutCod = findViewById(R.id.layoutCod);

        // Tombol 'Pilih' nonaktif di awal
        btnPilih.setEnabled(false);
        btnPilih.setAlpha(0.5f);

        // Terima metode pembayaran yang aktif saat ini dan tandai
        String currentMethod = getIntent().getStringExtra("CURRENT_PAYMENT_METHOD");
        if (currentMethod != null) {
            if (currentMethod.equals("QRIS")) {
                radioQris.setChecked(true);
            } else if (currentMethod.equals("BCA Virtual Account")) {
                radioBca.setChecked(true);
            } else if (currentMethod.equals("Bayar di Tempat (COD)")) {
                radioCod.setChecked(true);
            }
        }

        // --- PERBAIKAN UTAMA DI SINI ---
        // Tambahkan logika manual pada setiap listener baris

        layoutQris.setOnClickListener(v -> {
            radioQris.setChecked(true);
            radioBca.setChecked(false);
            radioCod.setChecked(false);
            btnPilih.setEnabled(true);
            btnPilih.setAlpha(1.0f);
        });

        layoutBca.setOnClickListener(v -> {
            radioQris.setChecked(false);
            radioBca.setChecked(true);
            radioCod.setChecked(false);
            btnPilih.setEnabled(true);
            btnPilih.setAlpha(1.0f);
        });

        layoutCod.setOnClickListener(v -> {
            radioQris.setChecked(false);
            radioBca.setChecked(false);
            radioCod.setChecked(true);
            btnPilih.setEnabled(true);
            btnPilih.setAlpha(1.0f);
        });

        // Hapus listener lama dari RadioGroup karena tidak berfungsi
        // radioGroupPayment.setOnCheckedChangeListener(...);

        btnBack.setOnClickListener(v -> {
            setResult(Activity.RESULT_CANCELED);
            finish();
        });

        btnPilih.setOnClickListener(v -> {
            String paymentMethod = "Tidak Dipilih";

            if (radioQris.isChecked()) {
                paymentMethod = "QRIS";
            } else if (radioBca.isChecked()) {
                paymentMethod = "BCA Virtual Account";
            } else if (radioCod.isChecked()) {
                paymentMethod = "Bayar di Tempat (COD)";
            }

            Intent resultIntent = new Intent();
            resultIntent.putExtra("SELECTED_PAYMENT_METHOD", paymentMethod);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }
}