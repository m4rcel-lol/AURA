package dev.m5rcel.aura.domain.usecase

import dev.m5rcel.aura.data.repository.ReminderRepository
import dev.m5rcel.aura.domain.model.Reminder
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRemindersUseCase @Inject constructor(
    private val repository: ReminderRepository
) {
    operator fun invoke(): Flow<List<Reminder>> = repository.getAllReminders()
}
