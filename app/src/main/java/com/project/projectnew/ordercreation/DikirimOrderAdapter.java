package com.project.projectnew.ordercreation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.project.projectnew.R;
import java.util.List;

public class DikirimOrderAdapter extends RecyclerView.Adapter<DikirimOrderAdapter.DikirimViewHolder> {

    private final Context context;
    private final List<Order> orderList;

    public DikirimOrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public DikirimViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dikirim_order, parent, false);
        return new DikirimViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DikirimViewHolder holder, int position) {
        Order order = orderList.get(position);

        // Bind data utama
        holder.tvNoOrder.setText(order.getNoOrder());
        holder.tvTanggalPembelian.setText(order.getTanggalPembelian());
        holder.tvStatus.setText(order.getStatus());
        holder.tvTotalHarga.setText(order.getTotalHarga());

        // Setup product list inner RecyclerView
        holder.rvProducts.setLayoutManager(new LinearLayoutManager(context));
        holder.rvProducts.setAdapter(new ProductInOrderAdapter(order.getProductList()));

        // NOTE: Data tracking di-hardcode di sini karena tidak ada di model 'Order.java'
        // Untuk implementasi nyata, data ini harus berasal dari objek 'order'.
        holder.tvNoTracking.setText("JNX" + order.getNoOrder().substring(4, 15));
        holder.tvTanggalPengiriman.setText("17 Juni 2025");
        holder.tvEstimasiTiba.setText("18 - 19 Juni 2025");
        holder.tvPembeli.setText("Toko Sejahtera");

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class DikirimViewHolder extends RecyclerView.ViewHolder {
        TextView tvNoOrder, tvTanggalPembelian, tvStatus, tvTotalHarga;
        TextView tvNoTracking, tvTanggalPengiriman, tvEstimasiTiba, tvPembeli;
        RecyclerView rvProducts;

        public DikirimViewHolder(View itemView) {
            super(itemView);
            tvNoOrder = itemView.findViewById(R.id.tvNoOrder);
            tvTanggalPembelian = itemView.findViewById(R.id.tvTanggalPembelian);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvTotalHarga = itemView.findViewById(R.id.tvTotalHarga);
            rvProducts = itemView.findViewById(R.id.rvProducts);

            // View untuk detail pengiriman
            tvNoTracking = itemView.findViewById(R.id.tvNoTracking);
            tvTanggalPengiriman = itemView.findViewById(R.id.tvTanggalPengiriman);
            tvEstimasiTiba = itemView.findViewById(R.id.tvEstimasiTiba);
            tvPembeli = itemView.findViewById(R.id.tvPembeli);
        }
    }
}