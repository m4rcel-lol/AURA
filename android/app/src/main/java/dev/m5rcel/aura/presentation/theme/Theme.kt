package dev.m5rcel.aura.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = SleekPrimary,
    secondary = SleekSecondary,
    tertiary = SleekAccent,
    background = SleekBackground,
    surface = SleekCardBg,
    onBackground = SleekWhiteText,
    onSurface = SleekWhiteText,
    surfaceVariant = SleekCardBg,
    onSurfaceVariant = SleekSecondary,
    outline = SleekBorder
)

private val LightColorScheme = lightColorScheme(
    primary = SleekPrimary,
    secondary = SleekSecondary,
    tertiary = SleekAccent,
    background = Color(0xFFFAFAFC),
    surface = Color(0xFFF1F1F5),
    onBackground = Color(0xFF0C0C0F),
    onSurface = Color(0xFF0C0C0F),
    outline = Color(0xFFE5E5EB)
)

@Composable
fun AuraTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
