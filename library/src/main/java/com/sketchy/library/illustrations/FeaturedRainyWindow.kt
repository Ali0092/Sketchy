package com.sketchy.library.illustrations

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipPath
import com.sketchy.library.SketchyStyle
import com.sketchy.library.utils.*

// ─── Rainy Day Indoors ────────────────────────────────────────────────────────
//   A rain-streaked window looking out over a hazy city, a curtain drawn back,
//   a plant and a mug on the sill. Fully painted — and the one scene in the set
//   lit coolly and flatly by an overcast sky instead of a warm key light, so
//   depth here comes from value and haze rather than from highlights.

internal fun DrawScope.drawRainyWindowScene(t: Float, colors: SketchyStyle) {
    // ── The glass ───────────────────────────────────────────────────────
    val glass = roundRectPath(44f, 34f, 232f, 214f, 6f)
    fill(glass, vBrush(34f, 248f, colors.skyDeep.a(0.55f), colors.metal.lit(0.35f)))

    clipPath(glass) {
        // Far skyline: two silhouette layers at different haze strengths. The
        // fade — not the size — is what pushes the far row into the distance.
        for (i in 0..7) {
            val bx = 44f + i * 30f
            val bw = 20f + 12f * hash01(i)
            val top = 126f + 44f * hash01(i + 12)
            fill(rectPath(bx, top, bw, 248f - top), colors.fabricDark.a(0.22f))
        }
        for (i in 0..4) {
            val bx = 52f + i * 48f + 8f * hash01(i + 30)
            val bw = 30f + 14f * hash01(i + 40)
            val top = 154f + 30f * hash01(i + 50)
            // the near row is drawn as well as painted, so the outlined scene
            // still looks out over a skyline
            paint(rectPath(bx, top, bw, 248f - top), colors.fabricDark.a(0.45f), colors.lineOnly, 1.2f)
            // lit windows, a couple of them flickering
            for (r in 0..4) {
                for (c in 0..2) {
                    val seed = i * 40 + r * 3 + c
                    if (hash01(seed) < 0.45f) continue
                    val flicker = if (hash01(seed + 7) > 0.85f) pulse(t, hash01(seed)) else 1f
                    fill(
                        rectPath(bx + 5f + c * 9f, top + 8f + r * 13f, 4f, 6f),
                        colors.touch(colors.sun).a((0.28f + 0.42f * hash01(seed + 3)) * flicker)
                    )
                }
            }
        }
        // wet street glare at the base of the city
        fill(
            rectPath(44f, 226f, 232f, 22f),
            vBrush(226f, 248f, colors.paper.a(0.4f), colors.paper.a(0f))
        )

        // ── Rain, falling at 16 different speeds ─────────────────────────
        for (i in 0..15) {
            val x = 48f + 226f * hash01(i)
            val speed = 1.4f + 1.6f * hash01(i + 40)
            val len = 14f + 12f * hash01(i + 80)
            val p = loop(t * speed, hash01(i + 120))
            val y = -len + (214f + len) * p
            val streak = Path().apply {
                moveTo(d(x), d(34f + y))
                lineTo(d(x - 2.5f), d(34f + y + len))
            }
            brushStroke(
                streak,
                vBrush(34f + y, 34f + y + len, colors.hint(colors.paper).a(0f), colors.hint(colors.paper).a(0.6f)),
                width = 1f + 0.8f * hash01(i + 160)
            )
        }

        // ── Beads running down the inside of the pane ────────────────────
        for (i in 0..3) {
            val x = 66f + i * 54f
            val fallen = smooth01(loop(t * 0.5f, i * 0.25f))
            val y = 44f + 190f * fallen
            // the trail it has left behind
            val trail = Path().apply {
                moveTo(d(x), d(44f))
                quadraticTo(d(x + 3f), d((44f + y) / 2f), d(x), d(y))
            }
            brushStroke(
                trail,
                vBrush(44f, y, colors.hint(colors.paper).a(0f), colors.hint(colors.paper).a(0.3f)),
                width = 2f
            )
            fill(ellipsePath(x, y, 2.6f, 3.4f), colors.hint(colors.paper).a(0.6f))
            fill(ellipsePath(x - 0.8f, y - 1f, 1f, 1.2f), colors.hint(colors.paper).a(0.9f))
        }

        // condensation gathering along the top of the glass
        fill(
            rectPath(44f, 34f, 232f, 70f),
            vBrush(34f, 104f, colors.paper.a(0.26f), colors.paper.a(0f))
        )
        // one broad reflection raking across the pane
        sheen(glass, pt(60f, 240f), pt(200f, 40f), colors.paper.a(0.18f))
    }

    // ── Frame, drawn after the glass so it occludes it ───────────────────
    drawPath(glass, color = colors.ink, style = bold(2.8f))
    listOf(
        rectPath(155f, 34f, 6f, 214f),
        rectPath(44f, 138f, 232f, 6f)
    ).forEach { mullion ->
        paint(mullion, vBrush(34f, 248f, colors.paper, colors.metal), colors.ink, 1.6f)
    }
    // the frame's own shadow falling onto the glass
    innerRim(glass, 0f, 3f, colors.shade, 3.4f)

    // ── Curtain, drawn back to the right ────────────────────────────────
    //   Four pleats, each shaded on its own, which is what makes cloth read
    //   as cloth rather than as a flat coloured shape.
    for (i in 0..3) {
        val x0 = 244f + i * 15f
        val sway = 3f * wave(t, 0.15f + i * 0.12f)
        val pleat = Path().apply {
            moveTo(d(x0), d(20f))
            quadraticTo(d(x0 + 9f), d(150f), d(x0 + sway), d(288f))
            lineTo(d(x0 + 15f + sway), d(288f))
            quadraticTo(d(x0 + 7f), d(150f), d(x0 + 15f), d(20f))
            close()
        }
        paint(
            pleat,
            hBrush(x0, x0 + 15f, colors.fabricDark, colors.fabric.lit(0.22f), colors.fabric),
            colors.ink,
            1.6f
        )
    }
    // rail and tie-back
    paint(roundRectPath(230f, 14f, 78f, 8f, 3f), colors.woodDark, colors.ink, 1.8f)
    // the tie-back: colour the outlined drawing keeps
    paint(roundRectPath(246f, 150f, 62f, 12f, 5f), colors.accent, colors.ink, 2f)

    // ── Sill ────────────────────────────────────────────────────────────
    val sill = roundRectPath(30f, 248f, 260f, 16f, 4f)
    paint(sill, vBrush(248f, 264f, colors.paper.lit(0.4f), colors.metal), colors.ink, 2.4f)
    paint(rectPath(30f, 264f, 260f, 10f), colors.metalDark.a(0.7f), colors.lineOnly, 1.4f)
    fill(ellipsePath(150f, 252f, 96f, 3.5f), colors.paper.a(0.55f))

    // ── Plant on the sill, foreground left ──────────────────────────────
    contactShadow(84f, 250f, 28f, 6f, colors.shade)
    val pot = Path().apply {
        moveTo(d(64f), d(212f))
        lineTo(d(104f), d(212f))
        lineTo(d(98f), d(248f))
        lineTo(d(70f), d(248f))
        close()
    }
    paint(pot, vBrush(212f, 248f, colors.terracotta, colors.clay), colors.ink, 2.4f)
    shade(pot, hBrush(84f, 104f, colors.shade.a(0f), colors.shade))
    paint(roundRectPath(61f, 205f, 46f, 10f, 3f), colors.terracotta.lit(0.2f), colors.ink, 2f)
    // trailing leaves, two of them draping over the front of the sill
    val droop = 2.5f * wave(t, 0.25f)
    listOf(
        Triple(72f, 168f, -20f),
        Triple(84f, 156f, 0f),
        Triple(96f, 170f, 18f),
        Triple(100f, 236f, 46f)
    ).forEach { (bx, ty, spread) ->
        val leaf = Path().apply {
            moveTo(d(bx), d(206f))
            quadraticTo(d(bx - 14f + spread), d(ty + 20f + droop), d(bx + spread), d(ty + droop))
            quadraticTo(d(bx + 18f + spread), d(ty + 22f + droop), d(bx), d(206f))
            close()
        }
        paint(leaf, vBrush(ty, 206f, colors.leaf, colors.leafDark), colors.inkOf(colors.leaf), 1.8f)
        sketchLine(
            pt(bx, 206f),
            pt(bx + spread * 0.8f, ty + 8f + droop),
            colors.hint(colors.leafDark.a(0.75f)),
            1.2f
        )
    }

    // ── Mug on the sill, catching the cold light off the glass ───────────
    contactShadow(196f, 250f, 22f, 5f, colors.shade)
    val mug = Path().apply {
        moveTo(d(176f), d(214f))
        lineTo(d(216f), d(214f))
        lineTo(d(212f), d(246f))
        quadraticTo(d(196f), d(252f), d(180f), d(246f))
        close()
    }
    val handle = Path().apply {
        moveTo(d(215f), d(220f))
        quadraticTo(d(231f), d(224f), d(229f), d(233f))
        quadraticTo(d(227f), d(241f), d(212f), d(239f))
    }
    paintStroke(handle, colors.paper, colors.ink, width = 7f)
    paint(mug, hBrush(176f, 216f, colors.paper, colors.metal), colors.ink, 2.4f)
    shade(mug, hBrush(196f, 216f, colors.shade.a(0f), colors.shade))
    // cold rim light off the window rather than a warm highlight
    sheen(mug, pt(178f, 218f), pt(192f, 244f), colors.sky.lit(0.4f))
    paint(ellipsePath(196f, 214f, 20f, 6f), colors.paper, colors.ink, 2f)
    paint(ellipsePath(196f, 215f, 15.5f, 4.2f), colors.coffee, colors.line(colors.coffee), 1f)
    innerRim(mug, 0f, 2f, colors.shade, 2f)
    steam(192f, 210f, t, 0.1f, colors.hint(colors.paper.a(0.7f)), height = 40f)
}
