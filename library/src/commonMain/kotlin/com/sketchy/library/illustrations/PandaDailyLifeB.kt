package com.sketchy.library.illustrations

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.withTransform
import com.sketchy.library.SketchyStyle
import com.sketchy.library.characters.*
import com.sketchy.library.utils.*
import kotlin.math.cos
import kotlin.math.sin

// ─── An Evening With a Good Book ──────────────────────────────────────────────
//   Panda sits cross-legged on a picnic mat after dark, reading by the warm
//   glow of a lantern hung from a bent garden hook, a bamboo-leaf bookmark
//   trailing off the page.

internal fun DrawScope.drawPandaReadingLanternScene(t: Float, colors: SketchyStyle) {
    val flicker = pulse(t, 0f)

    // ── Night sky, background ────────────────────────────────────────────
    val hills = Path().apply {
        moveTo(d(20f), d(196f))
        quadraticTo(d(70f), d(166f), d(120f), d(188f))
        quadraticTo(d(170f), d(160f), d(230f), d(186f))
        quadraticTo(d(270f), d(168f), d(300f), d(190f))
        lineTo(d(300f), d(232f))
        lineTo(d(20f), d(232f))
        close()
    }
    paint(hills, colors.skyDeep.a(0.28f), colors.lineOnly, 1.2f)

    paintCircle(pt(258f, 62f), 22f, colors.paper.a(0.9f), colors.ink, 1.4f)
    sketchCircle(pt(250f, 56f), 4f, colors.faint(colors.metal.a(0.5f)), filled = true)
    sketchCircle(pt(264f, 68f), 2.6f, colors.faint(colors.metal.a(0.4f)), filled = true)

    twinkle(58f, 46f, 2.6f, t, 0.15f, colors.touch(colors.paper.a(0.85f)))
    twinkle(118f, 40f, 2.4f, t, 0.55f, colors.touch(colors.paper.a(0.8f)))
    twinkle(198f, 36f, 2.8f, t, 0.85f, colors.touch(colors.paper.a(0.8f)))

    // ── Lantern on a bent garden hook, midground ──────────────────────────
    contactShadow(252f, 288f, 12f, 3f, colors.shade)
    val pole = Path().apply {
        moveTo(d(252f), d(286f))
        lineTo(d(252f), d(150f))
        quadraticTo(d(252f), d(112f), d(222f), d(110f))
    }
    stroke(pole, colors.line(colors.woodDark), 2.6f)
    glow(222f, 128f, 46f, colors.sun.a(0.32f + 0.14f * flicker))
    paint(roundRectPath(206f, 112f, 32f, 40f, 6f), colors.metal.a(0.45f), colors.line(colors.metalDark), 1.6f)
    fill(roundRectPath(210f, 116f, 24f, 32f, 4f), colors.sun.a(0.55f + 0.2f * flicker))
    listOf(214f, 222f, 230f).forEach { lx ->
        sketchLine(pt(lx, 112f), pt(lx, 152f), colors.faint(colors.metalDark.a(0.6f)), 1.2f)
    }
    sketchLine(pt(222f, 112f), pt(222f, 104f), colors.line(colors.metalDark), 2f)

    // ── Picnic mat, ground ─────────────────────────────────────────────────
    contactShadow(150f, 288f, 96f, 12f, colors.shade)
    paint(ellipsePath(148f, 280f, 92f, 20f), colors.fabric.a(0.85f), colors.ink, 1.8f)
    sketchLine(pt(80f, 278f), pt(216f, 278f), colors.hint(colors.fabricDark.a(0.5f)), 1.4f)

    // ── Panda, seated cross-legged, foreground hero ──────────────────────
    pandaLeg(126f, 244f, 92f, 266f, colors, controlX = 100f, controlY = 258f, thickness = 15f)
    pandaLeg(174f, 244f, 208f, 264f, colors, controlX = 200f, controlY = 254f, thickness = 15f)
    pandaBody(150f, 192f, 248f, 40f, colors)

    // the book, held open in front of the chest
    contactShadow(150f, 234f, 42f, 6f, colors.shade)
    val leftPage = Path().apply {
        moveTo(d(112f), d(206f))
        lineTo(d(148f), d(200f))
        lineTo(d(148f), d(232f))
        lineTo(d(116f), d(238f))
        close()
    }
    val rightPage = Path().apply {
        moveTo(d(148f), d(200f))
        lineTo(d(186f), d(208f))
        lineTo(d(182f), d(240f))
        lineTo(d(148f), d(232f))
        close()
    }
    paint(leftPage, vBrush(200f, 238f, colors.paper.lit(0.3f), colors.paper), colors.ink, 2f)
    paint(rightPage, vBrush(200f, 240f, colors.paper.lit(0.45f), colors.sun.a(0.25f)), colors.ink, 2f)
    for (i in 0..3) {
        sketchLine(pt(120f, 212f + i * 5f), pt(144f, 208f + i * 5f), colors.inkSoft.a(0.4f), 1.2f)
        sketchLine(pt(154f, 208f + i * 5f), pt(178f, 213f + i * 5f), colors.inkSoft.a(0.4f), 1.2f)
    }
    sketchLine(pt(148f, 200f), pt(148f, 232f), colors.hint(colors.shade), 2f)
    // a bamboo-leaf bookmark, trailing off the bottom edge
    val bookmark = Path().apply {
        moveTo(d(160f), d(228f))
        quadraticTo(d(172f), d(252f), d(164f), d(272f))
        quadraticTo(d(158f), d(252f), d(160f), d(228f))
        close()
    }
    paint(bookmark, vBrush(228f, 272f, colors.leaf, colors.leafDark), colors.inkOf(colors.leaf), 1.6f)

    pandaArm(122f, 208f, 116f, 226f, colors, controlX = 104f, controlY = 220f, thickness = 12f)
    pandaArm(178f, 208f, 182f, 224f, colors, controlX = 198f, controlY = 216f, thickness = 12f)
    pandaHead(150f, 176f, 34f, colors, tilt = -10f, blink = pandaAutoBlink(t, 0.3f))

    // warm lantern light spilling across the scene
    glow(190f, 190f, 60f, colors.glow.a(0.16f + 0.1f * flicker))
    groundHint(300f, colors.inkFaint)
}

// ─── Baking Bamboo Cookies ─────────────────────────────────────────────────────
//   Panda in an apron shows off a fresh tray of bamboo-shaped cookies, the
//   oven still glowing warm behind, a rolling pin and a dusting of flour on
//   the counter.

internal fun DrawScope.drawPandaBakingScene(t: Float, colors: SketchyStyle) {
    val warmth = pulse(t, 0f)

    // ── Kitchen wall, background ─────────────────────────────────────────
    sketchLine(pt(20f, 70f), pt(300f, 70f), colors.faint(colors.inkFaint), 1.2f)
    listOf(50f, 84f, 118f).forEachIndexed { i, jx ->
        paint(roundRectPath(jx, 44f, 22f, 26f, 5f), colors.metal.a(0.4f), colors.lineOnly, 1.2f)
        fill(roundRectPath(jx + 3f, 50f, 16f, 10f, 3f), colors.leaf.a(0.35f + 0.1f * hash01(i)))
    }

    // ── Oven, midground left ─────────────────────────────────────────────
    contactShadow(78f, 268f, 46f, 8f, colors.shade)
    paint(roundRectPath(36f, 154f, 88f, 106f, 10f), vBrush(154f, 260f, colors.metal.lit(0.2f), colors.metalDark), colors.ink, 1.8f)
    val ovenDoor = ellipsePath(80f, 214f, 28f, 26f)
    paint(ovenDoor, colors.metalDark.a(0.6f), colors.line(colors.metalDark), 1.6f)
    glow(80f, 214f, 30f, colors.sunDeep.a(0.4f + 0.2f * warmth))
    fill(ellipsePath(80f, 214f, 18f, 17f), colors.sunDeep.a(0.5f + 0.25f * warmth))
    listOf(52f, 108f).forEach { kx -> sketchCircle(pt(kx, 168f), 4f, colors.line(colors.metalDark), width = 1.4f) }
    steam(80f, 182f, t, 0.1f, colors.hint(colors.paper.a(0.55f)), height = 30f)

    // ── Counter, foreground surface ──────────────────────────────────────
    val counter = surfacePath(backY = 236f, frontY = 278f, backInset = 30f, frontInset = 18f, r = 8f)
    paint(counter, vBrush(236f, 278f, colors.wood.lit(0.2f), colors.woodDark), colors.ink, 2f)
    clipPath(counter) {
        contactShadow(150f, 244f, 30f, 6f, colors.shade)
        contactShadow(198f, 246f, 22f, 5f, colors.shade)
    }
    val bowl = ellipsePath(150f, 246f, 26f, 9f)
    paint(bowl, hBrush(124f, 176f, colors.paper, colors.metal), colors.ink, 1.8f)
    fill(ellipsePath(150f, 242f, 20f, 7f), colors.wood.lit(0.5f))
    paint(roundRectPath(184f, 240f, 46f, 9f, 4f), vBrush(240f, 249f, colors.wood.lit(0.3f), colors.wood), colors.ink, 1.6f)
    listOf(184f, 226f).forEach { hx -> paint(roundRectPath(hx - 4f, 241f, 8f, 7f, 2f), colors.woodDark, colors.ink, 1.4f) }
    for (i in 0..5) {
        val fx = 120f + i * 18f + 6f * hash01(i)
        fill(ellipsePath(fx, 238f + 3f * hash01(i + 8), 2f, 1f), colors.paper.a(0.5f))
    }

    // ── Panda in an apron, foreground hero ────────────────────────────────
    pandaBody(214f, 156f, 224f, 40f, colors)
    val apron = Path().apply {
        moveTo(d(190f), d(168f))
        quadraticTo(d(188f), d(150f), d(214f), d(150f))
        quadraticTo(d(240f), d(150f), d(238f), d(168f))
        lineTo(d(232f), d(218f))
        quadraticTo(d(214f), d(226f), d(196f), d(218f))
        close()
    }
    paint(apron, colors.fabric, colors.ink, 2f)
    sketchLine(pt(196f, 176f), pt(232f, 176f), colors.hint(colors.fabricDark.a(0.7f)), 1.4f)
    val tie = Path().apply {
        moveTo(d(196f), d(168f))
        quadraticTo(d(180f), d(160f), d(184f), d(148f))
    }
    stroke(tie, colors.line(colors.fabricDark), 2.2f)

    pandaArm(190f, 176f, 170f, 198f, colors, controlX = 174f, controlY = 184f, thickness = 12f)
    pandaArm(238f, 176f, 256f, 198f, colors, controlX = 254f, controlY = 184f, thickness = 12f)

    // the tray of bamboo cookies, held up between the paws
    contactShadow(213f, 204f, 34f, 6f, colors.shade)
    paint(roundRectPath(180f, 196f, 66f, 12f, 4f), colors.metal.a(0.6f), colors.line(colors.metalDark), 1.8f)
    listOf(192f, 213f, 234f).forEachIndexed { i, cx ->
        val cookie = Path().apply {
            moveTo(d(cx - 9f), d(196f))
            quadraticTo(d(cx), d(188f), d(cx + 9f), d(196f))
            quadraticTo(d(cx), d(202f), d(cx - 9f), d(196f))
            close()
        }
        paint(cookie, colors.leaf.lit(0.1f), colors.inkOf(colors.leaf), 1.6f)
        listOf(-4f, 0f, 4f).forEach { seg ->
            sketchLine(pt(cx + seg, 190f), pt(cx + seg, 202f), colors.faint(colors.leafDark.a(0.6f)), 1f)
        }
        steam(cx, 190f, t, 0.15f * i, colors.hint(colors.paper.a(0.5f)), height = 22f)
    }

    pandaHead(214f, 138f, 34f, colors, tilt = 6f, expression = PandaExpression.Delighted, blink = pandaAutoBlink(t, 0.5f))

    groundHint(298f, colors.inkFaint)
    twinkle(46f, 100f, 2.4f, t, 0.3f, colors.touch(colors.sun.a(0.7f)))
    twinkle(266f, 150f, 2.4f, t, 0.7f, colors.touch(colors.paper.a(0.8f)))
}

// ─── An Afternoon Bike Ride ────────────────────────────────────────────────────
//   Panda pedals a small bicycle down a sunny lane, wheels and crank turning,
//   a bamboo stalk poking out of the front basket swaying in the breeze.

internal fun DrawScope.drawPandaBikeRideScene(t: Float, colors: SketchyStyle) {
    val crankDeg = (360f * t) % 360f
    val breeze = wave(t, 0f)

    // ── Sky, background ──────────────────────────────────────────────────
    glow(52f, 46f, 44f, colors.sun.a(0.4f))
    sketchCircle(pt(52f, 46f), 16f, colors.touch(colors.sun, 0.55f), filled = true)
    sketchCircle(pt(52f, 46f), 16f, colors.lineOnly, width = 1.2f)
    listOf(150f, 216f).forEach { cx ->
        val cloud = Path().apply {
            moveTo(d(cx - 24f), d(52f))
            quadraticTo(d(cx - 10f), d(38f), d(cx + 8f), d(48f))
            quadraticTo(d(cx + 26f), d(40f), d(cx + 30f), d(56f))
            quadraticTo(d(cx + 10f), d(64f), d(cx - 24f), d(56f))
            close()
        }
        paint(cloud, colors.paper.a(0.5f), colors.lineOnly, 1.2f)
    }
    val hillBand = Path().apply {
        moveTo(d(20f), d(214f))
        quadraticTo(d(100f), d(196f), d(180f), d(212f))
        quadraticTo(d(250f), d(200f), d(300f), d(214f))
        lineTo(d(300f), d(244f))
        lineTo(d(20f), d(244f))
        close()
    }
    paint(hillBand, colors.leaf.a(0.22f), colors.lineOnly, 1.2f)

    for (i in 0..2) {
        val ly = 218f + i * 9f
        val len = 18f + 10f * pulse(t, i * 0.2f)
        sketchLine(pt(24f, ly), pt(24f + len, ly), colors.inkFaint, 1.6f)
    }

    // ── The bicycle, midground prop ───────────────────────────────────────
    val rearX = 100f; val rearY = 250f
    val frontX = 222f; val frontY = 250f
    val bbX = 146f; val bbY = 236f
    contactShadow(rearX, 282f, 26f, 6f, colors.shade)
    contactShadow(frontX, 282f, 26f, 6f, colors.shade)

    listOf(rearX to rearY, frontX to frontY).forEachIndexed { i, (hx, hy) ->
        val hub = pt(hx, hy)
        sketchCircle(hub, 32f, colors.line(colors.metalDark), width = 2.2f)
        sketchCircle(hub, 28f, colors.metal.a(0.3f), width = 3f)
        withTransform({ rotate(degrees = crankDeg * 1.6f + i * 45f, pivot = hub) }) {
            sketchLine(pt(hx - 24f, hy), pt(hx + 24f, hy), colors.hint(colors.metal.lit(0.3f)), 1.6f)
            sketchLine(pt(hx, hy - 24f), pt(hx, hy + 24f), colors.hint(colors.metal.lit(0.3f)), 1.6f)
        }
        sketchCircle(hub, 5f, colors.metalDark, filled = true)
    }

    val frame = Path().apply {
        moveTo(d(rearX), d(rearY))
        lineTo(d(118f), d(184f))
        lineTo(d(bbX), d(bbY))
        close()
        moveTo(d(bbX), d(bbY))
        lineTo(d(206f), d(172f))
        lineTo(d(frontX), d(frontY))
        moveTo(d(118f), d(184f))
        lineTo(d(206f), d(172f))
    }
    drawPath(frame, color = colors.line(colors.metalDark), style = bold(2.6f))
    paint(roundRectPath(104f, 178f, 24f, 8f, 3f), colors.fabricDark, colors.ink, 1.8f)
    sketchLine(pt(206f, 172f), pt(206f, 154f), colors.line(colors.metalDark), 2.6f)
    sketchLine(pt(188f, 156f), pt(224f, 156f), colors.line(colors.metalDark), 2.6f)

    paint(roundRectPath(190f, 140f, 34f, 18f, 4f), colors.wood.a(0.7f), colors.ink, 1.6f)
    bambooStalk(198f, 108f, 140f, colors, sway = 6f * breeze, segments = 3)

    // ── Panda, pedaling, foreground hero ──────────────────────────────────
    val crankRad = crankDeg * kotlin.math.PI.toFloat() / 180f
    val footAX = bbX + 16f * cos(crankRad)
    val footAY = bbY + 16f * sin(crankRad) + 6f
    val footBX = bbX + 16f * cos(crankRad + kotlin.math.PI.toFloat())
    val footBY = bbY + 16f * sin(crankRad + kotlin.math.PI.toFloat()) + 6f
    pandaLeg(154f, 208f, footAX, footAY, colors, controlX = 150f, controlY = (208f + footAY) / 2f, thickness = 14f)
    pandaLeg(160f, 208f, footBX, footBY, colors, controlX = 168f, controlY = (208f + footBY) / 2f, thickness = 14f)
    pandaBody(158f, 168f, 216f, 36f, colors)
    pandaArm(150f, 178f, 194f, 158f, colors, controlX = 172f, controlY = 162f, thickness = 11f)
    pandaHead(158f, 154f, 32f, colors, tilt = 4f, expression = PandaExpression.Delighted, blink = pandaAutoBlink(t, 0.6f))

    groundHint(296f, colors.inkFaint)
    twinkle(272f, 96f, 2.4f, t, 0.4f, colors.touch(colors.sun))
    twinkle(60f, 130f, 2.4f, t, 0.75f, colors.touch(colors.paper.a(0.8f)))
}

// ─── Stargazing on the Blanket ─────────────────────────────────────────────────
//   Panda reclines on a picnic blanket under a sky thick with stars, a small
//   telescope on its tripod standing by, a shooting star crossing once a loop.

internal fun DrawScope.drawPandaStargazingScene(t: Float, colors: SketchyStyle) {
    // ── Night sky, background ────────────────────────────────────────────
    paintCircle(pt(66f, 60f), 26f, colors.paper.a(0.9f), colors.ink, 1.4f)
    sketchCircle(pt(58f, 52f), 5f, colors.faint(colors.metal.a(0.5f)), filled = true)
    sketchCircle(pt(74f, 66f), 3.2f, colors.faint(colors.metal.a(0.4f)), filled = true)
    glow(66f, 60f, 50f, colors.glow.a(0.22f))

    val starSpots = listOf(
        Triple(120f, 40f, 0.05f), Triple(160f, 66f, 0.35f), Triple(200f, 36f, 0.6f),
        Triple(240f, 58f, 0.15f), Triple(270f, 96f, 0.5f), Triple(130f, 92f, 0.8f),
        Triple(216f, 100f, 0.9f)
    )
    starSpots.forEach { (sx, sy, off) ->
        twinkle(sx, sy, 2.2f + 1.2f * hash01((sx + sy).toInt()), t, off, colors.touch(colors.paper.a(0.85f)))
    }
    // a shooting star, crossing once each loop
    val shootPhase = loop(t, 0f)
    if (shootPhase in 0.65f..0.85f) {
        val local = smooth01((shootPhase - 0.65f) / 0.2f)
        val sxA = 50f + 110f * local
        val syA = 26f + 34f * local
        val tail = Path().apply {
            moveTo(d(sxA - 20f), d(syA - 9f))
            lineTo(d(sxA), d(syA))
        }
        drawPath(tail, color = colors.touch(colors.paper).a(0.8f * (1f - local)), style = thin(2f))
    }

    // ── Picnic blanket, ground ─────────────────────────────────────────────
    contactShadow(168f, 278f, 100f, 14f, colors.shade)
    paint(ellipsePath(168f, 270f, 96f, 22f), colors.fabric.a(0.85f), colors.ink, 1.8f)
    listOf(-40f, 0f, 40f).forEach { off ->
        sketchLine(pt(168f + off, 254f), pt(168f + off, 286f), colors.hint(colors.accentBlue.a(0.5f)), 1.6f)
    }

    // ── Tripod telescope, midground ────────────────────────────────────────
    val apexX = 232f; val apexY = 190f
    contactShadow(apexX, 274f, 18f, 5f, colors.shade)
    listOf(210f, 232f, 254f).forEach { fx ->
        sketchLine(pt(fx, 270f), pt(apexX, apexY), colors.line(colors.woodDark), 2.2f)
    }
    val tube = Path().apply {
        moveTo(d(apexX - 8f), d(apexY + 4f))
        lineTo(d(apexX + 22f), d(apexY - 58f))
        lineTo(d(apexX + 32f), d(apexY - 54f))
        lineTo(d(apexX + 2f), d(apexY + 10f))
        close()
    }
    paint(tube, vBrush(apexY - 58f, apexY + 10f, colors.metal.lit(0.3f), colors.metalDark), colors.ink, 1.8f)
    paintCircle(pt(apexX + 25f, apexY - 56f), 7f, colors.sky.lit(0.2f), colors.ink, 1.6f)

    // ── Panda, reclining to look up at the sky, hero ─────────────────────
    pandaLeg(140f, 258f, 108f, 274f, colors, controlX = 120f, controlY = 270f, thickness = 15f)
    pandaLeg(180f, 258f, 200f, 276f, colors, controlX = 196f, controlY = 268f, thickness = 15f)
    pandaBody(160f, 208f, 262f, 40f, colors)
    pandaArm(140f, 222f, 112f, 250f, colors, controlX = 118f, controlY = 230f, thickness = 12f)
    pandaArm(182f, 222f, 200f, 244f, colors, controlX = 198f, controlY = 228f, thickness = 12f)
    pandaHead(160f, 188f, 34f, colors, tilt = -22f, blink = pandaAutoBlink(t, 0.2f))

    groundHint(300f, colors.inkFaint)
}

// ─── Tending the Garden ────────────────────────────────────────────────────────
//   Panda waters a young row of bamboo shoots with a little tin watering can,
//   droplets falling from the spout at staggered speeds.

internal fun DrawScope.drawPandaGardenScene(t: Float, colors: SketchyStyle) {
    // ── Sky and fence, background ─────────────────────────────────────────
    glow(268f, 44f, 40f, colors.sun.a(0.35f))
    sketchCircle(pt(268f, 44f), 15f, colors.touch(colors.sun, 0.5f), filled = true)
    sketchCircle(pt(268f, 44f), 15f, colors.lineOnly, width = 1.2f)
    for (i in 0..6) {
        val fx = 30f + i * 42f
        paint(roundRectPath(fx, 118f, 8f, 34f, 2f), colors.wood.a(0.35f), colors.lineOnly, 1.2f)
    }
    sketchLine(pt(24f, 128f), pt(296f, 128f), colors.faint(colors.woodDark.a(0.4f)), 1.2f)

    // ── Soil bed with young shoots, midground ──────────────────────────────
    contactShadow(160f, 260f, 118f, 14f, colors.shade)
    val soil = Path().apply {
        moveTo(d(40f), d(252f))
        quadraticTo(d(160f), d(232f), d(280f), d(252f))
        lineTo(d(280f), d(276f))
        lineTo(d(40f), d(276f))
        close()
    }
    paint(soil, vBrush(232f, 276f, colors.woodDark.lit(0.1f), colors.woodDark.shaded(0.2f)), colors.ink, 1.8f)
    for (i in 0..7) {
        val hx = 54f + i * 28f + 6f * hash01(i)
        sketchLine(pt(hx, 250f + 4f * hash01(i + 4)), pt(hx + 6f, 254f + 4f * hash01(i + 9)), colors.faint(colors.woodDark.a(0.4f)), 1.2f)
    }
    listOf(96f to 0.1f, 140f to 0.35f, 184f to 0.6f).forEach { (sx, off) ->
        bambooStalk(sx, 196f + 8f * hash01(sx.toInt()), 250f, colors, sway = 5f * wave(t, off), segments = 3)
    }

    // ── Panda watering, foreground hero ────────────────────────────────────
    contactShadow(226f, 274f, 40f, 10f, colors.shade)
    pandaLeg(212f, 234f, 200f, 268f, colors, controlX = 202f, controlY = 252f, thickness = 15f)
    pandaLeg(240f, 234f, 248f, 268f, colors, controlX = 246f, controlY = 252f, thickness = 15f)
    pandaBody(226f, 182f, 236f, 38f, colors)

    // the watering can, tilted to pour towards the shoots
    val canBody = Path().apply {
        moveTo(d(238f), d(196f))
        quadraticTo(d(216f), d(198f), d(214f), d(216f))
        quadraticTo(d(212f), d(228f), d(230f), d(226f))
        quadraticTo(d(240f), d(212f), d(238f), d(196f))
        close()
    }
    val spout = Path().apply {
        moveTo(d(214f), d(206f))
        quadraticTo(d(194f), d(210f), d(186f), d(222f))
    }
    val handle = Path().apply {
        moveTo(d(232f), d(198f))
        quadraticTo(d(240f), d(182f), d(226f), d(180f))
        quadraticTo(d(216f), d(182f), d(220f), d(196f))
    }
    paintStroke(handle, colors.metal.lit(0.2f), colors.ink, width = 5f)
    paintStroke(spout, colors.metal.lit(0.2f), colors.ink, width = 6f)
    paint(canBody, vBrush(196f, 228f, colors.metal.lit(0.3f), colors.metalDark), colors.ink, 1.8f)

    pandaArm(210f, 198f, 216f, 214f, colors, controlX = 204f, controlY = 206f, thickness = 12f)
    pandaArm(242f, 198f, 232f, 220f, colors, controlX = 246f, controlY = 210f, thickness = 12f)
    pandaHead(214f, 162f, 33f, colors, tilt = 18f, blink = pandaAutoBlink(t, 0.4f))

    // ── Water droplets, falling from the spout ─────────────────────────────
    for (i in 0..3) {
        val speed = 1.3f + 0.3f * hash01(i + 20)
        val phase = loop(t * speed, hash01(i + 5))
        val dx = 186f + 4f * hash01(i)
        val dy = 222f + phase * 30f
        val fade = smooth01(1f - phase)
        paintCircle(pt(dx, dy), 2.4f, colors.sky.a(0.15f + 0.75f * fade), colors.inkOf(colors.sky), 1.4f)
    }

    twinkle(150f, 210f, 2.2f, t, 0.2f, colors.touch(colors.sky.a(0.7f)))
    twinkle(184f, 214f, 2f, t, 0.6f, colors.touch(colors.sky.a(0.6f)))
    groundHint(288f, colors.inkFaint)
}
