package com.project.projectnew.ordercreation;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://10.0.2.2:8000/";
    private static Retrofit retrofit;
    private static RequestQueue volleyRequestQueue;

    // Retrofit client
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    // Volley request queue
    public static RequestQueue getVolleyQueue(Context context) {
        if (volleyRequestQueue == null) {
            volleyRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return volleyRequestQueue;
    }

    // Tambahan: getter untuk base URL (optional)
    public static String getBaseUrl() {
        return BASE_URL;
    }
}
