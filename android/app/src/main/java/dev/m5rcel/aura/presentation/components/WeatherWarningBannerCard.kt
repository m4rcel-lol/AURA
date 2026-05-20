package dev.m5rcel.aura.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.m5rcel.aura.domain.model.WeatherWarning
import dev.m5rcel.aura.domain.model.Severity

@Composable
fun WeatherWarningBannerCard(warnings: List<WeatherWarning>) {
    val primaryWarning = warnings.firstOrNull() ?: return

    // Sleek border and text colors representing severity gracefully
    val strokeColor = when (primaryWarning.severity) {
        Severity.EXTREME -> Color(0xFFEF4444) // Intense Red
        Severity.SEVERE -> Color(0xFFF87171)  // Light Red
        Severity.MODERATE -> Color(0xFFFBBF24) // Elegant Gold
        Severity.INFO -> Color(0xFF60A5FA)     // Calm Blue
    }

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, strokeColor.copy(alpha = 0.8f), RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Weather Warning",
                tint = strokeColor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = primaryWarning.title.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = strokeColor,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = primaryWarning.message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
