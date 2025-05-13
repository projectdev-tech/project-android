package com.example.shope;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class EditNamaPemilikActivity extends AppCompatActivity {

    private ImageView btnBack;
    private EditText etNamaPemilik;
    private MaterialButton btnSimpan;

    private static final String PREF_NAME = "UserData";
    private static final String KEY_NAMA_PEMILIK = "NAMA_PEMILIK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.namapemilik);

        btnBack = findViewById(R.id.btnBack);
        etNamaPemilik = findViewById(R.id.etNamaPemilik);
        btnSimpan = findViewById(R.id.btnSimpan);

        loadSavedData();

        btnBack.setOnClickListener(v -> finish());

        btnSimpan.setOnClickListener(v -> saveData());
    }

    private void loadSavedData() {
        SharedPreferences sharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String savedName = sharedPref.getString(KEY_NAMA_PEMILIK, "");
        etNamaPemilik.setText(savedName);
    }

    private void saveData() {
        String namaPemilik = etNamaPemilik.getText().toString().trim();

        if (namaPemilik.isEmpty()) {
            etNamaPemilik.setError("Nama pemilik tidak boleh kosong");
            return;
        }

        SharedPreferences sharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_NAMA_PEMILIK, namaPemilik);

        if (editor.commit()) {
            Toast.makeText(this, "Nama pemilik berhasil disimpan", Toast.LENGTH_SHORT).show();
            finish(); // Tutup aktivitas setelah simpan
        } else {
            Toast.makeText(this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
        }
    }
}
