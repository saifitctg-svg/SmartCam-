package com.smartcam.ai.ui.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.smartcam.ai.data.local.SecurityEventEntity
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    uiState: DashboardUiState,
    onNavigateToCameras: () -> Unit,
    onNavigateToEvents: () -> Unit,
    onNavigateToActivity: () -> Unit,
    onNavigateToReports: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToPrivacy: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("SmartCam AI Dashboard") },
                actions = {
                    IconButton(onClick = onNavigateToPrivacy) {
                        Icon(Icons.Default.Security, contentDescription = "Privacy & Consent")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Info, contentDescription = null)
                        Spacer(Modifier.width(12.dp))
                        Text(
                            text = if (uiState.isDemoData) {
                                "Demo data active. No cameras are connected and no surveillance is hidden."
                            } else {
                                "Transparent monitoring active. Detection sessions are locally audited."
                            },
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    DashboardStatCard(
                        title = "Cameras Online",
                        value = "${uiState.camerasOnline}",
                        icon = Icons.Default.Videocam,
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToCameras
                    )
                    DashboardStatCard(
                        title = "Cameras Offline",
                        value = "${uiState.camerasOffline}",
                        icon = Icons.Default.Warning,
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToCameras
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    DashboardStatCard(
                        title = "Today's Events",
                        value = "${uiState.todaysEvents}",
                        icon = Icons.Default.Notifications,
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToEvents
                    )
                    DashboardStatCard(
                        title = "Critical Alerts",
                        value = "${uiState.criticalAlerts}",
                        icon = Icons.Default.Error,
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToEvents
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    DashboardStatCard(
                        title = "People Detected",
                        value = "${uiState.peopleDetected}",
                        icon = Icons.Default.Person,
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToEvents
                    )
                    DashboardStatCard(
                        title = "Today's Activity",
                        value = "${uiState.activityMinutes / 60}h ${uiState.activityMinutes % 60}m",
                        icon = Icons.Default.Timer,
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToActivity
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(onClick = onNavigateToReports, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.Assessment, contentDescription = null)
                        Spacer(Modifier.width(6.dp))
                        Text("Reports")
                    }
                    OutlinedButton(onClick = onNavigateToSettings, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.Settings, contentDescription = null)
                        Spacer(Modifier.width(6.dp))
                        Text("Settings")
                    }
                }
            }

            item {
                Text(
                    text = "Recent Security Events",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            items(uiState.recentEvents) { event ->
                EventListItem(event)
            }
        }
    }
}

@Composable
fun DashboardStatCard(
    title: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(8.dp))
            Text(value, style = MaterialTheme.typography.headlineSmall)
            Text(title, style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
fun EventListItem(event: SecurityEventEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Warning,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error
            )
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(event.eventType.name.replace("_", " "), style = MaterialTheme.typography.titleSmall)
                Text(event.description, style = MaterialTheme.typography.bodySmall)
            }
            Text(
                text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(event.timestamp)),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}
