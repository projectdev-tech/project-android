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
    private KeranjangPagerAdapter adapter;
    private LinearLayout btnBeranda; // Ganti dari Button ke LinearLayout

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        btnBeranda = findViewById(R.id.btnBeranda); // Ambil dari layout XML

        adapter = new KeranjangPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        // Event klik LinearLayout
        btnBeranda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PesananActivity.this, BerandaActivity.class);
                startActivity(intent);
                finish(); // Optional
            }
        });
    }
}
