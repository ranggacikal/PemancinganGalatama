package com.example.pemancingangalatama;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pemancingangalatama.api.ConfigRetrofit;
import com.example.pemancingangalatama.model.ResponseTambahBerita;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahBeritaActivity extends AppCompatActivity {

    @BindView(R.id.edt_tambah_judul_berita)
    EditText edtTambahJudulBerita;
    @BindView(R.id.edt_tambah_tanggal_berita)
    EditText edtTambahTanggalBerita;
    @BindView(R.id.btn_pilih_tanggal_berita)
    Button btnPilihTanggalBerita;
    @BindView(R.id.edt_tambah_isi_berita)
    EditText edtTambahIsiBerita;
    @BindView(R.id.img_tambah_berita)
    ImageView imgTambahBerita;
    @BindView(R.id.btn_pilih_image_berita)
    Button btnPilihImageBerita;
    @BindView(R.id.btn_tambah_berita)
    Button btnTambahBerita;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    private static final int IMG_REQUEST = 777;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_berita);
        ButterKnife.bind(this);

        dateFormatter = new SimpleDateFormat("dd-MMMM-yyyy", Locale.US);
        btnPilihTanggalBerita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        btnPilihImageBerita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PilihGambar();
            }
        });

        btnTambahBerita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahBerita();
            }
        });

    }

    private void tambahBerita() {

        String judul_berita = edtTambahJudulBerita.getText().toString();
        String tanggal_berita = edtTambahTanggalBerita.getText().toString();
        String isi_berita = edtTambahIsiBerita.getText().toString();
        String image_berita = imageToString();

        final ProgressDialog progDialog = ProgressDialog.show(TambahBeritaActivity.this, "", "Loading..", false);
        ConfigRetrofit.service.tambahBerita(judul_berita, tanggal_berita, isi_berita, image_berita).enqueue(new Callback<ResponseTambahBerita>() {
            @Override
            public void onResponse(Call<ResponseTambahBerita> call, Response<ResponseTambahBerita> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();
                    String pesan = response.body().getPesan();
                    progDialog.dismiss();

                    if (status == 1){
                        progDialog.dismiss();
                        Toast.makeText(TambahBeritaActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        edtTambahJudulBerita.setText("");
                        edtTambahTanggalBerita.setText("");
                        edtTambahIsiBerita.setText("");
                        imgTambahBerita.setImageResource(R.mipmap.ic_launcher);
                    }else{
                        progDialog.dismiss();
                        Toast.makeText(TambahBeritaActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progDialog.dismiss();
                    Toast.makeText(TambahBeritaActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTambahBerita> call, Throwable t) {
                progDialog.dismiss();
                Toast.makeText(TambahBeritaActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void PilihGambar() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imgTambahBerita.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String imageToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        } else if (bitmap == null) {
            Toast.makeText(this, "Gambar Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        }
        byte[] imgByte = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }

    private void showDateDialog() {

        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                edtTambahTanggalBerita.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(TambahBeritaActivity.this, KelolaBeritaActivity.class));
        finish();
    }
}