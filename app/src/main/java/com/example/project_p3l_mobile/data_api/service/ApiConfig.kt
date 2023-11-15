package com.example.project_p3l_mobile.data_api.service

import com.example.project_p3l_mobile.data_api.api.ApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    private val customHeader = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("Accept", "application/json")
            .build()
        chain.proceed(request)
    }

    private val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(customHeader)
        .build()

    fun getApiService(): ApiService {
        val retrofit = Retrofit.Builder()
//            .baseUrl("https://gah-jinston-api.azurewebsites.net/")
//            .baseUrl("http://192.168.18.184:4000/")
            .baseUrl("https://project-p3l-be.frederikus.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}