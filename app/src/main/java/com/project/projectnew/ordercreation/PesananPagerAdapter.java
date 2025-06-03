package com.project.projectnew.ordercreation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.annotation.Nullable;

public class PesananPagerAdapter extends FragmentPagerAdapter {

    private final String[] tabTitles = new String[]{"Belum Bayar","Dalam Proses", "Riwayat Pesanan"};

    public PesananPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new BelumBayarFragment();
            case 1:
                return new DalamProsesFragment();
            case 2:
                return new RiwayatPesananFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    // Untuk judul tab
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
