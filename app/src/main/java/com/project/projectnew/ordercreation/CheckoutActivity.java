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
    private TextView tvqtyorder, tvsubtotal, tvtotal;
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
        tvqtyorder = findViewById(R.id.tvqtyorder);
        tvsubtotal = findViewById(R.id.tvsubtotal);
        tvtotal = findViewById(R.id.tvtotal);
        btnLanjutkan = findViewById(R.id.btnlanjutkan);
        btnBack = findViewById(R.id.btnBack);

        // Ambil data produk dari intent
        productList = (ArrayList<Product>) getIntent().getSerializableExtra("checkout_products");
        if (productList == null) productList = new ArrayList<>();

        // Set up RecyclerView
        rvCheckoutProducts.setLayoutManager(new LinearLayoutManager(this));
        rvCheckoutProducts.setAdapter(new CheckoutAdapter(productList));

        // Tampilkan ringkasan qty dan harga
        updateSummary();

        // Tombol lanjut ke PesananSukses
        btnLanjutkan.setOnClickListener(v -> {
            saveOrderToHistory(productList, formattedTotal);
            Intent intent = new Intent(CheckoutActivity.this, PesananSuksesActivity.class);
            intent.putExtra("total_harga", formattedTotal);
            startActivity(intent);
        });

        // Tombol kembali
        btnBack.setOnClickListener(v -> {
            finish();
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

        formattedTotal = formatter.format(totalHarga);
        tvsubtotal.setText(formattedTotal);
        tvtotal.setText(formattedTotal);
    }

    private void saveOrderToHistory(List<Product> productList, String totalHarga) {
        SharedPreferences prefs = getSharedPreferences("checkout_data", MODE_PRIVATE);
        Gson gson = new Gson();

        String existing = prefs.getString("order_history", null);
        Type listType = new TypeToken<List<Order>>() {}.getType();
        List<Order> orderHistory = existing != null ? gson.fromJson(existing, listType) : new ArrayList<>();

        // Generate No Order: 306-YYYY-MM-DD-00001
        String datePart = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        int orderNumber = orderHistory.size() + 1;
        String noOrder = String.format("306-%s-%05d", datePart, orderNumber);

        // Format tanggal pembelian: dd MMMM yyyy, HH.mm.ss
        String tanggalPembelian = new SimpleDateFormat("dd MMMM yyyy, HH.mm.ss", new Locale("in", "ID")).format(new Date());

        // Simpan timestamp waktu sekarang (waktu mulai hitung mundur)
        long startTimeMillis = System.currentTimeMillis();
        prefs.edit().putLong("start_time_millis", startTimeMillis).apply();

        // Status
        String status = "Menunggu Pembayaran";

        Order newOrder = new Order(noOrder, productList, totalHarga, "", tanggalPembelian, status);
        orderHistory.add(newOrder);

        String json = gson.toJson(orderHistory);
        prefs.edit().putString("order_history", json).apply();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
