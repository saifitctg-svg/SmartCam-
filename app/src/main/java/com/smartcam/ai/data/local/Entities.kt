package com.smartcam.ai.data.local

import androidx.room.*

enum class ZoneType {
    RESTRICTED,
    ENTRY,
    EXIT,
    READING,
    TV,
    WORK_AREA,
    PLAY_AREA,
    CUSTOM
}

enum class EventType {
    PERSON_DETECTED,
    UNAUTHORIZED_ENTRY,
    ZONE_ENTRY,
    ZONE_EXIT,
    LINE_CROSSING,
    LOITERING,
    CAMERA_OFFLINE,
    CAMERA_ONLINE
}

@Entity(tableName = "cameras")
data class CameraEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val ipAddress: String,
    val port: Int = 554,
    val rtspUrl: String,
    val username: String,
    val encryptedPasswordIv: String,
    val encryptedPasswordData: String,
    val location: String,
    val connectionType: String,
    val isEnabled: Boolean = true,
    val isOnline: Boolean = false,
    val lastSeen: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "zones",
    foreignKeys = [
        ForeignKey(
            entity = CameraEntity::class,
            parentColumns = ["id"],
            childColumns = ["cameraId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("cameraId")]
)
data class ZoneEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val cameraId: Long,
    val name: String,
    val type: ZoneType,
    val coordinatesJson: String // Serialized List of normalized PointF (0.0 to 1.0)
)

@Entity(
    tableName = "events",
    foreignKeys = [
        ForeignKey(
            entity = CameraEntity::class,
            parentColumns = ["id"],
            childColumns = ["cameraId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("cameraId"), Index("timestamp")]
)
data class SecurityEventEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val cameraId: Long,
    val zoneId: Long?,
    val eventType: EventType,
    val timestamp: Long = System.currentTimeMillis(),
    val confidence: Float,
    val snapshotPath: String?,
    val isAlertSent: Boolean = false,
    val description: String
)

@Entity(tableName = "activity_sessions")
data class ActivitySessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val cameraId: Long,
    val zoneId: Long,
    val zoneName: String,
    val zoneType: ZoneType,
    val startTime: Long,
    val endTime: Long,
    val durationSeconds: Long
)

@Entity(tableName = "audit_logs")
data class AuditLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val userId: String,
    val action: String,
    val targetId: String
)

@Entity(tableName = "smart_rules")
data class SmartRuleEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val cameraId: Long,
    val zoneId: Long?,
    val eventType: EventType,
    val conditionType: String, // e.g. "ALWAYS", "NIGHT_ONLY", "DURATION_GT_5MIN"
    val pushAlert: Boolean = true,
    val emailAlert: Boolean = false,
    val isEnabled: Boolean = true
)