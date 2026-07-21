package com.sketchy.library.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

/** Low-level Canvas drawing extensions shared by every illustration and empty-state scene. */

internal fun DrawScope.d(v: Float) = v.dp.toPx()
internal fun DrawScope.pt(x: Float, y: Float) = Offset(d(x), d(y))

internal fun DrawScope.bold(width: Float = 2.4f) = Stroke(
    width = d(width), cap = StrokeCap.Round, join = StrokeJoin.Round
)
internal fun DrawScope.thin(width: Float = 1.6f) = Stroke(
    width = d(width), cap = StrokeCap.Round, join = StrokeJoin.Round
)
internal fun DrawScope.dashed() = Stroke(
    width = d(1.4f),
    cap = StrokeCap.Round,
    pathEffect = PathEffect.dashPathEffect(floatArrayOf(d(3f), d(4f)))
)

internal fun DrawScope.stroke(path: Path, color: Color = Ink, width: Float = 2.4f) {
    drawPath(path = path, color = color, style = bold(width))
}

internal fun DrawScope.sketchLine(
    from: Offset,
    to: Offset,
    color: Color = Ink,
    width: Float = 2.4f
) {
    drawLine(
        color = color,
        start = from,
        end = to,
        strokeWidth = d(width),
        cap = StrokeCap.Round
    )
}

internal fun DrawScope.sketchCircle(
    center: Offset,
    radius: Float,
    color: Color = Ink,
    width: Float = 2.4f,
    filled: Boolean = false
) {
    drawCircle(
        color = color,
        radius = d(radius),
        center = center,
        style = if (filled) Fill else bold(width)
    )
}

/** A little star that twinkles: it swells, brightens, and flashes diagonal glints. */
internal fun DrawScope.twinkle(
    cx: Float,
    cy: Float,
    size: Float,
    t: Float,
    offset: Float = 0f,
    color: Color = Ink
) {
    val k = (1f + wave(t, offset)) / 2f
    val s = size * (0.7f + 0.5f * k)
    val c = color.copy(alpha = color.alpha * (0.35f + 0.65f * k))
    sketchLine(pt(cx - s, cy), pt(cx + s, cy), c, 1.8f)
    sketchLine(pt(cx, cy - s), pt(cx, cy + s), c, 1.8f)
    // diagonal glints only near peak brightness
    if (k > 0.7f) {
        val g = c.copy(alpha = c.alpha * (k - 0.7f) / 0.3f)
        val ds = s * 0.55f
        sketchLine(pt(cx - ds, cy - ds), pt(cx + ds, cy + ds), g, 1.4f)
        sketchLine(pt(cx - ds, cy + ds), pt(cx + ds, cy - ds), g, 1.4f)
    }
}

/** A faint dashed baseline used by illustration scenes for visual grounding. */
internal fun DrawScope.groundHint(y: Float) {
    drawLine(
        color = InkFaint,
        start = pt(20f, y),
        end = pt(300f, y),
        strokeWidth = d(1.2f),
        cap = StrokeCap.Round,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(d(2.5f), d(6f)))
    )
}

/** A faint dashed baseline shared by every empty-state icon for visual grounding. */
internal fun DrawScope.groundLine(y: Float, color: Color) {
    val path = Path().apply {
        moveTo(d(60f), d(y))
        lineTo(d(260f), d(y))
    }
    drawPath(path = path, color = color, style = dashed())
}
