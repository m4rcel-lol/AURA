package dev.m5rcel.aura.notification.reminder

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import dev.m5rcel.aura.domain.model.Reminder
import dev.m5rcel.aura.domain.model.ReminderTriggerMode
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SnoozeBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var reminderScheduler: ReminderScheduler

    override fun onReceive(context: Context, intent: Intent) {
        val reminderId = intent.getLongExtra("reminder_id", -1L)
        val title = intent.getStringExtra("reminder_title") ?: "Reminder"
        val description = intent.getStringExtra("reminder_description") ?: ""
        if (reminderId == -1L) return

        // Dismiss existing notification
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(reminderId.toInt())

        // Snooze for 10 minutes (10 * 60 * 1000)
        val snoozeMillis = 10 * 60 * 1000L
        val triggerAt = System.currentTimeMillis() + snoozeMillis

        val snoozedReminder = Reminder(
            id = reminderId,
            title = title,
            description = description,
            triggerMode = ReminderTriggerMode.EXACT_DATE_TIME,
            triggerAtMillis = triggerAt,
            relativeMillis = snoozeMillis,
            isCompleted = false,
            isSnoozed = true,
            snoozedUntilMillis = triggerAt,
            createdAt = System.currentTimeMillis()
        )

        reminderScheduler.reschedule(snoozedReminder)
    }
}
