package com.smartcam.ai.ui.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
    onNavigateToCameras: () -> Unit,
    onNavigateToEvents: () -> Unit,
    onNavigateToActivity: () -> Unit,
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
                            text = "Transparent Monitoring Active. All detection sessions are locally audited.",
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
                        title = "Cameras",
                        value = "3 Active",
                        icon = Icons.Default.Videocam,
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToCameras
                    )
                    DashboardStatCard(
                        title = "Events Today",
                        value = "12",
                        icon = Icons.Default.Notifications,
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
                        title = "Presence Logs",
                        value = "4 Sessions",
                        icon = Icons.Default.Timer,
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToActivity
                    )
                    DashboardStatCard(
                        title = "Retention",
                        value = "7 Days",
                        icon = Icons.Default.Storage,
                        modifier = Modifier.weight(1f),
                        onClick = {}
                    )
                }
            }

            item {
                Text(
                    text = "Recent Security Events",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            items(getMockEvents()) { event ->
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

private fun getMockEvents() = listOf(
    SecurityEventEntity(
        id = 1,
        cameraId = 1,
        zoneId = 1,
        eventType = com.smartcam.ai.data.local.EventType.UNAUTHORIZED_ENTRY,
        timestamp = System.currentTimeMillis() - 1000 * 60 * 15,
        confidence = 0.92f,
        snapshotPath = null,
        description = "Unauthorized presence detected in Restricted Zone"
    ),
    SecurityEventEntity(
        id = 2,
        cameraId = 2,
        zoneId = 2,
        eventType = com.smartcam.ai.data.local.EventType.ZONE_ENTRY,
        timestamp = System.currentTimeMillis() - 1000 * 60 * 45,
        confidence = 0.95f,
        snapshotPath = null,
        description = "Presence in Reading Zone detected"
    )
)