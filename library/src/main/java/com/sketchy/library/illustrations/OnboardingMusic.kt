package com.sketchy.library.illustrations

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.sketchy.library.SketchyColors
import com.sketchy.library.utils.*

// ─── Your Soundtrack, Anywhere ───────────────────────────────────────────────
//   A person walking with headphones on, floating music notes and a bouncing
//   equalizer bar chart.

internal fun DrawScope.drawListenAnywhereScene(t: Float, colors: SketchyColors) {
    val step = wave(t, 0f)

    // head + headphones
    sketchCircle(pt(150f, 110f), 20f, colors.ink, width = 2.4f)
    val band = Path().apply {
        moveTo(d(132f), d(104f))
        quadraticTo(d(150f), d(84f), d(168f), d(104f))
    }
    stroke(band, colors.ink, 2.4f)
    sketchCircle(pt(131f, 112f), 8f, colors.ink, width = 2.2f)
    sketchCircle(pt(169f, 112f), 8f, colors.ink, width = 2.2f)
    sketchCircle(pt(156f, 110f), 1.5f, colors.ink, filled = true)
    val smile = Path().apply {
        moveTo(d(148f), d(118f))
        quadraticTo(d(151f), d(121f), d(154f), d(118f))
    }
    stroke(smile, colors.ink, 1.6f)
    sketchLine(pt(150f, 130f), pt(150f, 138f), colors.ink)

    // walking body, gentle side-to-side sway
    val body = Path().apply {
        moveTo(d(150f), d(138f))
        quadraticTo(d(130f), d(142f), d(126f), d(156f))
        lineTo(d(128f + step * 4f), d(206f))
        quadraticTo(d(150f), d(216f), d(172f), d(206f))
        lineTo(d(174f - step * 4f), d(156f))
        quadraticTo(d(170f), d(142f), d(150f), d(138f))
        close()
    }
    stroke(body, colors.ink)

    // walking legs alternate with the step wave
    val legL = Path().apply {
        moveTo(d(138f), d(210f))
        quadraticTo(d(132f + step * 10f), d(234f), d(126f + step * 14f), d(256f))
    }
    val legR = Path().apply {
        moveTo(d(162f), d(210f))
        quadraticTo(d(168f - step * 10f), d(234f), d(174f - step * 14f), d(256f))
    }
    stroke(legL, colors.ink)
    stroke(legR, colors.ink)
    sketchLine(pt(118f + step * 14f, 256f), pt(134f + step * 14f, 256f), colors.ink)
    sketchLine(pt(166f - step * 14f, 256f), pt(182f - step * 14f, 256f), colors.ink)
    val armL = Path().apply {
        moveTo(d(128f), d(158f))
        quadraticTo(d(116f - step * 8f), d(174f), d(120f - step * 10f), d(192f))
    }
    stroke(armL, colors.ink)

    // floating music notes drifting up and fading
    val notes = listOf(0f to 210f, 0.33f to 230f, 0.66f to 250f)
    notes.forEach { (offset, startX) ->
        val phase = (t + offset) % 1f
        val ny = 100f - phase * 80f
        val alpha = (1f - phase).coerceIn(0f, 1f)
        val nx = startX
        sketchLine(pt(nx, ny), pt(nx, ny - 18f), colors.accent.copy(alpha = alpha), 2.2f)
        sketchCircle(pt(nx - 3f, ny), 4f, colors.accent.copy(alpha = alpha), filled = true)
        sketchLine(pt(nx, ny - 18f), pt(nx + 6f, ny - 16f), colors.accent.copy(alpha = alpha), 2.2f)
    }

    // small equalizer bars bouncing beside the person
    val bars = listOf(0f, 0.2f, 0.4f, 0.6f)
    bars.forEachIndexed { i, offset ->
        val h = 8f + 14f * (1f + wave(t, offset)) / 2f
        val x = 216f + i * 12f
        sketchLine(pt(x, 200f), pt(x, 200f - h), colors.accentSecondary, 4f)
    }

    twinkle(70f, 90f, 3f, t, 0.5f, colors.inkSoft)
    groundHint(266f, colors.inkFaint)
}

// ─── Discover New Sounds ──────────────────────────────────────────────────────
//   A person beside a record player, concentric sound waves rippling outward
//   from the speaker.

internal fun DrawScope.drawDiscoverMusicScene(t: Float, colors: SketchyColors) {
    // record player base
    val base = Path().apply {
        moveTo(d(70f), d(200f))
        lineTo(d(210f), d(200f))
        lineTo(d(210f), d(230f))
        lineTo(d(70f), d(230f))
        close()
    }
    stroke(base, colors.ink, 2.4f)

    // spinning vinyl disc
    val spin = (t * 360f) % 360f
    val discCx = 110f
    val discCy = 178f
    sketchCircle(pt(discCx, discCy), 34f, colors.ink, width = 2.2f)
    sketchCircle(pt(discCx, discCy), 24f, colors.inkFaint, width = 1.4f)
    sketchCircle(pt(discCx, discCy), 14f, colors.inkFaint, width = 1.2f)
    sketchCircle(pt(discCx, discCy), 4f, colors.accent, filled = true)
    val markRad = spin * kotlin.math.PI.toFloat() / 180f
    val markPt = pt(
        discCx + 30f * kotlin.math.cos(markRad),
        discCy + 30f * kotlin.math.sin(markRad)
    )
    sketchCircle(markPt, 2f, colors.accent, filled = true)

    // tonearm resting on the disc
    val arm = Path().apply {
        moveTo(d(168f), d(146f))
        quadraticTo(d(150f), d(150f), d(126f), d(168f))
    }
    stroke(arm, colors.ink, 2.2f)
    sketchCircle(pt(168f, 146f), 6f, colors.ink, width = 2f)

    // speaker with rippling sound waves
    val speakerCx = 190f
    val speakerCy = 178f
    sketchCircle(pt(speakerCx, speakerCy), 10f, colors.ink, width = 2.2f)
    for (i in 0..2) {
        val r = 22f + i * 16f
        val k = (1f + wave(t, i * 0.2f)) / 2f
        val arcRect = Rect(
            pt(speakerCx - r, speakerCy - r),
            Size(d(r * 2), d(r * 2))
        )
        val arc = Path().apply {
            arcTo(arcRect, -40f, 80f, forceMoveTo = true)
        }
        drawPath(arc, color = colors.accentSecondary.copy(alpha = 0.3f + 0.5f * k), style = thin(1.8f))
    }

    // person nodding along
    sketchCircle(pt(250f, 140f), 19f, colors.ink, width = 2.4f)
    val nod = 3f * wave(t, 0.1f)
    sketchLine(pt(244f, 142f + nod), pt(248f, 145f + nod), colors.ink, 1.6f)
    sketchLine(pt(252f, 142f + nod), pt(256f, 145f + nod), colors.ink, 1.6f)
    sketchLine(pt(250f, 159f), pt(250f, 166f), colors.ink)
    val body = Path().apply {
        moveTo(d(250f), d(166f))
        quadraticTo(d(232f), d(170f), d(228f), d(186f))
        lineTo(d(232f), d(224f))
        quadraticTo(d(250f), d(232f), d(270f), d(224f))
        lineTo(d(272f), d(186f))
        quadraticTo(d(268f), d(170f), d(250f), d(166f))
        close()
    }
    stroke(body, colors.ink)

    twinkle(60f, 120f, 3f, t, 0.4f, colors.accent)
    twinkle(288f, 90f, 3f, t, 0.7f, colors.inkSoft)
    groundHint(240f, colors.inkFaint)
}
