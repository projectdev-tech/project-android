import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.projectnew.R;

public class activity_checkout extends AppCompatActivity {

    TextView txtQty, txtSubtotal, txtDiskon, txtPengiriman, txtTotal;
    Button btnLanjut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        txtQty = findViewById(R.id.txtQty);
        int qty = 5;
        int subtotal = 50000;

        txtSubtotal = findViewById(R.id.txtSubtotal);
        txtDiskon = findViewById(R.id.txtDiskon);
        txtPengiriman = findViewById(R.id.txtPengiriman);
        txtTotal = findViewById(R.id.txtTotal);
        btnLanjut = findViewById(R.id.btnLanjut);
        int diskon = 0;
        int pengiriman = 0;
        int total = subtotal - diskon + pengiriman;

        txtQty.setText(String.valueOf(qty));
        txtSubtotal.setText("Rp. " + subtotal);
        txtDiskon.setText("Rp. " + diskon);
        txtPengiriman.setText("Rp. " + pengiriman);
        txtTotal.setText("Rp. " + total);

        btnLanjut.setOnClickListener(view -> {
            Toast.makeText(this, "Lanjut ke pembayaran...", Toast.LENGTH_SHORT).show();
        });
    }
}





