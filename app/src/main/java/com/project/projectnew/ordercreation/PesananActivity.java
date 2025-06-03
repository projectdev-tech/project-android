package com.project.projectnew.ordercreation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.project.projectnew.R;

public class PesananActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PesananPagerAdapter adapter;
    private LinearLayout btnBeranda; // Tombol Beranda

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        btnBeranda = findViewById(R.id.btnBeranda);

        adapter = new PesananPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        // Navigasi ke Beranda saat tombol btnBeranda ditekan
        btnBeranda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBeranda();
            }
        });
    }

    // Override tombol back agar kembali ke Beranda, bukan keluar aplikasi
    @Override
    public void onBackPressed() {
        goToBeranda();
    }

    private void goToBeranda() {
        Intent intent = new Intent(PesananActivity.this, BerandaActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
