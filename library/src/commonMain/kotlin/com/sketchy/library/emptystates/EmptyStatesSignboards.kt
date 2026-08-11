package com.sketchy.library.emptystates

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.withTransform
import com.sketchy.library.SketchyStyle
import com.sketchy.library.utils.*

/**
 * The **Signboards** category: road and traffic signage — signal lights, warning/stop signs,
 * cones, barriers, noticeboards. An open category; eleven scenes so far, more can follow later.
 */

// ─── Page Not Found ─────────────────────────────────────────────────────────────
//   A route sign knocked crooked on its post, cracked across the middle, "404"
//   painted on its face — the road that led here doesn't exist.

internal fun DrawScope.drawSignboardPageNotFound(t: Float, colors: SketchyStyle) {
    val cx = 150f
    val postTopY = 214f

    contactShadow(cx, 284f, 46f, 7f, colors.shade)
    castShadow(cx - 40f, postTopY + 6f, 96f, 12f, 22f, colors.shadeSoft)

    val post = Path().apply { moveTo(d(cx), d(postTopY)); lineTo(d(cx), d(272f)) }
    limb(post, colors.metalDark, colors.ink, 2.4f, thickness = 9f)
    sketchCircle(pt(cx - 2f, postTopY + 4f), 3f, colors.metalDark, filled = true)

    val tilt = -13f + 2.5f * wave(t, 0f)
    val signCx = cx + 8f
    val signCy = postTopY - 8f
    val signPivot = pt(signCx, signCy)
    withTransform({ rotate(degrees = tilt, pivot = signPivot) }) {
        val arrow = listOf(
            signCx - 74f to signCy - 26f,
            signCx + 34f to signCy - 26f,
            signCx + 74f to signCy,
            signCx + 34f to signCy + 26f,
            signCx - 74f to signCy + 26f
        )
        val panel = roundedPolygonPath(arrow, r = 8f)
        inkShadow(panel, colors.outlineShadow)
        cornerShade(panel, signCx, signCy, 80f, colors.outlineShadow.a(colors.outlineShadow.alpha * 0.6f))
        paint(panel, vBrush(signCy - 26f, signCy + 26f, colors.leaf.lit(0.05f), colors.leafDark), colors.ink, 2.4f)
        sheen(panel, pt(signCx - 40f, signCy - 20f), pt(signCx, signCy + 10f), colors.paper.a(0.2f))
        signLabel(colors.textMeasurer, "404", signCx - 12f, signCy, colors.line(colors.paper), fontSize = 22f, letterSpacing = 1f)
        sketchLine(pt(signCx - 32f, signCy - 22f), pt(signCx - 10f, signCy + 4f), colors.ink, 1.6f)
        sketchLine(pt(signCx - 10f, signCy + 4f), pt(signCx + 6f, signCy - 8f), colors.ink, 1.6f)
    }

    // a dashed lane line running toward the sign and simply stopping
    for (i in 0 until 5) {
        val fx = 56f + i * 20f
        sketchLine(pt(fx, 272f), pt(fx + 10f, 272f), colors.inkFaint, 3f)
    }

    twinkle(66f, 150f, 3f, t, 0.25f, colors.inkSoft)
    twinkle(258f, 120f, 3f, t, 0.65f, colors.inkSoft)
    groundLine(292f, colors.inkFaint)
}

// ─── Network Error ──────────────────────────────────────────────────────────────
//   A signal head leaning off true, every lens dark but a flickering red, a
//   frayed wire sparking loose below it — the connection dropped mid-signal.

internal fun DrawScope.drawSignboardNetworkError(t: Float, colors: SketchyStyle) {
    val cx = 160f
    val lean = -7f

    contactShadow(cx, 284f, 46f, 7f, colors.shade)
    castShadow(134f, 172f, 52f, 12f, 22f, colors.shadeSoft)

    val leanPivot = pt(cx, 272f)
    withTransform({ rotate(degrees = lean, pivot = leanPivot) }) {
        val pole = Path().apply { moveTo(d(cx), d(272f)); lineTo(d(cx), d(120f)) }
        limb(pole, colors.metalDark, colors.ink, 2.4f, thickness = 9f)

        val housing = roundRectPath(cx - 30f, 52f, 60f, 128f, 18f)
        inkShadow(housing, colors.outlineShadow)
        cornerShade(housing, cx, 116f, 78f, colors.outlineShadow.a(colors.outlineShadow.alpha * 0.6f))
        paint(housing, vBrush(52f, 180f, colors.charcoal.lit(0.18f), colors.charcoal), colors.ink, 2.4f)
        sheen(housing, pt(cx - 20f, 60f), pt(cx + 16f, 150f), colors.metal.a(0.18f))

        val lensY = listOf(80f, 116f, 152f)
        val flicker = 0.2f + 0.6f * pulse(t, 0f)
        paintCircle(pt(cx, lensY[0]), 14f, colors.accentRed.a(flicker), colors.ink, 2f)
        paintCircle(pt(cx, lensY[1]), 14f, colors.accent.a(0.16f), colors.ink, 2f)
        paintCircle(pt(cx, lensY[2]), 14f, colors.accentGreen.a(0.16f), colors.ink, 2f)

        val wire = Path().apply {
            moveTo(d(cx - 28f), d(90f))
            quadraticTo(d(cx - 58f), d(110f), d(cx - 60f), d(146f))
        }
        stroke(wire, colors.line(colors.metalDark), 2f)
        val sparkAlpha = 0.4f + 0.6f * pulse(t, 0.35f)
        sketchLine(pt(cx - 60f, 146f), pt(cx - 52f, 138f), colors.touch(colors.sun, sparkAlpha), 2f)
        sketchLine(pt(cx - 60f, 146f), pt(cx - 66f, 136f), colors.touch(colors.sun, sparkAlpha), 2f)
        sketchLine(pt(cx - 60f, 146f), pt(cx - 56f, 156f), colors.touch(colors.sun, sparkAlpha * 0.8f), 1.6f)

        val plate = roundRectPath(cx - 36f, 190f, 72f, 24f, 5f)
        paint(plate, vBrush(190f, 214f, colors.metal.lit(0.15f), colors.metalDark), colors.ink, 2f)
        signLabel(colors.textMeasurer, "SIGNAL LOST", cx, 202f, colors.ink, fontSize = 10.5f, maxWidth = 62f)
    }

    twinkle(70f, 96f, 3f, t, 0.2f, colors.inkSoft)
    twinkle(252f, 150f, 3f, t, 0.6f, colors.inkSoft)
    groundLine(292f, colors.inkFaint)
}

// ─── No Data Available ──────────────────────────────────────────────────────────
//   An overhead highway gantry board, its dot-matrix grid gone dark except one
//   idle blinking pixel, "NO DATA" barely legible on the dead display.

internal fun DrawScope.drawSignboardNoData(t: Float, colors: SketchyStyle) {
    val boardX = 66f
    val boardY = 82f
    val boardW = 188f
    val boardH = 66f
    val cx = boardX + boardW / 2f

    contactShadow(104f, 284f, 16f, 6f, colors.shade)
    contactShadow(216f, 284f, 16f, 6f, colors.shade)
    castShadow(boardX, boardY + boardH - 4f, boardW, 12f, 26f, colors.shadeSoft)

    listOf(104f, 216f).forEach { legX ->
        val leg = Path().apply { moveTo(d(legX), d(boardY + boardH - 6f)); lineTo(d(legX), d(272f)) }
        limb(leg, colors.metalDark, colors.ink, 2.2f, thickness = 8f)
    }

    val frame = roundRectPath(boardX, boardY, boardW, boardH, 10f)
    inkShadow(frame, colors.outlineShadow)
    cornerShade(frame, cx, boardY + boardH / 2f, maxOf(boardW, boardH) / 2f * 1.05f, colors.outlineShadow.a(colors.outlineShadow.alpha * 0.6f))
    paint(frame, vBrush(boardY, boardY + boardH, colors.charcoal.lit(0.08f), colors.charcoal), colors.ink, 2.4f)
    sheen(frame, pt(boardX + 14f, boardY + 8f), pt(boardX + boardW - 10f, boardY + boardH * 0.5f), colors.paper.a(0.14f))

    val cols = 14
    val rows = 4
    val padX = 14f
    val padY = 10f
    val cellW = (boardW - padX * 2f) / (cols - 1)
    val cellH = (boardH - padY * 2f) / (rows - 1)
    val liveIndex = (hash01((t * 3f).toInt()) * cols * rows).toInt()
    var idx = 0
    for (row in 0 until rows) {
        for (col in 0 until cols) {
            val px = boardX + padX + col * cellW
            val py = boardY + padY + row * cellH
            val alive = idx == liveIndex
            val alpha = if (alive) 0.5f + 0.5f * pulse(t, 0f) else 0.12f
            sketchCircle(pt(px, py), 1.3f, colors.faint(colors.accentBlue).a(alpha), filled = true)
            idx++
        }
    }

    signLabel(colors.textMeasurer, "NO DATA", cx, boardY + boardH / 2f, colors.line(colors.paper).a(0.5f), fontSize = 15f, letterSpacing = 1.5f)

    twinkle(58f, boardY - 6f, 3f, t, 0.3f, colors.inkSoft)
    twinkle(262f, boardY + 20f, 3f, t, 0.7f, colors.inkSoft)
    groundLine(292f, colors.inkFaint)
}

// ─── Sign In Required ───────────────────────────────────────────────────────────
//   A checkpoint boom gate down across the road, a padlock badge on its post
//   and a plate spelling out what it takes to get past.

internal fun DrawScope.drawSignboardUnauthorized(t: Float, colors: SketchyStyle) {
    val postX = 96f
    val pivotY = 210f

    contactShadow(postX, 284f, 22f, 6f, colors.shade)
    castShadow(postX - 10f, pivotY - 30f, 140f, 14f, 26f, colors.shadeSoft)

    val post = Path().apply { moveTo(d(postX), d(pivotY)); lineTo(d(postX), d(272f)) }
    limb(post, colors.metalDark, colors.ink, 2.4f, thickness = 10f)

    val bob = 1.5f * wave(t, 0f)
    val armAngle = -18f + bob
    val armPivot = pt(postX, pivotY)
    withTransform({ rotate(degrees = armAngle, pivot = armPivot) }) {
        val arm = roundRectPath(postX - 6f, pivotY - 10f, 172f, 18f, 6f)
        inkShadow(arm, colors.outlineShadow)
        paint(arm, colors.paper.a(0.94f), colors.ink, 2.2f)
        clipPath(arm) {
            for (i in -1..9) {
                val sx = postX - 6f + i * 18f
                sketchLine(pt(sx, pivotY + 12f), pt(sx + 14f, pivotY - 14f), colors.touch(colors.accentRed), 6f)
            }
        }
        val box = roundRectPath(postX - 26f, pivotY - 16f, 22f, 30f, 4f)
        paint(box, vBrush(pivotY - 16f, pivotY + 14f, colors.metal.lit(0.15f), colors.metalDark), colors.ink, 2f)
    }

    val lockCy = 172f
    val shackle = Path().apply {
        arcTo(
            rect = Rect(pt(postX - 10f, lockCy - 22f), Size(d(20f), d(20f))),
            startAngleDegrees = 180f,
            sweepAngleDegrees = 180f,
            forceMoveTo = true
        )
    }
    stroke(shackle, colors.line(colors.metalDark), 3f)
    val lockBody = roundRectPath(postX - 13f, lockCy - 4f, 26f, 22f, 5f)
    paint(lockBody, vBrush(lockCy - 4f, lockCy + 18f, colors.sun, colors.sunDeep), colors.ink, 2.2f)
    sketchCircle(pt(postX, lockCy + 6f), 2.2f, colors.ink, filled = true)

    val plateY = 224f
    val plate = roundRectPath(postX - 46f, plateY, 92f, 26f, 6f)
    inkShadow(plate, colors.outlineShadow)
    paint(plate, vBrush(plateY, plateY + 26f, colors.fabric.lit(0.15f), colors.fabricDark), colors.ink, 2.2f)
    signLabel(colors.textMeasurer, "SIGN IN REQUIRED", postX, plateY + 13f, colors.ink, fontSize = 10f, maxWidth = 82f)

    twinkle(64f, 130f, 3f, t, 0.3f, colors.inkSoft)
    twinkle(260f, 90f, 3f, t, 0.7f, colors.inkSoft)
    groundLine(292f, colors.inkFaint)
}

// ─── Access Denied ──────────────────────────────────────────────────────────────
//   A red no-entry disc on its post, a plate spelling out the refusal —
//   distinct from the checkpoint gate: this one doesn't open at all.

internal fun DrawScope.drawSignboardForbidden(t: Float, colors: SketchyStyle) {
    val cx = 160f
    val cy = 132f
    val radius = 58f

    contactShadow(cx, 284f, 46f, 7f, colors.shade)
    castShadow(cx - 48f, 188f, 96f, 14f, 28f, colors.shadeSoft)

    val post = Path().apply { moveTo(d(cx), d(192f)); lineTo(d(cx), d(272f)) }
    limb(post, colors.metalDark, colors.ink, 2.4f, thickness = 9f)

    val disc = ellipsePath(cx, cy, radius, radius)
    inkShadow(disc, colors.outlineShadow)
    cornerShade(disc, cx, cy, radius * 1.05f, colors.outlineShadow.a(colors.outlineShadow.alpha * 0.6f))
    paint(disc, vBrush(cy - radius, cy + radius, colors.accentRed.lit(0.05f), colors.clay), colors.ink, 2.6f)
    sheen(disc, pt(cx - 26f, cy - 34f), pt(cx + 16f, cy + 24f), colors.paper.a(0.22f))

    val bar = roundRectPath(cx - radius * 0.72f, cy - 11f, radius * 1.44f, 22f, 8f)
    paint(bar, colors.paper, colors.ink, 2.4f)

    val plateY = 200f
    val plate = roundRectPath(cx - 50f, plateY, 100f, 26f, 6f)
    inkShadow(plate, colors.outlineShadow)
    paint(plate, vBrush(plateY, plateY + 26f, colors.fabric.lit(0.15f), colors.fabricDark), colors.ink, 2.2f)
    signLabel(colors.textMeasurer, "ACCESS DENIED", cx, plateY + 13f, colors.ink, fontSize = 11f, maxWidth = 88f)

    twinkle(66f, 110f, 3f, t, 0.25f, colors.inkSoft)
    twinkle(254f, 160f, 3f, t, 0.65f, colors.inkSoft)
    groundLine(292f, colors.inkFaint)
}

// ─── Under Maintenance ──────────────────────────────────────────────────────────
//   An A-frame barricade, candy-striped board planted across the road, a
//   plaque staked above it and an amber beacon blinking on the corner.

internal fun DrawScope.drawSignboardMaintenance(t: Float, colors: SketchyStyle) {
    val cx = 160f
    val boardY = 170f
    val boardH = 40f
    val boardW = 170f
    val boardX = cx - boardW / 2f

    contactShadow(cx - 60f, 282f, 18f, 6f, colors.shade)
    contactShadow(cx + 60f, 282f, 18f, 6f, colors.shade)
    castShadow(boardX, boardY + boardH - 4f, boardW, 12f, 24f, colors.shadeSoft)

    listOf(-1f, 1f).forEach { side ->
        val leg = Path().apply {
            moveTo(d(cx + side * 6f), d(boardY + boardH - 8f))
            lineTo(d(cx + side * 64f), d(274f))
        }
        limb(leg, colors.woodDark, colors.ink, 2.2f, thickness = 8f)
    }

    val board = roundRectPath(boardX, boardY, boardW, boardH, 6f)
    inkShadow(board, colors.outlineShadow)
    paint(board, colors.paper.a(0.95f), colors.ink, 2.2f)
    clipPath(board) {
        for (i in -2..10) {
            val sx = boardX + i * 18f
            sketchLine(pt(sx, boardY + boardH + 4f), pt(sx + 14f, boardY - 4f), colors.touch(colors.sun), 7f)
        }
    }

    val plateY = 118f
    val plateW = 154f
    val plate = roundRectPath(cx - plateW / 2f, plateY, plateW, 30f, 6f)
    inkShadow(plate, colors.outlineShadow)
    cornerShade(plate, cx, plateY + 15f, plateW / 2f * 1.05f, colors.outlineShadow.a(colors.outlineShadow.alpha * 0.6f))
    paint(plate, vBrush(plateY, plateY + 30f, colors.fabric.lit(0.15f), colors.fabricDark), colors.ink, 2.2f)
    signLabel(colors.textMeasurer, "UNDER MAINTENANCE", cx, plateY + 15f, colors.ink, fontSize = 11.5f, maxWidth = plateW - 20f)
    val stake = Path().apply { moveTo(d(cx), d(plateY + 30f)); lineTo(d(cx), d(boardY)) }
    limb(stake, colors.metalDark, colors.ink, 2f, thickness = 5f)

    val beaconAlpha = 0.4f + 0.6f * pulse(t, 0f)
    glow(cx + plateW / 2f - 10f, plateY + 6f, 16f, colors.sun.a(0.4f * beaconAlpha))
    paintCircle(pt(cx + plateW / 2f - 10f, plateY + 6f), 6f, colors.sun.a(0.6f + 0.4f * beaconAlpha), colors.ink, 1.8f)

    twinkle(60f, 140f, 3f, t, 0.3f, colors.inkSoft)
    twinkle(258f, 200f, 3f, t, 0.7f, colors.inkSoft)
    groundLine(292f, colors.inkFaint)
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

        signLabel(colors.textMeasurer, "CAUTION", cx, 172f, colors.ink, fontSize = 14f, maxWidth = 86f)
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
    signLabel(colors.textMeasurer, "NOTHING POSTED YET", cx, boardY + boardH * 0.4f, colors.ink, fontSize = 12f, maxWidth = boardW - 48f)
    // one lone pin, waiting for the first post
    val pinAlpha = 0.7f + 0.3f * pulse(t, 0f)
    paintCircle(pt(cx, boardY + boardH * 0.74f), 3.4f, colors.touch(colors.accentRed, pinAlpha), colors.ink, 1.6f)

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

    signLabel(colors.textMeasurer, "STOP", cx, cy, colors.line(colors.paper), fontSize = 27f, letterSpacing = 1.5f)

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
