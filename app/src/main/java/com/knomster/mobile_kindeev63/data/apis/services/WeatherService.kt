package com.knomster.mobile_kindeev63.data.apis.services

import com.knomster.mobile_kindeev63.domain.ProjectConstants
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("data/2.5/weather?appid=${ProjectConstants.weatherAppId}&units=metric")
    fun getWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("lang") langCode: String
    ): Call<ResponseBody>
}