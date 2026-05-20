package dev.m5rcel.aura.domain.usecase

import dev.m5rcel.aura.data.repository.ReminderRepository
import javax.inject.Inject

class MarkReminderFiredUseCase @Inject constructor(
    private val repository: ReminderRepository
) {
    suspend operator fun invoke(reminderId: Long) {
        repository.updateCompletionStatus(reminderId, completed = true)
    }
}
