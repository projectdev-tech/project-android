package com.project.projectnew.ordercreation;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.project.projectnew.R;

public class UbahPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_password);

        ImageButton btnBack = findViewById(R.id.btnBack);
        Button btnSimpan = findViewById(R.id.btnSimpan);

        btnBack.setOnClickListener(v -> finish());

        btnSimpan.setOnClickListener(v -> {
            // Logika validasi dan penyimpanan bisa ditambahkan di sini nanti
            Toast.makeText(this, "Password berhasil diubah", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}