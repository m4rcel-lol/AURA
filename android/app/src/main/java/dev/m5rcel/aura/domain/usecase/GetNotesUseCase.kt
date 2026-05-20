package dev.m5rcel.aura.domain.usecase

import dev.m5rcel.aura.data.repository.NoteRepository
import dev.m5rcel.aura.domain.model.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(): Flow<List<Note>> = repository.getAllNotes()
}
