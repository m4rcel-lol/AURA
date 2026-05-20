package dev.m5rcel.aura.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val body: String,
    val color: String,
    val createdAt: Long,
    val updatedAt: Long,
    val isPinned: Boolean = false
)
