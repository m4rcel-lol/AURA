package dev.m5rcel.aura.domain.usecase

import dev.m5rcel.aura.data.repository.CalendarRepository
import dev.m5rcel.aura.domain.model.CalendarEvent
import javax.inject.Inject

class GetCalendarEventsUseCase @Inject constructor(
    private val repository: CalendarRepository
) {
    operator fun invoke(days: Int = 7): List<CalendarEvent> = repository.getCalendarEvents(days)
}
