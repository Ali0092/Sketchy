package com.sketchy.library.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

/**
 * Painting primitives for the fully colored illustrations: gradient brushes,
 * filled-and-outlined shapes, shading, specular highlights and soft shadows.
 *
 * Everything takes coordinates in the same 320dp design space the scenes are
 * drawn in and converts with [d] / [pt], so a brush stays locked to its shape
 * at any render size. Pure Compose graphics — no android.graphics, no blur
 * filters (soft shadows are faked by stacking translucent ellipses).
 *
 * These are also what makes one scene render two ways. An outlined scene reads
 * every material back as transparent (see
 * [com.sketchy.library.SketchyStyle]), and everything here treats a
 * transparent colour as *nothing to paint* rather than as black: a fill is
 * skipped, a shadow or a bloom never lands, and [paint] falls through to the
 * ink outline alone. Which is exactly the line drawing we want, from unchanged
 * scene code.
 *
 * The one rule a scene has to follow is to re-alpha palette colours with [a]
 * and never with `Color.copy(alpha = …)` — `copy` would turn an invisible
 * material into opaque black.
 */

// ── Timing & pseudo-random ───────────────────────────────────────────────────

/** The loop as a 0..1 swell instead of a −1..1 wave. */
internal fun pulse(t: Float, offset: Float = 0f) = (1f + wave(t, offset)) / 2f

/** The loop as a sawtooth 0..1, for anything that falls, rises, or resets. */
internal fun loop(t: Float, offset: Float = 0f) = ((t + offset) % 1f + 1f) % 1f

/** Smoothstep, clamped. */
internal fun smooth01(x: Float): Float {
    val c = x.coerceIn(0f, 1f)
    return c * c * (3f - 2f * c)
}

/**
 * Deterministic 0..1 jitter for an index — scattered rain, stars of varying
 * size, uneven wood grain. Keeps a scene a pure function of `t` with no state
 * and no `Random`, and (unlike `i * k` spacing) leaves no visible regularity.
 */
internal fun hash01(i: Int): Float {
    val v = kotlin.math.sin(i * 12.9898f) * 43758.547f
    return v - kotlin.math.floor(v)
}

// ── Deriving tones from a base hue ───────────────────────────────────────────

/**
 * Re-alphas a colour, except that an already invisible one stays invisible —
 * a material dropped by an outlined scene must not come back as translucent
 * black. Always use this on a palette slot instead of `copy(alpha = …)`.
 */
internal fun Color.a(alpha: Float) = if (isHidden) this else copy(alpha = alpha)

/** Mixed towards white — the lit face of a surface. */
internal fun Color.lit(amount: Float = 0.22f) =
    if (isHidden) this else lerp(this, Color.White, amount)

/** Mixed towards a cool near-black — the shadowed face of a surface. */
internal fun Color.shaded(amount: Float = 0.22f) =
    if (isHidden) this else lerp(this, Color(0xFF1A2434), amount)

/** Nothing to paint: either a material an outlined scene drops, or a faded-out tone. */
internal val Color.isHidden: Boolean get() = alpha == 0f

// ── Brushes ──────────────────────────────────────────────────────────────────

/** Stops as a list — [Color] is a value class, so it cannot be a vararg. */
private fun stopsOf(from: Color, to: Color, mid: Color?) =
    if (mid == null) listOf(from, to) else listOf(from, mid, to)

/** Vertical gradient between two design-space y positions. */
internal fun DrawScope.vBrush(
    fromY: Float,
    toY: Float,
    from: Color,
    to: Color,
    mid: Color? = null
) = Brush.verticalGradient(colors = stopsOf(from, to, mid), startY = d(fromY), endY = d(toY))

/** Horizontal gradient between two design-space x positions. */
internal fun DrawScope.hBrush(
    fromX: Float,
    toX: Float,
    from: Color,
    to: Color,
    mid: Color? = null
) = Brush.linearGradient(
    colors = stopsOf(from, to, mid),
    start = Offset(d(fromX), 0f),
    end = Offset(d(toX), 0f)
)

/** Diagonal gradient, for raking light across a surface. */
internal fun DrawScope.dBrush(
    fromX: Float,
    fromY: Float,
    toX: Float,
    toY: Float,
    from: Color,
    to: Color,
    mid: Color? = null
) = Brush.linearGradient(
    colors = stopsOf(from, to, mid),
    start = pt(fromX, fromY),
    end = pt(toX, toY)
)

/** Radial bloom that fades from [core] to fully transparent at the edge. */
internal fun DrawScope.glowBrush(cx: Float, cy: Float, radius: Float, core: Color) =
    Brush.radialGradient(
        colors = listOf(core, core.copy(alpha = core.alpha * 0.45f), core.copy(alpha = 0f)),
        center = pt(cx, cy),
        radius = d(radius)
    )

// ── Path builders ────────────────────────────────────────────────────────────

/** Rounded rectangle from a design-space origin, size and corner radius. */
internal fun DrawScope.roundRectPath(
    x: Float,
    y: Float,
    w: Float,
    h: Float,
    r: Float
): Path = Path().apply {
    val rr = d(r)
    val left = d(x)
    val top = d(y)
    val right = d(x + w)
    val bottom = d(y + h)
    moveTo(left + rr, top)
    lineTo(right - rr, top)
    quadraticTo(right, top, right, top + rr)
    lineTo(right, bottom - rr)
    quadraticTo(right, bottom, right - rr, bottom)
    lineTo(left + rr, bottom)
    quadraticTo(left, bottom, left, bottom - rr)
    lineTo(left, top + rr)
    quadraticTo(left, top, left + rr, top)
    close()
}

/** Plain rectangle in design space. */
internal fun DrawScope.rectPath(x: Float, y: Float, w: Float, h: Float): Path = Path().apply {
    moveTo(d(x), d(y))
    lineTo(d(x + w), d(y))
    lineTo(d(x + w), d(y + h))
    lineTo(d(x), d(y + h))
    close()
}

/**
 * Ellipse in design space. Use this — not [sketchCircle] — for anything lying
 * flat and seen at an angle: mug rims, cup interiors, plate edges, bowl mouths.
 * A ratio around `ry = 0.3f * rx` reads as a natural table-height viewpoint.
 */
internal fun DrawScope.ellipsePath(cx: Float, cy: Float, rx: Float, ry: Float): Path =
    Path().apply {
        arcTo(
            rect = Rect(pt(cx - rx, cy - ry), Size(d(rx * 2f), d(ry * 2f))),
            startAngleDegrees = 0f,
            sweepAngleDegrees = 359.9f,
            forceMoveTo = true
        )
        close()
    }

// ── Fills & outlines ─────────────────────────────────────────────────────────

internal fun DrawScope.fill(path: Path, color: Color) {
    if (color.isHidden) return
    drawPath(path = path, color = color)
}

internal fun DrawScope.fill(path: Path, brush: Brush) = drawPath(path = path, brush = brush)

/**
 * The workhorse of the painted scenes: fill a shape, then trace it in ink.
 * Outline weight carries depth — 2.2f–2.4f for foreground subjects, 1.6f for
 * midground, 1.2f or none at all in the far background.
 *
 * Outlined, the fill is simply not there and the shape comes out as its ink
 * outline — so prefer this over a bare [fill] for anything whose silhouette
 * carries the drawing.
 */
internal fun DrawScope.paint(path: Path, brush: Brush, ink: Color, width: Float = 2.2f) {
    drawPath(path = path, brush = brush)
    drawPath(path = path, color = ink, style = bold(width))
}

internal fun DrawScope.paint(path: Path, color: Color, ink: Color, width: Float = 2.2f) {
    fill(path, color)
    drawPath(path = path, color = ink, style = bold(width))
}

/**
 * [paint] for the shapes that are drawn as circles rather than paths — heads,
 * wheels, plates, badges, balloons.
 */
internal fun DrawScope.paintCircle(
    center: Offset,
    radius: Float,
    color: Color,
    ink: Color,
    width: Float = 2.4f
) {
    if (!color.isHidden) sketchCircle(center, radius, color, filled = true)
    sketchCircle(center, radius, ink, width = width)
}

/**
 * An arm, a leg, a stem, a cable: one plain ink line when outlined, a rod with
 * real thickness when painted.
 *
 * The branch is the point. [paintStroke] on its own would render the outlined
 * version as a *hollow* two-edged rod, which is right for a mug handle but
 * would quietly redraw every limb in the line-art scenes — those are meant to
 * stay single strokes.
 */
internal fun DrawScope.limb(
    path: Path,
    color: Color,
    ink: Color,
    width: Float = 2.4f,
    thickness: Float = 7f
) {
    if (color.isHidden) {
        drawPath(path = path, color = ink, style = bold(width))
    } else {
        paintStroke(path, color, ink, width = thickness, outline = width * 0.8f)
    }
}

/**
 * An open path drawn as a solid rod outlined on **both** edges — mug handles,
 * tails, cables. Stroking the fill and the ink along the same centreline would
 * instead run the outline straight down the middle of the rod, so the ink goes
 * on first at a wider stroke and the fill covers its middle.
 *
 * With no fill to cover it — an outlined scene — that trick would leave a fat
 * ink bar, so there the rod's middle is punched back out inside a layer and
 * only its two edges remain.
 */
internal fun DrawScope.paintStroke(
    path: Path,
    color: Color,
    ink: Color,
    width: Float,
    outline: Float = 1.9f
) {
    if (color.isHidden) {
        val bounds = path.getBounds().inflate(d(width + outline * 2f))
        drawContext.canvas.saveLayer(bounds, Paint())
        drawPath(path = path, color = ink, style = bold(width + outline * 2f))
        drawPath(path = path, color = Color.Black, style = bold(width), blendMode = BlendMode.Clear)
        drawContext.canvas.restore()
        return
    }
    drawPath(path = path, color = ink, style = bold(width + outline * 2f))
    drawPath(path = path, color = color, style = bold(width))
}

// ── Shading & light ──────────────────────────────────────────────────────────

/**
 * Paints [brush] inside [path] only, so a shadow or a highlight gradient can be
 * laid over a surface without escaping its silhouette. Bring your own
 * transparent stop — a shade that covers the whole shape isn't shading.
 */
internal fun DrawScope.shade(path: Path, brush: Brush) {
    clipPath(path) { drawPath(path = path, brush = brush) }
}

/** A specular streak clipped to [path], raking from [from] towards [to]. */
internal fun DrawScope.sheen(path: Path, from: Offset, to: Offset, color: Color) {
    if (color.isHidden) return
    val brush = Brush.linearGradient(
        colors = listOf(color.copy(alpha = 0f), color, color.copy(alpha = 0f)),
        start = from,
        end = to
    )
    clipPath(path) { drawPath(path = path, brush = brush) }
}

/** A radial light bloom — lamps, sunlight through glass, screen spill. */
internal fun DrawScope.glow(cx: Float, cy: Float, radius: Float, color: Color) {
    if (color.isHidden) return
    drawCircle(brush = glowBrush(cx, cy, radius, color), radius = d(radius), center = pt(cx, cy))
}

/**
 * The soft shadow an object casts where it meets a surface. Three stacked
 * ellipses — widest and faintest first — stand in for a blur, and this single
 * call does most of the work of making an object sit in real space rather than
 * float on top of the page. Draw it *before* the object.
 */
internal fun DrawScope.contactShadow(
    cx: Float,
    cy: Float,
    rx: Float,
    ry: Float,
    color: Color
) {
    if (color.isHidden) return
    val spread = floatArrayOf(1.45f, 1.18f, 1f)
    val alphas = floatArrayOf(0.35f, 0.6f, 1f)
    spread.forEachIndexed { i, s ->
        fill(
            ellipsePath(cx, cy, rx * s, ry * s),
            color.copy(alpha = color.alpha * alphas[i])
        )
    }
}

/**
 * A surface seen in perspective — a table, desk, counter or sill — wider at the
 * front than at the back, with softened front corners.
 *
 * Painted scenes sit on a **transparent** canvas, exactly like the line-art
 * ones, so a surface has to be a real object with visible ends: [backInset] and
 * [frontInset] hold it away from the canvas edge instead of letting it bleed
 * across as a coloured band.
 */
internal fun DrawScope.surfacePath(
    backY: Float,
    frontY: Float,
    backInset: Float,
    frontInset: Float,
    r: Float = 12f
): Path = Path().apply {
    val bl = backInset
    val br = 320f - backInset
    val fl = frontInset
    val fr = 320f - frontInset
    moveTo(d(bl), d(backY))
    lineTo(d(br), d(backY))
    lineTo(d(fr), d(frontY - r))
    quadraticTo(d(fr), d(frontY), d(fr - r), d(frontY))
    lineTo(d(fl + r), d(frontY))
    quadraticTo(d(fl), d(frontY), d(fl), d(frontY - r))
    close()
}

/**
 * Darkens the interior edge of a silhouette by re-stroking it offset away from
 * the light and clipping the result to itself. This is the trick that sells
 * hollow, rounded objects — mug rims, pot lips, lamp shades, screen bezels.
 */
internal fun DrawScope.innerRim(
    path: Path,
    dx: Float,
    dy: Float,
    color: Color,
    width: Float = 2f
) {
    if (color.isHidden) return
    clipPath(path) {
        translate(left = d(dx), top = d(dy)) {
            drawPath(path = path, color = color, style = bold(width))
        }
    }
}

/** Strokes a path with a brush — for anything that should taper away or fade out. */
internal fun DrawScope.brushStroke(path: Path, brush: Brush, width: Float = 2f) {
    drawPath(path = path, brush = brush, style = thin(width))
}

/**
 * A soft duplicate of [path]'s outline, offset by ([dx], [dy]) and drawn *before* the shape itself
 * — the outline-mode equivalent of a cast shadow, for scenes with nothing filled in to shade.
 * Pass [color] as `colors.outlineShadow` so it only ever shows up outlined: colorful scenes already
 * get real depth from [shade]/[contactShadow] and this collapses to a no-op there.
 */
internal fun DrawScope.inkShadow(
    path: Path,
    color: Color,
    dx: Float = 3f,
    dy: Float = 4f,
    width: Float = 2.2f
) {
    if (color.isHidden) return
    translate(left = d(dx), top = d(dy)) {
        drawPath(path = path, color = color, style = bold(width))
    }
}

/**
 * A light corner-weighted grey wash inside [path]'s own silhouette — outline mode's stand-in for
 * real material shading, more hand-inked weight than [inkShadow]'s thin duplicate stroke without
 * turning the shape into a fully painted fill. A radial gradient centred on the shape clamps to
 * [color] past [halfExtent], so tuning [halfExtent] to sit between the shape's flat-edge distance
 * and its corner distance makes the corners read heavier than the edges, with no per-corner
 * geometry. Pass [color] as `colors.outlineShadow` (lightened via [a]) so it only ever shows up
 * outlined: colorful scenes already get real shading from [shade]/[contactShadow].
 */
internal fun DrawScope.cornerShade(
    path: Path,
    cx: Float,
    cy: Float,
    halfExtent: Float,
    color: Color
) {
    if (color.isHidden) return
    shade(
        path,
        Brush.radialGradient(
            colors = listOf(color.a(0f), color.a(color.alpha * 0.5f), color),
            center = pt(cx, cy),
            radius = d(halfExtent)
        )
    )
}

/**
 * Text baked into a device's own screen, LED strip, or engraved plate — never a free-standing
 * sign. A no-op if [measurer] is null (the caller composable didn't supply one) or [color] is
 * hidden, so it's always safe to call unconditionally. Small and plain by design: a status
 * readout, not a headline.
 */
internal fun DrawScope.deviceLabel(
    measurer: TextMeasurer?,
    text: String,
    cx: Float,
    cy: Float,
    color: Color,
    fontSize: Float = 11f
) {
    if (measurer == null || color.isHidden) return
    val style = TextStyle(
        color = color,
        fontSize = fontSize.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = FontFamily.Monospace,
        textAlign = TextAlign.Center
    )
    val result = measurer.measure(text, style)
    drawText(
        result,
        topLeft = Offset(d(cx) - result.size.width / 2f, d(cy) - result.size.height / 2f)
    )
}

/** A directional shadow raked away from the light across a flat surface. */
internal fun DrawScope.castShadow(
    x: Float,
    y: Float,
    w: Float,
    h: Float,
    skew: Float,
    color: Color
) {
    if (color.isHidden) return
    val shadow = Path().apply {
        moveTo(d(x), d(y))
        lineTo(d(x + w), d(y))
        lineTo(d(x + w + skew), d(y + h))
        lineTo(d(x + skew), d(y + h))
        close()
    }
    fill(shadow, vBrush(y, y + h, color, color.a(0f)))
}

/**
 * A rising wisp of steam that curls, drifts, and fades out as it climbs —
 * stroked with an alpha gradient so the tip dissolves instead of stopping dead.
 *
 * The phase is deliberately shifted a quarter turn so that `t = 0` — the frame
 * `animate = false` freezes on — shows a fully formed wisp rather than nothing.
 */
internal fun DrawScope.steam(
    x: Float,
    y: Float,
    t: Float,
    offset: Float = 0f,
    color: Color,
    height: Float = 46f
) {
    if (color.isHidden) return
    val phase = loop(t, offset + 0.25f)
    val rise = phase * height * 0.45f
    // swells as it leaves the surface, then thins out towards the top of the climb
    val strength = kotlin.math.sin(phase * kotlin.math.PI.toFloat()).coerceIn(0f, 1f)
    val sway = 7f * wave(t, offset)
    val top = y - rise
    val tip = top - height * 0.9f
    val wisp = Path().apply {
        moveTo(d(x), d(top))
        quadraticTo(d(x + sway), d(top - height * 0.3f), d(x), d(top - height * 0.55f))
        quadraticTo(d(x - sway), d(top - height * 0.78f), d(x + sway * 0.4f), d(tip))
    }
    brushStroke(
        wisp,
        vBrush(tip, top, color.a(0f), color.a(color.alpha * (0.25f + 0.75f * strength))),
        width = 2.2f
    )
}
