import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;

public class activityPesanan extends AppCompatActivity {

    // Tab TextViews
    private TextView tabBelumBayar;
    private TextView tabDalamProses;
    private TextView tabDikirim;

    // Product Views
    private ImageView imageProduk;
    private TextView namaProduk;
    private TextView satuanProduk;
    private TextView hargaProduk;
    private TextView qtyProduk;

    // Action Views
    private ImageView arrowDown;
    private ImageView arrowRight;
    private TextView rincianPesanan;

    // Bottom Navigation
    private LinearLayout navBeranda;
    private LinearLayout navPesanan;
    private LinearLayout navChat;
    private LinearLayout navProfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan3);

        initViews();
        setupTabListeners();
        setupClickListeners();

        setActiveTab();
    }

    private void initViews() {
        // Initialize tab views
        tabBelumBayar = findViewById(R.id.tabBelumBayar);
        tabDalamProses = findViewById(R.id.tabDalamProses);
        tabDikirim = findViewById(R.id.tabDikirim);

        // Initialize product views
        imageProduk = findViewById(R.id.image_produk);
        namaProduk = findViewById(R.id.nama_produk);
        satuanProduk = findViewById(R.id.satuan_produk);
        hargaProduk = findViewById(R.id.harga_produk);
        qtyProduk = findViewById(R.id.qty_produk);

        // Initialize action views
        arrowDown = findViewById(R.id.arrow_up);
        arrowRight = findViewById(R.id.ic_arrow_right);

        // You'll need to add IDs to the LinearLayouts in XML for bottom navigation
        // navBeranda = findViewById(R.id.nav_beranda);
        // navPesanan = findViewById(R.id.nav_pesanan);
        // navChat = findViewById(R.id.nav_chat);
        // navProfil = findViewById(R.id.nav_profil);
    }

    private void setActiveTab() {
        // Set "Belum Bayar" as active tab (based on XML)
        tabBelumBayar.setText("Belum Bayar");
        tabBelumBayar.setTextColor(getResources().getColor(android.R.color.black));
        tabBelumBayar.setTypeface(null, android.graphics.Typeface.BOLD);
        tabBelumBayar.setBackgroundResource(R.drawable.tab_menu);
    }

    private void setupTabListeners() {
        tabBelumBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToTab("belum_bayar");
                // Current tab is already "Belum Bayar", so refresh or do nothing
                refreshOrderData();
            }
        });

        tabDalamProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToTab("dalam_proses");
                // Navigate to "Dalam Proses" tab
                // You can either filter current data or navigate to different activity
                loadDalamProsesOrders();
            }
        });

        tabDikirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToTab("dikirim");
                // Navigate to "Dikirim" tab
                loadDikirimOrders();
            }
        });
    }

    private void setupClickListeners() {
        // "Lihat Lainnya" click listener
        findViewById(R.id.lihat_lainnya_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandOrderDetails();
            }
        });