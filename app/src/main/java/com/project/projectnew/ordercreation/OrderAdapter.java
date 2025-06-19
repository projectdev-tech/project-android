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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.project.projectnew.R;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Order> orderList;
    private final List<Boolean> expandStates;

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_DIKIRIM = 2;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
        this.expandStates = new ArrayList<>();
        // Inisialisasi awal, akan disinkronkan lagi nanti
        for (int i = 0; i < orderList.size(); i++) {
            expandStates.add(false);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if ("Pesanan Dikirim".equalsIgnoreCase(orderList.get(position).getStatus())) {
            return VIEW_TYPE_DIKIRIM;
        }
        return VIEW_TYPE_NORMAL;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == VIEW_TYPE_DIKIRIM) {
            View view = inflater.inflate(R.layout.item_dikirim_order, parent, false);
            return new DikirimOrderViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_order, parent, false);
            return new OrderViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // --- PERBAIKAN UTAMA DI SINI ---
        // Memastikan 'expandStates' memiliki ukuran yang sama dengan 'orderList'
        while (expandStates.size() <= position) {
            expandStates.add(false);
        }
        // --------------------------------

        Order order = orderList.get(position);
        boolean isExpanded = expandStates.get(position);
        List<Product> productList = order.getProductList();

        List<Product> displayList = isExpanded ? productList : (productList.isEmpty() ? new ArrayList<>() : productList.subList(0, 1));

        if (holder.getItemViewType() == VIEW_TYPE_DIKIRIM) {
            bindDikirimViewHolder((DikirimOrderViewHolder) holder, order, displayList, isExpanded, position);
        } else {
            bindNormalViewHolder((OrderViewHolder) holder, order, displayList, isExpanded, position);
        }
    }

    private void bindNormalViewHolder(OrderViewHolder holder, Order order, List<Product> displayList, boolean isExpanded, int position) {
        Context context = holder.itemView.getContext();

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
        }

        holder.rvProducts.setLayoutManager(new LinearLayoutManager(context));
        holder.rvProducts.setAdapter(new ProductInOrderAdapter(displayList));
        setupExpandCollapse(holder.btnLihatLainnya, holder.tvLihatLainnya, holder.icLihatLainnya, order.getProductList().size(), isExpanded, position);

        if (holder.btnRincianPesanan != null) {
            holder.btnRincianPesanan.setOnClickListener(v -> {
                Intent intent;
                if ("Pesanan Diterima".equalsIgnoreCase(order.getStatus())) {
                    intent = new Intent(context, PesananDiterimaActivity.class);
                    intent.putExtra("ORDER_DETAIL", order);
                } else {
                    intent = new Intent(context, RincianPesananActivity.class);
                    intent.putExtra("total_harga", order.getTotalHarga());
                    intent.putExtra("waktu_pembayaran", order.getWaktuPembayaran());
                }
                context.startActivity(intent);
            });
        }
    }

    private void bindDikirimViewHolder(DikirimOrderViewHolder holder, Order order, List<Product> displayList, boolean isExpanded, int position) {
        Context context = holder.itemView.getContext();

        holder.tvNoOrder.setText(order.getNoOrder());
        holder.tvTanggalPembelian.setText(order.getTanggalPembelian());
        holder.tvStatus.setText(order.getStatus());
        holder.tvTotalHarga.setText(order.getTotalHarga());
        holder.tvNoTracking.setText(order.getNoTracking());
        holder.tvTanggalPengiriman.setText(order.getTanggalPengiriman());
        holder.tvEstimasiTiba.setText(order.getEstimasiTiba());
        holder.tvPembeli.setText(order.getPembeli());

        holder.rvProducts.setLayoutManager(new LinearLayoutManager(context));
        holder.rvProducts.setAdapter(new ProductInOrderAdapter(displayList));
        setupExpandCollapse(holder.btnLihatLainnya, holder.tvLihatLainnya, holder.icLihatLainnya, order.getProductList().size(), isExpanded, position);

        populateShippingStatus(holder.layoutShippingStatus, order.getShippingStatusList());
    }

    private void populateShippingStatus(LinearLayout layout, List<ShippingStatus> statusList) {
        Context context = layout.getContext();
        layout.removeAllViews();
        if (statusList == null || statusList.isEmpty()) {
            return;
        }

        LayoutInflater inflater = LayoutInflater.from(context);
        for (int i = 0; i < statusList.size(); i++) {
            ShippingStatus status = statusList.get(i);
            View statusView = inflater.inflate(R.layout.item_shipping_status, layout, false);

            TextView tvDate = statusView.findViewById(R.id.tvStatusDate);
            TextView tvTime = statusView.findViewById(R.id.tvStatusTime);
            TextView tvDesc = statusView.findViewById(R.id.tvStatusDescription);
            ImageView ivIcon = statusView.findViewById(R.id.ivStatusIcon);
            View line = statusView.findViewById(R.id.viewLine);

            tvDate.setText(status.getStatusDate());
            tvTime.setText(status.getStatusTime());
            tvDesc.setText(status.getStatusDescription());

            if (status.isActive()) {
                ivIcon.setImageResource(R.drawable.tick_circle_active);
                tvDate.setTextColor(ContextCompat.getColor(context, android.R.color.black));
            } else {
                ivIcon.setImageResource(R.drawable.tick_circle);
                tvDate.setTextColor(Color.GRAY);
            }

            line.setVisibility(i == statusList.size() - 1 ? View.GONE : View.VISIBLE);
            layout.addView(statusView);
        }
    }

    private void setupExpandCollapse(LinearLayout btnLihatLainnya, TextView tvLihatLainnya, ImageView icLihatLainnya, int productListSize, boolean isExpanded, int position) {
        if (productListSize > 1) {
            btnLihatLainnya.setVisibility(View.VISIBLE);
            tvLihatLainnya.setText(isExpanded ? "Lihat Lebih Sedikit" : "Lihat Lainnya");
            icLihatLainnya.setImageResource(isExpanded ? R.drawable.arrow_up : R.drawable.arrow_down);
            btnLihatLainnya.setOnClickListener(v -> {
                expandStates.set(position, !isExpanded);
                notifyItemChanged(position);
            });
        } else {
            btnLihatLainnya.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return orderList != null ? orderList.size() : 0;
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvNoOrder, tvTanggalPembelian, tvStatus, tvTotalHarga, tvLihatLainnya;
        RecyclerView rvProducts;
        LinearLayout btnLihatLainnya, layoutRincianPesanan;
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

    public static class DikirimOrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvNoOrder, tvTanggalPembelian, tvStatus, tvTotalHarga, tvNoTracking, tvTanggalPengiriman, tvEstimasiTiba, tvPembeli, tvLihatLainnya;
        RecyclerView rvProducts;
        LinearLayout btnLihatLainnya, layoutShippingStatus;
        ImageView icLihatLainnya;
        public DikirimOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNoOrder = itemView.findViewById(R.id.tvNoOrder);
            tvTanggalPembelian = itemView.findViewById(R.id.tvTanggalPembelian);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvTotalHarga = itemView.findViewById(R.id.tvTotalHarga);
            rvProducts = itemView.findViewById(R.id.rvProducts);
            btnLihatLainnya = itemView.findViewById(R.id.btnLihatLainnya);
            tvLihatLainnya = itemView.findViewById(R.id.tvLihatLainnya);
            icLihatLainnya = itemView.findViewById(R.id.icLihatLainnya);
            tvNoTracking = itemView.findViewById(R.id.tvNoTracking);
            tvTanggalPengiriman = itemView.findViewById(R.id.tvTanggalPengiriman);
            tvEstimasiTiba = itemView.findViewById(R.id.tvEstimasiTiba);
            tvPembeli = itemView.findViewById(R.id.tvPembeli);
            layoutShippingStatus = itemView.findViewById(R.id.layoutShippingStatus);
        }
    }
}