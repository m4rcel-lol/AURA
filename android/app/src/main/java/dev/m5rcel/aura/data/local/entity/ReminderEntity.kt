package dev.m5rcel.aura.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val triggerMode: String, // "EXACT_DATE_TIME" | "RELATIVE_DURATION"
    val triggerAtMillis: Long,
    val relativeMillis: Long?,
    val isCompleted: Boolean = false,
    val isSnoozed: Boolean = false,
    val snoozedUntilMillis: Long? = null,
    val createdAt: Long,
    val color: String = "DEFAULT"
)
