package com.project.projectnew.ordercreation;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.projectnew.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BelumBayarFragment extends Fragment {

    private RecyclerView rvBelumBayarOrders;
    private OrderAdapter orderAdapter;
    private List<Order> pendingOrders;

    public BelumBayarFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_belum_bayar, container, false);
        rvBelumBayarOrders = view.findViewById(R.id.rvOrders);

        rvBelumBayarOrders.setLayoutManager(new LinearLayoutManager(getContext()));

        rvBelumBayarOrders.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                int itemCount = parent.getAdapter() != null ? parent.getAdapter().getItemCount() : 0;

                if (position == 0) {
                    outRect.top = dpToPx(16, view.getContext());
                }

//                if (position == itemCount - 1) {
//                    outRect.bottom = dpToPx(8, view.getContext());
//                }
            }
        });

        // Ambil data order dari SharedPreferences
        SharedPreferences prefs = getActivity().getSharedPreferences("checkout_data", Context.MODE_PRIVATE);
        String json = prefs.getString("order_history", null);

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Order>>() {}.getType();
        List<Order> allOrders = json != null ? gson.fromJson(json, listType) : new ArrayList<>();

        pendingOrders = new ArrayList<>();
        for (Order order : allOrders) {
            if ("Menunggu Pembayaran".equalsIgnoreCase(order.getStatus())) {
                pendingOrders.add(order);
            }
        }

        Collections.reverse(pendingOrders);

        Log.d("BelumBayarFragment", "Jumlah order belum bayar: " + pendingOrders.size());

        orderAdapter = new OrderAdapter(getContext(), pendingOrders);
        rvBelumBayarOrders.setAdapter(orderAdapter);

        return view;
    }

    private int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
