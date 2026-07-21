package com.sketchy.library

import androidx.compose.ui.graphics.Color

/**
 * A fully reskinnable color palette shared by every sketch and empty state.
 * Override any part to fit your app's own design system without touching
 * any drawing code.
 */
data class SketchyColors(
    val ink: Color = Color(0xFF0D1B2A),
    val inkSoft: Color = Color(0x990D1B2A),
    val inkFaint: Color = Color(0x330D1B2A),
    val accent: Color = Color(0xFFFFBC00),
    val accentSecondary: Color = Color(0xFF008091),
)
