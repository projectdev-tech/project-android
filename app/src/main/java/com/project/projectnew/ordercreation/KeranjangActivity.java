package com.project.projectnew.ordercreation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.projectnew.R;

import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class KeranjangActivity extends AppCompatActivity {

    private static final String PREF_NAME = "SelectedProductsPref";
    private static final String KEY_SELECTED_PRODUCTS = "selected_products";

    private RecyclerView rvProducts;
    private TextView tvTotalQtyValue, tvTotalPriceValue;
    private Button btnLanjutkan;
    private ImageView btnBack;

    private List<Product> selectedProducts;
    private ProductAdapter productAdapter;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);

        // Inisialisasi view
        rvProducts = findViewById(R.id.rvProducts);
        tvTotalQtyValue = findViewById(R.id.tvTotalQtyValue);
        tvTotalPriceValue = findViewById(R.id.tvTotalPriceValue);
        btnLanjutkan = findViewById(R.id.btnLanjutkan);
        btnBack = findViewById(R.id.btnBack);

        // Load produk dari SharedPreferences
        loadSelectedProducts();

        // Inisialisasi adapter
        productAdapter = new ProductAdapter(
                selectedProducts,
                true,
                updatedList -> {
                    selectedProducts = updatedList;
                    updateTotalDisplay(selectedProducts);
                },
                updatedList -> saveSelectedProducts(updatedList)
        );

        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        rvProducts.setAdapter(productAdapter);

        // Update tampilan awal
        updateTotalDisplay(selectedProducts);

        // Tombol lanjut ke checkout
        btnLanjutkan.setOnClickListener(v -> {
            saveSelectedProducts(selectedProducts);
            Intent intent = new Intent(KeranjangActivity.this, CheckoutActivity.class);
            intent.putExtra("checkout_products", new ArrayList<>(selectedProducts));
            startActivity(intent);
        });

        // Tombol kembali
        btnBack.setOnClickListener(v -> returnUpdatedProducts());
    }

    private void loadSelectedProducts() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String json = sharedPreferences.getString(KEY_SELECTED_PRODUCTS, null);
        if (json != null) {
            Type type = new TypeToken<List<Product>>() {}.getType();
            selectedProducts = gson.fromJson(json, type);
        } else {
            selectedProducts = new ArrayList<>();
        }
    }

    private void saveSelectedProducts(List<Product> updatedProducts) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String json = gson.toJson(updatedProducts);
        sharedPreferences.edit().putString(KEY_SELECTED_PRODUCTS, json).apply();
    }

    private void updateTotalDisplay(List<Product> productList) {
        int totalQty = 0;
        int totalHarga = 0;

        for (Product p : productList) {
            int qty = p.getQuantity();
            if (qty > 0) {
                totalQty += qty;
                try {
                    int price = Integer.parseInt(p.getPrice().replaceAll("[^\\d]", ""));
                    totalHarga += price * qty;
                } catch (NumberFormatException ignored) {}
            }
        }

        tvTotalQtyValue.setText(String.valueOf(totalQty));

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(localeID);
        formatter.setMaximumFractionDigits(0);
        tvTotalPriceValue.setText(formatter.format(totalHarga));

        btnLanjutkan.setEnabled(totalQty > 0);
        btnLanjutkan.setAlpha(totalQty > 0 ? 1f : 0.5f);
    }

    private void returnUpdatedProducts() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("updated_products", gson.toJson(selectedProducts));
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        returnUpdatedProducts();
    }
}
