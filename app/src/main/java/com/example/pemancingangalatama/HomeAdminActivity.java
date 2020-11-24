package com.example.pemancingangalatama;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.pemancingangalatama.SharedPreference.SharedPreferencedConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeAdminActivity extends AppCompatActivity {

    @BindView(R.id.img_admin_home)
    CircleImageView imgAdminHome;
    @BindView(R.id.text_nama_home_admin)
    TextView textNamaHomeAdmin;
    @BindView(R.id.text_level_home_admin)
    TextView textLevelHomeAdmin;
    @BindView(R.id.btn_keluar_admin)
    Button btnKeluarAdmin;
    @BindView(R.id.relative_header)
    RelativeLayout relativeHeader;
    @BindView(R.id.card_kelola_pesanan)
    CardView cardKelolaPesanan;
    @BindView(R.id.card_kelola_berita)
    CardView cardKelolaBerita;
    @BindView(R.id.card_kelola_informasi)
    CardView cardKelolaInformasi;
    @BindView(R.id.card_kelola_user)
    CardView cardKelolaUser;

    private SharedPreferencedConfig preferencedConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        ButterKnife.bind(this);
        preferencedConfig = new SharedPreferencedConfig(this);

        textNamaHomeAdmin.setText(preferencedConfig.getPreferenceNamaLengkap());
        textLevelHomeAdmin.setText(preferencedConfig.getPreferenceLevelUser());

        final String image = preferencedConfig.getPreferenceImage();

        Glide.with(HomeAdminActivity.this)
                .load(image)
                .error(R.mipmap.ic_launcher)
                .into(imgAdminHome);

        btnKeluarAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeAdminActivity.this, "Logged Out...", Toast.LENGTH_SHORT).show();
                preferencedConfig.savePrefBoolean(SharedPreferencedConfig.PREFERENCE_IS_LOGIN, false);
                startActivity(new Intent(HomeAdminActivity.this, MainActivity.class));
                finish();
            }
        });

        cardKelolaPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeAdminActivity.this, KelolaPesananActivity.class));
                finish();
            }
        });

        cardKelolaBerita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeAdminActivity.this, KelolaBeritaActivity.class));
                finish();
            }
        });

        cardKelolaInformasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeAdminActivity.this, KelolaInformasiActivity.class));
                finish();
            }
        });

        cardKelolaUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeAdminActivity.this, KelolaUserActivity.class));
                finish();
            }
        });
    }
}