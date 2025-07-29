package com.project.projectnew.ordercreation;

import android.content.Intent;
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

        View layoutDetailAkun = view.findViewById(R.id.layoutDetailAkun);
        View layoutDaftarTransaksi = view.findViewById(R.id.layoutDaftarTransaksi);
        View layoutLogout = view.findViewById(R.id.layoutLogout);

        layoutDetailAkun.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), DetailAkunActivity.class);
            startActivity(intent);
        });

        // --- PERUBAHAN DI SINI ---
        layoutDaftarTransaksi.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), DaftarTransaksiActivity.class);
            startActivity(intent);
        });

        layoutLogout.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Logout...", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}