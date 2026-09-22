package com.sketchy.library.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.sketchy.library.SketchyStyle

/**
 * Shared plant-drawing primitives — pots, cacti, succulents, foliage — used by both the
 * "Plants and Things" empty states (`emptystates/EmptyStatesPlants.kt`) and the desert
 * illustration (`illustrations/PlantsAndThings.kt`), so the same hand-drawn plant vocabulary
 * reads consistently across both catalogs.
 */

/** A tapered terracotta pot: wider rim, narrower base, with a raised lip. */
internal fun DrawScope.drawPot(
    cx: Float,
    rimY: Float,
    rimW: Float,
    baseW: Float,
    h: Float,
    colors: SketchyStyle
) {
    contactShadow(cx, rimY + h + 8f, rimW * 0.6f, 7f, colors.shade)

    val body = Path().apply {
        moveTo(d(cx - rimW / 2f), d(rimY))
        lineTo(d(cx - baseW / 2f), d(rimY + h))
        quadraticTo(d(cx), d(rimY + h + 5f), d(cx + baseW / 2f), d(rimY + h))
        lineTo(d(cx + rimW / 2f), d(rimY))
        close()
    }
    inkShadow(body, colors.outlineShadow)
    cornerShade(body, cx, rimY + h / 2f, h * 0.75f, colors.outlineShadow.a(colors.outlineShadow.alpha * 0.6f))
    paint(body, vBrush(rimY, rimY + h, colors.terracotta.lit(0.15f), colors.clay), colors.ink, 2.4f)
    shade(body, hBrush(cx - rimW / 2f, cx + rimW / 2f, colors.shade.a(0f), colors.shade))
    sheen(body, pt(cx - rimW * 0.3f, rimY + 8f), pt(cx, rimY + h * 0.6f), colors.paper.a(0.25f))

    val rim = ellipsePath(cx, rimY, rimW / 2f, rimW * 0.12f)
    paint(rim, colors.clay, colors.ink, 2.2f)
}

/** Bare or planted soil sitting just inside a pot's rim. */
internal fun DrawScope.drawSoil(cx: Float, rimY: Float, rimW: Float, colors: SketchyStyle) {
    val soil = ellipsePath(cx, rimY + 2f, rimW / 2f - 6f, rimW * 0.09f)
    paint(soil, colors.woodDark, colors.ink, 1.8f)
}

/** One rounded, ribbed cactus column (a trunk or an arm) from [botY] up to a domed top at [topY]. */
private fun DrawScope.cactusColumn(
    cx: Float,
    topY: Float,
    botY: Float,
    w: Float,
    colors: SketchyStyle,
    ribs: Int = 6
) {
    val r = w / 2f
    val body = Path().apply {
        moveTo(d(cx - r), d(botY))
        lineTo(d(cx - r), d(topY + r))
        quadraticTo(d(cx - r), d(topY), d(cx), d(topY))
        quadraticTo(d(cx + r), d(topY), d(cx + r), d(topY + r))
        lineTo(d(cx + r), d(botY))
        close()
    }
    paint(body, vBrush(topY, botY, colors.leaf.lit(0.1f), colors.leafDark), colors.ink, 2.4f)
    shade(body, hBrush(cx - r, cx + r, colors.shade.a(0f), colors.shade.a(colors.shade.alpha * 0.5f)))
    sheen(body, pt(cx - r * 0.5f, topY + r), pt(cx - r * 0.1f, botY - r), colors.paper.a(0.16f))

    for (i in 0 until ribs) {
        val fx = cx - r + w * (i + 0.5f) / ribs
        sketchLine(pt(fx, topY + r * 0.6f), pt(fx, botY - 2f), colors.leafDark.a(0.55f), 1.4f)
        var sy = topY + r * 1.4f
        while (sy < botY - 6f) {
            sketchCircle(pt(fx, sy), 0.9f, colors.faint(colors.ink), filled = true)
            sy += 12f
        }
    }
}

/**
 * A saguaro-style cactus: a tall ribbed trunk with one or two arms branching partway up, each
 * curving out and then straight up — the desert scene's hero shape, and a compact stand-in
 * wherever an empty state wants a real, unmistakable cactus.
 */
internal fun DrawScope.drawSaguaroCactus(
    cx: Float,
    baseY: Float,
    height: Float,
    colors: SketchyStyle,
    trunkWidth: Float = height * 0.22f,
    arms: Int = 2,
    bloom: Boolean = false
) {
    contactShadow(cx, baseY + 4f, trunkWidth * 1.6f, 6f, colors.shade)

    val trunkTop = baseY - height
    val armW = trunkWidth * 0.55f

    for (i in 0 until arms) {
        val side = if (i % 2 == 0) 1f else -1f
        val branchY = trunkTop + height * (0.34f + i * 0.2f)
        val armTopY = branchY - height * 0.3f
        val outX = cx + side * (trunkWidth * 0.6f + armW)

        val elbow = Path().apply {
            moveTo(d(cx + side * trunkWidth * 0.35f), d(branchY + armW * 0.3f))
            cubicTo(
                d(cx + side * trunkWidth * 1.1f), d(branchY),
                d(outX), d(branchY - armW * 0.3f),
                d(outX), d(armTopY + armW * 0.7f)
            )
        }
        limb(elbow, colors.leafDark, colors.ink, 2.2f, thickness = armW * 1.3f)
        cactusColumn(outX, armTopY, branchY, armW, colors, ribs = 4)
    }

    // drawn after the arm connectors so the trunk's silhouette stays clean where they meet
    cactusColumn(cx, trunkTop, baseY, trunkWidth, colors)

    if (bloom) {
        val bloomColor = colors.touch(colors.accentRed, 0.9f)
        val bloomCy = trunkTop - trunkWidth * 0.05f
        for (i in 0 until 6) {
            val a = i * 60f * (kotlin.math.PI / 180.0).toFloat()
            val px = cx + trunkWidth * 0.3f * kotlin.math.cos(a)
            val py = bloomCy + trunkWidth * 0.3f * kotlin.math.sin(a)
            paintCircle(pt(px, py), trunkWidth * 0.16f, bloomColor, colors.ink, 1.4f)
        }
        paintCircle(pt(cx, bloomCy), trunkWidth * 0.13f, colors.sun, colors.ink, 1.4f)
    }
}

/** A squat, round barrel cactus — compact enough to sit in a small pot. */
internal fun DrawScope.drawBarrelCactus(
    cx: Float,
    baseY: Float,
    w: Float,
    h: Float,
    colors: SketchyStyle,
    bloom: Boolean = false
) {
    val body = Path().apply {
        moveTo(d(cx - w / 2f), d(baseY))
        quadraticTo(d(cx - w / 2f), d(baseY - h), d(cx), d(baseY - h))
        quadraticTo(d(cx + w / 2f), d(baseY - h), d(cx + w / 2f), d(baseY))
        close()
    }
    inkShadow(body, colors.outlineShadow)
    paint(body, vBrush(baseY - h, baseY, colors.leaf.lit(0.12f), colors.leafDark), colors.ink, 2.2f)
    sheen(body, pt(cx - w * 0.28f, baseY - h * 0.8f), pt(cx, baseY - h * 0.2f), colors.paper.a(0.18f))

    val ribs = 6
    for (i in 0 until ribs) {
        val fx = cx - w / 2f + w * (i + 0.5f) / ribs
        sketchLine(pt(fx, baseY - 2f), pt(fx, baseY - h * 0.85f), colors.leafDark.a(0.5f), 1.3f)
    }

    if (bloom) {
        paintCircle(pt(cx, baseY - h - 4f), w * 0.16f, colors.touch(colors.accent, 0.9f), colors.ink, 1.4f)
    }
}

/** A rosette succulent — thick pointed leaves radiating out from a low center, echeveria-style. */
internal fun DrawScope.drawSucculentRosette(
    cx: Float,
    baseY: Float,
    colors: SketchyStyle,
    scale: Float = 1f,
    leafColor: Color = colors.leaf
) {
    val rx = 24f * scale
    val ry = rx * 0.4f

    fun ring(count: Int, ringScale: Float, lift: Float) {
        for (i in 0 until count) {
            val a = (i.toFloat() / count) * 2f * kotlin.math.PI.toFloat()
            val ex = cx + rx * ringScale * kotlin.math.cos(a)
            val ey = baseY - lift - ry * ringScale * kotlin.math.sin(a) * 0.6f
            val midX = cx + (ex - cx) * 0.5f
            val midY = baseY - lift + (ey - (baseY - lift)) * 0.5f
            val perpX = -(ey - (baseY - lift)) * 0.4f
            val perpY = (ex - cx) * 0.4f
            val leaf = Path().apply {
                moveTo(d(cx), d(baseY - lift))
                quadraticTo(d(midX + perpX), d(midY + perpY), d(ex), d(ey))
                quadraticTo(d(midX - perpX), d(midY - perpY), d(cx), d(baseY - lift))
                close()
            }
            val tone = if (i % 2 == 0) leafColor else colors.leafDark
            paint(leaf, tone, colors.ink, 1.5f)
        }
    }
    ring(8, 1f, 0f)
    ring(6, 0.55f, 3f)
    paintCircle(pt(cx, baseY - ry * 0.5f), rx * 0.14f, colors.leafDark, colors.ink, 1.3f)
}

/**
 * A fuller foliage plant: several curved fronds radiating up from a base, each carrying a couple
 * of small leaflets — real, layered greenery rather than a bare stem-and-two-leaves sprig.
 */
internal fun DrawScope.drawFoliagePlant(
    cx: Float,
    baseY: Float,
    colors: SketchyStyle,
    height: Float = 60f,
    fronds: Int = 6,
    leafColor: Color = colors.leaf
) {
    for (i in 0 until fronds) {
        val f = if (fronds > 1) i.toFloat() / (fronds - 1) else 0.5f
        val angle = (-70f + 140f * f) * (kotlin.math.PI / 180.0).toFloat()
        val len = height * (0.7f + 0.3f * kotlin.math.sin(f * kotlin.math.PI.toFloat()))
        val tipX = cx + len * kotlin.math.sin(angle)
        val tipY = baseY - len * kotlin.math.cos(angle)
        val ctrlX = cx + len * 0.65f * kotlin.math.sin(angle)
        val ctrlY = baseY - len * 0.65f * kotlin.math.cos(angle)
        val frond = Path().apply {
            moveTo(d(cx), d(baseY))
            quadraticTo(d(ctrlX), d(ctrlY), d(tipX), d(tipY))
        }
        limb(frond, colors.leafDark, colors.ink, 1.8f, thickness = 3f)

        listOf(0.5f, 0.82f).forEach { along ->
            val fx = cx + len * along * kotlin.math.sin(angle)
            val fy = baseY - len * along * kotlin.math.cos(angle)
            val tone = if (i % 2 == 0) leafColor else colors.leafDark
            paintCircle(pt(fx, fy), 4f, tone, colors.ink, 1.2f)
        }
    }
}
