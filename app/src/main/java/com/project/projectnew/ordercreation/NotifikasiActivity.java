package com.project.projectnew.ordercreation;

import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.project.projectnew.R;

public class NotifikasiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi);

        ImageButton btnBack = findViewById(R.id.btnBack);
        TabLayout tabLayout = findViewById(R.id.tabLayoutNotifikasi);
        ViewPager viewPager = findViewById(R.id.viewPagerNotifikasi);

        NotifikasiPagerAdapter adapter = new NotifikasiPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        btnBack.setOnClickListener(v -> finish());
    }
}