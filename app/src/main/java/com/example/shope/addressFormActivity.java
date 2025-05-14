package com.example.storeapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class addressFormActivity extends AppCompatActivity {

    private EditText edtAlamatToko, edtKodePos;
    private Spinner spinnerProvinsi, spinnerKota, spinnerKecamatan, spinnerKelurahan;
    private Button btnSimpan;
    private ImageButton btnBack;
    private ImageView mapImage;

    // Sample data - in a real app, you would fetch these from an API
    private Map<String, List<String>> kotaByProvinsi = new HashMap<>();
    private Map<String, List<String>> kecamatanByKota = new HashMap<>();
    private Map<String, List<String>> kelurahanByKecamatan = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_form);

        // Initialize views
        edtAlamatToko = findViewById(R.id.edtAlamatToko);
        edtKodePos = findViewById(R.id.edtKodePos);
        spinnerProvinsi = findViewById(R.id.spinnerProvinsi);
        spinnerKota = findViewById(R.id.spinnerKota);
        spinnerKecamatan = findViewById(R.id.spinnerKecamatan);
        spinnerKelurahan = findViewById(R.id.spinnerKelurahan);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnBack = findViewById(R.id.btnBack);
        mapImage = findViewById(R.id.mapImage);

        // Initialize sample data
        initializeSampleData();

        // Setup spinners
        setupSpinners();

        // Setup button click listeners
        btnBack.setOnClickListener(v -> onBackPressed());

        btnSimpan.setOnClickListener(v -> {
            if (validateForm()) {
                // Save address data
                saveAddressData();
            }
        });

        // Set click listener on the map image
        mapImage.setOnClickListener(v -> {
            Toast.makeText(com.example.storeapp.addressFormActivity.this, "Map location selected", Toast.LENGTH_SHORT).show();
        });
    }

    private void initializeSampleData() {
        // Sample provinces
        List<String> provinceList = new ArrayList<>();
        provinceList.add("Pilih Provinsi");
        provinceList.add("DKI Jakarta");
        provinceList.add("Jawa Barat");
        provinceList.add("Jawa Tengah");
        provinceList.add("Jawa Timur");

        // Sample cities for DKI Jakarta
        List<String> jakartaCities = new ArrayList<>();
        jakartaCities.add("Pilih Kota");
        jakartaCities.add("Jakarta Pusat");
        jakartaCities.add("Jakarta Utara");
        jakartaCities.add("Jakarta Barat");
        jakartaCities.add("Jakarta Selatan");
        jakartaCities.add("Jakarta Timur");
        kotaByProvinsi.put("DKI Jakarta", jakartaCities);

        // Sample kecamatan for Jakarta Selatan
        List<String> jakselKecamatan = new ArrayList<>();
        jakselKecamatan.add("Pilih Kecamatan");
        jakselKecamatan.add("Kebayoran Baru");
        jakselKecamatan.add("Pancoran");
        jakselKecamatan.add("Setiabudi");
        jakselKecamatan.add("Tebet");
        kecamatanByKota.put("Jakarta Selatan", jakselKecamatan);

        // Sample kelurahan for Kebayoran Baru
        List<String> kebayoranBaruKelurahan = new ArrayList<>();
        kebayoranBaruKelurahan.add("Pilih Kelurahan");
        kebayoranBaruKelurahan.add("Gandaria Utara");
        kebayoranBaruKelurahan.add("Senayan");
        kebayoranBaruKelurahan.add("Kebayoran Baru");
        kebayoranBaruKelurahan.add("Gunung");
        kelurahanByKecamatan.put("Kebayoran Baru", kebayoranBaruKelurahan);

        // Add sample data for other provinces, cities, etc.
        // ...

        // Setup province spinner with initial data
        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, provinceList);
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvinsi.setAdapter(provinceAdapter);
    }

    private void setupSpinners() {
        // Province spinner listener
        spinnerProvinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedProvince = parent.getItemAtPosition(position).toString();
                updateKotaSpinner(selectedProvince);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Kota spinner listener
        spinnerKota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedKota = parent.getItemAtPosition(position).toString();
                updateKecamatanSpinner(selectedKota);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Kecamatan spinner listener
        spinnerKecamatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedKecamatan = parent.getItemAtPosition(position).toString();
                updateKelurahanSpinner(selectedKecamatan);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Initialize empty adapters for the dependent spinners
        List<String> emptyList = new ArrayList<>();
        emptyList.add("Pilih Kota");
        ArrayAdapter<String> emptyAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, emptyList);
        emptyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKota.setAdapter(emptyAdapter);

        emptyList = new ArrayList<>();
        emptyList.add("Pilih Kecamatan");
        emptyAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, emptyList);
        emptyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKecamatan.setAdapter(emptyAdapter);

        emptyList = new ArrayList<>();
        emptyList.add("Pilih Kelurahan");
        emptyAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, emptyList);
        emptyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKelurahan.setAdapter(emptyAdapter);
    }

    private void updateKotaSpinner(String province) {
        if (province.equals("Pilih Provinsi")) {
            List<String> emptyList = new ArrayList<>();
            emptyList.add("Pilih Kota");
            ArrayAdapter<String> emptyAdapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, emptyList);
            emptyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerKota.setAdapter(emptyAdapter);
            return;
        }

        List<String> kotaList = kotaByProvinsi.get(province);
        if (kotaList != null) {
            ArrayAdapter<String> kotaAdapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, kotaList);
            kotaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerKota.setAdapter(kotaAdapter);
        }
    }

    private void updateKecamatanSpinner(String kota) {
        if (kota.equals("Pilih Kota")) {
            List<String> emptyList = new ArrayList<>();
            emptyList.add("Pilih Kecamatan");
            ArrayAdapter<String> emptyAdapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, emptyList);
            emptyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerKecamatan.setAdapter(emptyAdapter);
            return;
        }

        List<String> kecamatanList = kecamatanByKota.get(kota);
        if (kecamatanList != null) {
            ArrayAdapter<String> kecamatanAdapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, kecamatanList);
            kecamatanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerKecamatan.setAdapter(kecamatanAdapter);
        }
    }

    private void updateKelurahanSpinner(String kecamatan) {
        if (kecamatan.equals("Pilih Kecamatan")) {
            List<String> emptyList = new ArrayList<>();
            emptyList.add("Pilih Kelurahan");
            ArrayAdapter<String> emptyAdapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, emptyList);
            emptyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerKelurahan.setAdapter(emptyAdapter);
            return;
        }

        List<String> kelurahanList = kelurahanByKecamatan.get(kecamatan);
        if (kelurahanList != null) {
            ArrayAdapter<String> kelurahanAdapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, kelurahanList);
            kelurahanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerKelurahan.setAdapter(kelurahanAdapter);
        }
    }

    private boolean validateForm() {
        boolean valid = true;

        // Validate alamat toko
        if (edtAlamatToko.getText().toString().trim().isEmpty()) {
            edtAlamatToko.setError("Alamat toko tidak boleh kosong");
            valid = false;
        }

        // Validate province selection
        if (spinnerProvinsi.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Silakan pilih provinsi", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        // Validate kota selection
        if (spinnerKota.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Silakan pilih kota", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        // Validate kecamatan selection
        if (spinnerKecamatan.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Silakan pilih kecamatan", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        // Validate kelurahan selection
        if (spinnerKelurahan.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Silakan pilih kelurahan", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        // Validate kode pos
        if (edtKodePos.getText().toString().trim().isEmpty()) {
            edtKodePos.setError("Kode pos tidak boleh kosong");
            valid = false;
        }

        return valid;
    }

    private void saveAddressData() {
        // Here you would typically save to your database or make an API call
        // For demo purposes, just show a success message
        String alamat = edtAlamatToko.getText().toString();
        String provinsi = spinnerProvinsi.getSelectedItem().toString();
        String kota = spinnerKota.getSelectedItem().toString();
        String kecamatan = spinnerKecamatan.getSelectedItem().toString();
        String kelurahan = spinnerKelurahan.getSelectedItem().toString();
        String kodePos = edtKodePos.getText().toString();

        Toast.makeText(this, "Alamat toko berhasil disimpan", Toast.LENGTH_SHORT).show();
        finish();
    }
}