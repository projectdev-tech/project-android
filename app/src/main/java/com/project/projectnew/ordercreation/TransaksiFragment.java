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

public class TransaksiFragment extends Fragment {

    private boolean hasTransactions = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transaksi, container, false);
        FrameLayout containerContent = view.findViewById(R.id.containerTransaksiContent);
        Button switchButton = view.findViewById(R.id.btnSwitchTransaksi);

        switchButton.setOnClickListener(v -> {
            hasTransactions = !hasTransactions;
            updateContent(inflater, containerContent);
        });

        updateContent(inflater, containerContent);
        return view;
    }

    private void updateContent(LayoutInflater inflater, FrameLayout container) {
        container.removeAllViews();

        if (hasTransactions) {
            View content = inflater.inflate(R.layout.fragment_transaksi_items, container, false);
            RecyclerView recyclerView = content.findViewById(R.id.recyclerViewTransaksi);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            List<TransaksiModel> dummyList = new ArrayList<>();
            dummyList.add(new TransaksiModel("Transaksi A", "Pembayaran A", "12:00:00"));
            dummyList.add(new TransaksiModel("Transaksi B", "Pembayaran B", "13:00:00"));
            dummyList.add(new TransaksiModel("Transaksi C", "Pembayaran C", "14:00:00"));

            TransaksiAdapter adapter = new TransaksiAdapter(requireContext(), dummyList);
            recyclerView.setAdapter(adapter);

            container.addView(content);
        } else {
            View emptyView = inflater.inflate(R.layout.fragment_transaksi_empty, container, false);
            container.addView(emptyView);
        }
    }
}
