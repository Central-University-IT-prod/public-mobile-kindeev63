package com.knomster.mobile_kindeev63

import com.knomster.mobile_kindeev63.data.apis.WeatherApiImpl
import com.knomster.mobile_kindeev63.domain.entities.WeatherWidgetData
import com.knomster.mobile_kindeev63.domain.useCases.WeatherUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.assertTrue

class WeatherUseCaseTest {

    private object Regexes {
        val temperature = "^-?\\d+°\$".toRegex()
        val temperatureVariation = "^.\\d+° / .\\d+°\$".toRegex()
        val temperatureFeels = "^.\\d+°\$".toRegex()
        val iconUrl = "https://openweathermap\\.org/img/wn/.*@2x\\.png".toRegex()
        val weatherStatus = "\\p{Lu}\\p{Ll}*".toRegex()
        val city = "\\p{Lu}\\p{Ll}*".toRegex()
    }

    @Test
    fun getWeatherUseCaseTest() {
        val weatherUseCase = WeatherUseCase(WeatherApiImpl())
        runBlocking {
            val results = weatherUseCase.getWeather(
                latitude = 54.3258229,
                longitude = 48.3861452,
            )
            assertTrue(results is WeatherWidgetData.WeatherData)
            val widgetData = results as WeatherWidgetData.WeatherData

            assertTrue(widgetData.temperature.matches(Regexes.temperature))
            assertTrue(widgetData.temperatureVariation.matches(Regexes.temperatureVariation))
            assertTrue(widgetData.temperatureFeels.matches(Regexes.temperatureFeels))
            assertTrue(widgetData.iconUrl.matches(Regexes.iconUrl))
            assertTrue(widgetData.weatherStatus.matches(Regexes.weatherStatus))
            assertTrue(widgetData.city.matches(Regexes.city))
        }
    }
}