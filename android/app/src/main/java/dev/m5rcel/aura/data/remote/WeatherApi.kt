package dev.m5rcel.aura.data.remote

import dev.m5rcel.aura.data.remote.dto.FloodResponse
import dev.m5rcel.aura.data.remote.dto.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Url
import retrofit2.http.Query

interface WeatherApi {
    @GET("v1/forecast")
    suspend fun getForecast(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,
        @Query("current") current: String = "temperature_2m,weathercode,windspeed_10m,relativehumidity_2m",
        @Query("daily") daily: String = "temperature_2m_max,temperature_2m_min",
        @Query("hourly") hourly: String = "precipitation,windgusts_10m,snowfall,visibility,cape",
        @Query("forecast_days") forecastDays: Int = 5,
        @Query("timezone") timezone: String = "auto"
    ): WeatherResponse

    @GET
    suspend fun getFloodData(
        @Url url: String
    ): FloodResponse
}
