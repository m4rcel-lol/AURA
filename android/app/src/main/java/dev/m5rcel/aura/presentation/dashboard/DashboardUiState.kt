package dev.m5rcel.aura.presentation.dashboard

import dev.m5rcel.aura.domain.model.*

data class DashboardUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val userName: String = "User",
    val weatherData: WeatherData? = null,
    val notes: List<Note> = emptyList(),
    val reminders: List<Reminder> = emptyList(),
    val recentApps: List<AppUsageStat> = emptyList(),
    val calendarEvents: List<CalendarEvent> = emptyList(),
    val weatherWarnings: List<WeatherWarning> = emptyList(),
    val isWifiOn: Boolean = true,
    val isBluetoothOn: Boolean = false,
    val isFlashlightOn: Boolean = false,
    val isSilentModeOn: Boolean = false,
    val batteryPct: Int = 100,
    val isBatteryCharging: Boolean = false,
    val storageUsedGb: Double = 45.5,
    val storageTotalGb: Double = 128.0
)
