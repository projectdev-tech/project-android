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
import java.util.List;

public class NotifikasiAdapter extends RecyclerView.Adapter<NotifikasiAdapter.ViewHolder> {

    private final List<Notifikasi> notifikasiList;

    public NotifikasiAdapter(List<Notifikasi> notifikasiList) {
        this.notifikasiList = notifikasiList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notifikasi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notifikasi notifikasi = notifikasiList.get(position);
        holder.tvNotifTitle.setText(notifikasi.getJudul());
        holder.tvNotifBody.setText(notifikasi.getIsi());
        holder.tvNotifDate.setText(notifikasi.getTanggal());
        holder.ivNotifIcon.setImageResource(notifikasi.getIkonResId());

        // --- PERUBAHAN UTAMA DI SINI ---
        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            // Hanya bereaksi jika notifikasi adalah tipe "Transaksi"
            if ("Transaksi".equalsIgnoreCase(notifikasi.getTipe())) {
                Intent intent = new Intent(context, DetailTransaksiActivity.class);
                // Kirim seluruh objek notifikasi ke halaman detail
                intent.putExtra("NOTIFIKASI_EXTRA", notifikasi);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifikasiList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivNotifIcon;
        TextView tvNotifTitle, tvNotifBody, tvNotifDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivNotifIcon = itemView.findViewById(R.id.ivNotifIcon);
            tvNotifTitle = itemView.findViewById(R.id.tvNotifTitle);
            tvNotifBody = itemView.findViewById(R.id.tvNotifBody);
            tvNotifDate = itemView.findViewById(R.id.tvNotifDate);
        }
    }
}