package com.project.projectnew.ordercreation;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OrderManager {

    private static final String PREF_NAME = "orders";
    private static final String KEY_PENDING_ORDERS = "pending_orders";

    public static void savePendingOrders(Context context, List<Order> orders) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = new Gson().toJson(orders);
        editor.putString(KEY_PENDING_ORDERS, json);
        editor.apply();
    }

    public static List<Order> getPendingOrders(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_PENDING_ORDERS, null);
        if (json == null) return new ArrayList<>();
        Type type = new TypeToken<List<Order>>() {}.getType();
        return new Gson().fromJson(json, type);
    }
}
