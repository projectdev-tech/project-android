package com.project.projectnew.ordercreation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.projectnew.R;

import java.util.ArrayList;
import java.util.List;

public class PromoFragment extends Fragment {

    private boolean hasItems = true;
    private RecyclerView recyclerView;
    private ViewGroup containerPromo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promo, container, false);
        containerPromo = view.findViewById(R.id.containerPromoContent);
        Button switchButton = view.findViewById(R.id.btnSwitchView);

        updateView(inflater);

        switchButton.setOnClickListener(v -> {
            hasItems = !hasItems;
            updateView(inflater);
        });

        return view;
    }

    private void updateView(LayoutInflater inflater) {
        containerPromo.removeAllViews();

        if (hasItems) {
            View listView = inflater.inflate(R.layout.fragment_promo_items, containerPromo, false);
            recyclerView = listView.findViewById(R.id.recyclerViewPromo);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(new PromoAdapter(getContext(), generateDummyPromos()));
            containerPromo.addView(listView);
        } else {
            View emptyView = inflater.inflate(R.layout.fragment_promo_empty, containerPromo, false);
            containerPromo.addView(emptyView);
        }
    }

    private List<PromoModel> generateDummyPromos() {
        List<PromoModel> list = new ArrayList<>();
        list.add(new PromoModel("Promo A", "Diskon 50%", "10:00"));
        list.add(new PromoModel("Promo B", "Gratis Ongkir", "12:00"));
        list.add(new PromoModel("Promo C", "Buy 1 Get 1", "14:00"));
        return list;
    }
}
