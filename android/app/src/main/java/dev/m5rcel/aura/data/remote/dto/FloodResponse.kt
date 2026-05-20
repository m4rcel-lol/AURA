package dev.m5rcel.aura.data.remote.dto

import com.google.gson.annotations.SerializedName

data class FloodResponse(
    val daily: FloodDailyData?
)

data class FloodDailyData(
    val time: List<String>,
    @SerializedName("river_discharge") val riverDischarge: List<Double>?
)
