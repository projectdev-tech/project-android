package com.example.shope;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class AlamatEmailActivity extends AppCompatActivity {

    private EditText etNamaPemilik;
    private MaterialButton btnSimpan;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alamat_email); // Pastikan nama layout XML sesuai

        // Inisialisasi komponen UI
        etNamaPemilik = findViewById(R.id.etNamaPemilik);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnBack = findViewById(R.id.btnBack);

        // Aksi tombol Simpan
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etNamaPemilik.getText().toString().trim();
                Toast.makeText(AlamatEmailActivity.this, "Email disimpan: " + email, Toast.LENGTH_SHORT).show();
                // Tambahkan logika penyimpanan jika diperlukan
            }
        });

        // Aksi tombol kembali
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // kembali ke activity sebelumnya
            }
        });
    }
