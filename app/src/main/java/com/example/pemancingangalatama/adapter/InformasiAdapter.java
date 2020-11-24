package com.example.pemancingangalatama.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pemancingangalatama.EditInformasiActivity;
import com.example.pemancingangalatama.KelolaInformasiActivity;
import com.example.pemancingangalatama.R;
import com.example.pemancingangalatama.model.DataInformasiItem;
import com.example.pemancingangalatama.model.JumlahBangkuTersedia;

import java.util.List;

public class InformasiAdapter extends RecyclerView.Adapter<InformasiAdapter.InformasiViewHolder> {

    Context context;
    List<DataInformasiItem> jumlahBangkuItems;

    public InformasiAdapter(Context context, List<DataInformasiItem> jumlahBangkuItems) {
        this.context = context;
        this.jumlahBangkuItems = jumlahBangkuItems;
    }

    @NonNull
    @Override
    public InformasiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_informasi, parent, false);
        return new InformasiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InformasiViewHolder holder, int position) {

        holder.txtJumlahBangku.setText(jumlahBangkuItems.get(position).getJumlahBangku());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditInformasiActivity.class);
                intent.putExtra(EditInformasiActivity.EXTRA_ID_INFORMASI, jumlahBangkuItems.get(position).getIdInformasi());
                intent.putExtra(EditInformasiActivity.EXTRA_JUMLAH_BANGKU_INFORMASI, jumlahBangkuItems.get(position).getJumlahBangku());
                context.startActivity(intent);
                ((KelolaInformasiActivity)context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return jumlahBangkuItems.size();
    }

    public class InformasiViewHolder extends RecyclerView.ViewHolder {

        TextView txtJumlahBangku;
        public InformasiViewHolder(@NonNull View itemView) {
            super(itemView);

            txtJumlahBangku = itemView.findViewById(R.id.text_jumlah_bangku_informasi_admin);
        }
    }
}
