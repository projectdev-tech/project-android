package com.project.projectnew.ordercreation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.util.Log;

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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TransaksiFragment extends Fragment {

    private boolean hasTransactions = true;
    private FrameLayout containerContent;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transaksi, container, false);
        containerContent = view.findViewById(R.id.containerTransaksiContent);
        Button switchButton = view.findViewById(R.id.btnSwitchTransaksi);

        switchButton.setOnClickListener(v -> {
            hasTransactions = !hasTransactions;
            updateContent(inflater);
        });

        updateContent(inflater);
        return view;
    }

    private void updateContent(LayoutInflater inflater) {
        containerContent.removeAllViews();

        if (hasTransactions) {
            View content = inflater.inflate(R.layout.fragment_transaksi_items, containerContent, false);
            recyclerView = content.findViewById(R.id.recyclerViewTransaksi);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            // Ambil data transaksi menggunakan Volley
            fetchTransaksiWithVolley();

            containerContent.addView(content);
        } else {
            View emptyView = inflater.inflate(R.layout.fragment_transaksi_empty, containerContent, false);
            containerContent.addView(emptyView);
        }
    }

    private void fetchTransaksiWithVolley() {
        if (getContext() == null) return;

        RequestQueue queue = ApiClient.getVolleyQueue(getContext());
        String url = ApiClient.getBaseUrl() + "api/transaksis";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                response -> {
                    List<TransaksiModel> transaksiList = new ArrayList<>();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            TransaksiModel transaksi = new TransaksiModel(
                                    obj.getString("title"),
                                    obj.getString("description"),
                                    obj.getString("time")
                            );
                            transaksiList.add(transaksi);
                        }

                        recyclerView.setAdapter(new TransaksiAdapter(getContext(), transaksiList));

                    } catch (JSONException e) {
                        Toast.makeText(getContext(), "Parsing error", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(getContext(), "Volley Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("TransaksiFragment", "Volley error", error);
                }
        );

        queue.add(jsonArrayRequest);
    }
}
