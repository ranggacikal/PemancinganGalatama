package com.example.pemancingangalatama.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pemancingangalatama.PembayaranActivity;
import com.example.pemancingangalatama.R;
import com.example.pemancingangalatama.TransaksiActivity;
import com.example.pemancingangalatama.model.DataPembayaranUserItem;
import com.example.pemancingangalatama.model.DataPemesananUserItem;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PemesananUserAdapter extends RecyclerView.Adapter<PemesananUserAdapter.PemesananUserViewHolder> {

    Context context;
    List<DataPemesananUserItem> pemesananUserItems;

    public PemesananUserAdapter(Context context, List<DataPemesananUserItem> pemesananUserItems) {
        this.context = context;
        this.pemesananUserItems = pemesananUserItems;
    }

    @NonNull
    @Override
    public PemesananUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pemesanan_user, parent, false);
        return new PemesananUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PemesananUserViewHolder holder, int position) {

        holder.txtId.setText(pemesananUserItems.get(position).getIdPemesanan());
        holder.txtTanggal.setText(pemesananUserItems.get(position).getTanggal());
        holder.txtJam.setText(pemesananUserItems.get(position).getJam());
        holder.txtJumlahPesanan.setText(pemesananUserItems.get(position).getJumlahPesanan());
        int total = Integer.parseInt(pemesananUserItems.get(position).getTotalHarga());
        Locale localID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localID);
        holder.txtTotalHarga.setText(formatRupiah.format(total));

        holder.btnSelesaikan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TransaksiActivity.class);
                intent.putExtra(TransaksiActivity.EXTRA_ID_PEMESANAN, pemesananUserItems.get(position).getIdPemesanan());
                intent.putExtra(TransaksiActivity.EXTRA_TANGGAL_PEMESANAN, pemesananUserItems.get(position).getTanggal());
                intent.putExtra(TransaksiActivity.EXTRA_JAM_PEMESANAN, pemesananUserItems.get(position).getJam());
                intent.putExtra(TransaksiActivity.EXTRA_JUMLAH_PEMESANAN, pemesananUserItems.get(position).getJumlahPesanan());
                intent.putExtra(TransaksiActivity.EXTRA_TOTAL_HARGA_PEMESANAN, pemesananUserItems.get(position).getTotalHarga());
                context.startActivity(intent);
                ((PembayaranActivity)context).finish();
            }
        });

        String status = pemesananUserItems.get(position).getStatusPesanan();

        if (status.equals("Belum Bayar") || status.equals("Pembayaran Ditolak")){
            holder.btnSelesaikan.setVisibility(View.VISIBLE);
        }else{
            holder.btnSelesaikan.setVisibility(View.GONE);
            holder.btnCekStatus.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return pemesananUserItems.size();
    }

    public class PemesananUserViewHolder extends RecyclerView.ViewHolder {

        TextView txtId, txtTanggal, txtJam, txtJumlahPesanan, txtTotalHarga, txtSudahBayar;
        Button btnSelesaikan, btnCekStatus;

        public PemesananUserViewHolder(@NonNull View itemView) {
            super(itemView);

            txtId = itemView.findViewById(R.id.text_id_pemesanan_user);
            txtTanggal = itemView.findViewById(R.id.text_tanggal_pemesanan_user);
            txtJam = itemView.findViewById(R.id.text_jam_pemesanan_user);
            txtJumlahPesanan = itemView.findViewById(R.id.text_jumlah_pesanan_user);
            txtTotalHarga = itemView.findViewById(R.id.text_total_harga_pemesanan_user);
            btnSelesaikan = itemView.findViewById(R.id.btn_selesaikan_pembayaran);
            txtSudahBayar = itemView.findViewById(R.id.text_sudah_bayar);
            btnCekStatus = itemView.findViewById(R.id.btn_status_pesanan);
        }
    }
}
