package com.smartcam.ai.data.db

import androidx.room.TypeConverter
import com.smartcam.ai.data.local.EventType
import com.smartcam.ai.data.local.ZoneType

class RoomConverters {
    @TypeConverter
    fun fromZoneType(value: ZoneType): String = value.name

    @TypeConverter
    fun toZoneType(value: String): ZoneType = ZoneType.valueOf(value)

    @TypeConverter
    fun fromEventType(value: EventType): String = value.name

    @TypeConverter
    fun toEventType(value: String): EventType = EventType.valueOf(value)
}