package com.example.pemancingangalatama.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseDataBerita{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("dataBerita")
	private List<DataBeritaItem> dataBerita;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDataBerita(List<DataBeritaItem> dataBerita){
		this.dataBerita = dataBerita;
	}

	public List<DataBeritaItem> getDataBerita(){
		return dataBerita;
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
			"ResponseDataBerita{" + 
			"pesan = '" + pesan + '\'' + 
			",dataBerita = '" + dataBerita + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}