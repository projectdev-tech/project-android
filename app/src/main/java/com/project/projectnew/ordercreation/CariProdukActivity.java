package com.project.projectnew.ordercreation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.project.projectnew.R;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// --- PERUBAHAN DI SINI: Mengimplementasikan listener yang baru ---
public class CariProdukActivity extends AppCompatActivity implements ProductAdapter.OnQuantityChangedListener {

    private SearchView searchView;
    private RecyclerView rvSuggestions;
    private SuggestionsAdapter suggestionsAdapter;
    private SearchHistoryManager historyManager;
    private List<String> suggestionsList = new ArrayList<>();

    private RecyclerView rvProducts;
    private ProductAdapter productAdapter;
    private List<Product> productListForAdapter = new ArrayList<>();
    private List<Product> originalProductList = new ArrayList<>();
    private LinearLayout layoutKategori;
    private LinearLayout layoutPilihanProduk;
    private ImageButton btnSearchAction;

    private FrameLayout fmTotal;
    private TextView tvTotal, tvQty;

    private AppDatabase db;
    private ExecutorService executorService;
    private Handler mainThreadHandler;

    private int originalRvPaddingLeft, originalRvPaddingTop, originalRvPaddingRight, originalRvPaddingBottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_produk);

        db = AppDatabase.getDatabase(this);
        executorService = Executors.newSingleThreadExecutor();
        mainThreadHandler = new Handler(Looper.getMainLooper());

        initViews();
        setupSuggestionsRecyclerView();
        setupProductRecyclerView();
        customizeSearchView(searchView);
        setupSearchViewListener();
        setupFocusListener();
        setupCategoryClickListeners();

        reloadDataAndApplyFilter(() -> {
            String categoryQuery = getIntent().getStringExtra("CATEGORY_QUERY");
            if (categoryQuery != null && !categoryQuery.isEmpty()) {
                handleCategoryClick(categoryQuery);
            } else {
                displayInitialSuggestions();
            }
        });

        fmTotal.setOnClickListener(v -> {
            Intent intent = new Intent(CariProdukActivity.this, KeranjangActivity.class);
            startActivity(intent);
        });

        btnSearchAction.setOnClickListener(v -> {
            searchView.setQuery(searchView.getQuery(), true);
        });
    }

    private void setupCategoryClickListeners() {
        findViewById(R.id.kategori_cari_makanan_minuman).setOnClickListener(v -> handleCategoryClick("Makanan & Minuman"));
        findViewById(R.id.kategori_cari_perawatan_rumah).setOnClickListener(v -> handleCategoryClick("Perawatan Rumah"));
        findViewById(R.id.kategori_cari_perlengkapan_mandi).setOnClickListener(v -> handleCategoryClick("Perlengkapan Mandi"));
        findViewById(R.id.kategori_cari_gas_air).setOnClickListener(v -> handleCategoryClick("Gas & Air"));
        findViewById(R.id.kategori_cari_perlengkapan_listrik).setOnClickListener(v -> handleCategoryClick("Perlengkapan Listrik"));
    }

    private void handleCategoryClick(String categoryName) {
        searchView.setQuery(categoryName, false);
        performSearch(categoryName, true);

        rvSuggestions.setVisibility(View.GONE);
        layoutPilihanProduk.setVisibility(View.GONE);
        layoutKategori.setVisibility(View.GONE);
        applyResultsOnlyStyleToRvProducts();
        searchView.clearFocus();

        searchView.post(this::hideDefaultCloseButton);
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadDataAndApplyFilter(null);
        hideDefaultCloseButton();
    }

    private void initViews() {
        historyManager = new SearchHistoryManager(this);
        searchView = findViewById(R.id.searchView);
        rvSuggestions = findViewById(R.id.rvSuggestions);
        rvProducts = findViewById(R.id.rvProducts);
        layoutKategori = findViewById(R.id.layoutKategori);
        layoutPilihanProduk = findViewById(R.id.layoutPilihanProduk);
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
        btnSearchAction = findViewById(R.id.imageButton2);

        fmTotal = findViewById(R.id.fmTotal);
        tvTotal = findViewById(R.id.tvTotal);
        tvQty = findViewById(R.id.tvQty);

        originalRvPaddingLeft = rvProducts.getPaddingLeft();
        originalRvPaddingTop = rvProducts.getPaddingTop();
        originalRvPaddingRight = rvProducts.getPaddingRight();
        originalRvPaddingBottom = rvProducts.getPaddingBottom();
    }

    private void setupProductRecyclerView() {
        // --- PERUBAHAN DI SINI: Menggunakan 'this' karena activity sudah implement listener yg benar ---
        productAdapter = new ProductAdapter(productListForAdapter, false, this);
        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        rvProducts.setAdapter(productAdapter);
    }

    private void reloadDataAndApplyFilter(Runnable onFinished) {
        if (executorService == null) return;
        executorService.execute(() -> {
            originalProductList.clear();
            originalProductList.addAll(db.productDao().getAllProducts());

            mainThreadHandler.post(() -> {
                String currentQuery = searchView.getQuery().toString();
                boolean isCategorySearch = (layoutKategori.getVisibility() == View.GONE) && !currentQuery.isEmpty();
                performSearch(currentQuery, isCategorySearch);
                updateTotalDisplay();

                if (onFinished != null) {
                    onFinished.run();
                }
            });
        });
    }

    // --- PERUBAHAN DI SINI: Metode updateTotal diganti dengan onQuantityChanged ---
    @Override
    public void onQuantityChanged(Product product) {
        if (executorService == null) return;
        // Langsung update produk yang berubah ke database
        executorService.execute(() -> db.productDao().updateProduct(product));
        // Hitung ulang dan perbarui tampilan total
        updateTotalDisplay();
    }

    private void updateTotalDisplay() {
        if (executorService == null) return;
        executorService.execute(() -> {
            List<Product> cartItems = db.productDao().getProductsInCart();
            int totalQty = 0;
            double totalPrice = 0;
            for (Product p : cartItems) {
                totalQty += p.getQuantity();
                try {
                    totalPrice += Double.parseDouble(p.getPrice().replaceAll("[^\\d]", "")) * p.getQuantity();
                } catch (NumberFormatException ignored) {}
            }
            int finalTotalQty = totalQty;
            double finalTotalPrice = totalPrice;
            mainThreadHandler.post(() -> {
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
                            dpToPx(80, this)
                    );

                } else {
                    fmTotal.setVisibility(View.GONE);
                    rvProducts.setPadding(
                            rvProducts.getPaddingLeft(),
                            rvProducts.getPaddingTop(),
                            rvProducts.getPaddingRight(),
                            originalRvPaddingBottom
                    );
                }
            });
        });
    }

    private void setupSuggestionsRecyclerView() {
        suggestionsAdapter = new SuggestionsAdapter(suggestionsList, suggestion -> {
            searchView.setQuery(suggestion, true);
        });
        rvSuggestions.setLayoutManager(new LinearLayoutManager(this));
        rvSuggestions.setAdapter(suggestionsAdapter);
    }

    private void setupFocusListener() {
        searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                searchView.post(this::hideDefaultCloseButton);
            }
        });
    }

    private void setupSearchViewListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null && !query.trim().isEmpty()) {
                    historyManager.saveSearchQuery(query);
                }

                rvSuggestions.setVisibility(View.GONE);
                layoutPilihanProduk.setVisibility(View.GONE);
                layoutKategori.setVisibility(View.GONE);
                applyResultsOnlyStyleToRvProducts();
                performSearch(query, false);

                searchView.clearFocus();
                hideDefaultCloseButton();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                hideDefaultCloseButton();

                if (newText.isEmpty()) {
                    rvSuggestions.setVisibility(View.VISIBLE);
                    layoutKategori.setVisibility(View.VISIBLE);
                    layoutPilihanProduk.setVisibility(View.VISIBLE);
                    resetRvProductsStyle();
                    productAdapter.updateData(new ArrayList<>(originalProductList));
                    displayInitialSuggestions();
                } else {
                    rvSuggestions.setVisibility(View.VISIBLE);
                    layoutKategori.setVisibility(View.VISIBLE);
                    layoutPilihanProduk.setVisibility(View.VISIBLE);
                    resetRvProductsStyle();
                    displayKeywordRecommendations(newText);
                }
                return true;
            }
        });
    }

    private void performSearch(String query, boolean isCategorySearch) {
        List<Product> filteredList = new ArrayList<>();
        if (query.isEmpty()) {
            filteredList.addAll(originalProductList);
        } else {
            String lowerCaseQuery = query.toLowerCase();
            for (Product product : originalProductList) {
                if (isCategorySearch) {
                    if (product.getCategory() != null && product.getCategory().equalsIgnoreCase(query)) {
                        filteredList.add(product);
                    }
                } else {
                    if (product.getName().toLowerCase().contains(lowerCaseQuery) ||
                            (product.getCategory() != null && product.getCategory().toLowerCase().contains(lowerCaseQuery))) {
                        filteredList.add(product);
                    }
                }
            }
        }
        productAdapter.updateData(filteredList);
    }

    private void displayInitialSuggestions() {
        List<String> combinedSuggestions = new ArrayList<>();
        List<String> history = historyManager.getSearchHistory();
        combinedSuggestions.addAll(history);

        List<String> frequent = getFrequentlySearched();
        for (String keyword : frequent) {
            if (combinedSuggestions.size() >= 4) {
                break;
            }
            if (!combinedSuggestions.contains(keyword)) {
                combinedSuggestions.add(keyword);
            }
        }

        suggestionsAdapter.updateData(combinedSuggestions);
    }

    private void displayKeywordRecommendations(String query) {
        List<String> recommendations = new ArrayList<>();
        String lowerCaseQuery = query.toLowerCase();

        for (String historyItem : historyManager.getSearchHistory()) {
            if (recommendations.size() >= 4) break;
            if (historyItem.toLowerCase().contains(lowerCaseQuery) && !recommendations.contains(historyItem)) {
                recommendations.add(historyItem);
            }
        }

        for (Product product : originalProductList) {
            if (recommendations.size() >= 4) break;
            if (product.getName().toLowerCase().contains(lowerCaseQuery) && !recommendations.contains(product.getName())) {
                recommendations.add(product.getName());
            }
        }

        for (Product product : originalProductList) {
            if (recommendations.size() >= 4) break;
            String category = product.getCategory();
            if (category != null && category.toLowerCase().contains(lowerCaseQuery) && !recommendations.contains(category)) {
                recommendations.add(category);
            }
        }

        if (recommendations.isEmpty()) {
            recommendations.add(query + " murah");
            recommendations.add("Promo " + query);
        }

        suggestionsAdapter.updateData(recommendations);
    }

    private List<String> getFrequentlySearched() {
        return Arrays.asList("Indomie", "Beras", "Minyak Goreng", "Kopi");
    }

    private void customizeSearchView(SearchView searchView) {
        int searchPlateId = getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchView.findViewById(searchPlateId);
        if (searchPlate != null) {
            searchPlate.setBackgroundColor(Color.TRANSPARENT);
        }
        int searchTextId = getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchText = searchView.findViewById(searchTextId);
        if (searchText != null) {
            searchText.setTextColor(Color.BLACK);
            searchText.setHintTextColor(Color.parseColor("#757575"));
            searchText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        }
        searchView.setIconified(false);
        searchView.requestFocus();
        hideDefaultCloseButton();
    }

    private void hideDefaultCloseButton() {
        int closeBtnId = getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeBtn = searchView.findViewById(closeBtnId);
        if (closeBtn != null) {
            closeBtn.setEnabled(false);
            closeBtn.setImageDrawable(null);
            closeBtn.setVisibility(View.GONE);
        }
    }

    private void applyResultsOnlyStyleToRvProducts() {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) rvProducts.getLayoutParams();
        params.topMargin = dpToPx(8, this);
        rvProducts.setLayoutParams(params);

        rvProducts.setPadding(
                originalRvPaddingLeft,
                dpToPx(16, this),
                originalRvPaddingRight,
                rvProducts.getPaddingBottom()
        );
    }

    private void resetRvProductsStyle() {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) rvProducts.getLayoutParams();
        params.topMargin = 0;
        rvProducts.setLayoutParams(params);

        rvProducts.setPadding(
                originalRvPaddingLeft,
                originalRvPaddingTop,
                originalRvPaddingRight,
                rvProducts.getPaddingBottom()
        );
    }

    private int dpToPx(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}