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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {

    private RecyclerView rvCheckoutProducts;
    private TextView tvQtyOrder, tvSubtotal, tvTotal;
    private Button btnLanjutkan;
    private ImageView btnBack;

    private List<Product> productList;
    private String formattedTotal = "Rp0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Inisialisasi view
        rvCheckoutProducts = findViewById(R.id.rvCheckoutProducts);
        tvQtyOrder = findViewById(R.id.tvqtyorder);
        tvSubtotal = findViewById(R.id.tvsubtotal);
        tvTotal = findViewById(R.id.tvtotal);
        btnLanjutkan = findViewById(R.id.btnlanjutkan);
        btnBack = findViewById(R.id.btnBack);

        // Ambil data produk dari intent
        productList = (ArrayList<Product>) getIntent().getSerializableExtra("checkout_products");
        if (productList == null) productList = new ArrayList<>();

        // Set RecyclerView
        rvCheckoutProducts.setLayoutManager(new LinearLayoutManager(this));
        rvCheckoutProducts.setAdapter(new CheckoutAdapter(productList));

        // Hitung dan tampilkan total qty & harga
        updateSummary();

        // Tombol lanjutkan checkout
        btnLanjutkan.setOnClickListener(v -> {
            // 1. Simpan ke riwayat pesanan
            saveOrderToHistory(productList, formattedTotal);

            // 2. Hapus data produk terpilih dari SharedPreferences
            SharedPreferences prefs = getSharedPreferences("SelectedProductsPref", MODE_PRIVATE);
            prefs.edit().remove("selected_products").apply();

            // 3. Reset quantity produk ke 0
            List<Product> allProducts = ProductManager.getInstance().getProducts();
            for (Product p : allProducts) {
                p.setQuantity(0);
            }

            // 4. Navigasi ke halaman Pesanan Sukses
            Intent intent = new Intent(CheckoutActivity.this, PesananSuksesActivity.class);
            intent.putExtra("total_harga", formattedTotal);
            startActivity(intent);
        });

        // Tombol kembali
        btnBack.setOnClickListener(v -> finish());
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

        tvQtyOrder.setText(String.valueOf(totalQty));

        // Format harga dengan locale Indonesia
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(localeID);
        formatter.setMaximumFractionDigits(0);

        formattedTotal = formatter.format(totalHarga);  // Tanpa tambahan "Rp " manual

        tvSubtotal.setText(formattedTotal);
        tvTotal.setText(formattedTotal);
    }

    private void saveOrderToHistory(List<Product> productList, String totalHarga) {
        SharedPreferences prefs = getSharedPreferences("checkout_data", MODE_PRIVATE);
        Gson gson = new Gson();

        String existing = prefs.getString("order_history", null);
        Type listType = new TypeToken<List<Order>>() {}.getType();
        List<Order> orderHistory = existing != null ? gson.fromJson(existing, listType) : new ArrayList<>();

        // Buat nomor order: 306-YYYY-MM-DD-00001
        String datePart = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        int orderNumber = orderHistory.size() + 1;
        String noOrder = String.format("306-%s-%05d", datePart, orderNumber);

        // Tanggal pembelian
        String tanggalPembelian = new SimpleDateFormat("dd MMMM yyyy, HH.mm.ss", new Locale("in", "ID"))
                .format(new Date());

        // Simpan timestamp waktu mulai pembayaran
        long startTimeMillis = System.currentTimeMillis();
        prefs.edit().putLong("start_time_millis", startTimeMillis).apply();

        // Status default
        String status = "Menunggu Pembayaran";

        // Simpan order baru (WAKTU PEMBAYARAN SEKARANG LONG)
        Order newOrder = new Order(noOrder, productList, totalHarga, startTimeMillis, tanggalPembelian, status);
        orderHistory.add(newOrder);

        // Simpan kembali ke SharedPreferences
        String json = gson.toJson(orderHistory);
        prefs.edit().putString("order_history", json).apply();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
