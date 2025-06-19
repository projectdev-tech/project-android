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

public class DikirimFragment extends Fragment {

    private OrderAdapter orderAdapter;
    private List<Order> orderList = new ArrayList<>();
    private AppDatabase db;
    private ExecutorService executorService;
    private Handler handler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dikirim, container, false);

        // PERBAIKAN: Pindahkan inisialisasi ke onCreateView
        Context context = getContext();
        if (context != null) {
            db = AppDatabase.getDatabase(context);
            executorService = Executors.newSingleThreadExecutor();
            handler = new Handler(Looper.getMainLooper());
        }

        RecyclerView rvDikirimOrders = view.findViewById(R.id.rvDikirimOrders);
        rvDikirimOrders.setLayoutManager(new LinearLayoutManager(context));

        rvDikirimOrders.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                if (parent.getChildAdapterPosition(view) == 0) {
                    outRect.top = dpToPx(16, view.getContext());
                }
            }
        });

        orderAdapter = new OrderAdapter(orderList);
        rvDikirimOrders.setAdapter(orderAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadOrdersFromDb();
    }

    private void loadOrdersFromDb() {
        if (executorService != null && db != null) {
            executorService.execute(() -> {
                List<Order> ordersFromDb = db.orderDao().getOrdersByStatus("Pesanan Dikirim");
                handler.post(() -> {
                    orderList.clear();
                    orderList.addAll(ordersFromDb);
                    orderAdapter.notifyDataSetChanged();
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