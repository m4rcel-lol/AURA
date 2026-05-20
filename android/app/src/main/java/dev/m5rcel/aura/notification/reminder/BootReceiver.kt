package dev.m5rcel.aura.notification.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import dev.m5rcel.aura.domain.usecase.GetPendingRemindersUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {

    @Inject
    lateinit var getPendingRemindersUseCase: GetPendingRemindersUseCase

    @Inject
    lateinit var reminderScheduler: ReminderScheduler

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED &&
            intent.action != "android.intent.action.QUICKBOOT_POWERON"
        ) return

        val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        scope.launch {
            try {
                // Fetch the list snapshot of pending (non-completed) reminders
                val reminders = getPendingRemindersUseCase().first()
                val now = System.currentTimeMillis()
                reminders.forEach { reminder ->
                    if (reminder.triggerAtMillis > now) {
                        reminderScheduler.schedule(reminder)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                scope.cancel()
            }
        }
    }
}
