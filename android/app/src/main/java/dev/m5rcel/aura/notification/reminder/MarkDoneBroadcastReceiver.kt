package dev.m5rcel.aura.notification.reminder

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import dev.m5rcel.aura.domain.usecase.MarkReminderFiredUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MarkDoneBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var markReminderFiredUseCase: MarkReminderFiredUseCase

    override fun onReceive(context: Context, intent: Intent) {
        val reminderId = intent.getLongExtra("reminder_id", -1L)
        if (reminderId == -1L) return

        // Dismiss existing notification
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(reminderId.toInt())

        val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        scope.launch {
            markReminderFiredUseCase(reminderId)
            scope.cancel()
        }
    }
}
