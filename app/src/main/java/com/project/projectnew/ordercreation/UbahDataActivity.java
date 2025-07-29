package com.project.projectnew.ordercreation;

import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.project.projectnew.R;

public class UbahDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_data);

        ImageButton btnBack = findViewById(R.id.btnBack);
        Button btnSimpan = findViewById(R.id.btnSimpan);
        TextView tvToolbarTitle = findViewById(R.id.tvToolbarTitle);
        TextView tvLabel = findViewById(R.id.tvLabel);
        EditText etValue = findViewById(R.id.etValue);

        // Ambil data yang dikirim dari DetailAkunActivity
        String fieldType = getIntent().getStringExtra("FIELD_TYPE");
        String currentValue = getIntent().getStringExtra("CURRENT_VALUE");

        // Atur tampilan berdasarkan tipe field yang akan diubah
        if (fieldType != null) {
            tvToolbarTitle.setText(fieldType);
            tvLabel.setText(fieldType);
            etValue.setText(currentValue);
            etValue.setHint("Masukkan " + fieldType.toLowerCase());

            // Sesuaikan tipe keyboard
            if (fieldType.equals("Nomor Telepon")) {
                etValue.setInputType(InputType.TYPE_CLASS_PHONE);
            } else if (fieldType.equals("Alamat Email")) {
                etValue.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            } else {
                etValue.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
            }
        }

        btnBack.setOnClickListener(v -> finish());
        btnSimpan.setOnClickListener(v -> {
            Toast.makeText(this, "Perubahan disimpan", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}