package com.project.projectnew.ordercreation;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton; // Import khusus untuk ImageButton
import com.project.projectnew.R;

public class KeranjangActivity extends AppCompatActivity {

    AppCompatImageButton btnBack; // Ganti tipe dari Button ke AppCompatImageButton

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);

        // Inisialisasi ImageButton
        btnBack = findViewById(R.id.btnBack);

        // Handle klik tombol
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Tutup activity
            }
        });

        // Versi singkat dengan lambda:
        // btnBack.setOnClickListener(v -> finish());
    }
}