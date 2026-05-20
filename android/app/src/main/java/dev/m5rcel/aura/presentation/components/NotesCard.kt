package dev.m5rcel.aura.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.m5rcel.aura.domain.model.Note
import dev.m5rcel.aura.domain.model.NoteColor

@Composable
fun NotesCard(
    notes: List<Note>,
    onDeleteNote: (Note) -> Unit
) {
    Column {
        Text(
            text = "PERSISTENT NOTES",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        if (notes.isEmpty()) {
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.outlinedCardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No notes created. Tap + above to record.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(notes, key = { it.id }) { note ->
                    // Extract note color representation as a sleek tag dot
                    val dotColor = when (note.color) {
                        NoteColor.RED -> Color(0xFFEF4444)
                        NoteColor.GREEN -> Color(0xFF10B981)
                        NoteColor.BLUE -> Color(0xFF3B82F6)
                        NoteColor.YELLOW -> Color(0xFFF59E0B)
                        else -> MaterialTheme.colorScheme.secondary
                    }

                    OutlinedCard(
                        modifier = Modifier
                            .width(180.dp)
                            .height(136.dp)
                            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(16.dp)),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.outlinedCardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(8.dp)
                                                .clip(CircleShape)
                                                .background(dotColor)
                                        )
                                        Text(
                                            text = note.title,
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    if (note.isPinned) {
                                        Icon(
                                            imageVector = Icons.Default.PushPin,
                                            contentDescription = "Pinned",
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(12.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = note.body,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                                    maxLines = 3,
                                    overflow = TextOverflow.Ellipsis,
                                    lineHeight = androidx.compose.ui.unit.TextUnit.Companion.Unspecified
                                )
                            }
                            IconButton(
                                onClick = { onDeleteNote(note) },
                                modifier = Modifier
                                    .align(Alignment.End)
                                    .size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
