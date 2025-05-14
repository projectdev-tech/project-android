import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.project.projectnew.R;

public class Pembayaranactivity extends AppCompatActivity {
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener((View v) -> {
            finish(); // kembali ke layar sebelumnya
        });
    }
}


