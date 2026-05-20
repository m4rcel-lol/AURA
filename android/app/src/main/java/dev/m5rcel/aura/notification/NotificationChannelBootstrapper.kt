package dev.m5rcel.aura.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationChannelBootstrapper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun createAllChannels() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Reminders channel
        NotificationChannel(
            "aura_reminders",
            "Reminders",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "AURA reminder alerts"
            enableVibration(true)
            vibrationPattern = longArrayOf(0, 250, 100, 250)
        }.also { manager.createNotificationChannel(it) }

        // Weather Warnings channel
        NotificationChannel(
            "aura_weather_warnings",
            "Weather Warnings",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Severe and notable weather alerts for your area"
            enableVibration(true)
            enableLights(true)
            lightColor = Color.RED
        }.also { manager.createNotificationChannel(it) }
    }
}
