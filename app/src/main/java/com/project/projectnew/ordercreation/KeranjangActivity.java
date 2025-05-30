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

public class KeranjangActivity extends AppCompatActivity {

    private RecyclerView rvProducts;
    private TextView tvTotalQtyValue, tvTotalPriceValue;
    private Button btnLanjutkan;

    private List<Product> selectedProducts;
    private int totalQty = 0;
    private int totalHarga = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);

        rvProducts = findViewById(R.id.rvProducts);
        tvTotalQtyValue = findViewById(R.id.tvTotalQtyValue);
        tvTotalPriceValue = findViewById(R.id.tvTotalPriceValue);
        btnLanjutkan = findViewById(R.id.btnLanjutkan); // Tambah referensi tombol

        selectedProducts = (ArrayList<Product>) getIntent().getSerializableExtra("selected_products");

        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        rvProducts.setAdapter(new ProductAdapter(selectedProducts, updatedList -> {
            updateTotalDisplay(updatedList);
        }));

        updateTotalDisplay(selectedProducts);

        btnLanjutkan.setOnClickListener(v -> {
            Intent intent = new Intent(KeranjangActivity.this, CheckoutActivity.class);
            intent.putExtra("checkout_products", new ArrayList<>(selectedProducts));
            startActivity(intent);
        });

    }

    private void updateTotalDisplay(List<Product> productList) {
        totalQty = 0;
        totalHarga = 0;

        for (Product p : productList) {
            totalQty += p.getQuantity();
            totalHarga += p.getQuantity() * Integer.parseInt(p.getPrice().replaceAll("[^\\d]", ""));
        }

        tvTotalQtyValue.setText(String.valueOf(totalQty));

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(localeID);
        formatter.setMaximumFractionDigits(0);
        tvTotalPriceValue.setText(formatter.format(totalHarga));
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("updated_products", new ArrayList<>(selectedProducts));
        setResult(RESULT_OK, resultIntent);
        super.onBackPressed();
    }
}
