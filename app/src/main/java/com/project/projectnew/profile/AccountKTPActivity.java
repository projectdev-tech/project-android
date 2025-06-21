package com.project.projectnew.profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.project.projectnew.R;

public class AccountKTPActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 300;

    private EditText etKtpNumber;
    private ImageView ivKtpPhoto, btnBack;
    private Button btnAmbilFoto;
    private MaterialButton btnSimpan;

    private Bitmap capturedKtpBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_ktp);

        etKtpNumber = findViewById(R.id.et_ktp_number);
        ivKtpPhoto = findViewById(R.id.iv_ktp_photo);
        btnAmbilFoto = findViewById(R.id.btn_ambil_foto);
        btnBack = findViewById(R.id.btnBack);
        btnSimpan = findViewById(R.id.btn_simpan);

        // Isi data jika dikirim sebelumnya
        String nomorKtp = getIntent().getStringExtra("nomor_ktp");
        if (nomorKtp != null) {
            etKtpNumber.setText(nomorKtp);
        }

        // Tombol kembali
        btnBack.setOnClickListener(v -> finish());

        // Ambil foto
        btnAmbilFoto.setOnClickListener(v -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        });

        // Simpan data dan kembali
        btnSimpan.setOnClickListener(v -> {
            String ktpNumber = etKtpNumber.getText().toString().trim();

            if (ktpNumber.isEmpty()) {
                Toast.makeText(this, "Nomor KTP tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent resultIntent = new Intent();
            resultIntent.putExtra("ktp_number_baru", ktpNumber);
            // Tambahkan bitmap jika dibutuhkan (misalnya di SharedPreferences / Upload API)
            // Untuk contoh ini, kita hanya kirim nomor
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            capturedKtpBitmap = (Bitmap) data.getExtras().get("data");
            ivKtpPhoto.setImageBitmap(capturedKtpBitmap);
        }
    }
}
