package com.sketchy.library

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Signature sketched illustrations, hand-drawn as line-art on Canvas.
 * Warm off-white ink against the dark page backgrounds.
 *
 * Every scene receives a looping phase `t` (0..1 over ~4s) that drives gentle
 * ambient motion — pulsing sparkles, ringing bells, a sweeping stopwatch needle —
 * on top of a soft entrance fade/scale and a slow whole-canvas float.
 */

private val Ink = Color(0xFF0D1B2A)
private val InkSoft = Color(0x990D1B2A)
private val InkFaint = Color(0x330D1B2A)
private val Accent = Color(0xFFFFBC00)
private val AccentTeal = Color(0xFF008091)

internal const val TWO_PI = 2f * PI.toFloat()

/** Smooth −1..1 sine wave over the loop, optionally phase-shifted. */
internal fun wave(t: Float, offset: Float = 0f) = sin((t + offset) * TWO_PI)

/** Every sketch currently available in the library. */
enum class Sketch(val displayName: String) {
    PlanTasks("Plan Every Task"),
    FindFocus("Find Your Focus"),
    NeverMissMeeting("Never Miss a Meeting"),
    CaptureThoughts("Capture Every Thought"),
    BuildBetterHabits("Build Better Habits"),
}

/** The square size every scene is hand-drawn against; content scales to fit any other size. */
internal val DesignSize = 320.dp

/**
 * Renders a single [Sketch]. Set [animate] to false to freeze the scene at
 * its resting frame instead of looping its ambient motion.
 *
 * The scene is hand-drawn against a 320dp design canvas and scales uniformly
 * to fit whatever size [modifier] gives it, so it works equally well as a
 * small gallery thumbnail or a full-bleed illustration.
 */
@Composable
fun SketchyIllustration(
    sketch: Sketch,
    modifier: Modifier = Modifier.size(DesignSize),
    animate: Boolean = true,
) {
    // Looping phase driving all ambient motion inside the scenes.
    val t: Float = if (animate) {
        val transition = rememberInfiniteTransition(label = "sketchy_art")
        val phase by transition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(tween(4000, easing = LinearEasing)),
            label = "phase"
        )
        phase
    } else {
        0f
    }
    // Soft entrance when the page first composes.
    val appear = remember { Animatable(0f) }
    LaunchedEffect(Unit) { appear.animateTo(1f, tween(700, easing = FastOutSlowInEasing)) }

    Canvas(
        modifier = modifier
            .graphicsLayer {
                alpha = appear.value
                val entrance = 0.94f + 0.06f * appear.value
                scaleX = entrance
                scaleY = entrance
                // slow breathing float of the whole artwork
                translationY = wave(t) * 3.dp.toPx()
            }
    ) {
        // Scale the 320dp design canvas uniformly to fit whatever size we were given.
        val fit = minOf(size.width, size.height) / DesignSize.toPx()
        withTransform({ scale(scaleX = fit, scaleY = fit, pivot = Offset.Zero) }) {
            when (sketch) {
                Sketch.PlanTasks -> drawTasksScene(t)
                Sketch.FindFocus -> drawFocusScene(t)
                Sketch.NeverMissMeeting -> drawMeetingsScene(t)
                Sketch.CaptureThoughts -> drawNotesScene(t)
                Sketch.BuildBetterHabits -> drawHabitsScene(t)
            }
        }
    }
}

// ─── Helpers ────────────────────────────────────────────────────────────────

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

private fun DrawScope.groundHint(y: Float) {
    drawLine(
        color = InkFaint,
        start = pt(20f, y),
        end = pt(300f, y),
        strokeWidth = d(1.2f),
        cap = StrokeCap.Round,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(d(2.5f), d(6f)))
    )
}

// ─── Scene 1: Plan Every Task ───────────────────────────────────────────────
//   Person seated at a small desk with an open laptop, floating checklist,
//   a leafy plant, and a scatter of sparkles.

private fun DrawScope.drawTasksScene(t: Float) {
    // ── floating checklist paper (upper left) ───────────────
    val paper = Path().apply {
        moveTo(d(30f), d(38f))
        lineTo(d(102f), d(30f))
        lineTo(d(110f), d(132f))
        lineTo(d(38f), d(140f))
        close()
    }
    stroke(paper)

    // little folded corner
    val fold = Path().apply {
        moveTo(d(96f), d(30.5f))
        lineTo(d(102f), d(38f))
        lineTo(d(96f), d(39f))
        close()
    }
    stroke(fold, InkSoft, 1.8f)

    // checklist rows — teal checks sweep in from top to bottom, then reset
    for (row in 0..3) {
        val y = 53f + row * 20f
        val bx = 45f + row * 0.7f   // drift with the paper's tilt
        val box = Path().apply {
            moveTo(d(bx), d(y))
            lineTo(d(bx + 10f), d(y - 1f))
            lineTo(d(bx + 11f), d(y + 9f))
            lineTo(d(bx + 1f), d(y + 10f))
            close()
        }
        sketchLine(pt(bx + 18f, y + 6f), pt(bx + 55f, y + 3f), InkSoft, 2.0f)

        val checkAt = 0.08f + row * 0.17f
        if (t > checkAt) {
            // checked: box fills solid teal with a yellow tick popping on top
            drawPath(box, color = AccentTeal, style = Fill)
            val pop = 1f + 0.5f * (1f - ((t - checkAt) / 0.10f).coerceAtMost(1f))
            val ccx = bx + 5f
            val ccy = y + 4f
            sketchLine(pt(ccx - 4f * pop, ccy), pt(ccx - 0.5f, ccy + 4f * pop), Accent, 2.4f)
            sketchLine(pt(ccx - 0.5f, ccy + 4f * pop), pt(ccx + 6f * pop, ccy - 4f * pop), Accent, 2.4f)
        } else {
            // unchecked: plain outlined box
            stroke(box, Ink, 1.8f)
        }
    }

    // ── person seated (center-left) ─────────────────────────
    // head
    sketchCircle(pt(148f, 108f), 22f, width = 2.4f)
    // top bun
    sketchCircle(pt(148f, 84f), 8f, width = 2.2f)
    // small hair strand
    val strand = Path().apply {
        moveTo(d(140f), d(88f))
        quadraticTo(d(132f), d(96f), d(130f), d(108f))
    }
    stroke(strand, Ink, 2.0f)

    // face – tiny features
    sketchCircle(pt(154f, 108f), 1.5f, filled = true)                          // eye
    val smile = Path().apply {
        moveTo(d(151f), d(115f))
        quadraticTo(d(154f), d(118f), d(157f), d(115f))
    }
    stroke(smile, Ink, 1.8f)

    // neck
    sketchLine(pt(148f, 130f), pt(148f, 138f))

    // hoodie / torso (rounded trapezoid)
    val body = Path().apply {
        moveTo(d(148f), d(138f))
        quadraticTo(d(128f), d(140f), d(120f), d(150f))
        lineTo(d(112f), d(200f))
        quadraticTo(d(148f), d(212f), d(180f), d(200f))
        lineTo(d(174f), d(150f))
        quadraticTo(d(168f), d(140f), d(148f), d(138f))
        close()
    }
    stroke(body)

    // sleeve seam hint
    val seam = Path().apply {
        moveTo(d(126f), d(150f))
        quadraticTo(d(135f), d(155f), d(148f), d(155f))
        quadraticTo(d(161f), d(155f), d(170f), d(150f))
    }
    stroke(seam, InkSoft, 1.6f)

    // right arm reaching to laptop
    val armR = Path().apply {
        moveTo(d(174f), d(155f))
        quadraticTo(d(200f), d(160f), d(214f), d(178f))
    }
    stroke(armR)
    // hand
    sketchCircle(pt(216f, 180f), 4f, width = 2.0f)

    // left arm resting
    val armL = Path().apply {
        moveTo(d(120f), d(154f))
        quadraticTo(d(108f), d(172f), d(114f), d(190f))
    }
    stroke(armL)

    // legs (sitting)
    val legL = Path().apply {
        moveTo(d(126f), d(206f))
        quadraticTo(d(128f), d(230f), d(122f), d(252f))
    }
    val legR = Path().apply {
        moveTo(d(166f), d(206f))
        quadraticTo(d(170f), d(230f), d(168f), d(252f))
    }
    stroke(legL)
    stroke(legR)
    // feet
    sketchLine(pt(116f, 252f), pt(132f, 252f))
    sketchLine(pt(162f, 252f), pt(178f, 252f))

    // ── desk + laptop (right) ───────────────────────────────
    // desk top
    val desk = Path().apply {
        moveTo(d(190f), d(200f))
        lineTo(d(300f), d(190f))
    }
    stroke(desk)
    // desk right leg
    sketchLine(pt(294f, 190f), pt(294f, 258f))

    // laptop base
    val laptopBase = Path().apply {
        moveTo(d(210f), d(192f))
        lineTo(d(285f), d(182f))
        lineTo(d(288f), d(190f))
        lineTo(d(213f), d(200f))
        close()
    }
    stroke(laptopBase)

    // laptop screen
    val laptopScreen = Path().apply {
        moveTo(d(210f), d(192f))
        lineTo(d(220f), d(150f))
        lineTo(d(292f), d(140f))
        lineTo(d(285f), d(182f))
    }
    stroke(laptopScreen)
    // little on-screen content line — second line "types" itself in a loop
    sketchLine(pt(232f, 160f), pt(275f, 154f), InkSoft, 1.8f)
    val typed = 22f + 7f * wave(t, 0.25f)
    sketchLine(pt(232f, 168f), pt(232f + typed, 168f - typed * 0.14f), InkSoft, 1.8f)

    // ── plant (bottom-left) ────────────────────────────────
    val pot = Path().apply {
        moveTo(d(20f), d(258f))
        lineTo(d(58f), d(258f))
        lineTo(d(52f), d(285f))
        lineTo(d(26f), d(285f))
        close()
    }
    stroke(pot, Accent, 2.4f)
    sketchLine(pt(23f, 264f), pt(55f, 264f), Accent.copy(alpha = 0.6f), 1.6f)
    // teal leaves swaying gently from the pot's rim
    val leaf1 = Path().apply {
        moveTo(d(30f), d(258f))
        quadraticTo(d(15f), d(240f), d(20f), d(220f))
    }
    val leaf2 = Path().apply {
        moveTo(d(38f), d(258f))
        quadraticTo(d(34f), d(232f), d(40f), d(214f))
    }
    val leaf3 = Path().apply {
        moveTo(d(46f), d(258f))
        quadraticTo(d(58f), d(240f), d(56f), d(224f))
    }
    val potTop = pt(39f, 258f)
    withTransform({ rotate(degrees = 2.5f * wave(t, 0.3f), pivot = potTop) }) {
        stroke(leaf1, AccentTeal)
        stroke(leaf2, AccentTeal)
        stroke(leaf3, AccentTeal)
    }

    // ── twinkling stars + ground ───────────────────────────
    twinkle(240f, 60f, 4f, t, 0f, Accent)
    twinkle(272f, 100f, 3f, t, 0.33f, InkSoft)
    twinkle(90f, 190f, 3f, t, 0.66f, InkSoft)
    // subtle floor hint
    groundHint(285f)
}

// ─── Scene 2: Find Your Focus ───────────────────────────────────────────────
//   A person meditating cross-legged inside a softly breathing aura ring,
//   a hovering stopwatch with a sweeping needle, and a phone set face-down
//   drifting off to sleep ("zzz").

private fun DrawScope.drawFocusScene(t: Float) {
    // ── breathing aura rings around the person ──────────────
    sketchCircle(pt(160f, 186f), 104f + 5f * wave(t), InkFaint, 1.4f)
    sketchCircle(pt(160f, 186f), 86f + 4f * wave(t, 0.12f), InkFaint, 1.2f)

    // ── hovering stopwatch (top-left) ───────────────────────
    val swCx = 84f
    val swCy = 86f
    val swR = 28f
    // crown + side button
    sketchLine(pt(swCx, swCy - swR - 4f), pt(swCx, swCy - swR - 12f))
    sketchLine(pt(swCx - 6f, swCy - swR - 14f), pt(swCx + 6f, swCy - swR - 14f))
    sketchLine(pt(swCx + 20f, swCy - 22f), pt(swCx + 26f, swCy - 28f))
    // body + inner rim
    sketchCircle(pt(swCx, swCy), swR, width = 2.4f)
    sketchCircle(pt(swCx, swCy), swR - 5f, InkSoft, 1.4f)
    // quarter ticks
    sketchLine(pt(swCx, swCy - swR + 8f), pt(swCx, swCy - swR + 12f), Ink, 1.6f)
    sketchLine(pt(swCx + swR - 8f, swCy), pt(swCx + swR - 12f, swCy), Ink, 1.6f)
    sketchLine(pt(swCx, swCy + swR - 8f), pt(swCx, swCy + swR - 12f), Ink, 1.6f)
    sketchLine(pt(swCx - swR + 8f, swCy), pt(swCx - swR + 12f, swCy), Ink, 1.6f)
    // sweeping needle — one full lap per loop
    val needleAngle = (-90.0 + 360.0 * t) * kotlin.math.PI / 180.0
    val needleTip = pt(
        swCx + 16f * cos(needleAngle).toFloat(),
        swCy + 16f * sin(needleAngle).toFloat()
    )
    drawLine(Accent, pt(swCx, swCy), needleTip, strokeWidth = d(2.6f), cap = StrokeCap.Round)
    sketchCircle(pt(swCx, swCy), 3f, filled = true)

    // ── person meditating (center) ──────────────────────────
    // head + top bun
    sketchCircle(pt(160f, 142f), 20f, width = 2.4f)
    sketchCircle(pt(160f, 118f), 7f, width = 2.2f)
    // closed eyes — two calm downward arcs
    val eyeL = Path().apply {
        moveTo(d(149f), d(140f))
        quadraticTo(d(152f), d(143f), d(155f), d(140f))
    }
    val eyeR = Path().apply {
        moveTo(d(165f), d(140f))
        quadraticTo(d(168f), d(143f), d(171f), d(140f))
    }
    stroke(eyeL, Ink, 1.8f)
    stroke(eyeR, Ink, 1.8f)
    // serene smile
    val calm = Path().apply {
        moveTo(d(156f), d(150f))
        quadraticTo(d(160f), d(153f), d(164f), d(150f))
    }
    stroke(calm, Ink, 1.8f)

    // neck
    sketchLine(pt(160f, 162f), pt(160f, 170f))

    // torso — upright, slim, shoulders relaxed
    val fBody = Path().apply {
        moveTo(d(160f), d(170f))
        quadraticTo(d(140f), d(174f), d(134f), d(188f))
        lineTo(d(130f), d(206f))
        quadraticTo(d(160f), d(218f), d(190f), d(206f))
        lineTo(d(186f), d(188f))
        quadraticTo(d(180f), d(174f), d(160f), d(170f))
        close()
    }
    stroke(fBody)

    // folded legs — wide grounded base, knees out past the shoulders
    val fLegs = Path().apply {
        moveTo(d(130f), d(206f))
        quadraticTo(d(102f), d(214f), d(100f), d(228f))
        quadraticTo(d(100f), d(240f), d(126f), d(242f))
        quadraticTo(d(160f), d(248f), d(194f), d(242f))
        quadraticTo(d(220f), d(240f), d(220f), d(228f))
        quadraticTo(d(218f), d(214f), d(190f), d(206f))
    }
    stroke(fLegs)
    // shin cross hint
    val shins = Path().apply {
        moveTo(d(126f), d(238f))
        quadraticTo(d(160f), d(224f), d(194f), d(238f))
    }
    stroke(shins, InkSoft, 1.6f)

    // arms resting on the knees
    val fArmL = Path().apply {
        moveTo(d(138f), d(186f))
        quadraticTo(d(116f), d(200f), d(112f), d(220f))
    }
    val fArmR = Path().apply {
        moveTo(d(182f), d(186f))
        quadraticTo(d(204f), d(200f), d(208f), d(220f))
    }
    stroke(fArmL)
    stroke(fArmR)
    // open palms
    sketchCircle(pt(112f, 223f), 3.5f, width = 2.0f)
    sketchCircle(pt(208f, 223f), 3.5f, width = 2.0f)

    // ── phone face-down, silenced (bottom-right) ────────────
    val phone = Path().apply {
        moveTo(d(232f), d(252f))
        lineTo(d(280f), d(252f))
        lineTo(d(283f), d(266f))
        lineTo(d(235f), d(266f))
        close()
    }
    stroke(phone)
    // camera strip peeking on the back
    sketchLine(pt(268f, 257f), pt(276f, 257f), InkSoft, 1.6f)
    // zzz drifting up — each z fades in sequence
    val zSpots = listOf(Triple(246f, 236f, 5f), Triple(256f, 222f, 4f), Triple(266f, 210f, 3f))
    zSpots.forEachIndexed { i, (zx, zy, zs) ->
        val zInk = Ink.copy(alpha = 0.25f + 0.55f * (1f + wave(t, -i * 0.22f)) / 2f)
        sketchLine(pt(zx, zy), pt(zx + zs, zy), zInk, 1.8f)
        sketchLine(pt(zx + zs, zy), pt(zx, zy + zs), zInk, 1.8f)
        sketchLine(pt(zx, zy + zs), pt(zx + zs, zy + zs), zInk, 1.8f)
    }

    // twinkling stars
    twinkle(244f, 84f, 4f, t, 0.3f, Accent)
    twinkle(60f, 190f, 3f, t, 0.6f, InkSoft)
    twinkle(272f, 156f, 3f, t, 0.9f, InkSoft)

    groundHint(292f)
}

// ─── Scene 3: Never Miss a Meeting ──────────────────────────────────────────
//   Big alarm clock with twin bells, ringing waves, and a floating calendar tag.

private fun DrawScope.drawMeetingsScene(t: Float) {
    val cx = 160f
    val cy = 172f
    val r = 74f

    // The loop tells a story: the minute hand ticks its way up to 12, then the
    // alarm erupts — the whole clock shakes and the sound waves burst outward.
    val tickPhase = (t / 0.62f).coerceIn(0f, 1f)
    val ringing = t > 0.62f
    val ringT = ((t - 0.62f) / 0.38f).coerceIn(0f, 1f)

    // minute hand advances in discrete ticks (like a real clock)
    val steps = 8f
    val rawStep = tickPhase * steps
    val stepIndex = kotlin.math.floor(rawStep)
    val snap = ((rawStep - stepIndex) * 6f).coerceAtMost(1f)   // quick jump, then hold
    val stepped = ((stepIndex + snap) / steps).coerceAtMost(1f)
    val minuteDeg = -48f + 48f * stepped                        // …lands on 12
    val hourDeg = -10f + 10f * tickPhase

    // ringing waves — quiet while ticking, bursting while the alarm rings
    for (i in 0..2) {
        val offset = 8f + i * 8f
        val ripple =
            if (ringing) 0.12f + 0.22f * (1f + wave(ringT * 2f, -i * 0.18f)) / 2f
            else 0.05f
        val ringRect = Rect(
            offset = pt(cx - r - offset - 12, cy - r - offset - 12),
            size = Size(d((r + offset + 12) * 2), d((r + offset + 12) * 2))
        )
        val leftArc = Path().apply {
            arcTo(
                rect = ringRect,
                startAngleDegrees = 150f,
                sweepAngleDegrees = 40f,
                forceMoveTo = true
            )
        }
        drawPath(leftArc, color = Ink.copy(alpha = ripple), style = thin(1.4f))
        val rightArc = Path().apply {
            arcTo(
                rect = ringRect,
                startAngleDegrees = -10f,
                sweepAngleDegrees = 40f,
                forceMoveTo = true
            )
        }
        drawPath(rightArc, color = Ink.copy(alpha = ripple), style = thin(1.4f))
    }

    // everything attached to the clock vibrates while the alarm rings
    val clockPivot = pt(cx, cy)
    val shake = if (ringing) 2.6f * sin(ringT * 10f * TWO_PI) * (1f - 0.4f * ringT) else 0f
    withTransform({ rotate(degrees = shake, pivot = clockPivot) }) {
    // twin bells on top
    // left bell dome
    val bellL = Path().apply {
        arcTo(
            rect = Rect(pt(cx - 58f, cy - r - 26f), Size(d(36f), d(36f))),
            startAngleDegrees = 180f,
            sweepAngleDegrees = 180f,
            forceMoveTo = true
        )
    }
    stroke(bellL)
    // right bell dome
    val bellR = Path().apply {
        arcTo(
            rect = Rect(pt(cx + 22f, cy - r - 26f), Size(d(36f), d(36f))),
            startAngleDegrees = 180f,
            sweepAngleDegrees = 180f,
            forceMoveTo = true
        )
    }
    stroke(bellR)
    // little "clanger" nubs
    sketchCircle(pt(cx - 40f, cy - r - 30f), 2.5f, filled = true)
    sketchCircle(pt(cx + 40f, cy - r - 30f), 2.5f, filled = true)

    // handle between bells (curve above)
    val handle = Path().apply {
        moveTo(d(cx - 22f), d(cy - r - 8f))
        cubicTo(
            d(cx - 22f), d(cy - r - 44f),
            d(cx + 22f), d(cy - r - 44f),
            d(cx + 22f), d(cy - r - 8f)
        )
    }
    stroke(handle)

    // clock main face (outer + inner rim)
    sketchCircle(pt(cx, cy), r, width = 2.6f)
    sketchCircle(pt(cx, cy), r - 6f, InkSoft, 1.4f)

    // clock legs
    sketchLine(pt(cx - 46f, cy + r - 8f), pt(cx - 60f, cy + r + 22f))
    sketchLine(pt(cx + 46f, cy + r - 8f), pt(cx + 60f, cy + r + 22f))
    // small foot pads
    sketchLine(pt(cx - 66f, cy + r + 22f), pt(cx - 54f, cy + r + 22f))
    sketchLine(pt(cx + 54f, cy + r + 22f), pt(cx + 66f, cy + r + 22f))

    // hour marks (12, 3, 6, 9)
    sketchLine(pt(cx, cy - r + 8f), pt(cx, cy - r + 16f))
    sketchLine(pt(cx + r - 16f, cy), pt(cx + r - 8f, cy))
    sketchLine(pt(cx, cy + r - 8f), pt(cx, cy + r - 16f))
    sketchLine(pt(cx - r + 8f, cy), pt(cx - r + 16f, cy))
    // small tick dots at 1,2,4,5,7,8,10,11
    for (i in 0..11) {
        if (i % 3 == 0) continue
        val ang = -90.0 + i * 30.0
        val rad = ang * kotlin.math.PI / 180.0
        val tx = cx + (r - 10f) * cos(rad).toFloat()
        val ty = cy + (r - 10f) * sin(rad).toFloat()
        sketchCircle(pt(tx, ty), 1.6f, InkSoft, filled = true)
    }

    // clock hands — ticking toward 12:00, minute hand in brand yellow
    fun handTip(deg: Float, len: Float): Offset {
        val rad = (deg - 90f) * kotlin.math.PI.toFloat() / 180f
        return pt(cx + len * cos(rad), cy + len * sin(rad))
    }
    sketchLine(pt(cx, cy), handTip(hourDeg, 34f))                            // hour
    sketchLine(pt(cx, cy), handTip(minuteDeg, 44f), Accent, 2.4f)            // minute
    sketchCircle(pt(cx, cy), 3.5f, filled = true)
    }

    // ── little "!" bubble (top-right) — pops while the alarm rings ─────────
    val bubblePivot = pt(255f, 96f)
    val bubbleScale = if (ringing) 1f + 0.18f * (1f + sin(ringT * 2f * TWO_PI)) / 2f else 1f
    withTransform({ scale(scaleX = bubbleScale, scaleY = bubbleScale, pivot = bubblePivot) }) {
    val bubble = Path().apply {
        moveTo(d(255f), d(80f))
        cubicTo(d(235f), d(80f), d(230f), d(96f), d(240f), d(108f))
        lineTo(d(238f), d(120f))
        lineTo(d(254f), d(110f))
        cubicTo(d(272f), d(112f), d(280f), d(96f), d(275f), d(88f))
        cubicTo(d(272f), d(82f), d(265f), d(80f), d(255f), d(80f))
        close()
    }
    stroke(bubble)
    val markColor = if (ringing) Accent else Ink
    sketchLine(pt(255f, 90f), pt(255f, 100f), markColor, 2.4f)
    sketchCircle(pt(255f, 104f), 1.8f, markColor, filled = true)
    }

    // ── mini calendar page (bottom-left) ────────────────────
    val cal = Path().apply {
        moveTo(d(32f), d(240f))
        lineTo(d(72f), d(240f))
        lineTo(d(72f), d(282f))
        lineTo(d(32f), d(282f))
        close()
    }
    stroke(cal)
    // header strip
    sketchLine(pt(32f, 250f), pt(72f, 250f))
    // rings
    sketchCircle(pt(42f, 238f), 2f, filled = true)
    sketchCircle(pt(62f, 238f), 2f, filled = true)
    // 3x3 grid of tiny dots
    for (row in 0..2) {
        for (col in 0..2) {
            sketchCircle(
                pt(40f + col * 12f, 258f + row * 8f),
                1.2f,
                InkSoft,
                filled = true
            )
        }
    }
    // one highlighted day (top-right cell)
    sketchCircle(pt(64f, 258f), 3f, Accent, filled = true)

    // twinkling stars
    twinkle(80f, 90f, 3f, t, 0.2f, InkSoft)
    twinkle(268f, 178f, 4f, t, 0.5f, Accent)
    twinkle(50f, 180f, 3f, t, 0.8f, InkSoft)

    groundHint(292f)
}

// ─── Scene 4: Capture Every Thought ─────────────────────────────────────────
//   A person writing in an open notebook with a light-bulb "idea" above.

private fun DrawScope.drawNotesScene(t: Float) {
    // ── light bulb (top center) ────────────────────────────
    val bulbCx = 160f
    val bulbCy = 72f
    // the idea switches on and off — a soft square wave so it truly "clicks"
    val glow = (((wave(t) * 3f).coerceIn(-1f, 1f)) + 1f) / 2f

    // warm light spilling out of the glass while it's on
    if (glow > 0.01f) {
        drawCircle(Accent.copy(alpha = 0.10f * glow), radius = d(38f), center = pt(bulbCx, bulbCy))
        drawCircle(Accent.copy(alpha = 0.22f * glow), radius = d(24f), center = pt(bulbCx, bulbCy))
    }

    // rays — golden and long when lit, faint stubs when off
    val rayGap = 4f
    val rayR = 30f
    val rays = 8
    for (i in 0 until rays) {
        val a = (-90 + i * (360 / rays)) * kotlin.math.PI / 180
        val stagger = 2f * (1f + wave(t, i / rays.toFloat())) / 2f
        val rayLen = 5f + 9f * glow + stagger
        val rayColor = lerp(InkFaint, Accent, glow)
        val fx = bulbCx + (rayR + rayGap) * cos(a).toFloat()
        val fy = bulbCy + (rayR + rayGap) * sin(a).toFloat()
        val tx = bulbCx + (rayR + rayGap + rayLen) * cos(a).toFloat()
        val ty = bulbCy + (rayR + rayGap + rayLen) * sin(a).toFloat()
        sketchLine(pt(fx, fy), pt(tx, ty), rayColor, 2.0f)
    }
    // bulb glass
    sketchCircle(pt(bulbCx, bulbCy), 22f)
    // filament curl inside
    val filament = Path().apply {
        moveTo(d(bulbCx - 8f), d(bulbCy + 4f))
        quadraticTo(d(bulbCx - 4f), d(bulbCy - 10f), d(bulbCx), d(bulbCy - 4f))
        quadraticTo(d(bulbCx + 4f), d(bulbCy + 4f), d(bulbCx + 8f), d(bulbCy - 6f))
    }
    stroke(filament, Accent.copy(alpha = 0.20f + 0.80f * glow), 2.0f)
    // base / screw threads
    val baseTop = Path().apply {
        moveTo(d(bulbCx - 12f), d(bulbCy + 20f))
        quadraticTo(d(bulbCx), d(bulbCy + 28f), d(bulbCx + 12f), d(bulbCy + 20f))
    }
    stroke(baseTop)
    sketchLine(pt(bulbCx - 10f, bulbCy + 30f), pt(bulbCx + 10f, bulbCy + 30f), Ink, 1.8f)
    sketchLine(pt(bulbCx - 8f, bulbCy + 36f), pt(bulbCx + 8f, bulbCy + 36f), Ink, 1.8f)
    // tip
    sketchLine(pt(bulbCx - 4f, bulbCy + 40f), pt(bulbCx + 4f, bulbCy + 40f), Ink, 2.0f)

    // ── person leaning over notebook (center) ───────────────
    // head
    sketchCircle(pt(148f, 152f), 20f, width = 2.4f)
    // side hair sweep
    val hairSweep = Path().apply {
        moveTo(d(132f), d(146f))
        quadraticTo(d(122f), d(158f), d(130f), d(172f))
    }
    stroke(hairSweep)
    // face
    sketchCircle(pt(154f, 154f), 1.5f, filled = true)
    val focus = Path().apply {
        moveTo(d(150f), d(162f))
        quadraticTo(d(154f), d(164f), d(158f), d(161f))
    }
    stroke(focus, Ink, 1.8f)

    // neck
    sketchLine(pt(148f, 172f), pt(148f, 180f))

    // body / hoodie — leaning forward
    val nBody = Path().apply {
        moveTo(d(148f), d(180f))
        quadraticTo(d(120f), d(184f), d(112f), d(210f))
        quadraticTo(d(140f), d(230f), d(190f), d(224f))
        lineTo(d(180f), d(190f))
        quadraticTo(d(170f), d(180f), d(148f), d(180f))
        close()
    }
    stroke(nBody)

    // left arm resting on page
    val nArmL = Path().apply {
        moveTo(d(126f), d(210f))
        quadraticTo(d(115f), d(228f), d(130f), d(240f))
    }
    stroke(nArmL)
    // right arm holding pen
    val nArmR = Path().apply {
        moveTo(d(184f), d(210f))
        quadraticTo(d(210f), d(224f), d(214f), d(238f))
    }
    stroke(nArmR)

    // pen
    val pen = Path().apply {
        moveTo(d(212f), d(238f))
        lineTo(d(232f), d(244f))
    }
    stroke(pen, Accent, 2.6f)
    sketchLine(pt(232f, 244f), pt(238f, 246f), Ink, 2.2f)

    // ── open notebook (bottom) ─────────────────────────────
    val book = Path().apply {
        moveTo(d(52f), d(248f))
        quadraticTo(d(160f), d(238f), d(268f), d(248f))
        lineTo(d(276f), d(296f))
        quadraticTo(d(160f), d(304f), d(44f), d(296f))
        close()
    }
    stroke(book)
    // spine (center crease)
    val spine = Path().apply {
        moveTo(d(160f), d(242f))
        lineTo(d(160f), d(302f))
    }
    stroke(spine, InkSoft, 1.4f)

    // page lines — left page
    sketchLine(pt(70f, 258f), pt(148f, 256f), InkSoft, 1.6f)
    sketchLine(pt(70f, 268f), pt(148f, 266f), InkSoft, 1.6f)
    sketchLine(pt(70f, 278f), pt(140f, 276f), InkSoft, 1.6f)
    // right page — the person is writing, so only 2 lines
    sketchLine(pt(172f, 256f), pt(250f, 258f), Ink, 1.8f)
    sketchLine(pt(172f, 268f), pt(230f, 270f), Ink, 1.8f)

    // little idea dots around bulb — glow with the light
    sketchCircle(pt(102f, 46f), 2.5f, lerp(InkSoft, Accent, glow), filled = true)
    sketchCircle(pt(218f, 42f), 2.2f, lerp(InkSoft, Accent, glow), filled = true)
    twinkle(76f, 90f, 3f, t, 0.15f, InkSoft)
    twinkle(240f, 96f, 3f, t, 0.55f, Accent)

    groundHint(302f)
}

// ─── Scene 5: Build Better Habits ───────────────────────────────────────────
//   A person mid-run, progress ring behind them, streak flame in a corner.

private fun DrawScope.drawHabitsScene(t: Float) {
    // big progress ring (background)
    val ringCx = 160f
    val ringCy = 165f
    val ringR = 118f
    // full ring soft
    sketchCircle(pt(ringCx, ringCy), ringR, InkFaint, 1.6f)
    // progress arc fills the whole ring every loop — 0 → 100% in brand yellow
    val progress = t * t * (3f - 2f * t)   // smoothstep: eases in and out
    val sweep = (359.9f * progress).coerceAtLeast(0.1f)
    val progressArc = Path().apply {
        arcTo(
            rect = Rect(
                pt(ringCx - ringR, ringCy - ringR),
                Size(d(ringR * 2), d(ringR * 2))
            ),
            startAngleDegrees = -90f,
            sweepAngleDegrees = sweep,
            forceMoveTo = true
        )
    }
    drawPath(progressArc, color = Accent, style = bold(3.6f))
    // glowing dot riding the progress tip
    val tipRad = (sweep - 90f) * kotlin.math.PI.toFloat() / 180f
    val tipPt = pt(ringCx + ringR * cos(tipRad), ringCy + ringR * sin(tipRad))
    sketchCircle(tipPt, 5f, Accent.copy(alpha = 0.35f), filled = true)
    sketchCircle(tipPt, 3.2f, Accent, filled = true)

    // ── person running (center) ────────────────────────────
    // head
    sketchCircle(pt(158f, 108f), 20f, width = 2.4f)
    // ponytail streaming back
    val tail = Path().apply {
        moveTo(d(140f), d(102f))
        quadraticTo(d(120f), d(96f), d(108f), d(112f))
    }
    stroke(tail)
    // face
    sketchCircle(pt(166f, 108f), 1.6f, filled = true)
    val determined = Path().apply {
        moveTo(d(164f), d(116f))
        lineTo(d(170f), d(116f))
    }
    stroke(determined, Ink, 1.8f)

    // neck (angled forward)
    sketchLine(pt(154f, 128f), pt(150f, 138f))

    // torso (leaning forward)
    val hBody = Path().apply {
        moveTo(d(150f), d(138f))
        quadraticTo(d(160f), d(160f), d(172f), d(182f))
        quadraticTo(d(154f), d(190f), d(138f), d(184f))
        quadraticTo(d(138f), d(158f), d(150f), d(138f))
        close()
    }
    stroke(hBody)
    // shirt seam
    val hSeam = Path().apply {
        moveTo(d(140f), d(158f))
        quadraticTo(d(155f), d(166f), d(168f), d(168f))
    }
    stroke(hSeam, InkSoft, 1.6f)

    // front arm reaching forward
    val hArmF = Path().apply {
        moveTo(d(152f), d(144f))
        quadraticTo(d(178f), d(140f), d(196f), d(126f))
    }
    stroke(hArmF)
    // front hand
    sketchCircle(pt(198f, 124f), 3f, filled = true)

    // back arm swinging back
    val hArmB = Path().apply {
        moveTo(d(140f), d(150f))
        quadraticTo(d(122f), d(158f), d(110f), d(174f))
    }
    stroke(hArmB)
    sketchCircle(pt(108f, 176f), 3f, filled = true)

    // front leg raised (knee up)
    val hLegF = Path().apply {
        moveTo(d(166f), d(186f))
        quadraticTo(d(196f), d(198f), d(206f), d(220f))
    }
    stroke(hLegF)
    // front foot
    sketchLine(pt(202f, 222f), pt(216f, 218f))

    // back leg pushing off
    val hLegB = Path().apply {
        moveTo(d(148f), d(188f))
        quadraticTo(d(130f), d(210f), d(110f), d(232f))
    }
    stroke(hLegB)
    sketchLine(pt(104f, 234f), pt(120f, 234f))

    // speed lines behind — drifting with the runner's motion
    val drift = 5f * wave(t)
    sketchLine(pt(70f + drift, 130f), pt(96f + drift, 130f), InkSoft, 1.8f)
    sketchLine(pt(60f - drift, 152f), pt(94f - drift, 152f), InkSoft, 1.8f)
    sketchLine(pt(72f + drift, 174f), pt(96f + drift, 174f), InkSoft, 1.8f)

    // ── streak flame (top-right corner) — flickers ─────────
    val flamePivot = pt(278f, 66f)
    withTransform({
        val flicker = 1f + 0.07f * sin(t * 2f * TWO_PI)
        scale(scaleX = flicker, scaleY = flicker, pivot = flamePivot)
    }) {
    val flame = Path().apply {
        moveTo(d(272f), d(88f))
        cubicTo(d(258f), d(70f), d(272f), d(56f), d(266f), d(38f))
        cubicTo(d(282f), d(52f), d(296f), d(72f), d(288f), d(88f))
        cubicTo(d(284f), d(94f), d(276f), d(94f), d(272f), d(88f))
        close()
    }
    stroke(flame)
    // inner flame
    val flameIn = Path().apply {
        moveTo(d(276f), d(84f))
        cubicTo(d(270f), d(74f), d(278f), d(66f), d(276f), d(58f))
        cubicTo(d(284f), d(66f), d(288f), d(78f), d(283f), d(84f))
        cubicTo(d(281f), d(88f), d(278f), d(88f), d(276f), d(84f))
        close()
    }
    stroke(flameIn, Accent, 2.0f)
    }

    // ── little sprout (bottom-left) ────────────────────────
    // stem
    val stem = Path().apply {
        moveTo(d(46f), d(290f))
        quadraticTo(d(48f), d(272f), d(52f), d(258f))
    }
    stroke(stem)
    // left leaf
    val leafA = Path().apply {
        moveTo(d(48f), d(268f))
        quadraticTo(d(32f), d(262f), d(30f), d(250f))
        quadraticTo(d(42f), d(252f), d(48f), d(266f))
        close()
    }
    stroke(leafA)
    // right leaf
    val leafB = Path().apply {
        moveTo(d(52f), d(262f))
        quadraticTo(d(70f), d(258f), d(74f), d(246f))
        quadraticTo(d(60f), d(248f), d(52f), d(260f))
        close()
    }
    stroke(leafB)

    // twinkling stars
    twinkle(230f, 240f, 4f, t, 0.4f, Accent)
    twinkle(96f, 60f, 3f, t, 0.7f, InkSoft)

    groundHint(298f)
}
