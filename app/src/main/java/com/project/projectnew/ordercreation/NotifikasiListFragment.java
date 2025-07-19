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
        View layoutKosong = view.findViewById(R.id.layoutKosong);
        rvNotifikasi.setLayoutManager(new LinearLayoutManager(getContext()));

        String tipe = getArguments() != null ? getArguments().getString(ARG_TIPE) : "";

        List<Notifikasi> allNotif = createDummyNotif();
        List<Notifikasi> filteredNotif = allNotif.stream()
                .filter(n -> n.getTipe().equalsIgnoreCase(tipe))
                .collect(Collectors.toList());

        if (filteredNotif.isEmpty()) {
            rvNotifikasi.setVisibility(View.GONE);
            layoutKosong.setVisibility(View.VISIBLE);
        } else {
            rvNotifikasi.setVisibility(View.VISIBLE);
            layoutKosong.setVisibility(View.GONE);
            NotifikasiAdapter adapter = new NotifikasiAdapter(filteredNotif);
            rvNotifikasi.setAdapter(adapter);
        }

        return view;
    }

    private List<Notifikasi> createDummyNotif() {
        List<Notifikasi> list = new ArrayList<>();

        // --- PERUBAHAN KEDUA DI SINI ---
        // Gunakan nomor order statis yang sama
        list.add(new Notifikasi("Transaksi", "Pesanan Dikirim", "Pesanan Anda telah dikirim oleh kurir.", "17 Jul 2025", R.drawable.ic_document_download, "DUMMY-ORDER-3"));
        list.add(new Notifikasi("Transaksi", "Pembayaran Berhasil", "Pembayaran Anda telah kami terima.", "16 Jul 2025", R.drawable.ic_document_download, "DUMMY-ORDER-4"));

        list.add(new Notifikasi("Promo", "Diskon Kemerdekaan!", "Dapatkan diskon hingga 70% untuk produk pilihan.", "15 Jul 2025", R.drawable.ic_tag, ""));
        list.add(new Notifikasi("Info", "Pembaruan Aplikasi", "Versi terbaru telah tersedia. Segera perbarui!", "13 Jul 2025", R.drawable.ic_direct_inbox, ""));

        return list;
    }

    // Metode bantuan ini tidak lagi diperlukan, bisa dihapus
    // private String getDummyNoOrderForNotif(int sequence) { ... }
}