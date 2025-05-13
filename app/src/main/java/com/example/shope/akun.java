package com.example.shope;

/ ProfileActivity.java
// This is the Java activity file for handling the profile page

        package com.example.akun;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize all clickable containers
        LinearLayout namaPemilikContainer = findViewById(R.id.namaPemilikContainer);
        LinearLayout namaTokoContainer = findViewById(R.id.namaTokoContainer);
        LinearLayout nomorKTPContainer = findViewById(R.id.nomorKTPContainer);
        LinearLayout nomorTeleponContainer = findViewById(R.id.nomorTeleponContainer);
        LinearLayout emailContainer = findViewById(R.id.emailContainer);
        LinearLayout passwordContainer = findViewById(R.id.passwordContainer);
        LinearLayout alamatTokoContainer = findViewById(R.id.alamatTokoContainer);

        // Set click listeners for each container
        namaPemilikContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for editing owner name
                Toast.makeText(ProfileActivity.this, "Edit Nama Pemilik", Toast.LENGTH_SHORT).show();
                // You would typically start a new activity or show a dialog here
            }
        });

        namaTokoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for editing store name
                Toast.makeText(ProfileActivity.this, "Edit Nama Toko", Toast.LENGTH_SHORT).show();
            }
        });

        nomorKTPContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for editing KTP number
                Toast.makeText(ProfileActivity.this, "Edit Nomor KTP", Toast.LENGTH_SHORT).show();
            }
        });

        nomorTeleponContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for editing phone number
                Toast.makeText(ProfileActivity.this, "Edit Nomor Telepon", Toast.LENGTH_SHORT).show();
            }
        });

        emailContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for editing email
                Toast.makeText(ProfileActivity.this, "Edit Alamat Email", Toast.LENGTH_SHORT).show();
            }
        });

        passwordContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for editing password
                Toast.makeText(ProfileActivity.this, "Edit Password", Toast.LENGTH_SHORT).show();
            }
        });

        alamatTokoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for editing store address
                Toast.makeText(ProfileActivity.this, "Edit Alamat Toko", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
}