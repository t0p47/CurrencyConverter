package com.t0p47.currencyconverter.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "http://api.exchangeratesapi.io/"

private val loggingInterceptor = HttpLoggingInterceptor().apply{
    this.level = HttpLoggingInterceptor.Level.BODY
}

private val okHttpClient = OkHttpClient().newBuilder()
    .readTimeout(5, TimeUnit.SECONDS)
    .connectTimeout(4, TimeUnit.SECONDS)
    .retryOnConnectionFailure(false)
    .addInterceptor(loggingInterceptor)
    .build()

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .build()

object Api{
    val retrofitService: ApiService by lazy{
        retrofit.create(ApiService::class.java)
    }
}