package dev.m5rcel.aura.data.local.dao

import androidx.room.*
import dev.m5rcel.aura.data.local.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminders ORDER BY triggerAtMillis ASC")
    fun getAllReminders(): Flow<List<ReminderEntity>>

    @Query("SELECT * FROM reminders WHERE isCompleted = 0 ORDER BY triggerAtMillis ASC")
    fun getPendingReminders(): Flow<List<ReminderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: ReminderEntity): Long

    @Delete
    suspend fun deleteReminder(reminder: ReminderEntity)

    @Query("SELECT * FROM reminders WHERE id = :id LIMIT 1")
    suspend fun getReminderById(id: Long): ReminderEntity?

    @Query("UPDATE reminders SET isCompleted = :completed WHERE id = :id")
    suspend fun updateCompletionStatus(id: Long, completed: Boolean)
}
