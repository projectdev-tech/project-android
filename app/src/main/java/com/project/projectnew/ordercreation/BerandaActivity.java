package com.project.projectnew.ordercreation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.project.projectnew.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class BerandaActivity extends AppCompatActivity implements ProductAdapter.TotalUpdateListener {

    private static final int REQUEST_CODE_KERANJANG = 100;

    LinearLayout btnKeranjang;
    RecyclerView rvProducts;
    ProductAdapter productAdapter;
    List<Product> productList;

    ViewPager2 carouselViewPager;
    LinearLayout carouselDots;
    ImageView[] dots;
    List<Integer> imageList = Arrays.asList(
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3
    );

    FrameLayout fmTotal;
    TextView tvTotal, tvQty;
    LinearLayout lnPilihanProduk;

    private Handler handler = new Handler();
    private Runnable runnable;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);

        ImageView notificationIcon = findViewById(R.id.notificationIcon);
        LinearLayout notificationIconNavbar = findViewById(R.id.notificationIconNavbar);

        notificationIcon.setOnClickListener(v -> {
            Intent intent = new Intent(BerandaActivity.this, MenuNotifikasi.class);
            startActivity(intent);
        });
        notificationIconNavbar.setOnClickListener(v -> {
            Intent intent = new Intent(BerandaActivity.this, MenuNotifikasi.class);
            startActivity(intent);
        });

        lnPilihanProduk = findViewById(R.id.lnPilihanProduk);
        carouselViewPager = findViewById(R.id.carouselViewPager);
        carouselDots = findViewById(R.id.carouselDots);
        fmTotal = findViewById(R.id.fmTotal);
        tvTotal = findViewById(R.id.tvTotal);
        tvQty = findViewById(R.id.tvQty);
        rvProducts = findViewById(R.id.rvProducts);
        btnKeranjang = findViewById(R.id.btnKeranjang);

        carouselViewPager.setAdapter(new ImageSliderAdapter(imageList));
        carouselViewPager.post(() -> addDots(0));

        carouselViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                addDots(position);
                currentIndex = position + 1;
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3000);
            }
        });

        autoSlideImages();

        productList = ProductManager.getInstance().getProducts();
        productAdapter = new ProductAdapter(productList, this);
        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        rvProducts.setAdapter(productAdapter);

        btnKeranjang.setOnClickListener(v -> {
            Intent intent = new Intent(BerandaActivity.this, Keranjang2Activity.class);
            intent.putExtra("selected_products", new ArrayList<>(getSelectedProducts()));
            startActivityForResult(intent, REQUEST_CODE_KERANJANG);
        });

        fmTotal.setOnClickListener(v -> {
            Intent intent = new Intent(BerandaActivity.this, KeranjangActivity.class);
            intent.putExtra("selected_products", new ArrayList<>(getSelectedProducts()));
            startActivityForResult(intent, REQUEST_CODE_KERANJANG);
        });

        updateTotal(productList);
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
            int margin = (int) (getResources().getDisplayMetrics().density * 2);
            params.setMargins(margin, 0, margin, 0);
            carouselDots.addView(dots[i], params);
        }
    }

    private void autoSlideImages() {
        runnable = () -> {
            if (currentIndex >= imageList.size()) currentIndex = 0;
            carouselViewPager.setCurrentItem(currentIndex++, true);
            handler.postDelayed(runnable, 3000);
        };
        handler.postDelayed(runnable, 3000);
    }

    private List<Product> getSelectedProducts() {
        List<Product> selected = new ArrayList<>();
        for (Product p : productList) {
            if (p.getQuantity() > 0) selected.add(p);
        }
        return selected;
    }

    @Override
    public void updateTotal(List<Product> products) {
        int totalQty = 0;
        int totalPrice = 0;

        for (Product p : products) {
            int qty = p.getQuantity();
            if (qty > 0) {
                totalQty += qty;
                String priceStr = p.getPrice().replace("Rp", "").replace(".", "").replace(",", "").trim();
                try {
                    int priceInt = Integer.parseInt(priceStr);
                    totalPrice += priceInt * qty;
                } catch (NumberFormatException ignored) {}
            }
        }

        if (totalQty > 0) {
            fmTotal.setVisibility(View.VISIBLE);
            int paddingBottomDp = (int) (72 * getResources().getDisplayMetrics().density);
            lnPilihanProduk.setPadding(
                    lnPilihanProduk.getPaddingLeft(),
                    lnPilihanProduk.getPaddingTop(),
                    lnPilihanProduk.getPaddingRight(),
                    paddingBottomDp
            );

            Locale localeID = new Locale("in", "ID");
            NumberFormat formatter = NumberFormat.getCurrencyInstance(localeID);
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_KERANJANG && resultCode == RESULT_OK && data != null) {
            ArrayList<Product> updatedList = (ArrayList<Product>) data.getSerializableExtra("updated_products");
            if (updatedList != null) {
                for (Product updated : updatedList) {
                    for (Product original : productList) {
                        if (updated.getId().equals(original.getId())) {
                            original.setQuantity(updated.getQuantity());
                            break;
                        }
                    }
                }
                productAdapter.notifyDataSetChanged();
                updateTotal(productList);
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
            int marginHorizontal = (int) (16 * parent.getContext().getResources().getDisplayMetrics().density);
            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
            params.setMargins(marginHorizontal, 0, marginHorizontal, 0);
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
            ImageView imageView;

            ImageViewHolder(@NonNull ImageView imageView) {
                super(imageView);
                this.imageView = imageView;
            }
        }
    }
}
