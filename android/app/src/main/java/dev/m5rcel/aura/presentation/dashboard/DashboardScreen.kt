package dev.m5rcel.aura.presentation.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.m5rcel.aura.presentation.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var showAddNoteSheet by remember { mutableStateOf(false) }
    var showAddReminderSheet by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        text = "A U R A", 
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onBackground,
                        letterSpacing = androidx.compose.ui.unit.TextUnit.Companion.Unspecified
                    ) 
                },
                actions = {
                    IconButton(onClick = { /* Open Settings Bottom Sheet */ }) {
                        Icon(
                            imageVector = Icons.Default.Settings, 
                            contentDescription = "Settings",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddNoteSheet = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add, 
                    contentDescription = "Add Note",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // Spacer for smooth top transition
            item {
                Spacer(modifier = Modifier.height(4.dp))
            }

            // Widget 1: Time Aware Greeting Card
            item {
                GreetingCard(userName = state.userName)
            }

            // Widget 10: Weather Warning Banner (Conditional)
            if (state.weatherWarnings.isNotEmpty()) {
                item {
                    WeatherWarningBannerCard(warnings = state.weatherWarnings)
                }
            }

            // Widget 2: Weather Card
            item {
                WeatherCard(weatherData = state.weatherData)
            }

            // Widget 3: Recent Apps Row
            item {
                RecentAppsCard(recentApps = state.recentApps)
            }

            // Widget 4: Notes Grid
            item {
                NotesCard(
                    notes = state.notes,
                    onDeleteNote = { viewModel.deleteNote(it) }
                )
            }

            // Widget 9: Reminders List
            item {
                RemindersCard(
                    reminders = state.reminders,
                    onCompleteReminder = { id, done -> viewModel.completeReminder(id, done) },
                    onAddReminderClick = { showAddReminderSheet = true }
                )
            }

            // Widget 5: Calendar Info
            item {
                CalendarCard(events = state.calendarEvents)
            }

            // Widget 6: Quick toggles
            item {
                QuickTogglesCard(
                    isWifiOn = state.isWifiOn,
                    isBluetoothOn = state.isBluetoothOn,
                    isSilentModeOn = state.isSilentModeOn
                )
            }

            // Widget 7: Battery and Storage Status pair
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        BatteryCard(pct = state.batteryPct, isCharging = state.isBatteryCharging)
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        StorageCard(used = state.storageUsedGb, total = state.storageTotalGb)
                    }
                }
            }

            // Widget 8: Mini Track Music Player
            item {
                MusicPlayerCard()
            }

            // Bottom Spacer
            item {
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}
