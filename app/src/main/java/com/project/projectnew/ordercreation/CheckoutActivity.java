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
            // 1. Simpan ke riwayat pesanan (SEKARANG DIAKTIFKAN KEMBALI)
            saveOrderToHistory(productList, formattedTotal);

            // 2. Hapus data produk terpilih dari SharedPreferences
            getSharedPreferences("SelectedProductsPref", MODE_PRIVATE)
                    .edit().remove("selected_products").apply();

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

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(localeID);
        formatter.setMaximumFractionDigits(0);
        formattedTotal = formatter.format(totalHarga);

        tvSubtotal.setText(formattedTotal);
        tvTotal.setText(formattedTotal);
    }

    private void saveOrderToHistory(List<Product> productList, String totalHarga) {
        // Buat nomor order unik berdasarkan waktu
        String datePart = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        // Gunakan timestamp untuk membuat nomor lebih unik untuk sesi ini
        String noOrder = String.format("306-%s-%s", datePart, System.currentTimeMillis() % 10000);

        // Tanggal pembelian
        String tanggalPembelian = new SimpleDateFormat("dd MMMM yyyy, HH.mm.ss", new Locale("in", "ID"))
                .format(new Date());

        // Waktu mulai pembayaran
        long startTimeMillis = System.currentTimeMillis();

        // Status default
        String status = "Menunggu Pembayaran";

        // Buat objek Order baru
        Order newOrder = new Order(noOrder, new ArrayList<>(productList), totalHarga, startTimeMillis, tanggalPembelian, status);

        // Panggil metode baru untuk menambahkan order ke daftar runtime
        DummyDataGenerator.addOrder(newOrder);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}