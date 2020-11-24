package com.example.pemancingangalatama.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseDataInformasi{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("dataInformasi")
	private List<DataInformasiItem> dataInformasi;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDataInformasi(List<DataInformasiItem> dataInformasi){
		this.dataInformasi = dataInformasi;
	}

	public List<DataInformasiItem> getDataInformasi(){
		return dataInformasi;
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
			"ResponseDataInformasi{" + 
			"pesan = '" + pesan + '\'' + 
			",dataInformasi = '" + dataInformasi + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}