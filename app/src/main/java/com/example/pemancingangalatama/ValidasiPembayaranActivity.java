package com.example.pemancingangalatama;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pemancingangalatama.adapter.ValidasiPembayaranAdapter;
import com.example.pemancingangalatama.api.ConfigRetrofit;
import com.example.pemancingangalatama.model.DataPembayaranUserItem;
import com.example.pemancingangalatama.model.ResponseDataPembayaranUser;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ValidasiPembayaranActivity extends AppCompatActivity {

    public static final String EXTRA_ID_PEMESANAN = "extraIdPemesanan";
    @BindView(R.id.rv_validasi_pemesanan)
    RecyclerView rvValidasiPemesanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validasi_pembayaran);
        ButterKnife.bind(this);

        loadRecyclerValidasi();

    }

    private void loadRecyclerValidasi() {

        String id_pemesanan = getIntent().getStringExtra(EXTRA_ID_PEMESANAN);

        ConfigRetrofit.service.GetDataPembayaran(id_pemesanan).enqueue(new Callback<ResponseDataPembayaranUser>() {
            @Override
            public void onResponse(Call<ResponseDataPembayaranUser> call, Response<ResponseDataPembayaranUser> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();

                    if (status == 1){

                        List<DataPembayaranUserItem> pembayaranList = response.body().getDataPembayaranUser();
                        ValidasiPembayaranAdapter adapter = new ValidasiPembayaranAdapter(ValidasiPembayaranActivity.this, pembayaranList);
                        rvValidasiPemesanan.setAdapter(adapter);
                        rvValidasiPemesanan.setLayoutManager(new LinearLayoutManager(ValidasiPembayaranActivity.this));
                    }else{
                        Toast.makeText(ValidasiPembayaranActivity.this, "Data Belum Ada...", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(ValidasiPembayaranActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataPembayaranUser> call, Throwable t) {
                Toast.makeText(ValidasiPembayaranActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ValidasiPembayaranActivity.this, KelolaPesananActivity.class));
        finish();
    }
}