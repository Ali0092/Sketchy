package com.sketchy.library.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import com.sketchy.library.utils.d
import com.sketchy.library.utils.hash01
import com.sketchy.library.utils.pt
import kotlin.math.hypot

/**
 * Shared rendering for every hand-drawn icon: the same path geometry throughout, with only the
 * corner radius and the paint operation (stroke vs. fill) changing per [IconStyle].
 */

/**
 * Redraws [path] with a hand-inked wobble instead of a mathematically perfect edge: walks it at a
 * fixed arc-length step, nudges each sample a small pseudo-random amount along its local normal,
 * then reconnects the samples with short curves instead of straight segments — the same
 * unsteady-by-hand quality every illustration and empty state already has, applied to an icon's
 * otherwise-precise geometry. Deterministic (no [kotlin.random.Random]) so a static icon doesn't
 * flicker between recompositions — the same input path always produces the same wobble.
 *
 * Only ever sketch a single-contour path (one unbroken `moveTo…[lineTo/cubicTo/quadraticTo]…`
 * chain, optionally `close()`d) — a composite built via [Path.addPath] loses every contour after
 * the first once resampled, since [PathMeasure] only walks the first. Sketch each piece before
 * combining it into a composite (a hole, a multi-segment line group), never the combined result.
 */
internal fun DrawScope.sketch(path: Path, amplitude: Float = 0.34f, step: Float = 2.4f): Path {
    val measure = PathMeasure().apply { setPath(path, false) }
    val length = measure.length
    if (length <= 0f) return path

    val amp = d(amplitude)
    val sampleStep = d(step).coerceAtLeast(0.5f)
    val samples = (length / sampleStep).toInt().coerceIn(6, 160)
    val seed = (length * 12.9f).toInt()

    val start = measure.getPosition(0f)
    val end = measure.getPosition(length)
    val closed = hypot((end.x - start.x).toDouble(), (end.y - start.y).toDouble()) < 0.01

    val result = Path()
    var prevX = 0f
    var prevY = 0f
    for (i in 0..samples) {
        val dist = length * i / samples
        val pos = measure.getPosition(dist)
        val tan = measure.getTangent(dist)
        val n = hash01(seed + i) * 2f - 1f
        val x = pos.x + (-tan.y) * amp * n
        val y = pos.y + tan.x * amp * n
        when (i) {
            0 -> result.moveTo(x, y)
            samples -> result.quadraticTo(prevX, prevY, x, y)
            else -> result.quadraticTo(prevX, prevY, (prevX + x) / 2f, (prevY + y) / 2f)
        }
        prevX = x
        prevY = y
    }
    if (closed) result.close()
    return result
}

/** The corner radius to build a shape with — zero (sharp corners) for [IconStyle.Sharp], [rounded]
 *  for every other style. */
internal fun cornerRadius(style: IconStyle, rounded: Float = 2f) = if (style == IconStyle.Sharp) 0f else rounded

/**
 * The effective stroke width for a line-family icon: [IconStyle.Outlined] always draws at 70% of
 * [strokeWidth] — thinner than the rest by definition — and [multiplier] scales a specific accent
 * (a bolder "filled" handle, a finer separator line) relative to that same base.
 */
internal fun outlineWidth(style: IconStyle, strokeWidth: Float, multiplier: Float = 1f): Float {
    val base = if (style == IconStyle.Outlined) strokeWidth * 0.7f else strokeWidth
    return base * multiplier
}

/** The stroke every line-family icon is traced with — see [outlineWidth] — square/mitred joins
 *  instead of round for [IconStyle.Sharp]. */
internal fun DrawScope.iconStroke(style: IconStyle, strokeWidth: Float, multiplier: Float = 1f): Stroke =
    Stroke(
        width = d(outlineWidth(style, strokeWidth, multiplier)),
        cap = if (style == IconStyle.Sharp) StrokeCap.Square else StrokeCap.Round,
        join = if (style == IconStyle.Sharp) StrokeJoin.Miter else StrokeJoin.Round,
        miter = 4f
    )

/** Renders [path] as whichever family [style] calls for — the common case, for an icon whose
 *  silhouette needs no internal cut-out. [path] is sketched (see [sketch]) before painting, so
 *  callers should pass the raw, mathematically clean geometry. */
internal fun DrawScope.paintIcon(path: Path, style: IconStyle, tint: Color, strokeWidth: Float) {
    val inked = sketch(path)
    when (style) {
        IconStyle.Filled -> drawPath(inked, color = tint, style = Fill)
        IconStyle.TwoTone -> {
            drawPath(inked, color = tint.copy(alpha = tint.alpha * 0.28f), style = Fill)
            drawPath(inked, color = tint, style = iconStroke(IconStyle.Default, strokeWidth))
        }
        else -> drawPath(inked, color = tint, style = iconStroke(style, strokeWidth))
    }
}

/**
 * A new path combining [this] with [hole] under the even-odd fill rule, so filling the result
 * leaves [hole] as a real cut-out — a gear's hub, a lens, a keyhole, a flap crease. Stroking either
 * path on its own is unaffected by fill type, so the pair still traces correctly for the line
 * families (the caller draws both separately there).
 */
internal fun Path.withHole(hole: Path): Path = Path().apply {
    addPath(this@withHole)
    addPath(hole)
    fillType = PathFillType.EvenOdd
}

/** A small always-solid accent — a bell's clapper, a cart's wheels, a share node — that stays
 *  filled in every style, the same way real icon sets keep small connector marks solid throughout. */
internal fun DrawScope.solidDot(cx: Float, cy: Float, r: Float, tint: Color) {
    drawCircle(color = tint, radius = d(r), center = pt(cx, cy))
}
