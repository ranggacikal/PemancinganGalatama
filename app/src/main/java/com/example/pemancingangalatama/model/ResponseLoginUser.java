package com.example.pemancingangalatama.model;

import com.google.gson.annotations.SerializedName;

public class ResponseLoginUser{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("dataLogin")
	private DataLogin dataLogin;

	@SerializedName("sukses")
	private boolean sukses;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDataLogin(DataLogin dataLogin){
		this.dataLogin = dataLogin;
	}

	public DataLogin getDataLogin(){
		return dataLogin;
	}

	public void setSukses(boolean sukses){
		this.sukses = sukses;
	}

	public boolean isSukses(){
		return sukses;
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
			"ResponseLoginUser{" + 
			"pesan = '" + pesan + '\'' + 
			",dataLogin = '" + dataLogin + '\'' + 
			",sukses = '" + sukses + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}