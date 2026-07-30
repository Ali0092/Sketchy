package com.sketchy.library.illustrations

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.withTransform
import com.sketchy.library.SketchyStyle
import com.sketchy.library.characters.*
import com.sketchy.library.utils.*

/**
 * Panda's daily-life illustrations, set A: five original moments from a panda's day (breakfast,
 * a park walk, a nap, a rainy errand, bath time), all built on the shared [pandaHead]/[pandaBody]/
 * [pandaArm]/[pandaLeg]/[bambooStalk] rig from `characters/PandaCharacter.kt` so the character stays
 * consistent scene to scene. See the sketchy-illustrations skill's `references/theming.md`.
 */

/**
 * A small drifting "Z" mark for a sleeping panda — the same instinct as [steam]: a phase-shifted
 * [loop] so `t = 0` shows an already-drifting mark rather than nothing, fading out as it rises.
 * Call it two or three times at staggered [offset]s for a cascading "z Z Z" rather than one mark.
 */
private fun DrawScope.driftingZ(
    x: Float,
    y: Float,
    t: Float,
    offset: Float,
    color: Color,
    size: Float = 14f,
) {
    if (color.isHidden) return
    val phase = loop(t, offset + 0.25f)
    val rise = phase * 30f
    val drift = 8f * phase
    val strength = kotlin.math.sin(phase * kotlin.math.PI.toFloat()).coerceIn(0f, 1f)
    val s = size * (0.75f + 0.45f * phase)
    val zx = x + drift
    val zy = y - rise
    val z = Path().apply {
        moveTo(d(zx - s * 0.5f), d(zy - s * 0.42f))
        lineTo(d(zx + s * 0.5f), d(zy - s * 0.42f))
        lineTo(d(zx - s * 0.5f), d(zy + s * 0.42f))
        lineTo(d(zx + s * 0.5f), d(zy + s * 0.42f))
    }
    drawPath(path = z, color = color.a(color.alpha * strength), style = thin(1.8f))
}

// ─── A Panda's Morning Bamboo ─────────────────────────────────────────────────
//   Panda breakfasting cross-legged on a woven floor mat, a low table holding a
//   steaming bowl of bamboo shoots, morning sun pouring through a window behind.

internal fun DrawScope.drawPandaMorningBambooScene(t: Float, colors: SketchyStyle) {
    val breathe = pulse(t, 0f)

    // ── Background: window, upper left ──────────────────────────────────
    val paneX = 36f
    val paneY = 34f
    val paneW = 104f
    val paneH = 88f
    val pane = roundRectPath(paneX, paneY, paneW, paneH, 8f)
    fill(pane, vBrush(paneY, paneY + paneH, colors.sky, colors.paper))
    clipPath(pane) {
        val grove = Path().apply {
            moveTo(d(paneX), d(paneY + paneH * 0.72f))
            quadraticTo(d(paneX + paneW * 0.3f), d(paneY + paneH * 0.5f), d(paneX + paneW * 0.55f), d(paneY + paneH * 0.68f))
            quadraticTo(d(paneX + paneW * 0.8f), d(paneY + paneH * 0.48f), d(paneX + paneW), d(paneY + paneH * 0.7f))
            lineTo(d(paneX + paneW), d(paneY + paneH))
            lineTo(d(paneX), d(paneY + paneH))
            close()
        }
        paint(grove, colors.leafDark.a(0.35f), colors.lineOnly, 1.4f)
        val sunY = paneY + 40f - 4f * breathe
        glow(paneX + 52f, sunY, 34f, colors.sun.a(0.55f + 0.2f * breathe))
        sketchCircle(pt(paneX + 52f, sunY), 12f, colors.touch(colors.sun, 0.5f), filled = true)
        sketchCircle(pt(paneX + 52f, sunY), 12f, colors.lineOnly, width = 1.6f)
        fill(
            rectPath(paneX, paneY + paneH * 0.5f, paneW, paneH * 0.5f),
            vBrush(paneY + paneH * 0.5f, paneY + paneH, colors.glow.a(0f), colors.glow)
        )
    }
    drawPath(pane, color = colors.ink, style = bold(2.2f))
    sketchLine(pt(paneX + paneW / 2f, paneY), pt(paneX + paneW / 2f, paneY + paneH), colors.ink, 1.8f)
    sketchLine(pt(paneX, paneY + paneH / 2f), pt(paneX + paneW, paneY + paneH / 2f), colors.ink, 1.8f)
    val sill = roundRectPath(paneX - 6f, paneY + paneH, paneW + 12f, 8f, 3f)
    paint(sill, vBrush(paneY + paneH, paneY + paneH + 8f, colors.wood, colors.woodDark), colors.ink, 1.6f)

    // ── Midground: floor mat, low table, bowl of bamboo shoots ──────────
    val mat = roundRectPath(48f, 232f, 224f, 40f, 18f)
    paint(mat, vBrush(232f, 272f, colors.wood.lit(0.15f), colors.woodDark), colors.ink, 1.8f)
    for (i in 0..2) {
        val gy = 244f + i * 10f
        sketchLine(pt(60f, gy), pt(264f, gy - 2f), colors.faint(colors.woodDark.a(0.3f)), 1.2f)
    }
    clipPath(mat) {
        contactShadow(58f, 262f, 18f, 5f, colors.shade)
        contactShadow(140f, 252f, 50f, 10f, colors.shade)
    }

    val tableTop = ellipsePath(206f, 222f, 60f, 17f)
    paint(tableTop, hBrush(146f, 266f, colors.wood, colors.woodDark), colors.ink, 1.6f)
    shade(tableTop, hBrush(206f, 266f, colors.shade.a(0f), colors.shade))
    listOf(178f, 234f).forEach { lx ->
        paint(
            rectPath(lx, 234f, 10f, 30f),
            vBrush(234f, 264f, colors.woodDark, colors.woodDark.shaded(0.3f)),
            colors.ink,
            1.4f
        )
    }
    clipPath(tableTop) {
        contactShadow(210f, 214f, 30f, 8f, colors.shade)
    }

    val bowl = Path().apply {
        moveTo(d(186f), d(202f))
        lineTo(d(234f), d(202f))
        quadraticTo(d(230f), d(220f), d(210f), d(222f))
        quadraticTo(d(190f), d(220f), d(186f), d(202f))
        close()
    }
    paint(bowl, vBrush(202f, 222f, colors.clay.lit(0.15f), colors.clay), colors.ink, 1.8f)
    val bowlRim = ellipsePath(210f, 202f, 24f, 7f)
    paint(bowlRim, colors.wood, colors.ink, 1.6f)
    listOf(-10f, 2f, 13f).forEachIndexed { i, off ->
        val shoot = Path().apply {
            moveTo(d(210f + off), d(200f))
            quadraticTo(d(210f + off - 3f), d(186f - i * 2f), d(210f + off + 2f), d(176f - i * 3f))
            quadraticTo(d(210f + off + 6f), d(186f - i * 2f), d(210f + off + 3f), d(200f))
            close()
        }
        paint(shoot, vBrush(176f - i * 3f, 200f, colors.leaf, colors.leafDark), colors.inkOf(colors.leaf), 1.4f)
    }
    steam(210f, 196f, t, 0.15f, colors.hint(colors.paper.a(0.75f)), height = 34f)

    // a bamboo stalk planted beside the mat, a homely touch echoing the coffee scene's little plant
    bambooStalk(58f, 148f, 260f, colors, sway = 4f * wave(t, 0.1f))

    // ── Foreground hero: panda seated cross-legged, breakfasting ────────
    pandaBody(140f, 198f, 248f, 46f, colors)
    pandaLeg(122f, 240f, 176f, 250f, colors, controlX = 148f, controlY = 262f, thickness = 15f)
    pandaLeg(160f, 240f, 108f, 248f, colors, controlX = 138f, controlY = 264f, thickness = 15f)
    pandaArm(112f, 206f, 122f, 232f, colors, controlX = 104f, controlY = 222f, thickness = 11f)
    val handX = 182f
    val handY = 188f
    pandaArm(168f, 204f, handX, handY, colors, controlX = 190f, controlY = 196f, thickness = 11f)
    // a bamboo shoot pinched in the raised paw, on its way to the mouth
    val nibble = Path().apply {
        moveTo(d(handX), d(handY))
        quadraticTo(d(handX + 6f), d(handY - 14f), d(handX + 2f), d(handY - 24f))
    }
    limb(nibble, colors.leaf, colors.inkOf(colors.leaf), 1.6f, thickness = 5f)
    pandaHead(
        140f, 168f, 38f, colors,
        tilt = 5f * wave(t, 0.05f),
        expression = PandaExpression.Delighted,
        blink = pandaAutoBlink(t, 0.2f)
    )

    twinkle(268f, 58f, 4f, t, 0.35f, colors.touch(colors.sun))
    twinkle(96f, 96f, 3f, t, 0.6f, colors.touch(colors.paper.a(0.9f)))
}

// ─── A Panda's Park Walk ──────────────────────────────────────────────────────
//   Panda strolling along a garden path under a shady tree, a couple of leaves
//   drifting down and a young bamboo stalk planted at the path's edge.

internal fun DrawScope.drawPandaParkWalkScene(t: Float, colors: SketchyStyle) {
    val bob = 2.5f * wave(t, 0.25f)
    val stride = wave(t, 0f)

    // ── Background: sun, distant hill, shady tree ───────────────────────
    glow(258f, 52f, 46f, colors.sun.a(0.4f + 0.15f * pulse(t, 0f)))
    sketchCircle(pt(258f, 52f), 20f, colors.touch(colors.sun, 0.5f), filled = true)
    sketchCircle(pt(258f, 52f), 20f, colors.lineOnly, width = 1.6f)

    val hill = Path().apply {
        moveTo(d(20f), d(220f))
        quadraticTo(d(90f), d(190f), d(170f), d(212f))
        quadraticTo(d(240f), d(232f), d(300f), d(206f))
        lineTo(d(300f), d(280f))
        lineTo(d(20f), d(280f))
        close()
    }
    paint(hill, colors.leaf.a(0.22f), colors.lineOnly, 1.2f)

    // tree, background right, weight lighter than the hero panda
    val trunk = Path().apply {
        moveTo(d(238f), d(258f))
        quadraticTo(d(232f), d(200f), d(238f), d(148f))
    }
    limb(trunk, colors.woodDark, colors.ink, 1.6f, thickness = 12f)
    listOf(
        Triple(238f, 118f, 34f),
        Triple(206f, 140f, 26f),
        Triple(268f, 138f, 26f),
        Triple(220f, 100f, 22f)
    ).forEach { (cx, cy, r) -> paintCircle(pt(cx, cy), r, colors.leaf, colors.ink, 1.6f) }
    shade(ellipsePath(238f, 118f, 34f, 34f), hBrush(220f, 270f, colors.shade.a(0f), colors.shade))

    // drifting leaves, each falling and turning at its own pace
    listOf(0.15f, 0.55f, 0.85f).forEachIndexed { i, off ->
        val phase = loop(t, off)
        val lx = 200f + i * 22f + 14f * wave(t, off * 2f)
        val ly = 110f + phase * 150f
        val leaf = Path().apply {
            moveTo(d(lx - 5f), d(ly))
            quadraticTo(d(lx), d(ly - 7f), d(lx + 5f), d(ly))
            quadraticTo(d(lx), d(ly + 7f), d(lx - 5f), d(ly))
            close()
        }
        val leafPivot = pt(lx, ly)
        withTransform({ rotate(degrees = 360f * phase + i * 40f, pivot = leafPivot) }) {
            paint(leaf, colors.leaf.a(0.9f - 0.3f * phase), colors.inkOf(colors.leaf), 1.2f)
        }
    }

    // bamboo stalk planted at the path's edge
    contactShadow(52f, 262f, 16f, 4f, colors.shade)
    bambooStalk(52f, 156f, 258f, colors, sway = 5f * wave(t, 0.2f))

    groundHint(266f, colors.inkFaint)

    // ── Foreground hero: panda mid-stride ────────────────────────────────
    val cx = 150f
    val topY = 178f + bob
    contactShadow(cx, 258f, 44f, 9f, colors.shade)
    pandaLeg(cx - 14f, 232f + bob, cx + 10f + 16f * stride, 254f, colors, controlX = cx - 4f, controlY = 246f + bob, thickness = 15f)
    pandaLeg(cx + 14f, 232f + bob, cx - 10f - 16f * stride, 254f, colors, controlX = cx + 4f, controlY = 246f + bob, thickness = 15f)
    pandaBody(cx, topY, topY + 50f, 42f, colors)
    pandaArm(cx - 30f, topY + 16f, cx - 14f - 18f * stride, topY + 48f, colors, controlX = cx - 34f, controlY = topY + 32f, thickness = 11f)
    pandaArm(cx + 30f, topY + 16f, cx + 14f + 18f * stride, topY + 48f, colors, controlX = cx + 34f, controlY = topY + 32f, thickness = 11f)
    pandaHead(
        cx, topY - 30f, 34f, colors,
        tilt = 4f * wave(t, 0.1f),
        expression = PandaExpression.Content,
        blink = pandaAutoBlink(t, 0.4f)
    )

    twinkle(96f, 70f, 4f, t, 0.4f, colors.touch(colors.sun))
    twinkle(276f, 128f, 3f, t, 0.75f, colors.touch(colors.leaf))
}

// ─── A Panda's Nap Time ───────────────────────────────────────────────────────
//   Panda curled up asleep on a floor cushion under a shady tree, a blanket
//   draped over, a paw over the eyes, and a little "Zzz" drifting up.

internal fun DrawScope.drawPandaNapTimeScene(t: Float, colors: SketchyStyle) {
    // ── Background: a small shade tree, upper left ──────────────────────
    val trunk = Path().apply {
        moveTo(d(70f), d(210f))
        quadraticTo(d(66f), d(160f), d(72f), d(112f))
    }
    limb(trunk, colors.woodDark, colors.ink, 1.4f, thickness = 10f)
    listOf(Triple(72f, 88f, 30f), Triple(46f, 104f, 22f), Triple(98f, 102f, 22f)).forEach { (cx, cy, r) ->
        paintCircle(pt(cx, cy), r, colors.leaf, colors.ink, 1.4f)
    }
    glow(72f, 96f, 60f, colors.glow.a(0.5f))

    // ── Midground: a round floor cushion ─────────────────────────────────
    contactShadow(164f, 282f, 82f, 14f, colors.shade)
    val cushion = ellipsePath(164f, 268f, 78f, 20f)
    paint(cushion, vBrush(250f, 286f, colors.fabric.lit(0.15f), colors.fabricDark), colors.ink, 1.8f)
    sheen(cushion, pt(110f, 254f), pt(160f, 276f), colors.paper.a(0.25f))

    // ── Foreground hero: the panda, curled into a ball ───────────────────
    val cx = 168f
    val topY = 220f
    val bottomY = 262f
    val halfWidth = 54f
    pandaBody(cx, topY, bottomY, halfWidth, colors)

    // a blanket draped over the lower half
    val blanket = Path().apply {
        moveTo(d(cx - halfWidth * 0.95f), d(bottomY - 14f))
        quadraticTo(d(cx - halfWidth * 0.5f), d(bottomY + 10f), d(cx), d(bottomY + 8f))
        quadraticTo(d(cx + halfWidth * 0.5f), d(bottomY + 10f), d(cx + halfWidth * 0.95f), d(bottomY - 14f))
        quadraticTo(d(cx + halfWidth * 0.6f), d(bottomY - 32f), d(cx), d(bottomY - 28f))
        quadraticTo(d(cx - halfWidth * 0.6f), d(bottomY - 32f), d(cx - halfWidth * 0.95f), d(bottomY - 14f))
        close()
    }
    paint(blanket, vBrush(bottomY - 32f, bottomY + 10f, colors.fabric.lit(0.15f), colors.fabricDark), colors.ink, 1.8f)
    sketchLine(
        pt(cx - halfWidth * 0.7f, bottomY - 12f),
        pt(cx + halfWidth * 0.7f, bottomY - 10f),
        colors.inkSoft,
        1.4f
    )
    val trim = Path().apply {
        moveTo(d(cx - halfWidth * 0.9f), d(bottomY - 20f))
        quadraticTo(d(cx), d(bottomY - 34f), d(cx + halfWidth * 0.9f), d(bottomY - 20f))
    }
    stroke(trim, colors.touch(colors.accentBlue), 1.6f)

    // a little foot peeking out from under the blanket
    pandaLeg(cx + 34f, bottomY - 6f, cx + 50f, bottomY + 4f, colors, controlX = cx + 44f, controlY = bottomY, thickness = 13f)

    // head, tucked to one side, resting
    val headCx = cx - 46f
    val headCy = topY - 6f
    pandaHead(headCx, headCy, 34f, colors, tilt = -16f, expression = PandaExpression.Sleepy, blink = 1f)

    // a paw draped over the brow, sleeping-mask style
    pandaArm(
        cx - 20f, topY + 4f, headCx - 8f, headCy - 20f, colors,
        controlX = headCx + 12f, controlY = headCy - 26f, thickness = 11f
    )

    // a cascading "Zzz" drifting up from beside the head
    driftingZ(headCx + 4f, headCy - 44f, t, 0f, colors.inkSoft, 11f)
    driftingZ(headCx + 16f, headCy - 58f, t, 0.33f, colors.inkSoft, 14f)
    driftingZ(headCx + 30f, headCy - 74f, t, 0.66f, colors.inkSoft, 17f)

    twinkle(220f, 90f, 3f, t, 0.4f, colors.touch(colors.sun.a(0.8f)))
    twinkle(60f, 150f, 3f, t, 0.7f, colors.touch(colors.leaf))
}

// ─── A Panda's Rainy Errand ───────────────────────────────────────────────────
//   Panda standing under a little umbrella, rain streaking down at a dozen
//   different speeds, a puddle rippling underfoot at the edge of the path.

internal fun DrawScope.drawPandaRainyDayScene(t: Float, colors: SketchyStyle) {
    // ── Background: overcast clouds ──────────────────────────────────────
    listOf(Triple(80f, 46f, 26f), Triple(108f, 40f, 32f), Triple(136f, 48f, 24f)).forEach { (cx, cy, r) ->
        paintCircle(pt(cx, cy), r, colors.metal.a(0.35f), colors.lineOnly, 1.2f)
    }
    listOf(Triple(216f, 58f, 22f), Triple(242f, 52f, 28f), Triple(266f, 60f, 20f)).forEach { (cx, cy, r) ->
        paintCircle(pt(cx, cy), r, colors.metal.a(0.3f), colors.lineOnly, 1.2f)
    }

    // ── Rain, falling at different speeds across the whole scene ─────────
    for (i in 0..13) {
        val x = 40f + 240f * hash01(i)
        val speed = 1.3f + 1.2f * hash01(i + 50)
        val len = 16f + 10f * hash01(i + 90)
        val p = loop(t * speed, hash01(i + 130))
        val y0 = 26f + 210f * p - len
        val streak = Path().apply {
            moveTo(d(x), d(y0))
            lineTo(d(x - 2f), d(y0 + len))
        }
        brushStroke(
            streak,
            vBrush(y0, y0 + len, colors.hint(colors.skyDeep).a(0f), colors.hint(colors.skyDeep).a(0.55f)),
            width = 1f + 0.6f * hash01(i + 160)
        )
    }

    // ── Puddle underfoot, with ripples spreading and fading ──────────────
    contactShadow(160f, 270f, 46f, 9f, colors.shadeSoft)
    val puddle = ellipsePath(160f, 270f, 54f, 12f)
    paint(puddle, vBrush(258f, 276f, colors.sky, colors.skyDeep), colors.line(colors.skyDeep), 1.6f)
    sheen(puddle, pt(122f, 262f), pt(198f, 274f), colors.paper.a(0.3f))
    for (i in 0..1) {
        val phase = loop(t * 0.6f, i * 0.5f)
        val rx = 8f + 34f * phase
        val ring = ellipsePath(160f, 270f, rx, rx * 0.26f)
        stroke(ring, colors.hint(colors.skyDeep).a(0.4f * (1f - phase)), 1.4f)
    }
    groundHint(284f, colors.inkFaint)

    // ── Foreground hero: panda sheltering under an umbrella ──────────────
    val cx = 150f
    val footShift = 2.5f * wave(t, 0.4f)
    pandaLeg(cx - 16f, 238f, cx - 20f + footShift, 258f, colors, controlX = cx - 18f, controlY = 250f, thickness = 15f)
    pandaLeg(cx + 16f, 238f, cx + 20f + footShift, 258f, colors, controlX = cx + 18f, controlY = 250f, thickness = 15f)
    pandaBody(cx, 195f, 250f, 44f, colors)
    pandaArm(cx - 34f, 204f, cx - 40f, 232f, colors, controlX = cx - 40f, controlY = 218f, thickness = 11f)
    pandaArm(cx + 30f, 202f, cx + 46f, 178f, colors, controlX = cx + 42f, controlY = 190f, thickness = 11f)

    // umbrella, held up in the raised paw
    val poleX = cx + 46f
    val pole = Path().apply {
        moveTo(d(poleX), d(178f))
        lineTo(d(poleX), d(122f))
    }
    limb(pole, colors.wood, colors.ink, 1.8f, thickness = 5f)
    val domeCx = poleX
    val domeCy = 116f
    val rx = 52f
    val ry = 34f
    val dome = Path().apply {
        moveTo(d(domeCx - rx), d(domeCy))
        quadraticTo(d(domeCx - rx * 0.6f), d(domeCy - ry * 1.4f), d(domeCx), d(domeCy - ry * 1.45f))
        quadraticTo(d(domeCx + rx * 0.6f), d(domeCy - ry * 1.4f), d(domeCx + rx), d(domeCy))
        quadraticTo(d(domeCx + rx * 0.75f), d(domeCy + 9f), d(domeCx + rx * 0.5f), d(domeCy))
        quadraticTo(d(domeCx + rx * 0.25f), d(domeCy + 9f), d(domeCx), d(domeCy))
        quadraticTo(d(domeCx - rx * 0.25f), d(domeCy + 9f), d(domeCx - rx * 0.5f), d(domeCy))
        quadraticTo(d(domeCx - rx * 0.75f), d(domeCy + 9f), d(domeCx - rx), d(domeCy))
        close()
    }
    paint(dome, vBrush(domeCy - ry * 1.45f, domeCy, colors.fabric.lit(0.15f), colors.fabricDark), colors.ink, 2f)
    for (rib in -2..2) {
        val ribX = domeCx + rib * rx * 0.4f
        sketchLine(pt(domeCx, domeCy - ry * 1.3f), pt(ribX, domeCy - 2f), colors.hint(colors.fabricDark.a(0.6f)), 1.2f)
    }
    sketchLine(pt(domeCx, domeCy - ry * 1.45f), pt(domeCx, domeCy - ry * 1.45f - 8f), colors.ink, 1.6f)

    pandaHead(
        cx, 165f, 36f, colors,
        tilt = 3f * wave(t, 0.15f),
        expression = PandaExpression.Content,
        blink = pandaAutoBlink(t, 0.5f)
    )

    twinkle(210f, 96f, 3f, t, 0.3f, colors.touch(colors.sky))
    twinkle(60f, 200f, 3f, t, 0.6f, colors.touch(colors.skyDeep))
}

// ─── A Panda's Bath Time ──────────────────────────────────────────────────────
//   Panda soaking in a little wooden tub, bubbles drifting up and popping,
//   a folded towel and a plant on the shelf behind, thoroughly content.

internal fun DrawScope.drawPandaBathTimeScene(t: Float, colors: SketchyStyle) {
    // ── Background: shelf with a towel + plant, a small round window ────
    val shelf = roundRectPath(40f, 66f, 72f, 9f, 4f)
    paint(shelf, vBrush(66f, 75f, colors.wood, colors.woodDark), colors.ink, 1.6f)
    val towel = roundRectPath(48f, 42f, 44f, 26f, 8f)
    paint(towel, vBrush(42f, 68f, colors.fabric.lit(0.2f), colors.fabricDark), colors.ink, 1.6f)
    sketchLine(pt(52f, 54f), pt(88f, 54f), colors.inkSoft, 1.2f)
    val pot = Path().apply {
        moveTo(d(96f), d(50f))
        lineTo(d(116f), d(50f))
        lineTo(d(113f), d(66f))
        lineTo(d(99f), d(66f))
        close()
    }
    paint(pot, vBrush(50f, 66f, colors.terracotta, colors.clay), colors.ink, 1.4f)
    listOf(-8f, 0f, 8f).forEach { off ->
        val leaf = Path().apply {
            moveTo(d(106f), d(50f))
            quadraticTo(d(106f + off), d(34f), d(106f + off * 1.6f), d(26f))
            quadraticTo(d(106f + off * 0.6f), d(38f), d(106f), d(50f))
            close()
        }
        paint(leaf, colors.leaf, colors.inkOf(colors.leaf), 1.2f)
    }

    paintCircle(pt(252f, 58f), 28f, colors.sky, colors.ink, 1.6f)
    glow(252f, 58f, 42f, colors.glow.a(0.6f))
    sketchLine(pt(252f, 34f), pt(252f, 82f), colors.ink, 1.4f)
    sketchLine(pt(228f, 58f), pt(276f, 58f), colors.ink, 1.4f)

    // ── Midground: the tub ────────────────────────────────────────────────
    contactShadow(160f, 278f, 60f, 12f, colors.shade)
    val tub = Path().apply {
        moveTo(d(160f - 68f), d(222f))
        quadraticTo(d(160f - 74f), d(248f), d(160f - 52f), d(270f))
        quadraticTo(d(160f), d(278f), d(160f + 52f), d(270f))
        quadraticTo(d(160f + 74f), d(248f), d(160f + 68f), d(222f))
        close()
    }
    paint(tub, vBrush(222f, 270f, colors.wood.lit(0.1f), colors.woodDark), colors.ink, 1.8f)
    shade(tub, hBrush(160f, 232f, colors.shade.a(0f), colors.shade))
    listOf(240f to 62f, 258f to 56f).forEach { (hy, hrx) ->
        stroke(ellipsePath(160f, hy, hrx, 7f), colors.faint(colors.metalDark.a(0.7f)), 1.6f)
    }

    val water = ellipsePath(160f, 222f, 62f, 16f)
    paint(water, vBrush(210f, 222f, colors.sky, colors.skyDeep), colors.line(colors.skyDeep), 1.4f)

    // ── Foreground hero: the panda, soaking contentedly ──────────────────
    pandaBody(160f, 182f, 222f, 42f, colors)
    pandaArm(122f, 196f, 100f, 214f, colors, controlX = 108f, controlY = 200f, thickness = 11f)
    pandaArm(198f, 196f, 220f, 214f, colors, controlX = 212f, controlY = 200f, thickness = 11f)

    // the tub's near lip, capping the join between the body and the water
    val rim = ellipsePath(160f, 222f, 68f, 13f)
    paint(rim, colors.wood, colors.ink, 1.8f)
    val waterFront = ellipsePath(160f, 225f, 58f, 7f)
    paint(waterFront, vBrush(218f, 225f, colors.sky, colors.skyDeep), colors.line(colors.skyDeep), 1.2f)

    // bubbles rising off the water and popping near the top of their climb
    for (i in 0..6) {
        val bx = 112f + 96f * hash01(i)
        val phase = loop(t, hash01(i + 20))
        val by = 220f - phase * 70f - 20f * hash01(i + 5)
        val size = 4f + 5f * hash01(i + 10)
        val pop = ((1f - (phase - 0.85f) / 0.15f).coerceIn(0f, 1f)).let { if (phase > 0.85f) it else 1f }
        paintCircle(pt(bx, by), size * pop, colors.paper.a(0.5f), colors.inkSoft.a(0.6f * pop), 1.2f)
    }
    // a little suds cap on the head
    paintCircle(pt(160f, 132f), 9f, colors.paper.a(0.85f), colors.inkSoft, 1.4f)
    paintCircle(pt(150f, 125f), 3.5f, colors.paper.a(0.8f), colors.inkSoft, 1.2f)
    paintCircle(pt(172f, 127f), 4.5f, colors.paper.a(0.8f), colors.inkSoft, 1.2f)

    pandaHead(
        160f, 166f, 36f, colors,
        tilt = 3f * wave(t, 0.15f),
        expression = PandaExpression.Delighted,
        blink = pandaAutoBlink(t, 0.3f)
    )

    twinkle(238f, 86f, 3f, t, 0.3f, colors.touch(colors.sky))
    twinkle(88f, 150f, 3f, t, 0.6f, colors.touch(colors.paper.a(0.9f)))
}
