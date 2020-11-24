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

import com.example.pemancingangalatama.KelolaPesananActivity;
import com.example.pemancingangalatama.R;
import com.example.pemancingangalatama.SetBangkuActivity;
import com.example.pemancingangalatama.ValidasiPembayaranActivity;
import com.example.pemancingangalatama.model.DataPemesananAdminItem;


import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PemesananAdminAdapter extends RecyclerView.Adapter<PemesananAdminAdapter.PemesananAdminViewHolder> {

    Context context;
    List<DataPemesananAdminItem> pemesananAdminItems;

    public PemesananAdminAdapter(Context context, List<DataPemesananAdminItem> pemesananAdminItems) {
        this.context = context;
        this.pemesananAdminItems = pemesananAdminItems;
    }

    @NonNull
    @Override
    public PemesananAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pemesanan_admin, parent, false);
        return new PemesananAdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PemesananAdminViewHolder holder, int position) {

        String id = pemesananAdminItems.get(position).getIdPemesanan();
        String jam = pemesananAdminItems.get(position).getJam();
        String tanggal = pemesananAdminItems.get(position).getTanggal();
        String jumlah = pemesananAdminItems.get(position).getJumlahPesanan();
        String total = pemesananAdminItems.get(position).getTotalHarga();
        String status = pemesananAdminItems.get(position).getStatusPesanan();
        String no_bangku =pemesananAdminItems.get(position).getNoBangku();

        holder.txtId.setText(id);
        holder.txtJam.setText(jam);
        holder.txtTanggal.setText(tanggal);
        holder.txtJumlah.setText(jumlah);
        int totalHarga = Integer.parseInt(total);

        Locale localID = new Locale("in", "ID");
        NumberFormat formatRp = NumberFormat.getCurrencyInstance(localID);

        holder.txtTotalHarga.setText(formatRp.format(totalHarga));

        holder.txtStatus.setText(status);

        holder.btnValidasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ValidasiPembayaranActivity.class);
                intent.putExtra(ValidasiPembayaranActivity.EXTRA_ID_PEMESANAN, id);
                context.startActivity(intent);
                ((KelolaPesananActivity)context).finish();
            }
        });

        if (status.equals("Belum Bayar") || status.equals("Pembayaran Ditolak")){
            holder.btnValidasi.setVisibility(View.GONE);
            holder.txtBelumBayar.setVisibility(View.VISIBLE);
        }else if (status.equals("Pembayaran Sudah Di Validasi")){
            holder.btnValidasi.setVisibility(View.GONE);
            holder.txtBelumBayar.setVisibility(View.GONE);
            holder.btnSetBangku.setVisibility(View.VISIBLE);
        }

        if (!no_bangku.isEmpty()){
            holder.txtPemesananSelesai.setVisibility(View.VISIBLE);
            holder.txtBelumBayar.setVisibility(View.GONE);
            holder.btnSetBangku.setVisibility(View.GONE);
            holder.btnValidasi.setVisibility(View.GONE);
        }

        holder.btnSetBangku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SetBangkuActivity.class);
                intent.putExtra(SetBangkuActivity.EXTRA_ID_PESANAN, id);
                intent.putExtra(SetBangkuActivity.EXTRA_JUMLAH_PESANAN, jumlah);
                context.startActivity(intent);
                ((KelolaPesananActivity)context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return pemesananAdminItems.size();
    }

    public class PemesananAdminViewHolder extends RecyclerView.ViewHolder {

        TextView txtId, txtJam, txtTanggal, txtJumlah, txtTotalHarga, txtStatus, txtBelumBayar, txtPemesananSelesai;
        Button btnValidasi, btnSetBangku;

        public PemesananAdminViewHolder(@NonNull View itemView) {
            super(itemView);
            txtId = itemView.findViewById(R.id.text_id_pemesanan_admin);
            txtJam = itemView.findViewById(R.id.text_jam_pemesanan_admin);
            txtTanggal = itemView.findViewById(R.id.text_tanggal_pemesanan_admin);
            txtJumlah = itemView.findViewById(R.id.text_jumlah_pesanan_admin);
            txtTotalHarga = itemView.findViewById(R.id.text_total_harga_pemesanan_admin);
            txtStatus = itemView.findViewById(R.id.text_status_pemesanan_admin);
            btnValidasi = itemView.findViewById(R.id.btn_validasi_pemesanan_admin);
            txtBelumBayar = itemView.findViewById(R.id.text_belum_bayar_pemesanan_admin);
            btnSetBangku = itemView.findViewById(R.id.btn_set_bangku_pemesanan_admin);
            txtPemesananSelesai = itemView.findViewById(R.id.text_pemesanan_selesai_admin);
        }
    }
}
