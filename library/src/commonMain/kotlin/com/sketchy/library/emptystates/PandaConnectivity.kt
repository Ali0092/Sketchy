package com.sketchy.library.emptystates

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import com.sketchy.library.SketchyStyle
import com.sketchy.library.characters.*
import com.sketchy.library.utils.*

// ─── Panda No Internet ────────────────────────────────────────────────────────
//   Panda pokes a drooping, dead router antenna; its wifi arcs flicker in gaps
//   instead of glowing, and its light blinks red.

internal fun DrawScope.drawPandaNoInternet(t: Float, colors: SketchyStyle) {
    val cx = 122f
    val headCy = 128f
    val bodyTop = 156f
    val bodyBottom = 218f
    val halfWidth = 32f

    val routerX = 214f
    val routerY = 150f
    val routerW = 62f
    val routerH = 26f

    contactShadow(cx, 262f, 46f, 7f, colors.shade)
    contactShadow(routerX + routerW / 2f, 250f, 34f, 5f, colors.shade)

    // dead antenna, drooping instead of standing proud
    val antennaBaseX = routerX + routerW * 0.72f
    val droop = 6f + 4f * wave(t, 0.1f)
    val antennaTipX = antennaBaseX + 18f + droop
    val antennaTipY = routerY - 34f + droop * 0.4f
    val antenna = Path().apply {
        moveTo(d(antennaBaseX), d(routerY))
        quadraticTo(d(antennaBaseX + 10f), d(routerY - 26f), d(antennaTipX), d(antennaTipY))
    }
    stroke(antenna, colors.metalDark, 2.2f)
    sketchCircle(pt(antennaTipX, antennaTipY), 3f, colors.metalDark, filled = true)

    // broken wifi arcs — gapped and flickering rather than glowing steady
    val arcCy = antennaTipY - 4f
    listOf(14f, 24f).forEachIndexed { i, r ->
        val k = (1f + wave(t, 0.3f + i * 0.25f)) / 2f
        if (k > 0.35f) {
            val arc = Path().apply {
                arcTo(
                    rect = Rect(pt(antennaTipX - r, arcCy - r), Size(d(r * 2), d(r * 2))),
                    startAngleDegrees = 235f,
                    sweepAngleDegrees = 50f,
                    forceMoveTo = true
                )
            }
            drawPath(arc, color = colors.inkSoft.a(0.25f + 0.5f * k), style = bold(2f))
        }
    }

    // router body
    val router = roundRectPath(routerX, routerY, routerW, routerH, 8f)
    paint(router, vBrush(routerY, routerY + routerH, colors.metal.lit(0.3f), colors.metalDark), colors.ink, 2f)
    val ledFlicker = 0.25f + 0.5f * (1f + wave(t, 0f)) / 2f
    sketchCircle(pt(routerX + routerW - 12f, routerY + routerH / 2f), 3f, colors.accentRed.copy(alpha = ledFlicker), filled = true)
    sketchLine(pt(routerX + 10f, routerY + routerH / 2f), pt(routerX + routerW - 24f, routerY + routerH / 2f), colors.inkFaint, 1.4f)

    // panda, worried, reaching out to poke the dead antenna
    pandaLeg(cx - 16f, bodyBottom - 4f, cx - 24f, 262f, colors, thickness = 13f)
    pandaLeg(cx + 16f, bodyBottom - 4f, cx + 26f, 262f, colors, thickness = 13f)
    pandaArm(
        cx - halfWidth + 2f, bodyTop + 22f,
        cx - 40f, bodyTop + 44f,
        colors, controlX = cx - 40f, controlY = bodyTop + 20f
    )
    val pokeReach = 4f * wave(t, 0.2f)
    pandaArm(
        cx + halfWidth - 4f, bodyTop + 18f,
        antennaBaseX - 6f + pokeReach, routerY - 10f,
        colors, controlX = cx + 46f, controlY = bodyTop - 4f, thickness = 10f
    )
    pandaBody(cx, bodyTop, bodyBottom, halfWidth, colors)
    pandaHead(
        cx, headCy, 30f, colors,
        tilt = 8f + 3f * wave(t, 0.15f),
        expression = PandaExpression.Worried,
        blink = pandaAutoBlink(t, 0.1f)
    )

    twinkle(70f, 100f, 3f, t, 0.15f, colors.inkSoft)
    twinkle(250f, 110f, 3f, t, 0.6f, colors.inkSoft)
    groundLine(272f, colors.inkFaint)
}

// ─── Panda Server Error ───────────────────────────────────────────────────────
//   Panda flinches back, paws thrown up, as a stack of servers cracks and its
//   warning light flickers.

internal fun DrawScope.drawPandaServerError(t: Float, colors: SketchyStyle) {
    val cx = 110f
    val headCy = 126f
    val bodyTop = 154f
    val bodyBottom = 216f
    val halfWidth = 32f

    val rackX = 196f
    val rackW = 74f

    contactShadow(cx, 260f, 46f, 7f, colors.shade)
    contactShadow(rackX + rackW / 2f, 250f, 40f, 6f, colors.shade)

    for (row in 0..2) {
        val y = 152f + row * 32f
        val box = roundRectPath(rackX, y, rackW, 26f, 5f)
        paint(box, vBrush(y, y + 26f, colors.metal.lit(0.3f), colors.metalDark), colors.ink, 2f)
        val ledColor = if (row == 1) {
            colors.accentRed.copy(alpha = 0.35f + 0.65f * (1f + wave(t, 0.3f)) / 2f)
        } else {
            colors.accentGreen
        }
        sketchCircle(pt(rackX + rackW - 12f, y + 13f), 2.6f, ledColor, filled = true)
        sketchLine(pt(rackX + 10f, y + 13f), pt(rackX + rackW - 26f, y + 13f), colors.inkFaint, 1.4f)
    }

    val crack = Path().apply {
        moveTo(d(rackX + 30f), d(150f))
        lineTo(d(rackX + 44f), d(180f))
        lineTo(d(rackX + 26f), d(198f))
        lineTo(d(rackX + 46f), d(238f))
    }
    val flicker = 0.5f + 0.5f * (1f + wave(t, 0f)) / 2f
    drawPath(crack, color = colors.accentRed.copy(alpha = flicker), style = bold(2.2f))

    // warning triangle above the rack
    val warnCx = rackX + rackW / 2f
    val warn = Path().apply {
        moveTo(d(warnCx), d(94f))
        lineTo(d(warnCx + 16f), d(122f))
        lineTo(d(warnCx - 16f), d(122f))
        close()
    }
    paint(warn, vBrush(94f, 122f, colors.sun, colors.sunDeep), colors.ink, 2f)
    sketchLine(pt(warnCx, 102f), pt(warnCx, 112f), colors.accentRed, 2f)
    sketchCircle(pt(warnCx, 117f), 1.5f, colors.accentRed, filled = true)

    // panda, flinching, paws thrown up in surprise
    val flinch = 3f * wave(t, 0.2f)
    pandaLeg(cx - 16f, bodyBottom - 4f, cx - 22f, 258f, colors, thickness = 13f)
    pandaLeg(cx + 16f, bodyBottom - 4f, cx + 24f, 258f, colors, thickness = 13f)
    pandaArm(
        cx - halfWidth + 4f, bodyTop + 16f,
        cx - 44f, bodyTop - 20f + flinch,
        colors, controlX = cx - 50f, controlY = bodyTop
    )
    pandaArm(
        cx + halfWidth - 4f, bodyTop + 16f,
        cx + 12f, bodyTop - 26f - flinch,
        colors, controlX = cx + 26f, controlY = bodyTop - 6f
    )
    pandaBody(cx, bodyTop, bodyBottom, halfWidth, colors)
    pandaHead(
        cx, headCy, 30f, colors,
        tilt = -6f,
        expression = PandaExpression.Surprised,
        blink = 0f
    )

    twinkle(76f, 96f, 3f, t, 0.25f, colors.inkSoft)
    groundLine(268f, colors.inkFaint)
}

// ─── Panda Sync Failed ────────────────────────────────────────────────────────
//   Panda heaves on a crank wheel that only rocks a few stubborn degrees each
//   way instead of turning over, an "x" flickering at its hub.

internal fun DrawScope.drawPandaSyncFailed(t: Float, colors: SketchyStyle) {
    val wheelCx = 186f
    val wheelCy = 156f
    val wheelR = 42f
    val struggle = 14f * wave(t, 0f)

    contactShadow(wheelCx, 226f, 34f, 5f, colors.shade)

    val handleLen = wheelR + 18f
    val knobAngleRad = (180f + struggle) * kotlin.math.PI.toFloat() / 180f
    val knobX = wheelCx + handleLen * kotlin.math.cos(knobAngleRad)
    val knobY = wheelCy + handleLen * kotlin.math.sin(knobAngleRad)

    val wheelPivot = pt(wheelCx, wheelCy)
    withTransform({ rotate(degrees = struggle, pivot = wheelPivot) }) {
        paintCircle(pt(wheelCx, wheelCy), wheelR, colors.metal.lit(0.15f), colors.ink, 2.2f)
        sketchCircle(pt(wheelCx, wheelCy), 12f, colors.inkSoft, width = 1.6f)
        for (i in 0 until 6) {
            val a = i * (360.0 / 6.0) * kotlin.math.PI / 180.0
            val fx = wheelCx + (wheelR - 4f) * kotlin.math.cos(a).toFloat()
            val fy = wheelCy + (wheelR - 4f) * kotlin.math.sin(a).toFloat()
            val tx = wheelCx + (wheelR + 10f) * kotlin.math.cos(a).toFloat()
            val ty = wheelCy + (wheelR + 10f) * kotlin.math.sin(a).toFloat()
            sketchLine(pt(fx, fy), pt(tx, ty), colors.ink, 3.2f)
        }
        // handle, pointing toward the panda so it reads as the crank arm
        sketchLine(pt(wheelCx, wheelCy), pt(wheelCx - handleLen, wheelCy), colors.metalDark, 3f)
        paintCircle(pt(wheelCx - handleLen, wheelCy), 6f, colors.metal, colors.ink, 2f)
    }

    val xAlpha = 0.5f + 0.5f * (1f + wave(t, 0.25f)) / 2f
    sketchLine(pt(wheelCx - 9f, wheelCy - 9f), pt(wheelCx + 9f, wheelCy + 9f), colors.accentRed.copy(alpha = xAlpha), 2.4f)
    sketchLine(pt(wheelCx - 9f, wheelCy + 9f), pt(wheelCx + 9f, wheelCy - 9f), colors.accentRed.copy(alpha = xAlpha), 2.4f)

    // panda heaving on the crank handle, straining rather than spinning free
    val cx = 106f
    val headCy = 140f
    val bodyTop = 168f
    val bodyBottom = 226f
    val halfWidth = 32f

    contactShadow(cx, 262f, 44f, 6f, colors.shade)
    val lean = 3f * wave(t, 0f)
    pandaLeg(cx - 14f, bodyBottom - 4f, cx - 24f - lean, 258f, colors, thickness = 13f)
    pandaLeg(cx + 16f, bodyBottom - 4f, cx + 22f, 258f, colors, thickness = 13f)
    pandaArm(
        cx + halfWidth - 6f, bodyTop + 20f,
        knobX, knobY,
        colors, controlX = cx + 50f, controlY = bodyTop + 6f, thickness = 11f
    )
    pandaArm(
        cx - halfWidth + 6f, bodyTop + 26f,
        cx - 46f, bodyTop + 40f,
        colors, controlX = cx - 48f, controlY = bodyTop + 14f
    )
    pandaBody(cx, bodyTop, bodyBottom, halfWidth, colors)
    pandaHead(
        cx, headCy, 30f, colors,
        tilt = 6f + lean,
        expression = PandaExpression.Worried,
        blink = pandaAutoBlink(t, 0.4f)
    )

    twinkle(250f, 100f, 3f, t, 0.4f, colors.inkSoft)
    groundLine(272f, colors.inkFaint)
}

// ─── Panda Under Maintenance ──────────────────────────────────────────────────
//   A gear turns freely and steadily while panda, hard hat on, taps a wrench
//   against nothing in particular.

internal fun DrawScope.drawPandaUnderMaintenance(t: Float, colors: SketchyStyle) {
    val gearCx = 214f
    val gearCy = 150f
    val gearR = 42f

    val cx = 108f
    val headCy = 132f
    val bodyTop = 160f
    val bodyBottom = 222f
    val halfWidth = 32f

    contactShadow(cx, 260f, 44f, 6f, colors.shade)
    contactShadow(gearCx, 226f, 34f, 5f, colors.shade)

    val gearPivot = pt(gearCx, gearCy)
    withTransform({ rotate(degrees = 360f * t, pivot = gearPivot) }) {
        paintCircle(pt(gearCx, gearCy), gearR, colors.metal.lit(0.2f), colors.ink, 2.2f)
        sketchCircle(pt(gearCx, gearCy), 13f, colors.inkSoft, width = 1.6f)
        for (i in 0 until 8) {
            val a = i * (360.0 / 8.0) * kotlin.math.PI / 180.0
            val fx = gearCx + (gearR - 2f) * kotlin.math.cos(a).toFloat()
            val fy = gearCy + (gearR - 2f) * kotlin.math.sin(a).toFloat()
            val tx = gearCx + (gearR + 12f) * kotlin.math.cos(a).toFloat()
            val ty = gearCy + (gearR + 12f) * kotlin.math.sin(a).toFloat()
            sketchLine(pt(fx, fy), pt(tx, ty), colors.ink, 3.4f)
        }
    }

    pandaLeg(cx - 14f, bodyBottom - 4f, cx - 22f, 258f, colors, thickness = 13f)
    pandaLeg(cx + 14f, bodyBottom - 4f, cx + 20f, 258f, colors, thickness = 13f)
    pandaArm(
        cx - halfWidth + 4f, bodyTop + 20f,
        cx - 42f, bodyTop + 44f,
        colors, controlX = cx - 46f, controlY = bodyTop + 16f
    )

    // wrench held in the raised paw, tapped now and then — the gear does the turning
    val tap = 3f * wave(t, 0f)
    val wrenchPivot = pt(cx + 44f, bodyTop + 6f)
    withTransform({ rotate(degrees = -34f + tap, pivot = wrenchPivot) }) {
        val wrench = Path().apply {
            moveTo(d(cx + 20f), d(bodyTop + 44f))
            lineTo(d(cx + 58f), d(bodyTop + 6f))
            lineTo(d(cx + 64f), d(bodyTop + 12f))
            lineTo(d(cx + 26f), d(bodyTop + 50f))
            close()
        }
        paint(
            wrench,
            dBrush(cx + 20f, bodyTop + 44f, cx + 64f, bodyTop + 12f, colors.metal.lit(0.35f), colors.metalDark),
            colors.accentBlue, 2f
        )
        paintCircle(pt(cx + 14f, bodyTop + 50f), 9f, colors.metal, colors.accentBlue, 2f)
    }
    pandaArm(
        cx + halfWidth - 6f, bodyTop + 12f,
        cx + 38f, bodyTop - 4f,
        colors, controlX = cx + 42f, controlY = bodyTop + 8f, thickness = 10f
    )

    pandaBody(cx, bodyTop, bodyBottom, halfWidth, colors)
    pandaHead(
        cx, headCy, 30f, colors,
        tilt = -4f,
        expression = PandaExpression.Content,
        blink = pandaAutoBlink(t, 0.6f)
    )

    // hard hat, ears peeking out beneath the brim
    val hatBrimY = headCy - 32f
    val hatTopY = headCy - 64f
    val hat = Path().apply {
        moveTo(d(cx - 26f), d(hatBrimY))
        quadraticTo(d(cx), d(hatTopY), d(cx + 26f), d(hatBrimY))
        close()
    }
    paint(hat, vBrush(hatTopY, hatBrimY, colors.sun, colors.sunDeep), colors.ink, 2.2f)
    sketchLine(pt(cx - 28f, hatBrimY), pt(cx + 28f, hatBrimY), colors.line(colors.sunDeep), 2.2f)

    twinkle(258f, 210f, 3f, t, 0.4f, colors.inkSoft)
    groundLine(270f, colors.inkFaint)
}

// ─── Panda Location Not Found ─────────────────────────────────────────────────
//   Panda holds a map up, sweeping a magnifying glass over roads that lead
//   nowhere, while a pin bobs nearby with no destination in sight.

internal fun DrawScope.drawPandaLocationNotFound(t: Float, colors: SketchyStyle) {
    val cx = 140f
    val headCy = 122f
    val bodyTop = 150f
    val bodyBottom = 214f
    val halfWidth = 34f

    contactShadow(cx, 256f, 46f, 7f, colors.shade)

    pandaLeg(cx - 16f, bodyBottom - 4f, cx - 22f, 250f, colors, thickness = 13f)
    pandaLeg(cx + 16f, bodyBottom - 4f, cx + 22f, 250f, colors, thickness = 13f)
    pandaBody(cx, bodyTop, bodyBottom, halfWidth, colors)

    // map, held up in front, a road leading nowhere in particular
    val mapY = bodyTop + 14f
    val map = Path().apply {
        moveTo(d(cx - 46f), d(mapY))
        lineTo(d(cx + 46f), d(mapY - 4f))
        lineTo(d(cx + 42f), d(mapY + 52f))
        lineTo(d(cx - 44f), d(mapY + 56f))
        close()
    }
    paint(map, vBrush(mapY, mapY + 56f, colors.paper, colors.metal.lit(0.1f)), colors.ink, 2.2f)
    val road = Path().apply {
        moveTo(d(cx - 28f), d(mapY + 38f))
        quadraticTo(d(cx - 4f), d(mapY + 10f), d(cx + 20f), d(mapY + 28f))
        quadraticTo(d(cx + 30f), d(mapY + 38f), d(cx + 32f), d(mapY + 18f))
    }
    stroke(road, colors.faint(colors.terracotta), 1.6f)
    sketchCircle(pt(cx + 4f, mapY + 20f), 3f, colors.accentRed, filled = true)

    // magnifying glass, sweeping slowly over the map
    val sweep = 6f * wave(t, 0f)
    val glassPivot = pt(cx + 2f, mapY + 24f)
    withTransform({ rotate(degrees = sweep, pivot = glassPivot) }) {
        paintCircle(pt(cx - 6f, mapY + 16f), 20f, colors.sky.a(0.4f), colors.accentBlue, 2.4f)
        limb(
            Path().apply {
                moveTo(d(cx + 8f), d(mapY + 30f))
                lineTo(d(cx + 24f), d(mapY + 46f))
            },
            colors.metalDark, colors.accentBlue, 2.6f, thickness = 5f
        )
    }

    // paws gripping the map's near edges
    pandaArm(cx - halfWidth + 4f, bodyTop + 22f, cx - 42f, mapY + 26f, colors, controlX = cx - 48f, controlY = bodyTop + 10f)
    pandaArm(cx + halfWidth - 4f, bodyTop + 22f, cx + 40f, mapY + 26f, colors, controlX = cx + 48f, controlY = bodyTop + 10f)

    pandaHead(
        cx, headCy, 30f, colors,
        tilt = -8f + 3f * wave(t, 0.2f),
        expression = PandaExpression.Worried,
        blink = pandaAutoBlink(t, 0.2f)
    )

    // a lost pin, bobbing beside the panda with no destination marked
    val bob = 5f * wave(t, 0.4f)
    val pinX = 246f
    val pinTop = 150f + bob
    val pin = Path().apply {
        moveTo(d(pinX), d(pinTop + 40f))
        cubicTo(d(pinX - 20f), d(pinTop + 16f), d(pinX - 18f), d(pinTop), d(pinX), d(pinTop))
        cubicTo(d(pinX + 18f), d(pinTop), d(pinX + 20f), d(pinTop + 16f), d(pinX), d(pinTop + 40f))
        close()
    }
    contactShadow(pinX, 250f, 14f, 4f, colors.shadeSoft)
    paint(pin, vBrush(pinTop, pinTop + 40f, colors.terracotta.lit(0.25f), colors.clay), colors.ink, 2f)
    paintCircle(pt(pinX, pinTop + 13f), 7f, colors.paper, colors.accentRed, 1.8f)

    twinkle(78f, 96f, 3f, t, 0.3f, colors.inkSoft)
    twinkle(260f, 210f, 3f, t, 0.7f, colors.inkSoft)
    groundLine(270f, colors.inkFaint)
}
