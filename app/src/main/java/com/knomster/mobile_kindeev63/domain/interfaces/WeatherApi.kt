package com.knomster.mobile_kindeev63.domain.interfaces

import com.knomster.mobile_kindeev63.domain.entities.responses.WeatherResponse

interface WeatherApi {
    fun getWeather(latitude: Double, longitude: Double): WeatherResponse?
}