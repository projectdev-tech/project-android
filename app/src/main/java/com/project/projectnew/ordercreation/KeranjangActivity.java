package com.project.projectnew.ordercreation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
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
    private ImageView btnBack;

    private List<Product> selectedProducts;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);

        rvProducts = findViewById(R.id.rvProducts);
        tvTotalQtyValue = findViewById(R.id.tvTotalQtyValue);
        tvTotalPriceValue = findViewById(R.id.tvTotalPriceValue);
        btnLanjutkan = findViewById(R.id.btnLanjutkan);
        btnBack = findViewById(R.id.btnBack);

        selectedProducts = (ArrayList<Product>) getIntent().getSerializableExtra("selected_products");
        if (selectedProducts == null) selectedProducts = new ArrayList<>();

        cartAdapter = new CartAdapter(selectedProducts, updatedList -> {
            selectedProducts = updatedList;
            updateTotalDisplay(selectedProducts);
        });

        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        rvProducts.setAdapter(cartAdapter);

        updateTotalDisplay(selectedProducts);

        btnLanjutkan.setOnClickListener(v -> {
            Intent intent = new Intent(KeranjangActivity.this, CheckoutActivity.class);
            intent.putExtra("checkout_products", new ArrayList<>(selectedProducts));
            startActivity(intent);
        });

        btnBack.setOnClickListener(v -> returnUpdatedData());
    }

    private void updateTotalDisplay(List<Product> productList) {
        int totalQty = 0;
        int totalHarga = 0;

        for (Product p : productList) {
            totalQty += p.getQuantity();
            totalHarga += p.getQuantity() * Integer.parseInt(p.getPrice().replaceAll("[^\\d]", ""));
        }

        tvTotalQtyValue.setText(String.valueOf(totalQty));

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(localeID);
        formatter.setMaximumFractionDigits(0);
        tvTotalPriceValue.setText(formatter.format(totalHarga));

        // ➤ Disable tombol jika tidak ada produk
        btnLanjutkan.setEnabled(totalQty > 0);
        btnLanjutkan.setAlpha(totalQty > 0 ? 1f : 0.5f);  // transparan kalau disabled
    }

    @Override
    public void onBackPressed() {
        returnUpdatedData();
        super.onBackPressed();
    }

    private void returnUpdatedData() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("updated_products", new ArrayList<>(selectedProducts));
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
