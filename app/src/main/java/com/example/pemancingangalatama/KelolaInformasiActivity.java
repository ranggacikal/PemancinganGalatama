package com.example.pemancingangalatama;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.pemancingangalatama.adapter.InformasiAdapter;
import com.example.pemancingangalatama.api.ConfigRetrofit;
import com.example.pemancingangalatama.model.DataInformasiItem;
import com.example.pemancingangalatama.model.JumlahBangkuTersedia;
import com.example.pemancingangalatama.model.ResponseDataInformasi;
import com.example.pemancingangalatama.model.ResponseJumlahBangkuTersedia;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaInformasiActivity extends AppCompatActivity {

    @BindView(R.id.rv_informasi_admin)
    RecyclerView rvInformasiAdmin;
    @BindView(R.id.refreshI_informasi)
    SwipeRefreshLayout refreshIInformasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_informasi);
        ButterKnife.bind(this);

        loadDataBangku();
    }

    private void loadDataBangku() {

        ConfigRetrofit.service.dataInformasi().enqueue(new Callback<ResponseDataInformasi>() {
            @Override
            public void onResponse(Call<ResponseDataInformasi> call, Response<ResponseDataInformasi> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();

                    if (status==1){

                        List<DataInformasiItem> informasiItems = response.body().getDataInformasi();
                        InformasiAdapter adapter = new InformasiAdapter(KelolaInformasiActivity.this, informasiItems);
                        rvInformasiAdmin.setAdapter(adapter);
                        rvInformasiAdmin.setLayoutManager(new LinearLayoutManager(KelolaInformasiActivity.this));

                    }else{
                        Toast.makeText(KelolaInformasiActivity.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(KelolaInformasiActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataInformasi> call, Throwable t) {
                Toast.makeText(KelolaInformasiActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(KelolaInformasiActivity.this, HomeAdminActivity.class));
        finish();
    }
}