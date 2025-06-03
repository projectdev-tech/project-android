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

public class InfoFragment extends Fragment {

    private boolean hasItems = true; // toggle state

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        FrameLayout contentContainer = view.findViewById(R.id.containerInfoContent);
        Button switchButton = view.findViewById(R.id.btnSwitchInfoView);

        // First Load
        updateInfoContent(inflater, contentContainer);

        // Button toggle listener
        switchButton.setOnClickListener(v -> {
            hasItems = !hasItems;
            updateInfoContent(inflater, contentContainer);
        });

        return view;
    }

    private void updateInfoContent(LayoutInflater inflater, FrameLayout container) {
        container.removeAllViews();
        View contentView;
        if (hasItems) {
            contentView = inflater.inflate(R.layout.fragment_info_items, container, false);

            TextView textJudul1 = contentView.findViewById(R.id.textJudulInfo1);
            TextView textJudul2 = contentView.findViewById(R.id.textJudulInfo2);
            TextView textJudul3 = contentView.findViewById(R.id.textJudulInfo3);
            TextView textJudul4 = contentView.findViewById(R.id.textJudulInfo4);

            View.OnClickListener listener = v -> {
                Intent intent = new Intent(getActivity(), DetailInfoActivity.class);
                startActivity(intent);
            };

            if (textJudul1 != null) textJudul1.setOnClickListener(listener);
            if (textJudul2 != null) textJudul2.setOnClickListener(listener);
            if (textJudul3 != null) textJudul3.setOnClickListener(listener);
            if (textJudul4 != null) textJudul4.setOnClickListener(listener);

        } else {
            contentView = inflater.inflate(R.layout.fragment_info_empty, container, false);
        }

        container.addView(contentView);
    }
}
