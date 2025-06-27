package com.project.projectnew.ordercreation;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue; // <-- PENAMBAHAN 1: Import kelas TypedValue
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.project.projectnew.R;

public class CariProdukActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_produk);

        ImageButton btnBack = findViewById(R.id.btnBack);
        SearchView searchView = findViewById(R.id.searchView);

        btnBack.setOnClickListener(v -> {
            finish();
        });

        // Panggil metode untuk kustomisasi tampilan
        customizeSearchView(searchView);

        // Memastikan keyboard langsung muncul dan fokus
        searchView.setIconified(false);
        searchView.requestFocus();
    }

    private void customizeSearchView(SearchView searchView) {
        // Menghilangkan Garis Bawah (Underline)
        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchView.findViewById(searchPlateId);
        if (searchPlate != null) {
            searchPlate.setBackgroundColor(Color.TRANSPARENT);
        }

        // Menghilangkan Ikon Kaca Pembesar (Search Icon) di dalam
        int magIconId = searchView.getContext().getResources().getIdentifier("android:id/search_mag_icon", null, null);
        ImageView magIcon = searchView.findViewById(magIconId);
        if (magIcon != null) {
            magIcon.setVisibility(View.GONE);
        }

        // Mengubah warna dan ukuran teks
        int searchTextId = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchText = searchView.findViewById(searchTextId);
        if (searchText != null) {
            searchText.setTextColor(Color.BLACK);

            // --- PERUBAHAN DI SINI ---
            // 1. Mengatur warna hint menjadi #757575
            searchText.setHintTextColor(Color.parseColor("#757575"));

            // 2. Mengatur ukuran font menjadi 14sp
            searchText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        }

        // Listener untuk memastikan tombol 'X' selalu tersembunyi
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                int closeBtnId = searchView.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
                ImageView closeBtn = searchView.findViewById(closeBtnId);
                if (closeBtn != null) {
                    closeBtn.setVisibility(View.GONE);
                }
                return true;
            }
        });

        // Panggilan awal untuk menyembunyikan tombol X
        int closeBtnId = searchView.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeBtn = searchView.findViewById(closeBtnId);
        if (closeBtn != null) {
            closeBtn.setVisibility(View.GONE);
        }
    }
}