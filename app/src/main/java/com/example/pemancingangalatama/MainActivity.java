package com.example.pemancingangalatama;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pemancingangalatama.SharedPreference.SharedPreferencedConfig;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_admin)
    Button btnAdmin;
    @BindView(R.id.btn_pelanggan)
    Button btnPelanggan;

    private SharedPreferencedConfig preferencedConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        preferencedConfig = new SharedPreferencedConfig(this);

        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginAdminActivity.class));
                finish();
            }
        });

        btnPelanggan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Login.class));
                finish();
            }
        });

        if (preferencedConfig.getPreferenceIsLogin()){
            if (preferencedConfig.getPreferenceLevelUser().equals("User")){
                startActivity(new Intent(MainActivity.this, HomeUserActivity.class));
                finish();
            }else if (preferencedConfig.getPreferenceLevelUser().equals("Admin")){
                startActivity(new Intent(MainActivity.this, HomeAdminActivity.class));
                finish();
            }
        }
    }
}