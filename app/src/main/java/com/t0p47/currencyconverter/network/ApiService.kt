package com.t0p47.currencyconverter.network

import com.t0p47.currencyconverter.model.LatestCurrency
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {


    //TODO: If server answer is error than show old currencyRates or some default values(maybe from self server)
    //https://exchangeratesapi.io/
    @GET("latest")
    suspend fun getLatestCurrency(@QueryMap params: Map<String, String>): LatestCurrency

}