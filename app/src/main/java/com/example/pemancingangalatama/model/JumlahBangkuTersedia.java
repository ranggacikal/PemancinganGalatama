package com.example.pemancingangalatama.model;

import com.google.gson.annotations.SerializedName;

public class JumlahBangkuTersedia{

	@SerializedName("jumlah_bangku")
	private String jumlahBangku;

	public void setJumlahBangku(String jumlahBangku){
		this.jumlahBangku = jumlahBangku;
	}

	public String getJumlahBangku(){
		return jumlahBangku;
	}

	@Override
 	public String toString(){
		return 
			"JumlahBangkuTersedia{" + 
			"jumlah_bangku = '" + jumlahBangku + '\'' + 
			"}";
		}
}