package com.project.projectnew.ordercreation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KeranjangActivity extends AppCompatActivity {

    private RecyclerView rvProducts;
    private TextView tvTotalQtyValue, tvTotalPriceValue;
    private Button btnLanjutkan;
    private ImageView btnBack;

    private List<Product> selectedProducts = new ArrayList<>();
    private ProductAdapter productAdapter;
    private AppDatabase db;
    private ExecutorService executorService;
    private Handler mainThreadHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);

        initViews();

        db = AppDatabase.getDatabase(this);
        executorService = Executors.newSingleThreadExecutor();

        setupAdapter();
        loadCartProductsFromDb();

        btnLanjutkan.setOnClickListener(v -> {
            Intent intent = new Intent(KeranjangActivity.this, CheckoutActivity.class);
            intent.putExtra("checkout_products", new ArrayList<>(selectedProducts));
            startActivity(intent);
        });

        btnBack.setOnClickListener(v -> finish());
    }

    private void initViews() {
        rvProducts = findViewById(R.id.rvProducts);
        tvTotalQtyValue = findViewById(R.id.tvTotalQtyValue);
        tvTotalPriceValue = findViewById(R.id.tvTotalPriceValue);
        btnLanjutkan = findViewById(R.id.btnLanjutkan);
        btnBack = findViewById(R.id.btnBack);
    }

    private void setupAdapter() {
        productAdapter = new ProductAdapter(
                selectedProducts,
                true, // Mode Keranjang
                updatedList -> {
                    for(Product p : updatedList) {
                        executorService.execute(() -> db.productDao().updateProduct(p));
                    }
                    updateTotalDisplay(updatedList);
                },
                updatedList -> { // Hapus item dari list dan set kuantitas jadi 0 di DB
                    for(Product p : updatedList) {
                        if(p.getQuantity() == 0) {
                            executorService.execute(() -> db.productDao().updateProduct(p));
                        }
                    }
                }
        );
        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        rvProducts.setAdapter(productAdapter);
    }

    private void loadCartProductsFromDb() {
        executorService.execute(() -> {
            List<Product> cartProducts = db.productDao().getProductsInCart();
            mainThreadHandler.post(() -> {
                selectedProducts.clear();
                selectedProducts.addAll(cartProducts);
                productAdapter.notifyDataSetChanged();
                updateTotalDisplay(selectedProducts);
            });
        });
    }

    private void updateTotalDisplay(List<Product> productList) {
        int totalQty = 0;
        int totalHarga = 0;
        for (Product p : productList) {
            totalQty += p.getQuantity();
            try {
                totalHarga += Integer.parseInt(p.getPrice().replaceAll("[^\\d]", "")) * p.getQuantity();
            } catch (NumberFormatException ignored) {}
        }

        tvTotalQtyValue.setText(String.valueOf(totalQty));
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(localeID);
        formatter.setMaximumFractionDigits(0);
        tvTotalPriceValue.setText(formatter.format(totalHarga));

        btnLanjutkan.setEnabled(totalQty > 0);
        btnLanjutkan.setAlpha(totalQty > 0 ? 1f : 0.5f);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}