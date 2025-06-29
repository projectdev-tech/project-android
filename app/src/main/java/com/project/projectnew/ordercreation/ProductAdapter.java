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
    private final TotalUpdateListener totalUpdateListener;
    private final OnCartChangedListener cartChangedListener;
    private boolean isKeranjangMode;

    public interface TotalUpdateListener { void updateTotal(List<Product> products); }
    public interface OnCartChangedListener { void onCartChanged(List<Product> updatedProducts); }

    public ProductAdapter(List<Product> list, boolean isKeranjangMode, TotalUpdateListener listener) {
        this(list, isKeranjangMode, listener, null);
    }

    public ProductAdapter(List<Product> list, boolean isKeranjangMode, TotalUpdateListener listener, OnCartChangedListener cartChangedListener) {
        this.productList = list;
        this.isKeranjangMode = isKeranjangMode;
        this.totalUpdateListener = listener;
        this.cartChangedListener = cartChangedListener;
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

        // Logika ini sudah benar, saya hanya merapikannya agar lebih jelas
        // dan memastikan tombol sampah muncul dengan benar di mode keranjang
        if (isKeranjangMode) {
            holder.btnTrash.setVisibility(View.VISIBLE);
            holder.tvStock.setVisibility(View.GONE);
        } else {
            holder.btnTrash.setVisibility(product.getQuantity() > 0 ? View.VISIBLE : View.GONE);
            holder.tvStock.setVisibility(product.getQuantity() > 0 ? View.GONE : View.VISIBLE);
        }

        if (product.getQuantity() > 0) {
            holder.btnTambah.setVisibility(View.GONE);
            holder.layoutJumlah.setVisibility(View.VISIBLE);
            holder.tvQuantity.setText(String.valueOf(product.getQuantity()));
        } else {
            holder.layoutJumlah.setVisibility(View.GONE);
            if (product.getStock() > 0) {
                holder.btnTambah.setVisibility(View.VISIBLE);
                holder.tvStock.setText("Tersedia");
            } else {
                holder.btnTambah.setVisibility(View.GONE);
                holder.tvStock.setText("Habis");
            }
        }

        holder.btnTambah.setOnClickListener(v -> {
            if (product.getQuantity() < product.getStock()) {
                product.setQuantity(1); // Langsung set ke 1 saat pertama kali tambah
                notifyItemChanged(position);
                triggerUpdate();
            }
        });

        holder.btnPlus.setOnClickListener(v -> {
            if (product.getQuantity() < product.getStock()) {
                product.setQuantity(product.getQuantity() + 1);
                notifyItemChanged(position);
                triggerUpdate();
            }
        });

        holder.btnMinus.setOnClickListener(v -> {
            if (product.getQuantity() > 1) {
                product.setQuantity(product.getQuantity() - 1);
                notifyItemChanged(position);
                triggerUpdate();
            } else { // Jika kuantitas 1 lalu diminus, maka jadi 0
                product.setQuantity(0);
                if (isKeranjangMode) {
                    // Di keranjang, item akan dihapus dari list
                    productList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, productList.size());
                } else {
                    // Di halaman lain, hanya mengubah tampilan
                    notifyItemChanged(position);
                }
                triggerUpdate();
            }
        });

        // Logika tombol sampah tidak diubah, hanya dirapikan
        holder.btnTrash.setOnClickListener(v -> {
            product.setQuantity(0);
            if (isKeranjangMode) {
                productList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, productList.size());
            } else {
                notifyItemChanged(position);
            }
            triggerUpdate();
        });
    }

    // Metode ini ditambahkan untuk mempermudah update data dari luar
    public void updateData(List<Product> newProductList) {
        this.productList.clear();
        this.productList.addAll(newProductList);
        notifyDataSetChanged();
    }

    private void triggerUpdate() {
        if (totalUpdateListener != null) {
            totalUpdateListener.updateTotal(productList);
        }
        if (isKeranjangMode && cartChangedListener != null) {
            cartChangedListener.onCartChanged(productList);
        }
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