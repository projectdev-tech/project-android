package com.example.shope;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class TransactionListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ddaftar_transaksi);

        // Setup toolbar buttons
        ImageButton backButton = findViewById(R.id.back_button);
        ImageButton optionsButton = findViewById(R.id.options_button);

        backButton.setOnClickListener(v -> onBackPressed());
        optionsButton.setOnClickListener(v ->
                Toast.makeText(this, "Options clicked", Toast.LENGTH_SHORT).show()
        );

        // Setup RecyclerView
        RecyclerView recyclerView = findViewById(R.id.transaction_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }