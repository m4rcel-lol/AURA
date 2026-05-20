package dev.m5rcel.aura.data.repository

import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.PackageManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.m5rcel.aura.domain.model.AppUsageStat
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsageStatsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getRecentAppsUsage(): List<AppUsageStat> {
        val pm = context.packageManager
        val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as? UsageStatsManager
            ?: return getFallbackAppsList(pm)

        val endTime = System.currentTimeMillis()
        val startTime = endTime - 1000 * 60 * 60 * 24 * 7 // Last 7 days

        val stats = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_BEST,
            startTime,
            endTime
        )

        if (stats.isNullOrEmpty()) {
            return getFallbackAppsList(pm)
        }

        // Aggregate and sort
        val sortedStats = stats
            .filter { it.totalTimeInForeground > 0 }
            .sortedByDescending { it.totalTimeInForeground }
            .take(8)

        return sortedStats.map { stat ->
            val appLabel = try {
                val appInfo = pm.getApplicationInfo(stat.packageName, 0)
                pm.getApplicationLabel(appInfo).toString()
            } catch (e: Exception) {
                stat.packageName.substringAfterLast('.')
            }

            AppUsageStat(
                packageName = stat.packageName,
                appName = appLabel,
                totalTimeInForegroundMs = stat.totalTimeInForeground,
                lastTimeUsedMs = stat.lastTimeStamp
            )
        }
    }

    private fun getFallbackAppsList(pm: PackageManager): List<AppUsageStat> {
        // Return standard popular system packages as fallback
        val defaultApps = listOf(
            "com.android.chrome" to "Chrome",
            "com.google.android.youtube" to "YouTube",
            "com.google.android.apps.maps" to "Maps",
            "com.spotify.music" to "Spotify",
            "com.whatsapp" to "WhatsApp",
            "com.google.android.calendar" to "Calendar"
        )

        return defaultApps.mapIndexed { index, (pkg, name) ->
            AppUsageStat(
                packageName = pkg,
                appName = name,
                totalTimeInForegroundMs = 1000 * 60 * 60 * (6L - index), // Mock hours descending
                lastTimeUsedMs = System.currentTimeMillis() - (1000 * 60 * 30 * index) // minutes ago
            )
        }
    }
}
