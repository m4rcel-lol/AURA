package dev.m5rcel.aura.notification.weather

import dev.m5rcel.aura.data.remote.dto.WeatherResponse
import dev.m5rcel.aura.domain.model.Severity
import dev.m5rcel.aura.domain.model.WarningType
import dev.m5rcel.aura.domain.model.WeatherWarning
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherWarningAnalyzer @Inject constructor() {

    fun analyze(weather: WeatherResponse, floodDischargePct: Double?): List<WeatherWarning> {
        val warnings = mutableListOf<WeatherWarning>()

        // 1. Current & Hourly Precipitation rules (Rain)
        val hourlyPrecipitation = weather.hourly?.precipitation ?: emptyList()
        val maxPrecipitationInForecast = hourlyPrecipitation.maxOrNull() ?: 0.0

        if (maxPrecipitationInForecast >= 25.0) {
            warnings.add(
                WeatherWarning(
                    severity = Severity.SEVERE,
                    type = WarningType.HEAVY_RAIN,
                    title = "Torrential Rain Warning",
                    message = "Torrential rain of ${String.format("%.1f", maxPrecipitationInForecast)} mm/h expected. Risk of localized flash flooding."
                )
            )
        } else if (maxPrecipitationInForecast >= 10.0) {
            warnings.add(
                WeatherWarning(
                    severity = Severity.MODERATE,
                    type = WarningType.HEAVY_RAIN,
                    title = "Heavy Rain Advisory",
                    message = "Heavy rain of ${String.format("%.1f", maxPrecipitationInForecast)} mm/h forecast. Plan ahead."
                )
            )
        }

        // 2. Wind Gusts rules
        val hourlyWindGusts = weather.hourly?.windGusts ?: emptyList()
        val maxWindGustInForecast = hourlyWindGusts.maxOrNull() ?: 0.0

        if (maxWindGustInForecast >= 120.0) {
            warnings.add(
                WeatherWarning(
                    severity = Severity.EXTREME,
                    type = WarningType.EXTREME_WIND,
                    title = "Hurricane-Force Wind Gusts",
                    message = "Catastrophic winds up to ${String.format("%.1f", maxWindGustInForecast)} km/h expected. Stay indoors!"
                )
            )
        } else if (maxWindGustInForecast >= 90.0) {
            warnings.add(
                WeatherWarning(
                    severity = Severity.SEVERE,
                    type = WarningType.STORM,
                    title = "Severe Storm Gusts Warning",
                    message = "Storm-force gusts up to ${String.format("%.1f", maxWindGustInForecast)} km/h expected in the next few hours."
                )
            )
        } else if (maxWindGustInForecast >= 60.0) {
            warnings.add(
                WeatherWarning(
                    severity = Severity.MODERATE,
                    type = WarningType.EXTREME_WIND,
                    title = "Strong Wind Advisory",
                    message = "Strong winds up to ${String.format("%.1f", maxWindGustInForecast)} km/h expected. Secure loose outdoor objects."
                )
            )
        }

        // 3. Snowfall rules
        val hourlySnowfall = weather.hourly?.snowfall ?: emptyList()
        val maxSnowfallInForecast = hourlySnowfall.maxOrNull() ?: 0.0

        if (maxSnowfallInForecast >= 5.0) {
            warnings.add(
                WeatherWarning(
                    severity = Severity.SEVERE,
                    type = WarningType.SNOWFALL,
                    title = "Heavy Snowfall Warning",
                    message = "Significant snow accumulations up to ${String.format("%.1f", maxSnowfallInForecast)} cm/h likely. Hazardous road conditions."
                )
            )
        } else if (maxSnowfallInForecast >= 2.0) {
            warnings.add(
                WeatherWarning(
                    severity = Severity.MODERATE,
                    type = WarningType.SNOWFALL,
                    title = "Snowfall Advisory",
                    message = "Accumulating snowfall up to ${String.format("%.1f", maxSnowfallInForecast)} cm/h expected. Drive with extreme caution."
                )
            )
        }

        // 4. Visibility rules
        val hourlyVisibility = weather.hourly?.visibility ?: emptyList()
        val minVisibilityInForecast = hourlyVisibility.minOrNull() ?: 20000.0

        if (minVisibilityInForecast <= 200.0) {
            warnings.add(
                WeatherWarning(
                    severity = Severity.SEVERE,
                    type = WarningType.LOW_VISIBILITY,
                    title = "Dense Fog Warning",
                    message = "Dense fog reducing visibility below 200 meters. Extremely dangerous driving conditions."
                )
            )
        } else if (minVisibilityInForecast <= 1000.0) {
            warnings.add(
                WeatherWarning(
                    severity = Severity.MODERATE,
                    type = WarningType.LOW_VISIBILITY,
                    title = "Low Visibility Advisory",
                    message = "Visibility expected to reduce below 1 km due to fog or mist."
                )
            )
        }

        // 5. Thunderstorm (using WMO Weather Codes)
        val currentCode = weather.current.weatherCode
        if (currentCode == 95 || currentCode == 96 || currentCode == 99) {
            warnings.add(
                WeatherWarning(
                    severity = Severity.SEVERE,
                    type = WarningType.THUNDERSTORM,
                    title = "Severe Thunderstorm Warning",
                    message = "Active thunderstorms with cloud-to-ground lightning and wind gusts observed in the area."
                )
            )
        }

        // 6. Extreme Heat rules
        val currentTemp = weather.current.temperature
        if (currentTemp >= 38.0) {
            warnings.add(
                WeatherWarning(
                    severity = Severity.SEVERE,
                    type = WarningType.EXTREME_HEAT,
                    title = "Extreme Heat Warning",
                    message = "Dangerously hot temperatures of ${String.format("%.1f", currentTemp)}°C observed. Limit outdoor activities and stay hydrated."
                )
            )
        }

        // 7. Extreme Cold rules
        if (currentTemp <= -15.0) {
            warnings.add(
                WeatherWarning(
                    severity = Severity.SEVERE,
                    type = WarningType.EXTREME_COLD,
                    title = "Extreme Cold Warning",
                    message = "Hazardous cold temperatures of ${String.format("%.1f", currentTemp)}°C reported. Protect pipes and pets."
                )
            )
        }

        // 8. High CAPE (Lightning/Instability metric)
        val hourlyCape = weather.hourly?.cape ?: emptyList()
        val maxCapeInForecast = hourlyCape.maxOrNull() ?: 0.0
        if (maxCapeInForecast >= 1500.0) {
            warnings.add(
                WeatherWarning(
                    severity = Severity.MODERATE,
                    type = WarningType.THUNDERSTORM,
                    title = "Severe Atmospheric Instability",
                    message = "High CAPE of ${String.format("%.0f", maxCapeInForecast)} J/kg signal convective storm developments later today."
                )
            )
        }

        // 9. River Flood Risk
        if (floodDischargePct != null && floodDischargePct >= 200.0) {
            warnings.add(
                WeatherWarning(
                    severity = Severity.SEVERE,
                    type = WarningType.FLOOD,
                    title = "River Flood Warning",
                    message = "River discharge modeling exceeds 200% of the long-term averages. High risk of overflowing waterways."
                )
            )
        }

        // Sort warnings by severity descending
        return warnings.sortedByDescending { it.severity.ordinal }
    }
}
