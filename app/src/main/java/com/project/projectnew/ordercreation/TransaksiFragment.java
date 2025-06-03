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

public class TransaksiFragment extends Fragment {

    private boolean hasTransactions = true; // toggle state

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transaksi, container, false);

        FrameLayout contentContainer = view.findViewById(R.id.containerTransaksiContent);
        Button switchButton = view.findViewById(R.id.btnSwitchTransaksi);

        updateTransaksiContent(inflater, contentContainer);

        switchButton.setOnClickListener(v -> {
            hasTransactions = !hasTransactions;
            updateTransaksiContent(inflater, contentContainer);
        });

        return view;
    }
// recycle view ~~
//    Poli -> api/ci3
    private void updateTransaksiContent(LayoutInflater inflater, FrameLayout container) {
        container.removeAllViews();
        View contentView;
        if (hasTransactions) {
            contentView = inflater.inflate(R.layout.fragment_transaksi_items, container, false);

            int[] textIds = {
                    R.id.textJudulTransaksi1, R.id.textJudulTransaksi2,
                    R.id.textJudulTransaksi3, R.id.textJudulTransaksi4,
                    R.id.textJudulTransaksi5, R.id.textJudulTransaksi6
            };

            for (int id : textIds) {
                TextView textView = contentView.findViewById(id);
                if (textView != null) {
                    textView.setOnClickListener(v -> {
                        Intent intent = new Intent(getActivity(), DetailTransaksiActivity.class);
                        startActivity(intent);
                    });
                }
            }

        } else {
            contentView = inflater.inflate(R.layout.fragment_transaksi_empty, container, false);
        }

        container.addView(contentView);
    }
}
