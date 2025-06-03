package com.project.projectnew.ordercreation;

import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    private static final ProductManager instance = new ProductManager();

    private final List<Product> productList;

    private ProductManager() {
        productList = new ArrayList<>();
        // Produk hanya diinisialisasi sekali, sekarang menyertakan ID unik
        productList.add(new Product("p001", "Nama Produk", "Satuan", "Rp 10.000", 1));
        productList.add(new Product("p002", "Nama Produk", "Satuan", "Rp 20.000", 2));
        productList.add(new Product("p003", "Nama Produk", "Satuan", "Rp 40.000", 4));
        productList.add(new Product("p004", "Nama Produk", "Satuan", "Rp 80.000", 8));
    }

    public static ProductManager getInstance() {
        return instance;
    }

    // Mengembalikan semua produk
    public List<Product> getProducts() {
        return productList;
    }

    // Mengembalikan hanya produk yang quantity > 0 (produk yang ada di keranjang)
    public List<Product> getSelectedProducts() {
        List<Product> selected = new ArrayList<>();
        for (Product p : productList) {
            if (p.getQuantity() > 0) {
                selected.add(p);
            }
        }
        return selected;
    }
}
