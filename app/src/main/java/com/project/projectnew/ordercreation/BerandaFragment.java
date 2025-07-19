package com.project.projectnew.ordercreation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.project.projectnew.R;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BerandaFragment extends Fragment implements ProductAdapter.OnQuantityChangedListener {

    private RecyclerView rvProducts;
    private FrameLayout fmTotal;
    private TextView tvTotal, tvQty;
    private Toolbar toolbar;
    private LinearLayout searchBarLayout;
    private List<Product> productList = new ArrayList<>();
    private ProductAdapter productAdapter;
    private AppDatabase db;
    private ExecutorService executorService;
    private Handler mainThreadHandler;
    private ViewPager2 carouselViewPager;
    private LinearLayout carouselDots;
    private ImageView[] dots;
    private final Handler carouselHandler = new Handler(Looper.getMainLooper());
    private Runnable carouselRunnable;
    private final List<Integer> imageList = Arrays.asList(
            R.drawable.img_banner_1,
            R.drawable.img_banner_2,
            R.drawable.img_banner_3
    );
    private int originalBottomPadding;
    private ImageButton btnNotifikasi; // <-- Tambahkan variabel untuk tombol notifikasi

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beranda, container, false);

        initViews(view);
        setupCategoryClickListeners(view);

        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        }

        db = AppDatabase.getDatabase(getContext());
        executorService = Executors.newSingleThreadExecutor();
        mainThreadHandler = new Handler(Looper.getMainLooper());

        setupCarousel();
        setupProductAdapter();

        fmTotal.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), KeranjangActivity.class);
            startActivity(intent);
        });

        searchBarLayout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CariProdukActivity.class);
            startActivity(intent);
        });

        // --- PERUBAHAN DI SINI ---
        btnNotifikasi.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), NotifikasiActivity.class);
            startActivity(intent);
        });

        return view;
    }

    private void setupCategoryClickListeners(View view) {
        view.findViewById(R.id.kategori_makanan_minuman).setOnClickListener(v -> navigateToSearchWithCategory("Makanan & Minuman"));
        view.findViewById(R.id.kategori_perawatan_rumah).setOnClickListener(v -> navigateToSearchWithCategory("Perawatan Rumah"));
        view.findViewById(R.id.kategori_perlengkapan_mandi).setOnClickListener(v -> navigateToSearchWithCategory("Perlengkapan Mandi"));
        view.findViewById(R.id.kategori_gas_air).setOnClickListener(v -> navigateToSearchWithCategory("Gas & Air"));
        view.findViewById(R.id.kategori_perlengkapan_listrik).setOnClickListener(v -> navigateToSearchWithCategory("Perlengkapan Listrik"));
    }

    private void navigateToSearchWithCategory(String categoryName) {
        if (getActivity() == null) return;
        Intent intent = new Intent(getActivity(), CariProdukActivity.class);
        intent.putExtra("CATEGORY_QUERY", categoryName);
        startActivity(intent);
    }

    private void updateTotalDisplay() {
        if(executorService == null) return;
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
                if(isAdded()) {
                    if (finalTotalQty > 0) {
                        fmTotal.setVisibility(View.VISIBLE);
                        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
                        formatter.setMaximumFractionDigits(0);
                        tvTotal.setText(formatter.format(finalTotalPrice));
                        tvQty.setText(finalTotalQty + " Produk");
                        rvProducts.setPadding(
                                rvProducts.getPaddingLeft(),
                                rvProducts.getPaddingTop(),
                                rvProducts.getPaddingRight(),
                                dpToPx(72)
                        );
                    } else {
                        fmTotal.setVisibility(View.GONE);
                        rvProducts.setPadding(
                                rvProducts.getPaddingLeft(),
                                rvProducts.getPaddingTop(),
                                rvProducts.getPaddingRight(),
                                originalBottomPadding
                        );
                    }
                }
            });
        });
    }

    private int dpToPx(int dp) {
        if (getContext() == null) {
            return dp;
        }
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                getContext().getResources().getDisplayMetrics()
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        loadProductsFromDb();
        startAutoSlide();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (carouselHandler != null) {
            carouselHandler.removeCallbacksAndMessages(null);
        }
    }

    private void initViews(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        rvProducts = view.findViewById(R.id.rvProducts);
        fmTotal = view.findViewById(R.id.fmTotal);
        tvTotal = view.findViewById(R.id.tvTotal);
        tvQty = view.findViewById(R.id.tvQty);
        carouselViewPager = view.findViewById(R.id.carouselViewPager);
        carouselDots = view.findViewById(R.id.carouselDots);
        searchBarLayout = view.findViewById(R.id.search_bar_layout);
        originalBottomPadding = rvProducts.getPaddingBottom();
        // --- PERUBAHAN DI SINI ---
        // Cari ID tombol notifikasi dari layout toolbar
        btnNotifikasi = toolbar.findViewById(R.id.btnNotifikasi);
    }

    private void setupProductAdapter() {
        productAdapter = new ProductAdapter(productList, false, this);
        rvProducts.setLayoutManager(new LinearLayoutManager(getContext()));
        rvProducts.setAdapter(productAdapter);
    }

    private void loadProductsFromDb() {
        if (executorService == null) return;
        executorService.execute(() -> {
            List<Product> productsFromDb = db.productDao().getAllProducts();
            mainThreadHandler.post(() -> {
                if(isAdded() && productAdapter != null) {
                    productList.clear();
                    productList.addAll(productsFromDb);
                    productAdapter.notifyDataSetChanged();
                    updateTotalDisplay();
                }
            });
        });
    }

    @Override
    public void onQuantityChanged(Product product) {
        if (executorService == null) return;
        executorService.execute(() -> db.productDao().updateProduct(product));
        updateTotalDisplay();
    }


    private void setupCarousel() {
        ImageSliderAdapter sliderAdapter = new ImageSliderAdapter(imageList);
        carouselViewPager.setAdapter(sliderAdapter);
        int realCount = imageList.size();
        if (realCount > 0) {
            int middlePosition = Integer.MAX_VALUE / 2;
            int initialPosition = middlePosition - (middlePosition % realCount);
            carouselViewPager.setCurrentItem(initialPosition, false);
            carouselViewPager.post(() -> addDots(initialPosition));
        }
    }

    private void addDots(int position) {
        if (carouselDots == null || imageList.isEmpty() || getContext() == null) return;
        int realPosition = position % imageList.size();
        carouselDots.removeAllViews();
        dots = new ImageView[imageList.size()];
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(getContext());
            dots[i].setImageResource(i == realPosition ? R.drawable.dot_active : R.drawable.dot_inactive);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);
            carouselDots.addView(dots[i], params);
        }
    }

    private void startAutoSlide() {
        if (carouselHandler != null) {
            carouselHandler.removeCallbacksAndMessages(null);
        }
        carouselRunnable = () -> {
            int nextItem = carouselViewPager.getCurrentItem() + 1;
            carouselViewPager.setCurrentItem(nextItem, true);
        };
        carouselViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                addDots(position);
                carouselHandler.removeCallbacks(carouselRunnable);
                carouselHandler.postDelayed(carouselRunnable, 3000);
            }
        });
        carouselHandler.postDelayed(carouselRunnable, 3000);
    }

    private static class ImageSliderAdapter extends RecyclerView.Adapter<ImageViewHolder> {
        private final List<Integer> images;
        ImageSliderAdapter(List<Integer> images) { this.images = images; }
        @NonNull @Override
        public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carousel_banner, parent, false);
            return new ImageViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
            if (images != null && !images.isEmpty()) {
                int realPosition = position % images.size();
                holder.bannerImage.setImageResource(images.get(realPosition));
            }
        }
        @Override
        public int getItemCount() { return images.isEmpty() ? 0 : Integer.MAX_VALUE; }
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        final ImageView bannerImage;
        ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            bannerImage = itemView.findViewById(R.id.imageViewBanner);
        }
    }
}