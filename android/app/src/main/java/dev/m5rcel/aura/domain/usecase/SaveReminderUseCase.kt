package dev.m5rcel.aura.domain.usecase

import dev.m5rcel.aura.data.repository.ReminderRepository
import dev.m5rcel.aura.domain.model.Reminder
import javax.inject.Inject

class SaveReminderUseCase @Inject constructor(
    private val repository: ReminderRepository
) {
    suspend operator fun invoke(reminder: Reminder): Long = repository.saveReminder(reminder)
}
