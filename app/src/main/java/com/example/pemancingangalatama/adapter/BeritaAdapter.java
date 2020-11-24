package com.example.pemancingangalatama.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pemancingangalatama.DetailBeritaActivity;
import com.example.pemancingangalatama.EditBeritaActivity;
import com.example.pemancingangalatama.HomeUserActivity;
import com.example.pemancingangalatama.KelolaBeritaActivity;
import com.example.pemancingangalatama.R;
import com.example.pemancingangalatama.SharedPreference.SharedPreferencedConfig;
import com.example.pemancingangalatama.api.ConfigRetrofit;
import com.example.pemancingangalatama.model.DataBeritaItem;
import com.example.pemancingangalatama.model.ResponseHapusBerita;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeritaAdapter extends RecyclerView.Adapter<BeritaAdapter.BeritaViewHolder> {

    Context context;
    List<DataBeritaItem> beritaItems;

    private SharedPreferencedConfig preferencedConfig;


    public BeritaAdapter(Context context, List<DataBeritaItem> beritaItems) {
        this.context = context;
        this.beritaItems = beritaItems;
    }

    @NonNull
    @Override
    public BeritaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_berita, parent, false);
        return new BeritaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BeritaViewHolder holder, int position) {

        preferencedConfig = new SharedPreferencedConfig(context);
        final String imgLink = beritaItems.get(position).getImageBerita();

        Glide.with(context)
                .load(imgLink)
                .error(R.mipmap.ic_launcher)
                .into(holder.imgBerita);

        holder.txtJudul.setText(beritaItems.get(position).getJudulBerita());
        holder.txtTanggal.setText(beritaItems.get(position).getTanggalBerita());
        holder.txtIsi.setText(beritaItems.get(position).getIsiBerita());

        String id_berita = beritaItems.get(position).getIdBerita();

        String level = preferencedConfig.getPreferenceLevelUser();

        if (level.equals("User")) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailBeritaActivity.class);
                    intent.putExtra(DetailBeritaActivity.EXTRA_IMAGE_BERITA, beritaItems.get(position).getImageBerita());
                    intent.putExtra(DetailBeritaActivity.EXTRA_JUDUL_BERITA, beritaItems.get(position).getJudulBerita());
                    intent.putExtra(DetailBeritaActivity.EXTRA_TANGGAL_BERITA, beritaItems.get(position).getTanggalBerita());
                    intent.putExtra(DetailBeritaActivity.EXTRA_ISI_BERITA, beritaItems.get(position).getIsiBerita());
                    context.startActivity(intent);
                    ((HomeUserActivity) context).finish();
                }
            });
        } else if (level.equals("Admin")) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailBeritaActivity.class);
                    intent.putExtra(DetailBeritaActivity.EXTRA_IMAGE_BERITA, beritaItems.get(position).getImageBerita());
                    intent.putExtra(DetailBeritaActivity.EXTRA_JUDUL_BERITA, beritaItems.get(position).getJudulBerita());
                    intent.putExtra(DetailBeritaActivity.EXTRA_TANGGAL_BERITA, beritaItems.get(position).getTanggalBerita());
                    intent.putExtra(DetailBeritaActivity.EXTRA_ISI_BERITA, beritaItems.get(position).getIsiBerita());
                    context.startActivity(intent);
                    ((KelolaBeritaActivity) context).finish();
                }
            });

            holder.btnEditBerita.setVisibility(View.VISIBLE);
            holder.btnHapusBerita.setVisibility(View.VISIBLE);

            holder.btnEditBerita.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentEdit = new Intent(context, EditBeritaActivity.class);
                    intentEdit.putExtra(EditBeritaActivity.EXTRA_ID_BERITA, beritaItems.get(position).getIdBerita());
                    intentEdit.putExtra(EditBeritaActivity.EXTRA_JUDUL_BERITA, beritaItems.get(position).getJudulBerita());
                    intentEdit.putExtra(EditBeritaActivity.EXTRA_TANGGAL_BERITA, beritaItems.get(position).getTanggalBerita());
                    intentEdit.putExtra(EditBeritaActivity.EXTRA_ISI_BERITA, beritaItems.get(position).getIsiBerita());
                    context.startActivity(intentEdit);
                    ((KelolaBeritaActivity) context).finish();
                }
            });

            holder.btnHapusBerita.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Dialog dialogHapus = new Dialog(context);
                    dialogHapus.setContentView(R.layout.dialog_hapus_berita);

                    final TextView iya = dialogHapus.findViewById(R.id.text_iya_hapus_berita);
                    final TextView tidak = dialogHapus.findViewById(R.id.text_tidak_hapus_berita);

                    dialogHapus.show();

                    iya.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ConfigRetrofit.service.hapusBerita(id_berita).enqueue(new Callback<ResponseHapusBerita>() {
                                @Override
                                public void onResponse(Call<ResponseHapusBerita> call, Response<ResponseHapusBerita> response) {
                                    if (response.isSuccessful()) {

                                        int status = response.body().getStatus();
                                        String pesan = response.body().getPesan();

                                        if (status == 1) {

                                            Toast.makeText(context, pesan, Toast.LENGTH_SHORT).show();
                                            dialogHapus.dismiss();
                                        } else {
                                            Toast.makeText(context, pesan, Toast.LENGTH_SHORT).show();
                                        }

                                    } else {
                                        Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseHapusBerita> call, Throwable t) {
                                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    });

                    tidak.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogHapus.dismiss();
                        }
                    });

                }
            });

        }


    }

    private void hapusBerita() {


    }

    @Override
    public int getItemCount() {
        return beritaItems.size();
    }

    public class BeritaViewHolder extends RecyclerView.ViewHolder {

        ImageView imgBerita;
        TextView txtJudul, txtTanggal, txtIsi;
        Button btnEditBerita, btnHapusBerita;

        public BeritaViewHolder(@NonNull View itemView) {
            super(itemView);

            imgBerita = itemView.findViewById(R.id.img_berita);
            txtJudul = itemView.findViewById(R.id.text_judul_berita);
            txtTanggal = itemView.findViewById(R.id.text_tanggal_berita);
            txtIsi = itemView.findViewById(R.id.text_isi_berita);
            btnEditBerita = itemView.findViewById(R.id.btn_edit_berita);
            btnHapusBerita = itemView.findViewById(R.id.btn_hapus_berita);
        }
    }
}
