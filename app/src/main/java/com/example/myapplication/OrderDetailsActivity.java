package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class OrderDetailsActivity extends AppCompatActivity {

    private TextView tvOrderId, tvTrackingNumber, tvOrderDate, tvStatus, tvDeliveryAddress;
    private LinearLayout layoutOrderSummary;
    private ImageView btnExpandSummary;
    private boolean isExpanded = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pesanandikirim3);

        // Initialize toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Initialize views
        tvOrderId = findViewById(R.id.tvOrderId);
        tvTrackingNumber = findViewById(R.id.tvTrackingNumber);
        tvOrderDate = findViewById(R.id.tvOrderDate);
        tvStatus = findViewById(R.id.tvStatus);
        tvDeliveryAddress = findViewById(R.id.tvDeliveryAddress);
        layoutOrderSummary = findViewById(R.id.layoutOrderSummary);
        btnExpandSummary = findViewById(R.id.btnExpandSummary);

        // Set data
        setOrderData();

        // Set back button click listener
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Set expand/collapse button click listener
        btnExpandSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded) {
                    layoutOrderSummary.setVisibility(View.GONE);
                    btnExpandSummary.setImageResource(R.drawable.arrow_up);
                } else {
                    layoutOrderSummary.setVisibility(View.VISIBLE);
                    btnExpandSummary.setImageResource(R.drawable.ic_arrow_down);
                }
                isExpanded = !isExpanded;
            }
        });
    }

    private void setOrderData() {
        // Set order details
        tvOrderId.setText("INV-3034-XI-0001");
        tvTrackingNumber.setText("00123-4533-22-0001");
        tvOrderDate.setText("22 April 2023, 11:30");
        tvStatus.setText("Pesanan Diterima");
        tvDeliveryAddress.setText("Jl. Letnan Purnata No. 195, Gunung Nangka, Kec. Kib. Duk, Tangerang, Banten 15810");
    }
}
}
