package com.sketchy.library.utils

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.sketchy.library.SketchyStyle

/**
 * Shared weather-drawing primitives — a cloud silhouette, a sun disc, a crescent moon, a lightning
 * bolt, falling rain and a rainbow — used by both the "Weather" empty states
 * (`emptystates/EmptyStatesWeather.kt`) and the rainy-day illustration (`illustrations/Weather.kt`),
 * so the same hand-drawn weather vocabulary reads consistently across both catalogs.
 */

/** A single fluffy, two-lobed cloud silhouette. No face — callers compose one on top if they need it. */
internal fun DrawScope.drawCloudSilhouette(
    cx: Float,
    cy: Float,
    w: Float,
    h: Float,
    colors: SketchyStyle,
    tint: Color = colors.paper,
    strokeWidth: Float = 2.4f
) {
    val hw = w / 2f
    val baseY = cy + h * 0.30f
    val topY = cy - h * 0.62f

    val cloud = Path().apply {
        moveTo(d(cx - hw * 0.86f), d(baseY))
        cubicTo(
            d(cx - hw * 1.04f), d(cy - h * 0.05f),
            d(cx - hw * 0.86f), d(cy - h * 0.5f),
            d(cx - hw * 0.42f), d(cy - h * 0.42f)
        )
        cubicTo(
            d(cx - hw * 0.34f), d(topY + h * 0.05f),
            d(cx - hw * 0.02f), d(topY - h * 0.06f),
            d(cx + hw * 0.14f), d(topY + h * 0.04f)
        )
        cubicTo(
            d(cx + hw * 0.36f), d(topY - h * 0.08f),
            d(cx + hw * 0.72f), d(topY + h * 0.14f),
            d(cx + hw * 0.66f), d(cy - h * 0.28f)
        )
        cubicTo(
            d(cx + hw * 0.98f), d(cy - h * 0.24f),
            d(cx + hw * 1.05f), d(cy + h * 0.08f),
            d(cx + hw * 0.82f), d(baseY)
        )
        cubicTo(
            d(cx + hw * 0.45f), d(baseY + h * 0.14f),
            d(cx - hw * 0.45f), d(baseY + h * 0.14f),
            d(cx - hw * 0.86f), d(baseY)
        )
        close()
    }
    inkShadow(cloud, colors.outlineShadow)
    paint(cloud, vBrush(topY, baseY, tint.lit(0.12f), tint), colors.ink, strokeWidth)
    sheen(cloud, pt(cx - hw * 0.5f, topY + h * 0.12f), pt(cx, cy), colors.paper.a(0.28f))
}

/** A warm sun disc with a soft radial bloom — the same glow+circle composition used across the
 *  library's existing sun moments (a desert horizon, a sunrise through a window), now reusable. */
internal fun DrawScope.drawSunDisc(
    cx: Float,
    cy: Float,
    r: Float,
    colors: SketchyStyle,
    warmth: Float = 1f
) {
    glow(cx, cy, r * 2.2f, colors.sun.a(0.55f * warmth))
    sketchCircle(pt(cx, cy), r, colors.touch(colors.sun, 0.85f), filled = true)
    sketchCircle(pt(cx, cy), r, colors.lineOnly, width = 2f)
}

/** A crescent moon — a real lune shape (two overlapping circle arcs), not a plain full-circle stand-in. */
internal fun DrawScope.drawCrescentMoon(
    cx: Float,
    cy: Float,
    r: Float,
    colors: SketchyStyle,
    phase: Float = 0.6f
) {
    val bite = r * phase
    val moon = Path().apply {
        arcTo(
            rect = Rect(pt(cx - r, cy - r), Size(d(r * 2f), d(r * 2f))),
            startAngleDegrees = -90f,
            sweepAngleDegrees = 180f,
            forceMoveTo = true
        )
        arcTo(
            rect = Rect(pt(cx - r + bite, cy - r), Size(d(r * 2f), d(r * 2f))),
            startAngleDegrees = 90f,
            sweepAngleDegrees = -180f,
            forceMoveTo = false
        )
        close()
    }
    paint(moon, colors.paper.lit(0.1f), colors.ink, 2.2f)
    sheen(moon, pt(cx - r * 0.5f, cy - r * 0.5f), pt(cx, cy + r * 0.4f), colors.sky.a(0.3f))
}

/** A single zig-zag lightning bolt, its brightness driven by [flashAlpha] (pass a `pulse(t, offset)`). */
internal fun DrawScope.drawLightningBolt(
    cx: Float,
    topY: Float,
    colors: SketchyStyle,
    flashAlpha: Float,
    length: Float = 70f
) {
    val bolt = Path().apply {
        moveTo(d(cx + length * 0.12f), d(topY))
        lineTo(d(cx - length * 0.18f), d(topY + length * 0.5f))
        lineTo(d(cx + length * 0.02f), d(topY + length * 0.5f))
        lineTo(d(cx - length * 0.16f), d(topY + length))
        lineTo(d(cx + length * 0.28f), d(topY + length * 0.38f))
        lineTo(d(cx + length * 0.06f), d(topY + length * 0.38f))
        close()
    }
    paint(bolt, colors.touch(colors.accent, 0.55f + 0.45f * flashAlpha), colors.ink, 2f)
}

/** A handful of falling rain streaks between [xFrom]/[xTo] and [topY]/[bottomY], each at its own
 *  jittered speed/length/x — generalizes the falling-rain loop first written for the rainy window. */
internal fun DrawScope.drawRainStreaks(
    xFrom: Float,
    xTo: Float,
    topY: Float,
    bottomY: Float,
    count: Int,
    t: Float,
    colors: SketchyStyle,
    seed: Int = 0,
    color: Color = colors.hint(colors.sky)
) {
    if (color.isHidden) return
    val span = bottomY - topY
    for (i in 0 until count) {
        val x = xFrom + (xTo - xFrom) * hash01(i + seed)
        val speed = 1.3f + 1.3f * hash01(i + seed + 40)
        val len = span * (0.12f + 0.1f * hash01(i + seed + 80))
        val p = loop(t * speed, hash01(i + seed + 120))
        val y = topY - len + (span + len) * p
        val streak = Path().apply {
            moveTo(d(x), d(y))
            lineTo(d(x - 2f), d(y + len))
        }
        brushStroke(
            streak,
            vBrush(y, y + len, color.a(0f), color.a(color.alpha * 0.65f)),
            width = 1f + 0.7f * hash01(i + seed + 160)
        )
    }
}

/** A nested-arc rainbow through the four accents — several accents together in one motif, same as
 *  the exposed-wire stripes in `EmptyStatesNetwork.kt`'s `drawNetworkUnsecureWifi`. */
internal fun DrawScope.drawRainbow(cx: Float, cy: Float, r: Float, colors: SketchyStyle, bands: Int = 4) {
    val hues = listOf(colors.accentRed, colors.accent, colors.accentGreen, colors.accentBlue)
    for (i in 0 until bands) {
        val bandR = r - i * (r * 0.13f)
        val arc = Path().apply {
            arcTo(
                rect = Rect(pt(cx - bandR, cy - bandR), Size(d(bandR * 2f), d(bandR * 2f))),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 180f,
                forceMoveTo = true
            )
        }
        drawPath(arc, color = hues[i % hues.size].a(0.85f), style = thin(3.2f))
    }
}
