package com.example.shope;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class Nomer_Telepon_Activity extends AppCompatActivity {

    private EditText etNamaPemilik;
    private MaterialButton btnSimpan;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nomer_telepon); // Pastikan nama file XML-nya sesuai

        // Inisialisasi komponen UI
        etNamaPemilik = findViewById(R.id.etNamaPemilik);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnBack = findViewById(R.id.btnBack);

        // Aksi tombol Simpan
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomorTelepon = etNamaPemilik.getText().toString().trim();
                Toast.makeText(Nomer_Telepon_Activity.this, "Nomor disimpan: " + nomorTelepon, Toast.LENGTH_SHORT).show();
                // Tambahkan logika simpan jika diperlukan
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


