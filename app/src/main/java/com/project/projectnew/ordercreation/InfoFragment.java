package com.project.projectnew.ordercreation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.projectnew.R;

import java.util.ArrayList;
import java.util.List;

public class InfoFragment extends Fragment {

    private boolean hasItems = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        FrameLayout contentContainer = view.findViewById(R.id.containerInfoContent);
        Button switchButton = view.findViewById(R.id.btnSwitchInfoView);

        updateInfoContent(inflater, contentContainer);

        switchButton.setOnClickListener(v -> {
            hasItems = !hasItems;
            updateInfoContent(inflater, contentContainer);
        });

        return view;
    }

    private void updateInfoContent(LayoutInflater inflater, FrameLayout container) {
        container.removeAllViews();

        if (hasItems) {
            View contentView = inflater.inflate(R.layout.fragment_info_items, container, false);
            RecyclerView recyclerView = contentView.findViewById(R.id.recyclerViewInfo);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            List<InfoItem> dummyList = new ArrayList<>();
            dummyList.add(new InfoItem("Judul 1", "Deskripsi 1", "12:00:00"));
            dummyList.add(new InfoItem("Judul 2", "Deskripsi 2", "13:30:00"));
            dummyList.add(new InfoItem("Judul 3", "Deskripsi 3", "15:15:00"));

            InfoAdapter adapter = new InfoAdapter(requireContext(), dummyList);
            recyclerView.setAdapter(adapter);

            container.addView(contentView);
        } else {
            View emptyView = inflater.inflate(R.layout.fragment_info_empty, container, false);
            container.addView(emptyView);
        }
    }
}
