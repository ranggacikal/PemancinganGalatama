package com.example.pemancingangalatama.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseDataPembayaranUser{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("dataPembayaranUser")
	private List<DataPembayaranUserItem> dataPembayaranUser;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDataPembayaranUser(List<DataPembayaranUserItem> dataPembayaranUser){
		this.dataPembayaranUser = dataPembayaranUser;
	}

	public List<DataPembayaranUserItem> getDataPembayaranUser(){
		return dataPembayaranUser;
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
			"ResponseDataPembayaranUser{" + 
			"pesan = '" + pesan + '\'' + 
			",dataPembayaranUser = '" + dataPembayaranUser + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}