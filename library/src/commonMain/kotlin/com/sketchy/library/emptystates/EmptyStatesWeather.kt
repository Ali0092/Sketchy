package com.sketchy.library.emptystates

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.sketchy.library.SketchyStyle
import com.sketchy.library.utils.*

/**
 * The **Weather** category: the same cute cloud mascot every time — a fluffy two-lobed cloud with
 * a small changing face — with only its expression and the sky around it (a peeking sun, a
 * crescent moon and stars, falling rain, a lightning bolt, a rainbow) changing to carry each state,
 * the same way "Clock and Time" reuses one alarm clock and "Lined Man" reuses one figure.
 */

private const val CloudW = 132f
private const val CloudH = 76f

/** The shared cloud mascot body, with [face] drawn on top. */
private fun DrawScope.drawCloudMascot(
    cx: Float,
    cy: Float,
    colors: SketchyStyle,
    tint: Color = colors.paper,
    w: Float = CloudW,
    h: Float = CloudH,
    face: DrawScope.() -> Unit
) {
    drawCloudSilhouette(cx, cy, w, h, colors, tint)
    face()
}

/** A closed, content eye — a gentle downward curve. */
private fun DrawScope.closedCloudEye(ex: Float, ey: Float, colors: SketchyStyle, dip: Float = 5f) {
    val eye = Path().apply {
        moveTo(d(ex - 7f), d(ey))
        quadraticTo(d(ex), d(ey + dip), d(ex + 7f), d(ey))
    }
    drawPath(eye, color = colors.ink, style = bold(2.4f))
}

/** An open, round eye. */
private fun DrawScope.openCloudEye(ex: Float, ey: Float, colors: SketchyStyle, r: Float = 4.2f) {
    sketchCircle(pt(ex, ey), r, colors.ink, filled = true)
}

/** A happy, winking eye — a gentle upward curve. */
private fun DrawScope.winkCloudEye(ex: Float, ey: Float, colors: SketchyStyle) {
    val wink = Path().apply {
        moveTo(d(ex - 7f), d(ey + 1f))
        quadraticTo(d(ex), d(ey - 6f), d(ex + 7f), d(ey + 1f))
    }
    drawPath(wink, color = colors.ink, style = bold(2.4f))
}

/** A worried/surprised "x" eye, straight from the inspiration sketch. */
private fun DrawScope.xCloudEye(ex: Float, ey: Float, colors: SketchyStyle, r: Float = 5f) {
    sketchLine(pt(ex - r, ey - r), pt(ex + r, ey + r), colors.ink, 2.2f)
    sketchLine(pt(ex - r, ey + r), pt(ex + r, ey - r), colors.ink, 2.2f)
}

/** A single-curve mouth: positive [curve] reads as a smile, negative as a frown. */
private fun DrawScope.cloudMouth(cx: Float, cy: Float, colors: SketchyStyle, curve: Float = 3f) {
    val mouth = Path().apply {
        moveTo(d(cx - 6f), d(cy))
        quadraticTo(d(cx), d(cy + curve), d(cx + 6f), d(cy))
    }
    drawPath(mouth, color = colors.ink, style = bold(2f))
}

// ─── No Connection ──────────────────────────────────────────────────────────
//   A dark storm cloud, startled, with a lightning bolt flashing beneath it —
//   the signal got lost in the storm.

internal fun DrawScope.drawWeatherNoConnection(t: Float, colors: SketchyStyle) {
    val cx = 160f
    val cy = 138f + 3f * wave(t, 0f)

    drawLightningBolt(cx + 22f, cy + 30f, colors, pulse(t, 0f), length = 66f)

    drawCloudMascot(cx, cy, colors, tint = colors.metal.shaded(0.16f)) {
        xCloudEye(cx - 16f, cy - 8f, colors)
        xCloudEye(cx + 16f, cy - 8f, colors)
        cloudMouth(cx, cy + 16f, colors, curve = -4f)
    }

    twinkle(66f, 108f, 3f, t, 0.2f, colors.inkSoft)
    twinkle(252f, 156f, 3f, t, 0.6f, colors.inkSoft)
    groundLine(268f, colors.inkFaint)
}

// ─── Something Went Wrong ───────────────────────────────────────────────────
//   A rain cloud, eyes drooping, letting heavier rain fall than usual —
//   a little static in the sky.

internal fun DrawScope.drawWeatherSomethingWrong(t: Float, colors: SketchyStyle) {
    val cx = 160f
    val cy = 132f + 3f * wave(t, 0.1f)

    drawRainStreaks(cx - 58f, cx + 58f, cy + 26f, 260f, 9, t, colors, seed = 5, color = colors.hint(colors.sky))

    drawCloudMascot(cx, cy, colors, tint = colors.metal.lit(0.05f)) {
        closedCloudEye(cx - 16f, cy - 6f, colors, dip = -6f)
        closedCloudEye(cx + 16f, cy - 6f, colors, dip = -6f)
        cloudMouth(cx, cy + 17f, colors, curve = -3f)
    }

    twinkle(74f, 100f, 3f, t, 0.3f, colors.inkSoft)
    twinkle(246f, 132f, 3f, t, 0.7f, colors.inkSoft)
    groundLine(286f, colors.inkFaint)
}

// ─── All Caught Up ──────────────────────────────────────────────────────────
//   A cheerful, winking cloud beside a bright sun — clear skies, nothing left
//   to do.

internal fun DrawScope.drawWeatherAllCaughtUp(t: Float, colors: SketchyStyle) {
    val cx = 128f
    val cy = 158f + 3f * wave(t, 0.2f)

    drawSunDisc(214f, 108f, 34f, colors)

    drawCloudMascot(cx, cy, colors) {
        winkCloudEye(cx - 16f, cy - 8f, colors)
        openCloudEye(cx + 16f, cy - 8f, colors)
        cloudMouth(cx, cy + 16f, colors, curve = 7f)
    }

    twinkle(64f, 118f, 3f, t, 0.2f, colors.touch(colors.sun, 0.8f))
    twinkle(258f, 168f, 3.2f, t, 0.6f, colors.touch(colors.accentBlue, 0.7f))
    groundLine(262f, colors.inkFaint)
}

// ─── All Quiet ──────────────────────────────────────────────────────────────
//   A single content cloud drifting alone under a faint scatter of stars —
//   no new notifications right now.

internal fun DrawScope.drawWeatherAllQuiet(t: Float, colors: SketchyStyle) {
    val cx = 160f
    val cy = 158f + 4f * wave(t, 0f)

    drawCloudMascot(cx, cy, colors) {
        closedCloudEye(cx - 16f, cy - 7f, colors)
        closedCloudEye(cx + 16f, cy - 7f, colors)
        cloudMouth(cx, cy + 16f, colors, curve = 2f)
    }

    twinkle(88f, 96f, 3f, t, 0.3f, colors.inkSoft)
    twinkle(238f, 112f, 3f, t, 0.6f, colors.inkSoft)
    twinkle(200f, 84f, 2.4f, t, 0.9f, colors.inkFaint)
    groundLine(262f, colors.inkFaint)
}

// ─── No Results Found ────────────────────────────────────────────────────────
//   The cloud, puzzled, watches a few smaller wisps drift off and dissolve —
//   we searched every cloud and found nothing.

internal fun DrawScope.drawWeatherNoResults(t: Float, colors: SketchyStyle) {
    val cx = 140f
    val cy = 150f + 3f * wave(t, 0.15f)

    listOf(0f to 210f, 0.4f to 236f, 0.75f to 252f).forEach { (offset, wispCy) ->
        val phase = loop(t * 0.6f, offset)
        val x = 200f + phase * 70f
        val alpha = (1f - phase) * 0.5f
        drawCloudSilhouette(x, wispCy, 30f, 18f, colors, tint = colors.paper.a(alpha))
    }

    drawCloudMascot(cx, cy, colors) {
        xCloudEye(cx - 16f, cy - 8f, colors)
        xCloudEye(cx + 16f, cy - 8f, colors)
        cloudMouth(cx, cy + 16f, colors, curve = -2f)
    }

    twinkle(70f, 100f, 3f, t, 0.4f, colors.inkSoft)
    groundLine(262f, colors.inkFaint)
}

// ─── Just a Moment ────────────────────────────────────────────────────────────
//   Partly cloudy: a sun steadily sliding in and out from behind a drifting
//   cloud — gathering the clouds, hang tight.

internal fun DrawScope.drawWeatherLoading(t: Float, colors: SketchyStyle) {
    val sunCx = 160f
    val sunCy = 140f

    drawSunDisc(sunCx, sunCy, 40f, colors, warmth = 0.85f)

    val cx = sunCx + 46f * wave(t, 0f)
    val cy = 156f
    drawCloudMascot(cx, cy, colors, w = 116f, h = 64f) {
        openCloudEye(cx - 14f, cy - 6f, colors, r = 3.6f)
        openCloudEye(cx + 14f, cy - 6f, colors, r = 3.6f)
        cloudMouth(cx, cy + 14f, colors, curve = 1f)
    }

    twinkle(72f, 220f, 3f, t, 0.3f, colors.inkSoft)
    twinkle(248f, 220f, 3f, t, 0.7f, colors.inkSoft)
    groundLine(256f, colors.inkFaint)
}

// ─── Nothing Scheduled Tonight ────────────────────────────────────────────────
//   A sleepy cloud drifts under a crescent moon and a handful of stars —
//   nothing on the calendar under these stars.

internal fun DrawScope.drawWeatherQuietNight(t: Float, colors: SketchyStyle) {
    val cx = 176f
    val cy = 168f + 3f * wave(t, 0.05f)

    drawCrescentMoon(96f, 96f, 30f, colors)

    drawCloudMascot(cx, cy, colors) {
        closedCloudEye(cx - 16f, cy - 7f, colors, dip = 4f)
        closedCloudEye(cx + 16f, cy - 7f, colors, dip = 4f)
        cloudMouth(cx, cy + 16f, colors, curve = 1f)
    }

    twinkle(58f, 150f, 3f, t, 0.2f, colors.inkSoft)
    twinkle(140f, 68f, 3f, t, 0.5f, colors.inkSoft)
    twinkle(250f, 128f, 3f, t, 0.8f, colors.inkSoft)
    groundLine(264f, colors.inkFaint)
}

// ─── Welcome! ─────────────────────────────────────────────────────────────────
//   A rainbow arches behind a delighted cloud, sun peeking beside it — let's
//   get started, clear skies ahead.

internal fun DrawScope.drawWeatherWelcome(t: Float, colors: SketchyStyle) {
    val cx = 160f
    val cy = 176f + 3f * wave(t, 0f)

    drawRainbow(cx, 220f, 92f, colors)
    drawSunDisc(232f, 96f, 26f, colors)

    drawCloudMascot(cx, cy, colors) {
        val cheer = pulse(t, 0f)
        openCloudEye(cx - 16f, cy - 8f, colors)
        openCloudEye(cx + 16f, cy - 8f, colors)
        cloudMouth(cx, cy + 15f, colors, curve = 6f + 2f * cheer)
    }

    twinkle(64f, 128f, 3f, t, 0.3f, colors.touch(colors.accent, 0.8f))
    groundLine(272f, colors.inkFaint)
}

// ─── Your Inbox is Empty ──────────────────────────────────────────────────────
//   A calm cloud releasing just a few gentle drops — new messages will rain
//   in here.

internal fun DrawScope.drawWeatherEmptyInbox(t: Float, colors: SketchyStyle) {
    val cx = 160f
    val cy = 140f + 3f * wave(t, 0.1f)

    drawRainStreaks(cx - 36f, cx + 36f, cy + 24f, 220f, 3, t, colors, seed = 20, color = colors.hint(colors.sky))

    drawCloudMascot(cx, cy, colors) {
        openCloudEye(cx - 16f, cy - 7f, colors, r = 3.8f)
        openCloudEye(cx + 16f, cy - 7f, colors, r = 3.8f)
        cloudMouth(cx, cy + 16f, colors, curve = 3f)
    }

    twinkle(76f, 110f, 3f, t, 0.4f, colors.inkSoft)
    twinkle(244f, 120f, 3f, t, 0.7f, colors.inkSoft)
    groundLine(250f, colors.inkFaint)
}

// ─── Under Maintenance ────────────────────────────────────────────────────────
//   An overcast cloud drifts half in front of a dimmed sun — we're clearing
//   the skies, check back soon.

internal fun DrawScope.drawWeatherMaintenance(t: Float, colors: SketchyStyle) {
    val sunCx = 190f
    val sunCy = 130f
    drawSunDisc(sunCx, sunCy, 32f, colors, warmth = 0.35f)

    val cx = 148f + 4f * wave(t, 0f)
    val cy = 148f
    drawCloudMascot(cx, cy, colors, tint = colors.metal.lit(0.02f)) {
        closedCloudEye(cx - 16f, cy - 7f, colors, dip = 3f)
        closedCloudEye(cx + 16f, cy - 7f, colors, dip = 3f)
        cloudMouth(cx, cy + 16f, colors, curve = 0f)
    }

    twinkle(70f, 210f, 3f, t, 0.3f, colors.inkSoft)
    twinkle(250f, 190f, 3f, t, 0.6f, colors.inkSoft)
    groundLine(262f, colors.inkFaint)
}
