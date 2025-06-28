package com.project.projectnew.ordercreation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.project.projectnew.R;

public class PesananFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pesanan, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager viewPager = view.findViewById(R.id.viewPager);

        // PENTING: Gunakan getChildFragmentManager() di dalam Fragment
        PesananPagerAdapter pagerAdapter = new PesananPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        // Cek apakah ada argumen untuk membuka tab tertentu
        if (getArguments() != null) {
            int tabIndex = getArguments().getInt("TAB_INDEX", 0); // Default ke tab pertama
            viewPager.setCurrentItem(tabIndex);
        }

        return view;
    }
}