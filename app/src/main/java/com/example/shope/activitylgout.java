package com.example.shope;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;

public class activitylgout extends Dialog {

    private OnLogoutListener logoutListener;

    public interface OnLogoutListener {
        void onLogout();
        void onCancel();
    }

    public activitylgout(@NonNull Context context, OnLogoutListener listener) {
        super(context);
        this.logoutListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_logout);

        // Make dialog background transparent
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Initialize buttons
        Button btnCancel = findViewById(R.id.btnCancel);
        Button btnLogout = findViewById(R.id.btnLogout);

        // Set click listeners
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (logoutListener != null) {
                    logoutListener.onCancel();
                }
                dismiss();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (logoutListener != null) {
                    logoutListener.onLogout();
                }
                dismiss();
            }
        });
    }
