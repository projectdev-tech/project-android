package com.project.projectnew.ordercreation;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.project.projectnew.R;

public class DetailPromoInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_promo_info);

        ImageButton btnBack = findViewById(R.id.btnBack);
        TextView tvToolbarTitle = findViewById(R.id.tvToolbarTitle);
        TextView tvPromoTitle = findViewById(R.id.tvPromoTitle);
        TextView tvPromoDate = findViewById(R.id.tvPromoDate);
        TextView tvPromoBody = findViewById(R.id.tvPromoBody);
        // --- 1. Ambil referensi ke ImageView banner ---
        ImageView ivBanner = findViewById(R.id.ivBanner);

        btnBack.setOnClickListener(v -> finish());

        Notifikasi notifikasi = (Notifikasi) getIntent().getSerializableExtra("NOTIFIKASI_PROMO_INFO_EXTRA");

        if (notifikasi != null) {
            tvToolbarTitle.setText(notifikasi.getTipe());
            tvPromoTitle.setText(notifikasi.getJudul());
            tvPromoDate.setText(notifikasi.getTanggal());
            tvPromoBody.setText(notifikasi.getIsi());

            // --- 2. Tambahkan logika untuk memilih gambar banner ---
            String tipe = notifikasi.getTipe();
            if ("Promo".equalsIgnoreCase(tipe)) {
                ivBanner.setImageResource(R.drawable.banner_tag);
            } else if ("Info".equalsIgnoreCase(tipe)) {
                ivBanner.setImageResource(R.drawable.banner_direct_inbox);
            } else {
                // Fallback jika ada tipe lain
                ivBanner.setImageResource(R.drawable.img_banner_1);
            }
        }
    }
}