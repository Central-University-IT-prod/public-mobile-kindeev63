package com.knomster.userslibrary.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UsersRetrofitClient {
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