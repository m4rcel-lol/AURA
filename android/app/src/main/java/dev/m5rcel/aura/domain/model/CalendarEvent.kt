package dev.m5rcel.aura.domain.model

data class CalendarEvent(
    val id: Long,
    val title: String,
    val description: String?,
    val startTimeMillis: Long,
    val endTimeMillis: Long,
    val isAllDay: Boolean,
    val eventColor: Int?
)
