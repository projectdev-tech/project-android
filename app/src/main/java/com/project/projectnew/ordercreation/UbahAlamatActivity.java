package com.project.projectnew.ordercreation;

// --- PASTIKAN SEMUA IMPORT INI ADA ---
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.project.projectnew.R; // <-- Import ini yang paling penting!

public class UbahAlamatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_alamat);

        // Inisialisasi Views
        ImageButton btnBack = findViewById(R.id.btnBack);
        Button btnSimpan = findViewById(R.id.btnSimpan);
        EditText etAlamatJalan = findViewById(R.id.etAlamatJalan);
        Spinner spinnerProvinsi = findViewById(R.id.spinnerProvinsi);
        Spinner spinnerKota = findViewById(R.id.spinnerKota);
        Spinner spinnerKecamatan = findViewById(R.id.spinnerKecamatan);
        Spinner spinnerKelurahan = findViewById(R.id.spinnerKelurahan);

        // Ambil data alamat saat ini (jika ada) dan tampilkan
        String currentAddress = getIntent().getStringExtra("CURRENT_VALUE");
        if (currentAddress != null) {
            etAlamatJalan.setText(currentAddress);
        }

        // Setup Spinner Provinsi
        ArrayAdapter<CharSequence> provinsiAdapter = ArrayAdapter.createFromResource(this,
                R.array.provinsi_array, android.R.layout.simple_spinner_item);
        provinsiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvinsi.setAdapter(provinsiAdapter);

        // Setup Spinner lain dengan data placeholder
        ArrayAdapter<String> placeholderAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new String[]{"Pilih..."});
        placeholderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKota.setAdapter(placeholderAdapter);
        spinnerKecamatan.setAdapter(placeholderAdapter);
        spinnerKelurahan.setAdapter(placeholderAdapter);

        // Atur listeners
        btnBack.setOnClickListener(v -> finish());
        btnSimpan.setOnClickListener(v -> {
            Toast.makeText(this, "Alamat berhasil disimpan", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}