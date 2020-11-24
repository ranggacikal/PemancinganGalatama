package com.example.pemancingangalatama.model;

import com.google.gson.annotations.SerializedName;

public class ResponseTambahBerita{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("status")
	private int status;

	public String getPesan(){
		return pesan;
	}

	public int getStatus(){
		return status;
	}
}