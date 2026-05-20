package dev.m5rcel.aura.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.VolumeMute
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickTogglesCard(
    isWifiOn: Boolean,
    isBluetoothOn: Boolean,
    isSilentModeOn: Boolean
) {
    var wifiState by remember { mutableStateOf(isWifiOn) }
    var bluetoothState by remember { mutableStateOf(isBluetoothOn) }
    var silentState by remember { mutableStateOf(isSilentModeOn) }

    Column {
        Text(
            text = "SYSTEM CONTROLS",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            item {
                FilterChip(
                    selected = wifiState,
                    onClick = { wifiState = !wifiState },
                    label = { Text("Wi-Fi".uppercase(), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold) },
                    leadingIcon = { Icon(Icons.Default.Wifi, contentDescription = "wifi logo", modifier = Modifier.size(16.dp)) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                        selectedLabelColor = MaterialTheme.colorScheme.primary,
                        selectedLeadingIconColor = MaterialTheme.colorScheme.primary,
                        containerColor = MaterialTheme.colorScheme.surface,
                        labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        leadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = wifiState,
                        borderColor = MaterialTheme.colorScheme.outline,
                        selectedBorderColor = MaterialTheme.colorScheme.primary,
                        borderWidth = 1.dp,
                        selectedBorderWidth = 1.dp
                    )
                )
            }
            item {
                FilterChip(
                    selected = bluetoothState,
                    onClick = { bluetoothState = !bluetoothState },
                    label = { Text("Bluetooth".uppercase(), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold) },
                    leadingIcon = { Icon(Icons.Default.Bluetooth, contentDescription = "bluetooth logo", modifier = Modifier.size(16.dp)) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                        selectedLabelColor = MaterialTheme.colorScheme.primary,
                        selectedLeadingIconColor = MaterialTheme.colorScheme.primary,
                        containerColor = MaterialTheme.colorScheme.surface,
                        labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        leadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = bluetoothState,
                        borderColor = MaterialTheme.colorScheme.outline,
                        selectedBorderColor = MaterialTheme.colorScheme.primary,
                        borderWidth = 1.dp,
                        selectedBorderWidth = 1.dp
                    )
                )
            }
            item {
                FilterChip(
                    selected = silentState,
                    onClick = { silentState = !silentState },
                    label = { Text("Silent Mode".uppercase(), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold) },
                    leadingIcon = { Icon(Icons.Default.VolumeMute, contentDescription = "volume mute logo", modifier = Modifier.size(16.dp)) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                        selectedLabelColor = MaterialTheme.colorScheme.primary,
                        selectedLeadingIconColor = MaterialTheme.colorScheme.primary,
                        containerColor = MaterialTheme.colorScheme.surface,
                        labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        leadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = silentState,
                        borderColor = MaterialTheme.colorScheme.outline,
                        selectedBorderColor = MaterialTheme.colorScheme.primary,
                        borderWidth = 1.dp,
                        selectedBorderWidth = 1.dp
                    )
                )
            }
        }
    }
}
