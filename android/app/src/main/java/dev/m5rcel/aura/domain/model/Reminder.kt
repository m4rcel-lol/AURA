package dev.m5rcel.aura.domain.model

data class Reminder(
    val id: Long = 0,
    val title: String,
    val description: String,
    val triggerMode: ReminderTriggerMode,
    val triggerAtMillis: Long,
    val relativeMillis: Long?,
    val isCompleted: Boolean = false,
    val isSnoozed: Boolean = false,
    val snoozedUntilMillis: Long? = null,
    val createdAt: Long,
    val color: ReminderColor = ReminderColor.DEFAULT
)

enum class ReminderTriggerMode {
    EXACT_DATE_TIME, RELATIVE_DURATION
}

enum class ReminderColor {
    DEFAULT, RED, GREEN, BLUE, YELLOW, PURPLE
}
