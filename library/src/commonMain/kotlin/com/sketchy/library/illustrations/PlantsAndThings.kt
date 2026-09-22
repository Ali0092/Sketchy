package com.sketchy.library.illustrations

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipPath
import com.sketchy.library.SketchyStyle
import com.sketchy.library.utils.*

// ─── A Desert in Bloom ────────────────────────────────────────────────────────
//   A full desert scene: a low sun over hazed mesas, a real sandy dune floor
//   with cracked earth and ripples, a tall two-armed saguaro standing over a
//   scatter of barrel cacti, a succulent, dry brush and scattered rock — one
//   cactus flower catching the light.

internal fun DrawScope.drawDesertBloomScene(t: Float, colors: SketchyStyle) {
    val shimmer = (1f + wave(t, 0f)) / 2f

    // ── Sun, low over the horizon ────────────────────────────────────────
    val sunCx = 236f
    val sunCy = 74f
    glow(sunCx, sunCy, 56f, colors.sun.a(0.55f + 0.15f * shimmer))
    sketchCircle(pt(sunCx, sunCy), 26f, colors.touch(colors.sun, 0.75f), filled = true)
    sketchCircle(pt(sunCx, sunCy), 26f, colors.lineOnly, width = 2f)

    // ── Distant mesas, hazed by the heat ─────────────────────────────────
    val mesas = Path().apply {
        moveTo(d(8f), d(158f))
        lineTo(d(40f), d(158f))
        lineTo(d(52f), d(122f))
        lineTo(d(78f), d(122f))
        lineTo(d(90f), d(100f))
        lineTo(d(112f), d(100f))
        lineTo(d(124f), d(130f))
        lineTo(d(168f), d(130f))
        lineTo(d(178f), d(150f))
        lineTo(d(312f), d(150f))
        lineTo(d(312f), d(166f))
        lineTo(d(8f), d(166f))
        close()
    }
    paint(mesas, colors.clay.a(0.35f), colors.lineOnly, 1.6f)

    for (i in 0 until 3) {
        val x = 60f + i * 90f
        val rise = 6f * shimmer
        sketchLine(pt(x, 148f - rise), pt(x + 3f, 130f - rise), colors.inkFaint.a(0.3f), 1.2f)
    }

    // ── Sandy dune floor: a real, bounded surface with a front edge ──────
    val ground = surfacePath(backY = 166f, frontY = 300f, backInset = 30f, frontInset = 6f, r = 18f)
    paint(ground, vBrush(166f, 300f, colors.sun.lit(0.35f), colors.clay.lit(0.1f)), colors.ink, 2.4f)
    shade(ground, hBrush(6f, 314f, colors.shade.a(0f), colors.shade.a(colors.shade.alpha * 0.4f)))
    for (i in 0 until 4) {
        val ry = 190f + i * 26f
        val sway = 10f * (i % 2)
        val ripple = Path().apply {
            moveTo(d(24f - sway), d(ry))
            quadraticTo(d(160f), d(ry - 10f), d(296f + sway), d(ry))
        }
        drawPath(ripple, color = colors.faint(colors.clay.a(0.4f)), style = thin(1.4f))
    }
    listOf(70f to 268f, 150f to 282f, 230f to 270f).forEach { (x, y) ->
        sketchLine(pt(x, y), pt(x + 14f, y - 8f), colors.inkFaint, 1.2f)
        sketchLine(pt(x + 6f, y - 2f), pt(x - 4f, y - 10f), colors.inkFaint, 1.2f)
    }

    clipPath(ground) {
        contactShadow(150f, 234f, 46f, 10f, colors.shade)
        contactShadow(238f, 258f, 30f, 7f, colors.shade)
        contactShadow(70f, 256f, 26f, 6f, colors.shade)
    }

    // ── Hero: a tall two-armed saguaro standing over the scene ───────────
    drawSaguaroCactus(150f, 236f, 150f, colors, trunkWidth = 30f, arms = 2, bloom = true)

    // ── Supporting cacti and succulents ───────────────────────────────────
    drawBarrelCactus(238f, 258f, 44f, 38f, colors, bloom = true)
    drawSucculentRosette(70f, 256f, colors, scale = 1.1f)
    drawBarrelCactus(266f, 288f, 30f, 26f, colors)

    // dry desert brush, a few scattered tufts
    listOf(40f to 280f, 190f to 296f, 292f to 252f).forEach { (bx, by) ->
        for (i in -2..2) {
            val a = i * 18f * (kotlin.math.PI / 180.0).toFloat()
            val len = 14f + kotlin.math.abs(i) * 2f
            sketchLine(
                pt(bx, by),
                pt(bx + len * kotlin.math.sin(a), by - len * kotlin.math.cos(a)),
                colors.faint(colors.woodDark),
                1.3f
            )
        }
    }

    listOf(Triple(112f, 292f, 12f), Triple(200f, 300f, 9f)).forEach { (rx, ry, rr) ->
        val rock = ellipsePath(rx, ry, rr, rr * 0.6f)
        paint(rock, colors.metal.a(0.6f), colors.ink, 1.6f)
    }

    // a bird, far off, riding the heat
    val birdX = 100f + 40f * wave(t, 0.3f)
    val bird = Path().apply {
        moveTo(d(birdX - 10f), d(60f))
        quadraticTo(d(birdX - 3f), d(52f), d(birdX), d(58f))
        quadraticTo(d(birdX + 3f), d(52f), d(birdX + 10f), d(60f))
    }
    drawPath(bird, color = colors.inkFaint, style = thin(1.6f))

    twinkle(284f, 128f, 3f, t, 0.4f, colors.touch(colors.sun, 0.5f))
    twinkle(26f, 96f, 3f, t, 0.7f, colors.inkSoft)
}
