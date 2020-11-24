package com.example.pemancingangalatama.api;

import com.example.pemancingangalatama.model.ResponseDataBerita;
import com.example.pemancingangalatama.model.ResponseDataInformasi;
import com.example.pemancingangalatama.model.ResponseDataPembayaranUser;
import com.example.pemancingangalatama.model.ResponseDataPemesananAdmin;
import com.example.pemancingangalatama.model.ResponseDataPemesananUser;
import com.example.pemancingangalatama.model.ResponseDataUser;
import com.example.pemancingangalatama.model.ResponseEditBerita;
import com.example.pemancingangalatama.model.ResponseEditInformasi;
import com.example.pemancingangalatama.model.ResponseEditUser;
import com.example.pemancingangalatama.model.ResponseHapusBerita;
import com.example.pemancingangalatama.model.ResponseHapusUser;
import com.example.pemancingangalatama.model.ResponseInputNoBangku;
import com.example.pemancingangalatama.model.ResponseInsertPembayaran;
import com.example.pemancingangalatama.model.ResponseInsertPemesanan;
import com.example.pemancingangalatama.model.ResponseJumlahBangkuTersedia;
import com.example.pemancingangalatama.model.ResponseLoginUser;
import com.example.pemancingangalatama.model.ResponseRegister;
import com.example.pemancingangalatama.model.ResponseTambahBerita;
import com.example.pemancingangalatama.model.ResponseTolakPembayaran;
import com.example.pemancingangalatama.model.ResponseUbahStatusPesanan;
import com.example.pemancingangalatama.model.ResponseValidasiPembayaran;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @FormUrlEncoded
    @POST("Register")
    Call<ResponseRegister> Register(@Field("nama_lengkap") String nama_lengkap,
                                    @Field("email") String email,
                                    @Field("username") String username,
                                    @Field("password") String password,
                                    @Field("no_telpon") String no_telpon,
                                    @Field("level") String level,
                                    @Field("image_user") String image);

    @FormUrlEncoded
    @POST("Login")
    Call<ResponseLoginUser> LoginUser(@Field("username") String username,
                                      @Field("password") String password);

    @GET("getDataBerita")
    Call<ResponseDataBerita> DataBerita();

    @GET("JumlahBangkuTersedia")
    Call<ResponseJumlahBangkuTersedia> JumlahBangku();

    @FormUrlEncoded
    @POST("insertPemesanan")
    Call<ResponseInsertPemesanan> insertPemesanan(@Field("user_id") String id_user,
                                                  @Field("total_harga") int total_harga,
                                                  @Field("tanggal") String tanggal,
                                                  @Field("jam") String jam,
                                                  @Field("jumlah_pesanan") int jumlah_pesanan,
                                                  @Field("no_bangku") String no_bangku,
                                                  @Field("id_informasi") String id_informasi,
                                                  @Field("status_pesanan") String status_pesanan);

    @GET("GetDataPemesanan/{id_user}")
    Call<ResponseDataPemesananUser> GetDataPemesananUser(@Path("id_user") String id_user);

    @GET("GetDataPembayaran/{id_pemesanan}")
    Call<ResponseDataPembayaranUser> GetDataPembayaran(@Path("id_pemesanan") String id_pemesanan);

    @FormUrlEncoded
    @POST("InsertPembayaran")
    Call<ResponseInsertPembayaran> insertPembayaran(@Field("bukti_pembayaran") String bukti_pembayaran,
                                                    @Field("id_pemesanan") String id_pemesanan);

    @FormUrlEncoded
    @POST("UbahStatusPesanan")
    Call<ResponseUbahStatusPesanan> ubahStatusPesanan(@Field("id_pemesanan") String id_pemesanan,
                                                      @Field("status_pesanan") String status_pesanan);

    @GET("GetDataPemesananAdmin")
    Call<ResponseDataPemesananAdmin> dataPemesananAdmin();

    @FormUrlEncoded
    @POST("ValidasiPembayaran")
    Call<ResponseValidasiPembayaran> validasiPembayaran(@Field("id_pembayaran") String id_pembayaran,
                                                        @Field("validasi_pembayaran") String validasi_pembayaran);

    @FormUrlEncoded
    @POST("TolakPembayaran")
    Call<ResponseTolakPembayaran> tolakPembayaran(@Field("id_pembayaran") String id_pembayaran);

    @FormUrlEncoded
    @POST("InputNoBangku")
    Call<ResponseInputNoBangku> inputNoBangku(@Field("id_pemesanan") String id_pemesanan,
                                              @Field("no_bangku") String no_bangku);

    @FormUrlEncoded
    @POST("insertBerita")
    Call<ResponseTambahBerita> tambahBerita(@Field("judul_berita") String judul_berita,
                                            @Field("tanggal_berita") String tanggal_berita,
                                            @Field("isi_berita") String isi_berita,
                                            @Field("image_berita") String image_berita);

    @FormUrlEncoded
    @POST("EditBerita")
    Call<ResponseEditBerita> editBerita(@Field("id_berita") String id_berita,
                                        @Field("judul_berita") String judul_berita,
                                        @Field("tanggal_berita") String tanggal_berita,
                                        @Field("isi_berita") String isi_berita);

    @FormUrlEncoded
    @POST("HapusBerita")
    Call<ResponseHapusBerita> hapusBerita(@Field("id_berita") String id_berita);

    @GET("getDataInformasi")
    Call<ResponseDataInformasi> dataInformasi();

    @FormUrlEncoded
    @POST("EditInformasi")
    Call<ResponseEditInformasi> editInformasi(@Field("id_informasi")String id_informasi,
                                              @Field("jumlah_bangku") String jumlah_bangku);

    @GET("getDataUser")
    Call<ResponseDataUser> dataUser();

    @FormUrlEncoded
    @POST("EditUser")
    Call<ResponseEditUser> editUser(@Field("id_user") String id_user,
                                    @Field("nama_lengkap") String nama_lengkap,
                                    @Field("email") String email,
                                    @Field("username") String username,
                                    @Field("no_telpon") String no_telpon,
                                    @Field("level") String level);

    @FormUrlEncoded
    @POST("HapusUser")
    Call<ResponseHapusUser> hapusUser(@Field("id_user") String id_user);

}
