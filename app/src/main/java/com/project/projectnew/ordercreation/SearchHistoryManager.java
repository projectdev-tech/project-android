package com.project.projectnew.ordercreation;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SearchHistoryManager {

    private static final String PREFS_NAME = "SearchPrefs";
    private static final String HISTORY_KEY = "SearchHistory";
    private static final int MAX_HISTORY_SIZE = 4; // Batasi riwayat hingga 4 item

    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();

    public SearchHistoryManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public List<String> getSearchHistory() {
        String json = sharedPreferences.getString(HISTORY_KEY, null);
        if (json == null) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void saveSearchQuery(String query) {
        if (query == null || query.trim().isEmpty()) {
            return;
        }

        List<String> history = getSearchHistory();

        // Hapus duplikat jika ada
        history.remove(query);

        // Tambahkan query baru di posisi paling atas (indeks 0)
        history.add(0, query);

        // Pastikan ukuran riwayat tidak melebihi batas
        while (history.size() > MAX_HISTORY_SIZE) {
            history.remove(history.size() - 1);
        }

        // Simpan kembali ke SharedPreferences
        String json = gson.toJson(history);
        sharedPreferences.edit().putString(HISTORY_KEY, json).apply();
    }
}