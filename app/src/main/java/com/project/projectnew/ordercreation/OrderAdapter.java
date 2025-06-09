package com.project.projectnew.ordercreation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
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
            expandStates.add(false);
        }
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvNoOrder, tvTanggalPembelian, tvStatus, tvTotalHarga;
        RecyclerView rvProducts;
        LinearLayout btnLihatLainnya, layoutRincianPesanan;
        TextView tvLihatLainnya;
        ImageView icLihatLainnya;
        Button btnRincianPesanan;
        View dividerRincian;

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
            layoutRincianPesanan = itemView.findViewById(R.id.layoutRincianPesanan);
            dividerRincian = itemView.findViewById(R.id.dividerRincian);
        }
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        boolean isExpanded = expandStates.get(position);

        holder.tvNoOrder.setText(order.getNoOrder());
        holder.tvTanggalPembelian.setText(order.getTanggalPembelian());
        holder.tvStatus.setText(order.getStatus());
        holder.tvTotalHarga.setText(order.getTotalHarga());

        String status = order.getStatus();
        if ("Menunggu Konfirmasi".equalsIgnoreCase(status)) {
            holder.tvStatus.setTextColor(Color.parseColor("#FF9305"));
            holder.layoutRincianPesanan.setVisibility(View.GONE);
            holder.dividerRincian.setVisibility(View.GONE);
        } else if ("Menunggu Pembayaran".equalsIgnoreCase(status)) {
            holder.tvStatus.setTextColor(Color.parseColor("#FF3B31"));
            holder.layoutRincianPesanan.setVisibility(View.VISIBLE);
            holder.dividerRincian.setVisibility(View.VISIBLE);
        } else if ("Pesanan Diterima".equalsIgnoreCase(status)) {
            holder.tvStatus.setTextColor(Color.parseColor("#35C759"));
            holder.layoutRincianPesanan.setVisibility(View.VISIBLE);
            holder.dividerRincian.setVisibility(View.VISIBLE);
        } else {
            holder.tvStatus.setTextColor(Color.parseColor("#404040"));
            holder.layoutRincianPesanan.setVisibility(View.VISIBLE);
            holder.dividerRincian.setVisibility(View.VISIBLE);
        }

        List<Product> productList = order.getProductList();
        List<Product> displayList = isExpanded ? productList : productList.subList(0, Math.min(1, productList.size()));
        holder.rvProducts.setLayoutManager(new LinearLayoutManager(context));
        holder.rvProducts.setAdapter(new ProductInOrderAdapter(displayList));

        if (productList.size() > 1) {
            holder.btnLihatLainnya.setVisibility(View.VISIBLE);
            holder.tvLihatLainnya.setText(isExpanded ? "Lihat Lebih Sedikit" : "Lihat Lainnya");
            holder.icLihatLainnya.setImageResource(isExpanded ? R.drawable.arrow_up : R.drawable.arrow_down);
            holder.btnLihatLainnya.setOnClickListener(v -> {
                int currentPos = holder.getAdapterPosition();
                if (currentPos != RecyclerView.NO_POSITION) {
                    expandStates.set(currentPos, !expandStates.get(currentPos));
                    notifyItemChanged(currentPos);
                }
            });
        } else {
            holder.btnLihatLainnya.setVisibility(View.GONE);
        }

        // === PERUBAHAN UTAMA DI SINI ===
        holder.btnRincianPesanan.setOnClickListener(v -> {
            Intent intent;
            if ("Pesanan Diterima".equalsIgnoreCase(order.getStatus())) {
                // Jika pesanan selesai, buka PesananDiterimaActivity
                intent = new Intent(context, PesananDiterimaActivity.class);
                intent.putExtra("ORDER_DETAIL", order); // Mengirim seluruh objek Order
            } else {
                // Jika belum bayar, buka RincianPesananActivity (Pembayaran)
                intent = new Intent(context, RincianPesananActivity.class);
                intent.putExtra("total_harga", order.getTotalHarga());
                intent.putExtra("waktu_pembayaran", order.getWaktuPembayaran());
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
