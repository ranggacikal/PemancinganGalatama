package com.example.pemancingangalatama.adapter;

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
import com.example.pemancingangalatama.KelolaPesananActivity;
import com.example.pemancingangalatama.R;
import com.example.pemancingangalatama.ValidasiPembayaranActivity;
import com.example.pemancingangalatama.api.ConfigRetrofit;
import com.example.pemancingangalatama.model.DataPembayaranUserItem;
import com.example.pemancingangalatama.model.ResponseTolakPembayaran;
import com.example.pemancingangalatama.model.ResponseUbahStatusPesanan;
import com.example.pemancingangalatama.model.ResponseValidasiPembayaran;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ValidasiPembayaranAdapter extends RecyclerView.Adapter<ValidasiPembayaranAdapter.ValidasiPembayaranViewHolder> {

    Context context;
    List<DataPembayaranUserItem> pembayaranItems;

    String id_pembayaran;
    String id;

    public ValidasiPembayaranAdapter(Context context, List<DataPembayaranUserItem> pembayaranItems) {
        this.context = context;
        this.pembayaranItems = pembayaranItems;
    }

    @NonNull
    @Override
    public ValidasiPembayaranViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_validasi_pembayaran, parent, false);
        return new ValidasiPembayaranViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ValidasiPembayaranViewHolder holder, int position) {

        id_pembayaran = pembayaranItems.get(position).getIdPembayaran();
        id = pembayaranItems.get(position).getPemesananId();
        String tanggal = pembayaranItems.get(position).getTanggal();
        String total = pembayaranItems.get(position).getTotalHarga();
        final String img = pembayaranItems.get(position).getBuktiPembayaran();

        holder.txtId.setText(id);
        holder.txtTanggal.setText(tanggal);

        int totalHarga = Integer.parseInt(total);
        Locale localID = new Locale("in", "ID");
        NumberFormat formatRp = NumberFormat.getCurrencyInstance(localID);
        holder.txtTotal.setText(formatRp.format(totalHarga));

        Glide.with(context)
                .load(img)
                .error(R.mipmap.ic_launcher)
                .into(holder.imgValidasi);

        holder.btnTerima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                terimaValidasiPembayaran();
            }
        });

        holder.btnTolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tolakValidasiPembayaran();
            }
        });
    }

    private void tolakValidasiPembayaran() {


        ConfigRetrofit.service.tolakPembayaran(id_pembayaran).enqueue(new Callback<ResponseTolakPembayaran>() {
            @Override
            public void onResponse(Call<ResponseTolakPembayaran> call, Response<ResponseTolakPembayaran> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();

                    if (status==1){
                        ubahStatusGagal();
                        context.startActivity(new Intent(context, KelolaPesananActivity.class));
                        ((ValidasiPembayaranActivity)context).finish();
                    }else{
                        Toast.makeText(context, "Gagal Validasi", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTolakPembayaran> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void ubahStatusGagal() {

        String status = "Pembayaran Ditolak";

        ConfigRetrofit.service.ubahStatusPesanan(id, status).enqueue(new Callback<ResponseUbahStatusPesanan>() {
            @Override
            public void onResponse(Call<ResponseUbahStatusPesanan> call, Response<ResponseUbahStatusPesanan> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();

                    if (status==1){
                        Toast.makeText(context, "Status Pesanan Diubah", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "Gagal ubah status", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUbahStatusPesanan> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void terimaValidasiPembayaran() {

        String validasi = "Pembayaran Valid";

        ConfigRetrofit.service.validasiPembayaran(id_pembayaran, validasi).enqueue(new Callback<ResponseValidasiPembayaran>() {
            @Override
            public void onResponse(Call<ResponseValidasiPembayaran> call, Response<ResponseValidasiPembayaran> response) {
                if(response.isSuccessful()){

                    int status = response.body().getStatus();
                    String pesan = response.body().getPesan();
                    if (status==1){
                        Toast.makeText(context, pesan, Toast.LENGTH_SHORT).show();
                        ubahStatusBerhasil();
                        context.startActivity(new Intent(context, KelolaPesananActivity.class));
                        ((ValidasiPembayaranActivity)context).finish();
                    }else{
                        Toast.makeText(context, "Validasi gagal", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseValidasiPembayaran> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ubahStatusBerhasil() {

        String status = "Pembayaran Sudah Di Validasi";

        ConfigRetrofit.service.ubahStatusPesanan(id, status).enqueue(new Callback<ResponseUbahStatusPesanan>() {
            @Override
            public void onResponse(Call<ResponseUbahStatusPesanan> call, Response<ResponseUbahStatusPesanan> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();

                    if (status==1){
                        Toast.makeText(context, "Status Pesanan Diubah", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "Gagal Ubah Status", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUbahStatusPesanan> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return pembayaranItems.size();
    }

    public class ValidasiPembayaranViewHolder extends RecyclerView.ViewHolder {

        TextView txtId, txtTanggal, txtTotal;
        ImageView imgValidasi;
        Button btnTerima, btnTolak;

        public ValidasiPembayaranViewHolder(@NonNull View itemView) {
            super(itemView);

            txtId = itemView.findViewById(R.id.text_id_validasi);
            txtTanggal = itemView.findViewById(R.id.text_tanggal_validasi);
            txtTotal = itemView.findViewById(R.id.text_total_validasi);
            imgValidasi = itemView.findViewById(R.id.img_validasi_pembayaran);
            btnTerima = itemView.findViewById(R.id.btn_terima_validasi);
            btnTolak = itemView.findViewById(R.id.btn_tolak_validasi);
        }
    }
}
