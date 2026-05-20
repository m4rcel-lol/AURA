package dev.m5rcel.aura.domain.model

data class AppUsageStat(
    val packageName: String,
    val appName: String,
    val totalTimeInForegroundMs: Long,
    val lastTimeUsedMs: Long
)
