package com.project.projectnew.ordercreation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar; // Import Toolbar
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.project.projectnew.R;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BerandaActivity extends AppCompatActivity implements ProductAdapter.TotalUpdateListener {

    private LinearLayout btnPesanan;
    private RecyclerView rvProducts;
    private FrameLayout fmTotal;
    private TextView tvTotal, tvQty;
    private Toolbar toolbar; // Tambahkan variabel Toolbar

    private List<Product> productList = new ArrayList<>();
    private ProductAdapter productAdapter;
    private AppDatabase db;
    private ExecutorService executorService;
    private Handler mainThreadHandler;

    // Carousel components
    private ViewPager2 carouselViewPager;
    private LinearLayout carouselDots;
    private ImageView[] dots;
    private final Handler carouselHandler = new Handler(Looper.getMainLooper());
    private Runnable carouselRunnable;
    private final List<Integer> imageList = Arrays.asList(
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);

        initViews();
        // Setup Toolbar sebagai Action Bar aplikasi
        setSupportActionBar(toolbar);
        // Hapus judul default dari Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        db = AppDatabase.getDatabase(this);
        executorService = Executors.newSingleThreadExecutor();
        mainThreadHandler = new Handler(Looper.getMainLooper());

        setupCarousel();
        setupProductAdapter();

        fmTotal.setOnClickListener(v -> {
            Intent intent = new Intent(this, KeranjangActivity.class);
            startActivity(intent);
        });

        btnPesanan.setOnClickListener(v -> startActivity(new Intent(this, PesananActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProductsFromDb();
        startAutoSlide();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (carouselRunnable != null) {
            carouselHandler.removeCallbacks(carouselRunnable);
        }
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        btnPesanan = findViewById(R.id.btnPesanan);
        rvProducts = findViewById(R.id.rvProducts);
        fmTotal = findViewById(R.id.fmTotal);
        tvTotal = findViewById(R.id.tvTotal);
        tvQty = findViewById(R.id.tvQty);
        carouselViewPager = findViewById(R.id.carouselViewPager);
        carouselDots = findViewById(R.id.carouselDots);
    }

    private void setupProductAdapter() {
        // Pastikan ProductAdapter Anda adalah versi yang simpel (tanpa logika header)
        productAdapter = new ProductAdapter(productList, false, this);
        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        rvProducts.setAdapter(productAdapter);
    }

    private void loadProductsFromDb() {
        executorService.execute(() -> {
            List<Product> productsFromDb = db.productDao().getAllProducts();
            mainThreadHandler.post(() -> {
                productList.clear();
                productList.addAll(productsFromDb);
                productAdapter.notifyDataSetChanged();
                updateTotalDisplay();
            });
        });
    }

    @Override
    public void updateTotal(List<Product> updatedProducts) {
        for(Product p : updatedProducts) {
            executorService.execute(() -> db.productDao().updateProduct(p));
        }
        updateTotalDisplay();
    }

    private void updateTotalDisplay() {
        executorService.execute(() -> {
            List<Product> cartItems = db.productDao().getProductsInCart();
            int totalQty = 0;
            int totalPrice = 0;
            for (Product p : cartItems) {
                totalQty += p.getQuantity();
                try {
                    totalPrice += Integer.parseInt(p.getPrice().replaceAll("[^\\d]", "")) * p.getQuantity();
                } catch (NumberFormatException ignored) {}
            }
            int finalTotalQty = totalQty;
            int finalTotalPrice = totalPrice;
            mainThreadHandler.post(() -> {
                if (finalTotalQty > 0) {
                    fmTotal.setVisibility(View.VISIBLE);
                    NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
                    formatter.setMaximumFractionDigits(0);
                    tvTotal.setText(formatter.format(finalTotalPrice));
                    tvQty.setText(finalTotalQty + " Produk");
                } else {
                    fmTotal.setVisibility(View.GONE);
                }
            });
        });
    }

    private void setupCarousel() {
        carouselViewPager.setAdapter(new ImageSliderAdapter(imageList));
        addDots(0);
        carouselViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                addDots(position);
            }
        });
    }

    private void addDots(int position) {
        carouselDots.removeAllViews();
        dots = new ImageView[imageList.size()];
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageResource(i == position ? R.drawable.dot_active : R.drawable.dot_inactive);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);
            carouselDots.addView(dots[i], params);
        }
    }

    private void startAutoSlide() {
        if (carouselRunnable != null) {
            carouselHandler.removeCallbacks(carouselRunnable);
        }
        carouselRunnable = () -> {
            int currentItem = carouselViewPager.getCurrentItem();
            int nextItem = (currentItem + 1) % imageList.size();
            carouselViewPager.setCurrentItem(nextItem, true);
            carouselHandler.postDelayed(carouselRunnable, 3000);
        };
        carouselHandler.postDelayed(carouselRunnable, 3000);
    }

    private static class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder> {
        private final List<Integer> images;
        ImageSliderAdapter(List<Integer> images) { this.images = images; }

        @NonNull @Override
        public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carousel_banner, parent, false);
            return new ImageViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
            holder.bannerImage.setImageResource(images.get(position));
        }
        @Override
        public int getItemCount() { return images.size(); }

        static class ImageViewHolder extends RecyclerView.ViewHolder {
            final ImageView bannerImage;
            ImageViewHolder(@NonNull View itemView) {
                super(itemView);
                bannerImage = itemView.findViewById(R.id.imageViewBanner);
            }
        }
    }
}