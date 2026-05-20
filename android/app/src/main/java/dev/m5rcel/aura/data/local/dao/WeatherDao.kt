package dev.m5rcel.aura.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.m5rcel.aura.data.local.entity.WeatherEntity

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather WHERE id = 1 LIMIT 1")
    suspend fun getCachedWeather(): WeatherEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheWeather(weather: WeatherEntity)
}
