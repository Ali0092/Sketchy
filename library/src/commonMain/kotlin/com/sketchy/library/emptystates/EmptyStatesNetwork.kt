package com.sketchy.library.emptystates

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import com.sketchy.library.SketchyStyle
import com.sketchy.library.utils.*

/**
 * The **Network** category: every scene here is built only from network hardware — routers,
 * servers, switches, phones, cables, a scanner, a wall jack — never an abstract icon/sign (no
 * wifi-bar glyphs, no padlocks, no warning triangles). Status reads through device-native cues:
 * blinking LEDs, an unplugged cable, a frayed cord, an empty port — and, where a device plausibly
 * has one, a short status readout baked into its own screen, LED strip, or engraved plate (never a
 * free-standing sign).
 */

// ─── No Internet ─────────────────────────────────────────────────────────────
//   A modern mesh-router puck with a lit "OFFLINE" status strip, a weak fading
//   signal search rising from its crown, and a cable dangling unplugged.

internal fun DrawScope.drawNetworkNoInternet(t: Float, colors: SketchyStyle) {
    val cx = 160f
    val bodyW = 78f
    val bodyH = 112f
    val bodyY = 112f
    val bodyX = cx - bodyW / 2f
    val r = 30f

    contactShadow(cx, bodyY + bodyH + 14f, 48f, 7f, colors.shade)

    listOf(18f, 32f).forEachIndexed { i, radius ->
        val k = 0.15f + 0.35f * pulse(t, i * 0.3f)
        val arc = Path().apply {
            arcTo(
                rect = Rect(pt(cx - radius, bodyY - radius * 0.5f), Size(d(radius * 2), d(radius * 2))),
                startAngleDegrees = 200f,
                sweepAngleDegrees = 140f,
                forceMoveTo = true
            )
        }
        drawPath(arc, color = colors.accentRed.a(k), style = dashed())
    }

    val body = roundRectPath(bodyX, bodyY, bodyW, bodyH, r)
    inkShadow(body, colors.outlineShadow)
    cornerShade(body, cx, bodyY + bodyH / 2f, bodyH / 2f * 1.08f, colors.outlineShadow.a(colors.outlineShadow.alpha * 0.6f))
    paint(body, vBrush(bodyY, bodyY + bodyH, colors.metal.lit(0.3f), colors.metalDark), colors.ink, 2.4f)
    shade(body, hBrush(cx, bodyX + bodyW, colors.shade.a(0f), colors.shade))
    sheen(body, pt(bodyX + 12f, bodyY + 10f), pt(bodyX + bodyW - 8f, bodyY + bodyH * 0.55f), colors.paper.a(0.3f))

    // a modern accent trim ring near the crown, like a mesh point's brand band
    sketchLine(pt(bodyX + 16f, bodyY + 20f), pt(bodyX + bodyW - 16f, bodyY + 20f), colors.accentBlue.a(0.85f), 2f)

    for (i in 0 until 3) {
        val lx = cx - 18f + i * 18f
        val on = i == 2
        val alpha = if (on) 0.35f + 0.65f * pulse(t, 0f) else 1f
        val color = if (on) colors.accentRed.a(alpha) else colors.inkFaint
        sketchCircle(pt(lx, bodyY + 46f), 3f, color, filled = true)
    }

    // lit status strip, baked into the puck's own face
    val statusAlpha = 0.4f + 0.6f * pulse(t, 0f)
    deviceLabel(colors.textMeasurer, "OFFLINE", cx, bodyY + bodyH - 22f, colors.accentRed.a(statusAlpha), fontSize = 11f)

    // a dangling, unplugged ethernet cable
    val sway = 5f * wave(t, 0.4f)
    val cable = Path().apply {
        moveTo(d(bodyX + bodyW - 8f), d(bodyY + bodyH))
        cubicTo(
            d(bodyX + bodyW + 16f), d(bodyY + bodyH + 28f),
            d(bodyX + bodyW + sway), d(bodyY + bodyH + 44f),
            d(bodyX + bodyW + 4f + sway), d(bodyY + bodyH + 58f)
        )
    }
    limb(cable, colors.charcoal, colors.ink, 2.2f, thickness = 5f)
    val plug = roundRectPath(bodyX + bodyW - 4f + sway, bodyY + bodyH + 56f, 16f, 12f, 3f)
    paint(plug, colors.metalDark, colors.ink, 2f)

    twinkle(70f, 116f, 3f, t, 0.2f, colors.inkSoft)
    twinkle(250f, 150f, 3f, t, 0.6f, colors.inkSoft)
    groundLine(280f, colors.inkFaint)
}

// ─── Page Not Found (404) ─────────────────────────────────────────────────────
//   A flush low-voltage wall plate with "404" silkscreened above the jack, a
//   dim link LED, and a cable swinging as it hunts for a plug it never reaches.

internal fun DrawScope.drawNetworkPageNotFound(t: Float, colors: SketchyStyle) {
    val cx = 160f
    val plateY = 132f
    val plateW = 92f
    val plateH = 78f
    val plateX = cx - plateW / 2f
    val jackCy = plateY + plateH / 2f + 10f

    contactShadow(cx, plateY + plateH + 10f, 46f, 6f, colors.shade)

    val plate = roundRectPath(plateX, plateY, plateW, plateH, 12f)
    inkShadow(plate, colors.outlineShadow)
    cornerShade(plate, cx, plateY + plateH / 2f, maxOf(plateW, plateH) / 2f * 1.1f, colors.outlineShadow.a(colors.outlineShadow.alpha * 0.6f))
    paint(plate, vBrush(plateY, plateY + plateH, colors.paper, colors.metal.lit(0.2f)), colors.ink, 2.2f)
    sheen(plate, pt(plateX + 10f, plateY + 8f), pt(plateX + plateW - 10f, plateY + plateH * 0.5f), colors.paper.a(0.3f))

    // a permanent, unpowered silkscreen label — always visible, never pulsed
    deviceLabel(colors.textMeasurer, "404", cx, plateY + 22f, colors.ink, fontSize = 15f)

    val jack = roundRectPath(cx - 13f, jackCy - 9f, 26f, 18f, 3f)
    paint(jack, colors.charcoal, colors.ink, 1.8f)
    val linkAlpha = 0.25f + 0.2f * pulse(t, 0.5f)
    sketchCircle(pt(cx + 20f, jackCy), 2.4f, colors.accentBlue.a(linkAlpha), filled = true)

    val swing = 32f * wave(t, 0f)
    val anchorX = cx + 58f + swing
    val anchorY = jackCy + 12f
    val cable = Path().apply {
        moveTo(d(cx + 50f), d(34f))
        cubicTo(
            d(cx + 50f + swing * 0.5f), d(110f),
            d(anchorX - swing * 0.3f), d(174f),
            d(anchorX), d(anchorY)
        )
    }
    limb(cable, colors.charcoal, colors.ink, 2.4f, thickness = 6f)
    val plug = roundRectPath(anchorX - 9f, anchorY - 2f, 18f, 14f, 3f)
    paint(plug, colors.metalDark, colors.ink, 2.2f)
    sketchLine(pt(anchorX - 5f, anchorY + 12f), pt(anchorX - 5f, anchorY + 18f), colors.ink, 1.8f)
    sketchLine(pt(anchorX + 5f, anchorY + 12f), pt(anchorX + 5f, anchorY + 18f), colors.ink, 1.8f)

    twinkle(80f, 130f, 3f, t, 0.3f, colors.inkSoft)
    twinkle(250f, 96f, 3f, t, 0.7f, colors.inkSoft)
    groundLine(250f, colors.inkFaint)
}

// ─── Bad Gateway (502) ─────────────────────────────────────────────────────────
//   Two 1U gateway appliances whose link snaps in the middle; the erroring box
//   flashes a lit "502" readout in time with its distressed LED.

internal fun DrawScope.drawNetworkBadGateway(t: Float, colors: SketchyStyle) {
    val leftX = 50f
    val rightX = 186f
    val boxY = 118f
    val boxW = 84f
    val boxH = 68f
    val midX = (leftX + boxW + rightX) / 2f
    val midY = boxY + boxH / 2f

    contactShadow(leftX + boxW / 2f, boxY + boxH + 14f, 42f, 6f, colors.shade)
    contactShadow(rightX + boxW / 2f, boxY + boxH + 14f, 42f, 6f, colors.shade)

    val boxA = roundRectPath(leftX, boxY, boxW, boxH, 11f)
    inkShadow(boxA, colors.outlineShadow)
    cornerShade(boxA, leftX + boxW / 2f, boxY + boxH / 2f, maxOf(boxW, boxH) / 2f * 1.12f, colors.outlineShadow.a(colors.outlineShadow.alpha * 0.6f))
    paint(boxA, vBrush(boxY, boxY + boxH, colors.metal.lit(0.3f), colors.metalDark), colors.ink, 2.4f)
    sheen(boxA, pt(leftX + 8f, boxY + 8f), pt(leftX + boxW - 8f, boxY + boxH * 0.5f), colors.paper.a(0.3f))
    for (i in 0 until 4) {
        val vx = leftX + 16f + i * 14f
        sketchLine(pt(vx, boxY + 12f), pt(vx, boxY + 26f), colors.inkFaint, 1.4f)
    }
    sketchCircle(pt(leftX + boxW - 16f, boxY + boxH - 16f), 3.2f, colors.accentGreen, filled = true)

    val boxB = roundRectPath(rightX, boxY, boxW, boxH, 11f)
    inkShadow(boxB, colors.outlineShadow)
    cornerShade(boxB, rightX + boxW / 2f, boxY + boxH / 2f, maxOf(boxW, boxH) / 2f * 1.12f, colors.outlineShadow.a(colors.outlineShadow.alpha * 0.6f))
    paint(boxB, vBrush(boxY, boxY + boxH, colors.metal.lit(0.3f), colors.metalDark), colors.ink, 2.4f)
    sheen(boxB, pt(rightX + 8f, boxY + 8f), pt(rightX + boxW - 8f, boxY + boxH * 0.5f), colors.paper.a(0.3f))
    for (i in 0 until 4) {
        val vx = rightX + 16f + i * 14f
        sketchLine(pt(vx, boxY + 12f), pt(vx, boxY + 26f), colors.inkFaint, 1.4f)
    }
    val flicker = 0.35f + 0.65f * pulse(t, 0.4f)
    sketchCircle(pt(rightX + 16f, boxY + boxH - 16f), 3.2f, colors.accentRed.a(flicker), filled = true)
    deviceLabel(colors.textMeasurer, "502", rightX + boxW / 2f, boxY + boxH - 36f, colors.accentRed.a(flicker), fontSize = 12f)

    val gap = 10f + 3f * pulse(t, 0f)
    val segA = Path().apply {
        moveTo(d(leftX + boxW), d(midY))
        lineTo(d(midX - gap), d(midY))
    }
    val segB = Path().apply {
        moveTo(d(midX + gap), d(midY))
        lineTo(d(rightX), d(midY))
    }
    limb(segA, colors.charcoal, colors.ink, 2.2f, thickness = 5f)
    limb(segB, colors.charcoal, colors.ink, 2.2f, thickness = 5f)

    val sparkAlpha = 0.4f + 0.6f * pulse(t, 0.15f)
    sketchLine(pt(midX - 5f, midY - 9f), pt(midX + 2f, midY - 1f), colors.accentRed.a(sparkAlpha), 2.2f)
    sketchLine(pt(midX + 2f, midY - 1f), pt(midX - 4f, midY + 7f), colors.accentRed.a(sparkAlpha), 2.2f)
    sketchLine(pt(midX - 4f, midY + 7f), pt(midX + 5f, midY + 11f), colors.accentRed.a(sparkAlpha), 2.2f)

    twinkle(76f, 90f, 3f, t, 0.2f, colors.inkSoft)
    twinkle(250f, 196f, 3f, t, 0.6f, colors.inkSoft)
    groundLine(210f, colors.inkFaint)
}

// ─── Server Error (400+/500+) ──────────────────────────────────────────────────
//   A server tower overheating, an embedded status window flashing "ERROR" in
//   time with its flagged drive LED, and a frayed power cord sparking at its base.

internal fun DrawScope.drawNetworkServerError(t: Float, colors: SketchyStyle) {
    val towerX = 118f
    val towerY = 60f
    val towerW = 84f
    val towerH = 190f

    contactShadow(towerX + towerW / 2f, towerY + towerH + 10f, 50f, 7f, colors.shade)

    val tower = roundRectPath(towerX, towerY, towerW, towerH, 12f)
    inkShadow(tower, colors.outlineShadow)
    cornerShade(tower, towerX + towerW / 2f, towerY + towerH / 2f, towerW / 2f * 1.06f, colors.outlineShadow.a(colors.outlineShadow.alpha * 0.6f))
    paint(tower, vBrush(towerY, towerY + towerH, colors.metal.lit(0.3f), colors.metalDark), colors.ink, 2.4f)
    shade(tower, hBrush(towerX, towerX + towerW, colors.shade.a(0f), colors.shade))
    sheen(tower, pt(towerX + 10f, towerY + 12f), pt(towerX + towerW - 10f, towerY + towerH * 0.4f), colors.paper.a(0.28f))

    for (i in 0 until 4) {
        val y = towerY + 16f + i * 7f
        sketchLine(pt(towerX + 16f, y), pt(towerX + towerW - 16f, y), colors.inkFaint, 1.4f)
    }

    // embedded status window
    val statusAlpha = 0.4f + 0.6f * pulse(t, 0.2f)
    val statusY = towerY + 54f
    val statusWin = roundRectPath(towerX + 14f, statusY, towerW - 28f, 24f, 5f)
    fill(statusWin, colors.charcoal.a(0.6f))
    stroke(statusWin, colors.inkFaint, 1.4f)
    deviceLabel(colors.textMeasurer, "ERROR", towerX + towerW / 2f, statusY + 12f, colors.accentRed.a(statusAlpha), fontSize = 10f)

    for (row in 0..2) {
        val y = towerY + 96f + row * 30f
        sketchLine(pt(towerX + 12f, y), pt(towerX + towerW - 26f, y), colors.inkFaint, 1.4f)
        val flagged = row == 1
        val alpha = if (flagged) 0.4f + 0.6f * pulse(t, 0.2f) else 1f
        val ledColor = if (flagged) colors.accentRed.a(alpha) else colors.accentGreen
        sketchCircle(pt(towerX + towerW - 14f, y), 3f, ledColor, filled = true)
    }

    val cord = Path().apply {
        moveTo(d(towerX), d(towerY + towerH - 18f))
        cubicTo(
            d(towerX - 24f), d(towerY + towerH - 6f),
            d(towerX - 28f), d(towerY + towerH + 16f),
            d(towerX - 32f), d(towerY + towerH + 28f)
        )
    }
    limb(cord, colors.charcoal, colors.ink, 2.2f, thickness = 5f)
    val sparkAlpha = 0.35f + 0.65f * pulse(t, 0f)
    val sx = towerX - 32f
    val sy = towerY + towerH + 28f
    sketchLine(pt(sx - 5f, sy - 6f), pt(sx + 2f, sy), colors.accentRed.a(sparkAlpha), 2f)
    sketchLine(pt(sx + 2f, sy), pt(sx - 4f, sy + 7f), colors.accentRed.a(sparkAlpha), 2f)

    for (i in 0..1) {
        val sway = 6f * wave(t, i * 0.3f)
        val rise = loop(t, i * 0.3f)
        val topY = towerY - 8f - rise * 20f
        sketchLine(
            pt(towerX + 24f + i * 30f + sway, topY + 16f),
            pt(towerX + 24f + i * 30f - sway, topY),
            colors.inkFaint.a(0.5f * (1f - rise)),
            1.4f
        )
    }

    twinkle(96f, 108f, 3f, t, 0.4f, colors.inkSoft)
    twinkle(256f, 160f, 3f, t, 0.7f, colors.inkSoft)
    groundLine(286f, colors.inkFaint)
}

// ─── Unsecured Wi-Fi ────────────────────────────────────────────────────────────
//   A slim router puck and a thin-bezel laptop linked by a cable stripped bare
//   in the middle; the laptop's own screen flashes an "UNSECURE" warning line.

internal fun DrawScope.drawNetworkUnsecureWifi(t: Float, colors: SketchyStyle) {
    val routerX = 56f
    val routerY = 178f
    val routerW = 74f
    val routerH = 36f
    val lidX = 192f
    val laptopBaseY = 226f

    contactShadow(routerX + routerW / 2f, routerY + routerH + 10f, 38f, 5f, colors.shade)
    contactShadow(lidX + 39f, laptopBaseY + 8f, 50f, 6f, colors.shade)

    val router = roundRectPath(routerX, routerY, routerW, routerH, 17f)
    inkShadow(router, colors.outlineShadow)
    cornerShade(router, routerX + routerW / 2f, routerY + routerH / 2f, routerW / 2f * 1.05f, colors.outlineShadow.a(colors.outlineShadow.alpha * 0.6f))
    paint(router, vBrush(routerY, routerY + routerH, colors.metal.lit(0.3f), colors.metalDark), colors.ink, 2.2f)
    sheen(router, pt(routerX + 10f, routerY + 6f), pt(routerX + routerW - 10f, routerY + routerH * 0.6f), colors.paper.a(0.3f))
    sketchCircle(pt(routerX + 16f, routerY + routerH - 10f), 2.6f, colors.accentGreen, filled = true)

    listOf(12f, 20f).forEachIndexed { i, radius ->
        val k = 0.4f + 0.4f * pulse(t, i * 0.25f)
        val arc = Path().apply {
            arcTo(
                rect = Rect(
                    pt(routerX + routerW / 2f - radius, routerY - radius * 0.6f),
                    Size(d(radius * 2), d(radius * 2))
                ),
                startAngleDegrees = 210f,
                sweepAngleDegrees = 120f,
                forceMoveTo = true
            )
        }
        drawPath(arc, color = colors.accent.a(k), style = thin(1.6f))
    }

    val lidY = laptopBaseY - 56f
    val lidH = 56f
    val lidW = 78f
    val lid = roundRectPath(lidX, lidY, lidW, lidH, 8f)
    inkShadow(lid, colors.outlineShadow)
    cornerShade(lid, lidX + lidW / 2f, lidY + lidH / 2f, maxOf(lidW, lidH) / 2f * 1.05f, colors.outlineShadow.a(colors.outlineShadow.alpha * 0.6f))
    paint(lid, vBrush(lidY, lidY + lidH, colors.sky.a(0.5f), colors.metal), colors.ink, 2.2f)
    sheen(lid, pt(lidX + 10f, lidY + 8f), pt(lidX + lidW - 10f, lidY + lidH * 0.5f), colors.paper.a(0.28f))
    sketchCircle(pt(lidX + lidW / 2f, lidY + 7f), 1.6f, colors.accentBlue.a(0.5f), filled = true)
    val warnAlpha = 0.4f + 0.4f * pulse(t, 0.3f)
    deviceLabel(colors.textMeasurer, "UNSECURE", lidX + lidW / 2f, lidY + lidH / 2f + 5f, colors.accentRed.a(warnAlpha), fontSize = 9f)
    val base = roundRectPath(lidX - 7f, laptopBaseY, lidW + 14f, 9f, 3f)
    paint(base, colors.metalDark, colors.ink, 2f)

    // the connecting cable, stripped in the middle to bare, exposed wire
    val start = pt(routerX + routerW, routerY + routerH - 8f)
    val end = pt(lidX - 2f, laptopBaseY + 2f)
    fun along(f: Float) = Offset(
        start.x + (end.x - start.x) * f,
        start.y + (end.y - start.y) * f
    )
    val gapStart = along(0.42f)
    val gapEnd = along(0.58f)
    limb(
        Path().apply { moveTo(start.x, start.y); lineTo(gapStart.x, gapStart.y) },
        colors.charcoal, colors.ink, 2f, thickness = 4f
    )
    limb(
        Path().apply { moveTo(gapEnd.x, gapEnd.y); lineTo(end.x, end.y) },
        colors.charcoal, colors.ink, 2f, thickness = 4f
    )

    listOf(colors.accentRed, colors.accent, colors.accentBlue).forEachIndexed { i, c ->
        val off = d((i - 1) * 2.4f)
        sketchLine(Offset(gapStart.x, gapStart.y + off), Offset(gapEnd.x, gapEnd.y + off), c, 1.3f)
    }

    twinkle(38f, 148f, 3f, t, 0.3f, colors.inkSoft)
    twinkle(258f, 246f, 3f, t, 0.6f, colors.inkSoft)
    groundLine(246f, colors.inkFaint)
}

// ─── No Data ────────────────────────────────────────────────────────────────────
//   A NAS/SSD enclosure with its drive bay slid open and a "0GB" capacity
//   readout lit beside it — the tray, and the number, both empty.

internal fun DrawScope.drawNetworkNoData(t: Float, colors: SketchyStyle) {
    val boxX = 90f
    val boxY = 104f
    val boxW = 140f
    val boxH = 100f

    contactShadow(boxX + boxW / 2f, boxY + boxH + 12f, 62f, 7f, colors.shade)

    val box = roundRectPath(boxX, boxY, boxW, boxH, 14f)
    inkShadow(box, colors.outlineShadow)
    cornerShade(box, boxX + boxW / 2f, boxY + boxH / 2f, maxOf(boxW, boxH) / 2f * 1.08f, colors.outlineShadow.a(colors.outlineShadow.alpha * 0.6f))
    paint(box, vBrush(boxY, boxY + boxH, colors.metal.lit(0.3f), colors.metalDark), colors.ink, 2.4f)
    shade(box, hBrush(boxX, boxX + boxW, colors.shade.a(0f), colors.shade))
    sheen(box, pt(boxX + 12f, boxY + 12f), pt(boxX + boxW - 12f, boxY + boxH * 0.5f), colors.paper.a(0.3f))

    val slide = 7f + 5f * pulse(t, 0f)
    val bay = roundRectPath(boxX + 16f, boxY + 18f, boxW - 54f, 30f, 5f)
    fill(bay, colors.charcoal.a(0.5f))
    stroke(bay, colors.inkFaint, 1.6f)
    val tray = roundRectPath(boxX + 16f, boxY + 18f + 30f + slide, boxW - 54f, 9f, 3f)
    paint(tray, colors.metal, colors.ink, 1.8f)

    // capacity readout beside the bay
    val statusWin = roundRectPath(boxX + boxW - 36f, boxY + 18f, 24f, 30f, 5f)
    fill(statusWin, colors.charcoal.a(0.55f))
    stroke(statusWin, colors.inkFaint, 1.4f)
    val capAlpha = 0.5f + 0.5f * pulse(t, 0.1f)
    deviceLabel(colors.textMeasurer, "0GB", boxX + boxW - 24f, boxY + 33f, colors.accentBlue.a(capAlpha), fontSize = 8f)

    sketchCircle(pt(boxX + boxW - 18f, boxY + boxH - 16f), 3f, colors.inkFaint, filled = true)
    sketchLine(pt(boxX + 16f, boxY + boxH - 16f), pt(boxX + boxW - 34f, boxY + boxH - 16f), colors.inkFaint, 1.4f)

    twinkle(96f, 112f, 3f, t, 0.3f, colors.inkSoft)
    twinkle(250f, 168f, 3f, t, 0.6f, colors.inkSoft)
    groundLine(262f, colors.inkFaint)
}

// ─── Nothing Connected ──────────────────────────────────────────────────────────
//   A network switch with an integrated "0/7" link-count readout and every
//   port empty — nothing plugged in to list.

internal fun DrawScope.drawNetworkNoList(t: Float, colors: SketchyStyle) {
    val boxX = 48f
    val boxY = 140f
    val boxW = 224f
    val boxH = 52f
    val cx = boxX + boxW / 2f

    contactShadow(cx, boxY + boxH + 10f, 92f, 7f, colors.shade)

    val box = roundRectPath(boxX, boxY, boxW, boxH, 10f)
    inkShadow(box, colors.outlineShadow)
    cornerShade(box, cx, boxY + boxH / 2f, boxH / 2f * 1.05f, colors.outlineShadow.a(colors.outlineShadow.alpha * 0.6f))
    paint(box, vBrush(boxY, boxY + boxH, colors.metal.lit(0.3f), colors.metalDark), colors.ink, 2.2f)
    sheen(box, pt(boxX + 14f, boxY + 6f), pt(boxX + boxW - 14f, boxY + boxH * 0.55f), colors.paper.a(0.28f))

    // integrated link-count readout
    val readout = roundRectPath(cx - 26f, boxY + 7f, 52f, 14f, 4f)
    fill(readout, colors.charcoal.a(0.55f))
    stroke(readout, colors.inkFaint, 1.2f)
    val readAlpha = 0.5f + 0.5f * pulse(t, 0f)
    deviceLabel(colors.textMeasurer, "0/7", cx, boxY + 14f, colors.accentGreen.a(readAlpha), fontSize = 9f)

    for (i in 0 until 7) {
        val px = boxX + 20f + i * 28f
        val port = roundRectPath(px, boxY + 27f, 18f, 18f, 2f)
        fill(port, colors.charcoal.a(0.6f))
        stroke(port, colors.inkFaint, 1.4f)
    }

    twinkle(70f, 116f, 3f, t, 0.4f, colors.inkSoft)
    twinkle(254f, 116f, 3f, t, 0.7f, colors.inkSoft)
    groundLine(226f, colors.inkFaint)
}

// ─── No Messages ────────────────────────────────────────────────────────────────
//   Two thin-bezel phones, screens dark; one idles on a dim "EMPTY" readout,
//   joined by an idle dashed link — no packets moving.

internal fun DrawScope.drawNetworkNoMessages(t: Float, colors: SketchyStyle) {
    val phoneW = 62f
    val phoneH = 112f
    val leftX = 68f
    val rightX = 190f
    val phoneY = 98f

    contactShadow(leftX + phoneW / 2f, phoneY + phoneH + 10f, 34f, 5f, colors.shade)
    contactShadow(rightX + phoneW / 2f, phoneY + phoneH + 10f, 34f, 5f, colors.shade)

    listOf(leftX, rightX).forEachIndexed { idx, x ->
        val body = roundRectPath(x, phoneY, phoneW, phoneH, 14f)
        inkShadow(body, colors.outlineShadow)
        cornerShade(body, x + phoneW / 2f, phoneY + phoneH / 2f, phoneH / 2f * 1.05f, colors.outlineShadow.a(colors.outlineShadow.alpha * 0.6f))
        paint(body, vBrush(phoneY, phoneY + phoneH, colors.metal.lit(0.35f), colors.metalDark), colors.ink, 2.2f)
        sheen(body, pt(x + 8f, phoneY + 8f), pt(x + phoneW - 8f, phoneY + phoneH * 0.4f), colors.paper.a(0.28f))
        val screen = roundRectPath(x + 5f, phoneY + 7f, phoneW - 10f, phoneH - 20f, 7f)
        fill(screen, colors.charcoal.a(0.4f))
        sketchCircle(pt(x + phoneW / 2f, phoneY + 14f), 1.8f, colors.accentBlue.a(0.5f), filled = true)
        sketchLine(
            pt(x + phoneW / 2f - 9f, phoneY + phoneH - 8f),
            pt(x + phoneW / 2f + 9f, phoneY + phoneH - 8f),
            colors.inkFaint, 1.8f
        )
        if (idx == 0) {
            val idleAlpha = 0.35f + 0.25f * pulse(t, 0.2f)
            deviceLabel(colors.textMeasurer, "EMPTY", x + phoneW / 2f, phoneY + phoneH / 2f, colors.inkSoft.a(idleAlpha), fontSize = 9f)
        }
    }

    val midY = phoneY + phoneH / 2f
    val link = Path().apply {
        moveTo(d(leftX + phoneW), d(midY))
        lineTo(d(rightX), d(midY))
    }
    drawPath(link, color = colors.inkFaint, style = dashed())

    twinkle(160f, 78f, 3f, t, 0.4f, colors.inkSoft)
    groundLine(236f, colors.inkFaint)
}

// ─── No Comments ────────────────────────────────────────────────────────────────
//   A rounded smart-intercom body with a circular mesh grille, an idle backlit
//   ring, and an etched "MUTED" label beneath its mic button.

internal fun DrawScope.drawNetworkNoComments(t: Float, colors: SketchyStyle) {
    val cx = 160f
    val boxY = 108f
    val boxW = 90f
    val boxH = 116f
    val boxX = cx - boxW / 2f

    contactShadow(cx, boxY + boxH + 10f, 46f, 6f, colors.shade)

    val ant = Path().apply {
        moveTo(d(cx + 22f), d(boxY))
        lineTo(d(cx + 29f), d(boxY - 24f))
    }
    limb(ant, colors.metalDark, colors.ink, 2f, thickness = 3.5f)
    sketchCircle(pt(cx + 29f, boxY - 24f), 2.4f, colors.ink, filled = true)

    val box = roundRectPath(boxX, boxY, boxW, boxH, 28f)
    inkShadow(box, colors.outlineShadow)
    cornerShade(box, cx, boxY + boxH / 2f, boxH / 2f * 1.05f, colors.outlineShadow.a(colors.outlineShadow.alpha * 0.6f))
    paint(box, vBrush(boxY, boxY + boxH, colors.metal.lit(0.3f), colors.metalDark), colors.ink, 2.4f)
    shade(box, hBrush(boxX, boxX + boxW, colors.shade.a(0f), colors.shade))
    sheen(box, pt(boxX + 10f, boxY + 10f), pt(boxX + boxW - 10f, boxY + boxH * 0.4f), colors.paper.a(0.28f))

    // circular mesh grille
    val grilleCy = boxY + boxH / 2f - 8f
    sketchCircle(pt(cx, grilleCy), 30f, colors.inkFaint, width = 1.6f)
    for (ring in 1..2) {
        val ringR = ring * 9f
        val count = ring * 6
        for (i in 0 until count) {
            val a = (i * 360f / count) * (kotlin.math.PI / 180.0).toFloat()
            val px = cx + ringR * kotlin.math.cos(a)
            val py = grilleCy + ringR * kotlin.math.sin(a)
            sketchCircle(pt(px, py), 1.3f, colors.inkFaint, filled = true)
        }
    }

    // idle backlit ring around the grille base
    val idleAlpha = 0.3f + 0.2f * pulse(t, 0f)
    sketchCircle(pt(cx, grilleCy), 32f, colors.accentBlue.a(idleAlpha), width = 1.6f)

    // etched button label
    val btnY = boxY + boxH - 26f
    sketchCircle(pt(cx, btnY), 7f, colors.inkFaint, width = 1.6f)
    deviceLabel(colors.textMeasurer, "MUTED", cx, btnY + 16f, colors.inkFaint, fontSize = 9f)

    twinkle(94f, 148f, 3f, t, 0.3f, colors.inkSoft)
    twinkle(236f, 178f, 3f, t, 0.6f, colors.inkSoft)
    groundLine(258f, colors.inkFaint)
}

// ─── No Results ─────────────────────────────────────────────────────────────────
//   A handheld network-scanner appliance: a rotating radar sweep fades to
//   nothing on its own screen, reading "0 FOUND" beneath the grid.

internal fun DrawScope.drawNetworkNoResults(t: Float, colors: SketchyStyle) {
    val cx = 160f
    val bodyW = 92f
    val bodyH = 150f
    val bodyY = 80f
    val bodyX = cx - bodyW / 2f

    contactShadow(cx, bodyY + bodyH + 12f, 50f, 7f, colors.shade)

    val ant = Path().apply {
        moveTo(d(cx), d(bodyY))
        lineTo(d(cx), d(bodyY - 26f))
    }
    limb(ant, colors.metalDark, colors.ink, 2f, thickness = 4f)
    val antAlpha = 0.4f + 0.6f * pulse(t, 0f)
    sketchCircle(pt(cx, bodyY - 26f), 3.2f, colors.accentBlue.a(antAlpha), filled = true)

    val body = roundRectPath(bodyX, bodyY, bodyW, bodyH, 18f)
    inkShadow(body, colors.outlineShadow)
    cornerShade(body, cx, bodyY + bodyH / 2f, bodyH / 2f * 1.1f, colors.outlineShadow.a(colors.outlineShadow.alpha * 0.6f))
    paint(body, vBrush(bodyY, bodyY + bodyH, colors.metal.lit(0.3f), colors.metalDark), colors.ink, 2.4f)
    sheen(body, pt(bodyX + 10f, bodyY + 96f), pt(bodyX + bodyW - 10f, bodyY + bodyH - 10f), colors.paper.a(0.26f))

    val screenX = bodyX + 9f
    val screenY = bodyY + 12f
    val screenW = bodyW - 18f
    val screenH = 84f
    val screen = roundRectPath(screenX, screenY, screenW, screenH, 7f)
    fill(screen, colors.charcoal.a(0.7f))
    stroke(screen, colors.inkFaint, 1.6f)

    val radarCx = screenX + screenW / 2f
    val radarCy = screenY + screenH / 2f - 6f
    val radarR = 28f
    listOf(0.4f, 0.7f, 1f).forEach { f ->
        sketchCircle(pt(radarCx, radarCy), radarR * f, colors.inkFaint.a(0.5f), width = 1.2f)
    }

    // a rotating sweep, fading to nothing — the signal search itself
    val sweepAngle = 360f * t
    val radarPivot = pt(radarCx, radarCy)
    withTransform({ rotate(degrees = sweepAngle, pivot = radarPivot) }) {
        val wedge = Path().apply {
            moveTo(d(radarCx), d(radarCy))
            lineTo(d(radarCx + radarR), d(radarCy))
            arcTo(
                rect = Rect(pt(radarCx - radarR, radarCy - radarR), Size(d(radarR * 2), d(radarR * 2))),
                startAngleDegrees = 0f,
                sweepAngleDegrees = 42f,
                forceMoveTo = false
            )
            close()
        }
        fill(wedge, colors.accentBlue.a(0.3f))
    }
    val ghostAlpha = (1f - loop(t, 0.1f)) * 0.5f
    sketchCircle(pt(radarCx + radarR * 0.55f, radarCy - radarR * 0.35f), 2f, colors.accentBlue.a(ghostAlpha), filled = true)

    val readAlpha = 0.5f + 0.5f * pulse(t, 0f)
    deviceLabel(colors.textMeasurer, "0 FOUND", radarCx, screenY + screenH - 12f, colors.accentBlue.a(readAlpha), fontSize = 10f)

    for (i in 0 until 2) {
        val bx = cx - 16f + i * 32f
        sketchCircle(pt(bx, bodyY + bodyH - 26f), 7f, colors.inkFaint, width = 1.6f)
    }

    twinkle(96f, 140f, 3f, t, 0.4f, colors.inkSoft)
    twinkle(236f, 160f, 3f, t, 0.7f, colors.inkSoft)
    groundLine(258f, colors.inkFaint)
}
