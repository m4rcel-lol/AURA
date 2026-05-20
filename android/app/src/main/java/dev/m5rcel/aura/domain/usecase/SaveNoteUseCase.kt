package dev.m5rcel.aura.domain.usecase

import dev.m5rcel.aura.data.repository.NoteRepository
import dev.m5rcel.aura.domain.model.Note
import javax.inject.Inject

class SaveNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note): Long = repository.saveNote(note)
}
