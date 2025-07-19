package com.project.projectnew.ordercreation;

import android.os.Bundle;
import android.util.Log; // Import Log
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.project.projectnew.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DetailTransaksiActivity extends AppCompatActivity {

    private AppDatabase db;
    private ExecutorService executorService;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaksi);

        db = AppDatabase.getDatabase(this);
        executorService = Executors.newSingleThreadExecutor();

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        Notifikasi notifikasi = (Notifikasi) getIntent().getSerializableExtra("NOTIFIKASI_EXTRA");

        if (notifikasi != null) {
            executorService.execute(() -> {
                order = db.orderDao().getOrderByNoOrder(notifikasi.getOrderId());
                runOnUiThread(() -> {
                    if (order != null) {
                        // --- TAMBAHKAN KODE DEBUG DI SINI ---
                        int productCount = order.getProductList().size();
                        Log.d("DEBUG_PRODUK", "Jumlah produk yang diterima dari database: " + productCount);
                        // ------------------------------------

                        populateViews(notifikasi, order);
                    } else {
                        // --- TAMBAHKAN KODE DEBUG DI SINI ---
                        Log.e("DEBUG_PRODUK", "Error: Objek Order tidak ditemukan di database untuk ID: " + notifikasi.getOrderId());
                    }
                });
            });
        }
    }

    private void populateViews(Notifikasi notifikasi, Order order) {
        long timestamp = order.getWaktuPembayaran();
        Date date = new Date(timestamp);

        SimpleDateFormat formatJamTanggal = new SimpleDateFormat("HH:mm:ss, dd MMM yyyy", new Locale("in", "ID"));
        SimpleDateFormat formatTglBeli = new SimpleDateFormat("dd MMMM yyyy, HH.mm.ss", new Locale("in", "ID"));

        TextView tvJudul = findViewById(R.id.tvDetailJudul);
        TextView tvTanggalJam = findViewById(R.id.tvDetailTanggalJam);
        TextView tvStatus = findViewById(R.id.tvDetailStatus);
        tvJudul.setText(notifikasi.getJudul());
        tvTanggalJam.setText(formatJamTanggal.format(date));
        tvStatus.setText(notifikasi.getIsi());

        TextView tvNoOrder = findViewById(R.id.tvDetailNoOrder);
        TextView tvInvoiceNumber = findViewById(R.id.tvInvoiceNumber);
        TextView tvTglBeli = findViewById(R.id.tvDetailTglBeli);
        TextView tvStatusOrder = findViewById(R.id.tvDetailStatusOrder);
        tvNoOrder.setText(order.getNoOrder());
        tvInvoiceNumber.setText("INV-" + order.getNoOrder());
        tvTglBeli.setText(formatTglBeli.format(date));
        tvStatusOrder.setText(order.getStatus());

        RecyclerView rvProduk = findViewById(R.id.rvDetailProduk);
        rvProduk.setLayoutManager(new LinearLayoutManager(this));
        CheckoutAdapter adapter = new CheckoutAdapter(order.getProductList());
        rvProduk.setAdapter(adapter);

        updateSummary(order.getProductList());
    }

    private void updateSummary(List<Product> productList) {
        TextView tvQty = findViewById(R.id.tvDetailQty);
        TextView tvSubtotal = findViewById(R.id.tvDetailSubtotal);
        TextView tvTotal = findViewById(R.id.tvDetailTotal);
        int totalQty = 0;
        int totalHarga = 0;
        for (Product p : productList) {
            totalQty += p.getQuantity();
            try {
                totalHarga += Integer.parseInt(p.getPrice().replaceAll("[^\\d]", "")) * p.getQuantity();
            } catch (NumberFormatException ignored) {}
        }
        tvQty.setText(String.valueOf(totalQty));
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(localeID);
        formatter.setMaximumFractionDigits(0);
        String formattedTotal = formatter.format(totalHarga);
        tvSubtotal.setText(formattedTotal);
        tvTotal.setText(formattedTotal);

        TextView tvDiskon = findViewById(R.id.tvDetailDiskon);
        TextView tvOngkir = findViewById(R.id.tvDetailOngkir);
        tvDiskon.setText("Rp 0");
        tvOngkir.setText("Rp 0");
    }
}