package com.knomster.mobile_kindeev63.data.apis.clients

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherRetrofitClient {
    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit {
        return retrofit ?: createAndGetRetrofit(baseUrl)
    }

    private fun createAndGetRetrofit(baseUrl: String): Retrofit {
        val newRetrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit = newRetrofit
        return newRetrofit
    }
}