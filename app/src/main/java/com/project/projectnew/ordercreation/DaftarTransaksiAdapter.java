package com.project.projectnew.ordercreation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.project.projectnew.R;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DaftarTransaksiAdapter extends RecyclerView.Adapter<DaftarTransaksiAdapter.ViewHolder> {

    private final List<Order> orderList;

    public DaftarTransaksiAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daftar_transaksi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orderList.get(position);

        holder.tvNoOrder.setText(order.getNoOrder());

        SimpleDateFormat desiredFormat = new SimpleDateFormat("dd MMMM yyyy, HH.mm.ss", new Locale("in", "ID"));
        String formattedDate = desiredFormat.format(new Date(order.getWaktuPembayaran()));
        holder.tvTanggalOrder.setText(formattedDate);

        // --- PERUBAHAN UTAMA DI SINI ---
        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();

            // Buat Intent untuk membuka PesananDiterimaActivity
            Intent intent = new Intent(context, PesananDiterimaActivity.class);

            // Kirim seluruh objek Order yang diklik. Pastikan Order implements Serializable.
            intent.putExtra("ORDER_DETAIL", order);

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivStatusIcon;
        TextView tvNoOrder, tvTanggalOrder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivStatusIcon = itemView.findViewById(R.id.ivStatusIcon);
            tvNoOrder = itemView.findViewById(R.id.tvNoOrder);
            tvTanggalOrder = itemView.findViewById(R.id.tvTanggalOrder);
        }
    }
}