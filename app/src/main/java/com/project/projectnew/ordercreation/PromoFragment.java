package com.project.projectnew.ordercreation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.project.projectnew.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

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

            // Ambil data dari API menggunakan Volley
            fetchPromoWithVolley();

            containerPromo.addView(listView);
        } else {
            View emptyView = inflater.inflate(R.layout.fragment_promo_empty, containerPromo, false);
            containerPromo.addView(emptyView);
        }
    }

    private void fetchPromoWithVolley() {
        if (getContext() == null) return;

        RequestQueue queue = ApiClient.getVolleyQueue(getContext());
        String url = ApiClient.getBaseUrl() + "api/promos";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                response -> {
                    List<PromoModel> promoList = new ArrayList<>();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            PromoModel promo = new PromoModel(
                                    obj.getString("title"),
                                    obj.getString("description"),
                                    obj.getString("time")
                            );
                            promoList.add(promo);
                        }

                        recyclerView.setAdapter(new PromoAdapter(getContext(), promoList));

                    } catch (JSONException e) {
                        Toast.makeText(getContext(), "Parsing error", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(getContext(), "Volley Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
        );

        queue.add(jsonArrayRequest);
    }
}
