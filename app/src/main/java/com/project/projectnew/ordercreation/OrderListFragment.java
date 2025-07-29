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

    private static final String ARG_ORDER_STATUS = "order_status";

    private OrderAdapter orderAdapter;
    private List<Order> orderList = new ArrayList<>();
    private AppDatabase db;
    private ExecutorService executorService;
    private Handler handler;
    private String orderStatus;

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
        db = AppDatabase.getDatabase(getContext());
        executorService = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());
        if (getArguments() != null) {
            orderStatus = getArguments().getString(ARG_ORDER_STATUS);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);

        RecyclerView rvOrders = view.findViewById(R.id.rvOrders);
        rvOrders.setLayoutManager(new LinearLayoutManager(getContext()));
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
        if (orderStatus != null && executorService != null && db != null) {
            executorService.execute(() -> {
                // Baris ini sekarang akan berfungsi setelah OrderDao diperbaiki
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