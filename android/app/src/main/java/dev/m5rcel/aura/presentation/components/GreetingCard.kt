package dev.m5rcel.aura.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun GreetingCard(userName: String) {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val greeting = when (hour) {
        in 0..11 -> "Good morning"
        in 12..17 -> "Good afternoon"
        else -> "Good evening"
    }

    val dateFormat = SimpleDateFormat("EEEE, d MMMM", Locale.getDefault())
    val currentDateText = dateFormat.format(Date())

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            // Sleek Left Accent Line (Device status focus indicator)
            Box(
                modifier = Modifier
                    .width(5.dp)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.primary)
            )
            
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(24.dp)
            ) {
                Text(
                    text = currentDateText.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    letterSpacing = androidx.compose.ui.unit.TextUnit.Companion.Unspecified
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "$greeting,\n$userName",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = androidx.compose.ui.unit.TextUnit.Companion.Unspecified
                )
            }
        }
    }
}
