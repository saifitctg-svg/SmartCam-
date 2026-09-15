package com.smartcam.ai.engine

import android.graphics.PointF
import android.graphics.RectF
import com.smartcam.ai.data.local.ZoneEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

data class TrackedPerson(
    val id: Int,
    val boundingBox: RectF, // Normalized 0.0 to 1.0 coordinates
    val confidence: Float
)

interface DetectionEngine {
    fun startDetectionStream(cameraId: Long, zones: List<ZoneEntity>): Flow<List<TrackedPerson>>
    fun isPointInPolygon(point: PointF, polygon: List<PointF>): Boolean
}

@Singleton
class MockDetectionEngine @Inject constructor() : DetectionEngine {

    // Simulates live AI model running person tracking
    override fun startDetectionStream(cameraId: Long, zones: List<ZoneEntity>): Flow<List<TrackedPerson>> = flow {
        var x = 0.2f
        var y = 0.3f
        var stepX = 0.02f
        var stepY = 0.015f

        while (true) {
            delay(1000) // 1 FPS AI inference cycle for low power consumption

            // Move mock person around the frame
            x += stepX
            y += stepY
            if (x !in 0.1f..0.8f) stepX = -stepX
            if (y !in 0.1f..0.8f) stepY = -stepY

            val mockPerson = TrackedPerson(
                id = 101,
                boundingBox = RectF(x, y, x + 0.15f, y + 0.3f),
                confidence = 0.88f + Random.nextFloat() * 0.1f
            )

            emit(listOf(mockPerson))
        }
    }

    // Ray-casting algorithm for normalized polygon zone hit-testing
    override fun isPointInPolygon(point: PointF, polygon: List<PointF>): Boolean {
        var intersectCount = 0
        val n = polygon.size
        for (i in 0 until n) {
            val p1 = polygon[i]
            val p2 = polygon[(i + 1) % n]
            if ((point.y > minOf(p1.y, p2.y)) && (point.y <= maxOf(p1.y, p2.y)) &&
                (point.x <= maxOf(p1.x, p2.x)) && (p1.y != p2.y)
            ) {
                val xInters = (point.y - p1.y) * (p2.x - p1.x) / (p2.y - p1.y) + p1.x
                if (p1.x == p2.x || point.x <= xInters) {
                    intersectCount++
                }
            }
        }
        return intersectCount % 2 != 0
    }
}