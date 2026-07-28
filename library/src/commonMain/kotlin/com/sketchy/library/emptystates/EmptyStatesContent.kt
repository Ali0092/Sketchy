package com.sketchy.library.emptystates

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import com.sketchy.library.SketchyStyle
import com.sketchy.library.utils.*

// ─── No Results ──────────────────────────────────────────────────────────────
//   A magnifying glass sweeping over a blank, faintly lined page.

internal fun DrawScope.drawNoResults(t: Float, colors: SketchyStyle) {
    val page = Path().apply {
        moveTo(d(84f), d(96f))
        lineTo(d(224f), d(96f))
        lineTo(d(224f), d(240f))
        lineTo(d(84f), d(240f))
        close()
    }
    contactShadow(154f, 246f, 74f, 6f, colors.shade)
    paint(page, vBrush(96f, 240f, colors.paper, colors.metal), colors.ink, 2.2f)
    shade(page, hBrush(154f, 224f, colors.shade.a(0f), colors.shade))
    for (row in 0..3) {
        val y = 122f + row * 26f
        sketchLine(pt(102f, y), pt(206f, y), colors.inkFaint, 1.6f)
    }

    val sweep = 8f * wave(t, 0f)
    val sweepPivot = pt(180f, 150f)
    withTransform({ rotate(degrees = sweep, pivot = sweepPivot) }) {
        paintCircle(pt(172f, 142f), 30f, colors.sky.a(0.5f), colors.accentBlue, 3f)
        limb(
            Path().apply {
                moveTo(d(193f), d(163f))
                lineTo(d(214f), d(184f))
            },
            colors.metalDark, colors.accentBlue, 4f, thickness = 6f
        )
    }

    twinkle(70f, 76f, 3f, t, 0.4f, colors.inkSoft)
    groundLine(262f, colors.inkFaint)
}

// ─── No Data ─────────────────────────────────────────────────────────────────
//   A flat, dashed bar chart with a bobbing question mark above it.

internal fun DrawScope.drawNoData(t: Float, colors: SketchyStyle) {
    val baseY = 220f
    sketchLine(pt(80f, baseY), pt(240f, baseY), colors.ink, 2.2f)
    sketchLine(pt(80f, 100f), pt(80f, baseY), colors.ink, 2.2f)

    val heights = listOf(14f, 22f, 10f, 18f)
    heights.forEachIndexed { i, h ->
        val x = 108f + i * 34f
        val bar = Path().apply {
            moveTo(d(x), d(baseY))
            lineTo(d(x), d(baseY - h))
        }
        drawPath(bar, color = colors.inkFaint, style = dashed())
    }

    val bob = 5f * wave(t, 0f)
    sketchCircle(pt(160f, 70f + bob), 16f, colors.accent, width = 2.4f)
    sketchLine(pt(154f, 64f + bob), pt(160f, 60f + bob), colors.accent, 2.2f)
    sketchLine(pt(160f, 60f + bob), pt(166f, 66f + bob), colors.accent, 2.2f)
    sketchLine(pt(160f, 70f + bob), pt(160f, 74f + bob), colors.accent, 2.4f)
    sketchCircle(pt(160f, 80f + bob), 1.6f, colors.accent, filled = true)

    twinkle(230f, 96f, 3f, t, 0.5f, colors.inkSoft)
    groundLine(244f, colors.inkFaint)
}

// ─── No Comments ─────────────────────────────────────────────────────────────
//   An empty speech bubble bouncing gently, with a pencil hovering beside it.

internal fun DrawScope.drawNoComments(t: Float, colors: SketchyStyle) {
    val bounce = 4f * wave(t, 0f)
    val bubble = Path().apply {
        moveTo(d(90f), d(100f + bounce))
        lineTo(d(226f), d(100f + bounce))
        quadraticTo(d(238f), d(100f + bounce), d(238f), d(112f + bounce))
        lineTo(d(238f), d(170f + bounce))
        quadraticTo(d(238f), d(182f + bounce), d(226f), d(182f + bounce))
        lineTo(d(140f), d(182f + bounce))
        lineTo(d(116f), d(206f + bounce))
        lineTo(d(120f), d(182f + bounce))
        lineTo(d(102f), d(182f + bounce))
        quadraticTo(d(90f), d(182f + bounce), d(90f), d(170f + bounce))
        lineTo(d(90f), d(112f + bounce))
        quadraticTo(d(90f), d(100f + bounce), d(90f), d(100f + bounce))
        close()
    }
    paint(bubble, vBrush(100f + bounce, 206f + bounce, colors.paper, colors.metal), colors.ink, 2.4f)

    // pencil writing off to the side
    val pencil = Path().apply {
        moveTo(d(150f), d(140f + bounce))
        lineTo(d(190f), d(150f + bounce))
    }
    limb(pencil, colors.wood, colors.accent, 2.4f, thickness = 5f)
    sketchLine(pt(190f, 150f + bounce), pt(198f, 152f + bounce), colors.ink, 2f)

    twinkle(72f, 82f, 3f, t, 0.3f, colors.inkSoft)
    twinkle(250f, 132f, 3f, t, 0.7f, colors.inkSoft)
    groundLine(240f, colors.inkFaint)
}

// ─── No Messages ─────────────────────────────────────────────────────────────
//   Two chat bubbles — one solid, one faint — with typing dots between them.

internal fun DrawScope.drawNoMessages(t: Float, colors: SketchyStyle) {
    val bubbleA = Path().apply {
        moveTo(d(76f), d(108f))
        lineTo(d(178f), d(108f))
        quadraticTo(d(188f), d(108f), d(188f), d(118f))
        lineTo(d(188f), d(150f))
        quadraticTo(d(188f), d(160f), d(178f), d(160f))
        lineTo(d(96f), d(160f))
        lineTo(d(76f), d(178f))
        lineTo(d(80f), d(160f))
        quadraticTo(d(76f), d(160f), d(76f), d(150f))
        close()
    }
    paint(bubbleA, vBrush(108f, 178f, colors.fabric.lit(0.4f), colors.fabric), colors.ink, 2.2f)

    val bubbleB = Path().apply {
        moveTo(d(150f), d(180f))
        lineTo(d(244f), d(180f))
        quadraticTo(d(252f), d(180f), d(252f), d(188f))
        lineTo(d(252f), d(212f))
        quadraticTo(d(252f), d(220f), d(244f), d(220f))
        lineTo(d(176f), d(220f))
        lineTo(d(180f), d(234f))
        lineTo(d(158f), d(220f))
        quadraticTo(d(150f), d(220f), d(150f), d(212f))
        close()
    }
    paint(bubbleB, vBrush(180f, 234f, colors.paper, colors.metal.lit(0.2f)), colors.inkFaint, 1.8f)

    // typing dots fading in sequence
    for (i in 0..2) {
        val k = (1f + wave(t, i * 0.2f)) / 2f
        sketchCircle(
            pt(112f + i * 16f, 134f), 3f,
            colors.accentBlue.copy(alpha = 0.3f + 0.7f * k), filled = true
        )
    }

    groundLine(250f, colors.inkFaint)
}

// ─── Page Not Found (404) ─────────────────────────────────────────────────────
//   A tilted, cracked signpost with a lost "?", swaying in the wind.

internal fun DrawScope.drawPageNotFound(t: Float, colors: SketchyStyle) {
    contactShadow(160f, 244f, 26f, 5f, colors.shade)
    limb(
        Path().apply {
            moveTo(d(160f), d(240f))
            lineTo(d(160f), d(130f))
        },
        colors.woodDark, colors.ink, 3f, thickness = 8f
    )

    val sway = 6f * wave(t, 0f)
    val signPivot = pt(160f, 130f)
    withTransform({ rotate(degrees = sway, pivot = signPivot) }) {
        val sign = Path().apply {
            moveTo(d(96f), d(96f))
            lineTo(d(224f), d(96f))
            lineTo(d(224f), d(150f))
            lineTo(d(96f), d(150f))
            close()
        }
        paint(sign, vBrush(96f, 150f, colors.wood.lit(0.3f), colors.wood), colors.ink, 2.4f)
        val crack = Path().apply {
            moveTo(d(150f), d(96f))
            lineTo(d(166f), d(118f))
            lineTo(d(148f), d(132f))
            lineTo(d(170f), d(150f))
        }
        stroke(crack, colors.accentRed, 2f)
    }

    val bob = 5f * wave(t, 0.3f)
    sketchCircle(pt(200f, 60f + bob), 14f, colors.accentBlue, width = 2.2f)
    sketchLine(pt(196f, 55f + bob), pt(200f, 51f + bob), colors.accentBlue, 2f)
    sketchLine(pt(200f, 51f + bob), pt(204f, 56f + bob), colors.accentBlue, 2f)
    sketchLine(pt(200f, 60f + bob), pt(200f, 64f + bob), colors.accentBlue, 2.2f)
    sketchCircle(pt(200f, 70f + bob), 1.6f, colors.accentBlue, filled = true)

    twinkle(80f, 70f, 3f, t, 0.5f, colors.inkSoft)
    groundLine(254f, colors.inkFaint)
}
