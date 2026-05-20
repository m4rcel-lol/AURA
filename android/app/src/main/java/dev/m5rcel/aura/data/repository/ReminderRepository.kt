package dev.m5rcel.aura.data.repository

import dev.m5rcel.aura.data.local.dao.ReminderDao
import dev.m5rcel.aura.data.local.entity.ReminderEntity
import dev.m5rcel.aura.domain.model.Reminder
import dev.m5rcel.aura.domain.model.ReminderTriggerMode
import dev.m5rcel.aura.domain.model.ReminderColor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReminderRepository @Inject constructor(
    private val reminderDao: ReminderDao
) {
    fun getAllReminders(): Flow<List<Reminder>> {
        return reminderDao.getAllReminders().map { entities ->
            entities.map { mapToDomain(it) }
        }
    }

    fun getPendingReminders(): Flow<List<Reminder>> {
        return reminderDao.getPendingReminders().map { entities ->
            entities.map { mapToDomain(it) }
        }
    }

    suspend fun saveReminder(reminder: Reminder): Long {
        val entity = ReminderEntity(
            id = reminder.id,
            title = reminder.title,
            description = reminder.description,
            triggerMode = reminder.triggerMode.name,
            triggerAtMillis = reminder.triggerAtMillis,
            relativeMillis = reminder.relativeMillis,
            isCompleted = reminder.isCompleted,
            isSnoozed = reminder.isSnoozed,
            snoozedUntilMillis = reminder.snoozedUntilMillis,
            createdAt = reminder.createdAt,
            color = reminder.color.name
        )
        return reminderDao.insertReminder(entity)
    }

    suspend fun deleteReminder(reminder: Reminder) {
        val entity = ReminderEntity(
            id = reminder.id,
            title = reminder.title,
            description = reminder.description,
            triggerMode = reminder.triggerMode.name,
            triggerAtMillis = reminder.triggerAtMillis,
            relativeMillis = reminder.relativeMillis,
            isCompleted = reminder.isCompleted,
            isSnoozed = reminder.isSnoozed,
            snoozedUntilMillis = reminder.snoozedUntilMillis,
            createdAt = reminder.createdAt,
            color = reminder.color.name
        )
        reminderDao.deleteReminder(entity)
    }

    suspend fun getReminderById(id: Long): Reminder? {
        val entity = reminderDao.getReminderById(id) ?: return null
        return mapToDomain(entity)
    }

    suspend fun updateCompletionStatus(id: Long, completed: Boolean) {
        reminderDao.updateCompletionStatus(id, completed)
    }

    private fun mapToDomain(entity: ReminderEntity): Reminder {
        return Reminder(
            id = entity.id,
            title = entity.title,
            description = entity.description,
            triggerMode = try { ReminderTriggerMode.valueOf(entity.triggerMode) } catch (e: Exception) { ReminderTriggerMode.EXACT_DATE_TIME },
            triggerAtMillis = entity.triggerAtMillis,
            relativeMillis = entity.relativeMillis,
            isCompleted = entity.isCompleted,
            isSnoozed = entity.isSnoozed,
            snoozedUntilMillis = entity.snoozedUntilMillis,
            createdAt = entity.createdAt,
            color = try { ReminderColor.valueOf(entity.color) } catch (e: Exception) { ReminderColor.DEFAULT }
        )
    }
}
