package com.project.projectnew.ordercreation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.projectnew.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Product> productList;
    private final ProductAdapter.TotalUpdateListener totalUpdateListener;

    public CartAdapter(List<Product> productList, ProductAdapter.TotalUpdateListener listener) {
        this.productList = productList;
        this.totalUpdateListener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.tvName.setText(product.getName());
        holder.tvUnit.setText(product.getUnit());
        holder.tvPrice.setText(product.getPrice());
        holder.tvQuantity.setText(String.valueOf(product.getQuantity()));

        holder.layoutJumlah.setVisibility(View.VISIBLE);
        holder.btnTrash.setVisibility(View.VISIBLE);
        holder.btnTambah.setVisibility(View.GONE);
        holder.tvStock.setVisibility(View.GONE);

        holder.btnPlus.setOnClickListener(v -> {
            if (product.getQuantity() < product.getStock()) {
                product.setQuantity(product.getQuantity() + 1);
                notifyItemChanged(position);
                totalUpdateListener.updateTotal(productList);
            }
        });

        holder.btnMinus.setOnClickListener(v -> {
            if (product.getQuantity() > 1) {
                product.setQuantity(product.getQuantity() - 1);
                notifyItemChanged(position);
                totalUpdateListener.updateTotal(productList);
            }
        });

        holder.btnTrash.setOnClickListener(v -> {
            productList.remove(position); // 🔥 HAPUS dari list
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, productList.size());
            totalUpdateListener.updateTotal(productList);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvUnit, tvPrice, tvStock, tvQuantity;
        View layoutJumlah;
        ImageButton btnPlus, btnMinus, btnTrash;
        TextView btnTambah;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvUnit = itemView.findViewById(R.id.tvUnit);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvStock = itemView.findViewById(R.id.tvStock);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnTambah = itemView.findViewById(R.id.btnTambah);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnTrash = itemView.findViewById(R.id.btnTrash);
            layoutJumlah = itemView.findViewById(R.id.layoutJumlah);
        }
    }
}
