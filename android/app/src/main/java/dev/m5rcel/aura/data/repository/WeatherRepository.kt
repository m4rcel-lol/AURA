package dev.m5rcel.aura.data.repository

import dev.m5rcel.aura.data.local.dao.WeatherDao
import dev.m5rcel.aura.data.local.entity.WeatherEntity
import dev.m5rcel.aura.data.remote.WeatherApi
import dev.m5rcel.aura.data.remote.dto.WeatherResponse
import dev.m5rcel.aura.domain.model.WeatherData
import dev.m5rcel.aura.domain.model.DailyForecastItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherDao: WeatherDao
) {
    suspend fun getRemoteWeather(lat: Double, lon: Double): WeatherResponse {
        return weatherApi.getForecast(lat, lon)
    }

    suspend fun getCachedWeather(): WeatherData? {
        val cached = weatherDao.getCachedWeather() ?: return null
        
        // Mock a simple forecast based on cached temperatures
        val forecast = List(5) { index ->
            DailyForecastItem(
                dayAbbrev = when (index) {
                    0 -> "Mon"
                    1 -> "Tue"
                    2 -> "Wed"
                    3 -> "Thu"
                    4 -> "Fri"
                    else -> "Sat"
                },
                weatherCode = cached.weatherCode,
                minTemp = cached.tempMin,
                maxTemp = cached.tempMax
            )
        }

        return WeatherData(
            temperature = cached.temperature,
            weatherCode = cached.weatherCode,
            windSpeed = cached.windSpeed,
            humidity = cached.humidity,
            tempMax = cached.tempMax,
            tempMin = cached.tempMin,
            forecast = forecast
        )
    }

    suspend fun cacheWeather(data: WeatherData) {
        val entity = WeatherEntity(
            temperature = data.temperature,
            weatherCode = data.weatherCode,
            windSpeed = data.windSpeed,
            humidity = data.humidity,
            tempMax = data.tempMax,
            tempMin = data.tempMin,
            fetchedAt = System.currentTimeMillis()
        )
        weatherDao.cacheWeather(entity)
    }
}
