package com.example.pemancingangalatama.model;

import com.google.gson.annotations.SerializedName;

public class DataPembayaranUserItem{

	@SerializedName("validasi_pembayaran")
	private String validasiPembayaran;

	@SerializedName("pemesanan_id")
	private String pemesananId;

	@SerializedName("id_pembayaran")
	private String idPembayaran;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("jumlah_pesanan")
	private String jumlahPesanan;

	@SerializedName("jam")
	private String jam;

	@SerializedName("total_harga")
	private String totalHarga;

	@SerializedName("tanggal")
	private String tanggal;

	@SerializedName("bukti_pembayaran")
	private String buktiPembayaran;

	public void setValidasiPembayaran(String validasiPembayaran){
		this.validasiPembayaran = validasiPembayaran;
	}

	public String getValidasiPembayaran(){
		return validasiPembayaran;
	}

	public void setPemesananId(String pemesananId){
		this.pemesananId = pemesananId;
	}

	public String getPemesananId(){
		return pemesananId;
	}

	public void setIdPembayaran(String idPembayaran){
		this.idPembayaran = idPembayaran;
	}

	public String getIdPembayaran(){
		return idPembayaran;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setJumlahPesanan(String jumlahPesanan){
		this.jumlahPesanan = jumlahPesanan;
	}

	public String getJumlahPesanan(){
		return jumlahPesanan;
	}

	public void setJam(String jam){
		this.jam = jam;
	}

	public String getJam(){
		return jam;
	}

	public void setTotalHarga(String totalHarga){
		this.totalHarga = totalHarga;
	}

	public String getTotalHarga(){
		return totalHarga;
	}

	public void setTanggal(String tanggal){
		this.tanggal = tanggal;
	}

	public String getTanggal(){
		return tanggal;
	}

	public void setBuktiPembayaran(String buktiPembayaran){
		this.buktiPembayaran = buktiPembayaran;
	}

	public String getBuktiPembayaran(){
		return buktiPembayaran;
	}

	@Override
 	public String toString(){
		return 
			"DataPembayaranUserItem{" + 
			"validasi_pembayaran = '" + validasiPembayaran + '\'' + 
			",pemesanan_id = '" + pemesananId + '\'' + 
			",id_pembayaran = '" + idPembayaran + '\'' + 
			",user_id = '" + userId + '\'' + 
			",jumlah_pesanan = '" + jumlahPesanan + '\'' + 
			",jam = '" + jam + '\'' + 
			",total_harga = '" + totalHarga + '\'' + 
			",tanggal = '" + tanggal + '\'' + 
			",bukti_pembayaran = '" + buktiPembayaran + '\'' + 
			"}";
		}
}