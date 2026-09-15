package com.smartcam.ai.data.db

import androidx.room.TypeConverter
import com.smartcam.ai.data.local.EventType
import com.smartcam.ai.data.local.ZoneType

class RoomConverters {
    @TypeConverter
    fun fromZoneType(value: ZoneType?): String? {
        return value?.name
    }

    @TypeConverter
    fun toZoneType(value: String?): ZoneType? {
        return value?.let {
            runCatching { ZoneType.valueOf(it) }.getOrNull()
        }
    }

    @TypeConverter
    fun fromEventType(value: EventType?): String? {
        return value?.name
    }

    @TypeConverter
    fun toEventType(value: String?): EventType? {
        return value?.let {
            runCatching { EventType.valueOf(it) }.getOrNull()
        }
    }
}