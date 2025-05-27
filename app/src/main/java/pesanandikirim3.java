
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;

public class pesanandikirim3 extends AppCompatActivity {

    // Tab
    private TextView tabDalamProses, tabBelumBayar, tabDikirim;

    // Produk
    private ImageView imageProduk;
    private TextView namaProduk, satuanProduk, hargaProduk, qtyProduk;

    // Info Pesanan
    private TextView noOrderText, tanggalPembelianText, statusPesanan, totalHarga;

    // Navigasi
    private LinearLayout navBeranda, navPesanan, navChat, navProfil;

    // Expand/collapse
    private LinearLayout lihatLainnyaLayout;
    private ImageView arrowIcon;
    private boolean isExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pesanandikirim3);

        initViews();
        setupListeners();
        loadOrderData();
    }

    private void initViews() {
        // Tabs
        tabDalamProses = findViewById(R.id.tabDalamProses);
        tabBelumBayar = findViewById(R.id.tabBelumBayar);
        tabDikirim = findViewById(R.id.tabDikirim);

        // Produk
        imageProduk = findViewById(R.id.image_produk);
        namaProduk = findViewById(R.id.nama_produk);
        satuanProduk = findViewById(R.id.satuan_produk);
        hargaProduk = findViewById(R.id.harga_produk);
        qtyProduk = findViewById(R.id.qty_produk);

        // Info Pesanan
        noOrderText = findViewById(R.id.No_Order);
        tanggalPembelianText = findViewById(R.id.tanggal_pembelian_text);
        statusPesanan = findViewById(R.id.status_pesanan);
        totalHarga = findViewById(R.id.total_harga);

        // Navigasi
        navBeranda = findViewById(R.id.nav_beranda);
        navPesanan = findViewById(R.id.nav_pesanan);
        navChat = findViewById(R.id.nav_chat);
        navProfil = findViewById(R.id.nav_profil);

        // Expand
        lihatLainnyaLayout = findViewById(R.id.lihat_lainnya_layout);
        arrowIcon = findViewById(R.id.arrow_icon);
    }

    private void setupListeners() {
        // Tab
        tabDalamProses.setOnClickListener(v -> selectTab(tabDalamProses, "dalam_proses"));
        tabBelumBayar.setOnClickListener(v -> selectTab(tabBelumBayar, "belum_bayar"));
        tabDikirim.setOnClickListener(v -> selectTab(tabDikirim, "dikirim"));

        // Navigasi
        navBeranda.setOnClickListener(v -> navigateTo("home"));
        navPesanan.setOnClickListener(v -> refreshCurrentPage());
        navChat.setOnClickListener(v -> navigateTo("chat"));
        navProfil.setOnClickListener(v -> navigateTo("profile"));

        // Expand
        lihatLainnyaLayout.setOnClickListener(v -> toggleExpandCollapse());
    }

    private void selectTab(TextView selectedTab, String status) {
        resetTabStyles();
        selectedTab.setTextColor(ContextCompat.getColor(this, R.color.black));
        selectedTab.setBackground(ContextCompat.getDrawable(this, R.drawable.tab_menu3));
        selectedTab.setTypeface(null, android.graphics.Typeface.BOLD);
        loadOrdersByStatus(status);
    }

    private void resetTabStyles() {
        TextView[] tabs = {tabDalamProses, tabBelumBayar, tabDikirim};
        for (TextView tab : tabs) {
            tab.setTextColor(ContextCompat.getColor(this, R.color.gray_text));
            tab.setBackground(null);
            tab.setTypeface(null, android.graphics.Typeface.NORMAL);
        }
    }

    private void toggleExpandCollapse() {
        if (isExpanded) {
            collapseOrderDetails();
            arrowIcon.setRotation(0);
        } else {
            expandOrderDetails();
            arrowIcon.setRotation(180);
        }
        isExpanded = !isExpanded;
    }

    private void loadOrderData() {
        OrderData order = getSampleOrderData();
        noOrderText.setText(order.orderNumber);
        tanggalPembelianText.setText(order.purchaseDate);
        statusPesanan.setText(order.status);
        totalHarga.setText(order.totalPrice);
        namaProduk.setText(order.productName);
        satuanProduk.setText(order.productUnit);
        hargaProduk.setText(order.productPrice);
        qtyProduk.setText("Qty " + order.quantity + "x");
        imageProduk.setImageResource(R.drawable.ic_launcher_background); // Placeholder
    }

    private void loadOrdersByStatus(String status) {
        // Dummy logic
        switch (status) {
            case "dalam_proses":
                // Implementasi data pesanan dalam proses
                break;
            case "belum_bayar":
                // Implementasi data pesanan belum bayar
                break;
            case "dikirim":
                // Implementasi data pesanan dikirim
                break;
        }
    }

    private void expandOrderDetails() {
        // Tambah detail pesanan
    }

    private void collapseOrderDetails() {
        // Sembunyikan detail pesanan
    }

    private void navigateTo(String destination) {
        // Intent pindah activity
        // Contoh: startActivity(new Intent(this, HomeActivity.class));
    }

    private void refreshCurrentPage() {
        loadOrderData();
    }

    private OrderData getSampleOrderData() {
        return new OrderData(
                "306-2025-04-22-00001",
                "22 April 2025, 13.18.00",
                "Pesanan Dikirim",
                "Nama Produk",
                "Satuan Produk",
                "Rp 10.000",
                1,
                "Rp150.000",
                ""
        );
    }

    // Data Model
    private static class OrderData {
        String orderNumber, purchaseDate, status;
        String productName, productUnit, productPrice;
        int quantity;
        String totalPrice, productImageUrl;

        public OrderData(String orderNumber, String purchaseDate, String status,
                         String productName, String productUnit, String productPrice,
                         int quantity, String totalPrice, String productImageUrl) {
            this.orderNumber = orderNumber;
            this.purchaseDate = purchaseDate;
            this.status = status;
            this.productName = productName;
            this.productUnit = productUnit;
            this.productPrice = productPrice;
            this.quantity = quantity;
            this.totalPrice = totalPrice;
            this.productImageUrl = productImageUrl;
        }
    }
}
