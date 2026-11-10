package com.roadhelp.app.core.design
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
private val RoadHelpColors = lightColorScheme(
    primary = RoadTeal, onPrimary = White, secondary = RoadTealDark,
    background = Canvas, surface = White, onSurface = Ink, onBackground = Ink, error = EmergencyRed
)
@Composable
fun RoadHelpTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = RoadHelpColors, content = content)
}
