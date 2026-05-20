package dev.m5rcel.aura.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey val id: Int = 1, // Only cache one current weather state
    val temperature: Double,
    val weatherCode: Int,
    val windSpeed: Double,
    val humidity: Int,
    val tempMax: Double,
    val tempMin: Double,
    val fetchedAt: Long
)
