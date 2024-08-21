package com.knomster.mobile_kindeev63.domain.entities.responses

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WeatherResponse(
    @SerializedName("coord")
    val cords: Cords,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val rain: Rain,
    val clouds: Clouds,
    @SerializedName("dt")
    val dateTime: Long,
    @SerializedName("sys")
    val systemData: Sys,
    @SerializedName("timezone")
    val timeZone: Int,
    @SerializedName("id")
    val cityId: Int,
    @SerializedName("name")
    val cityName: String,
    @SerializedName("cod")
    val responseCode: Int
): Serializable

data class Cords(
    @SerializedName("lon")
    val longitude: Double,
    @SerializedName("lat")
    val latitude: Double
): Serializable

data class Weather(
    val id: Int,
    @SerializedName("main")
    val mainWeather: String,
    val description: String,
    val icon: String
): Serializable

data class Main(
    @SerializedName("temp")
    val temperature: Double,
    @SerializedName("feels_like")
    val feelsLikeTemperature: Double,
    @SerializedName("temp_min")
    val minimumTemperature: Double,
    @SerializedName("temp_max")
    val maximumTemperature: Double,
    @SerializedName("pressure")
    val atmosphericPressure: Int,
    val humidity: Int,
    @SerializedName("sea_level")
    val seaLevelPressure: Int,
    @SerializedName("grnd_level")
    val groundLevelPressure: Int
): Serializable

data class Wind(
    val speed: Double,
    @SerializedName("deg")
    val direction: Int,
    @SerializedName("gust")
    val windGust: Double
): Serializable

data class Rain(
    @SerializedName("1h")
    val oneHourRainfall: Double
): Serializable

data class Clouds(
    @SerializedName("all")
    val cloudinessPercentage: Int
): Serializable

data class Sys(
    val type: Int,
    val id: Int,
    @SerializedName("country")
    val countryCode: String,
    @SerializedName("sunrise")
    val sunriseTime: Long,
    @SerializedName("sunset")
    val sunsetTime: Long
): Serializable
