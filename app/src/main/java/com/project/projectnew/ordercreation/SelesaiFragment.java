package com.project.projectnew.ordercreation;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.project.projectnew.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelesaiFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selesai, container, false);
        RecyclerView rvSelesaiOrders = view.findViewById(R.id.rvSelesaiOrders);
        rvSelesaiOrders.setLayoutManager(new LinearLayoutManager(getContext()));

        // Menambahkan jarak di atas item pertama
        rvSelesaiOrders.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                if (parent.getChildAdapterPosition(view) == 0) {
                    outRect.top = dpToPx(16, view.getContext());
                }
            }
        });

        // Ambil data dari DummyDataGenerator dan filter berdasarkan status
        List<Order> allOrders = DummyDataGenerator.getOrders();
        List<Order> completedOrders = new ArrayList<>();
        for (Order order : allOrders) {
            if ("Pesanan Diterima".equalsIgnoreCase(order.getStatus())) {
                completedOrders.add(order);
            }
        }

        Collections.reverse(completedOrders); // Menampilkan yang terbaru di atas

        // Set adapter
        OrderAdapter orderAdapter = new OrderAdapter(getContext(), completedOrders);
        rvSelesaiOrders.setAdapter(orderAdapter);

        return view;
    }

    private int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}