package com.sketchy.library.emptystates

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.withTransform
import com.sketchy.library.SketchyColors
import com.sketchy.library.utils.*

// ─── Empty Inbox ──────────────────────────────────────────────────────────────
//   An open envelope with a paper airplane drifting away from it.

internal fun DrawScope.drawEmptyInbox(t: Float, colors: SketchyColors) {
    val envelope = Path().apply {
        moveTo(d(84f), d(120f))
        lineTo(d(236f), d(120f))
        lineTo(d(236f), d(206f))
        lineTo(d(84f), d(206f))
        close()
    }
    stroke(envelope, colors.ink, 2.4f)
    val flapOpen = 0.3f + 0.7f * (1f + wave(t, 0f)) / 2f
    val flap = Path().apply {
        moveTo(d(84f), d(120f))
        lineTo(d(160f), d(120f + 40f * flapOpen))
        lineTo(d(236f), d(120f))
    }
    stroke(flap, colors.inkSoft, 2f)

    val fly = t % 1f
    val planeX = 200f + fly * 60f
    val planeY = 90f - fly * 50f
    val planeAlpha = 1f - fly
    val planePivot = pt(planeX, planeY)
    withTransform({ rotate(degrees = -24f, pivot = planePivot) }) {
        val plane = Path().apply {
            moveTo(d(planeX - 16f), d(planeY))
            lineTo(d(planeX + 16f), d(planeY - 6f))
            lineTo(d(planeX - 4f), d(planeY + 4f))
            close()
        }
        drawPath(plane, color = colors.accent.copy(alpha = planeAlpha), style = Fill)
        stroke(plane, colors.ink.copy(alpha = planeAlpha), 1.6f)
    }

    twinkle(90f, 80f, 3f, t, 0.4f, colors.inkSoft)
    groundLine(232f, colors.inkFaint)
}

// ─── No Notifications ─────────────────────────────────────────────────────────
//   A bell swinging like a pendulum, with a small "all caught up" check.

internal fun DrawScope.drawNoNotifications(t: Float, colors: SketchyColors) {
    val pivot = pt(160f, 82f)
    val swing = 10f * wave(t, 0f)
    withTransform({ rotate(degrees = swing, pivot = pivot) }) {
        val bell = Path().apply {
            moveTo(d(122f), d(170f))
            quadraticTo(d(122f), d(110f), d(160f), d(96f))
            quadraticTo(d(198f), d(110f), d(198f), d(170f))
            close()
        }
        stroke(bell, colors.ink, 2.4f)
        sketchLine(pt(116f, 170f), pt(204f, 170f), colors.ink, 2.4f)
        sketchCircle(pt(160f, 184f), 9f, colors.ink, width = 2.2f)
        sketchLine(pt(160f, 86f), pt(160f, 74f), colors.ink, 2.2f)
    }

    val checkPop = 1f + 0.2f * (1f - ((t % 1f) - 0.5f).let { if (it < 0) -it else it } * 2f)
    val checkPivot = pt(220f, 200f)
    withTransform({ scale(scaleX = checkPop, scaleY = checkPop, pivot = checkPivot) }) {
        sketchCircle(pt(220f, 200f), 16f, colors.accentSecondary, width = 2.2f)
        sketchLine(pt(213f, 200f), pt(218f, 206f), colors.accentSecondary, 2.4f)
        sketchLine(pt(218f, 206f), pt(228f, 192f), colors.accentSecondary, 2.4f)
    }

    twinkle(90f, 130f, 3f, t, 0.5f, colors.inkSoft)
    groundLine(230f, colors.inkFaint)
}

// ─── Empty Calendar ───────────────────────────────────────────────────────────
//   A wide-open calendar page with a pulsing "today" ring and no events.

internal fun DrawScope.drawEmptyCalendar(t: Float, colors: SketchyColors) {
    val cal = Path().apply {
        moveTo(d(84f), d(102f))
        lineTo(d(236f), d(102f))
        lineTo(d(236f), d(226f))
        lineTo(d(84f), d(226f))
        close()
    }
    stroke(cal, colors.ink, 2.4f)
    sketchLine(pt(84f, 130f), pt(236f, 130f), colors.ink, 2.2f)
    sketchCircle(pt(112f, 96f), 3f, colors.ink, filled = true)
    sketchCircle(pt(208f, 96f), 3f, colors.ink, filled = true)

    for (row in 0..2) {
        for (col in 0..4) {
            sketchCircle(
                pt(108f + col * 26f, 154f + row * 24f), 2f,
                colors.inkFaint, filled = true
            )
        }
    }

    val pulse = (1f + wave(t, 0f)) / 2f
    sketchCircle(pt(160f, 178f), 14f + 4f * pulse, colors.accent.copy(alpha = 0.5f + 0.5f * pulse), width = 2.2f)

    twinkle(64f, 190f, 3f, t, 0.4f, colors.inkSoft)
    groundLine(244f, colors.inkFaint)
}

// ─── No Photos ────────────────────────────────────────────────────────────────
//   A tilting picture frame around a faint, dashed mountain scene.

internal fun DrawScope.drawNoPhotos(t: Float, colors: SketchyColors) {
    val tilt = 3f * wave(t, 0f)
    val framePivot = pt(160f, 160f)
    withTransform({ rotate(degrees = tilt, pivot = framePivot) }) {
        val frame = Path().apply {
            moveTo(d(90f), d(110f))
            lineTo(d(230f), d(110f))
            lineTo(d(230f), d(210f))
            lineTo(d(90f), d(210f))
            close()
        }
        stroke(frame, colors.ink, 2.4f)

        val mountains = Path().apply {
            moveTo(d(100f), d(196f))
            lineTo(d(140f), d(150f))
            lineTo(d(166f), d(178f))
            lineTo(d(196f), d(140f))
            lineTo(d(222f), d(196f))
        }
        drawPath(mountains, color = colors.inkFaint, style = dashed())
        sketchCircle(pt(200f, 132f), 10f, colors.accent.copy(alpha = 0.6f), width = 1.8f)
    }

    twinkle(70f, 90f, 3f, t, 0.5f, colors.inkSoft)
    groundLine(240f, colors.inkFaint)
}

// ─── All Done ─────────────────────────────────────────────────────────────────
//   A big checkmark that pops inside a ring, with confetti sparkles around it.

internal fun DrawScope.drawAllDone(t: Float, colors: SketchyColors) {
    val cx = 160f
    val cy = 160f
    val pop = (t % 1f)
    val scale = if (pop < 0.15f) 0.85f + 0.15f * (pop / 0.15f) else 1f
    val pivot = pt(cx, cy)

    withTransform({ scale(scaleX = scale, scaleY = scale, pivot = pivot) }) {
        sketchCircle(pt(cx, cy), 64f, colors.ink, width = 2.6f)
        sketchLine(pt(cx - 28f, cy), pt(cx - 8f, cy + 22f), colors.accent, 4f)
        sketchLine(pt(cx - 8f, cy + 22f), pt(cx + 32f, cy - 22f), colors.accent, 4f)
    }

    val confetti = listOf(
        Triple(90f, 90f, colors.accent),
        Triple(230f, 100f, colors.accentSecondary),
        Triple(96f, 220f, colors.accentSecondary),
        Triple(228f, 224f, colors.accent),
    )
    confetti.forEachIndexed { i, (x, y, c) -> twinkle(x, y, 4f, t, i * 0.2f, c) }

    groundLine(252f, colors.inkFaint)
}
