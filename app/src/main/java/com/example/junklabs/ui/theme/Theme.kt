package com.example.junklabs.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    secondary = LightBlue,
    tertiary = AccentBlue,
    background = BackgroundBlue,
    onPrimary = White,
    onSecondary = White,
    onTertiary = White,
    onBackground = TextColor,
    surface = White,
    onSurface = TextColor
)

@Composable
fun JunkLabsTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}