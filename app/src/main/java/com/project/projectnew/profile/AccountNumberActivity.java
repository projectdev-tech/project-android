package com.project.projectnew.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.project.projectnew.R;

public class AccountNumberActivity extends AppCompatActivity {

    private EditText etNomorTelepon;
    private ImageView btnBack;
    private MaterialButton btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_number); // Pastikan nama file XML sesuai

        etNomorTelepon = findViewById(R.id.etNomorTelepon);
        btnBack = findViewById(R.id.btnBack);
        btnSimpan = findViewById(R.id.btnSimpan);

        // Ambil nomor lama dari intent (jika ada)
        String nomorLama = getIntent().getStringExtra("nomor_telepon_lama");
        if (nomorLama != null) {
            etNomorTelepon.setText(nomorLama);
        }

        // Tombol kembali
        btnBack.setOnClickListener(v -> finish());

        // Tombol simpan
        btnSimpan.setOnClickListener(v -> {
            String nomorBaru = etNomorTelepon.getText().toString().trim();
            if (nomorBaru.isEmpty()) {
                Toast.makeText(this, "Nomor telepon tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kirim hasil ke AccountActivity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("nomor_telepon_baru", nomorBaru);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });
    }
}
