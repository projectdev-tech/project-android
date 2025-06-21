package com.project.projectnew.profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.projectnew.R;

public class AccountAddressActivity extends AppCompatActivity {

    private EditText edtAlamatToko, edtKodePos;
    private Spinner spinnerProvinsi, spinnerKota, spinnerKecamatan, spinnerKelurahan;
    private Button btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_address);

        edtAlamatToko = findViewById(R.id.edtAlamatToko);
        edtKodePos = findViewById(R.id.edtKodePos);
        spinnerProvinsi = findViewById(R.id.spinnerProvinsi);
        spinnerKota = findViewById(R.id.spinnerKota);
        spinnerKecamatan = findViewById(R.id.spinnerKecamatan);
        spinnerKelurahan = findViewById(R.id.spinnerKelurahan);
        btnSimpan = findViewById(R.id.btnSimpan);
        ImageButton btnBack = findViewById(R.id.btnBack);

        setupSpinner(spinnerProvinsi, new String[]{"Pilih Provinsi", "Jawa Barat", "DKI Jakarta", "Jawa Timur"});
        setupSpinner(spinnerKota, new String[]{"Pilih Kota", "Bandung", "Jakarta", "Surabaya"});
        setupSpinner(spinnerKecamatan, new String[]{"Pilih Kecamatan", "Coblong", "Cipayung", "Wonokromo"});
        setupSpinner(spinnerKelurahan, new String[]{"Pilih Kelurahan", "Dago", "Pondok Gede", "Ketintang"});

        btnBack.setOnClickListener(v -> onBackPressed());

        btnSimpan.setOnClickListener(v -> {
            String alamat = edtAlamatToko.getText().toString().trim();
            if (alamat.isEmpty()) {
                Toast.makeText(this, "Alamat toko tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent resultIntent = new Intent();
            resultIntent.putExtra("alamat_toko_baru", alamat);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    private void setupSpinner(Spinner spinner, String[] items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
    }
}
