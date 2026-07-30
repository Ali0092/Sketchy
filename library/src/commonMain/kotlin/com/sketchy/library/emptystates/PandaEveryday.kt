package com.sketchy.library.emptystates

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import com.sketchy.library.SketchyStyle
import com.sketchy.library.utils.*
import com.sketchy.library.characters.*

// ─── Panda Empty Inbox ─────────────────────────────────────────────────────
//   Panda lounges beside a completely empty inbox tray, perfectly content.

internal fun DrawScope.drawPandaEmptyInbox(t: Float, colors: SketchyStyle) {
    val trayX = 210f
    val trayY = 206f
    val tray = Path().apply {
        moveTo(d(trayX - 50f), d(trayY))
        lineTo(d(trayX - 50f), d(trayY + 22f))
        lineTo(d(trayX + 50f), d(trayY + 22f))
        lineTo(d(trayX + 50f), d(trayY))
    }
    contactShadow(trayX, trayY + 28f, 54f, 6f, colors.shade)
    paint(tray, vBrush(trayY, trayY + 22f, colors.metal.lit(0.3f), colors.metalDark), colors.ink, 2.4f)
    sketchLine(pt(trayX - 32f, trayY + 11f), pt(trayX + 32f, trayY + 11f), colors.inkFaint, 1.6f)

    val pcx = 110f
    val lean = 4f * wave(t, 0.1f)
    contactShadow(pcx, 252f, 42f, 7f, colors.shade)
    // reclining back on one paw, legs at ease
    pandaLeg(pcx - 12f, 226f, pcx - 46f, 246f, colors, controlX = pcx - 30f, controlY = 244f, thickness = 13f)
    pandaLeg(pcx + 12f, 226f, pcx + 34f, 250f, colors, controlX = pcx + 24f, controlY = 238f, thickness = 13f)
    pandaBody(pcx, 172f + lean * 0.2f, 228f, 32f, colors)
    pandaArm(pcx - 26f, 188f, pcx - 50f, 214f, colors, controlX = pcx - 44f, controlY = 196f, thickness = 10f)
    pandaArm(pcx + 26f, 188f, trayX - 56f, trayY + 6f, colors, controlX = pcx + 46f, controlY = 196f, thickness = 10f)
    pandaHead(pcx, 142f + lean, 35f, colors, tilt = -4f + lean, expression = PandaExpression.Content, blink = pandaAutoBlink(t))

    twinkle(70f, 150f, 3f, t, 0.4f, colors.inkSoft)
    twinkle(250f, 130f, 3f, t, 0.7f, colors.accent)
    groundLine(260f, colors.inkFaint)
}

// ─── Panda No Notifications ────────────────────────────────────────────────
//   Panda dozes beside a quiet, barely-swinging bell, little "Z"s drifting up.

internal fun DrawScope.drawPandaNoNotifications(t: Float, colors: SketchyStyle) {
    val bellX = 208f
    val swing = 2.5f * wave(t, 0f)
    val bellPivot = pt(bellX, 122f)
    withTransform({ rotate(degrees = swing, pivot = bellPivot) }) {
        val bell = Path().apply {
            moveTo(d(bellX - 30f), d(178f))
            quadraticTo(d(bellX - 30f), d(134f), d(bellX), d(122f))
            quadraticTo(d(bellX + 30f), d(134f), d(bellX + 30f), d(178f))
            close()
        }
        paint(bell, vBrush(122f, 178f, colors.sun, colors.sunDeep), colors.ink, 2.4f)
        shade(bell, hBrush(bellX - 30f, bellX + 30f, colors.shade.a(0f), colors.shade))
        limb(
            Path().apply {
                moveTo(d(bellX - 34f), d(178f))
                lineTo(d(bellX + 34f), d(178f))
            },
            colors.sunDeep, colors.ink, 2.2f, thickness = 5f
        )
        paintCircle(pt(bellX, 190f), 8f, colors.sunDeep, colors.ink, 2f)
        sketchLine(pt(bellX, 122f), pt(bellX, 112f), colors.ink, 2f)
    }

    val pcx = 112f
    contactShadow(pcx, 248f, 42f, 7f, colors.shade)
    // curled up asleep, head resting low
    pandaLeg(pcx - 10f, 220f, pcx - 24f, 240f, colors, controlX = pcx - 18f, controlY = 236f, thickness = 12f)
    pandaLeg(pcx + 10f, 220f, pcx + 26f, 240f, colors, controlX = pcx + 18f, controlY = 236f, thickness = 12f)
    pandaBody(pcx, 176f, 224f, 32f, colors)
    pandaArm(pcx - 24f, 190f, pcx - 8f, 214f, colors, controlX = pcx - 24f, controlY = 208f, thickness = 10f)
    pandaArm(pcx + 24f, 190f, pcx + 8f, 214f, colors, controlX = pcx + 24f, controlY = 208f, thickness = 10f)
    pandaHead(pcx, 154f, 34f, colors, tilt = 14f, expression = PandaExpression.Sleepy, blink = 0.85f)

    // a couple of drifting "Z"s, staggered so they don't rise in lockstep
    for (i in 0..1) {
        val rise = loop(t, 0.3f + i * 0.35f)
        val zx = pcx + 34f + i * 10f + rise * 14f
        val zy = 122f - rise * 30f
        val alpha = (1f - rise) * 0.8f
        val z = Path().apply {
            moveTo(d(zx - 5f), d(zy - 4f))
            lineTo(d(zx + 5f), d(zy - 4f))
            lineTo(d(zx - 5f), d(zy + 4f))
            lineTo(d(zx + 5f), d(zy + 4f))
        }
        stroke(z, colors.inkSoft.copy(alpha = alpha), 1.4f)
    }

    twinkle(80f, 170f, 3f, t, 0.5f, colors.inkSoft)
    groundLine(258f, colors.inkFaint)
}

// ─── Panda Empty Calendar ───────────────────────────────────────────────────
//   Panda sprawls across a wide-open, blank calendar page — nothing scheduled.

internal fun DrawScope.drawPandaEmptyCalendar(t: Float, colors: SketchyStyle) {
    val cal = roundRectPath(74f, 108f, 172f, 118f, 10f)
    contactShadow(160f, 232f, 84f, 6f, colors.shade)
    paint(cal, vBrush(108f, 226f, colors.paper, colors.metal), colors.ink, 2.4f)
    sketchLine(pt(74f, 136f), pt(246f, 136f), colors.ink, 2.2f)
    sketchCircle(pt(102f, 102f), 3f, colors.ink, filled = true)
    sketchCircle(pt(218f, 102f), 3f, colors.ink, filled = true)
    for (row in 0..2) {
        for (col in 0..4) {
            sketchCircle(pt(98f + col * 24f, 156f + row * 22f), 2f, colors.inkFaint, filled = true)
        }
    }

    // a little bamboo sprig stands in for a "today" marker
    bambooStalk(200f, 168f, 200f, colors, sway = 2f * wave(t, 0.2f), segments = 2)

    val lean = 3f * wave(t, 0.1f)
    val pcx = 150f
    // sprawled on top of the page, legs dangling off the front edge
    pandaLeg(pcx - 20f, 214f, pcx - 42f, 240f, colors, controlX = pcx - 34f, controlY = 232f, thickness = 13f)
    pandaLeg(pcx + 6f, 216f, pcx + 30f, 244f, colors, controlX = pcx + 20f, controlY = 236f, thickness = 13f)
    pandaBody(pcx - 6f, 178f + lean * 0.15f, 218f, 30f, colors)
    pandaArm(pcx - 30f, 192f, pcx - 58f, 200f, colors, controlX = pcx - 48f, controlY = 186f, thickness = 10f)
    pandaArm(pcx + 14f, 190f, pcx + 40f, 178f, colors, controlX = pcx + 30f, controlY = 176f, thickness = 10f)
    pandaHead(pcx - 24f, 176f + lean, 32f, colors, tilt = 78f, expression = PandaExpression.Sleepy, blink = 0.6f)

    twinkle(66f, 128f, 3f, t, 0.4f, colors.inkSoft)
    twinkle(252f, 150f, 3f, t, 0.7f, colors.accent)
    groundLine(252f, colors.inkFaint)
}

// ─── Panda No Photos ────────────────────────────────────────────────────────
//   Panda holds up a little camera beside an empty, tilting picture frame.

internal fun DrawScope.drawPandaNoPhotos(t: Float, colors: SketchyStyle) {
    val tilt = 3f * wave(t, 0f)
    val frameCx = 210f
    val framePivot = pt(frameCx, 160f)
    withTransform({ rotate(degrees = tilt, pivot = framePivot) }) {
        val frame = Path().apply {
            moveTo(d(frameCx - 46f), d(114f))
            lineTo(d(frameCx + 46f), d(114f))
            lineTo(d(frameCx + 46f), d(206f))
            lineTo(d(frameCx - 46f), d(206f))
            close()
        }
        paint(frame, vBrush(114f, 206f, colors.wood.lit(0.3f), colors.woodDark), colors.ink, 2.4f)
        fill(
            Path().apply {
                moveTo(d(frameCx - 36f), d(124f))
                lineTo(d(frameCx + 36f), d(124f))
                lineTo(d(frameCx + 36f), d(196f))
                lineTo(d(frameCx - 36f), d(196f))
                close()
            },
            vBrush(124f, 196f, colors.sky.lit(0.3f), colors.paper)
        )
        val hills = Path().apply {
            moveTo(d(frameCx - 32f), d(192f))
            lineTo(d(frameCx - 6f), d(154f))
            lineTo(d(frameCx + 10f), d(174f))
            lineTo(d(frameCx + 32f), d(140f))
        }
        drawPath(hills, color = colors.inkFaint, style = dashed())
        sketchCircle(pt(frameCx + 8f, 132f), 8f, colors.accent.copy(alpha = 0.6f), width = 1.8f)
    }

    val pcx = 108f
    contactShadow(pcx, 250f, 40f, 7f, colors.shade)
    pandaLeg(pcx - 14f, 226f, pcx - 20f, 252f, colors, thickness = 13f)
    pandaLeg(pcx + 14f, 226f, pcx + 22f, 252f, colors, thickness = 13f)
    pandaBody(pcx, 172f, 228f, 32f, colors)
    pandaArm(pcx - 24f, 188f, pcx - 44f, 214f, colors, controlX = pcx - 40f, controlY = 198f, thickness = 10f)

    // the camera, held up in the other paw
    val camX = pcx + 46f
    val camY = 168f
    pandaArm(pcx + 24f, 188f, camX - 8f, camY + 10f, colors, controlX = pcx + 42f, controlY = 182f, thickness = 10f)
    val camera = roundRectPath(camX - 18f, camY - 12f, 36f, 26f, 5f)
    paint(camera, vBrush(camY - 12f, camY + 14f, colors.metal.lit(0.3f), colors.metalDark), colors.ink, 2.2f)
    paintCircle(pt(camX, camY + 1f), 8f, colors.metalDark, colors.ink, 1.8f)
    sketchCircle(pt(camX, camY + 1f), 4f, colors.sky, filled = true)
    paint(roundRectPath(camX - 6f, camY - 18f, 12f, 6f, 2f), colors.metalDark, colors.ink, 1.6f)

    pandaHead(pcx, 140f, 34f, colors, tilt = -4f, expression = PandaExpression.Content, blink = pandaAutoBlink(t, 0.4f))

    twinkle(80f, 150f, 3f, t, 0.4f, colors.inkSoft)
    groundLine(260f, colors.inkFaint)
}

// ─── Panda All Done ─────────────────────────────────────────────────────────
//   Panda jumps for joy beside a big popping checkmark — everything's done!

internal fun DrawScope.drawPandaAllDone(t: Float, colors: SketchyStyle) {
    val pop = t % 1f
    val ringScale = if (pop < 0.15f) 0.85f + 0.15f * (pop / 0.15f) else 1f
    val ringCx = 218f
    val ringCy = 156f
    val ringPivot = pt(ringCx, ringCy)
    withTransform({ scale(scaleX = ringScale, scaleY = ringScale, pivot = ringPivot) }) {
        paintCircle(pt(ringCx, ringCy), 58f, colors.leaf.lit(0.55f), colors.ink, 2.6f)
        sketchLine(pt(ringCx - 26f, ringCy), pt(ringCx - 6f, ringCy + 20f), colors.accentGreen, 4f)
        sketchLine(pt(ringCx - 6f, ringCy + 20f), pt(ringCx + 28f, ringCy - 20f), colors.accentGreen, 4f)
    }

    // a joyful hop — torso, limbs and head all lift together, feet included
    val hop = kotlin.math.abs(wave(t, 0f)).let { it * it } * 16f
    val pcx = 110f
    contactShadow(pcx, 252f, 40f * (1f - hop / 32f), 7f, colors.shade)
    pandaLeg(pcx - 14f, 214f - hop, pcx - 20f, 238f - hop, colors, thickness = 13f)
    pandaLeg(pcx + 14f, 214f - hop, pcx + 22f, 238f - hop, colors, thickness = 13f)
    pandaBody(pcx, 158f - hop, 216f - hop, 32f, colors)
    // arms thrown straight up in celebration
    pandaArm(pcx - 26f, 176f - hop, pcx - 44f, 130f - hop, colors, controlX = pcx - 40f, controlY = 154f - hop, thickness = 10f)
    pandaArm(pcx + 26f, 176f - hop, pcx + 44f, 130f - hop, colors, controlX = pcx + 40f, controlY = 154f - hop, thickness = 10f)
    pandaHead(pcx, 130f - hop, 35f, colors, tilt = 6f * wave(t, 0.2f), expression = PandaExpression.Delighted, blink = pandaAutoBlink(t, 0.7f))

    bambooStalk(70f, 168f - hop * 0.4f, 226f, colors, sway = 6f * wave(t, 0.3f))

    val confetti = listOf(
        Triple(60f, 110f, colors.accent),
        Triple(250f, 100f, colors.accentBlue),
        Triple(56f, 210f, colors.accentGreen),
        Triple(258f, 214f, colors.accentRed),
    )
    confetti.forEachIndexed { i, (x, y, c) -> twinkle(x, y, 4f, t, i * 0.2f, c) }

    groundLine(262f, colors.inkFaint)
}
