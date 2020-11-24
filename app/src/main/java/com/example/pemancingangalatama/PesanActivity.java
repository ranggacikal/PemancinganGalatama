package com.example.pemancingangalatama;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pemancingangalatama.SharedPreference.SharedPreferencedConfig;
import com.example.pemancingangalatama.api.ConfigRetrofit;
import com.example.pemancingangalatama.model.ResponseInsertPemesanan;
import com.example.pemancingangalatama.model.ResponseJumlahBangkuTersedia;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesanActivity extends AppCompatActivity {

    @BindView(R.id.text_jumlah_bangku_tersedia_pesan)
    TextView textJumlahBangkuTersediaPesan;
    @BindView(R.id.edt_tanggal_pesan)
    EditText edtTanggalPesan;
    @BindView(R.id.btn_pilih_tanggal)
    Button btnPilihTanggal;
    @BindView(R.id.edt_jam_pesan_awal)
    EditText edtJamPesanAwal;
    @BindView(R.id.edt_jam_pesan_selesai)
    EditText edtJamPesanSelesai;
    @BindView(R.id.edt_jumlah_pesanan)
    EditText edtJumlahPesanan;
    @BindView(R.id.btn_pesan_tempat)
    Button btnPesanTempat;

    private SharedPreferencedConfig preferencedConfig;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan);
        ButterKnife.bind(this);

        preferencedConfig = new SharedPreferencedConfig(this);

        dateFormatter = new SimpleDateFormat("dd-MMMM-yyyy", Locale.US);

        btnPilihTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        btnPesanTempat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pesanTempat();
            }
        });

        getJumlahBangku();
    }

    private void getJumlahBangku() {

        ConfigRetrofit.service.JumlahBangku().enqueue(new Callback<ResponseJumlahBangkuTersedia>() {
            @Override
            public void onResponse(Call<ResponseJumlahBangkuTersedia> call, Response<ResponseJumlahBangkuTersedia> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();
                    String jumlah_bangku = response.body().getJumlahBangkuTersedia().getJumlahBangku();

                    if (status == 1){

                        textJumlahBangkuTersediaPesan.setText(jumlah_bangku);

                    }else{
                        textJumlahBangkuTersediaPesan.setText("Error...");
                    }

                }else{
                    Toast.makeText(PesanActivity.this, "Terjadi Kesalahan...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseJumlahBangkuTersedia> call, Throwable t) {
                Toast.makeText(PesanActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void pesanTempat() {

        String id_user = preferencedConfig.getPreferenceIdUser();
        String tanggal = edtTanggalPesan.getText().toString();
        String jam_pesan_awal = edtJamPesanAwal.getText().toString();
        String jam_pesan_selesai = edtJamPesanSelesai.getText().toString();
        String jumlah_pesanan = edtJumlahPesanan.getText().toString();

        if (tanggal.isEmpty()){
            edtTanggalPesan.setError("Tanggal Tidak Boleh Kosong");
            edtTanggalPesan.requestFocus();
            return;
        }

        if (jam_pesan_awal.isEmpty()){
            edtJamPesanAwal.setError("Jam Pesan Awal Tidak Boleh Kosong");
            edtJamPesanAwal.requestFocus();
            return;
        }

        if (jam_pesan_selesai.isEmpty()){
            edtJamPesanSelesai.setError("Jam Pesan Selesai Tidak boleh Kosong");
            edtJamPesanSelesai.requestFocus();
            return;
        }

        if (jumlah_pesanan.isEmpty()){
            edtJumlahPesanan.setError("Jumlah Pesanan Tidak Boleh Kosong");
            edtJumlahPesanan.requestFocus();
            return;
        }

        int jumlah_pesan = Integer.parseInt(jumlah_pesanan);
        int harga = 40000;
        int total_harga = jumlah_pesan * harga;
        String jam = jam_pesan_awal+" - "+jam_pesan_selesai;
        String id_informasi = "90000";
        String no_bangku = "";
        String status_pesanan = "Belum Bayar";

        final ProgressDialog progDialog = ProgressDialog.show(PesanActivity.this, "", "Loading...", false);

        ConfigRetrofit.service.insertPemesanan(id_user, total_harga, tanggal, jam, jumlah_pesan, no_bangku, id_informasi, status_pesanan)
                .enqueue(new Callback<ResponseInsertPemesanan>() {
            @Override
            public void onResponse(Call<ResponseInsertPemesanan> call, Response<ResponseInsertPemesanan> response) {

                    progDialog.dismiss();

                    int status = response.body().getStatus();
                    String pesan = response.body().getPesan();
                    if (status==1){
                        progDialog.dismiss();
                        Toast.makeText(PesanActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        edtTanggalPesan.setText("");
                        edtJamPesanAwal.setText("");
                        edtJamPesanSelesai.setText("");
                        edtJumlahPesanan.setText("");
                    }else{
                        Toast.makeText(PesanActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        progDialog.dismiss();
                    }
            }

            @Override
            public void onFailure(Call<ResponseInsertPemesanan> call, Throwable t) {
                Toast.makeText(PesanActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progDialog.dismiss();
            }
        });

    }

    private void showDateDialog() {

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                edtTanggalPesan.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PesanActivity.this, HomeUserActivity.class));
        finish();
    }
}