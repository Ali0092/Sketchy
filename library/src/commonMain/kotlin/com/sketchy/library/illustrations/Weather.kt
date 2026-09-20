package com.sketchy.library.illustrations

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.sketchy.library.SketchyStyle
import com.sketchy.library.utils.*

// ─── A Rainy Day ────────────────────────────────────────────────────────────
//   A full outdoor downpour: a bank of heavy storm clouds overhead, rain
//   falling in sheets across a hazy distant skyline, a wet street with
//   reflective puddles rippling underfoot, and a leaning open umbrella
//   catching the drops in the foreground.

internal fun DrawScope.drawRainyDayScene(t: Float, colors: SketchyStyle) {
    // ── Sky wash ───────────────────────────────────────────────────────
    fill(rectPath(0f, 0f, 320f, 220f), vBrush(0f, 220f, colors.skyDeep.a(0.5f), colors.sky.a(0.3f)))

    // ── Distant, hazed skyline ────────────────────────────────────────
    for (i in 0..6) {
        val bx = -10f + i * 50f
        val bw = 30f + 14f * hash01(i)
        val top = 150f + 30f * hash01(i + 10)
        paint(rectPath(bx, top, bw, 190f - top), colors.fabricDark.a(0.28f), colors.lineOnly, 1.2f)
    }

    // ── Storm clouds, banked across the top ──────────────────────────
    drawCloudSilhouette(96f, 78f, 148f, 66f, colors, tint = colors.metal.shaded(0.18f))
    drawCloudSilhouette(214f, 60f, 168f, 74f, colors, tint = colors.metalDark.lit(0.05f))
    drawCloudSilhouette(180f, 92f, 110f, 50f, colors, tint = colors.metal)

    // ── Rain, falling in sheets across the whole scene ───────────────
    drawRainStreaks(10f, 310f, 60f, 214f, 22, t, colors, seed = 0, color = colors.hint(colors.sky))

    // ── Wet street ─────────────────────────────────────────────────────
    val street = surfacePath(backY = 214f, frontY = 300f, backInset = 4f, frontInset = -4f, r = 0f)
    paint(street, vBrush(214f, 300f, colors.metalDark.lit(0.05f), colors.metalDark.shaded(0.1f)), colors.ink, 2.2f)
    fill(rectPath(4f, 214f, 312f, 18f), vBrush(214f, 232f, colors.sky.a(0.35f), colors.sky.a(0f)))

    // puddles catching the sky, each with rings rippling outward
    listOf(Triple(90f, 262f, 34f), Triple(230f, 280f, 26f)).forEach { (px, py, pr) ->
        fill(ellipsePath(px, py, pr, pr * 0.32f), colors.sky.a(0.4f))
        sheen(
            ellipsePath(px, py, pr, pr * 0.32f),
            pt(px - pr * 0.6f, py),
            pt(px + pr * 0.6f, py),
            colors.paper.a(0.3f)
        )
        for (ring in 0..1) {
            val phase = loop(t, ring * 0.5f + px * 0.001f)
            val ringR = pr * (0.25f + 0.75f * phase)
            val alpha = (1f - phase) * 0.35f
            drawPath(
                ellipsePath(px, py, ringR, ringR * 0.3f),
                color = colors.faint(colors.paper).a(alpha),
                style = thin(1.2f)
            )
        }
    }

    // ── Umbrella, foreground, leaning slightly ────────────────────────
    val umbrellaCx = 208f
    val topY = 150f
    val canopyR = 58f
    val lean = 4f

    contactShadow(umbrellaCx + 6f, 296f, 34f, 7f, colors.shade)

    val canopy = Path().apply {
        moveTo(d(umbrellaCx - canopyR + lean), d(topY))
        arcTo(
            rect = Rect(pt(umbrellaCx - canopyR + lean * 0.5f, topY - canopyR), Size(d(canopyR * 2f), d(canopyR * 2f))),
            startAngleDegrees = 180f,
            sweepAngleDegrees = 180f,
            forceMoveTo = false
        )
        val scallops = 5
        for (i in scallops downTo 1) {
            val x0 = umbrellaCx - canopyR + lean + canopyR * 2f * i / scallops
            val x1 = umbrellaCx - canopyR + lean + canopyR * 2f * (i - 1) / scallops
            val midX = (x0 + x1) / 2f
            quadraticTo(d(midX), d(topY + 9f), d(x1), d(topY))
        }
        close()
    }
    inkShadow(canopy, colors.outlineShadow)
    paint(canopy, vBrush(topY - canopyR, topY, colors.terracotta.lit(0.1f), colors.clay), colors.ink, 2.4f)
    sheen(canopy, pt(umbrellaCx - canopyR * 0.5f, topY - canopyR * 0.7f), pt(umbrellaCx, topY), colors.paper.a(0.22f))

    val apex = pt(umbrellaCx + lean * 0.5f, topY - canopyR * 0.92f)
    for (i in 0..5) {
        val x = umbrellaCx - canopyR + lean + canopyR * 2f * i / 5f
        sketchLine(apex, pt(x, topY - 2f), colors.faint(colors.ink), 1.3f)
    }
    sketchLine(apex, pt(umbrellaCx + lean * 0.5f, topY - canopyR - 10f), colors.ink, 2f)

    val handle = Path().apply {
        moveTo(d(umbrellaCx + lean * 0.5f), d(topY))
        lineTo(d(umbrellaCx), d(266f))
        quadraticTo(d(umbrellaCx - 16f), d(280f), d(umbrellaCx - 4f), d(286f))
    }
    limb(handle, colors.wood, colors.ink, 2.4f, thickness = 6f)

    // a couple of drops beading off the canopy's rim
    listOf(0.15f, 0.62f, 0.86f).forEachIndexed { i, f ->
        val x = umbrellaCx - canopyR + lean + canopyR * 2f * f
        val fall = smooth01(loop(t * 1.4f, i * 0.3f))
        val y = topY + 4f + 16f * fall
        fill(ellipsePath(x, y, 1.8f, 2.6f), colors.hint(colors.sky).a((1f - fall) * 0.8f))
    }

    groundHint(300f, colors.inkFaint)
}
