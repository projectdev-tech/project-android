package com.project.projectnew.ordercreation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.annotation.Nullable;

public class PesananPagerAdapter extends FragmentPagerAdapter {

    // Judul tab tetap sama
    private final String[] tabTitles = new String[]{"Belum Bayar", "Dalam Proses", "Dikirim", "Selesai"};

    public PesananPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // Logika di sini diubah total untuk menggunakan satu Fragment yang sama
        // dengan argumen yang berbeda untuk setiap tab.
        switch (position){
            case 0:
                // Mengirim status "Menunggu Pembayaran" untuk tab pertama
                return OrderListFragment.newInstance("Menunggu Pembayaran");
            case 1:
                // Mengirim status "Menunggu Konfirmasi" untuk tab kedua
                return OrderListFragment.newInstance("Menunggu Konfirmasi");
            case 2:
                // Mengirim status "Pesanan Dikirim" untuk tab ketiga
                return OrderListFragment.newInstance("Pesanan Dikirim");
            case 3:
                // Mengirim status "Pesanan Diterima" untuk tab keempat
                return OrderListFragment.newInstance("Pesanan Diterima");
            default:
                // Seharusnya tidak pernah terjadi, tapi baik untuk memiliki fallback
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