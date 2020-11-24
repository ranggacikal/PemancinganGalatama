package com.example.pemancingangalatama;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pemancingangalatama.api.ConfigRetrofit;
import com.example.pemancingangalatama.model.ResponseRegister;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    @BindView(R.id.edt_nama_register)
    EditText edtNamaRegister;
    @BindView(R.id.edt_email_register)
    EditText edtEmailRegister;
    @BindView(R.id.edt_username_register)
    EditText edtUsernameRegister;
    @BindView(R.id.edt_password_register)
    EditText edtPasswordRegister;
    @BindView(R.id.edt_repassword_register)
    EditText edtRepasswordRegister;
    @BindView(R.id.edt_notelp_register)
    EditText edtNotelpRegister;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.img_register)
    ImageView imgRegister;
    @BindView(R.id.btn_pilih_gambar_register)
    Button btnPilihGambarRegister;

    private static final int IMG_REQUEST = 777;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        btnPilihGambarRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PilihGambar();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {

        String nama = edtNamaRegister.getText().toString();
        String email = edtEmailRegister.getText().toString();
        String username = edtUsernameRegister.getText().toString();
        String password = edtPasswordRegister.getText().toString();
        String repassword = edtRepasswordRegister.getText().toString();
        String no_telpon = edtNotelpRegister.getText().toString();
        String image_user = imageToString();
        String level = "User";

        if (nama.isEmpty()){
            edtNamaRegister.setError("Nama Tidak Boleh Kosong");
            edtNamaRegister.requestFocus();
            return;
        }

        if (email.isEmpty()){
            edtEmailRegister.setError("Email Tidak Boleh Kosong");
            edtEmailRegister.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmailRegister.setError("Harap Masukan Email Yang Valid");
            edtEmailRegister.requestFocus();
            return;
        }

        if (username.isEmpty()){
            edtUsernameRegister.setError("Username Tidak Boleh Kosong");
            edtUsernameRegister.requestFocus();
            return;
        }

        if (password.isEmpty()){
            edtPasswordRegister.setError("Password Tidak Boleh Kosong");
            edtPasswordRegister.requestFocus();
            return;
        }

        if (!repassword.equals(password)){
            edtRepasswordRegister.setError("Password Tidak Sama");
            edtRepasswordRegister.requestFocus();
            return;
        }

        if (no_telpon.isEmpty()){
            edtNotelpRegister.setError("No Telfon Tidak Boleh Kosong");
            edtNotelpRegister.requestFocus();
            return;
        }

        if (image_user.isEmpty()){
            Toast.makeText(this, "Gambar Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog dialogRegist = ProgressDialog.show(Register.this, "", "Loading..", false);

        ConfigRetrofit.service.Register(nama, email, username, password, no_telpon, level, image_user).enqueue(new Callback<ResponseRegister>() {
            @Override
            public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                if (response.isSuccessful()){

                    dialogRegist.dismiss();
                    int status = response.body().getStatus();
                    String pesan = response.body().getPesan();

                    if (status == 1){
                        Toast.makeText(Register.this, pesan, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Register.this, Login.class));
                        finish();
                    }else{
                        Toast.makeText(Register.this, pesan, Toast.LENGTH_SHORT).show();
                    }
                }else{
                    dialogRegist.dismiss();
                    Toast.makeText(Register.this, "Terjadi Kesalahan...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseRegister> call, Throwable t) {
                Toast.makeText(Register.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                dialogRegist.dismiss();
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

        if (requestCode==IMG_REQUEST && resultCode == RESULT_OK && data != null){
            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imgRegister.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String imageToString(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }
}