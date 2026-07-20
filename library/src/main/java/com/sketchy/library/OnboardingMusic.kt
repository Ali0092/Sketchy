package com.sketchy.library

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope

// ─── Your Soundtrack, Anywhere ───────────────────────────────────────────────
//   A person walking with headphones on, floating music notes and a bouncing
//   equalizer bar chart.

internal fun DrawScope.drawListenAnywhereScene(t: Float) {
    val step = wave(t, 0f)

    // head + headphones
    sketchCircle(pt(150f, 110f), 20f, width = 2.4f)
    val band = Path().apply {
        moveTo(d(132f), d(104f))
        quadraticTo(d(150f), d(84f), d(168f), d(104f))
    }
    stroke(band, Ink, 2.4f)
    sketchCircle(pt(131f, 112f), 8f, Ink, width = 2.2f)
    sketchCircle(pt(169f, 112f), 8f, Ink, width = 2.2f)
    sketchCircle(pt(156f, 110f), 1.5f, filled = true)
    val smile = Path().apply {
        moveTo(d(148f), d(118f))
        quadraticTo(d(151f), d(121f), d(154f), d(118f))
    }
    stroke(smile, Ink, 1.6f)
    sketchLine(pt(150f, 130f), pt(150f, 138f))

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
    stroke(body)

    // walking legs alternate with the step wave
    val legL = Path().apply {
        moveTo(d(138f), d(210f))
        quadraticTo(d(132f + step * 10f), d(234f), d(126f + step * 14f), d(256f))
    }
    val legR = Path().apply {
        moveTo(d(162f), d(210f))
        quadraticTo(d(168f - step * 10f), d(234f), d(174f - step * 14f), d(256f))
    }
    stroke(legL)
    stroke(legR)
    sketchLine(pt(118f + step * 14f, 256f), pt(134f + step * 14f, 256f))
    sketchLine(pt(166f - step * 14f, 256f), pt(182f - step * 14f, 256f))
    val armL = Path().apply {
        moveTo(d(128f), d(158f))
        quadraticTo(d(116f - step * 8f), d(174f), d(120f - step * 10f), d(192f))
    }
    stroke(armL)

    // floating music notes drifting up and fading
    val notes = listOf(0f to 210f, 0.33f to 230f, 0.66f to 250f)
    notes.forEach { (offset, startX) ->
        val phase = (t + offset) % 1f
        val ny = 100f - phase * 80f
        val alpha = (1f - phase).coerceIn(0f, 1f)
        val nx = startX
        sketchLine(pt(nx, ny), pt(nx, ny - 18f), Accent.copy(alpha = alpha), 2.2f)
        sketchCircle(pt(nx - 3f, ny), 4f, Accent.copy(alpha = alpha), filled = true)
        sketchLine(pt(nx, ny - 18f), pt(nx + 6f, ny - 16f), Accent.copy(alpha = alpha), 2.2f)
    }

    // small equalizer bars bouncing beside the person
    val bars = listOf(0f, 0.2f, 0.4f, 0.6f)
    bars.forEachIndexed { i, offset ->
        val h = 8f + 14f * (1f + wave(t, offset)) / 2f
        val x = 216f + i * 12f
        sketchLine(pt(x, 200f), pt(x, 200f - h), AccentTeal, 4f)
    }

    twinkle(70f, 90f, 3f, t, 0.5f, InkSoft)
    groundHint(266f)
}

// ─── Discover New Sounds ──────────────────────────────────────────────────────
//   A person beside a record player, concentric sound waves rippling outward
//   from the speaker.

internal fun DrawScope.drawDiscoverMusicScene(t: Float) {
    // record player base
    val base = Path().apply {
        moveTo(d(70f), d(200f))
        lineTo(d(210f), d(200f))
        lineTo(d(210f), d(230f))
        lineTo(d(70f), d(230f))
        close()
    }
    stroke(base, Ink, 2.4f)

    // spinning vinyl disc
    val spin = (t * 360f) % 360f
    val discCx = 110f
    val discCy = 178f
    sketchCircle(pt(discCx, discCy), 34f, Ink, width = 2.2f)
    sketchCircle(pt(discCx, discCy), 24f, InkFaint, width = 1.4f)
    sketchCircle(pt(discCx, discCy), 14f, InkFaint, width = 1.2f)
    sketchCircle(pt(discCx, discCy), 4f, Accent, filled = true)
    val markRad = spin * kotlin.math.PI.toFloat() / 180f
    val markPt = pt(
        discCx + 30f * kotlin.math.cos(markRad),
        discCy + 30f * kotlin.math.sin(markRad)
    )
    sketchCircle(markPt, 2f, Accent, filled = true)

    // tonearm resting on the disc
    val arm = Path().apply {
        moveTo(d(168f), d(146f))
        quadraticTo(d(150f), d(150f), d(126f), d(168f))
    }
    stroke(arm, Ink, 2.2f)
    sketchCircle(pt(168f, 146f), 6f, Ink, width = 2f)

    // speaker with rippling sound waves
    val speakerCx = 190f
    val speakerCy = 178f
    sketchCircle(pt(speakerCx, speakerCy), 10f, Ink, width = 2.2f)
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
        drawPath(arc, color = AccentTeal.copy(alpha = 0.3f + 0.5f * k), style = thin(1.8f))
    }

    // person nodding along
    sketchCircle(pt(250f, 140f), 19f, width = 2.4f)
    val nod = 3f * wave(t, 0.1f)
    sketchLine(pt(244f, 142f + nod), pt(248f, 145f + nod), Ink, 1.6f)
    sketchLine(pt(252f, 142f + nod), pt(256f, 145f + nod), Ink, 1.6f)
    sketchLine(pt(250f, 159f), pt(250f, 166f))
    val body = Path().apply {
        moveTo(d(250f), d(166f))
        quadraticTo(d(232f), d(170f), d(228f), d(186f))
        lineTo(d(232f), d(224f))
        quadraticTo(d(250f), d(232f), d(270f), d(224f))
        lineTo(d(272f), d(186f))
        quadraticTo(d(268f), d(170f), d(250f), d(166f))
        close()
    }
    stroke(body)

    twinkle(60f, 120f, 3f, t, 0.4f, Accent)
    twinkle(288f, 90f, 3f, t, 0.7f, InkSoft)
    groundHint(240f)
}
