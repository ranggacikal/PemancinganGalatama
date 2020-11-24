package com.example.pemancingangalatama;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.pemancingangalatama.adapter.UserAdapter;
import com.example.pemancingangalatama.api.ConfigRetrofit;
import com.example.pemancingangalatama.model.DataUserItem;
import com.example.pemancingangalatama.model.ResponseDataUser;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaUserActivity extends AppCompatActivity {

    @BindView(R.id.rv_kelola_user)
    RecyclerView rvKelolaUser;
    @BindView(R.id.refresh_user)
    SwipeRefreshLayout refreshUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_user);
        ButterKnife.bind(this);

        loadDataUser();

        refreshUser.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDataUser();
                refreshUser.setRefreshing(false);
            }
        });
    }

    private void loadDataUser() {

        ConfigRetrofit.service.dataUser().enqueue(new Callback<ResponseDataUser>() {
            @Override
            public void onResponse(Call<ResponseDataUser> call, Response<ResponseDataUser> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();

                    if (status == 1){

                        List<DataUserItem> userList = response.body().getDataUser();
                        UserAdapter adapter = new UserAdapter(KelolaUserActivity.this, userList);
                        rvKelolaUser.setAdapter(adapter);
                        rvKelolaUser.setLayoutManager(new LinearLayoutManager(KelolaUserActivity.this));

                    }else{
                        Toast.makeText(KelolaUserActivity.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(KelolaUserActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataUser> call, Throwable t) {
                Toast.makeText(KelolaUserActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(KelolaUserActivity.this, HomeAdminActivity.class));
        finish();
    }
}