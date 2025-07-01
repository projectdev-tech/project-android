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
    private LinearLayout accountSection;
    private LinearLayout listingTransactionSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Inisialisasi komponen UI
        namaTokoTextView = findViewById(R.id.namaToko);
        namaPemilikTextView = findViewById(R.id.namaPemilik);
        kreditPointTextView = findViewById(R.id.kreditPointText);
        kuponTextView = findViewById(R.id.kuponText);
        logoutSection = findViewById(R.id.logoutSection);
        accountSection = findViewById(R.id.accountSection);
        listingTransactionSection = findViewById(R.id.listingTransactionSection);

        // Dummy data
        String namaToko = "Toko Sumber Agung";
        String namaPemilik = "Agung Mulyana";
        int kreditPoint = 1000;
        int jumlahKupon = 10;

        namaTokoTextView.setText(namaToko);
        namaPemilikTextView.setText(namaPemilik);
        kreditPointTextView.setText(kreditPoint + " Point");
        kuponTextView.setText(jumlahKupon + " Kupon");

        // Navigasi ke Akun
        accountSection.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, AccountActivity.class));
        });

        // Navigasi ke Daftar Transaksi
        listingTransactionSection.setOnClickListener(v -> {
            try {
                startActivity(new Intent(ProfileActivity.this, ListingTransactionActivity.class));
            } catch (Exception e) {
                Toast.makeText(ProfileActivity.this, "Gagal membuka transaksi: " + e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });

        // Logout
        logoutSection.setOnClickListener(v -> {
            Toast.makeText(ProfileActivity.this, "Logout berhasil", Toast.LENGTH_SHORT).show();
            // Tambahkan logika logout jika dibutuhkan
        });
    }
}
