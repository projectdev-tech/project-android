package com.project.projectnew;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.projectnew.ordercreation.BerandaFragment;
import com.project.projectnew.ordercreation.PesananFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(navListener);

        // Logika baru untuk menangani intent dari activity lain
        if (savedInstanceState == null) {
            handleIntent(getIntent());
        }
    }

    private void handleIntent(Intent intent) {
        String fragmentToLoad = intent.getStringExtra("FRAGMENT_TO_LOAD");

        if (fragmentToLoad != null && fragmentToLoad.equals("PESANAN")) {
            PesananFragment pesananFragment = new PesananFragment();
            // Cek apakah ada tab spesifik yang diminta
            int tabIndex = intent.getIntExtra("TAB_INDEX", 0);
            Bundle args = new Bundle();
            args.putInt("TAB_INDEX", tabIndex);
            pesananFragment.setArguments(args);

            loadFragment(pesananFragment);
            bottomNav.setSelectedItemId(R.id.nav_pesanan); // Sorot item menu "Pesanan"
        } else {
            // Default, muat BerandaFragment
            loadFragment(new BerandaFragment());
            bottomNav.setSelectedItemId(R.id.nav_beranda);
        }
    }

    private final BottomNavigationView.OnItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;

                int itemId = item.getItemId();
                if (itemId == R.id.nav_beranda) {
                    selectedFragment = new BerandaFragment();
                } else if (itemId == R.id.nav_pesanan) {
                    selectedFragment = new PesananFragment();
                } else if (itemId == R.id.nav_chat) {
                    // TODO: Buat ChatFragment
                } else if (itemId == R.id.nav_profil) {
                    // TODO: Buat ProfilFragment
                }

                if (selectedFragment != null) {
                    loadFragment(selectedFragment);
                    return true;
                }
                return false;
            };

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}