package com.project.projectnew.profile;

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

import com.project.projectnew.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressFormActivity extends AppCompatActivity {

    private EditText edtAlamatToko, edtKodePos;
    private Spinner spinnerProvinsi, spinnerKota, spinnerKecamatan, spinnerKelurahan;
    private Button btnSimpan;
    private ImageButton btnBack;
    private ImageView mapImage;

    private Map<String, List<String>> kotaByProvinsi = new HashMap<>();
    private Map<String, List<String>> kecamatanByKota = new HashMap<>();
    private Map<String, List<String>> kelurahanByKecamatan = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_form);

        edtAlamatToko = findViewById(R.id.edtAlamatToko);
        edtKodePos = findViewById(R.id.edtKodePos);
        spinnerProvinsi = findViewById(R.id.spinnerProvinsi);
        spinnerKota = findViewById(R.id.spinnerKota);
        spinnerKecamatan = findViewById(R.id.spinnerKecamatan);
        spinnerKelurahan = findViewById(R.id.spinnerKelurahan);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnBack = findViewById(R.id.btnBack);
        mapImage = findViewById(R.id.mapImage);

        initializeSampleData();
        setupSpinners();

        btnBack.setOnClickListener(v -> onBackPressed());

        btnSimpan.setOnClickListener(v -> {
            if (validateForm()) {
                saveAddressData();
            }
        });

        mapImage.setOnClickListener(v -> {
            Toast.makeText(AddressFormActivity.this, "Map location selected", Toast.LENGTH_SHORT).show();
        });
    }

    private void initializeSampleData() {
        List<String> provinceList = new ArrayList<>();
        provinceList.add("Pilih Provinsi");
        provinceList.add("DKI Jakarta");
        provinceList.add("Jawa Barat");
        provinceList.add("Jawa Tengah");
        provinceList.add("Jawa Timur");

        List<String> jakartaCities = new ArrayList<>();
        jakartaCities.add("Pilih Kota");
        jakartaCities.add("Jakarta Pusat");
        jakartaCities.add("Jakarta Utara");
        jakartaCities.add("Jakarta Barat");
        jakartaCities.add("Jakarta Selatan");
        jakartaCities.add("Jakarta Timur");
        kotaByProvinsi.put("DKI Jakarta", jakartaCities);

        List<String> jakselKecamatan = new ArrayList<>();
        jakselKecamatan.add("Pilih Kecamatan");
        jakselKecamatan.add("Kebayoran Baru");
        jakselKecamatan.add("Pancoran");
        jakselKecamatan.add("Setiabudi");
        jakselKecamatan.add("Tebet");
        kecamatanByKota.put("Jakarta Selatan", jakselKecamatan);

        List<String> kebayoranBaruKelurahan = new ArrayList<>();
        kebayoranBaruKelurahan.add("Pilih Kelurahan");
        kebayoranBaruKelurahan.add("Gandaria Utara");
        kebayoranBaruKelurahan.add("Senayan");
        kebayoranBaruKelurahan.add("Kebayoran Baru");
        kebayoranBaruKelurahan.add("Gunung");
        kelurahanByKecamatan.put("Kebayoran Baru", kebayoranBaruKelurahan);

        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, provinceList);
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvinsi.setAdapter(provinceAdapter);
    }

    private void setupSpinners() {
        spinnerProvinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedProvince = parent.getItemAtPosition(position).toString();
                updateKotaSpinner(selectedProvince);
                updateKecamatanSpinner("Pilih Kota");
                updateKelurahanSpinner("Pilih Kecamatan");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerKota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedKota = parent.getItemAtPosition(position).toString();
                updateKecamatanSpinner(selectedKota);
                updateKelurahanSpinner("Pilih Kecamatan");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerKecamatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedKecamatan = parent.getItemAtPosition(position).toString();
                updateKelurahanSpinner(selectedKecamatan);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        setEmptyAdapter(spinnerKota, "Pilih Kota");
        setEmptyAdapter(spinnerKecamatan, "Pilih Kecamatan");
        setEmptyAdapter(spinnerKelurahan, "Pilih Kelurahan");
    }

    private void updateKotaSpinner(String province) {
        List<String> kotaList = kotaByProvinsi.get(province);
        if (kotaList != null) {
            ArrayAdapter<String> kotaAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, kotaList);
            kotaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerKota.setAdapter(kotaAdapter);
        } else {
            setEmptyAdapter(spinnerKota, "Pilih Kota");
        }
    }

    private void updateKecamatanSpinner(String kota) {
        List<String> kecamatanList = kecamatanByKota.get(kota);
        if (kecamatanList != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, kecamatanList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerKecamatan.setAdapter(adapter);
        } else {
            setEmptyAdapter(spinnerKecamatan, "Pilih Kecamatan");
        }
    }

    private void updateKelurahanSpinner(String kecamatan) {
        List<String> kelurahanList = kelurahanByKecamatan.get(kecamatan);
        if (kelurahanList != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, kelurahanList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerKelurahan.setAdapter(adapter);
        } else {
            setEmptyAdapter(spinnerKelurahan, "Pilih Kelurahan");
        }
    }

    private void setEmptyAdapter(Spinner spinner, String defaultItem) {
        List<String> emptyList = new ArrayList<>();
        emptyList.add(defaultItem);
        ArrayAdapter<String> emptyAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, emptyList);
        emptyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(emptyAdapter);
    }

    private boolean validateForm() {
        boolean valid = true;
        StringBuilder errorMsg = new StringBuilder();

        if (edtAlamatToko.getText().toString().trim().isEmpty()) {
            edtAlamatToko.setError("Alamat toko tidak boleh kosong");
            valid = false;
        }

        if (spinnerProvinsi.getSelectedItemPosition() == 0)
            errorMsg.append("Pilih provinsi\n");

        if (spinnerKota.getSelectedItemPosition() == 0)
            errorMsg.append("Pilih kota\n");

        if (spinnerKecamatan.getSelectedItemPosition() == 0)
            errorMsg.append("Pilih kecamatan\n");

        if (spinnerKelurahan.getSelectedItemPosition() == 0)
            errorMsg.append("Pilih kelurahan\n");

        String kodePos = edtKodePos.getText().toString().trim();
        if (kodePos.isEmpty()) {
            edtKodePos.setError("Kode pos tidak boleh kosong");
            valid = false;
        } else if (!kodePos.matches("\\d{5}")) {
            edtKodePos.setError("Kode pos harus 5 digit angka");
            valid = false;
        }

        if (errorMsg.length() > 0) {
            Toast.makeText(this, errorMsg.toString().trim(), Toast.LENGTH_LONG).show();
            valid = false;
        }

        return valid;
    }

    private void saveAddressData() {
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
