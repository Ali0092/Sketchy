package com.sketchy.library.illustrations

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import com.sketchy.library.SketchyStyle
import com.sketchy.library.utils.*

// ─── Train Anywhere, Anytime ────────────────────────────────────────────────
//   A person mid-lunge with dumbbells, sweat drops flicking off, on a small
//   workout mat.

internal fun DrawScope.drawTrainAnywhereScene(t: Float, colors: SketchyStyle) {
    // mat
    val mat = Path().apply {
        moveTo(d(70f), d(270f))
        lineTo(d(250f), d(270f))
        lineTo(d(240f), d(288f))
        lineTo(d(80f), d(288f))
        close()
    }
    paint(mat, vBrush(270f, 288f, colors.fabric.lit(0.3f), colors.fabric), colors.accentBlue, 2.2f)

    val lunge = (1f + wave(t, 0f)) / 2f // 0..1 breathing effort

    contactShadow(160f, 280f, 74f, 7f, colors.shade)

    // head
    paintCircle(pt(160f, 96f), 20f, colors.skin, colors.ink, 2.4f)
    val ponytail = Path().apply {
        moveTo(d(144f), d(90f))
        quadraticTo(d(128f), d(84f), d(126f), d(70f))
    }
    limb(ponytail, colors.hair, colors.ink, 2.4f, thickness = 8f)
    sketchCircle(pt(166f, 96f), 1.6f, colors.ink, filled = true)
    sketchLine(pt(163f, 104f), pt(170f, 104f), colors.ink, 1.8f)
    val neck = Path().apply {
        moveTo(d(158f), d(116f))
        lineTo(d(158f), d(124f))
    }
    limb(neck, colors.skinDark, colors.ink, 2.4f, thickness = 9f)

    // torso, leaning into the lunge
    val body = Path().apply {
        moveTo(d(158f), d(124f))
        quadraticTo(d(140f), d(130f), d(136f), d(150f))
        lineTo(d(140f), d(186f))
        quadraticTo(d(160f), d(196f), d(182f), d(186f))
        lineTo(d(180f), d(150f))
        quadraticTo(d(174f), d(130f), d(158f), d(124f))
        close()
    }
    paint(body, vBrush(124f, 196f, colors.terracotta.lit(0.25f), colors.clay), colors.ink, 2.4f)
    shade(body, hBrush(160f, 184f, colors.shade.a(0f), colors.shade))

    // front leg bent forward, back leg extended
    val legF = Path().apply {
        moveTo(d(150f), d(188f))
        quadraticTo(d(146f), d(216f), d(120f), d(232f))
    }
    val legB = Path().apply {
        moveTo(d(172f), d(188f))
        quadraticTo(d(206f), d(210f), d(224f), d(248f))
    }
    limb(legF, colors.fabricDark, colors.ink, 2.4f, thickness = 10f)
    limb(legB, colors.fabricDark, colors.ink, 2.4f, thickness = 10f)
    sketchLine(pt(102f, 234f), pt(126f, 236f), colors.ink)
    sketchLine(pt(212f, 250f), pt(236f, 250f), colors.ink)

    // arms holding dumbbells, pumping with the effort wave
    val pump = 8f * lunge
    val armL = Path().apply {
        moveTo(d(140f), d(148f))
        quadraticTo(d(112f), d(140f - pump), d(96f), d(120f - pump))
    }
    val armR = Path().apply {
        moveTo(d(178f), d(148f))
        quadraticTo(d(206f), d(140f + pump), d(222f), d(120f + pump))
    }
    limb(armL, colors.skin, colors.ink, 2.4f, thickness = 8f)
    limb(armR, colors.skin, colors.ink, 2.4f, thickness = 8f)

    fun dumbbell(cx: Float, cy: Float) {
        sketchLine(pt(cx - 14f, cy), pt(cx + 14f, cy), colors.ink, 3f)
        paintCircle(pt(cx - 14f, cy), 7f, colors.metalDark, colors.accent, 2.4f)
        paintCircle(pt(cx + 14f, cy), 7f, colors.metalDark, colors.accent, 2.4f)
    }
    dumbbell(90f, 118f - pump)
    dumbbell(228f, 118f + pump)

    // sweat drops flicking off on effort peaks
    if (lunge > 0.6f) {
        val a = (lunge - 0.6f) / 0.4f
        sketchLine(pt(178f, 100f), pt(184f, 92f), colors.accentBlue.copy(alpha = a), 2f)
        sketchLine(pt(184f, 92f), pt(182f, 86f), colors.accentBlue.copy(alpha = a), 2f)
    }

    twinkle(70f, 90f, 3f, t, 0.4f, colors.inkSoft)
    twinkle(250f, 130f, 3f, t, 0.7f, colors.accent)
}

// ─── See Your Progress ───────────────────────────────────────────────────────
//   A person flexing beside a progress ring and a sweeping heart-rate line.

internal fun DrawScope.drawTrackProgressScene(t: Float, colors: SketchyStyle) {
    val ringCx = 130f
    val ringCy = 170f
    val ringR = 108f
    sketchCircle(pt(ringCx, ringCy), ringR, colors.inkFaint, 1.6f)
    val progress = t
    val sweep = (359.9f * progress).coerceAtLeast(0.1f)
    val arc = Path().apply {
        arcTo(
            rect = Rect(pt(ringCx - ringR, ringCy - ringR), Size(d(ringR * 2), d(ringR * 2))),
            startAngleDegrees = -90f,
            sweepAngleDegrees = sweep,
            forceMoveTo = true
        )
    }
    drawPath(arc, color = colors.accentBlue, style = bold(3.4f))

    // person flexing, roughly centered in the ring
    contactShadow(ringCx, 256f, 46f, 6f, colors.shade)
    paintCircle(pt(ringCx, 108f), 20f, colors.skin, colors.ink, 2.4f)
    sketchLine(pt(ringCx - 4f, 108f), pt(ringCx + 4f, 108f), colors.ink, 1.6f)
    val neck = Path().apply {
        moveTo(d(ringCx), d(128f))
        lineTo(d(ringCx), d(138f))
    }
    limb(neck, colors.skinDark, colors.ink, 2.4f, thickness = 9f)
    val torso = Path().apply {
        moveTo(d(ringCx), d(138f))
        quadraticTo(d(ringCx - 26f), d(144f), d(ringCx - 30f), d(170f))
        lineTo(d(ringCx - 24f), d(214f))
        quadraticTo(d(ringCx), d(224f), d(ringCx + 24f), d(214f))
        lineTo(d(ringCx + 30f), d(170f))
        quadraticTo(d(ringCx + 26f), d(144f), d(ringCx), d(138f))
        close()
    }
    paint(torso, vBrush(138f, 224f, colors.fabric.lit(0.3f), colors.fabricDark), colors.ink, 2.4f)
    shade(torso, hBrush(ringCx, ringCx + 30f, colors.shade.a(0f), colors.shade))
    listOf(
        Path().apply {
            moveTo(d(ringCx - 22f), d(222f))
            lineTo(d(ringCx - 26f), d(250f))
        },
        Path().apply {
            moveTo(d(ringCx + 22f), d(222f))
            lineTo(d(ringCx + 26f), d(250f))
        }
    ).forEach { leg -> limb(leg, colors.skinDark, colors.ink, 2.4f, thickness = 10f) }

    // flexed arm, bicep pulsing with the beat
    val flex = 1f + 0.1f * ((1f + wave(t, 0f)) / 2f)
    val armPath = Path().apply {
        moveTo(d(ringCx + 26f), d(150f))
        quadraticTo(d(ringCx + 52f), d(150f), d(ringCx + 50f), d(126f))
    }
    limb(armPath, colors.skin, colors.ink, 2.4f, thickness = 9f)
    val bicepPivot = pt(ringCx + 44f, 142f)
    withTransform({ scale(scaleX = flex, scaleY = flex, pivot = bicepPivot) }) {
        sketchCircle(bicepPivot, 11f, colors.accent.copy(alpha = 0.3f), filled = true)
    }
    val armDown = Path().apply {
        moveTo(d(ringCx - 26f), d(150f))
        quadraticTo(d(ringCx - 40f), d(180f), d(ringCx - 34f), d(206f))
    }
    limb(armDown, colors.skin, colors.ink, 2.4f, thickness = 9f)

    // stat card floating top-right with a sweeping heart-rate line
    val card = Path().apply {
        moveTo(d(224f), d(84f))
        lineTo(d(292f), d(84f))
        lineTo(d(292f), d(126f))
        lineTo(d(224f), d(126f))
        close()
    }
    paint(card, vBrush(84f, 126f, colors.paper, colors.metal), colors.ink, 2.2f)
    val hr = Path().apply {
        moveTo(d(230f), d(108f))
        lineTo(d(244f), d(108f))
        lineTo(d(250f), d(96f))
        lineTo(d(256f), d(116f))
        lineTo(d(262f), d(104f))
        lineTo(d(268f), d(108f))
        lineTo(d(286f), d(108f))
    }
    val sweepX = 230f + (286f - 230f) * ((t * 1.4f) % 1f)
    drawPath(hr, color = colors.accent, style = thin(2f))
    sketchCircle(pt(sweepX.coerceIn(230f, 286f), 108f), 3f, colors.accent, filled = true)

    twinkle(60f, 130f, 3f, t, 0.3f, colors.inkSoft)
    groundHint(268f, colors.inkFaint)
}
