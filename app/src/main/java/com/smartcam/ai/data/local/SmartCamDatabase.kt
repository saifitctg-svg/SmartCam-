package com.smartcam.ai.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.Update
import com.smartcam.ai.data.db.RoomConverters
import kotlinx.coroutines.flow.Flow

@Dao
interface CameraDao {
    @Query("SELECT * FROM cameras ORDER BY name ASC")
    fun getAllCameras(): Flow<List<CameraEntity>>

    @Query("SELECT * FROM cameras WHERE id = :id")
    suspend fun getCameraById(id: Long): CameraEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCamera(camera: CameraEntity): Long

    @Update
    suspend fun updateCamera(camera: CameraEntity)

    @Delete
    suspend fun deleteCamera(camera: CameraEntity)

    @Query("UPDATE cameras SET isOnline = :isOnline, lastSeen = :timestamp WHERE id = :id")
    suspend fun updateOnlineStatus(id: Long, isOnline: Boolean, timestamp: Long)
}

@Dao
interface ZoneDao {
    @Query("SELECT * FROM zones WHERE cameraId = :cameraId")
    fun getZonesForCamera(cameraId: Long): Flow<List<ZoneEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertZone(zone: ZoneEntity): Long

    @Delete
    suspend fun deleteZone(zone: ZoneEntity)
}

@Dao
interface EventDao {
    @Query("SELECT * FROM events ORDER BY timestamp DESC LIMIT 50")
    fun getRecentEvents(): Flow<List<SecurityEventEntity>>

    @Query("SELECT COUNT(*) FROM events WHERE timestamp >= :sinceTimestamp")
    fun getEventCountSince(sinceTimestamp: Long): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: SecurityEventEntity): Long

    @Query("DELETE FROM events WHERE timestamp < :cutoffTimestamp")
    suspend fun deleteEventsOlderThan(cutoffTimestamp: Long)
}

@Dao
interface ActivityDao {
    @Query("SELECT * FROM activity_sessions ORDER BY startTime DESC LIMIT 50")
    fun getRecentSessions(): Flow<List<ActivitySessionEntity>>

    @Insert
    suspend fun insertSession(session: ActivitySessionEntity): Long
}

@Dao
interface AuditDao {
    @Insert
    suspend fun insertLog(log: AuditLogEntity)

    @Query("SELECT * FROM audit_logs ORDER BY timestamp DESC LIMIT 100")
    fun getRecentLogs(): Flow<List<AuditLogEntity>>
}

@Dao
interface RuleDao {
    @Query("SELECT * FROM smart_rules WHERE isEnabled = 1")
    fun getActiveRules(): Flow<List<SmartRuleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRule(rule: SmartRuleEntity): Long
}

@Database(
    entities = [
        CameraEntity::class,
        ZoneEntity::class,
        SecurityEventEntity::class,
        ActivitySessionEntity::class,
        AuditLogEntity::class,
        SmartRuleEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoomConverters::class)
abstract class SmartCamDatabase : RoomDatabase() {
    abstract fun cameraDao(): CameraDao
    abstract fun zoneDao(): ZoneDao
    abstract fun eventDao(): EventDao
    abstract fun activityDao(): ActivityDao
    abstract fun auditDao(): AuditDao
    abstract fun ruleDao(): RuleDao
}