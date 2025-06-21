package com.project.projectnew.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.projectnew.R;

public class ProfileActivity extends AppCompatActivity {

    private TextView namaTokoTextView;
    private TextView namaPemilikTextView;
    private TextView kreditPointTextView;
    private TextView kuponTextView;

    private LinearLayout logoutSection;
    private LinearLayout accountSection; // Tombol "Akun"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile); // Pastikan file XML ini sesuai

        // Inisialisasi view berdasarkan ID yang ada di XML
        namaTokoTextView = findViewById(R.id.namaToko);
        namaPemilikTextView = findViewById(R.id.namaPemilik);
        kreditPointTextView = findViewById(R.id.kreditPointText);
        kuponTextView = findViewById(R.id.kuponText);
        logoutSection = findViewById(R.id.logoutSection);
        accountSection = findViewById(R.id.accountSection); // Inisialisasi tombol "Akun"

        // Dummy data (ganti dengan data nyata dari API, database, atau SharedPreferences)
        String namaToko = "Toko Sumber Agung";
        String namaPemilik = "Agung Mulyana";
        int kreditPoint = 1000;
        int jumlahKupon = 10;

        // Tampilkan data ke tampilan
        namaTokoTextView.setText(namaToko);
        namaPemilikTextView.setText(namaPemilik);
        kreditPointTextView.setText(kreditPoint + " Point");
        kuponTextView.setText(jumlahKupon + " Kupon");

        // Event klik untuk tombol "Akun"
        accountSection.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, AccountActivity.class);
            startActivity(intent);
        });

        // Event klik untuk tombol logout
        logoutSection.setOnClickListener(v -> {
            Toast.makeText(ProfileActivity.this, "Logout berhasil", Toast.LENGTH_SHORT).show();
            // Tambahkan logika logout (misalnya: clear SharedPreferences, redirect ke login, dll)
        });
    }
}
