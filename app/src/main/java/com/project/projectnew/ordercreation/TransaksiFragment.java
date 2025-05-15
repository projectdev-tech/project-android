package com.project.projectnew.ordercreation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.project.projectnew.R;

public class TransaksiFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaksi, container, false);

//        Button btnBuatNotifikasi = view.findViewById(R.id.btn_buat_notifikasi);
//        btnBuatNotifikasi.setOnClickListener(v -> {
//            Toast.makeText(getContext(), "Fitur buat notifikasi belum tersedia", Toast.LENGTH_SHORT).show();
//        });
//
        TextView textJudul1 = view.findViewById(R.id.textJudulTransaksi1);
        if (textJudul1 != null) {
            textJudul1.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), DetailTransaksiActivity.class);
                startActivity(intent);
            });
        }

        TextView textJudul2 = view.findViewById(R.id.textJudulTransaksi2);
        if (textJudul2 != null) {
            textJudul2.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), DetailTransaksiActivity.class);
                startActivity(intent);
            });
        }

        TextView textJudul3 = view.findViewById(R.id.textJudulTransaksi3);
        if (textJudul3 != null) {
            textJudul3.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), DetailTransaksiActivity.class);
                startActivity(intent);
            });
        }

        TextView textJudul4 = view.findViewById(R.id.textJudulTransaksi4);
        if (textJudul4 != null) {
            textJudul4.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), DetailTransaksiActivity.class);
                startActivity(intent);
            });
        }

        TextView textJudul5 = view.findViewById(R.id.textJudulTransaksi5);
        if (textJudul5 != null) {
            textJudul5.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), DetailTransaksiActivity.class);
                startActivity(intent);
            });
        }

        TextView textJudul6 = view.findViewById(R.id.textJudulTransaksi6);
        if (textJudul6 != null) {
            textJudul6.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), DetailTransaksiActivity.class);
                startActivity(intent);
            });
        }


        return view;
    }
}
