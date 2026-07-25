package com.sketchy.library.illustrations

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.withTransform
import com.sketchy.library.SketchyStyle
import com.sketchy.library.utils.*

// ─── The Weekly Grocery Run ───────────────────────────────────────────────────
//   A cart heaped with fresh produce rolling down an aisle, a paper bag packed
//   and waiting. Fully painted, with the aisle behind it faded back by haze.

internal fun DrawScope.drawGroceryRunScene(t: Float, colors: SketchyStyle) {
    // ── Aisle shelving, far background ──────────────────────────────────
    //   Two shelves of goods, held at low contrast so they read as distance
    //   rather than competing with the cart.
    listOf(84f, 148f).forEachIndexed { row, shelfY ->
        // the goods, sized and spaced off hash01 so no two rows repeat
        for (i in 0..7) {
            val bx = 44f + i * 28f + 6f * hash01(row * 9 + i)
            val bh = 22f + 14f * hash01(row * 9 + i + 30)
            val hue = when ((row + i) % 4) {
                0 -> colors.terracotta
                1 -> colors.fabric
                2 -> colors.leaf
                else -> colors.sun
            }
            paint(
                roundRectPath(bx, shelfY - bh, 18f, bh, 2f),
                hue.a(0.34f + 0.14f * hash01(i + row)),
                colors.lineOnly,
                1.2f
            )
        }
        paint(
            roundRectPath(38f, shelfY, 246f, 6f, 2f),
            colors.metal.a(0.75f),
            colors.line(colors.metalDark.a(0.55f)),
            1.4f
        )
    }

    // ── The cart, rocking gently as it rolls ────────────────────────────
    val cartPivot = pt(168f, 250f)
    withTransform({ rotate(degrees = 1.4f * wave(t, 0f), pivot = cartPivot) }) {
        contactShadow(168f, 254f, 78f, 10f, colors.shade)

        // handle, drawn first so the basket overlaps it
        val handle = Path().apply {
            moveTo(d(248f), d(150f))
            quadraticTo(d(278f), d(146f), d(282f), d(118f))
        }
        paintStroke(
            handle,
            colors.metal.lit(0.3f),
            colors.line(colors.metalDark),
            width = 2f,
            outline = 1.75f
        )

        // legs and lower tray
        sketchLine(pt(108f, 212f), pt(116f, 240f), colors.line(colors.metalDark), 3.4f)
        sketchLine(pt(230f, 212f), pt(222f, 240f), colors.line(colors.metalDark), 3.4f)
        sketchLine(pt(112f, 226f), pt(226f, 226f), colors.line(colors.metalDark.a(0.8f)), 2.6f)

        // basket: a trapezoid, wider at the rim
        val basket = Path().apply {
            moveTo(d(84f), d(148f))
            lineTo(d(248f), d(148f))
            lineTo(d(232f), d(214f))
            lineTo(d(100f), d(214f))
            close()
        }
        paint(basket, vBrush(148f, 214f, colors.metal.lit(0.35f), colors.metalDark), colors.ink, 2.4f)
        // the wire mesh, clipped so it can never escape the basket
        clipPath(basket) {
            for (i in 1..7) {
                val fx = 84f + i * 20.5f
                sketchLine(pt(fx, 148f), pt(fx - 2f, 214f), colors.faint(colors.metalDark.a(0.5f)), 1.4f)
            }
            listOf(166f, 186f, 206f).forEach { hy ->
                sketchLine(pt(84f, hy), pt(248f, hy), colors.faint(colors.metalDark.a(0.45f)), 1.4f)
            }
        }
        shade(basket, hBrush(170f, 248f, colors.shade.a(0f), colors.shade))
        // the rim catches the light along its whole length
        sketchLine(pt(84f, 148f), pt(248f, 148f), colors.paper.a(0.8f), 3f)
        sketchLine(pt(84f, 148f), pt(248f, 148f), colors.ink, 2f)

        // ── Produce heaped above the rim ─────────────────────────────────
        // leafy greens at the back
        listOf(104f, 122f, 138f).forEachIndexed { i, lx ->
            val leaf = Path().apply {
                moveTo(d(lx), d(150f))
                quadraticTo(d(lx - 16f), d(126f), d(lx + 2f), d(110f))
                quadraticTo(d(lx + 18f), d(128f), d(lx), d(150f))
                close()
            }
            paint(leaf, vBrush(110f, 150f, colors.leaf.lit(0.2f), colors.leafDark), colors.inkOf(colors.leaf), 1.8f)
            sketchLine(pt(lx, 148f), pt(lx + 1f, 116f), colors.hint(colors.leafDark.a(0.8f)), 1.2f)
            twinkle(lx + 6f, 118f + i * 4f, 2f, t, hash01(i), colors.touch(colors.paper.a(0.7f)))
        }
        // a baguette leaning out of the basket
        val baguette = Path().apply {
            moveTo(d(168f), d(150f))
            quadraticTo(d(176f), d(120f), d(196f), d(96f))
            lineTo(d(208f), d(104f))
            quadraticTo(d(190f), d(126f), d(182f), d(152f))
            close()
        }
        paint(baguette, dBrush(168f, 150f, 208f, 96f, colors.sun, colors.clay), colors.ink, 2f)
        listOf(0f, 1f, 2f).forEach { i ->
            sketchLine(
                pt(180f + i * 5f, 128f - i * 11f),
                pt(190f + i * 5f, 122f - i * 11f),
                colors.hint(colors.clay.a(0.8f)),
                1.6f
            )
        }
        // apples and oranges, the top one bobbing as the cart rolls
        val bob = 2.5f * wave(t, 0.3f)
        listOf(
            Triple(154f, 138f + bob, colors.clay),
            Triple(214f, 136f, colors.sunDeep),
            Triple(236f, 144f, colors.sun),
            Triple(192f, 142f, colors.leaf)
        ).forEach { (cx, cy, hue) ->
            val fruit = ellipsePath(cx, cy, 15f, 14f)
            fill(fruit, glowBrush(cx - 5f, cy - 5f, 20f, hue.lit(0.42f)))
            fill(fruit, hue.a(0.72f))
            // outlined, each fruit keeps its own hue as its outline
            drawPath(fruit, color = colors.inkOf(hue), style = bold(2f))
            fill(ellipsePath(cx - 5f, cy - 6f, 4.5f, 3f), colors.paper.a(0.75f))
        }
        // a stalk on the apple
        sketchLine(pt(154f, 124f + bob), pt(157f, 117f + bob), colors.line(colors.woodDark), 2f)

        // ── Wheels, turning as it rolls ──────────────────────────────────
        listOf(116f, 222f).forEachIndexed { i, wx ->
            contactShadow(wx, 260f, 13f, 4f, colors.shade)
            val hub = pt(wx, 248f)
            sketchCircle(hub, 11f, colors.metalDark, filled = true)
            sketchCircle(hub, 11f, colors.ink, width = 2f)
            withTransform({
                rotate(degrees = (t * 360f + i * 90f) % 360f, pivot = hub)
            }) {
                sketchLine(pt(wx - 7f, 248f), pt(wx + 7f, 248f), colors.hint(colors.metal.lit(0.4f)), 2f)
                sketchLine(pt(wx, 241f), pt(wx, 255f), colors.hint(colors.metal.lit(0.4f)), 2f)
            }
            sketchCircle(hub, 3f, colors.paper, filled = true)
        }
    }

    // ── Packed paper bag, foreground right ──────────────────────────────
    //   Straight-sided with a folded-over rim and a visible side gusset, so it
    //   reads as a paper sack rather than as another plant pot.
    contactShadow(274f, 272f, 32f, 7f, colors.shade)
    val bag = Path().apply {
        moveTo(d(246f), d(186f))
        lineTo(d(304f), d(186f))
        lineTo(d(302f), d(270f))
        lineTo(d(248f), d(270f))
        close()
    }
    paint(bag, hBrush(246f, 304f, colors.wood.lit(0.34f), colors.woodDark), colors.ink, 2.4f)
    // the gusset fold running the full height, and the crease beside it
    sketchLine(pt(284f, 190f), pt(284f, 268f), colors.hint(colors.woodDark.a(0.65f)), 1.8f)
    sketchLine(pt(262f, 194f), pt(262f, 268f), colors.faint(colors.woodDark.a(0.35f)), 1.2f)
    shade(bag, hBrush(286f, 304f, colors.shade.a(0f), colors.shade))
    // the rolled-over rim at the top of the sack
    paint(
        roundRectPath(243f, 178f, 64f, 14f, 4f),
        vBrush(178f, 192f, colors.wood.lit(0.44f), colors.wood),
        colors.ink,
        2.2f
    )
    // a baguette and some greens standing up out of it
    val loaf = Path().apply {
        moveTo(d(266f), d(180f))
        quadraticTo(d(268f), d(148f), d(280f), d(126f))
        lineTo(d(292f), d(132f))
        quadraticTo(d(282f), d(154f), d(280f), d(180f))
        close()
    }
    paint(loaf, dBrush(266f, 180f, 292f, 126f, colors.sun, colors.clay), colors.ink, 2f)
    listOf(0f, 1f).forEach { i ->
        sketchLine(
            pt(272f + i * 4f, 158f - i * 14f),
            pt(282f + i * 4f, 152f - i * 14f),
            colors.hint(colors.clay.a(0.8f)),
            1.6f
        )
    }
    val sprig = Path().apply {
        moveTo(d(256f), d(180f))
        quadraticTo(d(238f), d(166f), d(244f), d(146f))
        quadraticTo(d(256f), d(160f), d(256f), d(180f))
        close()
    }
    paint(sprig, vBrush(146f, 180f, colors.leaf, colors.leafDark), colors.inkOf(colors.leaf), 1.8f)

    // the aisle floor, hinted at rather than painted, as in every other scene
    groundHint(280f, colors.inkFaint)
    twinkle(48f, 214f, 4f, t, 0.45f, colors.touch(colors.sun))
}
