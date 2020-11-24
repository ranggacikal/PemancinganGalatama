package com.example.pemancingangalatama.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pemancingangalatama.EditUserActivity;
import com.example.pemancingangalatama.KelolaUserActivity;
import com.example.pemancingangalatama.R;
import com.example.pemancingangalatama.api.ConfigRetrofit;
import com.example.pemancingangalatama.model.DataUserItem;
import com.example.pemancingangalatama.model.ResponseHapusUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    Context context;
    List<DataUserItem> userItems;


    public UserAdapter(Context context, List<DataUserItem> userItems) {
        this.context = context;
        this.userItems = userItems;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        final String image = userItems.get(position).getImageUser();
        String nama = userItems.get(position).getNamaLengkap();
        String email = userItems.get(position).getEmail();
        String id = userItems.get(position).getIdUser();
        String username = userItems.get(position).getUsername();
        String no_telpon = userItems.get(position).getNoTelpon();
        String level = userItems.get(position).getLevel();

        Glide.with(context)
                .load(image)
                .error(R.mipmap.ic_launcher)
                .into(holder.imgUser);

        holder.txtNama.setText(nama);
        holder.txtEmail.setText(email);
        holder.txtUsername.setText(username);
        holder.txtNoTelpon.setText(no_telpon);
        holder.txtLevel.setText(level);

        holder.btnEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditUserActivity.class);
                intent.putExtra(EditUserActivity.EXTRA_ID_USER, id);
                intent.putExtra(EditUserActivity.EXTRA_NAMA_LENGKAP_USER, nama);
                intent.putExtra(EditUserActivity.EXTRA_EMAIL_USER, email);
                intent.putExtra(EditUserActivity.EXTRA_USERNAME_USER, username);
                intent.putExtra(EditUserActivity.EXTRA_NO_TELPON_USER, no_telpon);
                intent.putExtra(EditUserActivity.EXTRA_LEVEL_USER, level);
                context.startActivity(intent);
                ((KelolaUserActivity)context).finish();
            }
        });

        holder.btnHapusUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_hapus_user);

                TextView txtIya = dialog.findViewById(R.id.text_iya_hapus_user);
                TextView txtTidak = dialog.findViewById(R.id.text_tidak_hapus_user);

                dialog.show();

                txtIya.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConfigRetrofit.service.hapusUser(id).enqueue(new Callback<ResponseHapusUser>() {
                            @Override
                            public void onResponse(Call<ResponseHapusUser> call, Response<ResponseHapusUser> response) {
                                if (response.isSuccessful()){

                                    int status = response.body().getStatus();

                                    if (status==1){

                                        Toast.makeText(context, "Hapus Data Berhasil", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }else{
                                        Toast.makeText(context, "Gagal Hapus Data", Toast.LENGTH_SHORT).show();
                                    }

                                }else{
                                    Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseHapusUser> call, Throwable t) {
                                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                txtTidak.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

    }



    @Override
    public int getItemCount() {
        return userItems.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        TextView txtNama, txtEmail, txtUsername, txtNoTelpon, txtLevel;
        CircleImageView imgUser;
        Button btnHapusUser, btnEditUser;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNama = itemView.findViewById(R.id.text_nama_lengkap_user);
            txtEmail = itemView.findViewById(R.id.text_email_user);
            txtUsername = itemView.findViewById(R.id.text_username_user);
            txtNoTelpon = itemView.findViewById(R.id.text_no_telpon_user);
            txtLevel = itemView.findViewById(R.id.text_level_user);
            imgUser = itemView.findViewById(R.id.img_user_list);
            btnEditUser = itemView.findViewById(R.id.btn_edit_user);
            btnHapusUser = itemView.findViewById(R.id.btn_hapus_user);
        }
    }
}
