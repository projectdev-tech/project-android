package com.project.projectnew.profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.projectnew.R;

public class AccountActivity extends AppCompatActivity {

    private static final int REQUEST_EDIT_NAME = 100;
    private static final int REQUEST_EDIT_SHOP = 200;
    private static final int REQUEST_EDIT_KTP = 300;
    private static final int REQUEST_EDIT_PHONE = 400;
    private static final int REQUEST_EDIT_EMAIL = 500;

    private LinearLayout nameSection, namaTokoSection, ktpSection, phoneSection, emailSection, passwordSection, addressSection;
    private TextView namaPemilikValue, namaTokoValue, ktpValue, phoneValue, emailValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Inisialisasi elemen layout
        nameSection = findViewById(R.id.nameSection);
        namaTokoSection = findViewById(R.id.namaSection);
        ktpSection = findViewById(R.id.ktpSection);
        phoneSection = findViewById(R.id.phoneSection);
        emailSection = findViewById(R.id.emailSection);
        passwordSection = findViewById(R.id.passwordSection);
        addressSection = findViewById(R.id.addressSection);

        namaPemilikValue = findViewById(R.id.namaPemilikValue);
        namaTokoValue = findViewById(R.id.namaTokoValue);
        ktpValue = findViewById(R.id.ktpValue);
        phoneValue = findViewById(R.id.phoneValue);
        emailValue = findViewById(R.id.emailValue);

        // Event klik Nama Pemilik
        nameSection.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, AccountNameActivity.class);
            intent.putExtra("nama_lama", namaPemilikValue.getText().toString());
            startActivityForResult(intent, REQUEST_EDIT_NAME);
        });

        // Event klik Nama Toko
        namaTokoSection.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, AccountShopActivity.class);
            intent.putExtra("nama_toko_lama", namaTokoValue.getText().toString());
            startActivityForResult(intent, REQUEST_EDIT_SHOP);
        });

        // Event klik KTP
        ktpSection.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, AccountKTPActivity.class);
            intent.putExtra("nomor_ktp", ktpValue.getText().toString());
            startActivityForResult(intent, REQUEST_EDIT_KTP);
        });

        // Event klik Nomor Telepon
        phoneSection.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, AccountNumberActivity.class);
            intent.putExtra("nomor_telepon_lama", phoneValue.getText().toString());
            startActivityForResult(intent, REQUEST_EDIT_PHONE);
        });

        // Event klik Email
        emailSection.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, AccountEmailActivity.class);
            intent.putExtra("email_lama", emailValue.getText().toString());
            startActivityForResult(intent, REQUEST_EDIT_EMAIL);
        });

        // Event klik Ubah Password
        passwordSection.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, AccountPasswordActivity.class);
            startActivity(intent); // tidak butuh result jika tidak ada data dikembalikan
        });

        addressSection.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, AddressFormActivity.class);
            startActivity(intent); // tidak butuh result jika tidak ada data dikembalikan
        });

        // Tombol kembali
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> onBackPressed());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case REQUEST_EDIT_NAME:
                    String namaBaru = data.getStringExtra("nama_baru");
                    if (namaBaru != null && !namaBaru.isEmpty()) {
                        namaPemilikValue.setText(namaBaru);
                        showToast("Nama pemilik diperbarui");
                    }
                    break;

                case REQUEST_EDIT_SHOP:
                    String tokoBaru = data.getStringExtra("nama_toko_baru");
                    if (tokoBaru != null && !tokoBaru.isEmpty()) {
                        namaTokoValue.setText(tokoBaru);
                        showToast("Nama toko diperbarui");
                    }
                    break;

                case REQUEST_EDIT_KTP:
                    String ktpBaru = data.getStringExtra("ktp_number_baru");
                    if (ktpBaru != null && !ktpBaru.isEmpty()) {
                        ktpValue.setText(ktpBaru);
                        showToast("Nomor KTP diperbarui");
                    }
                    break;

                case REQUEST_EDIT_PHONE:
                    String nomorBaru = data.getStringExtra("nomor_telepon_baru");
                    if (nomorBaru != null && !nomorBaru.isEmpty()) {
                        phoneValue.setText(nomorBaru);
                        showToast("Nomor telepon diperbarui");
                    }
                    break;

                case REQUEST_EDIT_EMAIL:
                    String emailBaru = data.getStringExtra("email_baru");
                    if (emailBaru != null && !emailBaru.isEmpty()) {
                        emailValue.setText(emailBaru);
                        showToast("Email diperbarui");
                    }
                    break;
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(AccountActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
