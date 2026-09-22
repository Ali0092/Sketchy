package com.sketchy.library.emptystates

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.sketchy.library.SketchyStyle
import com.sketchy.library.utils.*

/**
 * The **Clock and Time** category: the same cute twin-bell alarm clock every time — bold flat
 * outlines rather than the library's usual gradients/sheens, by design — with only its face,
 * hands and the small prop beside it changing to carry each state, the same way "Lined Man"
 * reuses one figure throughout.
 */

private const val ClockStrokeW = 3.2f
private const val ClockBodyR = 68f
private const val ClockFaceR = 52f

/** The shared clock body — shadow, legs, bells, outer body and face disc — with [face] drawn on top. */
private fun DrawScope.drawAlarmClockBody(
    cx: Float,
    cy: Float,
    colors: SketchyStyle,
    face: DrawScope.() -> Unit
) {
    val ink = colors.ink

    contactShadow(cx, cy + ClockBodyR + 18f, ClockBodyR * 0.85f, 10f, colors.shade)

    listOf(-1f, 1f).forEach { side ->
        val leg = roundRectPath(cx + side * 26f - 7f, cy + ClockBodyR - 8f, 14f, 10f, 4f)
        paint(leg, ink, ink, ClockStrokeW * 0.8f)
    }

    // twin bells, spaced well apart
    listOf(-1f, 1f).forEach { side ->
        val bellCx = cx + side * 39f
        val bellCy = cy - ClockBodyR - 4f
        paintCircle(pt(bellCx, bellCy), 18f, colors.plum, ink, ClockStrokeW)
    }
    paintCircle(pt(cx, cy - ClockBodyR - 4f), 5f, colors.plumDark, ink, ClockStrokeW * 0.7f)

    val body = ellipsePath(cx, cy, ClockBodyR, ClockBodyR)
    inkShadow(body, colors.outlineShadow)
    cornerShade(body, cx, cy, ClockBodyR * 1.05f, colors.outlineShadow.a(colors.outlineShadow.alpha * 0.6f))
    paint(body, colors.plum, ink, ClockStrokeW)

    paintCircle(pt(cx, cy), ClockFaceR, colors.paper, ink, ClockStrokeW)

    face()
}

/** A closed, sleepy/content eye — a gentle downward curve. */
private fun DrawScope.closedClockEye(ex: Float, ey: Float, colors: SketchyStyle, dip: Float = 6f) {
    val eye = Path().apply {
        moveTo(d(ex - 9f), d(ey))
        quadraticTo(d(ex), d(ey + dip), d(ex + 9f), d(ey))
    }
    drawPath(eye, color = colors.ink, style = bold(2.6f))
}

/** A closed, happy winking eye — a gentle upward curve. */
private fun DrawScope.winkClockEye(ex: Float, ey: Float, colors: SketchyStyle) {
    val wink = Path().apply {
        moveTo(d(ex - 9f), d(ey + 2f))
        quadraticTo(d(ex), d(ey - 8f), d(ex + 9f), d(ey + 2f))
    }
    drawPath(wink, color = colors.ink, style = bold(2.6f))
}

/** An open, awake eye — a ring with a small pupil. */
private fun DrawScope.openClockEye(
    ex: Float,
    ey: Float,
    colors: SketchyStyle,
    ringR: Float = 10f,
    pupilX: Float = 0f,
    pupilY: Float = 0f
) {
    paintCircle(pt(ex, ey), ringR, colors.paper, colors.ink, 3f)
    sketchCircle(pt(ex + pupilX, ey + pupilY), ringR * 0.36f, colors.ink, filled = true)
}

/** A tired little "x" eye. */
private fun DrawScope.tiredClockEye(ex: Float, ey: Float, colors: SketchyStyle, r: Float = 7f) {
    sketchLine(pt(ex - r, ey - r), pt(ex + r, ey + r), colors.ink, 2.4f)
    sketchLine(pt(ex - r, ey + r), pt(ex + r, ey - r), colors.ink, 2.4f)
}

/** A blank (or one-cell-highlighted) calendar card, ring-bound at the top. */
private fun DrawScope.drawCalendarCard(
    x: Float,
    y: Float,
    w: Float,
    h: Float,
    colors: SketchyStyle,
    highlightRow: Int = -1,
    highlightCol: Int = -1
) {
    val ink = colors.ink
    contactShadow(x + w / 2f, y + h + 8f, w * 0.5f, 6f, colors.shade)

    val headerH = h * 0.22f
    paint(roundRectPath(x, y, w, h, 10f), colors.paper, ink, 2.4f)
    paint(roundRectPath(x, y, w, headerH, 10f), colors.plum, ink, 2.4f)
    listOf(0.28f, 0.72f).forEach { f ->
        sketchCircle(pt(x + w * f, y), 4f, colors.plumDark, width = 2f)
    }

    val cols = 3
    val rows = 2
    val gridTop = y + headerH + 10f
    val gridH = h - headerH - 18f
    for (r in 0..rows) {
        val gy = gridTop + gridH * r / rows
        sketchLine(pt(x + 8f, gy), pt(x + w - 8f, gy), colors.inkFaint, 1.2f)
    }
    for (c in 0..cols) {
        val gx = x + w * c / cols
        sketchLine(pt(gx, gridTop), pt(gx, gridTop + gridH), colors.inkFaint, 1.2f)
    }
    if (highlightRow in 0 until rows && highlightCol in 0 until cols) {
        val hx = x + w * (highlightCol + 0.5f) / cols
        val hy = gridTop + gridH * (highlightRow + 0.5f) / rows
        sketchCircle(pt(hx, hy), 6f, colors.touch(colors.accent, 0.9f), filled = true)
    }
}

// ─── All Quiet ──────────────────────────────────────────────────────────────
//   Fast asleep — closed eyes, a small resting mouth, drooping hands — leaning
//   against a soft pillow and a blanket peeking out behind it.

internal fun DrawScope.drawClockAllQuiet(t: Float, colors: SketchyStyle) {
    val ink = colors.ink
    val cx = 132f
    val cy = 176f

    val pillowCx = 246f
    val pillowCy = 196f
    val pillowW = 132f
    val pillowH = 118f
    val pillowX = pillowCx - pillowW / 2f
    val pillowY = pillowCy - pillowH / 2f

    val blanket = roundRectPath(pillowX + 16f, pillowY + 22f, pillowW * 0.9f, pillowH * 0.88f, pillowH * 0.4f)
    paint(blanket, colors.plum, ink, ClockStrokeW)
    val pillow = roundRectPath(pillowX, pillowY, pillowW * 0.9f, pillowH * 0.88f, pillowH * 0.42f)
    paint(pillow, colors.paper, ink, ClockStrokeW)
    listOf(0.34f, 0.62f).forEach { f ->
        val fy = pillowY + pillowH * f
        val fold = Path().apply {
            moveTo(d(pillowX + 16f), d(fy))
            quadraticTo(d(pillowX + pillowW * 0.42f), d(fy + 9f), d(pillowX + pillowW * 0.72f), d(fy - 4f))
        }
        drawPath(fold, color = colors.faint(ink), style = thin(1.8f))
    }

    drawAlarmClockBody(cx, cy, colors) {
        listOf(-1f, 1f).forEach { side -> closedClockEye(cx + side * 17f, cy - 17f, colors, dip = 7f) }
        sketchLine(pt(cx, cy), pt(cx - 17f, cy + 13f), ink, ClockStrokeW * 0.75f)
        sketchLine(pt(cx, cy), pt(cx + 23f, cy + 8f), ink, ClockStrokeW * 0.75f)
        sketchCircle(pt(cx, cy), 3f, ink, filled = true)
        val mouth = Path().apply {
            moveTo(d(cx - 9f), d(cy + 31f))
            quadraticTo(d(cx), d(cy + 36f), d(cx + 9f), d(cy + 31f))
        }
        drawPath(mouth, color = ink, style = bold(ClockStrokeW * 0.7f))
    }

    listOf(0f to 15f, 0.33f to 21f, 0.66f to 28f).forEachIndexed { i, (offset, size) ->
        val phase = loop(t, offset)
        val zx = cx + 46f + i * 20f + phase * 10f
        val zy = cy - ClockBodyR - 46f - i * 26f - phase * 14f
        val alpha = 1f - phase
        val half = size / 2f
        val z = Path().apply {
            moveTo(d(zx - half), d(zy - half))
            lineTo(d(zx + half), d(zy - half))
            lineTo(d(zx - half), d(zy + half))
            lineTo(d(zx + half), d(zy + half))
        }
        drawPath(z, color = colors.touch(colors.plum, alpha), style = bold(ClockStrokeW * 0.6f))
    }
}

// ─── No Alarms Set ──────────────────────────────────────────────────────────
//   Calm and awake under a quiet night sky — a full moon and a couple of
//   stars — nothing's been set to go off.

internal fun DrawScope.drawClockNoAlarms(t: Float, colors: SketchyStyle) {
    val ink = colors.ink
    val cx = 160f
    val cy = 184f

    paintCircle(pt(80f, 84f), 24f, colors.paper, ink, 2.4f)
    twinkle(56f, 130f, 3f, t, 0.3f, colors.inkSoft)
    twinkle(232f, 96f, 3f, t, 0.7f, colors.inkSoft)

    drawAlarmClockBody(cx, cy, colors) {
        val drift = 1.5f * wave(t, 0f)
        listOf(-1f, 1f).forEach { side -> openClockEye(cx + side * 17f, cy - 15f, colors, pupilX = drift) }
        val mouth = Path().apply {
            moveTo(d(cx - 8f), d(cy + 28f))
            quadraticTo(d(cx), d(cy + 31f), d(cx + 8f), d(cy + 28f))
        }
        drawPath(mouth, color = ink, style = bold(ClockStrokeW * 0.7f))
        sketchLine(pt(cx, cy), pt(cx - 12f, cy - 18f), ink, ClockStrokeW * 0.75f)
        sketchLine(pt(cx, cy), pt(cx + 18f, cy - 12f), ink, ClockStrokeW * 0.75f)
        sketchCircle(pt(cx, cy), 3f, ink, filled = true)
    }

    groundLine(cy + ClockBodyR + 40f, colors.inkFaint)
}

// ─── Nothing Scheduled ──────────────────────────────────────────────────────
//   A relaxed, open-eyed clock beside a blank calendar page — the day's
//   completely open.

internal fun DrawScope.drawClockNothingScheduled(t: Float, colors: SketchyStyle) {
    val ink = colors.ink
    val cx = 124f
    val cy = 180f

    drawCalendarCard(214f, 122f, 96f, 108f, colors)

    drawAlarmClockBody(cx, cy, colors) {
        val blink = loop(t, 0.5f) > 0.93f
        if (blink) {
            listOf(-1f, 1f).forEach { side -> closedClockEye(cx + side * 17f, cy - 15f, colors, dip = 5f) }
        } else {
            listOf(-1f, 1f).forEach { side -> openClockEye(cx + side * 17f, cy - 15f, colors) }
        }
        val mouth = Path().apply {
            moveTo(d(cx - 10f), d(cy + 27f))
            quadraticTo(d(cx), d(cy + 34f), d(cx + 10f), d(cy + 27f))
        }
        drawPath(mouth, color = ink, style = bold(ClockStrokeW * 0.7f))
        sketchLine(pt(cx, cy), pt(cx - 16f, cy - 20f), ink, ClockStrokeW * 0.75f)
        sketchLine(pt(cx, cy), pt(cx + 20f, cy - 16f), ink, ClockStrokeW * 0.75f)
        sketchCircle(pt(cx, cy), 3f, ink, filled = true)
    }

    groundLine(cy + ClockBodyR + 40f, colors.inkFaint)
}

// ─── No Reminders Yet ───────────────────────────────────────────────────────
//   A content clock beside a blank notepad, corner folded, nothing written on
//   it yet.

internal fun DrawScope.drawClockNoReminders(t: Float, colors: SketchyStyle) {
    val ink = colors.ink
    val cx = 124f
    val cy = 180f

    val padX = 214f
    val padY = 128f
    val padW = 96f
    val padH = 100f
    contactShadow(padX + padW / 2f, padY + padH + 8f, padW * 0.5f, 6f, colors.shade)
    paint(roundRectPath(padX, padY, padW, padH, 10f), colors.paper, ink, 2.4f)
    val fold = Path().apply {
        moveTo(d(padX + padW - 22f), d(padY))
        lineTo(d(padX + padW), d(padY))
        lineTo(d(padX + padW), d(padY + 22f))
        close()
    }
    paint(fold, colors.paper.shaded(0.14f), ink, 1.8f)
    for (i in 0 until 3) {
        val ly = padY + 38f + i * 18f
        sketchLine(pt(padX + 14f, ly), pt(padX + padW - 14f, ly), colors.inkFaint, 1.4f)
    }

    drawAlarmClockBody(cx, cy, colors) {
        listOf(-1f, 1f).forEach { side -> closedClockEye(cx + side * 17f, cy - 16f, colors, dip = 4f) }
        val mouth = Path().apply {
            moveTo(d(cx - 9f), d(cy + 28f))
            quadraticTo(d(cx), d(cy + 33f), d(cx + 9f), d(cy + 28f))
        }
        drawPath(mouth, color = ink, style = bold(ClockStrokeW * 0.7f))
        sketchLine(pt(cx, cy), pt(cx - 8f, cy - 21f), ink, ClockStrokeW * 0.75f)
        sketchLine(pt(cx, cy), pt(cx + 21f, cy - 6f), ink, ClockStrokeW * 0.75f)
        sketchCircle(pt(cx, cy), 3f, ink, filled = true)
    }

    groundLine(cy + ClockBodyR + 40f, colors.inkFaint)
}

// ─── Session Expired ────────────────────────────────────────────────────────
//   A dizzy, tired clock beside an hourglass that's already run all the way
//   out — time's up on this one.

internal fun DrawScope.drawClockSessionExpired(t: Float, colors: SketchyStyle) {
    val ink = colors.ink
    val cx = 124f
    val cy = 184f

    val hgX = 232f
    val hgTopY = 130f
    val hgW = 64f
    val hgH = 96f
    contactShadow(hgX, hgTopY + hgH + 10f, 40f, 6f, colors.shade)
    paint(roundRectPath(hgX - hgW / 2f, hgTopY, hgW, 10f, 4f), colors.wood, ink, 2.2f)
    paint(roundRectPath(hgX - hgW / 2f, hgTopY + hgH - 10f, hgW, 10f, 4f), colors.wood, ink, 2.2f)
    val glass = Path().apply {
        moveTo(d(hgX - hgW / 2f + 6f), d(hgTopY + 10f))
        lineTo(d(hgX + hgW / 2f - 6f), d(hgTopY + 10f))
        lineTo(d(hgX + 6f), d(hgTopY + hgH / 2f))
        lineTo(d(hgX + hgW / 2f - 6f), d(hgTopY + hgH - 10f))
        lineTo(d(hgX - hgW / 2f + 6f), d(hgTopY + hgH - 10f))
        lineTo(d(hgX - 6f), d(hgTopY + hgH / 2f))
        close()
    }
    paint(glass, colors.paper.a(0.5f), ink, 2.2f)
    val sand = Path().apply {
        moveTo(d(hgX - hgW / 2f + 10f), d(hgTopY + hgH - 12f))
        lineTo(d(hgX + hgW / 2f - 10f), d(hgTopY + hgH - 12f))
        lineTo(d(hgX), d(hgTopY + hgH / 2f + 14f))
        close()
    }
    paint(sand, colors.sun, colors.inkOf(colors.sun), 1.6f)

    drawAlarmClockBody(cx, cy, colors) {
        val jitter = 1f * wave(t * 6f, 0f)
        listOf(-1f, 1f).forEach { side -> tiredClockEye(cx + side * 17f + jitter, cy - 15f, colors) }
        val mouth = Path().apply {
            moveTo(d(cx - 9f), d(cy + 30f))
            quadraticTo(d(cx - 3f), d(cy + 26f), d(cx + 2f), d(cy + 30f))
            quadraticTo(d(cx + 6f), d(cy + 33f), d(cx + 9f), d(cy + 29f))
        }
        drawPath(mouth, color = ink, style = bold(ClockStrokeW * 0.65f))
        sketchLine(pt(cx, cy), pt(cx - 4f, cy + 22f), ink, ClockStrokeW * 0.75f)
        sketchLine(pt(cx, cy), pt(cx + 4f, cy + 24f), ink, ClockStrokeW * 0.75f)
        sketchCircle(pt(cx, cy), 3f, ink, filled = true)
    }

    groundLine(cy + ClockBodyR + 40f, colors.inkFaint)
}

// ─── Time's Up ──────────────────────────────────────────────────────────────
//   Wide-eyed and startled, hands straight up at the top of the hour, bells
//   ringing — the alarm's actually going off.

internal fun DrawScope.drawClockTimeUp(t: Float, colors: SketchyStyle) {
    val ink = colors.ink
    val cx = 160f
    val cy = 184f

    listOf(-1f, 1f).forEach { side ->
        val bellCx = cx + side * 39f
        val bellCy = cy - ClockBodyR - 4f
        val k = 0.35f + 0.45f * pulse(t, if (side < 0f) 0f else 0.3f)
        val arc = Path().apply {
            arcTo(
                rect = Rect(pt(bellCx - 30f, bellCy - 30f), Size(d(60f), d(60f))),
                startAngleDegrees = if (side < 0f) 210f else -30f,
                sweepAngleDegrees = 60f,
                forceMoveTo = true
            )
        }
        drawPath(arc, color = colors.touch(colors.accentRed, k), style = thin(2.2f))
    }

    drawAlarmClockBody(cx, cy, colors) {
        val shake = 1.5f * wave(t * 8f, 0f)
        listOf(-1f, 1f).forEach { side -> openClockEye(cx + side * 17f + shake, cy - 16f, colors, ringR = 12f) }
        paintCircle(pt(cx, cy + 30f), 6f + 1.5f * pulse(t, 0f), colors.paper, ink, 2.4f)
        sketchLine(pt(cx, cy), pt(cx, cy - 26f), ink, ClockStrokeW * 0.75f)
        sketchLine(pt(cx, cy), pt(cx + 20f, cy - 6f), ink, ClockStrokeW * 0.75f)
        sketchCircle(pt(cx, cy), 3f, ink, filled = true)
    }

    groundLine(cy + ClockBodyR + 40f, colors.inkFaint)
}

// ─── All Caught Up ──────────────────────────────────────────────────────────
//   A cheerful, winking clock beside a fully checked-off list card — nothing
//   left on the clock.

internal fun DrawScope.drawClockAllCaughtUp(t: Float, colors: SketchyStyle) {
    val ink = colors.ink
    val cx = 124f
    val cy = 180f

    val cardX = 212f
    val cardY = 120f
    val cardW = 96f
    val cardH = 112f
    contactShadow(cardX + cardW / 2f, cardY + cardH + 8f, cardW * 0.5f, 6f, colors.shade)
    paint(roundRectPath(cardX, cardY, cardW, cardH, 10f), colors.paper, ink, 2.4f)
    for (i in 0 until 3) {
        val ry = cardY + 26f + i * 28f
        val box = roundRectPath(cardX + 14f, ry - 8f, 16f, 16f, 3f)
        paint(box, colors.touch(colors.accentGreen, 0.25f), ink, 1.8f)
        val check = Path().apply {
            moveTo(d(cardX + 16f), d(ry))
            lineTo(d(cardX + 20f), d(ry + 4f))
            lineTo(d(cardX + 27f), d(ry - 6f))
        }
        drawPath(check, color = colors.touch(colors.accentGreen, 0.9f), style = bold(1.8f))
        sketchLine(pt(cardX + 36f, ry), pt(cardX + cardW - 12f, ry), colors.inkFaint, 1.6f)
    }

    drawAlarmClockBody(cx, cy, colors) {
        val cheer = pulse(t, 0f)
        winkClockEye(cx - 17f, cy - 15f, colors)
        openClockEye(cx + 17f, cy - 15f, colors)
        val stretch = 3f * cheer
        val mouth = Path().apply {
            moveTo(d(cx - 12f - stretch), d(cy + 26f))
            quadraticTo(d(cx), d(cy + 38f + stretch), d(cx + 12f + stretch), d(cy + 26f))
        }
        drawPath(mouth, color = ink, style = bold(ClockStrokeW * 0.7f))
        sketchLine(pt(cx, cy), pt(cx - 22f, cy - 8f), ink, ClockStrokeW * 0.75f)
        sketchLine(pt(cx, cy), pt(cx + 20f, cy - 14f), ink, ClockStrokeW * 0.75f)
        sketchCircle(pt(cx, cy), 3f, ink, filled = true)
    }

    twinkle(66f, 108f, 3.5f, t, 0.2f, colors.touch(colors.sun))
    twinkle(70f, 236f, 3f, t, 0.6f, colors.touch(colors.accentBlue, 0.8f))
    groundLine(cy + ClockBodyR + 40f, colors.inkFaint)
}

// ─── Coming Soon ────────────────────────────────────────────────────────────
//   A clock winking beside a calendar with one date already marked — it's on
//   its way, just not quite here yet.

internal fun DrawScope.drawClockComingSoon(t: Float, colors: SketchyStyle) {
    val ink = colors.ink
    val cx = 124f
    val cy = 180f

    drawCalendarCard(212f, 122f, 96f, 108f, colors, highlightRow = 0, highlightCol = 2)

    drawAlarmClockBody(cx, cy, colors) {
        winkClockEye(cx - 17f, cy - 15f, colors)
        openClockEye(cx + 17f, cy - 15f, colors)
        val mouth = Path().apply {
            moveTo(d(cx - 9f), d(cy + 27f))
            quadraticTo(d(cx), d(cy + 33f), d(cx + 9f), d(cy + 27f))
        }
        drawPath(mouth, color = ink, style = bold(ClockStrokeW * 0.7f))
        sketchLine(pt(cx, cy), pt(cx, cy - 24f), ink, ClockStrokeW * 0.75f)
        sketchLine(pt(cx, cy), pt(cx + 22f, cy), ink, ClockStrokeW * 0.75f)
        sketchCircle(pt(cx, cy), 3f, ink, filled = true)
    }

    twinkle(78f, 118f, 3f, t, 0.4f, colors.inkSoft)
    groundLine(cy + ClockBodyR + 40f, colors.inkFaint)
}
