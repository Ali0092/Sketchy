package com.sketchy.library.illustrations

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.withTransform
import com.sketchy.library.SketchyStyle
import com.sketchy.library.utils.*

// ─── Your Workspace at Home ───────────────────────────────────────────────────
//   A desk at the end of the day: an open laptop glowing, a desk lamp pooling
//   warm light across the wood, a plant, a mug still steaming, and a cat curled
//   up in front of the keyboard refusing to move. Outlined, the bulb, the
//   highlighted row on screen, the plant and the cat's nose are the only colour
//   left in the drawing.

internal fun DrawScope.drawHomeWorkspaceScene(t: Float, colors: SketchyStyle) {
    val pulse = (1f + wave(t, 0f)) / 2f       // lamp filament breathing
    val deskY = 212f

    // ── Wall shelf, far background: low contrast so it stays back ───────
    //   Painted, the shelf and its books are flat blocks of colour with no
    //   outline; outlined, they are drawn instead — hence [lineOnly].
    val shelf = rectPath(40f, 96f, 96f, 6f)
    paint(shelf, colors.woodDark.a(0.55f), colors.lineOnly, 1.4f)
    listOf(
        Triple(48f, 68f, colors.terracotta),
        Triple(60f, 74f, colors.fabric),
        Triple(72f, 64f, colors.leaf)
    ).forEach { (bx, h, c) ->
        paint(rectPath(bx, 96f - h + 24f, 9f, h - 24f), c.a(0.5f), colors.lineOnly, 1.4f)
    }
    // a leaning frame, tilted just enough to feel lived-in
    val frame = roundRectPath(96f, 60f, 34f, 36f, 3f)
    fill(frame, colors.paper.a(0.6f))
    drawPath(frame, color = colors.ink.a(0.45f), style = bold(1.4f))

    // ── Desk lamp, upper right — the key light ──────────────────────────
    sketchLine(pt(268f, deskY), pt(268f, 150f), colors.line(colors.metalDark), 3.4f)
    val arm = Path().apply {
        moveTo(d(268f), d(152f))
        quadraticTo(d(268f), d(126f), d(242f), d(122f))
    }
    drawPath(arm, color = colors.line(colors.metalDark), style = bold(3.4f))
    val lampShade = Path().apply {
        moveTo(d(216f), d(150f))
        lineTo(d(232f), d(112f))
        lineTo(d(258f), d(122f))
        lineTo(d(240f), d(156f))
        close()
    }
    paint(lampShade, dBrush(216f, 112f, 258f, 156f, colors.paper, colors.metal), colors.ink, 2.2f)
    // the bulb, blooming over its own housing
    glow(228f, 156f, 30f, colors.sun.a(0.55f + 0.2f * pulse))
    sketchCircle(pt(228f, 152f), 5f, colors.touch(colors.sun, 0.6f), filled = true)
    sketchCircle(pt(228f, 152f), 5f, colors.lineOnly, width = 1.6f)

    // ── Desk: a real surface with ends, seen slightly from above ─────────
    val desk = surfacePath(backY = deskY, frontY = 296f, backInset = 24f, frontInset = 10f)
    fill(desk, vBrush(deskY, 296f, colors.wood, colors.woodDark))
    for (i in 0..3) {
        val gy = deskY + 18f + i * 19f
        val spread = 8f + i * 4f
        sketchLine(
            pt(28f - spread, gy),
            pt(292f + spread, gy - 2f),
            colors.faint(colors.woodDark.a(0.28f)),
            1.2f
        )
    }
    // the pool of lamplight landing on the wood, held inside the desk
    clipPath(desk) {
        glow(214f, 226f, 96f, colors.glow.a(0.42f + 0.12f * pulse))
    }
    sketchLine(pt(24f, deskY), pt(296f, deskY), colors.sun.a(0.6f), 3f)
    drawPath(desk, color = colors.ink, style = bold(2.2f))
    // the far corner of the desk falls away from the lamp
    shade(desk, hBrush(120f, 10f, colors.shade.a(0f), colors.shade))

    // Everything standing on the desk casts its shadow onto it — clipped to the
    // surface so no shadow can float off the edge into empty canvas.
    clipPath(desk) {
        contactShadow(56f, deskY + 4f, 30f, 7f, colors.shade)   // plant
        contactShadow(164f, deskY + 2f, 74f, 9f, colors.shade)  // laptop
        contactShadow(88f, 286f, 50f, 9f, colors.shade)         // cat
        contactShadow(258f, 268f, 26f, 6f, colors.shade)        // mug
    }

    // ── Laptop, centre ──────────────────────────────────────────────────
    // lid, leaning back a touch
    val lid = Path().apply {
        moveTo(d(112f), d(196f))
        lineTo(d(122f), d(122f))
        lineTo(d(226f), d(122f))
        lineTo(d(228f), d(196f))
        close()
    }
    paint(lid, vBrush(122f, 196f, colors.metal, colors.metalDark), colors.ink, 2.4f)
    val screen = Path().apply {
        moveTo(d(120f), d(190f))
        lineTo(d(128f), d(129f))
        lineTo(d(220f), d(129f))
        lineTo(d(221f), d(190f))
        close()
    }
    paint(screen, vBrush(129f, 190f, colors.fabric, colors.fabricDark), colors.ink, 1.4f)
    // content on screen: a title bar and a few lines of work
    paint(rectPath(132f, 136f, 82f, 7f), colors.paper.a(0.7f), colors.lineOnly, 1.2f)
    listOf(150f, 160f, 170f).forEachIndexed { i, ly ->
        paint(
            rectPath(132f, ly, 70f - i * 16f, 5f),
            colors.paper.a(0.45f - i * 0.08f),
            colors.lineOnly,
            1.2f
        )
    }
    // the highlighted row — colour the outlined scene keeps
    paint(rectPath(132f, 180f, 26f, 6f), colors.accent.a(0.85f), colors.lineOnly, 1.2f)
    // glass reflection raking across the screen
    sheen(screen, pt(120f, 190f), pt(200f, 124f), colors.paper.a(0.28f))
    // keyboard deck in perspective, wider at the front
    val deck = Path().apply {
        moveTo(d(112f), d(196f))
        lineTo(d(228f), d(196f))
        lineTo(d(240f), d(214f))
        lineTo(d(100f), d(214f))
        close()
    }
    paint(deck, vBrush(196f, 214f, colors.paper, colors.metal), colors.ink, 2.4f)
    paint(rectPath(126f, 200f, 88f, 8f), colors.metalDark.a(0.35f), colors.lineOnly, 1.2f)
    sketchLine(pt(152f, 211f), pt(188f, 211f), colors.hint(colors.metalDark.a(0.6f)), 3f)
    // screen light spilling onto the deck
    shade(deck, vBrush(196f, 210f, colors.fabric.a(0.35f), colors.fabric.a(0f)))

    // ── Plant, left of the desk ─────────────────────────────────────────
    val pot = Path().apply {
        moveTo(d(36f), d(178f))
        lineTo(d(78f), d(178f))
        lineTo(d(71f), d(214f))
        lineTo(d(43f), d(214f))
        close()
    }
    paint(pot, vBrush(178f, 214f, colors.terracotta, colors.clay), colors.ink, 2.2f)
    shade(pot, hBrush(56f, 78f, colors.shade.a(0f), colors.shade))
    paint(roundRectPath(33f, 172f, 48f, 10f, 3f), colors.terracotta, colors.ink, 2f)
    val sway = 3f * wave(t, 0.2f)
    listOf(
        Triple(44f, 118f, -18f),
        Triple(57f, 100f, 2f),
        Triple(70f, 122f, 18f)
    ).forEach { (bx, ty, spread) ->
        val leaf = Path().apply {
            moveTo(d(bx + 5f), d(172f))
            quadraticTo(d(bx - 12f + spread + sway), d(ty + 22f), d(bx + spread + sway), d(ty))
            quadraticTo(d(bx + 20f + spread + sway), d(ty + 24f), d(bx + 5f), d(172f))
            close()
        }
        paint(leaf, vBrush(ty, 172f, colors.leaf, colors.leafDark), colors.inkOf(colors.leaf), 1.8f)
        sketchLine(
            pt(bx + 5f, 172f),
            pt(bx + spread + sway, ty + 8f),
            colors.hint(colors.leafDark.a(0.7f)),
            1.2f
        )
    }

    // ── Mug, foreground right ───────────────────────────────────────────
    val mug = Path().apply {
        moveTo(d(238f), d(232f))
        lineTo(d(278f), d(232f))
        lineTo(d(274f), d(266f))
        quadraticTo(d(258f), d(272f), d(242f), d(266f))
        close()
    }
    val handle = Path().apply {
        moveTo(d(277f), d(240f))
        quadraticTo(d(294f), d(244f), d(292f), d(254f))
        quadraticTo(d(290f), d(262f), d(274f), d(260f))
    }
    paintStroke(handle, colors.paper, colors.ink, width = 7f)
    paint(mug, hBrush(238f, 278f, colors.paper, colors.metal), colors.ink, 2.4f)
    shade(mug, hBrush(258f, 278f, colors.shade.a(0f), colors.shade))
    sheen(mug, pt(240f, 238f), pt(256f, 264f), colors.paper)
    paint(ellipsePath(258f, 232f, 20f, 6f), colors.paper, colors.ink, 2f)
    paint(ellipsePath(258f, 233f, 15.5f, 4.2f), colors.coffee, colors.line(colors.coffee), 1f)
    steam(254f, 228f, t, 0.15f, colors.hint(colors.paper.a(0.7f)), height = 38f)

    // ── The cat, foreground left, curled and immovable ───────────────────
    // tail, sweeping slowly behind the body
    val tailPivot = pt(126f, 274f)
    withTransform({ rotate(degrees = 7f * wave(t, 0.3f), pivot = tailPivot) }) {
        val tail = Path().apply {
            moveTo(d(126f), d(276f))
            quadraticTo(d(168f), d(280f), d(160f), d(252f))
        }
        paintStroke(tail, colors.woodDark, colors.ink, width = 9f)
    }
    val body = Path().apply {
        moveTo(d(46f), d(288f))
        quadraticTo(d(40f), d(252f), d(76f), d(246f))
        quadraticTo(d(118f), d(240f), d(130f), d(268f))
        quadraticTo(d(136f), d(288f), d(96f), d(292f))
        close()
    }
    paint(body, vBrush(246f, 292f, colors.wood, colors.woodDark), colors.ink, 2.4f)
    shade(body, vBrush(268f, 292f, colors.shade.a(0f), colors.shade))
    // head, tucked down against the body
    val head = ellipsePath(56f, 258f, 21f, 19f)
    paint(head, vBrush(240f, 278f, colors.wood, colors.woodDark), colors.ink, 2.4f)
    listOf(-1f, 1f).forEach { side ->
        val ear = Path().apply {
            moveTo(d(56f + side * 15f), d(246f))
            lineTo(d(56f + side * 19f), d(230f))
            lineTo(d(56f + side * 3f), d(240f))
            close()
        }
        paint(ear, colors.wood, colors.ink, 2f)
        fill(
            Path().apply {
                moveTo(d(56f + side * 14f), d(244f))
                lineTo(d(56f + side * 16.5f), d(235f))
                lineTo(d(56f + side * 7f), d(240f))
                close()
            },
            colors.touch(colors.terracotta, 0.3f)
        )
    }
    // closed eyes, a nose, and whiskers
    listOf(48f, 66f).forEach { ex ->
        val eye = Path().apply {
            moveTo(d(ex - 5f), d(256f))
            quadraticTo(d(ex), d(261f), d(ex + 5f), d(256f))
        }
        drawPath(eye, color = colors.ink, style = thin(1.8f))
    }
    fill(
        Path().apply {
            moveTo(d(53f), d(266f))
            lineTo(d(59f), d(266f))
            lineTo(d(56f), d(270f))
            close()
        },
        colors.touch(colors.clay, 0.75f)
    )
    listOf(-1f, 1f).forEach { side ->
        sketchLine(
            pt(56f + side * 9f, 267f),
            pt(56f + side * 24f, 264f),
            colors.ink.a(0.5f),
            1.2f
        )
        sketchLine(
            pt(56f + side * 9f, 270f),
            pt(56f + side * 23f, 274f),
            colors.ink.a(0.5f),
            1.2f
        )
    }

    twinkle(292f, 88f, 4f, t, 0.4f, colors.touch(colors.sun))
    twinkle(28f, 132f, 3f, t, 0.75f, colors.touch(colors.paper.a(0.9f)))
}
