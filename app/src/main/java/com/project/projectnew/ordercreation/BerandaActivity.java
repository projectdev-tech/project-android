package com.project.projectnew.ordercreation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;  // ganti import Button jadi LinearLayout
import androidx.appcompat.app.AppCompatActivity;
import com.project.projectnew.R;

public class BerandaActivity extends AppCompatActivity {

    LinearLayout btnPesanan;  // ganti Button jadi LinearLayout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);

        // Hubungkan LinearLayout dengan ID dari layout XML
        btnPesanan = findViewById(R.id.btnPesanan);

        // Klik untuk pindah ke KeranjangActivity
        btnPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BerandaActivity.this, KeranjangActivity.class);
                startActivity(intent);
            }
        });
    }
}
