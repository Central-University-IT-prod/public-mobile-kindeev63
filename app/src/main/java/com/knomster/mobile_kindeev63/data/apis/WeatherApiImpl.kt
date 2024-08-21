package com.knomster.mobile_kindeev63.data.apis

import com.google.gson.Gson
import com.knomster.mobile_kindeev63.data.apis.clients.WeatherRetrofitClient
import com.knomster.mobile_kindeev63.data.apis.services.WeatherService
import com.knomster.mobile_kindeev63.domain.interfaces.WeatherApi
import com.knomster.mobile_kindeev63.domain.entities.responses.WeatherResponse
import java.util.Locale

class WeatherApiImpl: WeatherApi {

    /**
     * Получение погоды
     */
    override fun getWeather(latitude: Double, longitude: Double): WeatherResponse? {
        try {
            val client = WeatherRetrofitClient.getClient("https://api.openweathermap.org/")
            val weatherService = client.create(WeatherService::class.java)
            val call = weatherService.getWeather(
                latitude = latitude,
                longitude = longitude,
                langCode = Locale.getDefault().language
            )
            val response = call.execute()
            if (response.isSuccessful) {
                val data = response.body()?.string() ?: return null
                return Gson().fromJson(data, WeatherResponse::class.java)
            }
            return null
        } catch (_: Exception) {
            return null
        }
    }
}