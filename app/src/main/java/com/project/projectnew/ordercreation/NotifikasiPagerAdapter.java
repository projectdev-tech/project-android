package com.project.projectnew.ordercreation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class NotifikasiPagerAdapter extends FragmentPagerAdapter {

    private final String[] tabTitles = new String[]{"Transaksi", "Promo", "Info"};

    public NotifikasiPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return NotifikasiListFragment.newInstance("Transaksi");
            case 1:
                return NotifikasiListFragment.newInstance("Promo");
            case 2:
                return NotifikasiListFragment.newInstance("Info");
            default:
                return new Fragment();
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}