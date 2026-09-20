package com.sketchy.library.emptystates

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import com.sketchy.library.SketchyStyle
import com.sketchy.library.utils.*

/**
 * The **Plants and Things** category: every scene here centers on real, full plants — cacti,
 * succulents, foliage — potted or growing in the ground, alongside garden props like a plant
 * stand, a wheelbarrow, a mailbox planter or a greenhouse. Every scene carries at least one
 * unmistakable plant; none lean on a bare tool (a magnifying glass, a watering can) to carry
 * the concept on its own.
 */

// ─── No Data ──────────────────────────────────────────────────────────────────
//   A big empty terracotta pot takes center stage on a shelf, dry and cracked —
//   flanked by two real potted plants that are very much not empty.

internal fun DrawScope.drawPlantsNoData(t: Float, colors: SketchyStyle) {
    val cx = 160f
    val shelfY = 214f
    val shelfW = 224f

    val shelf = roundRectPath(cx - shelfW / 2f, shelfY, shelfW, 16f, 4f)
    paint(shelf, colors.wood, colors.ink, 2.2f)
    shade(shelf, hBrush(cx - shelfW / 2f, cx + shelfW / 2f, colors.shade.a(0f), colors.shade))

    val sideH = 30f
    drawPot(cx - 88f, shelfY - sideH, 42f, 32f, sideH, colors)
    drawBarrelCactus(cx - 88f, shelfY - sideH, 30f, 26f, colors)
    drawPot(cx + 90f, shelfY - sideH + 4f, 40f, 30f, sideH - 4f, colors)
    drawSucculentRosette(cx + 90f, shelfY - sideH + 4f, colors, scale = 0.85f)

    drawPot(cx, shelfY - 76f, 96f, 68f, 76f, colors)
    drawSoil(cx, shelfY - 76f, 96f, colors)
    sketchLine(pt(cx - 16f, shelfY - 75f), pt(cx - 6f, shelfY - 81f), colors.inkFaint, 1.2f)
    sketchLine(pt(cx + 10f, shelfY - 74f), pt(cx + 20f, shelfY - 80f), colors.inkFaint, 1.2f)

    val drift = 3f * wave(t, 0.2f)
    twinkle(cx - 104f, shelfY - 130f + drift, 3f, t, 0.2f, colors.inkSoft)
    twinkle(cx + 112f, shelfY - 156f - drift, 3f, t, 0.6f, colors.inkSoft)
    groundLine(shelfY + 48f, colors.inkFaint)
}

// ─── No Favorites ───────────────────────────────────────────────────────────
//   A tall plant stand with a couple of blank hanging tags — nothing's been
//   favorited yet — standing between two real potted plants on the ground.

internal fun DrawScope.drawPlantsNoFavorites(t: Float, colors: SketchyStyle) {
    val cx = 160f
    val standW = 196f
    val topY = 96f
    val midY = 160f
    val botY = 234f
    val groundY = botY + 44f

    contactShadow(cx, botY + 10f, standW * 0.5f, 8f, colors.shade)

    listOf(-1f, 1f).forEach { side ->
        val leg = Path().apply {
            moveTo(d(cx + side * 16f), d(topY))
            lineTo(d(cx + side * standW / 2f), d(botY))
        }
        limb(leg, colors.wood, colors.ink, 2.2f, thickness = 5.5f)
    }
    listOf(topY + 20f, midY, botY - 12f).forEach { y ->
        val shelfW = standW * (0.4f + (y - topY) / (botY - topY) * 0.6f)
        val plank = roundRectPath(cx - shelfW / 2f, y, shelfW, 9f, 3f)
        paint(plank, colors.wood.lit(0.1f), colors.ink, 2f)
    }

    listOf(-1, 1).forEachIndexed { i, side ->
        val sway = 3f * wave(t, i * 0.4f)
        val hookX = cx + side * 48f
        val hookY = topY - 6f
        val tagY = hookY + 28f
        sketchLine(pt(hookX, hookY), pt(hookX + sway, tagY - 11f), colors.inkFaint, 1.4f)
        val tag = roundRectPath(hookX + sway - 11f, tagY - 11f, 22f, 15f, 3f)
        paint(tag, colors.paper, colors.ink, 1.8f)
    }

    val potH1 = 40f
    drawPot(cx - standW / 2f - 32f, groundY - potH1, 52f, 38f, potH1, colors)
    drawSucculentRosette(cx - standW / 2f - 32f, groundY - potH1, colors, scale = 1f)
    val potH2 = 34f
    drawPot(cx + standW / 2f + 30f, groundY - potH2, 46f, 34f, potH2, colors)
    drawBarrelCactus(cx + standW / 2f + 30f, groundY - potH2, 34f, 30f, colors)

    twinkle(52f, 84f, 3f, t, 0.3f, colors.inkSoft)
    twinkle(266f, 110f, 3f, t, 0.7f, colors.inkSoft)
    groundLine(groundY, colors.inkFaint)
}

// ─── Empty Search ───────────────────────────────────────────────────────────
//   A row of thriving potted plants along a path, with one obvious gap — a
//   faint dashed outline where a pot should be — right in the middle.

internal fun DrawScope.drawPlantsEmptySearch(t: Float, colors: SketchyStyle) {
    val groundY = 254f
    val xs = listOf(56f, 108f, 160f, 212f, 264f)
    val potH = 46f

    xs.forEachIndexed { i, x ->
        if (i == 2) {
            val ghost = ellipsePath(x, groundY, 24f, 6.5f)
            drawPath(ghost, color = colors.inkFaint, style = dashed())
            twinkle(x, groundY - 34f, 3.5f, t, 0.4f, colors.inkSoft)
        } else {
            val rimY = groundY - potH
            drawPot(x, rimY, 44f, 32f, potH, colors)
            drawSoil(x, rimY, 44f, colors)
            when (i) {
                0 -> drawBarrelCactus(x, rimY, 30f, 28f, colors)
                1 -> drawSucculentRosette(x, rimY, colors, scale = 0.85f)
                3 -> drawFoliagePlant(x, rimY, colors, height = 38f, fronds = 5)
                else -> drawBarrelCactus(x, rimY, 28f, 26f, colors, bloom = true)
            }
        }
    }

    twinkle(160f, 76f, 3f, t, 0.6f, colors.inkSoft)
    groundLine(groundY + 4f, colors.inkFaint)
}

// ─── No Tasks ───────────────────────────────────────────────────────────────
//   A row of plant markers, each already checked off beside a fully thriving
//   plant — everything in the bed's already been tended to.

internal fun DrawScope.drawPlantsNoTasks(t: Float, colors: SketchyStyle) {
    val groundY = 240f
    val xs = listOf(66f, 122f, 198f, 254f)

    xs.forEachIndexed { i, x ->
        val sway = 1f * wave(t, i * 0.2f)
        val stake = Path().apply {
            moveTo(d(x), d(groundY))
            lineTo(d(x + sway), d(groundY - 32f))
        }
        limb(stake, colors.wood, colors.ink, 1.6f, thickness = 2.8f)
        val tag = roundRectPath(x + sway - 9f, groundY - 45f, 18f, 14f, 3f)
        paint(tag, colors.paper, colors.ink, 1.6f)
        val check = Path().apply {
            moveTo(d(x + sway - 5f), d(groundY - 38f))
            lineTo(d(x + sway - 1f), d(groundY - 35f))
            lineTo(d(x + sway + 6f), d(groundY - 43f))
        }
        drawPath(check, color = colors.touch(colors.accentGreen, 0.85f), style = bold(1.7f))

        when (i) {
            0 -> drawBarrelCactus(x, groundY, 30f, 28f, colors)
            1 -> drawSucculentRosette(x, groundY, colors, scale = 0.9f)
            2 -> drawFoliagePlant(x, groundY, colors, height = 40f, fronds = 5)
            else -> drawBarrelCactus(x, groundY, 28f, 26f, colors, bloom = true)
        }
    }

    twinkle(70f, 150f, 3f, t, 0.4f, colors.inkSoft)
    groundLine(groundY + 18f, colors.inkFaint)
}

// ─── No Notifications ───────────────────────────────────────────────────────
//   A glass terrarium jar sits quietly on a shelf, a real little plant growing
//   inside it — calm and still, nothing new to hear.

internal fun DrawScope.drawPlantsNoNotifications(t: Float, colors: SketchyStyle) {
    val cx = 160f
    val shelfY = 228f
    val shelfW = 176f

    val shelf = roundRectPath(cx - shelfW / 2f, shelfY, shelfW, 12f, 4f)
    paint(shelf, colors.wood, colors.ink, 2.2f)

    val jarW = 108f
    val jarH = 108f
    val jarX = cx - jarW / 2f
    val jarY = shelfY - jarH

    contactShadow(cx, shelfY + 2f, jarW * 0.5f, 6f, colors.shade)

    val jar = roundRectPath(jarX, jarY, jarW, jarH, 22f)
    inkShadow(jar, colors.outlineShadow)
    paint(jar, colors.sky.a(0.22f), colors.ink, 2.2f)
    sheen(jar, pt(jarX + 12f, jarY + 12f), pt(jarX + 26f, jarY + jarH - 22f), colors.paper.a(0.35f))

    val lid = roundRectPath(cx - jarW * 0.32f, jarY - 11f, jarW * 0.64f, 13f, 4f)
    paint(lid, colors.wood, colors.ink, 1.8f)

    drawSoil(cx, jarY + jarH - 16f, jarW - 26f, colors)
    drawFoliagePlant(cx, jarY + jarH - 16f, colors, height = 50f, fronds = 5)
    drawSucculentRosette(cx - 24f, jarY + jarH - 16f, colors, scale = 0.55f)

    val breathe = pulse(t, 0f)
    twinkle(cx - 68f, jarY - 22f, 2.5f, t, 0.5f, colors.touch(colors.sun, 0.35f + 0.3f * breathe))
    twinkle(cx + 72f, jarY + 8f, 2.5f, t, 0.8f, colors.touch(colors.sun, 0.3f))
    groundLine(shelfY + 42f, colors.inkFaint)
}

// ─── All Caught Up ──────────────────────────────────────────────────────────
//   A fully bloomed potted plant beside a blooming cactus — a whole little
//   garden that's already been completely tended to.

internal fun DrawScope.drawPlantsAllCaughtUp(t: Float, colors: SketchyStyle) {
    val groundY = 250f

    val potH1 = 70f
    val rim1 = groundY - potH1
    drawPot(114f, rim1, 88f, 62f, potH1, colors)
    drawSoil(114f, rim1, 88f, colors)
    val sway = 2f * wave(t, 0f)
    drawFoliagePlant(114f + sway * 0.3f, rim1, colors, height = 70f, fronds = 7)

    val bloomCx = 114f + sway * 0.3f
    val bloomCy = rim1 - 74f
    val petalColor = colors.touch(colors.accentRed, 0.9f)
    for (i in 0 until 5) {
        val a = i * 72f * (kotlin.math.PI / 180.0).toFloat()
        val px = bloomCx + 9f * kotlin.math.cos(a)
        val py = bloomCy + 9f * kotlin.math.sin(a)
        paintCircle(pt(px, py), 6.5f, petalColor, colors.ink, 1.5f)
    }
    paintCircle(pt(bloomCx, bloomCy), 5.5f, colors.sun, colors.ink, 1.5f)

    val potH2 = 46f
    val rim2 = groundY - potH2
    drawPot(224f, rim2, 60f, 42f, potH2, colors)
    drawSaguaroCactus(224f, rim2, 84f, colors, trunkWidth = 22f, arms = 1, bloom = true)

    twinkle(60f, 100f, 4f, t, 0.2f, colors.touch(colors.sun))
    twinkle(270f, 80f, 3f, t, 0.6f, colors.touch(colors.accentGreen, 0.8f))
    groundLine(groundY + 14f, colors.inkFaint)
}

// ─── No Messages ────────────────────────────────────────────────────────────
//   A mailbox-shaped planter, a real flowering plant spilling over its edge,
//   a touch overgrown — the flag's down, nothing's arrived.

internal fun DrawScope.drawPlantsNoMessages(t: Float, colors: SketchyStyle) {
    val cx = 160f
    val postY = 138f
    val postH = 100f

    contactShadow(cx, postY + postH + 8f, 32f, 6f, colors.shade)
    val post = roundRectPath(cx - 6f, postY, 12f, postH, 3f)
    paint(post, colors.wood, colors.ink, 2.2f)

    val boxW = 88f
    val boxH = 56f
    val boxX = cx - boxW / 2f
    val boxY = postY - boxH + 8f
    val box = Path().apply {
        moveTo(d(boxX), d(boxY + boxH))
        lineTo(d(boxX), d(boxY + boxH * 0.4f))
        quadraticTo(d(boxX), d(boxY), d(cx), d(boxY))
        quadraticTo(d(boxX + boxW), d(boxY), d(boxX + boxW), d(boxY + boxH * 0.4f))
        lineTo(d(boxX + boxW), d(boxY + boxH))
        close()
    }
    inkShadow(box, colors.outlineShadow)
    paint(box, vBrush(boxY, boxY + boxH, colors.terracotta.lit(0.1f), colors.clay), colors.ink, 2.2f)
    sheen(box, pt(boxX + 10f, boxY + 8f), pt(boxX + boxW - 10f, boxY + boxH * 0.5f), colors.paper.a(0.25f))

    val opening = ellipsePath(cx, boxY + 5f, boxW * 0.32f, 7f)
    paint(opening, colors.woodDark, colors.ink, 1.6f)

    drawFoliagePlant(cx, boxY + 3f, colors, height = 42f, fronds = 5)

    val droop = 8f + 3f * wave(t, 0f)
    val stem = Path().apply {
        moveTo(d(cx), d(boxY))
        quadraticTo(d(cx + 24f), d(boxY - 10f), d(cx + 32f), d(boxY + droop))
    }
    limb(stem, colors.leafDark, colors.ink, 1.8f, thickness = 2.8f)
    val petalColor = colors.touch(colors.accent, 0.85f)
    for (i in 0 until 5) {
        val a = i * 72f * (kotlin.math.PI / 180.0).toFloat()
        val px = cx + 32f + 6.5f * kotlin.math.cos(a)
        val py = boxY + droop + 6.5f * kotlin.math.sin(a)
        paintCircle(pt(px, py), 5f, petalColor, colors.ink, 1.3f)
    }

    val flag = roundRectPath(cx + 10f, postY + 10f, 11f, 15f, 2f)
    paint(flag, colors.accentRed.a(0.7f), colors.ink, 1.6f)

    twinkle(78f, 120f, 3f, t, 0.3f, colors.inkSoft)
    twinkle(248f, 152f, 3f, t, 0.7f, colors.inkSoft)
    groundLine(postY + postH + 32f, colors.inkFaint)
}

// ─── Empty Cart ─────────────────────────────────────────────────────────────
//   An empty wheelbarrow — nothing loaded in its tray — parked between two
//   real potted plants waiting on the ground to be moved.

internal fun DrawScope.drawPlantsEmptyCart(t: Float, colors: SketchyStyle) {
    val cx = 160f
    val trayY = 158f
    val trayW = 156f
    val trayH = 48f
    val groundY = trayY + trayH + 46f

    contactShadow(cx, trayY + trayH + 32f, 76f, 8f, colors.shade)

    val wheelCx = cx - trayW * 0.28f
    val wheelCy = trayY + trayH + 22f
    val wheelPivot = pt(wheelCx, wheelCy)
    sketchCircle(wheelPivot, 17f, colors.metalDark, width = 2.4f)
    withTransform({ rotate(degrees = (t * 720f) % 360f, pivot = wheelPivot) }) {
        sketchLine(pt(wheelCx - 17f, wheelCy), pt(wheelCx + 17f, wheelCy), colors.inkFaint, 1.4f)
        sketchLine(pt(wheelCx, wheelCy - 17f), pt(wheelCx, wheelCy + 17f), colors.inkFaint, 1.4f)
    }
    paintCircle(wheelPivot, 4.2f, colors.metal, colors.ink, 1.6f)

    val legY = trayY + trayH + 6f
    sketchLine(pt(cx + trayW * 0.3f, legY), pt(cx + trayW * 0.3f, legY + 24f), colors.wood, 4.2f)
    sketchLine(pt(cx + trayW * 0.15f, legY), pt(cx + trayW * 0.15f, legY + 24f), colors.wood, 4.2f)

    val tray = Path().apply {
        moveTo(d(cx - trayW / 2f), d(trayY))
        lineTo(d(cx - trayW * 0.36f), d(trayY + trayH))
        lineTo(d(cx + trayW * 0.36f), d(trayY + trayH))
        lineTo(d(cx + trayW / 2f), d(trayY))
        close()
    }
    inkShadow(tray, colors.outlineShadow)
    paint(tray, vBrush(trayY, trayY + trayH, colors.metal.lit(0.2f), colors.metalDark), colors.ink, 2.4f)
    sheen(tray, pt(cx - trayW * 0.3f, trayY + 4f), pt(cx, trayY + trayH * 0.6f), colors.paper.a(0.25f))

    listOf(-1f, 1f).forEach { side ->
        val handle = Path().apply {
            moveTo(d(cx + side * trayW * 0.42f), d(trayY + 6f))
            lineTo(d(cx + side * (trayW * 0.42f + 50f)), d(trayY - 8f))
        }
        limb(handle, colors.wood, colors.ink, 2f, thickness = 4.2f)
    }

    val potH = 34f
    drawPot(cx - trayW / 2f - 34f, groundY - potH, 46f, 34f, potH, colors)
    drawSucculentRosette(cx - trayW / 2f - 34f, groundY - potH, colors, scale = 0.9f)
    drawPot(cx + trayW / 2f + 32f, groundY - potH, 44f, 32f, potH, colors)
    drawBarrelCactus(cx + trayW / 2f + 32f, groundY - potH, 32f, 28f, colors)

    twinkle(cx, 96f, 3f, t, 0.3f, colors.inkSoft)
    groundLine(groundY, colors.inkFaint)
}

// ─── Welcome ────────────────────────────────────────────────────────────────
//   A single seed loops down into a big fresh pot of soil, flanked by a real
//   succulent and a young cactus — the very first thing planted here.

internal fun DrawScope.drawPlantsWelcome(t: Float, colors: SketchyStyle) {
    val cx = 160f
    val rimY = 208f

    drawPot(cx, rimY, 100f, 70f, 76f, colors)
    drawSoil(cx, rimY, 100f, colors)
    sketchCircle(pt(cx, rimY - 2f), 5.5f, colors.inkFaint, width = 1.4f)

    val phase = loop(t, 0f)
    val dropY = rimY - 108f + phase * 104f
    if (phase < 0.92f) {
        paintCircle(pt(cx, dropY), 5.5f, colors.woodDark, colors.ink, 1.6f)
        twinkle(cx + 18f, dropY - 12f, 2.5f, t, 0f, colors.touch(colors.sun, 0.7f))
    }

    drawSucculentRosette(cx - 80f, rimY + 8f, colors, scale = 1.05f)
    drawBarrelCactus(cx + 78f, rimY + 6f, 36f, 32f, colors)

    twinkle(cx - 30f, 90f, 3f, t, 0.3f, colors.touch(colors.sun, 0.8f))
    twinkle(cx + 40f, 78f, 3f, t, 0.7f, colors.touch(colors.accentGreen, 0.7f))
    groundLine(282f, colors.inkFaint)
}

// ─── Maintenance ────────────────────────────────────────────────────────────
//   A greenhouse door shut for the day, real plants visible growing behind
//   its glass, a rake resting outside beside a potted cactus.

internal fun DrawScope.drawPlantsMaintenance(t: Float, colors: SketchyStyle) {
    val cx = 160f
    val baseY = 226f
    val houseW = 188f
    val wallH = 68f
    val roofH = 52f

    contactShadow(cx, baseY + 8f, houseW * 0.55f, 8f, colors.shade)

    val houseX = cx - houseW / 2f
    val roofY = baseY - wallH - roofH

    val house = Path().apply {
        moveTo(d(houseX), d(baseY))
        lineTo(d(houseX), d(baseY - wallH))
        lineTo(d(cx), d(roofY))
        lineTo(d(houseX + houseW), d(baseY - wallH))
        lineTo(d(houseX + houseW), d(baseY))
        close()
    }
    inkShadow(house, colors.outlineShadow)
    paint(house, vBrush(roofY, baseY, colors.sky.a(0.3f), colors.metal.a(0.25f)), colors.ink, 2.4f)

    drawFoliagePlant(cx - houseW * 0.22f, baseY - 4f, colors, height = 42f, fronds = 5)
    drawSaguaroCactus(cx + houseW * 0.2f, baseY - 4f, 58f, colors, trunkWidth = 15f, arms = 1)

    for (i in 1 until 4) {
        val x = houseX + houseW * i / 4f
        sketchLine(pt(x, baseY - wallH), pt(x, baseY), colors.inkFaint, 1.4f)
    }
    sketchLine(pt(houseX, baseY - wallH), pt(houseX + houseW, baseY - wallH), colors.inkFaint, 1.4f)
    sketchLine(pt(cx - houseW * 0.25f, roofY + roofH * 0.5f), pt(cx, roofY), colors.inkFaint, 1.2f)
    sketchLine(pt(cx + houseW * 0.25f, roofY + roofH * 0.5f), pt(cx, roofY), colors.inkFaint, 1.2f)

    val doorW = 40f
    val door = roundRectPath(cx - doorW / 2f, baseY - wallH + 8f, doorW, wallH - 8f, 4f)
    paint(door, colors.wood.a(0.55f), colors.ink, 2f)
    sketchLine(pt(cx - doorW / 2f + 4f, baseY - 20f), pt(cx + doorW / 2f - 4f, baseY - 20f), colors.ink, 2.4f)

    val leanX = houseX - 30f
    val rake = Path().apply {
        moveTo(d(leanX), d(baseY))
        lineTo(d(leanX + 10f), d(baseY - 82f))
    }
    limb(rake, colors.wood, colors.ink, 1.8f, thickness = 3f)
    for (i in -2..2) {
        sketchLine(pt(leanX + 10f + i * 4f, baseY - 82f), pt(leanX + 10f + i * 4f, baseY - 88f), colors.metalDark, 1.6f)
    }

    val potH = 30f
    drawPot(houseX - 74f, baseY - potH, 42f, 30f, potH, colors)
    drawBarrelCactus(houseX - 74f, baseY - potH, 28f, 24f, colors)

    twinkle(cx - 80f, roofY - 6f, 3f, t, 0.3f, colors.inkSoft)
    twinkle(cx + 94f, roofY + 10f, 3f, t, 0.7f, colors.inkSoft)
    groundLine(baseY + 28f, colors.inkFaint)
}
