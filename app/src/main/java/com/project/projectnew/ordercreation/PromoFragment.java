package com.project.projectnew.ordercreation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.project.projectnew.R;

public class PromoFragment extends Fragment {

    private boolean hasItems = true; // toggle state

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promo, container, false);

        FrameLayout contentContainer = view.findViewById(R.id.containerPromoContent);
        Button switchButton = view.findViewById(R.id.btnSwitchView);

        // First Load
        updatePromoContent(inflater, contentContainer);

        // Button to switch
        switchButton.setOnClickListener(v -> {
            hasItems = !hasItems; // toggle state
            updatePromoContent(inflater, contentContainer);
        });

        return view;
    }

    private void updatePromoContent(LayoutInflater inflater, FrameLayout container) {
        container.removeAllViews();
        View contentView;
        if (hasItems) {
            contentView = inflater.inflate(R.layout.fragment_promo_items, container, false);

            TextView textJudul1 = contentView.findViewById(R.id.textJudulPromo1);
            if (textJudul1 != null) {
                textJudul1.setOnClickListener(v -> {
                    startActivity(new Intent(getActivity(), DetailPromoActivity.class));
                });
            }

            TextView textJudul2 = contentView.findViewById(R.id.textJudulPromo2);
            if (textJudul2 != null) {
                textJudul2.setOnClickListener(v -> {
                    startActivity(new Intent(getActivity(), DetailPromoActivity.class));
                });
            }

            TextView textJudul3 = contentView.findViewById(R.id.textJudulPromo3);
            if (textJudul3 != null) {
                textJudul3.setOnClickListener(v -> {
                    startActivity(new Intent(getActivity(), DetailPromoActivity.class));
                });
            }

            TextView textJudul4 = contentView.findViewById(R.id.textJudulPromo4);
            if (textJudul4 != null) {
                textJudul4.setOnClickListener(v -> {
                    startActivity(new Intent(getActivity(), DetailPromoActivity.class));
                });
            }

        } else {
            contentView = inflater.inflate(R.layout.fragment_promo_empty, container, false);
        }

        container.addView(contentView);
    }
}
