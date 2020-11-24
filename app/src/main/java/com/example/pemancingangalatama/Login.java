package com.example.pemancingangalatama;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pemancingangalatama.SharedPreference.SharedPreferencedConfig;
import com.example.pemancingangalatama.api.ConfigRetrofit;
import com.example.pemancingangalatama.model.ResponseLoginUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    @BindView(R.id.edt_username_login)
    EditText edtUsernameLogin;
    @BindView(R.id.edt_password_login)
    EditText edtPasswordLogin;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_register_login)
    Button btnRegisterLogin;
    @BindView(R.id.lupa_password)
    EditText lupaPassword;

    private SharedPreferencedConfig preferencedConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        preferencedConfig = new SharedPreferencedConfig(this);

        btnRegisterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });
    }

    private void LoginUser() {

        String username = edtUsernameLogin.getText().toString();
        String password = edtPasswordLogin.getText().toString();

        if (username.isEmpty()){
            edtUsernameLogin.setError("Username Tidak Boleh Kosong");
            edtUsernameLogin.requestFocus();
            return;
        }

        if (password.isEmpty()){
            edtPasswordLogin.setError("Password Tidak Boleh Kosong");
            edtPasswordLogin.requestFocus();
            return;
        }

        final ProgressDialog dialogLogin = ProgressDialog.show(Login.this, "", "Loading..", false);
        ConfigRetrofit.service.LoginUser(username, password).enqueue(new Callback<ResponseLoginUser>() {
            @Override
            public void onResponse(Call<ResponseLoginUser> call, Response<ResponseLoginUser> response) {
                if (response.isSuccessful()){

                    dialogLogin.dismiss();
                    int status = response.body().getStatus();
                    String pesan = response.body().getPesan();

                    if (status==1){
                        String levelUser = response.body().getDataLogin().getLevel();

                        if (levelUser.equals("User")) {

                            Toast.makeText(Login.this, pesan, Toast.LENGTH_SHORT).show();
                            String id_user = response.body().getDataLogin().getIdUser();
                            String nama_lengkap = response.body().getDataLogin().getNamaLengkap();
                            String email = response.body().getDataLogin().getEmail();
                            String username = response.body().getDataLogin().getUsername();
                            String level = response.body().getDataLogin().getLevel();
                            String image = response.body().getDataLogin().getImageUser();

                            preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_ID_USER, id_user);
                            preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_NAMA_LENGKAP, nama_lengkap);
                            preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_EMAIL, email);
                            preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_USERNAME, username);
                            preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_LEVEL_USER, level);
                            preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_IMAGE, image);
                            preferencedConfig.savePrefBoolean(SharedPreferencedConfig.PREFERENCE_IS_LOGIN, true);
                            startActivity(new Intent(Login.this, HomeUserActivity.class));
                            finish();
                        }else if (levelUser.equals("Admin")){
                            Toast.makeText(Login.this, "Ini Adalah Halaman Login User", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(Login.this, pesan, Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Login.this, "Terjadi Kesalahan...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseLoginUser> call, Throwable t) {
                dialogLogin.dismiss();
                Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Login.this, MainActivity.class));
        finish();
    }
}