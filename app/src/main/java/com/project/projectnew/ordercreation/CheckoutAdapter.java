package com.project.projectnew.ordercreation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.projectnew.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.ViewHolder> {

    private final List<Product> productList;

    public CheckoutAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public CheckoutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_checkout_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutAdapter.ViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.tvProductName.setText(product.getName());
        holder.tvProductUnit.setText(product.getUnit());
        holder.tvProductQty.setText("Qty " + product.getQuantity() + " x");

        // Format harga sesuai lokal Indonesia
        Locale localeID = new Locale("in", "ID");
        NumberFormat format = NumberFormat.getCurrencyInstance(localeID);
        format.setMaximumFractionDigits(0);
        int hargaSatuan = Integer.parseInt(product.getPrice().replaceAll("[^\\d]", ""));
        holder.tvProductPrice.setText(format.format(hargaSatuan));

        // Gambar default
        holder.ivProductImage.setImageResource(R.drawable.product); // gunakan gambar placeholder kamu
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvProductUnit, tvProductPrice, tvProductQty;
        ImageView ivProductImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImage = itemView.findViewById(R.id.imageView); // pastikan ID sesuai di XML
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductUnit = itemView.findViewById(R.id.tvProductUnit);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvProductQty = itemView.findViewById(R.id.tvProductQty);
        }
    }
}
