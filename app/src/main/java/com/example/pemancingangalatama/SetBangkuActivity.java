package com.example.pemancingangalatama;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pemancingangalatama.api.ConfigRetrofit;
import com.example.pemancingangalatama.model.ResponseInputNoBangku;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetBangkuActivity extends AppCompatActivity {

    public static final String EXTRA_ID_PESANAN = "extraIdPesanan";
    public static final String EXTRA_JUMLAH_PESANAN = "extraJumlahPesanan";
    @BindView(R.id.text_id_set_kursi)
    TextView textIdSetKursi;
    @BindView(R.id.text_jumlah_set_kursi)
    TextView textJumlahSetKursi;
    @BindView(R.id.edt_set_nomer_kursi)
    EditText edtSetNomerKursi;
    @BindView(R.id.btn_acak_nomor)
    Button btnAcakNomor;
    @BindView(R.id.text_nomer_acak)
    TextView textNomerAcak;
    @BindView(R.id.btn_input_nomor_kursi)
    Button btnInputNomorKursi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_bangku);
        ButterKnife.bind(this);

        final Random myRandom = new Random();

        textIdSetKursi.setText(getIntent().getStringExtra(EXTRA_ID_PESANAN));
        textJumlahSetKursi.setText(getIntent().getStringExtra(EXTRA_JUMLAH_PESANAN));

        btnAcakNomor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textNomerAcak.setText(String.valueOf(myRandom.nextInt(40)));
            }
        });

        btnInputNomorKursi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputNoKursi();
            }
        });
    }

    private void inputNoKursi() {

        String id = getIntent().getStringExtra(EXTRA_ID_PESANAN);
        String no_bangku = edtSetNomerKursi.getText().toString();

        ConfigRetrofit.service.inputNoBangku(id, no_bangku).enqueue(new Callback<ResponseInputNoBangku>() {
            @Override
            public void onResponse(Call<ResponseInputNoBangku> call, Response<ResponseInputNoBangku> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();
                    String pesan = response.body().getPesan();

                    if (status == 1){

                        Toast.makeText(SetBangkuActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SetBangkuActivity.this, KelolaPesananActivity.class));
                        finish();
                    }else{
                        Toast.makeText(SetBangkuActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SetBangkuActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseInputNoBangku> call, Throwable t) {
                Toast.makeText(SetBangkuActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SetBangkuActivity.this, KelolaPesananActivity.class));
        finish();
    }
}