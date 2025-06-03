package com.project.projectnew.ordercreation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.projectnew.R;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private final Context context;
    private final List<Order> orderList;
    private final List<Boolean> expandStates;

    public OrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
        this.expandStates = new ArrayList<>();
        for (int i = 0; i < orderList.size(); i++) {
            expandStates.add(false); // default collapsed
        }
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvNoOrder, tvTanggalPembelian, tvStatus, tvTotalHarga;
        RecyclerView rvProducts;
        LinearLayout btnLihatLainnya;
        TextView tvLihatLainnya;
        ImageView icLihatLainnya;
        TextView btnRincianPesanan;

        public OrderViewHolder(View itemView) {
            super(itemView);
            tvNoOrder = itemView.findViewById(R.id.tvNoOrder);
            tvTanggalPembelian = itemView.findViewById(R.id.tvTanggalPembelian);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvTotalHarga = itemView.findViewById(R.id.tvTotalHarga);
            rvProducts = itemView.findViewById(R.id.rvProducts);
            btnLihatLainnya = itemView.findViewById(R.id.btnLihatLainnya);
            tvLihatLainnya = itemView.findViewById(R.id.tvLihatLainnya);
            icLihatLainnya = itemView.findViewById(R.id.icLihatLainnya);
            btnRincianPesanan = itemView.findViewById(R.id.btnRincianPesanan);
        }
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        boolean isExpanded = expandStates.get(position);

        holder.tvNoOrder.setText(order.getNoOrder());
        holder.tvTanggalPembelian.setText(order.getTanggalPembelian());
        holder.tvStatus.setText(order.getStatus());
        holder.tvTotalHarga.setText(order.getTotalHarga());

        List<Product> productList = order.getProductList();
        List<Product> displayList = isExpanded
                ? productList
                : productList.subList(0, Math.min(1, productList.size()));

        holder.rvProducts.setLayoutManager(new LinearLayoutManager(context));
        holder.rvProducts.setAdapter(new ProductInOrderAdapter(displayList));

        if (productList.size() > 1) {
            holder.btnLihatLainnya.setVisibility(View.VISIBLE);
            holder.tvLihatLainnya.setText(isExpanded ? "Lihat Lebih Sedikit" : "Lihat Lainnya");
            holder.icLihatLainnya.setImageResource(isExpanded ? R.drawable.arrow_up : R.drawable.arrow_down);

            holder.btnLihatLainnya.setOnClickListener(v -> {
                int currentPos = holder.getAdapterPosition();
                if (currentPos != RecyclerView.NO_POSITION) {
                    boolean currentExpanded = expandStates.get(currentPos);
                    expandStates.set(currentPos, !currentExpanded);
                    notifyItemChanged(currentPos);
                }
            });
        } else {
            holder.btnLihatLainnya.setVisibility(View.GONE);
        }

        // Handle klik rincian pesanan
        holder.btnRincianPesanan.setOnClickListener(v -> {
            Intent intent = new Intent(context, RincianPesananActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // wajib jika dari fragment

            // Kirim data order lewat intent
            intent.putExtra("total_harga", order.getTotalHarga());

            // Pastikan waktu_pembayaran dalam long, bukan String
            intent.putExtra("waktu_pembayaran", order.getWaktuPembayaran());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
