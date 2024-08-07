package com.caletasolutions.core.presentation.designsystem

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val DarkColorTheme = darkColorScheme(
    primary = SPCGreen,
    background = SPCBlack,
    surface = SPCDarkGray,
    secondary = SPCWhite,
    tertiary = SPCWhite,
    primaryContainer = SPCGreen30,
    onPrimary = SPCBlack,
    onBackground = SPCWhite,
    onSurface = SPCWhite,
    onSurfaceVariant = SPCGray,
    error = SPCDarkRed,
    errorContainer = SPCDarkRed5

)


@Composable
fun SecurePrintTheme(content: @Composable () -> Unit) {
    val colorScheme = DarkColorTheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }
    MaterialTheme(colorScheme = colorScheme, content = content, typography = Typography)
}