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

public class PromoAdapter extends RecyclerView.Adapter<PromoAdapter.PromoViewHolder> {

    private Context context;
    private List<PromoModel> promoList;

    public PromoAdapter(Context context, List<PromoModel> promoList) {
        this.context = context;
        this.promoList = promoList;
    }

    @NonNull
    @Override
    public PromoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_promo, parent, false);
        return new PromoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromoViewHolder holder, int position) {
        PromoModel promo = promoList.get(position);
        holder.title.setText(promo.getTitle());
        holder.description.setText(promo.getDescription());
        holder.time.setText(promo.getTime());

        holder.title.setOnClickListener(v -> {
            context.startActivity(new Intent(context, DetailPromoActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        return promoList.size();
    }

    static class PromoViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, time;

        public PromoViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textJudulPromo);
            description = itemView.findViewById(R.id.textDeskripsiPromo);
            time = itemView.findViewById(R.id.textWaktuPromo);
        }
    }
}
