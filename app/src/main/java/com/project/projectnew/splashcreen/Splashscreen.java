package com.project.projectnew.splashcreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.project.projectnew.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.project.projectnew.productcatalog.MainActivity;
import com.project.projectnew.R;

public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        Button buttonMasuk = findViewById(R.id.button_masuk);
        buttonMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pindah ke MainActivity
                Intent intent = new Intent(Splashscreen.this, MainActivity.class);
                startActivity(intent);
                finish(); // Tutup Splashscreen agar tidak bisa kembali ke sini
            }
        });
    }
}
