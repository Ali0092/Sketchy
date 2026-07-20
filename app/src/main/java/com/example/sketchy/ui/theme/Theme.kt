package com.example.sketchy.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = SketchyGold,
    onPrimary = SketchyInk,
    secondary = SketchyTeal,
    onSecondary = SketchyOnDark,
    tertiary = SketchyGold,
    onTertiary = SketchyInk,
    background = SketchyInk,
    onBackground = SketchyOnDark,
    surface = SketchyInkLight,
    onSurface = SketchyOnDark,
    surfaceVariant = SketchyInkLight,
    onSurfaceVariant = SketchyOnDark,
)

private val LightColorScheme = lightColorScheme(
    primary = SketchyGold,
    onPrimary = SketchyInk,
    secondary = SketchyTeal,
    onSecondary = Color.White,
    tertiary = SketchyInk,
    onTertiary = SketchyCream,
    background = SketchyCream,
    onBackground = SketchyInk,
    surface = SketchySurface,
    onSurface = SketchyInk,
    surfaceVariant = SketchySurfaceVariant,
    onSurfaceVariant = SketchyInk,
)

/**
 * Brand theme built from the same ink/gold/teal palette as the illustrations
 * themselves. Dynamic (Material You) color is opt-in rather than default, so
 * the brand palette is what people see out of the box.
 */
@Composable
fun SketchyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
