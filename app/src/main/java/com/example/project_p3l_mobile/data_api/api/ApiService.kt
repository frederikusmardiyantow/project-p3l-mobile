package com.example.project_p3l_mobile.data_api.api

import com.example.project_p3l_mobile.data_api.model.HistoryTransaksiData
import com.example.project_p3l_mobile.data_api.model.ResponseBase
import com.example.project_p3l_mobile.data_api.model.ResponseDataHistoryByIdCust
import com.example.project_p3l_mobile.data_api.model.ResponseDataJenisKamar
import com.example.project_p3l_mobile.data_api.model.ResponseDataJenisKamarById
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
}