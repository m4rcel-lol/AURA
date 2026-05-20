package dev.m5rcel.aura.data.repository

import android.content.ContentUris
import android.content.Context
import android.provider.CalendarContract
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.m5rcel.aura.domain.model.CalendarEvent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalendarRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getCalendarEvents(days: Int = 7): List<CalendarEvent> {
        val eventsList = mutableListOf<CalendarEvent>()
        val contentResolver = context.contentResolver

        val now = System.currentTimeMillis()
        val end = now + (1000 * 60 * 60 * 24 * days)

        val uri = CalendarContract.Instances.CONTENT_URI
        val builder = uri.buildUpon()
        ContentUris.appendId(builder, now)
        ContentUris.appendId(builder, end)

        val projection = arrayOf(
            CalendarContract.Instances.EVENT_ID,
            CalendarContract.Instances.TITLE,
            CalendarContract.Instances.DESCRIPTION,
            CalendarContract.Instances.BEGIN,
            CalendarContract.Instances.END,
            CalendarContract.Instances.ALL_DAY,
            CalendarContract.Instances.DISPLAY_COLOR
        )

        val selection = "${CalendarContract.Instances.VISIBLE} = 1"

        try {
            val cursor = contentResolver.query(
                builder.build(),
                projection,
                selection,
                null,
                "${CalendarContract.Instances.BEGIN} ASC"
            )

            cursor?.use {
                val idIdx = cursor.getColumnIndex(CalendarContract.Instances.EVENT_ID)
                val titleIdx = cursor.getColumnIndex(CalendarContract.Instances.TITLE)
                val descIdx = cursor.getColumnIndex(CalendarContract.Instances.DESCRIPTION)
                val beginIdx = cursor.getColumnIndex(CalendarContract.Instances.BEGIN)
                val endIdx = cursor.getColumnIndex(CalendarContract.Instances.END)
                val allDayIdx = cursor.getColumnIndex(CalendarContract.Instances.ALL_DAY)
                val colorIdx = cursor.getColumnIndex(CalendarContract.Instances.DISPLAY_COLOR)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idIdx)
                    val title = cursor.getString(titleIdx) ?: "Unnamed Event"
                    val desc = cursor.getString(descIdx)
                    val begin = cursor.getLong(beginIdx)
                    val endTime = cursor.getLong(endIdx)
                    val allDay = cursor.getInt(allDayIdx) == 1
                    val color = cursor.getInt(colorIdx)

                    eventsList.add(
                        CalendarEvent(
                            id = id,
                            title = title,
                            description = desc,
                            startTimeMillis = begin,
                            endTimeMillis = endTime,
                            isAllDay = allDay,
                            eventColor = color
                        )
                    )
                }
            }
        } catch (e: SecurityException) {
            // Permission denied: returns fallback / empty
            return getFallbackEvents()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return eventsList.ifEmpty { getFallbackEvents() }
    }

    private fun getFallbackEvents(): List<CalendarEvent> {
        val now = System.currentTimeMillis()
        return listOf(
            CalendarEvent(
                id = 1,
                title = "Team Daily Standup",
                description = "Daily synced check-in",
                startTimeMillis = now + 1000 * 60 * 60 * 2, // In 2 hours
                endTimeMillis = now + 1000 * 60 * 60 * 2 + 1000 * 60 * 30, // 30 min duration
                isAllDay = false,
                eventColor = -16738680 // Blue
            ),
            CalendarEvent(
                id = 2,
                title = "Material You Design Sync",
                description = "Review Aura layouts with designer",
                startTimeMillis = now + 1000 * 60 * 60 * 5, // In 5 hours
                endTimeMillis = now + 1000 * 60 * 60 * 6, // 1 hour duration
                isAllDay = false,
                eventColor = -65281 // Magenta
            ),
            CalendarEvent(
                id = 3,
                title = "Product Launch Session",
                description = "Big product launch party",
                startTimeMillis = now + 1000 * 60 * 60 * 24 * 2, // In 2 days
                endTimeMillis = now + 1000 * 60 * 60 * 24 * 2 + 1000 * 60 * 60 * 2,
                isAllDay = false,
                eventColor = -16711936 // Green
            )
        )
    }
}
