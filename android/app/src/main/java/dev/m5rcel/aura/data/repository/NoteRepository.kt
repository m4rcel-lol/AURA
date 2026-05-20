package dev.m5rcel.aura.data.repository

import dev.m5rcel.aura.data.local.dao.NoteDao
import dev.m5rcel.aura.data.local.entity.NoteEntity
import dev.m5rcel.aura.domain.model.Note
import dev.m5rcel.aura.domain.model.NoteColor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {
    fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes().map { entities ->
            entities.map { entity ->
                Note(
                    id = entity.id,
                    title = entity.title,
                    body = entity.body,
                    color = try { NoteColor.valueOf(entity.color) } catch (e: Exception) { NoteColor.DEFAULT },
                    createdAt = entity.createdAt,
                    updatedAt = entity.updatedAt,
                    isPinned = entity.isPinned
                )
            }
        }
    }

    suspend fun saveNote(note: Note): Long {
        val entity = NoteEntity(
            id = note.id,
            title = note.title,
            body = note.body,
            color = note.color.name,
            createdAt = note.createdAt,
            updatedAt = note.updatedAt,
            isPinned = note.isPinned
        )
        return noteDao.insertNote(entity)
    }

    suspend fun deleteNote(note: Note) {
        val entity = NoteEntity(
            id = note.id,
            title = note.title,
            body = note.body,
            color = note.color.name,
            createdAt = note.createdAt,
            updatedAt = note.updatedAt,
            isPinned = note.isPinned
        )
        noteDao.deleteNote(entity)
    }
}
