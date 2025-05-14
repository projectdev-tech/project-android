package com.project.projectnew.ordercreation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class NotifikasiPagerAdapter extends FragmentStateAdapter {

    public NotifikasiPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new TransaksiFragment();
            case 1:
                return new PromoFragment();
            case 2:
                return new InfoFragment();
            default:
                return new TransaksiFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
