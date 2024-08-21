package com.knomster.mobile_kindeev63

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.knomster.mobile_kindeev63.domain.entities.WeatherWidgetData
import com.knomster.mobile_kindeev63.presentation.ui.elements.WeatherWidget
import org.junit.Rule
import org.junit.Test

class WeatherWidgetUiTest {
    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun weatherWidget_displaysWeatherData() {

        val weatherData = WeatherWidgetData.WeatherData(
            temperature = "-100°",
            temperatureVariation = "-100° / 100°",
            temperatureFeels = "-100°",
            iconUrl = "https://openweathermap.org/img/wn/04d@2x.png",
            weatherStatus = "Облачно с прояснениями",
            city = "Екатеринбург"
        )
        rule.setContent { WeatherWidget(weatherData = weatherData) }
        rule.waitForIdle()
        rule.onNode(hasText("-100°")).assertExists()
        rule.onNodeWithText("-100° / 100°").assertIsDisplayed()
        rule.onNodeWithText(
            rule.activity.getString(R.string.feels_like) + " -100°"
        ).assertIsDisplayed()
        rule.onNodeWithText("Облачно с прояснениями").assertIsDisplayed()
        rule.onNodeWithText("Екатеринбург").assertIsDisplayed()

        // Wait for image load
        Thread.sleep(10000)
        rule.onNodeWithTag("weatherIcon").assertIsDisplayed()
        rule.onNodeWithTag("weatherIconLoad").assertDoesNotExist()
    }

    @Test
    fun weatherWidget_InternetError() {
        val weatherData = WeatherWidgetData.InternetError

        rule.setContent { WeatherWidget(weatherData = weatherData) }

        rule.onNodeWithText(rule.activity.getString(R.string.no_connection)).assertIsDisplayed()
    }

    @Test
    fun weatherWidget_LocationError() {
        val weatherData = WeatherWidgetData.LocationError

        rule.setContent { WeatherWidget(weatherData = weatherData) }

        rule.onNodeWithText(rule.activity.getString(R.string.no_location_permission)).assertIsDisplayed()
    }

    @Test
    fun weatherWidget_Loading() {
        val weatherData = WeatherWidgetData.Load

        rule.setContent { WeatherWidget(weatherData = weatherData) }

        rule.onNodeWithTag("loadBar").assertIsDisplayed()
    }
}