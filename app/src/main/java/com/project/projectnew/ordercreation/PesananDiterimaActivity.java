package com.project.projectnew.ordercreation;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.project.projectnew.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PesananDiterimaActivity extends AppCompatActivity {

    private TextView tvNoOrder, tvInvoiceNumber, tvTanggalPembelian, tvStatus;
    private TextView tvQtyOrder, tvSubtotal, tvTotal;
    private RecyclerView rvProducts;
    private ImageButton imgbtnBack;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan_diterima);

        // Ambil data Order dari Intent
        order = (Order) getIntent().getSerializableExtra("ORDER_DETAIL");

        if (order == null) {
            finish(); // Jika tidak ada data, tutup activity
            return;
        }

        initViews();
        populateData();
    }

    private void initViews() {
        // Header
        tvNoOrder = findViewById(R.id.tvNoOrder);
        tvInvoiceNumber = findViewById(R.id.tvInvoiceNumber);
        tvTanggalPembelian = findViewById(R.id.tvTanggalPembelian);
        tvStatus = findViewById(R.id.tvStatus);

        // Product List
        rvProducts = findViewById(R.id.rvProducts);
        rvProducts.setLayoutManager(new LinearLayoutManager(this));

        // Summary
        tvQtyOrder = findViewById(R.id.tvqtyorder);
        tvSubtotal = findViewById(R.id.tvsubtotal);
        tvTotal = findViewById(R.id.tvtotal);

        // Back Button
        imgbtnBack = findViewById(R.id.imgbtnBack);
        imgbtnBack.setOnClickListener(v -> finish());
    }

    private void populateData() {
        // Populate Header
        tvNoOrder.setText(order.getNoOrder());
        tvInvoiceNumber.setText("INV-" + order.getNoOrder());
        tvTanggalPembelian.setText(order.getTanggalPembelian());
        tvStatus.setText(order.getStatus());
        // PERBAIKAN: Menggunakan Color.parseColor untuk menghindari error resource
        tvStatus.setTextColor(Color.parseColor("#35C759"));

        // Populate Product List
        CheckoutAdapter adapter = new CheckoutAdapter(order.getProductList());
        rvProducts.setAdapter(adapter);

        // Populate Summary
        updateSummary(order.getProductList());
    }

    private void updateSummary(List<Product> productList) {
        int totalQty = 0;
        int totalHarga = 0;

        for (Product p : productList) {
            totalQty += p.getQuantity();
            String cleanPrice = p.getPrice().replaceAll("[^\\d]", "");
            if (!cleanPrice.isEmpty()) {
                totalHarga += p.getQuantity() * Integer.parseInt(cleanPrice);
            }
        }

        tvQtyOrder.setText(String.valueOf(totalQty));

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(localeID);
        formatter.setMaximumFractionDigits(0);
        String formattedTotal = formatter.format(totalHarga);

        tvSubtotal.setText(formattedTotal);
        tvTotal.setText(formattedTotal);
    }
}
