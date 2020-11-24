package com.example.pemancingangalatama.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseDataPemesananAdmin{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("dataPemesananAdmin")
	private List<DataPemesananAdminItem> dataPemesananAdmin;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDataPemesananAdmin(List<DataPemesananAdminItem> dataPemesananAdmin){
		this.dataPemesananAdmin = dataPemesananAdmin;
	}

	public List<DataPemesananAdminItem> getDataPemesananAdmin(){
		return dataPemesananAdmin;
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
			"ResponseDataPemesananAdmin{" + 
			"pesan = '" + pesan + '\'' + 
			",dataPemesananAdmin = '" + dataPemesananAdmin + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}