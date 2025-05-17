import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.projectnew.R;

public class PaymentActivity extends AppCompatActivity {

    private TextView txtVaNumber;
    private Button btnCopy;
    private LinearLayout layoutMbanking, layoutBanking, layoutAtm;
    private LinearLayout contentMbanking;
    private ImageView iconMbanking, iconBanking, iconAtm;
    private Button btnConfirm;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pyement);

        // Initialize views
        txtVaNumber = findViewById(R.id.txtVaNumber);
        btnCopy = findViewById(R.id.btnCopy);
        layoutMbanking = findViewById(R.id.layoutMbanking);
        layoutBanking = findViewById(R.id.layoutBanking);
        layoutAtm = findViewById(R.id.layoutAtm);
        contentMbanking = findViewById(R.id.contentMbanking);
        iconMbanking = findViewById(R.id.iconMbanking);
        iconBanking = findViewById(R.id.iconBanking);
        iconAtm = findViewById(R.id.iconAtm);
        btnConfirm = findViewById(R.id.btnCopy);
        btnBack = findViewById(R.id.btnBack);

        setupListeners();
    }

    private void setupListeners() {
        // Copy VA number to clipboard
        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("VA Number", txtVaNumber.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(PaymentActivity.this, "Nomor VA disalin", Toast.LENGTH_SHORT).show();
            }
        });

        // Toggle mBanking instructions visibility
        layoutMbanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contentMbanking.getVisibility() == View.VISIBLE) {
                    contentMbanking.setVisibility(View.GONE);
                    iconMbanking.setImageResource(R.drawable.icon_down);
                } else {
                    contentMbanking.setVisibility(View.VISIBLE);
                    iconMbanking.setImageResource(R.drawable.icon_up);
                }
            }
        });

        // Toggle banking instructions visibility
        layoutBanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement toggle functionality similar to mBanking
                if (iconBanking.getTag() == null || iconBanking.getTag().equals("down")) {
                    iconBanking.setImageResource(R.drawable.icon_up);
                    iconBanking.setTag("up");
                    // Show banking content
                } else {
                    iconBanking.setImageResource(R.drawable.icon_down);
                    iconBanking.setTag("down");
                    // Hide banking content
                }
            }
        });

        // Toggle ATM instructions visibility
        layoutAtm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement toggle functionality similar to mBanking
                if (iconAtm.getTag() == null || iconAtm.getTag().equals("down")) {
                    iconAtm.setImageResource(R.drawable.icon_up);
                    iconAtm.setTag("up");
                    // Show ATM content
                } else {
                    iconAtm.setImageResource(R.drawable.icon_down);
                    iconAtm.setTag("down");
                    // Hide ATM content
                }
            }
        });

        // Confirm payment button
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PaymentActivity.this, "Pembayaran dikonfirmasi", Toast.LENGTH_SHORT).show();
                // Implement payment confirmation logic
            }
        });

        // Back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
