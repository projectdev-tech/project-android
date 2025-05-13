package com.example.shope;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

class NamaTokoActivity extends AppCompatActivity {

    private EditText etNamaPemilik;
    private MaterialButton btnSimpan;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nama_toko); // ganti sesuai nama file layout kamu

        // Inisialisasi view
        etNamaPemilik = findViewById(R.id.etNamaPemilik);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnBack = findViewById(R.id.btnBack);

        // Aksi tombol simpan
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaToko = etNamaPemilik.getText().toString().trim();
                Toast.makeText(NamaTokoActivity.this, "Tersimpan: " + namaToko, Toast.LENGTH_SHORT).show();
                // Tambahkan aksi simpan ke server/database jika perlu
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
}
