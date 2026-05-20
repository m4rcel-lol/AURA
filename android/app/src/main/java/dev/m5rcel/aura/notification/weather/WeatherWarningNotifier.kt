package dev.m5rcel.aura.notification.weather

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.m5rcel.aura.MainActivity
import dev.m5rcel.aura.domain.model.Severity
import dev.m5rcel.aura.domain.model.WeatherWarning
import kotlinx.coroutines.flow.first
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherWarningNotifier @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStore: DataStore<Preferences>
) {
    private val sentWarningKeys = stringSetPreferencesKey("sent_warning_keys")

    suspend fun notifyIfNeeded(warnings: List<WeatherWarning>) {
        if (warnings.isEmpty()) return

        val storedKeys = dataStore.data.first()[sentWarningKeys] ?: emptySet()
        val mutableStoredKeys = storedKeys.toMutableSet()

        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH", Locale.US)
        val dateHour = dateFormat.format(Date())

        warnings.forEach { warning ->
            val key = "${warning.type.name}_${warning.severity.name}_$dateHour"
            
            // Check deduplication
            if (!mutableStoredKeys.contains(key)) {
                sendNotification(warning)
                mutableStoredKeys.add(key)
            }
        }

        // Keep local keys trimmed to avoid unlimited growth (prune values older than 24 hours can be mocked or solved simply by keeping current day keys)
        if (mutableStoredKeys.size > 100) {
            val list = mutableStoredKeys.toList()
            val trimmed = list.subList(list.size - 50, list.size).toSet()
            dataStore.edit { preferences ->
                preferences[sentWarningKeys] = trimmed
            }
        } else {
            dataStore.edit { preferences ->
                preferences[sentWarningKeys] = mutableStoredKeys
            }
        }
    }

    private fun sendNotification(warning: WeatherWarning) {
        val openAppIntent = Intent(context, MainActivity::class.java).apply {
            putExtra("weather_warning_type", warning.type.name)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val openAppPendingIntent = PendingIntent.getActivity(
            context,
            warning.type.ordinal,
            openAppIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = "aura_weather_warnings"
        val importance = when (warning.severity) {
            Severity.SEVERE, Severity.EXTREME -> NotificationCompat.PRIORITY_HIGH
            else -> NotificationCompat.PRIORITY_DEFAULT
        }
        
        val notificationColor = when (warning.severity) {
            Severity.EXTREME -> Color.RED
            Severity.SEVERE -> Color.parseColor("#FF6600") // Orange
            Severity.MODERATE -> Color.parseColor("#E0A96D") // Gold
            Severity.INFO -> Color.parseColor("#0061A4")
        }

        val designatorEmoji = when (warning.severity) {
            Severity.EXTREME -> "🚨 EXTREME WEATHER:"
            Severity.SEVERE -> "⚠️ SEVERE WEATHER:"
            else -> "🌦️ WEATHER ADVISORY:"
        }

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.stat_sys_warning) // standard android triangle icon
            .setContentTitle("$designatorEmoji ${warning.title}")
            .setContentText(warning.message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(warning.message))
            .setPriority(importance)
            .setCategory(NotificationCompat.CATEGORY_RECOMMENDATION)
            .setColorized(true)
            .setColor(notificationColor)
            .setAutoCancel(true)
            .setContentIntent(openAppPendingIntent)
            .addAction(android.R.drawable.ic_menu_info_details, "View Details", openAppPendingIntent)

        if (warning.severity == Severity.EXTREME) {
            builder.setVibrate(longArrayOf(0, 400, 200, 400, 200, 800))
        }

        try {
            val manager = NotificationManagerCompat.from(context)
            if (manager.areNotificationsEnabled()) {
                manager.notify(warning.type.ordinal, builder.build())
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}
