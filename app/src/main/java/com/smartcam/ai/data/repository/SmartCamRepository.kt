package com.smartcam.ai.data.repository

import com.smartcam.ai.data.local.EventType
import com.smartcam.ai.data.local.SecurityEventEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

interface SmartCamRepository {
    fun observeDashboard(): Flow<DashboardSnapshot>
}

data class DashboardSnapshot(
    val camerasOnline: Int,
    val camerasOffline: Int,
    val todaysEvents: Int,
    val criticalAlerts: Int,
    val peopleDetected: Int,
    val activityMinutes: Int,
    val recentEvents: List<SecurityEventEntity>,
    val isDemoData: Boolean
)

@Singleton
class DemoSmartCamRepository @Inject constructor() : SmartCamRepository {
    override fun observeDashboard(): Flow<DashboardSnapshot> = flowOf(
        DashboardSnapshot(
            camerasOnline = 2,
            camerasOffline = 1,
            todaysEvents = 12,
            criticalAlerts = 2,
            peopleDetected = 7,
            activityMinutes = 225,
            recentEvents = listOf(
                SecurityEventEntity(
                    cameraId = 1,
                    zoneId = 1,
                    eventType = EventType.UNAUTHORIZED_ENTRY,
                    confidence = 0.92f,
                    snapshotPath = null,
                    description = "Presence in Restricted Zone"
                ),
                SecurityEventEntity(
                    cameraId = 2,
                    zoneId = 2,
                    eventType = EventType.ZONE_ENTRY,
                    confidence = 0.95f,
                    snapshotPath = null,
                    description = "Presence in Reading Zone"
                )
            ),
            isDemoData = true
        )
    )
}
