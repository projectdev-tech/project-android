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

public class PromoFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promo, container, false);

        TextView textJudul1 = view.findViewById(R.id.textJudulPromo1);
        TextView textJudul2 = view.findViewById(R.id.textJudulPromo2);

        if (textJudul1 != null) {
            textJudul1.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), DetailPromoActivity.class);
                startActivity(intent);
            });
        }

        if (textJudul2 != null) {
            textJudul2.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), DetailPromoActivity.class);
                startActivity(intent);
            });
        }

        return view;
    }
}
