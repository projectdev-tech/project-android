package com.project.projectnew.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.project.projectnew.R;

public class AccountEmailActivity extends AppCompatActivity {

    private EditText etEmail;
    private ImageView btnBack;
    private MaterialButton btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_email); // Sesuaikan dengan nama file XML Anda

        // Inisialisasi view
        etEmail = findViewById(R.id.etNamaPemilik);
        btnBack = findViewById(R.id.btnBack);
        btnSimpan = findViewById(R.id.btnSimpan);

        // Ambil email lama dari intent
        String emailLama = getIntent().getStringExtra("email_lama");
        if (emailLama != null) {
            etEmail.setText(emailLama);
        }

        // Tombol kembali
        btnBack.setOnClickListener(v -> finish());

        // Tombol simpan
        btnSimpan.setOnClickListener(v -> {
            String emailBaru = etEmail.getText().toString().trim();
            if (emailBaru.isEmpty()) {
                Toast.makeText(this, "Email tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kirim hasil ke AccountActivity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("email_baru", emailBaru);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });
    }
}
