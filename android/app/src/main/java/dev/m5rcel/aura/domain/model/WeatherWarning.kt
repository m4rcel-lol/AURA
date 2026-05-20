package dev.m5rcel.aura.domain.model

data class WeatherWarning(
    val severity: Severity,
    val type: WarningType,
    val title: String,
    val message: String,
    val timestamp: Long = System.currentTimeMillis()
)

enum class Severity {
    INFO, MODERATE, SEVERE, EXTREME
}

enum class WarningType {
    HEAVY_RAIN, STORM, EXTREME_WIND, SNOWFALL, LOW_VISIBILITY,
    EXTREME_HEAT, EXTREME_COLD, THUNDERSTORM, FLOOD
}
