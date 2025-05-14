package com.example.shope;



import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class PasswordChangeActivity extends AppCompatActivity {

    private EditText etOldPassword;
    private EditText etNewPassword;
    private EditText etConfirmNewPassword;
    private ImageButton btnToggleOldPassword;
    private ImageButton btnToggleNewPassword;
    private ImageButton btnToggleConfirmPassword;
    private Button btnSimpan;

    private boolean isOldPasswordVisible = false;
    private boolean isNewPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasword_change);

        // Setup ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Password");
        }

        // Initialize views
        etOldPassword = findViewById(R.id.et_old_password);
        etNewPassword = findViewById(R.id.et_new_password);
        etConfirmNewPassword = findViewById(R.id.et_confirm_new_password);
        btnToggleOldPassword = findViewById(R.id.btn_toggle_old_password);
        btnToggleNewPassword = findViewById(R.id.btn_toggle_new_password);
        btnToggleConfirmPassword = findViewById(R.id.btn_toggle_confirm_password);
        btnSimpan = findViewById(R.id.btn_simpan);

        // Setup password toggle listeners
        setupPasswordToggles();

        // Setup save button listener
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndSavePassword();
            }
        });
    }

    private void setupPasswordToggles() {
        // Toggle visibility for old password
        btnToggleOldPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(etOldPassword, btnToggleOldPassword, isOldPasswordVisible);
                isOldPasswordVisible = !isOldPasswordVisible;
            }
        });

        // Toggle visibility for new password
        btnToggleNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(etNewPassword, btnToggleNewPassword, isNewPasswordVisible);
                isNewPasswordVisible = !isNewPasswordVisible;
            }
        });

        // Toggle visibility for confirm password
        btnToggleConfirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(etConfirmNewPassword, btnToggleConfirmPassword, isConfirmPasswordVisible);
                isConfirmPasswordVisible = !isConfirmPasswordVisible;
            }
        });
    }

    private void togglePasswordVisibility(EditText editText, ImageButton toggleButton, boolean isCurrentlyVisible) {
        // Update EditText and toggle button based on current visibility state
        if (isCurrentlyVisible) {
            // Hide password
            editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            toggleButton.setImageResource(R.drawable.ic_visibility_off);
        } else {
            // Show password
            editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            toggleButton.setImageResource(R.drawable.ic_visibility_off);
        }

        // Move cursor to the end of text
        editText.setSelection(editText.getText().length());
    }

    private void validateAndSavePassword() {
        String oldPassword = etOldPassword.getText().toString().trim();
        String newPassword = etNewPassword.getText().toString().trim();
        String confirmNewPassword = etConfirmNewPassword.getText().toString().trim();

        // Validate old password
        if (TextUtils.isEmpty(oldPassword)) {
            etOldPassword.setError("Silakan masukkan password lama Anda");
            etOldPassword.requestFocus();
            return;
        }

        // Validate new password
        if (TextUtils.isEmpty(newPassword)) {
            etNewPassword.setError("Silakan masukkan password baru");
            etNewPassword.requestFocus();
            return;
        }

        // Validate confirm password
        if (TextUtils.isEmpty(confirmNewPassword)) {
            etConfirmNewPassword.setError("Silakan konfirmasi password baru Anda");
            etConfirmNewPassword.requestFocus();
            return;
        }

        // Check if new password and confirm password match
        if (!newPassword.equals(confirmNewPassword)) {
            etConfirmNewPassword.setError("Password baru tidak cocok");
            etConfirmNewPassword.requestFocus();
            return;
        }

        // Here you would typically call your API to change the password
        // For demo purposes, we'll just show a toast message
        Toast.makeText(this, "Password berhasil diubah", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
