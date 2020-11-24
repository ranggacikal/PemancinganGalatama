package com.example.pemancingangalatama;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pemancingangalatama.SharedPreference.SharedPreferencedConfig;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransaksiActivity extends AppCompatActivity {

    @BindView(R.id.text_nama_pemesan_transaksi)
    TextView textNamaPemesanTransaksi;
    @BindView(R.id.text_tanggal_pemesan_transaksi)
    TextView textTanggalPemesanTransaksi;
    @BindView(R.id.text_jam_pemesan_transaksi)
    TextView textJamPemesanTransaksi;
    @BindView(R.id.text_jumlah_pemesan_transaksi)
    TextView textJumlahPemesanTransaksi;
    @BindView(R.id.text_total_harga_pemesan_transaksi)
    TextView textTotalHargaPemesanTransaksi;
    @BindView(R.id.btn_cek_petunjuk_bayar)
    Button btnCekPetunjukBayar;
    @BindView(R.id.btn_bayar_transfer_bank)
    Button btnBayarTransferBank;

    public static final String EXTRA_ID_PEMESANAN = "ExtraIdPemesanan";
    public static final String EXTRA_TANGGAL_PEMESANAN = "ExtraTanggalPemesanan";
    public static final String EXTRA_JAM_PEMESANAN = "ExtraJamPemesanan";
    public static final String EXTRA_JUMLAH_PEMESANAN = "ExtraJumlahPemesanan";
    public static final String EXTRA_TOTAL_HARGA_PEMESANAN = "ExtraTotalHargaPemesanan";

    private SharedPreferencedConfig preferencedConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);
        ButterKnife.bind(this);

        preferencedConfig = new SharedPreferencedConfig(this);

        textNamaPemesanTransaksi.setText(preferencedConfig.getPreferenceNamaLengkap());
        textTanggalPemesanTransaksi.setText(getIntent().getStringExtra(EXTRA_TANGGAL_PEMESANAN));
        textJamPemesanTransaksi.setText(getIntent().getStringExtra(EXTRA_JAM_PEMESANAN));
        textJumlahPemesanTransaksi.setText(getIntent().getStringExtra(EXTRA_JUMLAH_PEMESANAN));
        int total = Integer.parseInt(getIntent().getStringExtra(EXTRA_TOTAL_HARGA_PEMESANAN));

        Locale localID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localID);

        textTotalHargaPemesanTransaksi.setText(formatRupiah.format(total));

        btnCekPetunjukBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekPetunjuk();
            }
        });

        btnBayarTransferBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transferBank();
            }
        });

    }

    private void transferBank() {

        Intent intent = new Intent(TransaksiActivity.this, TransferActivity.class);
        intent.putExtra(TransferActivity.EXTRA_ID_TRANSFER, getIntent().getStringExtra(EXTRA_ID_PEMESANAN));
        intent.putExtra(TransferActivity.EXTRA_NAMA_TRANSFER, preferencedConfig.getPreferenceNamaLengkap());
        startActivity(intent);
        finish();

    }

    private void cekPetunjuk() {

        final Dialog dialog = new Dialog(TransaksiActivity.this);
        dialog.setContentView(R.layout.dialog_petinjuk_bayar);
        dialog.setTitle("Petunjuk Pembayaran");

        final TextView txtTutup = dialog.findViewById(R.id.text_dialog_tutup_cek);

        dialog.show();

        txtTutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(TransaksiActivity.this, PembayaranActivity.class));
        finish();
    }
}