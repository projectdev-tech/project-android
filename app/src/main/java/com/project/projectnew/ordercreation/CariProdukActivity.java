package com.project.projectnew.ordercreation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

public class CariProdukActivity extends AppCompatActivity implements ProductAdapter.TotalUpdateListener {

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

        // --- PERBAIKAN DI SINI ---
        // Memanggil metode untuk menampilkan riwayat/sugesti saat activity pertama kali dibuat
        displayInitialSuggestions();
        // -------------------------

        loadInitialProducts();

        fmTotal.setOnClickListener(v -> {
            Intent intent = new Intent(CariProdukActivity.this, KeranjangActivity.class);
            startActivity(intent);
        });

        btnSearchAction.setOnClickListener(v -> {
            searchView.setQuery(searchView.getQuery(), true);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadInitialProducts();

        // Pastikan sugesti juga diperbarui saat kembali ke halaman ini
        if (searchView.getQuery().toString().isEmpty()){
            displayInitialSuggestions();
        }
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
    }

    private void setupProductRecyclerView() {
        productAdapter = new ProductAdapter(productListForAdapter, false, this);
        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        rvProducts.setAdapter(productAdapter);
    }

    private void loadInitialProducts() {
        executorService.execute(() -> {
            List<Product> productsFromDb = db.productDao().getAllProducts();
            mainThreadHandler.post(() -> {
                originalProductList.clear();
                originalProductList.addAll(productsFromDb);

                if (searchView.getQuery().toString().isEmpty()) {
                    productAdapter.updateData(new ArrayList<>(originalProductList));
                }

                updateTotalDisplay();
            });
        });
    }

    @Override
    public void updateTotal(List<Product> updatedProducts) {
        executorService.execute(() -> {
            for (Product updatedProduct : updatedProducts) {
                db.productDao().updateProduct(updatedProduct);
            }
            mainThreadHandler.post(this::updateTotalDisplay);
        });
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
                } else {
                    fmTotal.setVisibility(View.GONE);
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
                layoutKategori.setVisibility(View.GONE);
                layoutPilihanProduk.setVisibility(View.GONE);
                performSearch(query);

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
                    displayInitialSuggestions();
                    productAdapter.updateData(new ArrayList<>(originalProductList));
                } else {
                    rvSuggestions.setVisibility(View.VISIBLE);
                    layoutKategori.setVisibility(View.VISIBLE);
                    layoutPilihanProduk.setVisibility(View.VISIBLE);
                    displayKeywordRecommendations(newText);
                }
                return true;
            }
        });
    }

    private void performSearch(String query) {
        List<Product> filteredList = new ArrayList<>();
        if (query.isEmpty()) {
            filteredList.addAll(originalProductList);
        } else {
            for (Product product : originalProductList) {
                if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(product);
                }
            }
        }
        productAdapter.updateData(filteredList);
    }

    private void displayInitialSuggestions() {
        List<String> history = historyManager.getSearchHistory();
        if (history.isEmpty()) {
            suggestionsList = getFrequentlySearched();
        } else {
            suggestionsList = history;
        }
        suggestionsAdapter.updateData(suggestionsList);
    }

    private void displayKeywordRecommendations(String query) {
        List<String> recommendations = new ArrayList<>();
        if (query.toLowerCase().contains("kopi")) {
            recommendations.add("Kopi Kapal Api");
            recommendations.add("Kopi ABC Susu");
        } else if (query.toLowerCase().contains("indo")) {
            recommendations.add("Indomie Goreng");
            recommendations.add("Indomilk Coklat");
        } else {
            recommendations.add(query + " murah");
            recommendations.add("Promo " + query);
        }
        suggestionsAdapter.updateData(recommendations);
    }

    private List<String> getFrequentlySearched() {
        return Arrays.asList("Indomie", "Beras", "Minyak Goreng", "Kopi", "Gula");
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
            closeBtn.setVisibility(View.GONE);
        }
    }
}