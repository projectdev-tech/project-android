package com.project.projectnew.ordercreation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.project.projectnew.R;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NotifikasiListFragment extends Fragment {

    private static final String ARG_TIPE = "tipe_notifikasi";

    public static NotifikasiListFragment newInstance(String tipe) {
        NotifikasiListFragment fragment = new NotifikasiListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TIPE, tipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifikasi_list, container, false);

        RecyclerView rvNotifikasi = view.findViewById(R.id.rvNotifikasi);
        View layoutKosong = view.findViewById(R.id.layoutKosong); // Ambil referensi layout 'kosong'
        rvNotifikasi.setLayoutManager(new LinearLayoutManager(getContext()));

        String tipe = getArguments() != null ? getArguments().getString(ARG_TIPE) : "";

        // Filter data dummy berdasarkan tipe
        List<Notifikasi> allNotif = createDummyNotif();
        List<Notifikasi> filteredNotif = allNotif.stream()
                .filter(n -> n.getTipe().equalsIgnoreCase(tipe))
                .collect(Collectors.toList());

        // --- PERUBAHAN UTAMA DI SINI ---
        // Cek apakah daftar yang sudah difilter itu kosong atau tidak
        if (filteredNotif.isEmpty()) {
            // Jika kosong, sembunyikan RecyclerView dan tampilkan layout 'kosong'
            rvNotifikasi.setVisibility(View.GONE);
            layoutKosong.setVisibility(View.VISIBLE);
        } else {
            // Jika ada isinya, tampilkan RecyclerView dan sembunyikan layout 'kosong'
            rvNotifikasi.setVisibility(View.VISIBLE);
            layoutKosong.setVisibility(View.GONE);

            // Pasang adapter hanya jika ada data
            NotifikasiAdapter adapter = new NotifikasiAdapter(filteredNotif);
            rvNotifikasi.setAdapter(adapter);
        }

        return view;
    }

    // Data dummy untuk notifikasi
    private List<Notifikasi> createDummyNotif() {
        List<Notifikasi> list = new ArrayList<>();

        list.add(new Notifikasi("Transaksi", "Pesanan Dikirim", "Pesanan #123456 telah dikirim oleh kurir.", "17 Jul 2025", R.drawable.ic_document_download));
        list.add(new Notifikasi("Transaksi", "Pembayaran Berhasil", "Pembayaran untuk pesanan #123455 berhasil.", "16 Jul 2025", R.drawable.ic_document_download));

        list.add(new Notifikasi("Promo", "Diskon Kemerdekaan!", "Dapatkan diskon hingga 70% untuk produk pilihan.", "15 Jul 2025", R.drawable.ic_tag));
        list.add(new Notifikasi("Promo", "Gratis Ongkir Kembali", "Nikmati gratis ongkir tanpa minimum belanja.", "14 Jul 2025", R.drawable.ic_tag));

        // Sengaja kosongkan "Info" untuk mengetes
        // list.add(new Notifikasi("Info", "Pembaruan Aplikasi", "Versi terbaru telah tersedia. Segera perbarui!", "13 Jul 2025", R.drawable.ic_direct_inbox));

        return list;
    }
}