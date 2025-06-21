package com.project.projectnew.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.projectnew.R;

public class AccountPasswordActivity extends AppCompatActivity {

    private EditText etOldPassword, etNewPassword, etConfirmPassword;
    private ImageButton toggleOldPassword, toggleNewPassword, toggleConfirmPassword;

    private boolean showOld = false, showNew = false, showConfirm = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_password);

        etOldPassword = findViewById(R.id.et_old_password);
        etNewPassword = findViewById(R.id.et_new_password);
        etConfirmPassword = findViewById(R.id.et_confirm_new_password);

        toggleOldPassword = findViewById(R.id.btn_toggle_old_password);
        toggleNewPassword = findViewById(R.id.btn_toggle_new_password);
        toggleConfirmPassword = findViewById(R.id.btn_toggle_confirm_password);

        toggleOldPassword.setOnClickListener(v -> togglePasswordVisibility(etOldPassword, toggleOldPassword, "old"));
        toggleNewPassword.setOnClickListener(v -> togglePasswordVisibility(etNewPassword, toggleNewPassword, "new"));
        toggleConfirmPassword.setOnClickListener(v -> togglePasswordVisibility(etConfirmPassword, toggleConfirmPassword, "confirm"));

        findViewById(R.id.btn_simpan).setOnClickListener(v -> savePassword());

        ImageView back = findViewById(R.id.btnBack);
        back.setOnClickListener(v -> onBackPressed());
    }

    private void togglePasswordVisibility(EditText editText, ImageButton button, String field) {
        boolean current = false;

        if (field.equals("old")) {
            showOld = !showOld;
            current = showOld;
        } else if (field.equals("new")) {
            showNew = !showNew;
            current = showNew;
        } else if (field.equals("confirm")) {
            showConfirm = !showConfirm;
            current = showConfirm;
        }

        if (current) {
            editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            button.setImageResource(R.drawable.ic_visibility_on);
        } else {
            editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            button.setImageResource(R.drawable.ic_visibility_off);
        }

        editText.setSelection(editText.getText().length());
    }

    private void savePassword() {
        String oldPass = etOldPassword.getText().toString().trim();
        String newPass = etNewPassword.getText().toString().trim();
        String confirmPass = etConfirmPassword.getText().toString().trim();

        if (oldPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
            Toast.makeText(this, "Semua kolom wajib diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPass.equals(confirmPass)) {
            Toast.makeText(this, "Password baru dan konfirmasi tidak sama", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO: Logika update password ke database / server
        Toast.makeText(this, "Password berhasil diperbarui", Toast.LENGTH_SHORT).show();
        finish();
    }
}
