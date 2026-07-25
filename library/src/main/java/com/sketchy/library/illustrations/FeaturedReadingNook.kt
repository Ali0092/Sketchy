package com.sketchy.library.illustrations

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.sketchy.library.SketchyStyle
import com.sketchy.library.utils.*

// ─── A Quiet Reading Corner ───────────────────────────────────────────────────
//   An armchair under a floor lamp with a book left open on the seat, a mug on a
//   stack of paperbacks, and a blanket over the arm. Fully painted, warm lamp
//   light from the upper right falling away into the corner of the room.

internal fun DrawScope.drawReadingNookScene(t: Float, colors: SketchyStyle) {
    val lampPulse = pulse(t, 0f)

    // ── Floor lamp, upper right — the key light ──────────────────────────
    contactShadow(276f, 292f, 26f, 7f, colors.shade)
    sketchLine(pt(276f, 288f), pt(276f, 98f), colors.line(colors.metalDark), 3.6f)
    sketchLine(pt(276f, 288f), pt(276f, 98f), colors.metal.lit(0.35f), 1.4f)
    paint(ellipsePath(276f, 290f, 22f, 6f), colors.metalDark, colors.lineOnly, 1.8f)
    val lampShade = Path().apply {
        moveTo(d(248f), d(96f))
        lineTo(d(258f), d(52f))
        lineTo(d(294f), d(52f))
        lineTo(d(304f), d(96f))
        close()
    }
    paint(lampShade, vBrush(52f, 96f, colors.sun.lit(0.4f), colors.sunDeep), colors.ink, 2.4f)
    innerRim(lampShade, 0f, -3f, colors.clay.a(0.45f), 2.4f)
    // the light itself: a hot mouth, a halo, and a cone falling to the left
    fill(ellipsePath(276f, 96f, 28f, 6f), colors.touch(colors.sun.lit(0.55f), 0.55f))
    glow(276f, 104f, 62f, colors.sun.a(0.5f + 0.15f * lampPulse))
    val cone = Path().apply {
        moveTo(d(250f), d(98f))
        lineTo(d(302f), d(98f))
        lineTo(d(272f), d(286f))
        lineTo(d(178f), d(286f))
        close()
    }
    fill(cone, vBrush(98f, 286f, colors.glow.a(0.30f + 0.08f * lampPulse), colors.glow.a(0f)))

    // ── The armchair ────────────────────────────────────────────────────
    contactShadow(150f, 292f, 108f, 12f, colors.shade)
    // back rest
    val back = roundRectPath(76f, 122f, 152f, 122f, 24f)
    paint(back, vBrush(122f, 244f, colors.fabric.lit(0.26f), colors.fabricDark), colors.ink, 2.4f)
    shade(back, hBrush(170f, 76f, colors.shade.a(0f), colors.shade))
    // buttoned seams down the back
    listOf(116f, 152f, 188f).forEach { sx ->
        sketchLine(pt(sx, 138f), pt(sx, 232f), colors.hint(colors.fabricDark.a(0.7f)), 1.6f)
        sketchCircle(pt(sx, 176f), 2.6f, colors.line(colors.fabricDark), filled = true)
    }
    // seat cushion, wider at the front for perspective
    val seat = Path().apply {
        moveTo(d(74f), d(226f))
        lineTo(d(230f), d(226f))
        lineTo(d(246f), d(274f))
        lineTo(d(58f), d(274f))
        close()
    }
    paint(seat, vBrush(226f, 274f, colors.fabric.lit(0.34f), colors.fabricDark), colors.ink, 2.4f)
    shade(seat, hBrush(180f, 58f, colors.shade.a(0f), colors.shade))
    sketchLine(pt(88f, 238f), pt(222f, 238f), colors.hint(colors.fabricDark.a(0.6f)), 1.6f)
    // arms, in front of both
    listOf(true, false).forEach { left ->
        val ax = if (left) 44f else 226f
        val arm = roundRectPath(ax, 186f, 44f, 84f, 18f)
        paint(
            arm,
            vBrush(186f, 270f, colors.fabric.lit(if (left) 0.16f else 0.36f), colors.fabricDark),
            colors.ink,
            2.4f
        )
        shade(
            arm,
            hBrush(if (left) ax + 44f else ax, if (left) ax else ax + 44f, colors.shade, colors.shade.a(0f))
        )
    }
    // stubby wooden legs
    listOf(78f, 214f).forEach { lx ->
        paint(roundRectPath(lx, 272f, 14f, 22f, 4f), colors.woodDark, colors.ink, 2f)
    }

    // ── Blanket thrown over the right arm ───────────────────────────────
    val blanket = Path().apply {
        moveTo(d(224f), d(196f))
        quadraticTo(d(258f), d(190f), d(272f), d(210f))
        quadraticTo(d(266f), d(248f), d(250f), d(266f))
        quadraticTo(d(232f), d(240f), d(224f), d(196f))
        close()
    }
    paint(blanket, dBrush(224f, 190f, 272f, 266f, colors.terracotta.lit(0.3f), colors.clay), colors.ink, 2.2f)
    listOf(0f, 1f, 2f).forEach { i ->
        val fold = Path().apply {
            moveTo(d(232f + i * 12f), d(200f + i * 6f))
            quadraticTo(d(244f + i * 10f), d(226f), d(238f + i * 8f), d(256f - i * 6f))
        }
        drawPath(fold, color = colors.hint(colors.clay.a(0.6f)), style = thin(1.6f))
    }

    // ── The open book, left on the seat ─────────────────────────────────
    contactShadow(152f, 258f, 48f, 7f, colors.shade)
    // the page nearest the lamp lifts and settles, as if just put down
    val lift = 4f * lampPulse
    val leftPage = Path().apply {
        moveTo(d(106f), d(240f))
        lineTo(d(151f), d(222f))
        lineTo(d(151f), d(248f))
        lineTo(d(110f), d(258f))
        close()
    }
    val rightPage = Path().apply {
        moveTo(d(151f), d(222f))
        lineTo(d(198f), d(238f - lift))
        lineTo(d(194f), d(256f - lift))
        lineTo(d(151f), d(248f))
        close()
    }
    // the shadowed page, then the lamp-lit one
    paint(leftPage, vBrush(222f, 258f, colors.paper, colors.metal), colors.ink, 2f)
    paint(rightPage, vBrush(222f, 256f, colors.paper.lit(0.4f), colors.metal.lit(0.2f)), colors.ink, 2f)
    // lines of text, fading as the page turns away
    for (i in 0..3) {
        sketchLine(
            pt(114f, 240f + i * 4.5f),
            pt(146f, 231f + i * 4.5f),
            colors.inkSoft.a(0.4f),
            1.2f
        )
        sketchLine(
            pt(158f, 231f + i * 4.5f - lift),
            pt(188f, 240f + i * 4.5f - lift),
            colors.inkSoft.a(0.5f),
            1.2f
        )
    }
    // the spine crease, and a ribbon marker trailing off the seat
    sketchLine(pt(151f, 222f), pt(151f, 248f), colors.hint(colors.shade), 2.4f)
    val ribbon = Path().apply {
        moveTo(d(160f), d(226f))
        quadraticTo(d(172f), d(252f), d(166f), d(276f))
    }
    // the ribbon marker: colour the outlined drawing keeps
    drawPath(ribbon, color = colors.accent, style = bold(3.4f))

    // ── Book stack and mug, foreground left ─────────────────────────────
    contactShadow(60f, 296f, 44f, 8f, colors.shade)
    listOf(
        Triple(24f, 278f, colors.fabricDark),
        Triple(28f, 262f, colors.terracotta),
        Triple(22f, 246f, colors.leafDark)
    ).forEachIndexed { i, (bx, by, spine) ->
        val book = roundRectPath(bx, by, 78f - i * 2f, 17f, 3f)
        // outlined, the three spines are drawn in three different accents
        paint(book, vBrush(by, by + 17f, spine.lit(0.3f), spine), colors.inkOf(spine), 2.2f)
        // the block of pages along the front edge
        paint(rectPath(bx + 5f, by + 4f, 68f - i * 2f, 9f), colors.paper.a(0.85f), colors.lineOnly, 1.2f)
        listOf(0f, 1f, 2f).forEach { p ->
            sketchLine(
                pt(bx + 6f, by + 6f + p * 3f),
                pt(bx + 70f - i * 2f, by + 6f + p * 3f),
                colors.faint(colors.metal.a(0.6f)),
                1f
            )
        }
    }
    // mug resting on top of the stack
    contactShadow(62f, 246f, 20f, 4f, colors.shade)
    val mug = Path().apply {
        moveTo(d(44f), d(214f))
        lineTo(d(80f), d(214f))
        lineTo(d(76f), d(242f))
        quadraticTo(d(62f), d(248f), d(48f), d(242f))
        close()
    }
    val handle = Path().apply {
        moveTo(d(79f), d(220f))
        quadraticTo(d(94f), d(224f), d(92f), d(232f))
        quadraticTo(d(90f), d(239f), d(76f), d(237f))
    }
    paintStroke(handle, colors.paper, colors.ink, width = 6.5f)
    paint(mug, hBrush(44f, 80f, colors.paper, colors.metal), colors.ink, 2.4f)
    shade(mug, hBrush(62f, 80f, colors.shade.a(0f), colors.shade))
    sheen(mug, pt(46f, 218f), pt(60f, 240f), colors.paper)
    paint(ellipsePath(62f, 214f, 18f, 5.5f), colors.paper, colors.ink, 2f)
    paint(ellipsePath(62f, 215f, 14f, 4f), colors.coffee, colors.line(colors.coffee), 1f)
    innerRim(mug, 0f, 2f, colors.shade, 2f)
    steam(58f, 210f, t, 0.2f, colors.hint(colors.paper.a(0.75f)), height = 40f)

    twinkle(216f, 82f, 4f, t, 0.4f, colors.touch(colors.sun))
    twinkle(38f, 158f, 3f, t, 0.8f, colors.touch(colors.paper.a(0.85f)))
    groundHint(300f, colors.inkFaint)
}
