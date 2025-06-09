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

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> {

    private final Context context;
    private final List<InfoItem> infoList;

    public InfoAdapter(Context context, List<InfoItem> infoList) {
        this.context = context;
        this.infoList = infoList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, time;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textJudulInfo);
            description = itemView.findViewById(R.id.textDeskripsiInfo);
            time = itemView.findViewById(R.id.textWaktuInfo);
        }
    }

    @NonNull
    @Override
    public InfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoAdapter.ViewHolder holder, int position) {
        InfoItem item = infoList.get(position);
        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());
        holder.time.setText(item.getTime());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailInfoActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }
}
