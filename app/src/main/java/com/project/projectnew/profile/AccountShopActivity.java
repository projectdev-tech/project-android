package com.project.projectnew.profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.project.projectnew.R;

public class AccountShopActivity extends AppCompatActivity {

    private EditText etNamaToko;
    private ImageView btnBack;
    private MaterialButton btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_shop);

        etNamaToko = findViewById(R.id.etNamaToko);
        btnBack = findViewById(R.id.btnBack);
        btnSimpan = findViewById(R.id.btnSimpan);

        // Terima data dari activity sebelumnya
        String namaLama = getIntent().getStringExtra("nama_toko_lama");
        if (namaLama != null) {
            etNamaToko.setText(namaLama);
        }

        btnBack.setOnClickListener(v -> finish());

        btnSimpan.setOnClickListener(v -> {
            String namaBaru = etNamaToko.getText().toString().trim();

            if (namaBaru.isEmpty()) {
                Toast.makeText(this, "Nama toko tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else {
                Intent result = new Intent();
                result.putExtra("nama_toko_baru", namaBaru);
                setResult(RESULT_OK, result);
                finish();
            }
        });
    }
}
