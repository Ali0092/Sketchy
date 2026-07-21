package com.sketchy.library.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.sin

/** Shared ink palette and timing constants every scene is built from. */

internal val Ink = Color(0xFF0D1B2A)
internal val InkSoft = Color(0x990D1B2A)
internal val InkFaint = Color(0x330D1B2A)
internal val Accent = Color(0xFFFFBC00)
internal val AccentTeal = Color(0xFF008091)

/** The square size every scene is hand-drawn against; content scales to fit any other size. */
internal val DesignSize = 320.dp

internal const val TWO_PI = 2f * PI.toFloat()

/** Smooth −1..1 sine wave over the loop, optionally phase-shifted. */
internal fun wave(t: Float, offset: Float = 0f) = sin((t + offset) * TWO_PI)
