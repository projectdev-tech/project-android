package com.project.projectnew.ordercreation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.projectnew.R;

import java.util.List;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.ViewHolder> {

    private final Context context;
    private final List<TransaksiModel> transaksiList;

    public TransaksiAdapter(Context context, List<TransaksiModel> transaksiList) {
        this.context = context;
        this.transaksiList = transaksiList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaksi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TransaksiModel transaksi = transaksiList.get(position);
        holder.textJudul.setText(transaksi.getJudul());
        holder.textDeskripsi.setText(transaksi.getDeskripsi());
        holder.textWaktu.setText(transaksi.getWaktu());

        holder.textJudul.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailTransaksiActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return transaksiList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textJudul, textDeskripsi, textWaktu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textJudul = itemView.findViewById(R.id.textJudulTransaksi);
            textDeskripsi = itemView.findViewById(R.id.textDeskripsiTransaksi);
            textWaktu = itemView.findViewById(R.id.textWaktuTransaksi);
        }
    }
}
