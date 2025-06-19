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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CheckoutActivity extends AppCompatActivity {

    private List<Product> productList;
    private String formattedTotal = "Rp0";
    private AppDatabase db;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        db = AppDatabase.getDatabase(this);
        executorService = Executors.newSingleThreadExecutor();

        RecyclerView rvCheckoutProducts = findViewById(R.id.rvCheckoutProducts);
        TextView tvQtyOrder = findViewById(R.id.tvqtyorder);
        TextView tvSubtotal = findViewById(R.id.tvsubtotal);
        TextView tvTotal = findViewById(R.id.tvtotal);
        Button btnLanjutkan = findViewById(R.id.btnlanjutkan);
        ImageView btnBack = findViewById(R.id.btnBack);

        productList = (ArrayList<Product>) getIntent().getSerializableExtra("checkout_products");
        if (productList == null) productList = new ArrayList<>();

        rvCheckoutProducts.setLayoutManager(new LinearLayoutManager(this));
        rvCheckoutProducts.setAdapter(new CheckoutAdapter(productList));

        updateSummary(tvQtyOrder, tvSubtotal, tvTotal);

        btnLanjutkan.setOnClickListener(v -> {
            saveOrderToDb();
            Intent intent = new Intent(CheckoutActivity.this, PesananSuksesActivity.class);
            intent.putExtra("total_harga", formattedTotal);
            // Hapus backstack agar tidak bisa kembali ke checkout setelah order dibuat
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        btnBack.setOnClickListener(v -> finish());
    }

    private void updateSummary(TextView tvQtyOrder, TextView tvSubtotal, TextView tvTotal) {
        int totalQty = 0;
        int totalHarga = 0;
        for (Product p : productList) {
            totalQty += p.getQuantity();
            try {
                totalHarga += Integer.parseInt(p.getPrice().replaceAll("[^\\d]", "")) * p.getQuantity();
            } catch (NumberFormatException ignored) {}
        }
        tvQtyOrder.setText(String.valueOf(totalQty));
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(localeID);
        formatter.setMaximumFractionDigits(0);
        formattedTotal = formatter.format(totalHarga);
        tvSubtotal.setText(formattedTotal);
        tvTotal.setText(formattedTotal);
    }

    private void saveOrderToDb() {
        executorService.execute(() -> {
            // Buat order baru
            String datePart = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            String noOrder = String.format("306-%s-%s", datePart, System.currentTimeMillis() % 10000);
            String tanggalPembelian = new SimpleDateFormat("dd MMMM yyyy, HH.mm.ss", new Locale("in", "ID")).format(new Date());
            long startTimeMillis = System.currentTimeMillis();
            String status = "Menunggu Pembayaran";
            Order newOrder = new Order(noOrder, new ArrayList<>(productList), formattedTotal, startTimeMillis, tanggalPembelian, status);

            // Simpan order baru ke database
            db.orderDao().insertOrder(newOrder);

            // Reset kuantitas semua produk di keranjang menjadi 0
            db.productDao().resetAllQuantities();
        });
    }
}