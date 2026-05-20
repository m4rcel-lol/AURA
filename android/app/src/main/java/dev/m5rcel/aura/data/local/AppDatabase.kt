package dev.m5rcel.aura.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.m5rcel.aura.data.local.dao.NoteDao
import dev.m5rcel.aura.data.local.dao.ReminderDao
import dev.m5rcel.aura.data.local.dao.WeatherDao
import dev.m5rcel.aura.data.local.entity.NoteEntity
import dev.m5rcel.aura.data.local.entity.ReminderEntity
import dev.m5rcel.aura.data.local.entity.WeatherEntity

@Database(
    entities = [NoteEntity::class, ReminderEntity::class, WeatherEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun reminderDao(): ReminderDao
    abstract fun weatherDao(): WeatherDao
}
