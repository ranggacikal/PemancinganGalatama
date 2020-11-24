package com.example.pemancingangalatama.model;

import com.google.gson.annotations.SerializedName;

public class DataInformasiItem{

	@SerializedName("jumlah_bangku")
	private String jumlahBangku;

	@SerializedName("id_informasi")
	private String idInformasi;

	public void setJumlahBangku(String jumlahBangku){
		this.jumlahBangku = jumlahBangku;
	}

	public String getJumlahBangku(){
		return jumlahBangku;
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
			"DataInformasiItem{" + 
			"jumlah_bangku = '" + jumlahBangku + '\'' + 
			",id_informasi = '" + idInformasi + '\'' + 
			"}";
		}
}