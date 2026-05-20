package dev.m5rcel.aura.domain.model

data class WeatherData(
    val temperature: Double,
    val weatherCode: Int,
    val windSpeed: Double,
    val humidity: Int,
    val tempMax: Double,
    val tempMin: Double,
    val forecast: List<DailyForecastItem>
)

data class DailyForecastItem(
    val dayAbbrev: String,
    val weatherCode: Int,
    val minTemp: Double,
    val maxTemp: Double
)
