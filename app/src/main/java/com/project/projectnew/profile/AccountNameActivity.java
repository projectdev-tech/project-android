package com.project.projectnew.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.project.projectnew.R;

public class AccountNameActivity extends AppCompatActivity {

    private EditText etNamaPemilik;
    private MaterialButton btnSimpan;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_name); // Pastikan ini sesuai nama XML

        // Inisialisasi view
        etNamaPemilik = findViewById(R.id.etNamaPemilik);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnBack = findViewById(R.id.btnBack);

        // Ambil nama lama yang dikirim dari AccountActivity
        String namaLama = getIntent().getStringExtra("nama_lama");
        if (namaLama != null) {
            etNamaPemilik.setText(namaLama);
        }

        // Tombol kembali
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Tutup activity, kembali ke sebelumnya
            }
        });

        // Tombol simpan
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaBaru = etNamaPemilik.getText().toString().trim();

                if (namaBaru.isEmpty()) {
                    Toast.makeText(AccountNameActivity.this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else {
                    // Kirim hasil kembali ke AccountActivity
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("nama_baru", namaBaru);
                    setResult(RESULT_OK, resultIntent);
                    finish(); // Tutup dan kembali ke AccountActivity
                }
            }
        });
    }
}
