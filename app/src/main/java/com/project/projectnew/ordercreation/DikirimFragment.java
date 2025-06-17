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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DikirimFragment extends Fragment {

    private RecyclerView rvDikirimOrders;
    private OrderAdapter orderAdapter;
    private List<Order> shippedOrders;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dikirim, container, false);
        rvDikirimOrders = view.findViewById(R.id.rvDikirimOrders);
        rvDikirimOrders.setLayoutManager(new LinearLayoutManager(getContext()));

        rvDikirimOrders.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                if (parent.getChildAdapterPosition(view) == 0) {
                    outRect.top = dpToPx(16, view.getContext());
                }
            }
        });

        loadOrders();

        orderAdapter = new OrderAdapter(getContext(), shippedOrders);
        rvDikirimOrders.setAdapter(orderAdapter);

        return view;
    }

    private void loadOrders() {
        if (getActivity() == null) return;
        SharedPreferences prefs = getActivity().getSharedPreferences("checkout_data", Context.MODE_PRIVATE);
        String json = prefs.getString("order_history", null);

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Order>>() {}.getType();
        List<Order> allOrders = json != null ? gson.fromJson(json, listType) : new ArrayList<>();

        shippedOrders = new ArrayList<>();
        for (Order order : allOrders) {
            if ("Pesanan Dikirim".equalsIgnoreCase(order.getStatus())) {
                shippedOrders.add(order);
            }
        }

        addDummyOrders();
        Collections.reverse(shippedOrders);
        Log.d("DikirimFragment", "Jumlah order dikirim: " + shippedOrders.size());
    }

    private void addDummyOrders() {
        // Dummy Pesanan 1
        List<Product> dummyProducts1 = new ArrayList<>();
        dummyProducts1.add(new Product("p005", "Beras Rojolele Super", "Karung 5kg", "Rp 68.000", 10, 1));
        dummyProducts1.add(new Product("p006", "Minyak Goreng Sania", "Pouch 2L", "Rp 35.000", 15, 2));

        Order dummyOrder1 = new Order(
                "306-2025-06-16-00001",
                dummyProducts1, "Rp 138.000",
                System.currentTimeMillis(), "16 Juni 2025, 14:30:15", "Pesanan Dikirim"
        );
        dummyOrder1.setNoTracking("JNE-TGR-2500184");
        dummyOrder1.setTanggalPengiriman("16 Juni 2025");
        dummyOrder1.setEstimasiTiba("17 - 18 Juni 2025");
        dummyOrder1.setPembeli("Toko Kelontong Berkah");

        List<ShippingStatus> statusList1 = new ArrayList<>();
        statusList1.add(new ShippingStatus("17 Juni 2025", "09:15 WIB", "Pesanan sedang diantar oleh kurir ke alamat tujuan.", true));
        statusList1.add(new ShippingStatus("16 Juni 2025", "18:45 WIB", "Pesanan telah tiba di gudang sortir JNE Tangerang.", false));
        statusList1.add(new ShippingStatus("16 Juni 2025", "15:00 WIB", "Pesanan telah di-pickup oleh kurir dari penjual.", false));
        dummyOrder1.setShippingStatusList(statusList1);
        shippedOrders.add(dummyOrder1);

        // Dummy Pesanan 2
        List<Product> dummyProducts2 = new ArrayList<>();
        dummyProducts2.add(new Product("p008", "Kecap Bango Manis", "Botol 520ml", "Rp 21.000", 50, 3));

        Order dummyOrder2 = new Order(
                "306-2025-06-17-00002",
                dummyProducts2, "Rp 63.000",
                System.currentTimeMillis(), "17 Juni 2025, 08:55:01", "Pesanan Dikirim"
        );
        dummyOrder2.setNoTracking("SPX-TGR-9981723");
        dummyOrder2.setTanggalPengiriman("17 Juni 2025");
        dummyOrder2.setEstimasiTiba("17 - 19 Juni 2025");
        dummyOrder2.setPembeli("Warung Makan Sederhana");

        List<ShippingStatus> statusList2 = new ArrayList<>();
        statusList2.add(new ShippingStatus("17 Juni 2025", "10:30 WIB", "Pesanan telah diserahkan kepada kurir.", true));
        statusList2.add(new ShippingStatus("17 Juni 2025", "09:10 WIB", "Penjual telah mengatur pengiriman.", false));
        dummyOrder2.setShippingStatusList(statusList2);
        shippedOrders.add(dummyOrder2);
    }

    private int dpToPx(int dp, Context context) {
        if (context == null) return dp;
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
