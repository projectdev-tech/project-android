package com.project.projectnew.ordercreation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.project.projectnew.R;

public class UbahKtpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_ktp);

        ImageButton btnBack = findViewById(R.id.btnBack);
        Button btnSimpan = findViewById(R.id.btnSimpan);
        EditText etNomorKtp = findViewById(R.id.etNomorKtp);
        Button btnAmbilFoto = findViewById(R.id.btnAmbilFoto);

        // --- PERUBAHAN DI SINI ---
        // Ganti referensi dari Button ke area upload
        View layoutUploadBox = findViewById(R.id.layoutUploadBox);

        String currentKtp = getIntent().getStringExtra("CURRENT_VALUE");
        if (currentKtp != null) {
            etNomorKtp.setText(currentKtp);
        }

        btnBack.setOnClickListener(v -> finish());

        btnSimpan.setOnClickListener(v -> {
            Toast.makeText(this, "Perubahan KTP disimpan", Toast.LENGTH_SHORT).show();
            finish();
        });

        // --- PERUBAHAN DI SINI ---
        // Pindahkan listener ke area upload
        layoutUploadBox.setOnClickListener(v -> {
            Toast.makeText(this, "Buka galeri untuk upload KTP...", Toast.LENGTH_SHORT).show();
        });

        btnAmbilFoto.setOnClickListener(v -> {
            Toast.makeText(this, "Buka kamera untuk ambil foto...", Toast.LENGTH_SHORT).show();
        });
    }
}