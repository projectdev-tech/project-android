package com.project.projectnew;



import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OrderDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan_selesai);

        // Setup bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation item selections
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        // Navigate to home
                        return true;
                    case R.id.navigation_orders:
                        // Already on orders
                        return true;
                    case R.id.navigation_profile:
                        // Navigate to profile
                        return true;
                }
                return false;
            }
        });

        // Set the active tab to orders
        bottomNavigationView.setSelectedItemId(R.id.navigation_orders);

        // Setup for the order detail expansion
        @SuppressLint("MissingInflatedId") View detailsSection = findViewById(R.id.details_section);
        ImageView expandIcon = findViewById(R.id.expand_icon);

        detailsSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle expansion logic would go here
                // This would show/hide additional details
            }
        });

        // Setup the "Lihat Lainnya" (View More) section
        View viewMoreSection = findViewById(R.id.view_more_section);
        viewMoreSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle expansion of more items if there are multiple items
            }
        });

        // Setup tab indicators at the top
        View processTab = findViewById(R.id.tab_process);
        View sentTab = findViewById(R.id.tab_sent);
        View completedTab = findViewById(R.id.tab_completed);

        // The completed tab is active in the image
        processTab.setAlpha(0.5f);
        sentTab.setAlpha(0.5f);
        completedTab.setAlpha(1.0f);
    }
}
}
