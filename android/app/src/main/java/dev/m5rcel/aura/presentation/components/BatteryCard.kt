package dev.m5rcel.aura.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.BatteryUnknown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun BatteryCard(pct: Int, isCharging: Boolean) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "BATTERY STATUS",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = if (isCharging) Icons.Default.BatteryChargingFull else Icons.Default.BatteryUnknown,
                    contentDescription = "Battery Status",
                    tint = if (isCharging) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "$pct%",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (isCharging) "POWER SOURCE ACTIVE" else "DISCHARGING",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                fontWeight = FontWeight.Medium
            )
        }
    }
}
