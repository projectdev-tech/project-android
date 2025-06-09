package com.project.projectnew.ordercreation;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.projectnew.R;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SelesaiFragment extends Fragment {

    private RecyclerView rvSelesaiOrders;
    private OrderAdapter orderAdapter;
    private List<Order> completedOrders;

    public SelesaiFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_selesai, container, false);
        rvSelesaiOrders = view.findViewById(R.id.rvSelesaiOrders);
        rvSelesaiOrders.setLayoutManager(new LinearLayoutManager(getContext()));

        // Menambahkan jarak di atas item pertama
        rvSelesaiOrders.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                if (parent.getChildAdapterPosition(view) == 0) {
                    outRect.top = dpToPx(16, view.getContext());
                }
            }
        });

        // Memuat pesanan
        loadOrders();

        // Mengatur adapter
        orderAdapter = new OrderAdapter(getContext(), completedOrders);
        rvSelesaiOrders.setAdapter(orderAdapter);

        return view;
    }

    private void loadOrders() {
        SharedPreferences prefs = getActivity().getSharedPreferences("checkout_data", Context.MODE_PRIVATE);
        String json = prefs.getString("order_history", null);
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Order>>() {}.getType();
        List<Order> allOrders = json != null ? gson.fromJson(json, listType) : new ArrayList<>();

        completedOrders = new ArrayList<>();
        for (Order order : allOrders) {
            // Filter untuk pesanan yang sudah selesai
            if ("Pesanan Diterima".equalsIgnoreCase(order.getStatus())) {
                completedOrders.add(order);
            }
        }

        // Tambahkan data dummy
        addDummyOrders();

        Collections.reverse(completedOrders);

        Log.d("SelesaiFragment", "Jumlah order selesai: " + completedOrders.size());
    }

    private void addDummyOrders() {
        String datePart = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String tanggalPembelian = new SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", new Locale("in", "ID"))
                .format(new Date());

        // Dummy Order Selesai 1
        List<Product> dummyProducts1 = new ArrayList<>();
        Product product1 = new Product("p004", "Produk Dummy Selesai 1", "Satuan", "Rp 80.000", 20);
        product1.setQuantity(1);
        dummyProducts1.add(product1);

        Order dummyOrder1 = new Order(
                String.format("306-%s-DUMMY-S1", datePart),
                dummyProducts1,
                "Rp 80.000",
                System.currentTimeMillis() - (2 * 24 * 60 * 60 * 1000), // 2 hari yang lalu
                tanggalPembelian,
                "Pesanan Diterima"
        );
        completedOrders.add(dummyOrder1);

        // Dummy Order Selesai 2
        List<Product> dummyProducts2 = new ArrayList<>();
        Product product2A = new Product("p005", "Produk Dummy Selesai 2A", "Satuan", "Rp 25.000", 15);
        product2A.setQuantity(4);
        dummyProducts2.add(product2A);
        Product product2B = new Product("p006", "Produk Dummy Selesai 2B", "Satuan", "Rp 15.000", 30);
        product2B.setQuantity(2);
        dummyProducts2.add(product2B);


        Order dummyOrder2 = new Order(
                String.format("306-%s-DUMMY-S2", datePart),
                dummyProducts2,
                "Rp 130.000",
                System.currentTimeMillis() - (3 * 24 * 60 * 60 * 1000), // 3 hari yang lalu
                new SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", new Locale("in", "ID")).format(new Date(System.currentTimeMillis() - (3 * 24 * 60 * 60 * 1000))),
                "Pesanan Diterima"
        );
        completedOrders.add(dummyOrder2);
    }

    private int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
