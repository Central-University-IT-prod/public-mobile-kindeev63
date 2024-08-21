package com.knomster.mobile_kindeev63.data.apis

import com.google.gson.Gson
import com.knomster.mobile_kindeev63.data.apis.clients.BoredRetrofitClient
import com.knomster.mobile_kindeev63.data.apis.services.BoredService
import com.knomster.mobile_kindeev63.domain.entities.responses.AdviceResponse
import com.knomster.mobile_kindeev63.domain.interfaces.AdviceApi

class BoredApiImpl: AdviceApi {

    /**
     * Получение случайной активности
     */
    override fun randomAdvice(): AdviceResponse? {
        try {
            val client = BoredRetrofitClient.getClient("https://www.boredapi.com/")
            val boredService = client.create(BoredService::class.java)
            val call = boredService.randomAdvice()
            val response = call.execute()
            if (response.isSuccessful) {
                val data = response.body()?.string() ?: return null
                return Gson().fromJson(data, AdviceResponse::class.java)
            }
            return null
        } catch (_: Exception) {
            return null
        }
    }
}