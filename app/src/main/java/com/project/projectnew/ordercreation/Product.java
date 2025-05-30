package com.project.projectnew.ordercreation;

import java.io.Serializable;

public class Product implements Serializable {

    private String id;
    private String name;
    private String unit;
    private String price;
    private int stock;
    private int quantity;

    // Konstruktor
    public Product(String id, String name, String unit, String price, int stock) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.price = price;
        this.stock = stock;
        this.quantity = 0;
    }

    // Getter
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public String getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public int getQuantity() {
        return quantity;
    }

    // Setter
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
