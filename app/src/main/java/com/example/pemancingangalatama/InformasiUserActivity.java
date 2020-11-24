package com.example.pemancingangalatama;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pemancingangalatama.api.ConfigRetrofit;
import com.example.pemancingangalatama.model.ResponseJumlahBangkuTersedia;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformasiUserActivity extends AppCompatActivity {

    @BindView(R.id.text_jumlah_bangku_informasi)
    TextView textJumlahBangkuInformasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informasi_user);
        ButterKnife.bind(this);

        jumlahBangkuTersedia();
    }

    private void jumlahBangkuTersedia() {
        ConfigRetrofit.service.JumlahBangku().enqueue(new Callback<ResponseJumlahBangkuTersedia>() {
            @Override
            public void onResponse(Call<ResponseJumlahBangkuTersedia> call, Response<ResponseJumlahBangkuTersedia> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();

                    if (status == 1){
                        String jumlahBangku = response.body().getJumlahBangkuTersedia().getJumlahBangku();
                        textJumlahBangkuInformasi.setText(jumlahBangku);
                    }else{
                        Toast.makeText(InformasiUserActivity.this, "Gagal Mengambil Data Bangku", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(InformasiUserActivity.this, "Terjadi Kesalahan...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseJumlahBangkuTersedia> call, Throwable t) {
                Toast.makeText(InformasiUserActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(InformasiUserActivity.this, HomeUserActivity.class));
        finish();
    }
}