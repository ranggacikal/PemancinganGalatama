package com.example.pemancingangalatama;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pemancingangalatama.api.ConfigRetrofit;
import com.example.pemancingangalatama.model.ResponseInsertPembayaran;
import com.example.pemancingangalatama.model.ResponseUbahStatusPesanan;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransferActivity extends AppCompatActivity {

    @BindView(R.id.text_nama_pemesan_transfer)
    TextView textNamaPemesanTransfer;
    @BindView(R.id.text_id_pemesan_transfer)
    TextView textIdPemesanTransfer;
    @BindView(R.id.img_bukti_transfer)
    ImageView imgBuktiTransfer;
    @BindView(R.id.btn_pilih_gambar_transfer)
    Button btnPilihGambarTransfer;
    @BindView(R.id.btn_upload_bukti_transfer)
    Button btnUploadBuktiTransfer;

    public static final String EXTRA_ID_TRANSFER = "ExtraIdTransfer";
    public static final String EXTRA_NAMA_TRANSFER = "ExtraNamaTransfer";

    private static final int IMG_REQUEST = 777;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        ButterKnife.bind(this);

        textIdPemesanTransfer.setText(getIntent().getStringExtra(EXTRA_ID_TRANSFER));
        textNamaPemesanTransfer.setText(getIntent().getStringExtra(EXTRA_NAMA_TRANSFER));

        btnPilihGambarTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihGambar();
            }
        });

        btnUploadBuktiTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadBuktiTransfer();
            }
        });
    }

    private void uploadBuktiTransfer() {

        String id_pemesanan = getIntent().getStringExtra(EXTRA_ID_TRANSFER);
        String bukti_pembayaran = imageToString();

        final ProgressDialog progDialog = ProgressDialog.show(TransferActivity.this, "", "Loading..", false);

        ConfigRetrofit.service.insertPembayaran(bukti_pembayaran, id_pemesanan).enqueue(new Callback<ResponseInsertPembayaran>() {
            @Override
            public void onResponse(Call<ResponseInsertPembayaran> call, Response<ResponseInsertPembayaran> response) {
                progDialog.dismiss();
                String pesan = response.body().getPesan();
                boolean sukses = response.body().isSukses();
                if (sukses == true) {
                    progDialog.dismiss();
                    Toast.makeText(TransferActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    ubahStatusPesanan();
                    startActivity(new Intent(TransferActivity.this, PembayaranActivity.class));
                    finish();

                } else {
                    progDialog.dismiss();
                    Toast.makeText(TransferActivity.this, pesan, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseInsertPembayaran> call, Throwable t) {
                progDialog.dismiss();
                Toast.makeText(TransferActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ubahStatusPesanan() {

        String id = getIntent().getStringExtra(EXTRA_ID_TRANSFER);
        String status = "Sudah Transfer";

        ConfigRetrofit.service.ubahStatusPesanan(id, status).enqueue(new Callback<ResponseUbahStatusPesanan>() {
            @Override
            public void onResponse(Call<ResponseUbahStatusPesanan> call, Response<ResponseUbahStatusPesanan> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();

                    if (status == 1){

                        Toast.makeText(TransferActivity.this, "Status Pesanan Diubah", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(TransferActivity.this, "Status Pesanan gagal diubah", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(TransferActivity.this, "Terjadi Kesalahan Ubah Status", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUbahStatusPesanan> call, Throwable t) {
                Toast.makeText(TransferActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void pilihGambar() {

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
                imgBuktiTransfer.setImageBitmap(bitmap);
                imgBuktiTransfer.setVisibility(View.VISIBLE);
                btnUploadBuktiTransfer.setVisibility(View.VISIBLE);
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
            Toast.makeText(this, "Gambar Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
        }
        byte[] imgByte = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(TransferActivity.this, PembayaranActivity.class));
        finish();
    }
}