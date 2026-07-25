package com.sketchy.library.illustrations

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import com.sketchy.library.SketchyStyle
import com.sketchy.library.utils.*

// ─── Track Every Expense ───────────────────────────────────────────────────────
//   A person checking a phone with a rising bar chart, coins dropping into a
//   jar beside them.

internal fun DrawScope.drawTrackSpendingScene(t: Float, colors: SketchyStyle) {
    contactShadow(122f, 264f, 46f, 6f, colors.shade)

    // head + hair
    paintCircle(pt(122f, 104f), 21f, colors.skin, colors.ink, 2.4f)
    val hair = Path().apply {
        moveTo(d(104f), d(96f))
        quadraticTo(d(100f), d(80f), d(122f), d(78f))
        quadraticTo(d(144f), d(80f), d(140f), d(96f))
    }
    limb(hair, colors.hair, colors.ink, 2.4f, thickness = 9f)
    sketchCircle(pt(128f, 104f), 1.5f, colors.ink, filled = true)
    val smile = Path().apply {
        moveTo(d(120f), d(111f))
        quadraticTo(d(123f), d(114f), d(126f), d(111f))
    }
    stroke(smile, colors.ink, 1.8f)
    val neck = Path().apply {
        moveTo(d(122f), d(125f))
        lineTo(d(122f), d(133f))
    }
    limb(neck, colors.skinDark, colors.ink, 2.4f, thickness = 9f)

    // body leaning slightly forward, arms holding a phone up
    val body = Path().apply {
        moveTo(d(122f), d(133f))
        quadraticTo(d(100f), d(138f), d(94f), d(150f))
        lineTo(d(88f), d(216f))
        quadraticTo(d(122f), d(228f), d(156f), d(216f))
        lineTo(d(150f), d(150f))
        quadraticTo(d(144f), d(138f), d(122f), d(133f))
        close()
    }
    paint(body, vBrush(133f, 228f, colors.fabric.lit(0.3f), colors.fabricDark), colors.ink, 2.4f)
    shade(body, hBrush(122f, 156f, colors.shade.a(0f), colors.shade))
    listOf(
        Path().apply {
            moveTo(d(96f), d(220f))
            lineTo(d(90f), d(260f))
        },
        Path().apply {
            moveTo(d(148f), d(220f))
            lineTo(d(154f), d(260f))
        }
    ).forEach { leg -> limb(leg, colors.fabricDark, colors.ink, 2.4f, thickness = 10f) }
    sketchLine(pt(84f, 260f), pt(100f, 260f), colors.ink)
    sketchLine(pt(146f, 260f), pt(162f, 260f), colors.ink)

    // arms reaching to the phone
    val armL = Path().apply {
        moveTo(d(96f), d(150f))
        quadraticTo(d(150f), d(150f), d(170f), d(140f))
    }
    val armR = Path().apply {
        moveTo(d(148f), d(150f))
        quadraticTo(d(168f), d(158f), d(178f), d(150f))
    }
    limb(armL, colors.skin, colors.ink, 2.4f, thickness = 8f)
    limb(armR, colors.skin, colors.ink, 2.4f, thickness = 8f)

    // phone with a bar chart that rises bar by bar, looping
    val phone = Path().apply {
        moveTo(d(150f), d(96f))
        lineTo(d(202f), d(96f))
        lineTo(d(202f), d(160f))
        lineTo(d(150f), d(160f))
        close()
    }
    paint(phone, vBrush(96f, 160f, colors.metal.lit(0.35f), colors.metalDark), colors.ink, 2.4f)
    sheen(phone, pt(152f, 156f), pt(200f, 100f), colors.paper.a(0.4f))
    val heights = listOf(14f, 24f, 34f)
    heights.forEachIndexed { i, h ->
        val growAt = 0.1f + i * 0.2f
        val grown = if (t > growAt) h * ((t - growAt) / 0.15f).coerceAtMost(1f) else 0f
        val x = 162f + i * 14f
        sketchLine(pt(x, 148f), pt(x, 148f - grown), colors.accent, 4f)
    }

    // coin jar with a coin dropping in on a loop
    val jar = Path().apply {
        moveTo(d(230f), d(200f))
        lineTo(d(230f), d(250f))
        quadraticTo(d(230f), d(258f), d(238f), d(258f))
        lineTo(d(266f), d(258f))
        quadraticTo(d(274f), d(258f), d(274f), d(250f))
        lineTo(d(274f), d(200f))
    }
    paint(jar, vBrush(200f, 258f, colors.sky.a(0.45f), colors.skyDeep.a(0.55f)), colors.ink, 2.2f)
    val drop = t % 1f
    val coinY = 178f + drop * 60f
    val coinAlpha = if (drop < 0.85f) 1f else (1f - drop) / 0.15f
    sketchCircle(pt(252f, coinY.coerceAtMost(236f)), 9f, colors.accent.copy(alpha = coinAlpha), width = 2.2f)
    sketchLine(pt(248f, 216f), pt(256f, 216f), colors.accentBlue.copy(alpha = 0.5f), 2f)

    twinkle(60f, 90f, 3f, t, 0.3f, colors.inkSoft)
    twinkle(220f, 76f, 3f, t, 0.6f, colors.accent)
    groundHint(276f, colors.inkFaint)
}

// ─── Watch Your Savings Grow ──────────────────────────────────────────────────
//   A person watering a money tree growing out of a piggy bank, a rising
//   savings line drifting in the background.

internal fun DrawScope.drawGrowSavingsScene(t: Float, colors: SketchyStyle) {
    // background rising savings line
    val riseT = t * t * (3f - 2f * t)
    val line = Path().apply {
        moveTo(d(30f), d(260f))
        lineTo(d(80f), d(260f - 20f * riseT))
        lineTo(d(140f), d(260f - 40f * riseT))
        lineTo(d(200f), d(260f - 70f * riseT))
        lineTo(d(270f), d(260f - 110f * riseT))
    }
    drawPath(line, color = colors.inkFaint, style = thin(1.6f))

    // piggy bank
    val piggy = Path().apply {
        moveTo(d(140f), d(210f))
        quadraticTo(d(110f), d(210f), d(108f), d(240f))
        quadraticTo(d(108f), d(266f), d(140f), d(268f))
        quadraticTo(d(190f), d(270f), d(206f), d(250f))
        quadraticTo(d(220f), d(230f), d(206f), d(212f))
        quadraticTo(d(190f), d(198f), d(160f), d(202f))
        close()
    }
    contactShadow(160f, 276f, 56f, 6f, colors.shade)
    paint(piggy, vBrush(198f, 270f, colors.terracotta.lit(0.3f), colors.clay), colors.ink, 2.4f)
    shade(piggy, hBrush(160f, 220f, colors.shade.a(0f), colors.shade))
    sketchLine(pt(150f, 218f), pt(166f, 214f), colors.ink, 2.4f)
    sketchCircle(pt(120f, 236f), 2f, colors.ink, filled = true)
    sketchLine(pt(112f, 262f), pt(116f, 274f), colors.ink)
    sketchLine(pt(176f, 266f), pt(180f, 278f), colors.ink)

    // tree growing from the slot, leaves are coin-shaped and sway
    val trunk = Path().apply {
        moveTo(d(158f), d(214f))
        quadraticTo(d(154f), d(170f), d(160f), d(130f))
    }
    limb(trunk, colors.woodDark, colors.accentGreen, 2.4f, thickness = 6f)
    val sway = 3f * wave(t, 0.2f)
    val leafPivot = pt(160f, 130f)
    withTransform({ rotate(degrees = sway, pivot = leafPivot) }) {
        val positions = listOf(140f to 118f, 160f to 100f, 182f to 120f, 160f to 140f)
        positions.forEach { (lx, ly) ->
            paintCircle(pt(lx, ly), 12f, colors.leaf, colors.accentGreen, 2.2f)
            sketchLine(pt(lx - 4f, ly), pt(lx + 4f, ly), colors.accent, 1.6f)
            sketchLine(pt(lx, ly - 4f), pt(lx, ly + 4f), colors.accent, 1.6f)
        }
    }

    // person watering the tree
    paintCircle(pt(224f, 150f), 19f, colors.skin, colors.ink, 2.4f)
    val faceSmile = Path().apply {
        moveTo(d(220f), d(157f))
        quadraticTo(d(223f), d(160f), d(226f), d(157f))
    }
    stroke(faceSmile, colors.ink, 1.6f)
    val pNeck = Path().apply {
        moveTo(d(224f), d(169f))
        lineTo(d(224f), d(176f))
    }
    limb(pNeck, colors.skinDark, colors.ink, 2.4f, thickness = 9f)
    val pBody = Path().apply {
        moveTo(d(224f), d(176f))
        quadraticTo(d(206f), d(180f), d(200f), d(196f))
        lineTo(d(196f), d(240f))
        quadraticTo(d(224f), d(250f), d(252f), d(240f))
        lineTo(d(248f), d(196f))
        quadraticTo(d(242f), d(180f), d(224f), d(176f))
        close()
    }
    paint(pBody, vBrush(176f, 250f, colors.fabric.lit(0.3f), colors.fabricDark), colors.ink, 2.4f)
    shade(pBody, hBrush(224f, 252f, colors.shade.a(0f), colors.shade))
    listOf(
        Path().apply {
            moveTo(d(202f), d(244f))
            lineTo(d(198f), d(270f))
        },
        Path().apply {
            moveTo(d(246f), d(244f))
            lineTo(d(250f), d(270f))
        }
    ).forEach { leg -> limb(leg, colors.fabricDark, colors.ink, 2.4f, thickness = 10f) }

    // watering can with a gentle stream toward the tree
    val can = Path().apply {
        moveTo(d(186f), d(198f))
        lineTo(d(214f), d(190f))
        lineTo(d(220f), d(200f))
        lineTo(d(192f), d(210f))
        close()
    }
    paint(can, vBrush(190f, 210f, colors.metal.lit(0.3f), colors.metalDark), colors.ink, 2f)
    val streamOn = (1f + wave(t, 0f)) / 2f > 0.4f
    if (streamOn) {
        sketchLine(pt(188f, 202f), pt(174f, 210f), colors.accentBlue.copy(alpha = 0.6f), 1.6f)
        sketchLine(pt(184f, 208f), pt(170f, 216f), colors.accentBlue.copy(alpha = 0.5f), 1.4f)
    }

    twinkle(70f, 90f, 3f, t, 0.4f, colors.accent)
    twinkle(250f, 100f, 3f, t, 0.7f, colors.inkSoft)
    groundHint(284f, colors.inkFaint)
}
