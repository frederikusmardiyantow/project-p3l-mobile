package com.example.project_p3l_mobile.data_api.api

import com.example.project_p3l_mobile.data_api.model.HistoryTransaksiData
import com.example.project_p3l_mobile.data_api.model.ResponseBase
import com.example.project_p3l_mobile.data_api.model.ResponseDataDetailHistory
import com.example.project_p3l_mobile.data_api.model.ResponseDataHistoryByIdCust
import com.example.project_p3l_mobile.data_api.model.ResponseDataJenisKamar
import com.example.project_p3l_mobile.data_api.model.ResponseDataJenisKamarById
import com.example.project_p3l_mobile.data_api.model.ResponseDataKetersediaanKamar
import com.example.project_p3l_mobile.data_api.model.ResponseDataLoginCustomer
import com.example.project_p3l_mobile.data_api.model.ResponseDataLoginPegawai
import com.example.project_p3l_mobile.data_api.model.ResponseDataProfile
import com.example.project_p3l_mobile.data_api.model.ResponseDataRegistrasi
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    suspend fun loginCustomer(
        @Field("email") email:String?,
        @Field("password") password:String?,
    ): ResponseDataLoginCustomer

    @GET("jenis")
    suspend fun getJenisKamar(): ResponseDataJenisKamar

    @GET("profile")
    suspend fun getProfile(
        @Header("Authorization") token:String,
    ): ResponseDataProfile

    @GET("jenis/{id}")
    suspend fun getJenisKamarById(
        @Path("id") id:Int,
    ): ResponseDataJenisKamarById

    @POST("logout")
    suspend fun logout(
        @Header("Authorization") token:String,
    ): ResponseBase

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("jenis_customer") jenis_customer:String?,
        @Field("nama_customer") nama_customer:String?,
        @Field("no_identitas") no_identitas:String?,
        @Field("jenis_identitas") jenis_identitas:String?,
        @Field("no_telp") no_telp:String?,
        @Field("email") email:String?,
        @Field("alamat") alamat:String?,
        @Field("password") password:String?,
        @Field("password_confirmation") password_confirmation:String?,
        @Field("flag_stat") flag_stat:String?,
    ): ResponseDataRegistrasi

    @FormUrlEncoded
    @PUT("ubahProfile")
    suspend fun ubahProfile(
        @Header("Authorization") token:String,
        @Field("jenis_customer") jenis_customer:String?,
        @Field("nama_customer") nama_customer:String?,
        @Field("no_identitas") no_identitas:String?,
        @Field("jenis_identitas") jenis_identitas:String?,
        @Field("no_telp") no_telp:String?,
        @Field("email") email:String?,
        @Field("alamat") alamat:String?,
        @Field("flag_stat") flag_stat:String?,
    ): ResponseDataProfile

    @FormUrlEncoded
    @POST("ubahPassword")
    suspend fun ubahPassword(
        @Header("Authorization") token:String,
        @Field("password_lama") password_lama:String?,
        @Field("password") password:String?,
        @Field("password_confirmation") password_confirmation: String?,
    ): ResponseBase

    @GET("transaksi")
    suspend fun getTransaksi(
        @Header("Authorization") token:String,
    ): ResponseDataHistoryByIdCust

    @FormUrlEncoded
    @POST("forget/request")
    suspend fun lupaPassword(
        @Field("email") email:String?,
        @Field("role") role:String?,
    ): ResponseBase

    @FormUrlEncoded
    @POST("login/admin")
    suspend fun loginPegawai(
        @Field("email") email:String?,
        @Field("password") password:String?,
    ): ResponseDataLoginPegawai

    @FormUrlEncoded
    @POST("ketersediaan/kamar")
    suspend fun postKetersediaanKamar(
        @Field("tgl_check_in") tgl_check_in:String?,
        @Field("tgl_check_out") tgl_check_out:String?,
        @Field("jumlah_dewasa") jumlah_dewasa:Int?,
        @Field("jumlah_anak_anak") jumlah_anak:Int?,
        @Field("jumlah_kamar") jumlah_kamar:Int?,
    ): ResponseDataKetersediaanKamar

    @GET("transaksi/detail/{id}")
    suspend fun getDetailTransaksi(
        @Path("id") id:Int,
        @Header("Authorization") token:String,
    ): ResponseDataDetailHistory

    @POST("transaksi/pembatalan/kamar/{id}")
    suspend fun postBatalPesanan(
        @Path("id") id:Int,
        @Header("Authorization") token:String,
    ): ResponseBase
}