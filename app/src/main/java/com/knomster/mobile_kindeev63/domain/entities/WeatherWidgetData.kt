package com.knomster.mobile_kindeev63.domain.entities

import java.io.Serializable

sealed class WeatherWidgetData: Serializable {
    data class WeatherData(
        val temperature: String,
        val temperatureVariation: String,
        val temperatureFeels: String,
        val iconUrl: String,
        val weatherStatus: String,
        val city: String
    ): WeatherWidgetData(), Serializable

    data object Load: WeatherWidgetData()

    data object InternetError: WeatherWidgetData()
    
    data object LocationError: WeatherWidgetData()
}
