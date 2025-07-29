package com.project.projectnew.ordercreation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.project.projectnew.R;

public class DetailAkunActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_akun);

        TextView tvNamaPemilikValue = findViewById(R.id.tvNamaPemilikValue);
        TextView tvNamaTokoValue = findViewById(R.id.tvNamaTokoValue);
        TextView tvNomorKtpValue = findViewById(R.id.tvNomorKtpValue);
        TextView tvNomorTeleponValue = findViewById(R.id.tvNomorTeleponValue);
        TextView tvAlamatEmailValue = findViewById(R.id.tvAlamatEmailValue);
        TextView tvAlamatTokoValue = findViewById(R.id.tvAlamatTokoValue);

        findViewById(R.id.layoutNamaPemilik).setOnClickListener(v ->
                openUbahDataActivity("Nama Pemilik", tvNamaPemilikValue.getText().toString()));

        findViewById(R.id.layoutNamaToko).setOnClickListener(v ->
                openUbahDataActivity("Nama Toko", tvNamaTokoValue.getText().toString()));

        findViewById(R.id.layoutNomorKtp).setOnClickListener(v -> {
            Intent intent = new Intent(this, UbahKtpActivity.class);
            intent.putExtra("CURRENT_VALUE", tvNomorKtpValue.getText().toString());
            startActivity(intent);
        });

        findViewById(R.id.layoutNomorTelepon).setOnClickListener(v ->
                openUbahDataActivity("Nomor Telepon", tvNomorTeleponValue.getText().toString()));

        findViewById(R.id.layoutAlamatEmail).setOnClickListener(v ->
                openUbahDataActivity("Alamat Email", tvAlamatEmailValue.getText().toString()));

        findViewById(R.id.layoutPassword).setOnClickListener(v -> {
            Intent intent = new Intent(this, UbahPasswordActivity.class);
            startActivity(intent);
        });

        // --- PERUBAHAN UTAMA DI SINI ---
        findViewById(R.id.layoutAlamatToko).setOnClickListener(v -> {
            Intent intent = new Intent(this, UbahAlamatActivity.class);
            intent.putExtra("CURRENT_VALUE", tvAlamatTokoValue.getText().toString());
            startActivity(intent);
        });

        findViewById(R.id.tvChangeProfilePicture).setOnClickListener(v -> showToast("Ubah Foto Profil"));
    }

    private void openUbahDataActivity(String fieldType, String currentValue) {
        Intent intent = new Intent(this, UbahDataActivity.class);
        intent.putExtra("FIELD_TYPE", fieldType);
        intent.putExtra("CURRENT_VALUE", currentValue);
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}