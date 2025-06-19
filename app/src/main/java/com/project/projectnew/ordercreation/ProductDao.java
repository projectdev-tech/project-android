package com.project.projectnew.ordercreation;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Product> products);

    @Update
    void updateProduct(Product product);

    @Query("SELECT * FROM products ORDER BY name ASC")
    List<Product> getAllProducts();

    @Query("SELECT * FROM products WHERE quantity > 0")
    List<Product> getProductsInCart();

    @Query("UPDATE products SET quantity = 0")
    void resetAllQuantities();
}