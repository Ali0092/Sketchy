package com.sketchy.library.emptystates

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import com.sketchy.library.SketchyColors
import com.sketchy.library.utils.*

// ─── No Internet ─────────────────────────────────────────────────────────────
//   Nested wifi arcs searching for a signal, with a small alert badge.

internal fun DrawScope.drawNoInternet(t: Float, colors: SketchyColors) {
    val cx = 160f
    val domeY = 208f
    val radii = listOf(20f, 38f, 56f)
    radii.forEachIndexed { i, r ->
        val k = (1f + wave(t, i * 0.22f)) / 2f
        val arc = Path().apply {
            arcTo(
                rect = Rect(pt(cx - r, domeY - r), Size(d(r * 2), d(r * 2))),
                startAngleDegrees = 205f,
                sweepAngleDegrees = 130f,
                forceMoveTo = true
            )
        }
        drawPath(arc, color = colors.ink.copy(alpha = 0.35f + 0.65f * k), style = bold(2.4f))
    }
    sketchCircle(pt(cx, domeY + 14f), 4f, colors.ink, filled = true)

    // alert badge, pulsing
    val badgePivot = pt(226f, 96f)
    val badgeScale = 1f + 0.15f * (1f + wave(t, 0.4f)) / 2f
    withTransform({ scale(scaleX = badgeScale, scaleY = badgeScale, pivot = badgePivot) }) {
        sketchCircle(badgePivot, 16f, colors.accent, width = 2.4f)
        sketchLine(pt(226f, 89f), pt(226f, 98f), colors.accent, 2.2f)
        sketchCircle(pt(226f, 103f), 1.6f, colors.accent, filled = true)
    }

    twinkle(90f, 130f, 3f, t, 0.15f, colors.inkSoft)
    twinkle(232f, 160f, 3f, t, 0.6f, colors.inkSoft)
    groundLine(250f, colors.inkFaint)
}

// ─── Server Error ────────────────────────────────────────────────────────────
//   A rack of servers with a crack running through it and a warning triangle.

internal fun DrawScope.drawServerError(t: Float, colors: SketchyColors) {
    val left = 96f
    val right = 224f
    for (row in 0..2) {
        val y = 120f + row * 40f
        val box = Path().apply {
            moveTo(d(left), d(y))
            lineTo(d(right), d(y))
            lineTo(d(right), d(y + 30f))
            lineTo(d(left), d(y + 30f))
            close()
        }
        stroke(box, colors.ink, 2.2f)
        val ledColor = if (row == 1) colors.accent.copy(
            alpha = 0.4f + 0.6f * (1f + wave(t, 0.3f)) / 2f
        ) else colors.accentSecondary
        sketchCircle(pt(right - 14f, y + 15f), 3f, ledColor, filled = true)
        sketchLine(pt(left + 12f, y + 15f), pt(right - 30f, y + 15f), colors.inkFaint, 1.6f)
    }

    val crack = Path().apply {
        moveTo(d(150f), d(112f))
        lineTo(d(172f), d(150f))
        lineTo(d(152f), d(168f))
        lineTo(d(178f), d(210f))
    }
    val flicker = 0.5f + 0.5f * (1f + wave(t, 0f)) / 2f
    drawPath(crack, color = colors.accent.copy(alpha = flicker), style = bold(2.6f))

    // warning triangle
    val warn = Path().apply {
        moveTo(d(160f), d(58f))
        lineTo(d(178f), d(90f))
        lineTo(d(142f), d(90f))
        close()
    }
    stroke(warn, colors.ink, 2.2f)
    sketchLine(pt(160f, 68f), pt(160f, 78f), colors.accent, 2.2f)
    sketchCircle(pt(160f, 84f), 1.6f, colors.accent, filled = true)

    twinkle(70f, 100f, 3f, t, 0.5f, colors.inkSoft)
    groundLine(230f, colors.inkFaint)
}

// ─── Sync Failed ──────────────────────────────────────────────────────────────
//   A refresh loop that keeps struggling instead of spinning free, with an
//   "x" at its center.

internal fun DrawScope.drawSyncFailed(t: Float, colors: SketchyColors) {
    val cx = 160f
    val cy = 150f
    val r = 56f
    val struggle = 18f * wave(t, 0f)
    val pivot = pt(cx, cy)
    withTransform({ rotate(degrees = struggle, pivot = pivot) }) {
        val arcTop = Path().apply {
            arcTo(
                rect = Rect(pt(cx - r, cy - r), Size(d(r * 2), d(r * 2))),
                startAngleDegrees = 200f,
                sweepAngleDegrees = 150f,
                forceMoveTo = true
            )
        }
        stroke(arcTop, colors.ink, 2.6f)
        val arcBottom = Path().apply {
            arcTo(
                rect = Rect(pt(cx - r, cy - r), Size(d(r * 2), d(r * 2))),
                startAngleDegrees = 20f,
                sweepAngleDegrees = 150f,
                forceMoveTo = true
            )
        }
        stroke(arcBottom, colors.ink, 2.6f)
        // arrowheads
        val headA = Path().apply {
            moveTo(d(cx - r + 4f), d(cy - 16f))
            lineTo(d(cx - r - 6f), d(cy - 8f))
            lineTo(d(cx - r - 2f), d(cy + 4f))
        }
        stroke(headA, colors.ink, 2.2f)
        val headB = Path().apply {
            moveTo(d(cx + r - 4f), d(cy + 16f))
            lineTo(d(cx + r + 6f), d(cy + 8f))
            lineTo(d(cx + r + 2f), d(cy - 4f))
        }
        stroke(headB, colors.ink, 2.2f)
    }

    val xAlpha = 0.5f + 0.5f * (1f + wave(t, 0.25f)) / 2f
    sketchLine(pt(cx - 12f, cy - 12f), pt(cx + 12f, cy + 12f), colors.accent.copy(alpha = xAlpha), 3f)
    sketchLine(pt(cx - 12f, cy + 12f), pt(cx + 12f, cy - 12f), colors.accent.copy(alpha = xAlpha), 3f)

    twinkle(240f, 90f, 3f, t, 0.4f, colors.inkSoft)
    groundLine(238f, colors.inkFaint)
}

// ─── Under Maintenance ─────────────────────────────────────────────────────────
//   A slowly turning gear crossed by a static wrench.

internal fun DrawScope.drawUnderMaintenance(t: Float, colors: SketchyColors) {
    val cx = 160f
    val cy = 150f
    val r = 46f
    val pivot = pt(cx, cy)
    withTransform({ rotate(degrees = 360f * t, pivot = pivot) }) {
        sketchCircle(pt(cx, cy), r, colors.ink, width = 2.4f)
        sketchCircle(pt(cx, cy), 14f, colors.inkSoft, width = 1.8f)
        for (i in 0 until 8) {
            val a = i * (360.0 / 8.0) * kotlin.math.PI / 180.0
            val fx = cx + (r - 2f) * kotlin.math.cos(a).toFloat()
            val fy = cy + (r - 2f) * kotlin.math.sin(a).toFloat()
            val tx = cx + (r + 14f) * kotlin.math.cos(a).toFloat()
            val ty = cy + (r + 14f) * kotlin.math.sin(a).toFloat()
            sketchLine(pt(fx, fy), pt(tx, ty), colors.ink, 4f)
        }
    }

    // static wrench overlay
    val wrench = Path().apply {
        moveTo(d(110f), d(210f))
        lineTo(d(170f), d(150f))
        lineTo(d(178f), d(158f))
        lineTo(d(118f), d(218f))
        close()
    }
    stroke(wrench, colors.accentSecondary, 2.2f)
    sketchCircle(pt(104f, 216f), 12f, colors.accentSecondary, width = 2.2f)

    twinkle(230f, 210f, 3f, t, 0.5f, colors.inkSoft)
    groundLine(250f, colors.inkFaint)
}

// ─── Location Not Found ──────────────────────────────────────────────────────
//   A map pin bobbing above a pulsing search radius, with a "?" inside it.

internal fun DrawScope.drawLocationNotFound(t: Float, colors: SketchyColors) {
    val cx = 160f
    val bob = 6f * wave(t, 0f)

    val pulse = (t % 1f)
    sketchCircle(pt(cx, 232f), 18f + 30f * pulse, colors.inkFaint, width = 1.4f)

    val pinTop = 92f + bob
    val pin = Path().apply {
        moveTo(d(cx), d(pinTop + 76f))
        cubicTo(
            d(cx - 40f), d(pinTop + 30f),
            d(cx - 34f), d(pinTop),
            d(cx), d(pinTop)
        )
        cubicTo(
            d(cx + 34f), d(pinTop),
            d(cx + 40f), d(pinTop + 30f),
            d(cx), d(pinTop + 76f)
        )
        close()
    }
    stroke(pin, colors.ink, 2.6f)
    sketchCircle(pt(cx, pinTop + 24f), 14f, colors.accent, width = 2.2f)

    twinkle(96f, 100f, 3f, t, 0.3f, colors.inkSoft)
    twinkle(230f, 130f, 3f, t, 0.7f, colors.inkSoft)
    groundLine(258f, colors.inkFaint)
}
