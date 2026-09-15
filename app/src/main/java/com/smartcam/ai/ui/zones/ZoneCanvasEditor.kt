package com.smartcam.ai.ui.zones

import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.smartcam.ai.data.local.ZoneType

@Composable
fun ZoneCanvasEditor(
    zoneType: ZoneType,
    onSaveZone: (List<PointF>) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    val points = remember { mutableStateListOf<PointF>() }
    val zoneColor = when (zoneType) {
        ZoneType.RESTRICTED -> Color(0xFFFF3B30)
        ZoneType.WORK_AREA -> Color(0xFF007AFF)
        ZoneType.READING -> Color(0xFF34C759)
        ZoneType.TV -> Color(0xFFFF9500)
        else -> Color(0xFFAF52DE)
    }

    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = "Tap on screen to add polygon points for $zoneType zone (min 3 points)",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(16.dp)
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        // Normalize coordinates from 0.0 to 1.0
                        val normX = (offset.x / size.width).coerceIn(0f, 1f)
                        val normY = (offset.y / size.height).coerceIn(0f, 1f)
                        points.add(PointF(normX, normY))
                    }
                }
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasW = size.width
                val canvasH = size.height

                if (points.isNotEmpty()) {
                    val path = Path().apply {
                        moveTo(points[0].x * canvasW, points[0].y * canvasH)
                        for (i in 1 until points.size) {
                            lineTo(points[i].x * canvasW, points[i].y * canvasH)
                        }
                        if (points.size >= 3) {
                            close()
                        }
                    }

                    // Draw semi-transparent fill
                    if (points.size >= 3) {
                        drawPath(path = path, color = zoneColor.copy(alpha = 0.35f))
                    }

                    // Draw boundary stroke
                    drawPath(
                        path = path,
                        color = zoneColor,
                        style = Stroke(width = 3.dp.toPx())
                    )

                    // Draw point handles
                    points.forEach { pt ->
                        drawCircle(
                            color = Color.White,
                            radius = 6.dp.toPx(),
                            center = Offset(pt.x * canvasW, pt.y * canvasH)
                        )
                        drawCircle(
                            color = zoneColor,
                            radius = 4.dp.toPx(),
                            center = Offset(pt.x * canvasW, pt.y * canvasH)
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(
                onClick = { if (points.isNotEmpty()) points.removeAt(points.size - 1) },
                enabled = points.isNotEmpty()
            ) {
                Icon(Icons.Default.Undo, contentDescription = null)
                Spacer(Modifier.width(4.dp))
                Text("Undo")
            }

            OutlinedButton(onClick = onCancel) {
                Icon(Icons.Default.Clear, contentDescription = null)
                Spacer(Modifier.width(4.dp))
                Text("Cancel")
            }

            Button(
                onClick = { onSaveZone(points.toList()) },
                enabled = points.size >= 3
            ) {
                Icon(Icons.Default.Check, contentDescription = null)
                Spacer(Modifier.width(4.dp))
                Text("Save Zone")
            }
        }
    }
}