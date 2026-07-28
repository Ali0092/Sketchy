package com.sketchy.library.illustrations

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipPath
import com.sketchy.library.SketchyStyle
import com.sketchy.library.utils.*

// ─── A Slow Morning Coffee ────────────────────────────────────────────────────
//   Sunrise pouring through a window onto a wooden table, a steaming ceramic
//   mug beside a croissant on a saucer, a little plant catching the light.
//   Outlined, it is a pen drawing of that morning with the sun and the leaves
//   the only colour in it; painted, the warm key light falls from the upper
//   left and the cool shadows fall to the right.

internal fun DrawScope.drawMorningCoffeeScene(t: Float, colors: SketchyStyle) {
    val breathe = (1f + wave(t, 0f)) / 2f     // 0..1, the sun slowly brightening

    // ── Window, upper left ──────────────────────────────────────────────
    //   Its sill lands just behind the table's back edge, so on a transparent
    //   canvas the window and the table read as one grouped scene rather than
    //   as two objects floating apart.
    val paneX = 44f
    val paneY = 84f
    val paneW = 124f
    val paneH = 116f
    val pane = roundRectPath(paneX, paneY, paneW, paneH, 8f)
    fill(pane, vBrush(paneY, paneY + paneH, colors.sky, colors.paper))
    // everything beyond the glass is clipped to it, so the morning stays outside
    clipPath(pane) {
        // distant hills, faded by the haze of the morning
        val hills = Path().apply {
            moveTo(d(paneX), d(paneY + 78f))
            quadraticTo(d(paneX + 34f), d(paneY + 56f), d(paneX + 64f), d(paneY + 76f))
            quadraticTo(d(paneX + 92f), d(paneY + 60f), d(paneX + paneW), d(paneY + 80f))
            lineTo(d(paneX + paneW), d(paneY + paneH))
            lineTo(d(paneX), d(paneY + paneH))
            close()
        }
        // painted they are pure haze, so the line drawing supplies their ridge
        paint(hills, colors.skyDeep.a(0.45f), colors.lineOnly, 1.6f)
        // the sun itself, low and hazy — and the warm mark the outlined scene keeps
        val sunY = paneY + 64f - 4f * breathe
        glow(paneX + 78f, sunY, 40f, colors.sun.a(0.6f + 0.2f * breathe))
        sketchCircle(pt(paneX + 78f, sunY), 13f, colors.touch(colors.sun, 0.5f), filled = true)
        sketchCircle(pt(paneX + 78f, sunY), 13f, colors.lineOnly, width = 1.8f)
        // light gathering along the bottom of the glass
        fill(
            rectPath(paneX, paneY + paneH * 0.4f, paneW, paneH * 0.6f),
            vBrush(paneY + paneH * 0.4f, paneY + paneH, colors.glow.a(0f), colors.glow)
        )
    }
    // frame + mullions
    drawPath(pane, color = colors.ink, style = bold(2.4f))
    sketchLine(pt(paneX + paneW / 2f, paneY), pt(paneX + paneW / 2f, paneY + paneH), colors.ink, 2f)
    sketchLine(pt(paneX, paneY + paneH / 2f), pt(paneX + paneW, paneY + paneH / 2f), colors.ink, 2f)
    // sill
    val sill = roundRectPath(paneX - 8f, paneY + paneH, paneW + 16f, 9f, 3f)
    paint(sill, vBrush(paneY + paneH, paneY + paneH + 9f, colors.wood, colors.woodDark), colors.ink, 1.8f)

    // ── Table: a top surface, a front edge, and legs ─────────────────────
    val tableY = 216f
    val table = surfacePath(backY = tableY, frontY = 264f, backInset = 26f, frontInset = 14f, r = 10f)
    fill(table, vBrush(tableY, 264f, colors.wood, colors.woodDark))
    // grain, running the length of the boards
    for (i in 0..2) {
        val gy = tableY + 13f + i * 14f
        val spread = 6f + i * 4f
        sketchLine(
            pt(30f - spread, gy),
            pt(290f + spread, gy + 2f),
            colors.faint(colors.woodDark.a(0.30f)),
            1.2f
        )
    }
    // the lit back edge of the table, catching the window light
    sketchLine(pt(26f, tableY), pt(294f, tableY), colors.sun.a(0.75f), 3f)
    drawPath(table, color = colors.ink, style = bold(2.2f))
    shade(table, hBrush(120f, 300f, colors.shade.a(0f), colors.shade))
    // legs, cropped by the bottom of the canvas, then the front edge over them
    listOf(44f, 250f).forEach { lx ->
        paint(
            rectPath(lx, 274f, 26f, 40f),
            vBrush(274f, 314f, colors.woodDark, colors.woodDark.shaded(0.35f)),
            colors.ink,
            2f
        )
    }
    paint(
        roundRectPath(14f, 262f, 292f, 16f, 5f),
        vBrush(262f, 278f, colors.wood.shaded(0.18f), colors.woodDark.shaded(0.28f)),
        colors.ink,
        2.2f
    )

    // Everything standing on the table casts its shadow onto it — clipped to the
    // surface so no shadow can float off the edge into empty canvas.
    clipPath(table) {
        contactShadow(58f, 216f, 26f, 6f, colors.shade)     // plant
        contactShadow(158f, 216f, 40f, 8f, colors.shade)    // mug
        contactShadow(238f, 214f, 44f, 8f, colors.shade)    // saucer
    }

    // ── Saucer + croissant, midground right ─────────────────────────────
    val saucer = ellipsePath(238f, 208f, 44f, 12f)
    paint(saucer, vBrush(196f, 220f, colors.paper, colors.metal), colors.ink, 1.8f)
    fill(ellipsePath(238f, 206f, 33f, 8f), colors.shadeSoft)
    // a crescent with tapered horns, not a blob: the tips curl down and in
    val croissant = Path().apply {
        moveTo(d(208f), d(204f))
        quadraticTo(d(202f), d(182f), d(220f), d(178f))
        quadraticTo(d(238f), d(172f), d(256f), d(178f))
        quadraticTo(d(274f), d(182f), d(268f), d(204f))
        quadraticTo(d(256f), d(194f), d(238f), d(196f))
        quadraticTo(d(220f), d(194f), d(208f), d(204f))
        close()
    }
    paint(croissant, vBrush(172f, 204f, colors.sun, colors.clay), colors.ink, 2f)
    // the rolled segments, each ridge catching the light on its upper edge
    listOf(-18f, -6f, 6f, 18f).forEach { off ->
        val ridge = Path().apply {
            moveTo(d(238f + off), d(176f + off * off * 0.014f))
            quadraticTo(d(238f + off * 1.1f), d(188f), d(238f + off * 1.3f), d(196f))
        }
        drawPath(ridge, color = colors.hint(colors.clay.a(0.75f)), style = thin(1.6f))
    }
    sheen(croissant, pt(212f, 174f), pt(238f, 194f), colors.paper.a(0.6f))

    // ── The mug: the hero, foreground centre-left ────────────────────────
    val mug = Path().apply {
        moveTo(d(126f), d(152f))
        lineTo(d(190f), d(152f))
        lineTo(d(184f), d(212f))
        quadraticTo(d(158f), d(220f), d(132f), d(212f))
        close()
    }
    // handle first so it tucks behind the body
    val handle = Path().apply {
        moveTo(d(188f), d(166f))
        quadraticTo(d(216f), d(170f), d(214f), d(186f))
        quadraticTo(d(212f), d(200f), d(184f), d(198f))
    }
    paintStroke(handle, colors.paper, colors.ink, width = 9f)
    paint(mug, hBrush(126f, 190f, colors.paper, colors.metal), colors.ink, 2.4f)
    // ceramic curvature: a cool shadow down the right, a bright edge on the left
    shade(mug, hBrush(150f, 190f, colors.shade.a(0f), colors.shade))
    sheen(mug, pt(128f, 160f), pt(150f, 200f), colors.paper)
    // rim seen at table height, and the coffee inside it
    val rim = ellipsePath(158f, 152f, 32f, 9.5f)
    paint(rim, colors.paper, colors.ink, 2.2f)
    val brew = ellipsePath(158f, 153f, 26f, 7.2f)
    paint(
        brew,
        vBrush(146f, 160f, colors.coffee, colors.coffee.shaded(0.5f)),
        colors.line(colors.coffee),
        1.2f
    )
    // a glint on the surface of the coffee, drifting with the steam
    fill(
        ellipsePath(150f + 1.5f * wave(t, 0.2f), 151f, 8f, 2f),
        colors.touch(colors.sun, 0.45f)
    )

    // steam, two wisps on slightly different phases
    steam(150f, 148f, t, 0f, colors.hint(colors.paper.a(0.85f)), height = 52f)
    steam(166f, 148f, t, 0.4f, colors.hint(colors.paper.a(0.7f)), height = 44f)

    // ── Plant on the table, far left ────────────────────────────────────
    val pot = Path().apply {
        moveTo(d(38f), d(184f))
        lineTo(d(78f), d(184f))
        lineTo(d(72f), d(214f))
        lineTo(d(44f), d(214f))
        close()
    }
    paint(pot, vBrush(184f, 214f, colors.terracotta, colors.clay), colors.ink, 2.2f)
    shade(pot, hBrush(58f, 78f, colors.shade.a(0f), colors.shade))
    val potLip = roundRectPath(35f, 178f, 46f, 9f, 3f)
    paint(potLip, colors.terracotta, colors.ink, 2f)
    // three leaves, leaning towards the light
    val leafSway = 2.5f * wave(t, 0.15f)
    listOf(
        Triple(46f, 150f, -16f),
        Triple(58f, 138f, 0f),
        Triple(70f, 152f, 14f)
    ).forEach { (bx, ty, spread) ->
        val leaf = Path().apply {
            moveTo(d(bx + 6f), d(178f))
            quadraticTo(
                d(bx - 8f + spread + leafSway), d(ty + 14f),
                d(bx + spread + leafSway), d(ty)
            )
            quadraticTo(
                d(bx + 16f + spread + leafSway), d(ty + 16f),
                d(bx + 6f), d(178f)
            )
            close()
        }
        // the one shape the line drawing lets keep its hue, in green
        paint(leaf, vBrush(ty, 178f, colors.leaf, colors.leafDark), colors.inkOf(colors.leaf), 1.8f)
    }

    twinkle(272f, 66f, 4f, t, 0.35f, colors.touch(colors.sun))
    twinkle(214f, 110f, 3f, t, 0.7f, colors.touch(colors.paper.a(0.9f)))
}
