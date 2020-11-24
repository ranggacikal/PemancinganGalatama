package com.example.pemancingangalatama;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.pemancingangalatama.adapter.BeritaAdapter;
import com.example.pemancingangalatama.api.ConfigRetrofit;
import com.example.pemancingangalatama.model.DataBeritaItem;
import com.example.pemancingangalatama.model.ResponseDataBerita;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaBeritaActivity extends AppCompatActivity {

    @BindView(R.id.rv_berita_admin)
    RecyclerView rvBeritaAdmin;
    @BindView(R.id.bottom_navigation_tambah_berita)
    BottomNavigationView bottomNavigationTambahBerita;
    @BindView(R.id.refresh_berita)
    SwipeRefreshLayout refreshBerita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_berita);
        ButterKnife.bind(this);

        loadDataBerita();
        bottomNavigationHandler();

        refreshBerita.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDataBerita();
                refreshBerita.setRefreshing(false);
            }
        });
    }

    private void bottomNavigationHandler() {

        bottomNavigationTambahBerita.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.action_tambah_berita:

                        startActivity(new Intent(KelolaBeritaActivity.this, TambahBeritaActivity.class));
                        finish();

                }

                return false;
            }
        });
    }

    private void loadDataBerita() {
        ConfigRetrofit.service.DataBerita().enqueue(new Callback<ResponseDataBerita>() {
            @Override
            public void onResponse(Call<ResponseDataBerita> call, Response<ResponseDataBerita> response) {
                if (response.isSuccessful()) {

                    int status = response.body().getStatus();

                    if (status == 1) {
                        List<DataBeritaItem> beritaList = response.body().getDataBerita();
                        BeritaAdapter adapter = new BeritaAdapter(KelolaBeritaActivity.this, beritaList);
                        rvBeritaAdmin.setAdapter(adapter);
                        rvBeritaAdmin.setLayoutManager(new LinearLayoutManager(KelolaBeritaActivity.this));
                    } else {
                        Toast.makeText(KelolaBeritaActivity.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(KelolaBeritaActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataBerita> call, Throwable t) {
                Toast.makeText(KelolaBeritaActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(KelolaBeritaActivity.this, HomeAdminActivity.class));
        finish();
    }
}