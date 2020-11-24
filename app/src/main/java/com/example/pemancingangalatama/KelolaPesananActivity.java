package com.example.pemancingangalatama;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pemancingangalatama.adapter.PemesananAdminAdapter;
import com.example.pemancingangalatama.api.ConfigRetrofit;
import com.example.pemancingangalatama.model.DataPemesananAdminItem;
import com.example.pemancingangalatama.model.ResponseDataPemesananAdmin;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaPesananActivity extends AppCompatActivity {

    @BindView(R.id.rv_pemesanan_admin)
    RecyclerView rvPemesananAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_pesanan);
        ButterKnife.bind(this);

        loadRecyclerPemesananAdmin();
    }

    private void loadRecyclerPemesananAdmin() {

        ConfigRetrofit.service.dataPemesananAdmin().enqueue(new Callback<ResponseDataPemesananAdmin>() {
            @Override
            public void onResponse(Call<ResponseDataPemesananAdmin> call, Response<ResponseDataPemesananAdmin> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();

                    if (status==1){

                        List<DataPemesananAdminItem> pemesananAdminList = response.body().getDataPemesananAdmin();
                        PemesananAdminAdapter adapter = new PemesananAdminAdapter(KelolaPesananActivity.this, pemesananAdminList);
                        rvPemesananAdmin.setAdapter(adapter);
                        rvPemesananAdmin.setLayoutManager(new LinearLayoutManager(KelolaPesananActivity.this));

                    }else{
                        Toast.makeText(KelolaPesananActivity.this, "Belum Ada Pesanan...", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(KelolaPesananActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataPemesananAdmin> call, Throwable t) {
                Toast.makeText(KelolaPesananActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(KelolaPesananActivity.this, HomeAdminActivity.class));
        finish();
    }
}