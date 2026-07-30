package com.sketchy.library.emptystates

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import com.sketchy.library.SketchyStyle
import com.sketchy.library.characters.*
import com.sketchy.library.utils.*

// ─── Panda No Results ─────────────────────────────────────────────────────────
//   Panda holds a magnifying glass out toward an empty, open box, shrugging
//   its free paw — nothing turned up.

internal fun DrawScope.drawPandaNoResults(t: Float, colors: SketchyStyle) {
    val cx = 108f
    val headCy = 122f
    val bodyTop = 150f
    val bodyBottom = 214f
    val halfWidth = 32f

    val boxX = 196f
    val boxY = 192f
    val boxW = 76f
    val boxH = 44f

    contactShadow(cx, 258f, 44f, 6f, colors.shade)
    contactShadow(boxX + boxW / 2f, boxY + boxH + 6f, 46f, 6f, colors.shade)

    // empty, open box — flaps splayed, a faint fold line inside
    val box = Path().apply {
        moveTo(d(boxX), d(boxY + 12f))
        lineTo(d(boxX + boxW), d(boxY + 12f))
        lineTo(d(boxX + boxW - 6f), d(boxY + boxH))
        lineTo(d(boxX + 6f), d(boxY + boxH))
        close()
    }
    paint(box, vBrush(boxY + 12f, boxY + boxH, colors.wood.lit(0.2f), colors.woodDark), colors.ink, 2.2f)
    sketchLine(pt(boxX + 12f, boxY + 24f), pt(boxX + boxW - 12f, boxY + 24f), colors.faint(colors.woodDark), 1.4f)
    val flapL = Path().apply {
        moveTo(d(boxX + 2f), d(boxY + 12f))
        lineTo(d(boxX + boxW * 0.46f), d(boxY + 12f))
        lineTo(d(boxX - 8f), d(boxY - 16f))
        close()
    }
    val flapR = Path().apply {
        moveTo(d(boxX + boxW - 2f), d(boxY + 12f))
        lineTo(d(boxX + boxW * 0.54f), d(boxY + 12f))
        lineTo(d(boxX + boxW + 8f), d(boxY - 16f))
        close()
    }
    paint(flapL, vBrush(boxY - 16f, boxY + 12f, colors.wood.lit(0.3f), colors.wood), colors.ink, 1.8f)
    paint(flapR, vBrush(boxY - 16f, boxY + 12f, colors.wood.lit(0.3f), colors.wood), colors.ink, 1.8f)

    pandaLeg(cx - 14f, bodyBottom - 4f, cx - 22f, 254f, colors, thickness = 13f)
    pandaLeg(cx + 14f, bodyBottom - 4f, cx + 20f, 254f, colors, thickness = 13f)

    // shrugging paw
    val shrug = 4f * wave(t, 0.2f)
    pandaArm(
        cx - halfWidth + 4f, bodyTop + 16f,
        cx - 48f, bodyTop - 2f - shrug,
        colors, controlX = cx - 50f, controlY = bodyTop + 20f
    )

    // magnifying glass, held out toward the box
    val reach = 3f * wave(t, 0f)
    val glassCx = boxX - 6f + reach
    val glassCy = boxY - 2f
    pandaArm(
        cx + halfWidth - 6f, bodyTop + 16f,
        glassCx - 16f, glassCy + 10f,
        colors, controlX = cx + 46f, controlY = bodyTop + 4f
    )
    val glassPivot = pt(glassCx, glassCy)
    withTransform({ rotate(degrees = 6f * wave(t, 0.1f), pivot = glassPivot) }) {
        paintCircle(glassPivot, 22f, colors.sky.a(0.4f), colors.accentBlue, 2.6f)
        limb(
            Path().apply {
                moveTo(d(glassCx + 14f), d(glassCy + 16f))
                lineTo(d(glassCx + 30f), d(glassCy + 32f))
            },
            colors.metalDark, colors.accentBlue, 3f, thickness = 5f
        )
    }

    pandaBody(cx, bodyTop, bodyBottom, halfWidth, colors)
    pandaHead(
        cx, headCy, 30f, colors,
        tilt = -6f + 2f * wave(t, 0.15f),
        expression = PandaExpression.Content,
        blink = pandaAutoBlink(t, 0.3f)
    )

    twinkle(74f, 90f, 3f, t, 0.4f, colors.inkSoft)
    twinkle(252f, 160f, 3f, t, 0.75f, colors.inkSoft)
    groundLine(268f, colors.inkFaint)
}

// ─── Panda No Data ────────────────────────────────────────────────────────────
//   Panda shrugs, both paws up, beside a flat, dashed chart with nothing
//   plotted on it and a bobbing question mark overhead.

internal fun DrawScope.drawPandaNoData(t: Float, colors: SketchyStyle) {
    val cx = 104f
    val headCy = 122f
    val bodyTop = 150f
    val bodyBottom = 214f
    val halfWidth = 32f

    val baseY = 222f
    val axisX = 176f
    val axisRight = 262f

    contactShadow(cx, 258f, 44f, 6f, colors.shade)

    sketchLine(pt(axisX, baseY), pt(axisRight, baseY), colors.ink, 2f)
    sketchLine(pt(axisX, 118f), pt(axisX, baseY), colors.ink, 2f)
    val heights = listOf(16f, 26f, 12f)
    heights.forEachIndexed { i, h ->
        val x = axisX + 26f + i * 26f
        val bar = Path().apply {
            moveTo(d(x), d(baseY))
            lineTo(d(x), d(baseY - h))
        }
        drawPath(bar, color = colors.inkFaint, style = dashed())
    }

    // a small bamboo stalk keeping the empty chart's corner from feeling too bare
    bambooStalk(268f, 150f, 214f, colors, sway = 3f * wave(t, 0.2f), segments = 3)

    // shrugging, both paws up
    val shrug = 3f * wave(t, 0.15f)
    pandaLeg(cx - 14f, bodyBottom - 4f, cx - 22f, 254f, colors, thickness = 13f)
    pandaLeg(cx + 14f, bodyBottom - 4f, cx + 20f, 254f, colors, thickness = 13f)
    pandaArm(
        cx - halfWidth + 4f, bodyTop + 18f,
        cx - 44f, bodyTop - 4f - shrug,
        colors, controlX = cx - 48f, controlY = bodyTop + 20f
    )
    pandaArm(
        cx + halfWidth - 4f, bodyTop + 18f,
        cx + 44f, bodyTop - 4f - shrug,
        colors, controlX = cx + 48f, controlY = bodyTop + 20f
    )
    pandaBody(cx, bodyTop, bodyBottom, halfWidth, colors)
    pandaHead(
        cx, headCy, 30f, colors,
        tilt = 5f * wave(t, 0.1f),
        expression = PandaExpression.Content,
        blink = pandaAutoBlink(t, 0.5f)
    )

    // a bobbing "?" wondering what should be plotted here
    val bob = 5f * wave(t, 0f)
    sketchCircle(pt(220f, 74f + bob), 14f, colors.accent, width = 2.2f)
    sketchLine(pt(215f, 69f + bob), pt(220f, 65f + bob), colors.accent, 2f)
    sketchLine(pt(220f, 65f + bob), pt(225f, 70f + bob), colors.accent, 2f)
    sketchLine(pt(220f, 74f + bob), pt(220f, 78f + bob), colors.accent, 2.2f)
    sketchCircle(pt(220f, 84f + bob), 1.6f, colors.accent, filled = true)

    twinkle(258f, 190f, 3f, t, 0.6f, colors.inkSoft)
    groundLine(268f, colors.inkFaint)
}

// ─── Panda No Comments ────────────────────────────────────────────────────────
//   An empty speech bubble bobs beside panda, who holds a pencil poised,
//   waiting to write the first line itself.

internal fun DrawScope.drawPandaNoComments(t: Float, colors: SketchyStyle) {
    val cx = 112f
    val headCy = 150f
    val bodyTop = 178f
    val bodyBottom = 236f
    val halfWidth = 32f

    val bounce = 4f * wave(t, 0f)
    val bubble = Path().apply {
        moveTo(d(168f), d(64f + bounce))
        lineTo(d(256f), d(64f + bounce))
        quadraticTo(d(268f), d(64f + bounce), d(268f), d(76f + bounce))
        lineTo(d(268f), d(114f + bounce))
        quadraticTo(d(268f), d(126f + bounce), d(256f), d(126f + bounce))
        lineTo(d(196f), d(126f + bounce))
        lineTo(d(182f), d(146f + bounce))
        lineTo(d(186f), d(126f + bounce))
        lineTo(d(180f), d(126f + bounce))
        quadraticTo(d(168f), d(126f + bounce), d(168f), d(114f + bounce))
        lineTo(d(168f), d(76f + bounce))
        quadraticTo(d(168f), d(64f + bounce), d(168f), d(64f + bounce))
        close()
    }
    paint(bubble, vBrush(64f + bounce, 146f + bounce, colors.paper, colors.metal), colors.ink, 2.2f)

    contactShadow(cx, 262f, 44f, 6f, colors.shade)
    pandaLeg(cx - 14f, bodyBottom - 4f, cx - 22f, 258f, colors, thickness = 13f)
    pandaLeg(cx + 14f, bodyBottom - 4f, cx + 20f, 258f, colors, thickness = 13f)
    pandaArm(
        cx - halfWidth + 4f, bodyTop + 18f,
        cx - 44f, bodyTop + 40f,
        colors, controlX = cx - 48f, controlY = bodyTop + 14f
    )
    val poise = 3f * wave(t, 0.2f)
    val handX = cx + 40f
    val handY = bodyTop - 12f + poise
    pandaArm(
        cx + halfWidth - 6f, bodyTop + 14f,
        handX, handY,
        colors, controlX = cx + 44f, controlY = bodyTop + 6f, thickness = 10f
    )
    pandaBody(cx, bodyTop, bodyBottom, halfWidth, colors)
    pandaHead(
        cx, headCy, 30f, colors,
        tilt = 8f + 2f * wave(t, 0.15f),
        expression = PandaExpression.Content,
        blink = pandaAutoBlink(t, 0.4f)
    )

    // pencil, tip poised just shy of the empty bubble
    val pencil = Path().apply {
        moveTo(d(handX - 24f), d(handY + 24f))
        lineTo(d(handX), d(handY))
    }
    limb(pencil, colors.wood, colors.accent, 2.2f, thickness = 5f)
    sketchLine(pt(handX, handY), pt(handX + 6f, handY - 6f), colors.ink, 1.8f)

    twinkle(150f, 56f, 3f, t, 0.3f, colors.inkSoft)
    twinkle(276f, 96f, 3f, t, 0.7f, colors.inkSoft)
    groundLine(270f, colors.inkFaint)
}

// ─── Panda No Messages ────────────────────────────────────────────────────────
//   An empty envelope hovers over an empty inbox tray while panda looks it
//   over, one paw resting on the rim.

internal fun DrawScope.drawPandaNoMessages(t: Float, colors: SketchyStyle) {
    val cx = 106f
    val headCy = 132f
    val bodyTop = 160f
    val bodyBottom = 222f
    val halfWidth = 32f

    val trayX = 176f
    val trayY = 210f
    val trayW = 96f
    val trayH = 18f

    contactShadow(cx, 260f, 44f, 6f, colors.shade)
    contactShadow(trayX + trayW / 2f, trayY + trayH + 6f, 56f, 7f, colors.shade)

    // shallow inbox tray, empty
    val tray = Path().apply {
        moveTo(d(trayX), d(trayY))
        lineTo(d(trayX + trayW), d(trayY))
        lineTo(d(trayX + trayW - 10f), d(trayY + trayH))
        lineTo(d(trayX + 10f), d(trayY + trayH))
        close()
    }
    paint(tray, vBrush(trayY, trayY + trayH, colors.metal.lit(0.25f), colors.metalDark), colors.ink, 2.2f)
    sketchLine(pt(trayX + 14f, trayY + 8f), pt(trayX + trayW - 14f, trayY + 8f), colors.inkFaint, 1.4f)

    // one small envelope hovering nearby, flap open and empty
    val bob = 4f * wave(t, 0.2f)
    val envX = 214f
    val envY = 158f + bob
    val envW = 56f
    val envH = 36f
    val env = Path().apply {
        moveTo(d(envX), d(envY))
        lineTo(d(envX + envW), d(envY))
        lineTo(d(envX + envW), d(envY + envH))
        lineTo(d(envX), d(envY + envH))
        close()
    }
    paint(env, vBrush(envY, envY + envH, colors.paper, colors.metal), colors.ink, 2f)
    val flap = Path().apply {
        moveTo(d(envX), d(envY))
        lineTo(d(envX + envW / 2f), d(envY + envH * 0.5f))
        lineTo(d(envX + envW), d(envY))
    }
    stroke(flap, colors.inkFaint, 1.6f)

    pandaLeg(cx - 14f, bodyBottom - 4f, cx - 22f, 256f, colors, thickness = 13f)
    pandaLeg(cx + 14f, bodyBottom - 4f, cx + 20f, 256f, colors, thickness = 13f)
    pandaArm(
        cx - halfWidth + 4f, bodyTop + 18f,
        cx - 46f, bodyTop + 42f,
        colors, controlX = cx - 50f, controlY = bodyTop + 16f
    )
    pandaArm(
        cx + halfWidth - 6f, bodyTop + 22f,
        trayX + 6f, trayY - 4f,
        colors, controlX = cx + 44f, controlY = bodyTop + 20f
    )
    pandaBody(cx, bodyTop, bodyBottom, halfWidth, colors)
    pandaHead(
        cx, headCy, 30f, colors,
        tilt = -6f + 2f * wave(t, 0.15f),
        expression = PandaExpression.Sleepy,
        blink = pandaAutoBlink(t, 0.5f)
    )

    twinkle(90f, 100f, 3f, t, 0.3f, colors.inkSoft)
    twinkle(268f, 150f, 3f, t, 0.7f, colors.inkSoft)
    groundLine(268f, colors.inkFaint)
}

// ─── Panda Page Not Found ─────────────────────────────────────────────────────
//   A cracked signpost sways above nothing useful while panda looks up,
//   shrugging at which way to go.

internal fun DrawScope.drawPandaPageNotFound(t: Float, colors: SketchyStyle) {
    val postX = 218f
    val postTopY = 78f
    val postBottomY = 208f

    contactShadow(postX, 212f, 20f, 4f, colors.shade)
    limb(
        Path().apply {
            moveTo(d(postX), d(postBottomY))
            lineTo(d(postX), d(postTopY))
        },
        colors.woodDark, colors.ink, 2.4f, thickness = 8f
    )

    val sway = 5f * wave(t, 0f)
    val signPivot = pt(postX, postTopY)
    withTransform({ rotate(degrees = sway, pivot = signPivot) }) {
        val sign = roundRectPath(postX - 52f, postTopY - 28f, 104f, 44f, 6f)
        paint(sign, vBrush(postTopY - 28f, postTopY + 16f, colors.wood.lit(0.3f), colors.wood), colors.ink, 2.2f)
        val crack = Path().apply {
            moveTo(d(postX - 10f), d(postTopY - 28f))
            lineTo(d(postX + 6f), d(postTopY - 8f))
            lineTo(d(postX - 12f), d(postTopY + 2f))
            lineTo(d(postX + 8f), d(postTopY + 14f))
        }
        stroke(crack, colors.accentRed, 2f)
        sketchLine(pt(postX - 32f, postTopY - 6f), pt(postX - 18f, postTopY - 6f), colors.inkFaint, 1.6f)
        sketchLine(pt(postX + 6f, postTopY - 6f), pt(postX + 28f, postTopY - 6f), colors.inkFaint, 1.6f)
    }

    // panda, looking up at a sign pointing nowhere useful
    val cx = 112f
    val headCy = 148f
    val bodyTop = 176f
    val bodyBottom = 232f
    val halfWidth = 32f

    contactShadow(cx, 262f, 44f, 6f, colors.shade)
    pandaLeg(cx - 14f, bodyBottom - 4f, cx - 22f, 258f, colors, thickness = 13f)
    pandaLeg(cx + 14f, bodyBottom - 4f, cx + 20f, 258f, colors, thickness = 13f)
    val shrug = 3f * wave(t, 0.2f)
    pandaArm(
        cx - halfWidth + 4f, bodyTop + 18f,
        cx - 44f, bodyTop - 2f - shrug,
        colors, controlX = cx - 46f, controlY = bodyTop + 20f
    )
    pandaArm(
        cx + halfWidth - 4f, bodyTop + 18f,
        cx + 40f, bodyTop + 42f,
        colors, controlX = cx + 44f, controlY = bodyTop + 16f
    )
    pandaBody(cx, bodyTop, bodyBottom, halfWidth, colors)
    pandaHead(
        cx, headCy, 30f, colors,
        tilt = 12f + 3f * wave(t, 0.15f),
        expression = PandaExpression.Worried,
        blink = pandaAutoBlink(t, 0.6f)
    )

    twinkle(70f, 100f, 3f, t, 0.3f, colors.inkSoft)
    groundLine(270f, colors.inkFaint)
}
