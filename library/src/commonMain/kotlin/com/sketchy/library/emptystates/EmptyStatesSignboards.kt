package com.sketchy.library.emptystates

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.withTransform
import com.sketchy.library.SketchyStyle
import com.sketchy.library.utils.*

/**
 * The **Signboards** category: road and traffic signage — signal lights, warning/stop signs,
 * cones, barriers, noticeboards. Five scenes for now; an open category, more can follow later.
 */

/**
 * A straight-edged polygon through design-space [points], each corner clipped by a short curve of
 * radius [r] — the stop-sign octagon and the warning triangle both hand-round their corners this
 * way instead of meeting at a sharp point.
 */
private fun DrawScope.roundedPolygonPath(points: List<Pair<Float, Float>>, r: Float): Path {
    val n = points.size
    fun corner(i: Int) = points[(i + n) % n]
    val path = Path()
    for (i in 0 until n) {
        val (cx, cy) = corner(i)
        val (px, py) = corner(i - 1)
        val (nx, ny) = corner(i + 1)
        val toPrev = kotlin.math.hypot((px - cx).toDouble(), (py - cy).toDouble()).toFloat()
        val toNext = kotlin.math.hypot((nx - cx).toDouble(), (ny - cy).toDouble()).toFloat()
        val f1 = (r / toPrev).coerceIn(0f, 0.5f)
        val f2 = (r / toNext).coerceIn(0f, 0.5f)
        val startX = cx + (px - cx) * f1
        val startY = cy + (py - cy) * f1
        val endX = cx + (nx - cx) * f2
        val endY = cy + (ny - cy) * f2
        if (i == 0) path.moveTo(d(startX), d(startY)) else path.lineTo(d(startX), d(startY))
        path.quadraticTo(d(cx), d(cy), d(endX), d(endY))
    }
    path.close()
    return path
}

// ─── All Clear ────────────────────────────────────────────────────────────────
//   A signal head on its pole, red and amber dark, the green lens glowing —
//   nothing to signal right now.

internal fun DrawScope.drawSignboardAllClear(t: Float, colors: SketchyStyle) {
    val cx = 160f

    contactShadow(cx, 284f, 46f, 7f, colors.shade)
    castShadow(134f, 172f, 52f, 12f, 24f, colors.shadeSoft)

    val pole = Path().apply { moveTo(d(cx), d(272f)); lineTo(d(cx), d(120f)) }
    limb(pole, colors.metalDark, colors.ink, 2.4f, thickness = 9f)

    val housing = roundRectPath(cx - 30f, 52f, 60f, 128f, 18f)
    inkShadow(housing, colors.outlineShadow)
    cornerShade(housing, cx, 116f, 78f, colors.outlineShadow.a(colors.outlineShadow.alpha * 0.6f))
    paint(housing, vBrush(52f, 180f, colors.charcoal.lit(0.18f), colors.charcoal), colors.ink, 2.4f)
    sheen(housing, pt(cx - 20f, 60f), pt(cx + 16f, 150f), colors.metal.a(0.22f))

    val lensY = listOf(80f, 116f, 152f)
    val dim = listOf(colors.accentRed.a(0.3f), colors.accent.a(0.3f))
    dim.forEachIndexed { i, c -> paintCircle(pt(cx, lensY[i]), 14f, c, colors.ink, 2f) }

    val breathe = 0.55f + 0.45f * pulse(t, 0f)
    glow(cx, lensY[2], 24f, colors.accentGreen.a(0.4f * breathe))
    val greenLens = ellipsePath(cx, lensY[2], 14f, 14f)
    paintCircle(pt(cx, lensY[2]), 14f, colors.accentGreen.a(0.55f + 0.45f * breathe), colors.ink, 2f)
    sheen(greenLens, pt(cx - 8f, lensY[2] - 8f), pt(cx + 6f, lensY[2] + 6f), colors.paper.a(0.4f))

    for (i in 0 until 4) {
        val rx = if (i % 2 == 0) cx - 24f else cx + 24f
        val ry = 62f + (i / 2) * 108f
        sketchCircle(pt(rx, ry), 1.6f, colors.inkFaint, filled = true)
    }

    twinkle(72f, 96f, 3f, t, 0.2f, colors.inkSoft)
    twinkle(252f, 150f, 3f, t, 0.6f, colors.inkSoft)
    groundLine(292f, colors.inkFaint)
}

// ─── No Warnings ────────────────────────────────────────────────────────────────
//   A hazard triangle on a post, blank and calm, swaying gently — nothing here
//   needs your attention.

internal fun DrawScope.drawSignboardNoWarnings(t: Float, colors: SketchyStyle) {
    val cx = 160f
    val postTopY = 196f

    contactShadow(cx, 284f, 44f, 7f, colors.shade)
    castShadow(cx - 44f, postTopY - 4f, 20f, 10f, 22f, colors.shadeSoft)

    val post = Path().apply { moveTo(d(cx), d(postTopY)); lineTo(d(cx), d(272f)) }
    limb(post, colors.metalDark, colors.ink, 2.4f, thickness = 8f)
    val foot = ellipsePath(cx, 274f, 20f, 6f)
    paint(foot, colors.metal, colors.ink, 1.8f)

    val sway = 2.6f * wave(t, 0f)
    val swayPivot = pt(cx, postTopY)
    withTransform({ rotate(degrees = sway, pivot = swayPivot) }) {
        val outer = listOf(cx to 66f, cx + 64f to postTopY, cx - 64f to postTopY)
        val triangle = roundedPolygonPath(outer, r = 14f)
        inkShadow(triangle, colors.outlineShadow)
        cornerShade(triangle, cx, 155f, 100f, colors.outlineShadow.a(colors.outlineShadow.alpha * 0.6f))
        paint(triangle, vBrush(66f, postTopY, colors.sun, colors.sunDeep), colors.ink, 2.6f)
        sheen(triangle, pt(cx - 30f, 90f), pt(cx + 20f, 170f), colors.paper.a(0.3f))

        val inner = listOf(cx to 82f, cx + 50f to postTopY - 14f, cx - 50f to postTopY - 14f)
        stroke(roundedPolygonPath(inner, r = 10f), colors.line(colors.paper).a(0.6f), 2.6f)

        deviceLabel(colors.textMeasurer, "CAUTION", cx, 172f, colors.ink, fontSize = 13f)
    }

    twinkle(68f, 110f, 3f, t, 0.3f, colors.inkSoft)
    twinkle(252f, 150f, 3f, t, 0.7f, colors.inkSoft)
    groundLine(292f, colors.inkFaint)
}

// ─── Coming Soon ────────────────────────────────────────────────────────────────
//   Two traffic cones strung with a diagonal band of caution tape — this
//   feature is on its way.

private fun DrawScope.conePath(cx: Float, baseY: Float, height: Float, baseW: Float, topW: Float): Path {
    val topY = baseY - height
    return Path().apply {
        moveTo(d(cx - baseW / 2f), d(baseY))
        quadraticTo(d(cx - baseW * 0.32f), d(baseY - height * 0.55f), d(cx - topW / 2f), d(topY))
        lineTo(d(cx + topW / 2f), d(topY))
        quadraticTo(d(cx + baseW * 0.32f), d(baseY - height * 0.55f), d(cx + baseW / 2f), d(baseY))
        close()
    }
}

internal fun DrawScope.drawSignboardComingSoon(t: Float, colors: SketchyStyle) {
    val leftCx = 116f
    val leftBaseY = 250f
    val leftH = 92f
    val rightCx = 202f
    val rightBaseY = 262f
    val rightH = 122f

    contactShadow(leftCx, leftBaseY + 8f, 34f, 6f, colors.shade)
    contactShadow(rightCx, rightBaseY + 8f, 40f, 7f, colors.shade)

    listOf(
        Triple(leftCx, leftBaseY, leftH) to 52f,
        Triple(rightCx, rightBaseY, rightH) to 62f
    ).forEach { (cone, baseW) ->
        val (cx, baseY, height) = cone
        val base = roundRectPath(cx - baseW / 2f - 5f, baseY - 7f, baseW + 10f, 12f, 3f)
        paint(base, vBrush(baseY - 7f, baseY + 5f, colors.metal.lit(0.2f), colors.metalDark), colors.ink, 2f)

        val body = conePath(cx, baseY, height, baseW, baseW * 0.18f)
        inkShadow(body, colors.outlineShadow)
        cornerShade(body, cx, baseY - height * 0.5f, height * 0.6f, colors.outlineShadow.a(colors.outlineShadow.alpha * 0.6f))
        paint(body, vBrush(baseY - height, baseY, colors.sun, colors.sunDeep), colors.ink, 2.4f)
        sheen(body, pt(cx - baseW * 0.2f, baseY - height * 0.75f), pt(cx + baseW * 0.15f, baseY - height * 0.1f), colors.paper.a(0.28f))

        listOf(0.38f, 0.62f).forEach { f ->
            val bandY = baseY - height * f
            val bandHalf = (baseW / 2f) * (1f - f) + (baseW * 0.09f) * f
            sketchLine(pt(cx - bandHalf * 0.82f, bandY), pt(cx + bandHalf * 0.82f, bandY), colors.hint(colors.paper), 4.5f)
        }
    }

    // caution tape strung between the two cones
    val tapeStart = leftCx + 16f to leftBaseY - leftH * 0.62f
    val tapeEnd = rightCx - 22f to rightBaseY - rightH * 0.7f
    val segments = 7
    for (i in 0 until segments) {
        val f0 = i / segments.toFloat()
        val f1 = (i + 1) / segments.toFloat()
        val x0 = tapeStart.first + (tapeEnd.first - tapeStart.first) * f0
        val y0 = tapeStart.second + (tapeEnd.second - tapeStart.second) * f0
        val x1 = tapeStart.first + (tapeEnd.first - tapeStart.first) * f1
        val y1 = tapeStart.second + (tapeEnd.second - tapeStart.second) * f1
        val color = if (i % 2 == 0) colors.touch(colors.accentRed) else colors.touch(colors.accent)
        sketchLine(pt(x0, y0), pt(x1, y1), color, 5f)
    }

    twinkle(66f, 150f, 3f, t, 0.3f, colors.inkSoft)
    twinkle(254f, 130f, 3f, t, 0.6f, colors.inkSoft)
    groundLine(288f, colors.inkFaint)
}

// ─── Nothing Posted Yet ───────────────────────────────────────────────────────
//   A blank noticeboard on two posts, one lone pin waiting — new posts will
//   show up on this board.

internal fun DrawScope.drawSignboardNoPosts(t: Float, colors: SketchyStyle) {
    val boardX = 70f
    val boardY = 88f
    val boardW = 180f
    val boardH = 108f
    val cx = boardX + boardW / 2f

    contactShadow(boardX + 32f, 282f, 20f, 6f, colors.shade)
    contactShadow(boardX + boardW - 32f, 282f, 20f, 6f, colors.shade)
    castShadow(boardX, boardY + boardH - 6f, boardW, 12f, 30f, colors.shadeSoft)

    val postL = Path().apply { moveTo(d(boardX + 30f), d(boardY + boardH - 10f)); lineTo(d(boardX + 26f), d(272f)) }
    val postR = Path().apply { moveTo(d(boardX + boardW - 30f), d(boardY + boardH - 10f)); lineTo(d(boardX + boardW - 26f), d(272f)) }
    limb(postL, colors.metalDark, colors.ink, 2.2f, thickness = 7f)
    limb(postR, colors.metalDark, colors.ink, 2.2f, thickness = 7f)

    val frame = roundRectPath(boardX, boardY, boardW, boardH, 14f)
    inkShadow(frame, colors.outlineShadow)
    cornerShade(frame, cx, boardY + boardH / 2f, maxOf(boardW, boardH) / 2f * 1.05f, colors.outlineShadow.a(colors.outlineShadow.alpha * 0.6f))
    paint(frame, vBrush(boardY, boardY + boardH, colors.fabric.lit(0.15f), colors.fabricDark), colors.ink, 2.4f)
    sheen(frame, pt(boardX + 14f, boardY + 10f), pt(boardX + boardW - 10f, boardY + boardH * 0.5f), colors.paper.a(0.22f))

    val panel = roundRectPath(boardX + 12f, boardY + 12f, boardW - 24f, boardH - 24f, 6f)
    paint(panel, vBrush(boardY + 12f, boardY + boardH - 12f, colors.clay.lit(0.15f), colors.clay), colors.ink, 1.8f)

    listOf(0.2f to 0.28f, 0.62f to 0.68f, 0.38f to 0.78f, 0.78f to 0.32f).forEachIndexed { i, (fx, fy) ->
        val px = boardX + 20f + fx * (boardW - 40f)
        val py = boardY + 22f + fy * (boardH - 44f)
        sketchCircle(pt(px, py), 1.6f, colors.faint(colors.charcoal), filled = true)
    }
    // one lone pin, waiting for the first post
    val pinAlpha = 0.7f + 0.3f * pulse(t, 0f)
    paintCircle(pt(cx, boardY + boardH / 2f), 3.4f, colors.touch(colors.accentRed, pinAlpha), colors.ink, 1.6f)

    twinkle(64f, boardY - 8f, 3f, t, 0.3f, colors.inkSoft)
    twinkle(256f, boardY + 30f, 3f, t, 0.7f, colors.inkSoft)
    groundLine(292f, colors.inkFaint)
}

// ─── End of the Road ────────────────────────────────────────────────────────────
//   A red octagon on a post at a barricaded dead end — you've reached the
//   end, nothing more to load.

internal fun DrawScope.drawSignboardEndOfRoad(t: Float, colors: SketchyStyle) {
    val cx = 160f
    val cy = 138f
    val radius = 60f

    contactShadow(cx, 284f, 48f, 7f, colors.shade)
    castShadow(cx - 50f, 196f, 100f, 14f, 30f, colors.shadeSoft)

    val post = Path().apply { moveTo(d(cx), d(200f)); lineTo(d(cx), d(272f)) }
    limb(post, colors.metalDark, colors.ink, 2.4f, thickness = 9f)

    val outerPts = (0 until 8).map { i ->
        val a = kotlin.math.PI / 4.0 * i + kotlin.math.PI / 8.0
        (cx + radius * kotlin.math.cos(a).toFloat()) to (cy + radius * kotlin.math.sin(a).toFloat())
    }
    val octagon = roundedPolygonPath(outerPts, r = 10f)
    inkShadow(octagon, colors.outlineShadow)
    cornerShade(octagon, cx, cy, radius * 1.05f, colors.outlineShadow.a(colors.outlineShadow.alpha * 0.6f))
    paint(octagon, vBrush(cy - radius, cy + radius, colors.accentRed.lit(0.05f), colors.clay), colors.ink, 2.6f)
    sheen(octagon, pt(cx - 30f, cy - 40f), pt(cx + 20f, cy + 30f), colors.paper.a(0.22f))

    val innerPts = outerPts.map { (x, y) -> (cx + (x - cx) * 0.86f) to (cy + (y - cy) * 0.86f) }
    stroke(roundedPolygonPath(innerPts, r = 8f), colors.line(colors.paper).a(0.7f), 3.2f)

    deviceLabel(colors.textMeasurer, "STOP", cx, cy, colors.line(colors.paper), fontSize = 24f)

    // low barrier gate across the dead end, diagonal hazard stripes clipped to its face
    val barY = 248f
    val bar = roundRectPath(cx - 54f, barY, 108f, 16f, 5f)
    paint(bar, colors.paper.a(0.92f), colors.ink, 2.2f)
    clipPath(bar) {
        for (i in -2..6) {
            val sx = cx - 54f + i * 16f
            sketchLine(pt(sx, barY + 18f), pt(sx + 14f, barY - 2f), colors.touch(colors.accentRed), 5f)
        }
    }

    twinkle(70f, 120f, 3f, t, 0.25f, colors.inkSoft)
    twinkle(252f, 170f, 3f, t, 0.65f, colors.inkSoft)
    groundLine(292f, colors.inkFaint)
}
