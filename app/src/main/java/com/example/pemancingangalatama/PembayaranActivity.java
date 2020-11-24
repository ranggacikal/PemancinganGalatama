package com.example.pemancingangalatama;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pemancingangalatama.SharedPreference.SharedPreferencedConfig;
import com.example.pemancingangalatama.adapter.PemesananUserAdapter;
import com.example.pemancingangalatama.api.ConfigRetrofit;
import com.example.pemancingangalatama.model.DataPembayaranUserItem;
import com.example.pemancingangalatama.model.DataPemesananUserItem;
import com.example.pemancingangalatama.model.ResponseDataPembayaranUser;
import com.example.pemancingangalatama.model.ResponseDataPemesananUser;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PembayaranActivity extends AppCompatActivity {

    @BindView(R.id.rv_pemesanan_user)
    RecyclerView rvPemesananUser;

    private SharedPreferencedConfig preferencedConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);
        ButterKnife.bind(this);
        preferencedConfig = new SharedPreferencedConfig(this);

        LoadRecyclerView();

    }

    private void LoadRecyclerView() {

        String id_user = preferencedConfig.getPreferenceIdUser();

        ConfigRetrofit.service.GetDataPemesananUser(id_user).enqueue(new Callback<ResponseDataPemesananUser>() {
            @Override
            public void onResponse(Call<ResponseDataPemesananUser> call, Response<ResponseDataPemesananUser> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();
                    String pesan = response.body().getPesan();

                    if (status == 1){
                        List<DataPemesananUserItem> pemesananUserList = response.body().getDataPemesananUser();
                        PemesananUserAdapter pemesananAdapter = new PemesananUserAdapter(PembayaranActivity.this, pemesananUserList);
                        rvPemesananUser.setAdapter(pemesananAdapter);
                        rvPemesananUser.setLayoutManager(new LinearLayoutManager(PembayaranActivity.this));
                    }else{
                        Toast.makeText(PembayaranActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(PembayaranActivity.this, "Terjadi Kesalahan...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataPemesananUser> call, Throwable t) {
                Toast.makeText(PembayaranActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PembayaranActivity.this, HomeUserActivity.class));
        finish();
    }
}