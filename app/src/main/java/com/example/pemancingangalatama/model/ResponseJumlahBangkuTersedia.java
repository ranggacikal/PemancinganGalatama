package com.example.pemancingangalatama.model;

import com.google.gson.annotations.SerializedName;

public class ResponseJumlahBangkuTersedia{

	@SerializedName("jumlah_bangku_tersedia")
	private JumlahBangkuTersedia jumlahBangkuTersedia;

	@SerializedName("status")
	private int status;

	public void setJumlahBangkuTersedia(JumlahBangkuTersedia jumlahBangkuTersedia){
		this.jumlahBangkuTersedia = jumlahBangkuTersedia;
	}

	public JumlahBangkuTersedia getJumlahBangkuTersedia(){
		return jumlahBangkuTersedia;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"ResponseJumlahBangkuTersedia{" + 
			"jumlah_bangku_tersedia = '" + jumlahBangkuTersedia + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}