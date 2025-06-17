package com.project.projectnew.ordercreation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.project.projectnew.R;

import java.util.List;

public class ShippingStatusAdapter extends RecyclerView.Adapter<ShippingStatusAdapter.ViewHolder> {

    private final Context context;
    private final List<ShippingStatus> statusList;

    public ShippingStatusAdapter(Context context, List<ShippingStatus> statusList) {
        this.context = context;
        this.statusList = statusList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shipping_status, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShippingStatus status = statusList.get(position);

        holder.tvStatusDate.setText(status.getStatusDate());
        holder.tvStatusTime.setText(status.getStatusTime());
        holder.tvStatusDescription.setText(status.getStatusDescription());

        if (status.isActive()) {
            holder.ivStatusIcon.setImageResource(R.drawable.tick_circle_active);
            holder.tvStatusDate.setTextColor(ContextCompat.getColor(context, R.color.black));
        } else {
            holder.ivStatusIcon.setImageResource(R.drawable.tick_circle); // Pastikan drawable ini ada
            holder.tvStatusDate.setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray));
        }

        // Sembunyikan garis vertikal untuk item terakhir
        if (position == statusList.size() - 1) {
            holder.viewLine.setVisibility(View.GONE);
        } else {
            holder.viewLine.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivStatusIcon;
        View viewLine;
        TextView tvStatusDate, tvStatusTime, tvStatusDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivStatusIcon = itemView.findViewById(R.id.ivStatusIcon);
            viewLine = itemView.findViewById(R.id.viewLine);
            tvStatusDate = itemView.findViewById(R.id.tvStatusDate);
            tvStatusTime = itemView.findViewById(R.id.tvStatusTime);
            tvStatusDescription = itemView.findViewById(R.id.tvStatusDescription);
        }
    }
}
