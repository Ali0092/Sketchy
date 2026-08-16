package com.sketchy.library.emptystates

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import com.sketchy.library.SketchyStyle
import com.sketchy.library.utils.*

// ─── Lined Man ──────────────────────────────────────────────────────────────
//   A minimal standing figure: one continuous open outline (up the left leg,
//   over a rounded head, down the right leg — no closing line across the
//   bottom). Every state below is the exact same body; only the eyes and
//   mouth change (and animate) to carry the mood.

/**
 * Draws the shared Lined Man body (shadow, sway, silhouette, ground line) and calls [face] inside
 * the same swaying transform so a state's eyes/mouth move with the body.
 */
private fun DrawScope.drawLinedMan(t: Float, colors: SketchyStyle, face: DrawScope.() -> Unit) {
    val sway = 2f * wave(t, 0f)
    val pivot = pt(160f, 270f)

    contactShadow(160f, 272f, 46f, 7f, colors.shade)

    withTransform({ rotate(degrees = sway, pivot = pivot) }) {
        val body = Path().apply {
            moveTo(d(96f), d(270f))
            lineTo(d(96f), d(124f))
            cubicTo(d(96f), d(90f), d(123f), d(70f), d(160f), d(70f))
            cubicTo(d(197f), d(70f), d(224f), d(90f), d(224f), d(124f))
            lineTo(d(224f), d(270f))
        }
        inkShadow(body, colors.outlineShadow)
        cornerShade(body, 160f, 170f, 78f, colors.outlineShadow.a(colors.outlineShadow.alpha * 0.6f))
        paint(body, colors.skin, colors.ink, 5.5f)

        face()
    }

    groundLine(286f, colors.inkFaint)
}

/** True for a brief window once per loop — the shut-eye moment of a blink. */
private fun blinking(t: Float, offset: Float = 0f, window: Float = 0.07f): Boolean =
    loop(t, offset) > 1f - window

private fun DrawScope.closedEye(cx: Float, cy: Float, dip: Float, colors: SketchyStyle, width: Float = 4.2f) {
    val eye = Path().apply {
        moveTo(d(cx - 11f), d(cy))
        quadraticTo(d(cx), d(cy + dip), d(cx + 11f), d(cy))
    }
    drawPath(eye, color = colors.ink, style = bold(width))
}

// ─── All Caught Up ──────────────────────────────────────────────────────────
//   Open ring eyes, pupils drifting together in a slow idle glance, a plain
//   smile — and a quick natural blink once per loop.

internal fun DrawScope.drawLinedManAllCaughtUp(t: Float, colors: SketchyStyle) {
    val glance = 2f * wave(t, 0.3f)
    val blink = blinking(t, 0f)
    drawLinedMan(t, colors) {
        if (blink) {
            closedEye(141f, 140f, 4f, colors)
            closedEye(178f, 138f, 4f, colors)
        } else {
            paintCircle(pt(141f, 140f), 11f, colors.paper, colors.ink, 4.2f)
            sketchCircle(pt(136f + glance, 136f), 4f, colors.ink, filled = true)
            paintCircle(pt(178f, 138f), 11f, colors.paper, colors.ink, 4.2f)
            sketchCircle(pt(174f + glance, 134f), 4f, colors.ink, filled = true)
        }

        val mouth = Path().apply {
            moveTo(d(128f), d(163f))
            cubicTo(d(142f), d(170f), d(171f), d(170f), d(192f), d(161f))
        }
        drawPath(mouth, color = colors.ink, style = bold(4.5f))
    }
    twinkle(84f, 96f, 4f, t, 0.35f, colors.touch(colors.sun))
    twinkle(238f, 108f, 3f, t, 0.7f, colors.touch(colors.paper, 0.85f))
}

// ─── Something Went Wrong ────────────────────────────────────────────────────
//   Eyes blown wide with small startled pupils that jitter, an open "O" mouth
//   that trembles — a jolt of alarm that never quite settles.

internal fun DrawScope.drawLinedManSomethingWrong(t: Float, colors: SketchyStyle) {
    val jolt = pulse(t, 0f)
    val jitterX = 1.4f * wave(t * 9f, 0f)
    val jitterY = 1.2f * wave(t * 9f, 0.25f)
    drawLinedMan(t, colors) {
        paintCircle(pt(141f + jitterX, 140f + jitterY), 13f, colors.paper, colors.ink, 4.2f)
        sketchCircle(pt(141f + jitterX, 140f + jitterY), 3f, colors.ink, filled = true)
        paintCircle(pt(178f + jitterX, 138f + jitterY), 13f, colors.paper, colors.ink, 4.2f)
        sketchCircle(pt(178f + jitterX, 138f + jitterY), 3f, colors.ink, filled = true)

        paintCircle(pt(160f, 168f), 7f + 1.5f * jolt, colors.paper, colors.ink, 4f)
    }
    twinkle(84f, 96f, 3f, t, 0.4f, colors.touch(colors.accentRed, 0.8f))
    twinkle(238f, 96f, 3f, t, 0.1f, colors.touch(colors.accentRed, 0.6f))
}

// ─── Loading ──────────────────────────────────────────────────────────────
//   Eyes closed in two calm curves, mouth relaxed — both breathing gently in
//   and out while something works in the background.

internal fun DrawScope.drawLinedManLoading(t: Float, colors: SketchyStyle) {
    val breathe = pulse(t, 0f)
    drawLinedMan(t, colors) {
        closedEye(141f, 140f, 6f + 2f * breathe, colors)
        closedEye(178f, 138f, 6f + 2f * breathe, colors)

        val mouthDip = 4f + 3f * breathe
        val mouth = Path().apply {
            moveTo(d(138f), d(163f))
            quadraticTo(d(160f), d(163f + mouthDip), d(182f), d(163f))
        }
        drawPath(mouth, color = colors.ink, style = bold(4f))
    }
    twinkle(238f, 100f, 3f, t, 0.5f, colors.touch(colors.sun, 0.7f))
    twinkle(84f, 110f, 3f, t, 0.2f, colors.touch(colors.paper, 0.55f))
}

// ─── No Connection ────────────────────────────────────────────────────────
//   Ring eyes with pupils drooping low and swaying, a downturned mouth, and a
//   slow heavy blink — a quiet, deflated look.

internal fun DrawScope.drawLinedManNoConnection(t: Float, colors: SketchyStyle) {
    val droop = 1.5f * pulse(t, 0.15f)
    val blink = blinking(t, 0f, window = 0.2f)
    drawLinedMan(t, colors) {
        if (blink) {
            closedEye(141f, 143f, 3f, colors)
            closedEye(178f, 141f, 3f, colors)
        } else {
            paintCircle(pt(141f, 142f), 11f, colors.paper, colors.ink, 4.2f)
            sketchCircle(pt(139f, 147f + droop), 4f, colors.ink, filled = true)
            paintCircle(pt(178f, 140f), 11f, colors.paper, colors.ink, 4.2f)
            sketchCircle(pt(176f, 145f + droop), 4f, colors.ink, filled = true)
        }

        val mouth = Path().apply {
            moveTo(d(130f), d(160f))
            cubicTo(d(144f), d(172f), d(176f), d(172f), d(190f), d(160f))
        }
        drawPath(mouth, color = colors.ink, style = bold(4.5f))
    }
    twinkle(238f, 108f, 3f, t, 0.6f, colors.touch(colors.inkSoft))
    twinkle(84f, 100f, 3f, t, 0.3f, colors.touch(colors.inkSoft, 0.6f))
}

// ─── Access Denied ────────────────────────────────────────────────────────
//   Flat, narrowing eyes and a straight mouth, the whole face giving one firm
//   little side-to-side shake — stern, unmoved, not budging.

internal fun DrawScope.drawLinedManAccessDenied(t: Float, colors: SketchyStyle) {
    val shake = 2f * wave(t * 6f, 0f)
    val squint = pulse(t, 0f)
    drawLinedMan(t, colors) {
        val eyeWidth = 4f + 1.4f * squint
        sketchLine(pt(128f + shake, 140f), pt(154f + shake, 140f), colors.ink, eyeWidth)
        sketchLine(pt(165f + shake, 138f), pt(191f + shake, 138f), colors.ink, eyeWidth)
        sketchLine(pt(135f + shake, 166f), pt(185f + shake, 166f), colors.ink, 4.5f)
    }
    twinkle(84f, 96f, 3f, t, 0.5f, colors.touch(colors.accentRed, 0.7f))
    twinkle(238f, 96f, 3f, t, 0.15f, colors.touch(colors.accentRed, 0.55f))
}

// ─── All Done ─────────────────────────────────────────────────────────────
//   One eye winks shut in a happy arc, the other blinks normally and stays
//   bright, and the smile stretches wider with each little cheer — confetti
//   sparkling on every side.

internal fun DrawScope.drawLinedManAllDone(t: Float, colors: SketchyStyle) {
    val cheer = pulse(t, 0f)
    val blink = blinking(t, 0.5f)
    drawLinedMan(t, colors) {
        val wink = Path().apply {
            moveTo(d(130f), d(142f))
            quadraticTo(d(141f), d(132f), d(152f), d(142f))
        }
        drawPath(wink, color = colors.ink, style = bold(4.2f))

        if (blink) {
            closedEye(178f, 138f, 4f, colors)
        } else {
            paintCircle(pt(178f, 138f), 11f, colors.paper, colors.ink, 4.2f)
            sketchCircle(pt(174f, 134f), 4f, colors.ink, filled = true)
        }

        val stretch = 4f * cheer
        val mouth = Path().apply {
            moveTo(d(124f - stretch), d(160f))
            cubicTo(d(140f), d(176f + stretch * 0.5f), d(180f), d(176f + stretch * 0.5f), d(196f + stretch), d(158f))
        }
        drawPath(mouth, color = colors.ink, style = bold(4.5f))
    }
    twinkle(80f, 88f, 4f, t, 0.1f, colors.touch(colors.sun))
    twinkle(240f, 92f, 3.5f, t, 0.35f, colors.touch(colors.accentGreen, 0.85f))
    twinkle(92f, 200f, 3f, t, 0.55f, colors.touch(colors.accentBlue, 0.8f))
    twinkle(232f, 200f, 3f, t, 0.75f, colors.touch(colors.accentRed, 0.8f))
}

// ─── No Results ───────────────────────────────────────────────────────────
//   Pupils slowly orbit inside each ring — searching all around — over a
//   small flat, uncertain mouth.

internal fun DrawScope.drawLinedManNoResults(t: Float, colors: SketchyStyle) {
    val orbitX = 4f * wave(t, 0f)
    val orbitY = 4f * wave(t, 0.25f)
    drawLinedMan(t, colors) {
        paintCircle(pt(141f, 140f), 11f, colors.paper, colors.ink, 4.2f)
        sketchCircle(pt(141f + orbitX, 140f + orbitY), 4f, colors.ink, filled = true)
        paintCircle(pt(178f, 138f), 11f, colors.paper, colors.ink, 4.2f)
        sketchCircle(pt(178f + orbitX, 138f + orbitY), 4f, colors.ink, filled = true)

        val mouth = Path().apply {
            moveTo(d(138f), d(164f))
            quadraticTo(d(160f), d(161f), d(182f), d(165f))
        }
        drawPath(mouth, color = colors.ink, style = bold(4.2f))
    }
    twinkle(84f, 96f, 3f, t, 0.3f, colors.touch(colors.accentBlue, 0.75f))
    twinkle(238f, 108f, 3f, t, 0.65f, colors.touch(colors.accentBlue, 0.55f))
}

// ─── Empty Inbox ──────────────────────────────────────────────────────────
//   A calm, steady gaze — pupils centered, no drift — with a soft, patient
//   smile and a slow, easy blink.

internal fun DrawScope.drawLinedManEmptyInbox(t: Float, colors: SketchyStyle) {
    val blink = blinking(t, 0.6f)
    drawLinedMan(t, colors) {
        if (blink) {
            closedEye(141f, 140f, 4f, colors)
            closedEye(178f, 138f, 4f, colors)
        } else {
            paintCircle(pt(141f, 140f), 11f, colors.paper, colors.ink, 4.2f)
            sketchCircle(pt(141f, 140f), 4f, colors.ink, filled = true)
            paintCircle(pt(178f, 138f), 11f, colors.paper, colors.ink, 4.2f)
            sketchCircle(pt(178f, 138f), 4f, colors.ink, filled = true)
        }

        val mouth = Path().apply {
            moveTo(d(134f), d(163f))
            quadraticTo(d(160f), d(168f), d(186f), d(163f))
        }
        drawPath(mouth, color = colors.ink, style = bold(4.2f))
    }
    twinkle(84f, 96f, 3f, t, 0.4f, colors.touch(colors.sun, 0.7f))
    twinkle(238f, 108f, 3f, t, 0.75f, colors.touch(colors.paper, 0.7f))
}

// ─── Welcome ──────────────────────────────────────────────────────────────
//   Big sparkly eyes with a tiny glint, a wide open excited mouth that
//   bounces — confetti on all four corners.

internal fun DrawScope.drawLinedManWelcome(t: Float, colors: SketchyStyle) {
    val bounce = pulse(t, 0f)
    drawLinedMan(t, colors) {
        paintCircle(pt(141f, 140f), 12f, colors.paper, colors.ink, 4.2f)
        sketchCircle(pt(141f, 140f), 5f, colors.ink, filled = true)
        sketchCircle(pt(138f, 137f), 1.6f, colors.paper, filled = true)
        paintCircle(pt(178f, 138f), 12f, colors.paper, colors.ink, 4.2f)
        sketchCircle(pt(178f, 138f), 5f, colors.ink, filled = true)
        sketchCircle(pt(175f, 135f), 1.6f, colors.paper, filled = true)

        paint(ellipsePath(160f, 168f, 14f, 8f + 2f * bounce), colors.paper, colors.ink, 4f)
    }
    twinkle(80f, 88f, 4f, t, 0.1f, colors.touch(colors.sun))
    twinkle(240f, 92f, 3.5f, t, 0.3f, colors.touch(colors.accentBlue, 0.85f))
    twinkle(92f, 200f, 3f, t, 0.55f, colors.touch(colors.accentGreen, 0.8f))
    twinkle(232f, 204f, 3f, t, 0.75f, colors.touch(colors.accentRed, 0.8f))
}

// ─── Thinking ─────────────────────────────────────────────────────────────
//   Pupils flick up and sideways as if pondering, over a small flat, tilted
//   mouth — working something out.

internal fun DrawScope.drawLinedManThinking(t: Float, colors: SketchyStyle) {
    val ponder = 2f * wave(t, 0f)
    drawLinedMan(t, colors) {
        paintCircle(pt(141f, 140f), 11f, colors.paper, colors.ink, 4.2f)
        sketchCircle(pt(139f + ponder, 133f), 4f, colors.ink, filled = true)
        paintCircle(pt(178f, 138f), 11f, colors.paper, colors.ink, 4.2f)
        sketchCircle(pt(176f + ponder, 131f), 4f, colors.ink, filled = true)

        sketchLine(pt(140f, 165f), pt(178f, 161f), colors.ink, 4.2f)
    }
    twinkle(238f, 100f, 3f, t, 0.4f, colors.touch(colors.accentBlue, 0.75f))
    twinkle(84f, 110f, 3f, t, 0.7f, colors.touch(colors.accentBlue, 0.55f))
}

// ─── Sleepy ───────────────────────────────────────────────────────────────
//   Heavy half-shut eyelids with a pupil just peeking through, and a slow
//   yawning "o" mouth that opens and closes.

internal fun DrawScope.drawLinedManSleepy(t: Float, colors: SketchyStyle) {
    val yawn = pulse(t, 0f)
    drawLinedMan(t, colors) {
        closedEye(141f, 138f, 2f, colors)
        sketchCircle(pt(141f, 144f), 3f, colors.ink, filled = true)
        closedEye(178f, 136f, 2f, colors)
        sketchCircle(pt(178f, 142f), 3f, colors.ink, filled = true)

        paintCircle(pt(160f, 168f), 5f + 4f * yawn, colors.paper, colors.ink, 4f)
    }
    twinkle(84f, 96f, 3f, t, 0.45f, colors.touch(colors.inkSoft, 0.6f))
    twinkle(238f, 108f, 3f, t, 0.8f, colors.touch(colors.paper, 0.55f))
}
