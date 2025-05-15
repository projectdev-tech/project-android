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

public class InfoFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_info, container, false);

        // Pastikan ID sesuai dengan yang ada di fragment_info.xml
        TextView textJudul1 = view.findViewById(R.id.textJudulInfo1);
        if (textJudul1 != null) {
            textJudul1.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), DetailInfoActivity.class);
                startActivity(intent);
            });
        }

        TextView textJudul2 = view.findViewById(R.id.textJudulInfo2);
        if (textJudul2 != null) {
             textJudul2.setOnClickListener(v -> {
                 Intent intent = new Intent(getActivity(), DetailInfoActivity.class);
                 startActivity(intent);
             });
         }

        TextView textJudul3 = view.findViewById(R.id.textJudulInfo2);
        if (textJudul3 != null) {
            textJudul3.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), DetailInfoActivity.class);
                startActivity(intent);
            });
        }

        TextView textJudul4 = view.findViewById(R.id.textJudulInfo2);
        if (textJudul4 != null) {
            textJudul4.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), DetailInfoActivity.class);
                startActivity(intent);
            });
        }

        return view;
    }
}
