package dev.m5rcel.aura.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.m5rcel.aura.domain.model.*
import dev.m5rcel.aura.domain.usecase.*
import dev.m5rcel.aura.notification.reminder.ReminderScheduler
import dev.m5rcel.aura.notification.weather.WeatherWarningAnalyzer
import dev.m5rcel.aura.notification.weather.WeatherWarningNotifier
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val saveNoteUseCase: SaveNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val getRemindersUseCase: GetRemindersUseCase,
    private val saveReminderUseCase: SaveReminderUseCase,
    private val deleteReminderUseCase: DeleteReminderUseCase,
    private val markReminderFiredUseCase: MarkReminderFiredUseCase,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getRecentAppsUseCase: GetRecentAppsUseCase,
    private val getCalendarEventsUseCase: GetCalendarEventsUseCase,
    private val reminderScheduler: ReminderScheduler,
    private val warningAnalyzer: WeatherWarningAnalyzer,
    private val warningNotifier: WeatherWarningNotifier
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadNotes()
        loadReminders()
        loadRecentApps()
        loadCalendarEvents()
        refreshWeather(51.5074, -0.1278) // default coordinates (London)
    }

    private fun loadNotes() {
        viewModelScope.launch {
            getNotesUseCase().collect { noteList ->
                _uiState.update { it.copy(notes = noteList, isLoading = false) }
            }
        }
    }

    private fun loadReminders() {
        viewModelScope.launch {
            getRemindersUseCase().collect { reminderList ->
                _uiState.update { it.copy(reminders = reminderList) }
            }
        }
    }

    fun loadRecentApps() {
        viewModelScope.launch {
            val apps = getRecentAppsUseCase()
            _uiState.update { it.copy(recentApps = apps) }
        }
    }

    fun loadCalendarEvents() {
        viewModelScope.launch {
            val events = getCalendarEventsUseCase()
            _uiState.update { it.copy(calendarEvents = events) }
        }
    }

    fun refreshWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                val data = getWeatherUseCase(lat, lon)
                _uiState.update { it.copy(weatherData = data) }

                // Check active warnings from latest forecast
                val cachedResponse = getWeatherUseCase.getCached()
                // Simple analysis mock
                // In production, we evaluate raw API responses
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.localizedMessage) }
            }
        }
    }

    fun addNote(title: String, body: String, color: NoteColor, isPinned: Boolean) {
        viewModelScope.launch {
            val newNote = Note(
                title = title,
                body = body,
                color = color,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis(),
                isPinned = isPinned
            )
            saveNoteUseCase(newNote)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            deleteNoteUseCase(note)
        }
    }

    fun addReminder(title: String, desc: String, mode: ReminderTriggerMode, delayMs: Long, color: ReminderColor) {
        viewModelScope.launch {
            val triggerTime = System.currentTimeMillis() + delayMs
            val reminder = Reminder(
                title = title,
                description = desc,
                triggerMode = mode,
                triggerAtMillis = triggerTime,
                relativeMillis = delayMs,
                createdAt = System.currentTimeMillis(),
                color = color
            )
            val id = saveReminderUseCase(reminder)
            val scheduled = reminder.copy(id = id)
            reminderScheduler.schedule(scheduled)
        }
    }

    fun completeReminder(id: Long, completed: Boolean) {
        viewModelScope.launch {
            markReminderFiredUseCase(id)
            reminderScheduler.cancel(id)
        }
    }

    fun deleteReminder(reminder: Reminder) {
        viewModelScope.launch {
            deleteReminderUseCase(reminder)
            reminderScheduler.cancel(reminder.id)
        }
    }
}
