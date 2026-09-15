package com.smartcam.ai.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF175A7A),
    onPrimary = Color.White,
    secondary = Color(0xFF4D635F),
    tertiary = Color(0xFF5B5F8A),
    error = Color(0xFFBA1A1A)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF8DCDFF),
    secondary = Color(0xFFB1CCC5),
    tertiary = Color(0xFFC2C4F4),
    error = Color(0xFFFFB4AB)
)

@Composable
fun SmartCamTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (useDarkTheme) DarkColors else LightColors,
        content = content
    )
}
