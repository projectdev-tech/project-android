package com.project.projectnew.ordercreation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.project.projectnew.R;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private final boolean isKeranjangMode;
    private final OnQuantityChangedListener quantityChangedListener;

    public interface OnQuantityChangedListener {
        void onQuantityChanged(Product product);
    }

    public ProductAdapter(List<Product> list, boolean isKeranjangMode, OnQuantityChangedListener listener) {
        this.productList = list;
        this.isKeranjangMode = isKeranjangMode;
        this.quantityChangedListener = listener;
    }

    @NonNull @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.tvName.setText(product.getName());
        holder.tvUnit.setText(product.getUnit());
        holder.tvPrice.setText(product.getPrice());

        // --- PERBAIKAN UTAMA PADA BLOK LOGIKA INI ---
        if (product.getQuantity() > 0) {
            // Tampilan jika produk ada di keranjang
            holder.btnTambah.setVisibility(View.GONE);
            holder.layoutJumlah.setVisibility(View.VISIBLE);
            holder.tvQuantity.setText(String.valueOf(product.getQuantity()));
            holder.tvStock.setVisibility(View.GONE);
            // Tombol sampah selalu muncul jika kuantitas > 0
            holder.btnTrash.setVisibility(View.VISIBLE);
        } else {
            // Tampilan jika produk belum ada di keranjang
            holder.layoutJumlah.setVisibility(View.GONE);
            holder.btnTrash.setVisibility(View.GONE); // Sembunyikan tombol sampah
            holder.tvStock.setVisibility(View.VISIBLE);

            if (product.getStock() > 0) {
                holder.btnTambah.setVisibility(View.VISIBLE);
                holder.tvStock.setText("Tersedia");
                holder.btnTambah.setEnabled(true);
            } else {
                holder.btnTambah.setVisibility(View.GONE);
                holder.tvStock.setText("Habis");
            }
        }

        holder.btnTambah.setOnClickListener(v -> {
            if (product.getQuantity() < product.getStock()) {
                product.setQuantity(1);
                // Cukup panggil notifyItemChanged, onBindViewHolder akan menangani sisanya
                notifyItemChanged(position);
                if (quantityChangedListener != null) {
                    quantityChangedListener.onQuantityChanged(product);
                }
            }
        });

        holder.btnPlus.setOnClickListener(v -> {
            if (product.getQuantity() < product.getStock()) {
                product.setQuantity(product.getQuantity() + 1);
                notifyItemChanged(position);
                if (quantityChangedListener != null) {
                    quantityChangedListener.onQuantityChanged(product);
                }
            }
        });

        holder.btnMinus.setOnClickListener(v -> {
            int oldQuantity = product.getQuantity();
            if (oldQuantity > 0) {
                product.setQuantity(oldQuantity - 1);

                // Panggil listener terlebih dahulu untuk update DB
                if (quantityChangedListener != null) {
                    quantityChangedListener.onQuantityChanged(product);
                }

                // Logika tampilan
                if (isKeranjangMode && product.getQuantity() == 0) {
                    productList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, productList.size());
                } else {
                    notifyItemChanged(position);
                }
            }
        });

        holder.btnTrash.setOnClickListener(v -> {
            product.setQuantity(0);

            // Panggil listener terlebih dahulu untuk update DB
            if (quantityChangedListener != null) {
                quantityChangedListener.onQuantityChanged(product);
            }

            // Logika tampilan
            if (isKeranjangMode) {
                productList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, productList.size());
            } else {
                notifyItemChanged(position);
            }
        });
    }

    public void updateData(List<Product> newProductList) {
        this.productList.clear();
        this.productList.addAll(newProductList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() { return productList != null ? productList.size() : 0; }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvUnit, tvPrice, tvStock, tvQuantity;
        Button btnTambah;
        ImageButton btnPlus, btnMinus, btnTrash;
        View layoutJumlah;

        public ProductViewHolder(@NonNull View itemView) {
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