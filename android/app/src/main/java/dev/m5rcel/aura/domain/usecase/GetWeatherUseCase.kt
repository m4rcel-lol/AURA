package dev.m5rcel.aura.domain.usecase

import dev.m5rcel.aura.data.repository.WeatherRepository
import dev.m5rcel.aura.domain.model.WeatherData
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double): WeatherData {
        val remote = repository.getRemoteWeather(lat, lon)
        
        // Convert remote DTO response to Domain Model
        val mappedItems = remote.daily.time.mapIndexed { index, time ->
            dev.m5rcel.aura.domain.model.DailyForecastItem(
                dayAbbrev = time.substringAfterLast("-"), // simple day extract placeholder
                weatherCode = remote.current.weatherCode,
                minTemp = remote.daily.tempMin.getOrElse(index) { 15.0 },
                maxTemp = remote.daily.tempMax.getOrElse(index) { 25.0 }
            )
        }

        val domain = WeatherData(
            temperature = remote.current.temperature,
            weatherCode = remote.current.weatherCode,
            windSpeed = remote.current.windSpeed,
            humidity = remote.current.humidity,
            tempMax = remote.daily.tempMax.firstOrNull() ?: 25.0,
            tempMin = remote.daily.tempMin.firstOrNull() ?: 15.0,
            forecast = mappedItems
        )
        repository.cacheWeather(domain)
        return domain
    }

    suspend fun getCached(): WeatherData? = repository.getCachedWeather()
}
