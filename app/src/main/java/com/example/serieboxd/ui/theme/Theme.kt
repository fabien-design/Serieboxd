package com.example.serieboxd.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF1D9E75),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF0F6E56),
    onPrimaryContainer = Color(0xFFE1F5EE),
    secondary = Color(0xFFEF9F27),
    onSecondary = Color(0xFF412402),
    tertiary = Color(0xFFE24B4A),
    onTertiary = Color(0xFFFFFFFF),
    background = Color(0xFF1C1C1A),
    onBackground = Color(0xFFF1EFE8),
    surface = Color(0xFF2C2C2A),
    onSurface = Color(0xFFF1EFE8),
    surfaceVariant = Color(0xFF3C3C3A),
    onSurfaceVariant = Color(0xFFD3D1C7),
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1D9E75),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFE1F5EE),
    onPrimaryContainer = Color(0xFF0F6E56),
    secondary = Color(0xFFEF9F27),
    onSecondary = Color(0xFF412402),
    tertiary = Color(0xFFE24B4A),
    onTertiary = Color(0xFFFFFFFF),
    background = Color(0xFFF1EFE8),
    onBackground = Color(0xFF2C2C2A),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF2C2C2A),
    surfaceVariant = Color(0xFFE1F5EE),
    onSurfaceVariant = Color(0xFF0F6E56),
)

@Composable
fun SerieboxdTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
