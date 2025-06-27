package com.project.projectnew.ordercreation;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderListFragment extends Fragment {

    // Kunci untuk menyimpan dan mengambil status pesanan dari arguments
    private static final String ARG_ORDER_STATUS = "order_status";

    private OrderAdapter orderAdapter;
    private List<Order> orderList = new ArrayList<>();
    private AppDatabase db;
    private ExecutorService executorService;
    private Handler handler;
    private String orderStatus;

    /**
     * Factory method untuk membuat instance baru dari fragment ini dengan status pesanan yang spesifik.
     * Ini adalah cara yang direkomendasikan untuk meneruskan argumen ke Fragment.
     *
     * @param status Status pesanan yang akan ditampilkan (e.g., "Menunggu Pembayaran").
     * @return Sebuah instance baru dari OrderListFragment.
     */
    public static OrderListFragment newInstance(String status) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ORDER_STATUS, status);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Ambil status pesanan dari arguments
        if (getArguments() != null) {
            orderStatus = getArguments().getString(ARG_ORDER_STATUS);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Gunakan layout generik yang baru kita buat
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);

        Context context = getContext();
        if (context != null) {
            db = AppDatabase.getDatabase(context);
            executorService = Executors.newSingleThreadExecutor();
            handler = new Handler(Looper.getMainLooper());
        }

        RecyclerView rvOrders = view.findViewById(R.id.rvOrders);
        rvOrders.setLayoutManager(new LinearLayoutManager(context));

        // Menambahkan jarak di atas item pertama
        rvOrders.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                if (parent.getChildAdapterPosition(view) == 0) {
                    outRect.top = dpToPx(16, view.getContext());
                }
            }
        });

        orderAdapter = new OrderAdapter(orderList);
        rvOrders.setAdapter(orderAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadOrdersFromDb();
    }

    private void loadOrdersFromDb() {
        // Pastikan status tidak null dan komponen lain sudah siap
        if (orderStatus != null && executorService != null && db != null) {
            executorService.execute(() -> {
                // Ambil data dari DB berdasarkan status yang diterima dari argumen
                List<Order> ordersFromDb = db.orderDao().getOrdersByStatus(orderStatus);
                handler.post(() -> {
                    if (orderList != null && orderAdapter != null) {
                        orderList.clear();
                        orderList.addAll(ordersFromDb);
                        orderAdapter.notifyDataSetChanged();
                    }
                });
            });
        }
    }

    private int dpToPx(int dp, Context context) {
        if (context == null) return dp;
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}