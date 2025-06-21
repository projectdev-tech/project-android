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

public class InfoFragment extends Fragment {

    private boolean hasItems = true;
    private RecyclerView recyclerView;

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
            recyclerView = contentView.findViewById(R.id.recyclerViewInfo);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            // Ambil data info dengan Volley
            fetchInfoWithVolley();

            container.addView(contentView);
        } else {
            View emptyView = inflater.inflate(R.layout.fragment_info_empty, container, false);
            container.addView(emptyView);
        }
    }

    private void fetchInfoWithVolley() {
        if (getContext() == null) return;

        RequestQueue queue = ApiClient.getVolleyQueue(getContext());
        String url = ApiClient.getBaseUrl() + "api/infos";

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, url, null,
                response -> {
                    List<InfoItem> infoList = new ArrayList<>();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            InfoItem info = new InfoItem(
                                    obj.getString("title"),
                                    obj.getString("description"),
                                    obj.getString("time")
                            );
                            infoList.add(info);
                        }
                        recyclerView.setAdapter(new InfoAdapter(getContext(), infoList));
                    } catch (JSONException e) {
                        Toast.makeText(getContext(), "Parsing error", Toast.LENGTH_SHORT).show();
                        Log.e("InfoFragment", "Parsing error", e);
                    }
                },
                error -> {
                    Toast.makeText(getContext(), "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("InfoFragment", "Volley error", error);
                }
        );

        queue.add(request);
    }
}
