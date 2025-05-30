package com.project.projectnew.ordercreation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.projectnew.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {

    private RecyclerView rvCheckoutProducts;
    private TextView tvqtyorder, tvsubtotal, tvtotal;
    private Button btnLanjutkan;
    private List<Product> productList;
    private String formattedTotal = "Rp0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Inisialisasi view
        rvCheckoutProducts = findViewById(R.id.rvCheckoutProducts);
        tvqtyorder = findViewById(R.id.tvqtyorder);
        tvsubtotal = findViewById(R.id.tvsubtotal);
        tvtotal = findViewById(R.id.tvtotal);
        btnLanjutkan = findViewById(R.id.btnlanjutkan);

        // Ambil data produk dari intent
        productList = (ArrayList<Product>) getIntent().getSerializableExtra("checkout_products");
        if (productList == null) productList = new ArrayList<>();

        // Set up RecyclerView
        rvCheckoutProducts.setLayoutManager(new LinearLayoutManager(this));
        rvCheckoutProducts.setAdapter(new CheckoutAdapter(productList));

        // Tampilkan ringkasan qty dan harga
        updateSummary();

        // Aksi ketika tombol lanjutkan ditekan
        btnLanjutkan.setOnClickListener(v -> {
            Intent intent = new Intent(CheckoutActivity.this, PesananSuksesActivity.class);
            intent.putExtra("total_harga", formattedTotal); // kirim total harga
            startActivity(intent);
        });
    }

    private void updateSummary() {
        int totalQty = 0;
        int totalHarga = 0;

        for (Product p : productList) {
            totalQty += p.getQuantity();
            String cleanPrice = p.getPrice().replaceAll("[^\\d]", "");
            if (!cleanPrice.isEmpty()) {
                totalHarga += p.getQuantity() * Integer.parseInt(cleanPrice);
            }
        }

        tvqtyorder.setText(String.valueOf(totalQty));

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(localeID);
        formatter.setMaximumFractionDigits(0);

        formattedTotal = formatter.format(totalHarga); // simpan format total untuk dikirim
        tvsubtotal.setText(formattedTotal);
        tvtotal.setText(formattedTotal);
    }
}
