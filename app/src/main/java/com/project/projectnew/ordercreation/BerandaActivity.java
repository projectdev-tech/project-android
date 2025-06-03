package com.project.projectnew.ordercreation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.projectnew.R;

import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class BerandaActivity extends AppCompatActivity implements ProductAdapter.TotalUpdateListener {

    private static final String PREF_NAME = "SelectedProductsPref";
    private static final String KEY_SELECTED_PRODUCTS = "selected_products";

    private LinearLayout btnPesanan, lnPilihanProduk;
    private RecyclerView rvProducts;
    private ViewPager2 carouselViewPager;
    private LinearLayout carouselDots;
    private FrameLayout fmTotal;
    private TextView tvTotal, tvQty;
    private ImageView[] dots;

    private List<Product> productList;
    private ProductAdapter productAdapter;
    private SharedPreferences sharedPreferences;
    private final Gson gson = new Gson();

    private final Handler handler = new Handler();
    private Runnable carouselRunnable;
    private int currentIndex = 0;

    private final List<Integer> imageList = Arrays.asList(
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3
    );

    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);

        initViews();
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        setupCarousel();
        setupProductList();
        setupResultLauncher();

        fmTotal.setOnClickListener(v -> launcher.launch(new Intent(this, KeranjangActivity.class)));

        btnPesanan.setOnClickListener(v -> startActivity(new Intent(this, PesananActivity.class)));

        updateTotal(productList);
    }

    private void initViews() {
        btnPesanan = findViewById(R.id.btnPesanan);
        lnPilihanProduk = findViewById(R.id.lnPilihanProduk);
        rvProducts = findViewById(R.id.rvProducts);
        carouselViewPager = findViewById(R.id.carouselViewPager);
        carouselDots = findViewById(R.id.carouselDots);
        fmTotal = findViewById(R.id.fmTotal);
        tvTotal = findViewById(R.id.tvTotal);
        tvQty = findViewById(R.id.tvQty);
    }

    private void setupResultLauncher() {
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        loadSelectedProducts();
                        productAdapter.notifyDataSetChanged();
                        updateTotal(productList);
                    }
                }
        );
    }

    private void setupCarousel() {
        carouselViewPager.setAdapter(new ImageSliderAdapter(imageList));

        // Panggil addDots setelah layout selesai
        carouselViewPager.post(() -> addDots(0));

        carouselViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                addDots(position);
                currentIndex = position + 1;
                handler.removeCallbacks(carouselRunnable);
                handler.postDelayed(carouselRunnable, 3000);
            }
        });

        startAutoSlide();
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
            int margin = (int) (2 * getResources().getDisplayMetrics().density);
            params.setMargins(margin, 0, margin, 0);
            carouselDots.addView(dots[i], params);
        }
    }

    private void startAutoSlide() {
        carouselRunnable = new Runnable() {
            @Override
            public void run() {
                if (currentIndex >= imageList.size()) currentIndex = 0;
                carouselViewPager.setCurrentItem(currentIndex++, true);
                handler.postDelayed(this, 3000);
            }
        };
        handler.postDelayed(carouselRunnable, 3000);
    }

    private void setupProductList() {
        productList = ProductManager.getInstance().getProducts();
        loadSelectedProducts();
        productAdapter = new ProductAdapter(productList, false, this);
        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        rvProducts.setAdapter(productAdapter);
    }

    @Override
    public void updateTotal(List<Product> products) {
        int totalQty = 0;
        int totalPrice = 0;

        for (Product p : products) {
            int qty = p.getQuantity();
            if (qty > 0) {
                totalQty += qty;
                try {
                    int price = Integer.parseInt(
                            p.getPrice()
                                    .replace("Rp", "")
                                    .replaceAll("[^\\d]", "")
                                    .trim()
                    );
                    totalPrice += price * qty;
                } catch (NumberFormatException ignored) {
                }
            }
        }

        if (totalQty > 0) {
            fmTotal.setVisibility(View.VISIBLE);
            int paddingBottom = (int) (72 * getResources().getDisplayMetrics().density);
            lnPilihanProduk.setPadding(
                    lnPilihanProduk.getPaddingLeft(),
                    lnPilihanProduk.getPaddingTop(),
                    lnPilihanProduk.getPaddingRight(),
                    paddingBottom
            );

            NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
            formatter.setMaximumFractionDigits(0);
            tvTotal.setText(formatter.format(totalPrice));
            tvQty.setText(totalQty + " Produk");
        } else {
            fmTotal.setVisibility(View.GONE);
            lnPilihanProduk.setPadding(
                    lnPilihanProduk.getPaddingLeft(),
                    lnPilihanProduk.getPaddingTop(),
                    lnPilihanProduk.getPaddingRight(),
                    0
            );
        }

        saveSelectedProducts(getSelectedProducts());
    }

    private List<Product> getSelectedProducts() {
        List<Product> selected = new ArrayList<>();
        for (Product p : productList) {
            if (p.getQuantity() > 0) {
                selected.add(p);
            }
        }
        return selected;
    }

    private void saveSelectedProducts(List<Product> selectedProducts) {
        String json = gson.toJson(selectedProducts);
        sharedPreferences.edit().putString(KEY_SELECTED_PRODUCTS, json).apply();
    }

    private void loadSelectedProducts() {
        String json = sharedPreferences.getString(KEY_SELECTED_PRODUCTS, null);
        if (json != null) {
            Type type = new TypeToken<List<Product>>() {}.getType();
            List<Product> savedProducts = gson.fromJson(json, type);
            for (Product original : productList) {
                original.setQuantity(0);
                for (Product saved : savedProducts) {
                    if (saved.getId().equals(original.getId())) {
                        original.setQuantity(saved.getQuantity());
                        break;
                    }
                }
            }
        }
    }

    private static class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder> {

        private final List<Integer> images;

        ImageSliderAdapter(List<Integer> images) {
            this.images = images;
        }

        @NonNull
        @Override
        public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ImageView imageView = new ImageView(parent.getContext());
            int margin = (int) (16 * parent.getContext().getResources().getDisplayMetrics().density);
            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
            params.setMargins(margin, 0, margin, 0);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return new ImageViewHolder(imageView);
        }

        @Override
        public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
            holder.imageView.setImageResource(images.get(position));
        }

        @Override
        public int getItemCount() {
            return images.size();
        }

        static class ImageViewHolder extends RecyclerView.ViewHolder {
            final ImageView imageView;

            ImageViewHolder(@NonNull ImageView imageView) {
                super(imageView);
                this.imageView = imageView;
            }
        }
    }
}
