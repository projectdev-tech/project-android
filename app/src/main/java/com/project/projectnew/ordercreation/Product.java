package com.project.projectnew.ordercreation;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "products")
public class Product implements Serializable {

    @PrimaryKey
    @NonNull
    private String id;

    private String name;
    private String unit;
    private String price;
    private int stock;
    private int quantity; // Kuantitas di keranjang
    private String category; // PENAMBAHAN FIELD KATEGORI

    // Konstruktor harus ada untuk Room
    public Product(@NonNull String id, String name, String unit, String price, int stock, int quantity, String category) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.price = price;
        this.stock = stock;
        this.quantity = quantity;
        this.category = category; // PENAMBAHAN FIELD KATEGORI
    }

    // Getters
    @NonNull
    public String getId() { return id; }
    public String getName() { return name; }
    public String getUnit() { return unit; }
    public String getPrice() { return price; }
    public int getStock() { return stock; }
    public int getQuantity() { return quantity; }
    public String getCategory() { return category; } // PENAMBAHAN GETTER

    // Setter
    public void setQuantity(int quantity) { this.quantity = quantity; }
}