package com.example.pemancingangalatama.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseDataPemesananUser{

	@SerializedName("dataPemesananUser")
	private List<DataPemesananUserItem> dataPemesananUser;

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("status")
	private int status;

	public void setDataPemesananUser(List<DataPemesananUserItem> dataPemesananUser){
		this.dataPemesananUser = dataPemesananUser;
	}

	public List<DataPemesananUserItem> getDataPemesananUser(){
		return dataPemesananUser;
	}

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
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
			"ResponseDataPemesananUser{" + 
			"dataPemesananUser = '" + dataPemesananUser + '\'' + 
			",pesan = '" + pesan + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}