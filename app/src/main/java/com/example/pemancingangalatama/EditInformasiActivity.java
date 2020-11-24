package com.example.pemancingangalatama;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pemancingangalatama.api.ConfigRetrofit;
import com.example.pemancingangalatama.model.ResponseEditInformasi;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditInformasiActivity extends AppCompatActivity {

    @BindView(R.id.edt_edit_informasi)
    EditText edtEditInformasi;
    @BindView(R.id.btn_update_informasi)
    Button btnUpdateInformasi;

    public static final String EXTRA_ID_INFORMASI = "extraIdInformasi";
    public static final String EXTRA_JUMLAH_BANGKU_INFORMASI = "extraJumlahBangkuInformasi";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_informasi);
        ButterKnife.bind(this);

        edtEditInformasi.setText(getIntent().getStringExtra(EXTRA_JUMLAH_BANGKU_INFORMASI));

        btnUpdateInformasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editInformasi();
            }
        });
    }

    private void editInformasi() {

        String id = getIntent().getStringExtra(EXTRA_ID_INFORMASI);
        String jumlah_bangku = edtEditInformasi.getText().toString();

        ConfigRetrofit.service.editInformasi(id, jumlah_bangku).enqueue(new Callback<ResponseEditInformasi>() {
            @Override
            public void onResponse(Call<ResponseEditInformasi> call, Response<ResponseEditInformasi> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();

                    if (status==1){
                        Toast.makeText(EditInformasiActivity.this, "Berhasil Edit Data", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditInformasiActivity.this, KelolaInformasiActivity.class));
                        finish();
                    }else{
                        Toast.makeText(EditInformasiActivity.this, "Gagal Edit Data", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(EditInformasiActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseEditInformasi> call, Throwable t) {
                Toast.makeText(EditInformasiActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        
    }
}