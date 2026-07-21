package com.sketchy.library.illustrations

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import com.sketchy.library.SketchyColors
import com.sketchy.library.utils.*
import kotlin.math.cos
import kotlin.math.sin

// ─── Plan Your Perfect Trip ──────────────────────────────────────────────────
//   A person packing an open suitcase, a passport and ticket floating above,
//   a paper airplane looping in the corner.

internal fun DrawScope.drawPlanTripScene(t: Float, colors: SketchyColors) {
    // open suitcase
    val caseBottom = Path().apply {
        moveTo(d(88f), d(206f))
        lineTo(d(232f), d(206f))
        lineTo(d(232f), d(258f))
        lineTo(d(88f), d(258f))
        close()
    }
    stroke(caseBottom, colors.ink, 2.4f)
    val lidOpen = 0.5f + 0.06f * wave(t, 0f)
    // the lid is a rectangle rotated open around the hinge at (88,206)
    val hinge = pt(88f, 206f)
    withTransform({ rotate(degrees = -70f * lidOpen, pivot = hinge) }) {
        val panel = Path().apply {
            moveTo(d(88f), d(206f))
            lineTo(d(232f), d(206f))
            lineTo(d(232f), d(158f))
            lineTo(d(88f), d(158f))
            close()
        }
        stroke(panel, colors.ink, 2.2f)
        sketchLine(pt(110f, 172f), pt(210f, 172f), colors.inkFaint, 1.6f)
        sketchLine(pt(110f, 188f), pt(190f, 188f), colors.inkFaint, 1.6f)
    }
    sketchLine(pt(148f, 206f), pt(148f, 216f), colors.ink, 2f)
    sketchCircle(pt(96f, 258f), 8f, colors.ink, width = 2f)
    sketchCircle(pt(224f, 258f), 8f, colors.ink, width = 2f)
    // folded clothes inside the base
    sketchLine(pt(104f, 224f), pt(150f, 220f), colors.accentSecondary, 3f)
    sketchLine(pt(104f, 238f), pt(150f, 234f), colors.accent, 3f)

    // person kneeling beside the suitcase
    sketchCircle(pt(258f, 176f), 19f, colors.ink, width = 2.4f)
    sketchCircle(pt(264f, 176f), 1.5f, colors.ink, filled = true)
    sketchLine(pt(258f, 195f), pt(258f, 202f), colors.ink)
    val body = Path().apply {
        moveTo(d(258f), d(202f))
        quadraticTo(d(238f), d(206f), d(234f), d(222f))
        lineTo(d(238f), d(252f))
        quadraticTo(d(258f), d(260f), d(280f), d(252f))
        lineTo(d(282f), d(222f))
        quadraticTo(d(278f), d(206f), d(258f), d(202f))
        close()
    }
    stroke(body, colors.ink)
    val arm = Path().apply {
        moveTo(d(238f), d(222f))
        quadraticTo(d(216f), d(224f), d(206f), d(216f))
    }
    stroke(arm, colors.ink)

    // passport + ticket floating above
    val floatY = 6f * wave(t, 0.25f)
    val passport = Path().apply {
        moveTo(d(150f), d(96f + floatY))
        lineTo(d(178f), d(90f + floatY))
        lineTo(d(184f), d(126f + floatY))
        lineTo(d(156f), d(132f + floatY))
        close()
    }
    stroke(passport, colors.accentSecondary, 2.2f)
    sketchCircle(pt(167f, 108f + floatY), 5f, colors.accentSecondary, width = 1.6f)

    val ticket = Path().apply {
        moveTo(d(196f), d(84f - floatY))
        lineTo(d(234f), d(78f - floatY))
        lineTo(d(238f), d(102f - floatY))
        lineTo(d(200f), d(108f - floatY))
        close()
    }
    stroke(ticket, colors.accent, 2.2f)
    sketchLine(pt(206f, 92f - floatY), pt(226f, 89f - floatY), colors.accent, 1.4f)

    twinkle(60f, 130f, 3f, t, 0.4f, colors.inkSoft)
    twinkle(230f, 60f, 3f, t, 0.7f, colors.accent)
    groundHint(276f, colors.inkFaint)
}

// ─── Explore The World ────────────────────────────────────────────────────────
//   A person with a backpack studying a compass while a plane loops along a
//   dashed flight path overhead.

internal fun DrawScope.drawExploreWorldScene(t: Float, colors: SketchyColors) {
    // globe
    val gx = 130f
    val gy = 176f
    val gr = 70f
    sketchCircle(pt(gx, gy), gr, colors.ink, width = 2.4f)
    val ellipse = Rect(pt(gx - gr, gy - gr * 0.4f), Size(d(gr * 2), d(gr * 0.8f)))
    val equator = Path().apply {
        arcTo(ellipse, 0f, 360f, forceMoveTo = true)
    }
    stroke(equator, colors.inkSoft, 1.4f)
    sketchLine(pt(gx, gy - gr), pt(gx, gy + gr), colors.inkSoft, 1.4f)
    val landA = Path().apply {
        moveTo(d(gx - 40f), d(gy - 20f))
        quadraticTo(d(gx - 20f), d(gy - 36f), d(gx), d(gy - 24f))
        quadraticTo(d(gx - 10f), d(gy - 6f), d(gx - 34f), d(gy))
        close()
    }
    drawPath(landA, color = colors.accentSecondary.copy(alpha = 0.3f), style = bold(2f))
    stroke(landA, colors.accentSecondary, 1.8f)

    // person with backpack, standing beside the globe
    sketchCircle(pt(230f, 132f), 19f, colors.ink, width = 2.4f)
    sketchCircle(pt(236f, 132f), 1.5f, colors.ink, filled = true)
    sketchLine(pt(230f, 151f), pt(230f, 158f), colors.ink)
    val body = Path().apply {
        moveTo(d(230f), d(158f))
        quadraticTo(d(210f), d(162f), d(206f), d(178f))
        lineTo(d(210f), d(224f))
        quadraticTo(d(230f), d(232f), d(252f), d(224f))
        lineTo(d(254f), d(178f))
        quadraticTo(d(250f), d(162f), d(230f), d(158f))
        close()
    }
    stroke(body, colors.ink)
    sketchLine(pt(214f, 228f), pt(210f, 258f), colors.ink)
    sketchLine(pt(248f, 228f), pt(252f, 258f), colors.ink)
    // backpack
    val pack = Path().apply {
        moveTo(d(252f), d(172f))
        lineTo(d(272f), d(176f))
        lineTo(d(268f), d(214f))
        lineTo(d(250f), d(212f))
        close()
    }
    stroke(pack, colors.accent, 2.2f)

    // compass held up, needle spinning to settle
    val settle = (t % 1f)
    val needleDeg = 360f * settle * (1f - settle) * 4f
    sketchCircle(pt(196f, 168f), 15f, colors.ink, width = 2.2f)
    val rad = needleDeg * kotlin.math.PI.toFloat() / 180f
    val needleTip = pt(196f + 10f * cos(rad), 168f + 10f * sin(rad))
    drawLine(colors.accent, pt(196f, 168f), needleTip, strokeWidth = d(2.2f))
    sketchCircle(pt(196f, 168f), 2f, colors.ink, filled = true)

    // plane looping the dashed flight path
    val path = Path().apply {
        moveTo(d(50f), d(60f))
        quadraticTo(d(160f), d(20f), d(280f), d(66f))
    }
    drawPath(path, color = colors.inkFaint, style = dashed())
    val flightT = t
    val planeX = 50f + 230f * flightT
    val planeY = 60f - 40f * sin(flightT * kotlin.math.PI.toFloat())
    val planePivot = pt(planeX, planeY)
    withTransform({ rotate(degrees = -20f + 40f * flightT, pivot = planePivot) }) {
        val plane = Path().apply {
            moveTo(d(planeX - 14f), d(planeY))
            lineTo(d(planeX + 14f), d(planeY - 4f))
            lineTo(d(planeX - 2f), d(planeY + 4f))
            close()
        }
        stroke(plane, colors.ink, 1.8f)
    }

    twinkle(70f, 96f, 3f, t, 0.3f, colors.accent)
    groundHint(276f, colors.inkFaint)
}
