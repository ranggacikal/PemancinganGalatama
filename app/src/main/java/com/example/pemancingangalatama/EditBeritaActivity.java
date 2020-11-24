package com.example.pemancingangalatama;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pemancingangalatama.api.ConfigRetrofit;
import com.example.pemancingangalatama.model.ResponseEditBerita;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditBeritaActivity extends AppCompatActivity {

    public static final String EXTRA_ID_BERITA = "extraIdBerita";
    public static final String EXTRA_JUDUL_BERITA = "extraJudulBerita";
    public static final String EXTRA_TANGGAL_BERITA = "extraTanggalBerita";
    public static final String EXTRA_ISI_BERITA = "extraIsiBerita";

    @BindView(R.id.edt_edit_judul_berita)
    EditText edtEditJudulBerita;
    @BindView(R.id.edt_edit_tanggal_berita)
    EditText edtEditTanggalBerita;
    @BindView(R.id.btn_pilih_tanggal_edit_berita)
    Button btnPilihTanggalEditBerita;
    @BindView(R.id.edt_edit_isi_berita)
    EditText edtEditIsiBerita;
    @BindView(R.id.btn_edit_berita)
    Button btnEditBerita;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_berita);
        ButterKnife.bind(this);

        edtEditJudulBerita.setText(getIntent().getStringExtra(EXTRA_JUDUL_BERITA));
        edtEditTanggalBerita.setText(getIntent().getStringExtra(EXTRA_TANGGAL_BERITA));
        edtEditIsiBerita.setText(getIntent().getStringExtra(EXTRA_ISI_BERITA));

        dateFormatter = new SimpleDateFormat("dd-MMMM-yyyy", Locale.US);
        btnPilihTanggalEditBerita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        btnEditBerita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editBerita();
            }
        });
    }

    private void editBerita() {

        String id = getIntent().getStringExtra(EXTRA_ID_BERITA);
        String judul = edtEditJudulBerita.getText().toString();
        String tanggal = edtEditTanggalBerita.getText().toString();
        String isi = edtEditIsiBerita.getText().toString();

        final ProgressDialog progDialog = ProgressDialog.show(EditBeritaActivity.this, "", "Loading..", false);
        ConfigRetrofit.service.editBerita(id, judul, tanggal, isi).enqueue(new Callback<ResponseEditBerita>() {
            @Override
            public void onResponse(Call<ResponseEditBerita> call, Response<ResponseEditBerita> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();
                    String pesan = response.body().getPesan();

                    if (status==1){

                        Toast.makeText(EditBeritaActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditBeritaActivity.this, KelolaBeritaActivity.class));
                        finish();

                    }else{
                        Toast.makeText(EditBeritaActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(EditBeritaActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseEditBerita> call, Throwable t) {
                Toast.makeText(EditBeritaActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

                edtEditTanggalBerita.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EditBeritaActivity.this, KelolaBeritaActivity.class));
        finish();
    }
}