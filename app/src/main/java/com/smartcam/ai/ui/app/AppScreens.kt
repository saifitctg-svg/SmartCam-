package com.smartcam.ai.ui.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smartcam.ai.data.local.EventType
import com.smartcam.ai.data.local.SecurityEventEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraScreen(onBack: () -> Unit, onAddCamera: () -> Unit) {
    val cameras = remember {
        listOf("Front Door" to "Online", "Living Room" to "Offline", "Office" to "Online")
    }
    AppScaffold("Cameras", onBack) {
        item {
            Button(onClick = onAddCamera, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(Modifier.padding(4.dp))
                Text("Add camera")
            }
        }
        items(cameras) { (name, status) ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null)
                    Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
                        Text(name, style = MaterialTheme.typography.titleMedium)
                        Text(status, color = if (status == "Online") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error)
                    }
                    OutlinedButton(onClick = {}) { Text("Test") }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCameraScreen(onBack: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var rtspUrl by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    AppScaffold("Add Camera", onBack) {
        item {
            Text("Camera credentials are stored using Android Keystore encryption.", style = MaterialTheme.typography.bodyMedium)
        }
        item { OutlinedTextField(name, { name = it }, Modifier.fillMaxWidth(), label = { Text("Camera name") }) }
        item { OutlinedTextField(rtspUrl, { rtspUrl = it }, Modifier.fillMaxWidth(), label = { Text("RTSP URL") }) }
        item { OutlinedTextField(username, { username = it }, Modifier.fillMaxWidth(), label = { Text("Username") }) }
        item {
            Button(onClick = onBack, enabled = name.isNotBlank() && rtspUrl.startsWith("rtsp://"), modifier = Modifier.fillMaxWidth()) {
                Text("Save camera")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsScreen(onBack: () -> Unit) {
    val events = remember { listOf(
        SecurityEventEntity(eventType = EventType.UNAUTHORIZED_ENTRY, cameraId = 1, zoneId = 1, confidence = .92f, snapshotPath = null, description = "Presence in Restricted Zone"),
        SecurityEventEntity(eventType = EventType.ZONE_ENTRY, cameraId = 2, zoneId = 2, confidence = .95f, snapshotPath = null, description = "Presence in Reading Zone")
    ) }
    AppScaffold("Security Events", onBack) {
        items(events) { event ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text(event.eventType.name.replace('_', ' '), style = MaterialTheme.typography.titleMedium)
                    Text(event.description)
                    Text("Confidence ${(event.confidence * 100).toInt()}%", style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityScreen(onBack: () -> Unit) {
    AppScaffold("Activity", onBack) {
        item { SummaryCard("Presence in Reading Zone", "45 minutes") }
        item { SummaryCard("Work Area Presence", "3 hours") }
        item { SummaryCard("Presence in TV Zone", "45 minutes") }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyScreen(onBack: () -> Unit) {
    var monitoringNotice by remember { mutableStateOf(true) }
    var localProcessing by remember { mutableStateOf(true) }
    AppScaffold("Privacy & Consent", onBack) {
        item { SummaryCard("Consent-based monitoring", "No hidden surveillance or facial recognition is enabled.") }
        item { SettingToggle("Monitoring notice", monitoringNotice) { monitoringNotice = it } }
        item { SettingToggle("Prefer local processing", localProcessing) { localProcessing = it } }
        item { SummaryCard("Data retention", "Events are retained for 7 days. Use Storage to clean up older data.") }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeatureStatusScreen(title: String, onBack: () -> Unit) {
    AppScaffold(title, onBack) {
        item { SummaryCard(title, "This feature is ready for configuration. Camera connectivity and AI processing are isolated behind interfaces.") }
        item { SummaryCard("Security note", "The MVP uses mock data and does not claim identity recognition or definitive human activities.") }
    }
}

@Composable
private fun SummaryCard(title: String, body: String) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(6.dp))
            Text(body)
        }
    }
}

@Composable
private fun SettingToggle(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Lock, contentDescription = null)
            Text(title, Modifier.weight(1f).padding(start = 12.dp))
            Switch(checked, onCheckedChange)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppScaffold(title: String, onBack: () -> Unit, content: androidx.compose.foundation.lazy.LazyListScope.() -> Unit) {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(title) },
            navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Back") } }
        )
    }) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            content = content
        )
    }
}