package com.example.pemancingangalatama;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pemancingangalatama.api.ConfigRetrofit;
import com.example.pemancingangalatama.model.ResponseEditUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUserActivity extends AppCompatActivity {

    @BindView(R.id.edt_edit_nama_lengkap_user)
    EditText edtEditNamaLengkapUser;
    @BindView(R.id.edt_edit_email_user)
    EditText edtEditEmailUser;
    @BindView(R.id.edt_edit_username_user)
    EditText edtEditUsernameUser;
    @BindView(R.id.edt_edit_no_telpon_user)
    EditText edtEditNoTelponUser;
    @BindView(R.id.spinner_edit_level_user)
    Spinner spinnerEditLevelUser;
    @BindView(R.id.btn_edit_user)
    Button btnEditUser;

    public static final String EXTRA_ID_USER = "extraIdUser";
    public static final String EXTRA_NAMA_LENGKAP_USER = "extraNamaLengkapUser";
    public static final String EXTRA_EMAIL_USER = "extraEmailUser";
    public static final String EXTRA_USERNAME_USER = "extraUsernameUser";
    public static final String EXTRA_NO_TELPON_USER = "extraNoTelponUser";
    public static final String EXTRA_LEVEL_USER = "extraLevelUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        ButterKnife.bind(this);

        String level = getIntent().getStringExtra(EXTRA_LEVEL_USER);

        if (level.equals("Admin")){
            spinnerEditLevelUser.setSelection(1);
        }else if (level.equals("User")){
            spinnerEditLevelUser.setSelection(0);
        }

        edtEditNamaLengkapUser.setText(getIntent().getStringExtra(EXTRA_NAMA_LENGKAP_USER));
        edtEditEmailUser.setText(getIntent().getStringExtra(EXTRA_EMAIL_USER));
        edtEditUsernameUser.setText(getIntent().getStringExtra(EXTRA_USERNAME_USER));
        edtEditNoTelponUser.setText(getIntent().getStringExtra(EXTRA_NO_TELPON_USER));

        btnEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUser();
            }
        });
    }

    private void editUser() {

        String id = getIntent().getStringExtra(EXTRA_ID_USER);
        String nama = edtEditNamaLengkapUser.getText().toString();
        String email = edtEditEmailUser.getText().toString();
        String username = edtEditUsernameUser.getText().toString();
        String no_telpon = edtEditNoTelponUser.getText().toString();
        String level = spinnerEditLevelUser.getSelectedItem().toString();

        final ProgressDialog progDialog = ProgressDialog.show(EditUserActivity.this, "", "Loading..", false);
        ConfigRetrofit.service.editUser(id, nama, email, username, no_telpon, level).enqueue(new Callback<ResponseEditUser>() {
            @Override
            public void onResponse(Call<ResponseEditUser> call, Response<ResponseEditUser> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();

                    if (status == 1){
                        Toast.makeText(EditUserActivity.this, "Berhasil Edit Data", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditUserActivity.this, KelolaUserActivity.class));
                        finish();
                    }else{
                        Toast.makeText(EditUserActivity.this, "Gagal Edit Data", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(EditUserActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseEditUser> call, Throwable t) {
                Toast.makeText(EditUserActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EditUserActivity.this, KelolaUserActivity.class));
        finish();
    }
}