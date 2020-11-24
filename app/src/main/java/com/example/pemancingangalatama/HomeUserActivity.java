package com.example.pemancingangalatama;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pemancingangalatama.SharedPreference.SharedPreferencedConfig;
import com.example.pemancingangalatama.adapter.BeritaAdapter;
import com.example.pemancingangalatama.api.ConfigRetrofit;
import com.example.pemancingangalatama.model.DataBeritaItem;
import com.example.pemancingangalatama.model.ResponseDataBerita;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeUserActivity extends AppCompatActivity {

    @BindView(R.id.img_user_home)
    CircleImageView imgUserHome;
    @BindView(R.id.text_nama_home)
    TextView textNamaHome;
    @BindView(R.id.relative_header)
    RelativeLayout relativeHeader;
    @BindView(R.id.rv_berita_user)
    RecyclerView rvBeritaUser;
    @BindView(R.id.btn_daftar)
    LinearLayout btnDaftar;
    @BindView(R.id.btn_pembayaran)
    LinearLayout btnPembayaran;
    @BindView(R.id.btn_informasi)
    LinearLayout btnInformasi;
    @BindView(R.id.btn_keluar_user)
    Button btnKeluarUser;

    private SharedPreferencedConfig preferencedConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);
        ButterKnife.bind(this);

        preferencedConfig = new SharedPreferencedConfig(this);

        loadListBerita();

        textNamaHome.setText(preferencedConfig.getPreferenceNamaLengkap());

        final String image = preferencedConfig.getPreferenceImage();

        Glide.with(HomeUserActivity.this)
                .load(image)
                .error(R.mipmap.ic_launcher)
                .into(imgUserHome);

        btnKeluarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeUserActivity.this, "Logged Out...", Toast.LENGTH_SHORT).show();
                preferencedConfig.savePrefBoolean(SharedPreferencedConfig.PREFERENCE_IS_LOGIN, false);
                startActivity(new Intent(HomeUserActivity.this, MainActivity.class));
                finish();
            }
        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeUserActivity.this, PesanActivity.class));
                finish();
            }
        });

        btnPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeUserActivity.this, PembayaranActivity.class));
                finish();
            }
        });

        btnInformasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeUserActivity.this, InformasiUserActivity.class));
                finish();
            }
        });
    }

    private void loadListBerita() {

        ConfigRetrofit.service.DataBerita().enqueue(new Callback<ResponseDataBerita>() {
            @Override
            public void onResponse(Call<ResponseDataBerita> call, Response<ResponseDataBerita> response) {
                if (response.isSuccessful()) {

                    int status = response.body().getStatus();

                    if (status == 1) {
                        List<DataBeritaItem> beritaList = response.body().getDataBerita();
                        BeritaAdapter adapter = new BeritaAdapter(HomeUserActivity.this, beritaList);
                        rvBeritaUser.setAdapter(adapter);
                        rvBeritaUser.setLayoutManager(new LinearLayoutManager(HomeUserActivity.this));
                    } else {
                        Toast.makeText(HomeUserActivity.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HomeUserActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataBerita> call, Throwable t) {
                Toast.makeText(HomeUserActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}