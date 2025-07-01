package com.project.projectnew.profile;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.projectnew.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ListingTransactionActivity extends AppCompatActivity {

    private ImageView btnFilter, btnBack;
    private LinearLayout listTransaksi;
    private RelativeLayout filterOverlay;
    private EditText tanggalDariEditText, tanggalSampaiEditText;
    private Button btnLihat, btnBatal;
    private final Calendar calendarFrom = Calendar.getInstance();
    private final Calendar calendarTo = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_transaksi);

        btnFilter = findViewById(R.id.btnFilter);
        btnBack = findViewById(R.id.btnBack);
        listTransaksi = findViewById(R.id.listTransaksi);
        filterOverlay = findViewById(R.id.filterOverlay);
        tanggalDariEditText = findViewById(R.id.tanggalDari);
        tanggalSampaiEditText = findViewById(R.id.tanggalSampai);
        btnLihat = findViewById(R.id.btnLihat);
        btnBatal = findViewById(R.id.btnBatal);

        btnFilter.setOnClickListener(v -> filterOverlay.setVisibility(View.VISIBLE));
        btnBatal.setOnClickListener(v -> filterOverlay.setVisibility(View.GONE));
        btnLihat.setOnClickListener(v -> {
            Toast.makeText(this, "Filter diterapkan", Toast.LENGTH_SHORT).show();
            filterOverlay.setVisibility(View.GONE);
        });
        btnBack.setOnClickListener(v -> onBackPressed());

        tanggalDariEditText.setOnClickListener(v -> showDatePicker(calendarFrom, tanggalDariEditText));
        tanggalSampaiEditText.setOnClickListener(v -> showDatePicker(calendarTo, tanggalSampaiEditText));

        // Contoh isi daftar transaksi
        addTransaksiItem("306-2025-04-22-00004", "28 April 2025, 13.18.00");
        addTransaksiItem("306-2025-04-22-00003", "21 April 2025, 13.18.00");
        addTransaksiItem("306-2025-04-22-00002", "14 April 2025, 13.18.00");
        addTransaksiItem("306-2025-04-22-00001", "7 April 2025, 13.18.00");
    }

    private void showDatePicker(Calendar calendar, EditText target) {
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", new Locale("id", "ID"));
            target.setText(sdf.format(calendar.getTime()));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void addTransaksiItem(String noOrder, String tanggal) {
        View itemView = getLayoutInflater().inflate(R.layout.item_listing_transaksi, listTransaksi, false);

        TextView tvNoOrder = itemView.findViewById(R.id.tvNoOrder);
        TextView tvTanggal = itemView.findViewById(R.id.tvTanggal);
        tvNoOrder.setText("No Order : " + noOrder);
        tvTanggal.setText(tanggal);

        itemView.setOnClickListener(v ->
                Toast.makeText(this, "Detail " + noOrder, Toast.LENGTH_SHORT).show());

        listTransaksi.addView(itemView);
    }
}
