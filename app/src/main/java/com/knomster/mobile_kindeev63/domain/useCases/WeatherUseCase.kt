package com.knomster.mobile_kindeev63.domain.useCases

import com.knomster.mobile_kindeev63.domain.entities.responses.WeatherResponse
import com.knomster.mobile_kindeev63.domain.entities.WeatherWidgetData
import com.knomster.mobile_kindeev63.domain.interfaces.WeatherApi
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.round

class WeatherUseCase(private val weatherApi: WeatherApi) {
    fun getWeather(latitude: Double, longitude: Double): WeatherWidgetData {
        val weatherResponse = weatherApi.getWeather(latitude, longitude) ?: return WeatherWidgetData.InternetError
        return WeatherWidgetData.WeatherData(
            temperature = weatherResponse.main.temperature.roundToTemperature(),
            temperatureVariation = temperatureVariation(weatherResponse),
            temperatureFeels = weatherResponse.main.feelsLikeTemperature.roundToTemperature(),
            iconUrl = "https://openweathermap.org/img/wn/${weatherResponse.weather.first().icon}@2x.png",
            weatherStatus = weatherResponse.weather.first().description.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
            city = weatherResponse.cityName
        )
    }

    private fun Double.roundToTemperature(): String {
        return "${round(this).toInt()}°"
    }
    private fun Double.roundToTemperatureVariation(): String {
        val number = ceil(abs(this)).toInt()
        if (number == 0) return "0°"
        val sign = if (this.toString()[0].isDigit()) '+' else '-'
        return "$sign$number°"
    }

    private fun temperatureVariation(weatherResponse: WeatherResponse): String {
        return "${weatherResponse.main.minimumTemperature.roundToTemperatureVariation()} / ${weatherResponse.main.maximumTemperature.roundToTemperatureVariation()}"
    }
}