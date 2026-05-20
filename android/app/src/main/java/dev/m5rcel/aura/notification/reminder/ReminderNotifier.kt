package dev.m5rcel.aura.notification.reminder

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.m5rcel.aura.MainActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReminderNotifier @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun notify(reminderId: Long, title: String, description: String) {
        val openAppIntent = Intent(context, MainActivity::class.java).apply {
            putExtra("reminder_id", reminderId)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val openAppPendingIntent = PendingIntent.getActivity(
            context,
            reminderId.toInt(),
            openAppIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val snoozeIntent = Intent(context, SnoozeBroadcastReceiver::class.java).apply {
            putExtra("reminder_id", reminderId)
            putExtra("reminder_title", title)
            putExtra("reminder_description", description)
        }
        val snoozePendingIntent = PendingIntent.getBroadcast(
            context,
            reminderId.toInt(),
            snoozeIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val doneIntent = Intent(context, MarkDoneBroadcastReceiver::class.java).apply {
            putExtra("reminder_id", reminderId)
        }
        val donePendingIntent = PendingIntent.getBroadcast(
            context,
            reminderId.toInt(),
            doneIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val builder = NotificationCompat.Builder(context, "aura_reminders")
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm) // Using system drawables as placeholder for project
            .setContentTitle(title)
            .setContentText(description.ifEmpty { "Tap to open AURA" })
            .setStyle(NotificationCompat.BigTextStyle().bigText(description.ifEmpty { "Tap to open AURA" }))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setAutoCancel(true)
            .setSound(soundUri)
            .setContentIntent(openAppPendingIntent)
            .addAction(android.R.drawable.ic_lock_idle_alarm, "Snooze 10m", snoozePendingIntent)
            .addAction(android.R.drawable.checkbox_on_background, "Mark Done", donePendingIntent)

        try {
            val manager = NotificationManagerCompat.from(context)
            if (manager.areNotificationsEnabled()) {
                manager.notify(reminderId.toInt(), builder.build())
            }
        } catch (e: SecurityException) {
            e.printStackTrace() // Handle no POST_NOTIFICATIONS on Android 13+
        }
    }
}
