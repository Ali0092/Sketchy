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
 * servers, switches, phones, cables, a satellite dish — never an abstract icon/sign (no wifi-bar
 * glyphs, no padlocks, no warning triangles). Status reads through device-native cues instead:
 * blinking LEDs, an unplugged cable, a frayed cord, an empty port.
 */

// ─── No Internet ─────────────────────────────────────────────────────────────
//   A wifi router with a weak, fading signal search and a cable dangling unplugged.

internal fun DrawScope.drawNetworkNoInternet(t: Float, colors: SketchyStyle) {
    val cx = 160f
    val bodyY = 172f
    val bodyW = 100f
    val bodyH = 42f
    val bodyX = cx - bodyW / 2f

    contactShadow(cx, bodyY + bodyH + 14f, 50f, 7f, colors.shade)

    val antL = Path().apply { moveTo(d(cx - 26f), d(bodyY)); lineTo(d(cx - 34f), d(bodyY - 40f)) }
    val antR = Path().apply { moveTo(d(cx + 26f), d(bodyY)); lineTo(d(cx + 34f), d(bodyY - 40f)) }
    limb(antL, colors.metalDark, colors.ink, 2f, thickness = 4f)
    limb(antR, colors.metalDark, colors.ink, 2f, thickness = 4f)
    sketchCircle(pt(cx - 34f, bodyY - 40f), 2.6f, colors.ink, filled = true)
    sketchCircle(pt(cx + 34f, bodyY - 40f), 2.6f, colors.ink, filled = true)

    // broken, fading signal search above the right antenna
    val arcCx = cx + 34f
    val arcCy = bodyY - 46f
    listOf(12f, 22f).forEachIndexed { i, r ->
        val k = 0.15f + 0.35f * pulse(t, i * 0.3f)
        val arc = Path().apply {
            arcTo(
                rect = Rect(pt(arcCx - r, arcCy - r), Size(d(r * 2), d(r * 2))),
                startAngleDegrees = 220f,
                sweepAngleDegrees = 100f,
                forceMoveTo = true
            )
        }
        drawPath(arc, color = colors.accentRed.a(k), style = dashed())
    }

    val body = roundRectPath(bodyX, bodyY, bodyW, bodyH, 12f)
    inkShadow(body, colors.outlineShadow)
    paint(body, vBrush(bodyY, bodyY + bodyH, colors.metal.lit(0.3f), colors.metalDark), colors.ink, 2.4f)
    shade(body, hBrush(cx, bodyX + bodyW, colors.shade.a(0f), colors.shade))

    // status LEDs, all dim except one slow red blink
    for (i in 0 until 4) {
        val lx = bodyX + 20f + i * 20f
        val on = i == 3
        val alpha = if (on) 0.35f + 0.65f * pulse(t, 0f) else 1f
        val color = if (on) colors.accentRed.a(alpha) else colors.inkFaint
        sketchCircle(pt(lx, bodyY + bodyH - 12f), 2.6f, color, filled = true)
    }

    // a dangling, unplugged ethernet cable
    val sway = 5f * wave(t, 0.4f)
    val cable = Path().apply {
        moveTo(d(bodyX + bodyW - 16f), d(bodyY + bodyH))
        cubicTo(
            d(bodyX + bodyW + 4f), d(bodyY + bodyH + 30f),
            d(bodyX + bodyW - 6f + sway), d(bodyY + bodyH + 46f),
            d(bodyX + bodyW - 2f + sway), d(bodyY + bodyH + 60f)
        )
    }
    limb(cable, colors.charcoal, colors.ink, 2.2f, thickness = 5f)
    val plug = roundRectPath(bodyX + bodyW - 10f + sway, bodyY + bodyH + 58f, 16f, 12f, 3f)
    paint(plug, colors.metalDark, colors.ink, 2f)

    twinkle(96f, 120f, 3f, t, 0.2f, colors.inkSoft)
    twinkle(226f, 160f, 3f, t, 0.6f, colors.inkSoft)
    groundLine(258f, colors.inkFaint)
}

// ─── Page Not Found (404) ─────────────────────────────────────────────────────
//   A cable hanging from above, swinging as it hunts for a wall jack it never reaches.

internal fun DrawScope.drawNetworkPageNotFound(t: Float, colors: SketchyStyle) {
    val cx = 160f
    val plateY = 150f
    val plateW = 64f
    val plateH = 54f
    val plateX = cx - plateW / 2f
    val jackCy = plateY + plateH / 2f

    val plate = roundRectPath(plateX, plateY, plateW, plateH, 8f)
    inkShadow(plate, colors.outlineShadow)
    paint(plate, vBrush(plateY, plateY + plateH, colors.paper, colors.metal.lit(0.2f)), colors.ink, 2.2f)
    val jack = roundRectPath(cx - 10f, jackCy - 8f, 20f, 16f, 3f)
    paint(jack, colors.charcoal, colors.ink, 1.8f)

    val swing = 30f * wave(t, 0f)
    val anchorX = cx + 46f + swing
    val anchorY = jackCy + 10f
    val cable = Path().apply {
        moveTo(d(cx + 40f), d(40f))
        cubicTo(
            d(cx + 40f + swing * 0.5f), d(110f),
            d(anchorX - swing * 0.3f), d(170f),
            d(anchorX), d(anchorY)
        )
    }
    limb(cable, colors.charcoal, colors.ink, 2.4f, thickness = 6f)
    val plug = roundRectPath(anchorX - 9f, anchorY - 2f, 18f, 14f, 3f)
    paint(plug, colors.metalDark, colors.ink, 2.2f)
    sketchLine(pt(anchorX - 5f, anchorY + 12f), pt(anchorX - 5f, anchorY + 18f), colors.ink, 1.8f)
    sketchLine(pt(anchorX + 5f, anchorY + 12f), pt(anchorX + 5f, anchorY + 18f), colors.ink, 1.8f)

    twinkle(90f, 130f, 3f, t, 0.3f, colors.inkSoft)
    twinkle(240f, 100f, 3f, t, 0.7f, colors.inkSoft)
    groundLine(248f, colors.inkFaint)
}

// ─── Bad Gateway (502) ─────────────────────────────────────────────────────────
//   Two gateway boxes whose link snaps in the middle, sparking at the break.

internal fun DrawScope.drawNetworkBadGateway(t: Float, colors: SketchyStyle) {
    val leftX = 66f
    val rightX = 190f
    val boxY = 130f
    val boxW = 66f
    val boxH = 54f
    val midX = (leftX + boxW + rightX) / 2f
    val midY = boxY + boxH / 2f

    contactShadow(leftX + boxW / 2f, boxY + boxH + 14f, 34f, 6f, colors.shade)
    contactShadow(rightX + boxW / 2f, boxY + boxH + 14f, 34f, 6f, colors.shade)

    val boxA = roundRectPath(leftX, boxY, boxW, boxH, 10f)
    inkShadow(boxA, colors.outlineShadow)
    paint(boxA, vBrush(boxY, boxY + boxH, colors.metal.lit(0.3f), colors.metalDark), colors.ink, 2.4f)
    sketchCircle(pt(leftX + boxW - 14f, boxY + boxH - 14f), 3f, colors.accentGreen, filled = true)

    val boxB = roundRectPath(rightX, boxY, boxW, boxH, 10f)
    inkShadow(boxB, colors.outlineShadow)
    paint(boxB, vBrush(boxY, boxY + boxH, colors.metal.lit(0.3f), colors.metalDark), colors.ink, 2.4f)
    val flicker = 0.35f + 0.65f * pulse(t, 0.4f)
    sketchCircle(pt(rightX + 14f, boxY + boxH - 14f), 3f, colors.accentRed.a(flicker), filled = true)

    val gap = 9f + 3f * pulse(t, 0f)
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
    sketchLine(pt(midX - 4f, midY - 8f), pt(midX + 2f, midY - 1f), colors.accentRed.a(sparkAlpha), 2f)
    sketchLine(pt(midX + 2f, midY - 1f), pt(midX - 3f, midY + 6f), colors.accentRed.a(sparkAlpha), 2f)
    sketchLine(pt(midX - 3f, midY + 6f), pt(midX + 4f, midY + 10f), colors.accentRed.a(sparkAlpha), 2f)

    twinkle(90f, 100f, 3f, t, 0.2f, colors.inkSoft)
    twinkle(240f, 190f, 3f, t, 0.6f, colors.inkSoft)
    groundLine(214f, colors.inkFaint)
}

// ─── Server Error (400+/500+) ──────────────────────────────────────────────────
//   A server tower overheating: vents shimmering, one drive LED in distress, a
//   frayed power cord sparking at its base.

internal fun DrawScope.drawNetworkServerError(t: Float, colors: SketchyStyle) {
    val towerX = 128f
    val towerY = 86f
    val towerW = 64f
    val towerH = 150f

    contactShadow(towerX + towerW / 2f, towerY + towerH + 10f, 40f, 7f, colors.shade)

    val tower = roundRectPath(towerX, towerY, towerW, towerH, 10f)
    inkShadow(tower, colors.outlineShadow)
    paint(tower, vBrush(towerY, towerY + towerH, colors.metal.lit(0.3f), colors.metalDark), colors.ink, 2.4f)
    shade(tower, hBrush(towerX, towerX + towerW, colors.shade.a(0f), colors.shade))

    for (i in 0 until 4) {
        val y = towerY + 14f + i * 6f
        sketchLine(pt(towerX + 14f, y), pt(towerX + towerW - 14f, y), colors.inkFaint, 1.4f)
    }

    for (row in 0..2) {
        val y = towerY + 56f + row * 26f
        sketchLine(pt(towerX + 10f, y), pt(towerX + towerW - 22f, y), colors.inkFaint, 1.4f)
        val flagged = row == 1
        val alpha = if (flagged) 0.4f + 0.6f * pulse(t, 0.2f) else 1f
        val ledColor = if (flagged) colors.accentRed.a(alpha) else colors.accentGreen
        sketchCircle(pt(towerX + towerW - 12f, y), 2.6f, ledColor, filled = true)
    }

    val cord = Path().apply {
        moveTo(d(towerX), d(towerY + towerH - 16f))
        cubicTo(
            d(towerX - 22f), d(towerY + towerH - 6f),
            d(towerX - 26f), d(towerY + towerH + 14f),
            d(towerX - 30f), d(towerY + towerH + 26f)
        )
    }
    limb(cord, colors.charcoal, colors.ink, 2.2f, thickness = 5f)
    val sparkAlpha = 0.35f + 0.65f * pulse(t, 0f)
    val sx = towerX - 30f
    val sy = towerY + towerH + 26f
    sketchLine(pt(sx - 5f, sy - 6f), pt(sx + 2f, sy), colors.accentRed.a(sparkAlpha), 2f)
    sketchLine(pt(sx + 2f, sy), pt(sx - 4f, sy + 7f), colors.accentRed.a(sparkAlpha), 2f)

    for (i in 0..1) {
        val sway = 6f * wave(t, i * 0.3f)
        val rise = loop(t, i * 0.3f)
        val topY = towerY - 6f - rise * 18f
        sketchLine(
            pt(towerX + 20f + i * 24f + sway, topY + 14f),
            pt(towerX + 20f + i * 24f - sway, topY),
            colors.inkFaint.a(0.5f * (1f - rise)),
            1.4f
        )
    }

    twinkle(96f, 110f, 3f, t, 0.4f, colors.inkSoft)
    twinkle(238f, 150f, 3f, t, 0.7f, colors.inkSoft)
    groundLine(258f, colors.inkFaint)
}

// ─── Unsecured Wi-Fi ────────────────────────────────────────────────────────────
//   A router and a laptop linked by a cable that's stripped bare in the middle,
//   exposed copper strands showing instead of an insulated line.

internal fun DrawScope.drawNetworkUnsecureWifi(t: Float, colors: SketchyStyle) {
    val routerX = 70f
    val routerY = 176f
    val routerW = 56f
    val routerH = 34f
    val lidX = 190f
    val laptopBaseY = 214f

    contactShadow(routerX + routerW / 2f, routerY + routerH + 10f, 28f, 5f, colors.shade)
    contactShadow(lidX + 30f, laptopBaseY + 8f, 40f, 6f, colors.shade)

    val ant = Path().apply {
        moveTo(d(routerX + routerW - 10f), d(routerY))
        lineTo(d(routerX + routerW - 4f), d(routerY - 30f))
    }
    limb(ant, colors.metalDark, colors.ink, 2f, thickness = 4f)
    sketchCircle(pt(routerX + routerW - 4f, routerY - 30f), 2.4f, colors.ink, filled = true)
    val router = roundRectPath(routerX, routerY, routerW, routerH, 9f)
    inkShadow(router, colors.outlineShadow)
    paint(router, vBrush(routerY, routerY + routerH, colors.metal.lit(0.3f), colors.metalDark), colors.ink, 2.2f)
    sketchCircle(pt(routerX + 12f, routerY + routerH - 10f), 2.4f, colors.accentGreen, filled = true)

    listOf(10f, 18f).forEachIndexed { i, r ->
        val k = 0.4f + 0.4f * pulse(t, i * 0.25f)
        val arc = Path().apply {
            arcTo(
                rect = Rect(
                    pt(routerX + routerW - 4f - r, routerY - 30f - r),
                    Size(d(r * 2), d(r * 2))
                ),
                startAngleDegrees = 300f,
                sweepAngleDegrees = 100f,
                forceMoveTo = true
            )
        }
        drawPath(arc, color = colors.accent.a(k), style = thin(1.6f))
    }

    val lidY = laptopBaseY - 44f
    val lid = roundRectPath(lidX, lidY, 60f, 44f, 6f)
    paint(lid, vBrush(lidY, lidY + 44f, colors.sky.a(0.5f), colors.metal), colors.ink, 2.2f)
    val base = roundRectPath(lidX - 6f, laptopBaseY, 72f, 8f, 3f)
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
        val off = d((i - 1) * 2.2f)
        sketchLine(Offset(gapStart.x, gapStart.y + off), Offset(gapEnd.x, gapEnd.y + off), c, 1.2f)
    }

    twinkle(50f, 130f, 3f, t, 0.3f, colors.inkSoft)
    twinkle(250f, 230f, 3f, t, 0.6f, colors.inkSoft)
    groundLine(238f, colors.inkFaint)
}

// ─── No Data ────────────────────────────────────────────────────────────────────
//   A storage box with its drive bay slid open — the tray is empty.

internal fun DrawScope.drawNetworkNoData(t: Float, colors: SketchyStyle) {
    val boxX = 108f
    val boxY = 120f
    val boxW = 104f
    val boxH = 78f

    contactShadow(boxX + boxW / 2f, boxY + boxH + 12f, 48f, 7f, colors.shade)

    val box = roundRectPath(boxX, boxY, boxW, boxH, 10f)
    inkShadow(box, colors.outlineShadow)
    paint(box, vBrush(boxY, boxY + boxH, colors.metal.lit(0.3f), colors.metalDark), colors.ink, 2.4f)
    shade(box, hBrush(boxX, boxX + boxW, colors.shade.a(0f), colors.shade))

    val slide = 6f + 4f * pulse(t, 0f)
    val bay = roundRectPath(boxX + 14f, boxY + 16f, boxW - 28f, 24f, 4f)
    fill(bay, colors.charcoal.a(0.5f))
    stroke(bay, colors.inkFaint, 1.6f)
    val tray = roundRectPath(boxX + 14f, boxY + 16f + 24f + slide, boxW - 28f, 8f, 3f)
    paint(tray, colors.metal, colors.ink, 1.8f)

    sketchCircle(pt(boxX + boxW - 16f, boxY + boxH - 14f), 2.6f, colors.inkFaint, filled = true)
    sketchLine(pt(boxX + 14f, boxY + boxH - 14f), pt(boxX + boxW - 30f, boxY + boxH - 14f), colors.inkFaint, 1.4f)

    twinkle(90f, 108f, 3f, t, 0.3f, colors.inkSoft)
    twinkle(232f, 150f, 3f, t, 0.6f, colors.inkSoft)
    groundLine(240f, colors.inkFaint)
}

// ─── Nothing Connected ──────────────────────────────────────────────────────────
//   A network switch, every port empty — nothing plugged in to list.

internal fun DrawScope.drawNetworkNoList(t: Float, colors: SketchyStyle) {
    val boxX = 66f
    val boxY = 150f
    val boxW = 188f
    val boxH = 40f

    contactShadow(boxX + boxW / 2f, boxY + boxH + 10f, 76f, 7f, colors.shade)

    val box = roundRectPath(boxX, boxY, boxW, boxH, 8f)
    inkShadow(box, colors.outlineShadow)
    paint(box, vBrush(boxY, boxY + boxH, colors.metal.lit(0.3f), colors.metalDark), colors.ink, 2.2f)

    for (i in 0 until 7) {
        val px = boxX + 16f + i * 24f
        val port = roundRectPath(px, boxY + 12f, 14f, 16f, 2f)
        fill(port, colors.charcoal.a(0.6f))
        stroke(port, colors.inkFaint, 1.4f)
    }

    val alpha = 0.5f + 0.5f * pulse(t, 0f)
    sketchCircle(pt(boxX + boxW - 10f, boxY + boxH / 2f), 2.2f, colors.inkFaint.a(alpha * 0.4f), filled = true)

    twinkle(88f, 120f, 3f, t, 0.4f, colors.inkSoft)
    twinkle(232f, 120f, 3f, t, 0.7f, colors.inkSoft)
    groundLine(220f, colors.inkFaint)
}

// ─── No Messages ────────────────────────────────────────────────────────────────
//   Two phones, screens dark, joined by an idle dashed link — no packets moving.

internal fun DrawScope.drawNetworkNoMessages(t: Float, colors: SketchyStyle) {
    val phoneW = 46f
    val phoneH = 84f
    val leftX = 84f
    val rightX = 190f
    val phoneY = 118f

    contactShadow(leftX + phoneW / 2f, phoneY + phoneH + 10f, 26f, 5f, colors.shade)
    contactShadow(rightX + phoneW / 2f, phoneY + phoneH + 10f, 26f, 5f, colors.shade)

    listOf(leftX, rightX).forEach { x ->
        val body = roundRectPath(x, phoneY, phoneW, phoneH, 10f)
        inkShadow(body, colors.outlineShadow)
        paint(body, vBrush(phoneY, phoneY + phoneH, colors.metal.lit(0.35f), colors.metalDark), colors.ink, 2.2f)
        val screen = roundRectPath(x + 5f, phoneY + 8f, phoneW - 10f, phoneH - 22f, 4f)
        fill(screen, colors.charcoal.a(0.35f))
        sketchLine(
            pt(x + phoneW / 2f - 6f, phoneY + phoneH - 8f),
            pt(x + phoneW / 2f + 6f, phoneY + phoneH - 8f),
            colors.inkFaint, 1.6f
        )
    }

    val midY = phoneY + phoneH / 2f
    val link = Path().apply {
        moveTo(d(leftX + phoneW), d(midY))
        lineTo(d(rightX), d(midY))
    }
    drawPath(link, color = colors.inkFaint, style = dashed())

    twinkle(160f, 96f, 3f, t, 0.4f, colors.inkSoft)
    groundLine(224f, colors.inkFaint)
}

// ─── No Comments ────────────────────────────────────────────────────────────────
//   A network intercom speaker, grille quiet, status LED barely glowing.

internal fun DrawScope.drawNetworkNoComments(t: Float, colors: SketchyStyle) {
    val cx = 160f
    val boxY = 130f
    val boxW = 70f
    val boxH = 84f
    val boxX = cx - boxW / 2f

    contactShadow(cx, boxY + boxH + 10f, 36f, 6f, colors.shade)

    val ant = Path().apply {
        moveTo(d(cx + 18f), d(boxY))
        lineTo(d(cx + 24f), d(boxY - 26f))
    }
    limb(ant, colors.metalDark, colors.ink, 2f, thickness = 3.5f)
    sketchCircle(pt(cx + 24f, boxY - 26f), 2.2f, colors.ink, filled = true)

    val box = roundRectPath(boxX, boxY, boxW, boxH, 12f)
    inkShadow(box, colors.outlineShadow)
    paint(box, vBrush(boxY, boxY + boxH, colors.metal.lit(0.3f), colors.metalDark), colors.ink, 2.4f)
    shade(box, hBrush(boxX, boxX + boxW, colors.shade.a(0f), colors.shade))

    for (row in 0..2) {
        for (col in 0..2) {
            sketchCircle(pt(boxX + 20f + col * 15f, boxY + 24f + row * 15f), 2f, colors.inkFaint, filled = true)
        }
    }

    val alpha = 0.4f + 0.3f * pulse(t, 0f)
    sketchCircle(pt(cx, boxY + boxH - 12f), 2.6f, colors.inkFaint.a(alpha), filled = true)

    twinkle(96f, 150f, 3f, t, 0.3f, colors.inkSoft)
    twinkle(224f, 180f, 3f, t, 0.6f, colors.inkSoft)
    groundLine(236f, colors.inkFaint)
}

// ─── No Results ─────────────────────────────────────────────────────────────────
//   A satellite dish sweeping side to side, searching and coming up empty.

internal fun DrawScope.drawNetworkNoResults(t: Float, colors: SketchyStyle) {
    val cx = 160f
    val standTopY = 210f

    contactShadow(cx, 240f, 34f, 6f, colors.shade)

    limb(
        Path().apply { moveTo(d(cx), d(standTopY)); lineTo(d(cx), d(236f)) },
        colors.metalDark, colors.ink, 2.4f, thickness = 6f
    )

    val sweep = 26f * wave(t, 0f)
    val pivot = pt(cx, standTopY)
    withTransform({ rotate(degrees = sweep, pivot = pivot) }) {
        val dish = Path().apply {
            moveTo(d(cx - 42f), d(standTopY - 6f))
            quadraticTo(d(cx), d(standTopY - 46f), d(cx + 42f), d(standTopY - 6f))
            quadraticTo(d(cx), d(standTopY + 6f), d(cx - 42f), d(standTopY - 6f))
            close()
        }
        inkShadow(dish, colors.outlineShadow)
        paint(dish, vBrush(standTopY - 46f, standTopY, colors.metal.lit(0.35f), colors.metalDark), colors.ink, 2.4f)
        limb(
            Path().apply { moveTo(d(cx), d(standTopY - 20f)); lineTo(d(cx), d(standTopY - 42f)) },
            colors.metalDark, colors.ink, 2f, thickness = 3.5f
        )
        sketchCircle(pt(cx, standTopY - 42f), 2.4f, colors.ink, filled = true)
    }

    twinkle(96f, 130f, 3f, t, 0.4f, colors.inkSoft)
    twinkle(228f, 150f, 3f, t, 0.7f, colors.inkSoft)
    groundLine(252f, colors.inkFaint)
}
