package dev.m5rcel.aura.data.remote.dto

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val current: CurrentWeather,
    val daily: DailyForecast,
    val hourly: HourlyData?
)

data class CurrentWeather(
    @SerializedName("temperature_2m") val temperature: Double,
    @SerializedName("weathercode") val weatherCode: Int,
    @SerializedName("windspeed_10m") val windSpeed: Double,
    @SerializedName("relativehumidity_2m") val humidity: Int
)

data class DailyForecast(
    val time: List<String>,
    @SerializedName("temperature_2m_max") val tempMax: List<Double>,
    @SerializedName("temperature_2m_min") val tempMin: List<Double>
)

data class HourlyData(
    val time: List<String>,
    val precipitation: List<Double>,
    @SerializedName("windgusts_10m") val windGusts: List<Double>,
    val snowfall: List<Double>,
    val visibility: List<Double>,
    val cape: List<Double>?
)
