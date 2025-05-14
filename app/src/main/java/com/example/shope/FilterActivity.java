package com.example.shope;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FilterActivity extends AppCompatActivity {

    private Calendar calendarDari = Calendar.getInstance();
    private Calendar calendarSampai = Calendar.getInstance();
    private SimpleDateFormat dateFormatter;
    private TextView tanggalDariText, tanggalSampaiText;
    private AlertDialog filterDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize date formatter with Indonesian locale
        dateFormatter = new SimpleDateFormat("dd MMMM yyyy", new Locale("id", "ID"));

        // Show filter dialog when the activity starts
        showFilterDialog();
    }

    private void showFilterDialog() {
        // Create dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Inflate the custom dialog layout
        View dialogView = LayoutInflater.from(this).inflate(R.layout.activity_transaksi, null);
        builder.setView(dialogView);

        // Prevent dialog from closing when clicking outside
        builder.setCancelable(false);

        // Create the dialog
        filterDialog = builder.create();

        // Initialize dialog views
        tanggalDariText = dialogView.findViewById(R.id.tanggalDariText);
        tanggalSampaiText = dialogView.findViewById(R.id.tanggalSampaiText);
        View tanggalDariLayout = dialogView.findViewById(R.id.tanggalDariLayout);
        View tanggalSampaiLayout = dialogView.findViewById(R.id.tanggalSampaiLayout);
        Button batalButton = dialogView.findViewById(R.id.batalButton);
        Button lihatButton = dialogView.findViewById(R.id.lihatButton);

        // Set click listeners for date fields
        tanggalDariLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(tanggalDariText, calendarDari);
            }
        });

        tanggalSampaiLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(tanggalSampaiText, calendarSampai);
            }
        });

        // Set click listeners for buttons
        batalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the dialog when "Batal" is clicked
                filterDialog.dismiss();
                // Close the activity as well
                finish();
            }
        });

        lihatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Process filter (just show a toast for this example)
                applyFilter();
                filterDialog.dismiss();
                finish();
            }
        });

        // Show the dialog
        filterDialog.show();
    }

    private void showDatePicker(final TextView textView, final Calendar calendar) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    textView.setText(dateFormatter.format(calendar.getTime()));
                    // Change text color when date is selected
                    textView.setTextColor(getResources().getColor(R.color.black));
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void applyFilter() {
        String fromDate = tanggalDariText.getText().toString();
        String toDate = tanggalSampaiText.getText().toString();

        // Check if dates were selected
        boolean dariSelected = !fromDate.equals("Pilih Tanggal");
        boolean sampaiSelected = !toDate.equals("Pilih Tanggal");

        // Create message based on what was selected
        String message;
        if (dariSelected && sampaiSelected) {
            message = "Filter dari " + fromDate + " sampai " + toDate;
        } else if (dariSelected) {
            message = "Filter dari " + fromDate;
        } else if (sampaiSelected) {
            message = "Filter sampai " + toDate;
        } else {
            message = "Tidak ada filter yang dipilih";
        }

        // Show toast with filter information
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}