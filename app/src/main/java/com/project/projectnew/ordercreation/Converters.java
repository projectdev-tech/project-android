package com.project.projectnew.ordercreation;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class Converters {
    private static Gson gson = new Gson();

    @TypeConverter
    public static String fromProductList(List<Product> productList) {
        return gson.toJson(productList);
    }

    @TypeConverter
    public static List<Product> toProductList(String productListString) {
        if (productListString == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<Product>>() {}.getType();
        return gson.fromJson(productListString, listType);
    }

    @TypeConverter
    public static String fromShippingStatusList(List<ShippingStatus> statusList) {
        return gson.toJson(statusList);
    }

    @TypeConverter
    public static List<ShippingStatus> toShippingStatusList(String statusListString) {
        if (statusListString == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<ShippingStatus>>() {}.getType();
        return gson.fromJson(statusListString, listType);
    }
}