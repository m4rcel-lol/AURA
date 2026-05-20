package dev.m5rcel.aura.domain.model

data class Note(
    val id: Long = 0,
    val title: String,
    val body: String,
    val color: NoteColor,
    val createdAt: Long,
    val updatedAt: Long,
    val isPinned: Boolean = false
)

enum class NoteColor {
    DEFAULT, RED, GREEN, BLUE, YELLOW, PURPLE
}
