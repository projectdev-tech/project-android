package com.project.projectnew.ordercreation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.project.projectnew.R;

public class ProfilFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        // Menambahkan listener agar tombol bisa diklik (untuk sementara menampilkan pesan)
        View layoutDetailAkun = view.findViewById(R.id.layoutDetailAkun);
        View layoutDaftarTransaksi = view.findViewById(R.id.layoutDaftarTransaksi);
        View layoutLogout = view.findViewById(R.id.layoutLogout);

        layoutDetailAkun.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Buka Detail Akun", Toast.LENGTH_SHORT).show();
        });

        layoutDaftarTransaksi.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Buka Daftar Transaksi", Toast.LENGTH_SHORT).show();
        });

        layoutLogout.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Logout...", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}