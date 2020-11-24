package com.example.pemancingangalatama.model;

import com.google.gson.annotations.SerializedName;

public class DataPemesananAdminItem{

	@SerializedName("id_pemesanan")
	private String idPemesanan;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("jumlah_pesanan")
	private String jumlahPesanan;

	@SerializedName("jam")
	private String jam;

	@SerializedName("nama_lengkap")
	private String namaLengkap;

	@SerializedName("total_harga")
	private String totalHarga;

	@SerializedName("tanggal")
	private String tanggal;

	@SerializedName("status_pesanan")
	private String statusPesanan;

	@SerializedName("no_bangku")
	private String noBangku;

	@SerializedName("id_informasi")
	private String idInformasi;

	public void setIdPemesanan(String idPemesanan){
		this.idPemesanan = idPemesanan;
	}

	public String getIdPemesanan(){
		return idPemesanan;
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

	public void setNamaLengkap(String namaLengkap){
		this.namaLengkap = namaLengkap;
	}

	public String getNamaLengkap(){
		return namaLengkap;
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

	public void setStatusPesanan(String statusPesanan){
		this.statusPesanan = statusPesanan;
	}

	public String getStatusPesanan(){
		return statusPesanan;
	}

	public void setNoBangku(String noBangku){
		this.noBangku = noBangku;
	}

	public String getNoBangku(){
		return noBangku;
	}

	public void setIdInformasi(String idInformasi){
		this.idInformasi = idInformasi;
	}

	public String getIdInformasi(){
		return idInformasi;
	}

	@Override
 	public String toString(){
		return 
			"DataPemesananAdminItem{" + 
			"id_pemesanan = '" + idPemesanan + '\'' + 
			",user_id = '" + userId + '\'' + 
			",jumlah_pesanan = '" + jumlahPesanan + '\'' + 
			",jam = '" + jam + '\'' + 
			",nama_lengkap = '" + namaLengkap + '\'' + 
			",total_harga = '" + totalHarga + '\'' + 
			",tanggal = '" + tanggal + '\'' + 
			",status_pesanan = '" + statusPesanan + '\'' + 
			",no_bangku = '" + noBangku + '\'' + 
			",id_informasi = '" + idInformasi + '\'' + 
			"}";
		}
}