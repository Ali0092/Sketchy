package com.sketchy.library.illustrations

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.withTransform
import com.sketchy.library.SketchyStyle
import com.sketchy.library.utils.*

/**
 * The **Signboards** illustrations category: road signage as the setting for a scene, not just
 * the subject — a crew at work, a signpost pointing the way. Pairs with the empty-state
 * `"Signboards"` category, which covers the same visual vocabulary for empty/error states.
 */

private fun DrawScope.workCone(cx: Float, baseY: Float, height: Float, baseW: Float, colors: SketchyStyle) {
    val topW = baseW * 0.2f
    val topY = baseY - height
    val base = roundRectPath(cx - baseW / 2f - 4f, baseY - 6f, baseW + 8f, 10f, 3f)
    paint(base, vBrush(baseY - 6f, baseY + 4f, colors.metal.lit(0.2f), colors.metalDark), colors.ink, 1.8f)
    val body = Path().apply {
        moveTo(d(cx - baseW / 2f), d(baseY))
        quadraticTo(d(cx - baseW * 0.32f), d(baseY - height * 0.55f), d(cx - topW / 2f), d(topY))
        lineTo(d(cx + topW / 2f), d(topY))
        quadraticTo(d(cx + baseW * 0.32f), d(baseY - height * 0.55f), d(cx + baseW / 2f), d(baseY))
        close()
    }
    inkShadow(body, colors.outlineShadow)
    paint(body, vBrush(baseY - height, baseY, colors.sun, colors.sunDeep), colors.ink, 2.2f)
    sketchLine(
        pt(cx - baseW * 0.28f, baseY - height * 0.4f),
        pt(cx + baseW * 0.28f, baseY - height * 0.4f),
        colors.hint(colors.paper),
        4f
    )
}

// ─── Road Work Ahead ────────────────────────────────────────────────────────────
//   A crew member digging beside a mound of gravel, a MEN AT WORK diamond sign
//   swaying on its post and a pair of cones standing guard over the job.

internal fun DrawScope.drawRoadWorkScene(t: Float, colors: SketchyStyle) {
    val signCx = 92f
    val signMidY = 138f
    val signHalfW = 44f
    val signHalfH = 48f

    contactShadow(signCx, 282f, 20f, 6f, colors.shade)
    contactShadow(210f, 288f, 56f, 8f, colors.shade)
    contactShadow(52f, 290f, 15f, 5f, colors.shade)
    contactShadow(60f, 294f, 11f, 4f, colors.shade)

    val post = Path().apply { moveTo(d(signCx), d(signMidY + signHalfH)); lineTo(d(signCx), d(272f)) }
    limb(post, colors.metalDark, colors.ink, 2.2f, thickness = 8f)

    val sway = 2.2f * wave(t, 0f)
    val signPivot = pt(signCx, signMidY + signHalfH)
    withTransform({ rotate(degrees = sway, pivot = signPivot) }) {
        val diamond = listOf(
            signCx to signMidY - signHalfH,
            signCx + signHalfW to signMidY,
            signCx to signMidY + signHalfH,
            signCx - signHalfW to signMidY
        )
        val panel = roundedPolygonPath(diamond, r = 10f)
        inkShadow(panel, colors.outlineShadow)
        cornerShade(panel, signCx, signMidY, signHalfH * 1.05f, colors.outlineShadow.a(colors.outlineShadow.alpha * 0.6f))
        paint(panel, vBrush(signMidY - signHalfH, signMidY + signHalfH, colors.sun, colors.sunDeep), colors.ink, 2.4f)
        sheen(panel, pt(signCx - 20f, signMidY - 24f), pt(signCx + 10f, signMidY + 10f), colors.paper.a(0.24f))
        signLabel(colors.textMeasurer, "MEN AT WORK", signCx, signMidY, colors.ink, fontSize = 10.5f, maxWidth = 66f)
    }

    workCone(48f, 290f, 34f, 22f, colors)
    workCone(62f, 294f, 26f, 18f, colors)

    // gravel mound
    val moundCx = 178f
    val mound = Path().apply {
        moveTo(d(moundCx - 42f), d(280f))
        quadraticTo(d(moundCx - 30f), d(248f), d(moundCx), d(244f))
        quadraticTo(d(moundCx + 30f), d(248f), d(moundCx + 40f), d(280f))
        close()
    }
    paint(mound, vBrush(244f, 280f, colors.clay.lit(0.15f), colors.terracotta), colors.ink, 2.2f)
    listOf(0.3f to 0.5f, 0.55f to 0.35f, 0.7f to 0.6f).forEach { (fx, fy) ->
        sketchCircle(pt(moundCx - 42f + fx * 84f, 280f - fy * 30f), 2.2f, colors.faint(colors.charcoal), filled = true)
    }

    // worker: hard hat, hi-vis vest, digging into the mound
    val cx = 224f
    val dig = pulse(t, 0f)
    contactShadow(cx, 282f, 30f, 6f, colors.shade)

    paintCircle(pt(cx, 118f), 17f, colors.skin, colors.ink, 2.2f)
    val hat = Path().apply {
        moveTo(d(cx - 20f), d(108f))
        quadraticTo(d(cx), d(84f), d(cx + 20f), d(108f))
        lineTo(d(cx + 22f), d(112f))
        lineTo(d(cx - 22f), d(112f))
        close()
    }
    paint(hat, vBrush(84f, 112f, colors.sun.lit(0.1f), colors.sunDeep), colors.ink, 2.2f)
    sketchLine(pt(cx - 4f, 122f), pt(cx + 4f, 122f), colors.ink, 1.6f)
    val neck = Path().apply { moveTo(d(cx), d(135f)); lineTo(d(cx), d(142f)) }
    limb(neck, colors.skinDark, colors.ink, 2.2f, thickness = 8f)

    val torso = Path().apply {
        moveTo(d(cx), d(142f))
        quadraticTo(d(cx - 22f), d(148f), d(cx - 24f), d(172f))
        lineTo(d(cx - 20f), d(206f))
        quadraticTo(d(cx), d(214f), d(cx + 20f), d(206f))
        lineTo(d(cx + 24f), d(172f))
        quadraticTo(d(cx + 22f), d(148f), d(cx), d(142f))
        close()
    }
    paint(torso, vBrush(142f, 214f, colors.terracotta.lit(0.15f), colors.clay), colors.ink, 2.2f)
    clipPath(torso) {
        sketchLine(pt(cx - 26f, 165f), pt(cx + 26f, 155f), colors.hint(colors.paper), 5f)
        sketchLine(pt(cx - 26f, 192f), pt(cx + 26f, 182f), colors.hint(colors.paper), 5f)
    }

    val legL = Path().apply { moveTo(d(cx - 12f), d(206f)); quadraticTo(d(cx - 16f), d(232f), d(cx - 14f), d(256f)) }
    val legR = Path().apply { moveTo(d(cx + 12f), d(206f)); quadraticTo(d(cx + 18f), d(230f), d(cx + 20f), d(254f)) }
    limb(legL, colors.fabricDark, colors.ink, 2.2f, thickness = 9f)
    limb(legR, colors.fabricDark, colors.ink, 2.2f, thickness = 9f)
    sketchLine(pt(cx - 22f, 258f), pt(cx - 4f, 258f), colors.ink, 2f)
    sketchLine(pt(cx + 10f, 256f), pt(cx + 30f, 256f), colors.ink, 2f)

    // shovel, swinging into the mound with the dig cycle
    val handleTopX = cx - 14f
    val handleTopY = 150f
    val handleBotX = moundCx + 4f
    val handleBotY = 258f - 14f * dig
    val midGripX = handleTopX + (handleBotX - handleTopX) * 0.42f
    val midGripY = handleTopY + (handleBotY - handleTopY) * 0.42f

    val armBack = Path().apply { moveTo(d(cx - 20f), d(152f)); lineTo(d(handleTopX), d(handleTopY)) }
    val armFront = Path().apply { moveTo(d(cx + 18f), d(160f)); lineTo(d(midGripX), d(midGripY)) }
    limb(armBack, colors.skin, colors.ink, 2.2f, thickness = 7f)
    limb(armFront, colors.skin, colors.ink, 2.2f, thickness = 7f)

    val shovel = Path().apply { moveTo(d(handleTopX), d(handleTopY)); lineTo(d(handleBotX), d(handleBotY)) }
    limb(shovel, colors.wood, colors.ink, 2f, thickness = 5f)
    val blade = Path().apply {
        moveTo(d(handleBotX - 10f), d(handleBotY))
        lineTo(d(handleBotX + 10f), d(handleBotY))
        lineTo(d(handleBotX + 7f), d(handleBotY + 14f))
        lineTo(d(handleBotX - 7f), d(handleBotY + 14f))
        close()
    }
    paint(blade, vBrush(handleBotY, handleBotY + 14f, colors.metal.lit(0.2f), colors.metalDark), colors.ink, 1.8f)

    if (dig > 0.6f) {
        val a = (dig - 0.6f) / 0.4f
        sketchLine(pt(handleBotX - 6f, handleBotY + 16f), pt(handleBotX - 12f, handleBotY + 8f), colors.inkFaint.a(a), 1.6f)
        sketchLine(pt(handleBotX + 8f, handleBotY + 16f), pt(handleBotX + 14f, handleBotY + 6f), colors.inkFaint.a(a), 1.6f)
    }

    twinkle(40f, 100f, 3f, t, 0.3f, colors.inkSoft)
    twinkle(270f, 130f, 3f, t, 0.7f, colors.accent)
    groundHint(276f, colors.inkFaint)
}

private fun DrawScope.arrowPlate(
    cx: Float,
    cy: Float,
    angle: Float,
    pointRight: Boolean,
    text: String,
    fill: Color,
    colors: SketchyStyle
) {
    val pivot = pt(cx, cy)
    withTransform({ rotate(degrees = angle, pivot = pivot) }) {
        val halfW = 50f
        val halfH = 15f
        val dir = if (pointRight) 1f else -1f
        val tip = cx + dir * (halfW + 16f)
        val points = listOf(
            cx - dir * halfW to cy - halfH,
            cx + dir * halfW to cy - halfH,
            tip to cy,
            cx + dir * halfW to cy + halfH,
            cx - dir * halfW to cy + halfH
        )
        val panel = roundedPolygonPath(points, r = 6f)
        inkShadow(panel, colors.outlineShadow)
        paint(panel, vBrush(cy - halfH, cy + halfH, fill.lit(0.1f), fill), colors.ink, 2.2f)
        signLabel(colors.textMeasurer, text, cx, cy, colors.line(colors.paper), fontSize = 10f, maxWidth = 76f)
    }
}

// ─── Every Path Leads Somewhere ─────────────────────────────────────────────────
//   A crossroads signpost strung with arrow placards pointing every which way, a
//   bicycle leaning against it, and a couple of birds drifting past a rising sun.

internal fun DrawScope.drawCrossroadsScene(t: Float, colors: SketchyStyle) {
    val cx = 176f
    val postTopY = 78f

    fun bird(bx: Float, by: Float, phase: Float) {
        val bob = 3f * wave(t, phase)
        val wing = Path().apply {
            moveTo(d(bx - 8f), d(by + bob))
            quadraticTo(d(bx - 3f), d(by - 6f + bob), d(bx), d(by + bob))
            quadraticTo(d(bx + 3f), d(by - 6f + bob), d(bx + 8f), d(by + bob))
        }
        stroke(wing, colors.inkSoft, 1.8f)
    }

    contactShadow(cx, 284f, 20f, 6f, colors.shade)
    contactShadow(140f, 288f, 34f, 6f, colors.shade)

    glow(250f, 56f, 70f, colors.sun.a(0.25f))
    paintCircle(pt(250f, 56f), 22f, colors.sun.a(0.5f), colors.ink, 1.6f)

    val post = Path().apply { moveTo(d(cx), d(postTopY)); lineTo(d(cx), d(276f)) }
    limb(post, colors.woodDark, colors.ink, 2.4f, thickness = 8f)

    arrowPlate(cx, 112f, -6f, true, "ADVENTURE", colors.terracotta, colors)
    arrowPlate(cx, 146f, 5f, false, "HOME", colors.clay, colors)
    arrowPlate(cx, 180f, -4f, true, "COFFEE", colors.woodDark, colors)

    // a bicycle leaning against the post, parked mid-journey
    val wheelR = 22f
    val backX = 120f
    val frontX = 170f
    val wheelY = 268f
    sketchCircle(pt(backX, wheelY), wheelR, colors.line(colors.metalDark), width = 2.2f)
    sketchCircle(pt(frontX, wheelY), wheelR, colors.line(colors.metalDark), width = 2.2f)
    sketchCircle(pt(backX, wheelY), 2f, colors.ink, filled = true)
    sketchCircle(pt(frontX, wheelY), 2f, colors.ink, filled = true)
    val frame = Path().apply {
        moveTo(d(backX), d(wheelY))
        lineTo(d(backX + 18f), d(wheelY - 34f))
        lineTo(d(frontX - 6f), d(wheelY - 30f))
        lineTo(d(frontX), d(wheelY))
        moveTo(d(backX + 18f), d(wheelY - 34f))
        lineTo(d(backX + 6f), d(wheelY - 50f))
    }
    stroke(frame, colors.line(colors.metalDark), 2.6f)
    val handlebarPost = Path().apply {
        moveTo(d(frontX - 6f), d(wheelY - 30f))
        lineTo(d(frontX - 6f), d(wheelY - 46f))
    }
    stroke(handlebarPost, colors.line(colors.metalDark), 2.6f)
    sketchLine(pt(frontX - 16f, wheelY - 46f), pt(frontX + 4f, wheelY - 46f), colors.line(colors.metalDark), 2.6f)
    val seatPost = Path().apply {
        moveTo(d(backX + 6f), d(wheelY - 50f))
        lineTo(d(backX - 2f), d(wheelY - 54f))
    }
    stroke(seatPost, colors.line(colors.metalDark), 2.2f)

    bird(58f, 66f, 0.1f)
    bird(88f, 46f, 0.45f)

    twinkle(46f, 96f, 3f, t, 0.3f, colors.accent)
    twinkle(300f, 40f, 3f, t, 0.6f, colors.inkSoft)
    groundHint(280f, colors.inkFaint)
}
