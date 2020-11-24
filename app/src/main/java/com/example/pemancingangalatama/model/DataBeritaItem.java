package com.example.pemancingangalatama.model;

import com.google.gson.annotations.SerializedName;

public class DataBeritaItem{

	@SerializedName("image_berita")
	private String imageBerita;

	@SerializedName("tanggal_berita")
	private String tanggalBerita;

	@SerializedName("id_berita")
	private String idBerita;

	@SerializedName("judul_berita")
	private String judulBerita;

	@SerializedName("isi_berita")
	private String isiBerita;

	public void setImageBerita(String imageBerita){
		this.imageBerita = imageBerita;
	}

	public String getImageBerita(){
		return imageBerita;
	}

	public void setTanggalBerita(String tanggalBerita){
		this.tanggalBerita = tanggalBerita;
	}

	public String getTanggalBerita(){
		return tanggalBerita;
	}

	public void setIdBerita(String idBerita){
		this.idBerita = idBerita;
	}

	public String getIdBerita(){
		return idBerita;
	}

	public void setJudulBerita(String judulBerita){
		this.judulBerita = judulBerita;
	}

	public String getJudulBerita(){
		return judulBerita;
	}

	public void setIsiBerita(String isiBerita){
		this.isiBerita = isiBerita;
	}

	public String getIsiBerita(){
		return isiBerita;
	}

	@Override
 	public String toString(){
		return 
			"DataBeritaItem{" + 
			"image_berita = '" + imageBerita + '\'' + 
			",tanggal_berita = '" + tanggalBerita + '\'' + 
			",id_berita = '" + idBerita + '\'' + 
			",judul_berita = '" + judulBerita + '\'' + 
			",isi_berita = '" + isiBerita + '\'' + 
			"}";
		}
}