package com.example.pemancingangalatama;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.pemancingangalatama.SharedPreference.SharedPreferencedConfig;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailBeritaActivity extends AppCompatActivity {

    @BindView(R.id.img_detail_berita)
    ImageView imgDetailBerita;
    @BindView(R.id.text_judul_detail_berita)
    TextView textJudulDetailBerita;
    @BindView(R.id.text_tanggal_detail_berita)
    TextView textTanggalDetailBerita;
    @BindView(R.id.text_isi_detail_berita)
    TextView textIsiDetailBerita;

    public static final String EXTRA_IMAGE_BERITA = "extraImageBerita";
    public static final String EXTRA_JUDUL_BERITA = "extraJudulBerita";
    public static final String EXTRA_TANGGAL_BERITA = "extraTanggalBerita";
    public static final String EXTRA_ISI_BERITA = "extraIsiBerita";

    private SharedPreferencedConfig preferencedConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_berita);
        ButterKnife.bind(this);
        preferencedConfig = new SharedPreferencedConfig(this);

        final String image = getIntent().getStringExtra(EXTRA_IMAGE_BERITA);
        String judul = getIntent().getStringExtra(EXTRA_JUDUL_BERITA);
        String tanggal = getIntent().getStringExtra(EXTRA_TANGGAL_BERITA);
        String isi = getIntent().getStringExtra(EXTRA_ISI_BERITA);

        Glide.with(DetailBeritaActivity.this)
                .load(image)
                .error(R.mipmap.ic_launcher)
                .into(imgDetailBerita);

        textJudulDetailBerita.setText(judul);
        textTanggalDetailBerita.setText(tanggal);
        textIsiDetailBerita.setText(isi);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String level = preferencedConfig.getPreferenceLevelUser();

        if (level.equals("User")){
            startActivity(new Intent(DetailBeritaActivity.this, HomeUserActivity.class));
            finish();
        }else if (level.equals("Admin")){
            startActivity(new Intent(DetailBeritaActivity.this, KelolaBeritaActivity.class));
            finish();
        }
    }
}