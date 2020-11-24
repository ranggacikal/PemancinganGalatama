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

public class LoginAdminActivity extends AppCompatActivity {

    @BindView(R.id.edt_username_login_admin)
    EditText edtUsernameLoginAdmin;
    @BindView(R.id.edt_password_login_admin)
    EditText edtPasswordLoginAdmin;
    @BindView(R.id.btn_login_admin)
    Button btnLoginAdmin;

    private SharedPreferencedConfig preferencedConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        ButterKnife.bind(this);
        preferencedConfig = new SharedPreferencedConfig(this);

        btnLoginAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAdmin();
            }
        });
    }

    private void loginAdmin() {

        String username = edtUsernameLoginAdmin.getText().toString();
        String password = edtPasswordLoginAdmin.getText().toString();

        if (username.isEmpty()){
            edtUsernameLoginAdmin.setError("Username Tidak Boleh Kosong");
            edtUsernameLoginAdmin.requestFocus();
            return;
        }

        if (password.isEmpty()){
            edtPasswordLoginAdmin.setError("Password Tidak Boleh Kosong");
            edtPasswordLoginAdmin.requestFocus();
            return;
        }

        final ProgressDialog dialogLogin = ProgressDialog.show(LoginAdminActivity.this, "", "Loading..", false);
        ConfigRetrofit.service.LoginUser(username, password).enqueue(new Callback<ResponseLoginUser>() {
            @Override
            public void onResponse(Call<ResponseLoginUser> call, Response<ResponseLoginUser> response) {
                if (response.isSuccessful()){
                    dialogLogin.dismiss();

                    int status = response.body().getStatus();

                    if (status == 1){
                        String levelUser = response.body().getDataLogin().getLevel();
                        dialogLogin.dismiss();

                        if (levelUser.equals("Admin")){
                            Toast.makeText(LoginAdminActivity.this, "Anda Adalah Admin! Login Berhasil.", Toast.LENGTH_SHORT).show();
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
                            startActivity(new Intent(LoginAdminActivity.this, HomeAdminActivity.class));
                            finish();
                        }else if (levelUser.equals("User")){
                            Toast.makeText(LoginAdminActivity.this, "Anda Bukan Admin. Ini Adalah Halaman Login Admin.", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        dialogLogin.dismiss();
                        Toast.makeText(LoginAdminActivity.this, "Username / Password Salah", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    dialogLogin.dismiss();
                    Toast.makeText(LoginAdminActivity.this, "Terjadi Kesalahan...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseLoginUser> call, Throwable t) {
                dialogLogin.dismiss();
                Toast.makeText(LoginAdminActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LoginAdminActivity.this, MainActivity.class));
        finish();
    }
}