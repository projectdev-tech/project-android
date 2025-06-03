package com.project.projectnew.ordercreation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.project.projectnew.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductInOrderAdapter extends RecyclerView.Adapter<ProductInOrderAdapter.ViewHolder> {

    private List<Product> products;

    public ProductInOrderAdapter(List<Product> products) {
        this.products = products;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvProductPrice, tvProductQty;

        public ViewHolder(View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvProductQty = itemView.findViewById(R.id.tvProductQty);
        }
    }

    @Override
    public ProductInOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_in_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductInOrderAdapter.ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.tvProductName.setText(product.getName());

        // Format harga dengan Locale Indonesia
        Locale localeID = new Locale("in", "ID");
        NumberFormat format = NumberFormat.getCurrencyInstance(localeID);
        format.setMaximumFractionDigits(0);

        try {
            int harga = Integer.parseInt(product.getPrice().replaceAll("[^\\d]", ""));
            holder.tvProductPrice.setText(format.format(harga));
        } catch (NumberFormatException e) {
            holder.tvProductPrice.setText(product.getPrice()); // fallback jika error
        }

        holder.tvProductQty.setText("Qty: " + product.getQuantity());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
