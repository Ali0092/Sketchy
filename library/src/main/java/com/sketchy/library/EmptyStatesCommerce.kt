package com.sketchy.library

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.withTransform

// ─── Empty Cart ───────────────────────────────────────────────────────────────
//   A shopping cart, bouncing gently, with dashed lines standing in for the
//   goods it doesn't have.

internal fun DrawScope.drawEmptyCart(t: Float, colors: SketchyColors) {
    val bounce = 5f * wave(t, 0f)
    val basket = Path().apply {
        moveTo(d(96f), d(120f + bounce))
        lineTo(d(232f), d(120f + bounce))
        lineTo(d(212f), d(190f + bounce))
        lineTo(d(116f), d(190f + bounce))
        close()
    }
    stroke(basket, colors.ink, 2.4f)
    sketchLine(pt(80f, 96f + bounce), pt(96f, 120f + bounce), colors.ink, 2.4f)
    sketchLine(pt(60f, 96f + bounce), pt(80f, 96f + bounce), colors.ink, 2.4f)

    for (i in 0..2) {
        val x = 128f + i * 32f
        sketchLine(pt(x, 132f + bounce), pt(x - 6f, 178f + bounce), colors.inkFaint, 1.6f)
    }

    sketchCircle(pt(136f, 210f + bounce), 11f, colors.ink, width = 2.2f)
    sketchCircle(pt(200f, 210f + bounce), 11f, colors.ink, width = 2.2f)

    twinkle(240f, 90f, 3f, t, 0.4f, colors.accent)
    twinkle(70f, 150f, 3f, t, 0.7f, colors.inkSoft)
    groundLine(244f, colors.inkFaint)
}

// ─── Empty Wishlist ───────────────────────────────────────────────────────────
//   A single big star, twinkling like the sparkles that orbit it.

internal fun DrawScope.drawEmptyWishlist(t: Float, colors: SketchyColors) {
    val cx = 160f
    val cy = 156f
    val k = (1f + wave(t, 0f)) / 2f
    val scale = 0.95f + 0.08f * k
    val pivot = pt(cx, cy)

    withTransform({ scale(scaleX = scale, scaleY = scale, pivot = pivot) }) {
        val outerR = 58f
        val innerR = 24f
        val star = Path().apply {
            for (i in 0 until 10) {
                val r = if (i % 2 == 0) outerR else innerR
                val a = (-90.0 + i * 36.0) * kotlin.math.PI / 180.0
                val x = cx + r * kotlin.math.cos(a).toFloat()
                val y = cy + r * kotlin.math.sin(a).toFloat()
                if (i == 0) moveTo(d(x), d(y)) else lineTo(d(x), d(y))
            }
            close()
        }
        drawPath(
            star,
            color = colors.accent.copy(alpha = 0.10f + 0.10f * k),
            style = Fill
        )
        stroke(star, colors.ink, 2.6f)
    }

    twinkle(90f, 90f, 4f, t, 0.2f, colors.accent)
    twinkle(232f, 100f, 3f, t, 0.5f, colors.inkSoft)
    twinkle(84f, 210f, 3f, t, 0.8f, colors.inkSoft)
    groundLine(240f, colors.inkFaint)
}

// ─── No Favorites ─────────────────────────────────────────────────────────────
//   A heart outline with a gentle, real heartbeat pulse.

internal fun DrawScope.drawNoFavorites(t: Float, colors: SketchyColors) {
    val cx = 160f
    val cy = 160f
    // sharp quick swell then a long rest — reads like an actual heartbeat
    val beatPhase = t % 1f
    val beat = if (beatPhase < 0.12f) kotlin.math.sin((beatPhase / 0.12f) * kotlin.math.PI).toFloat() else 0f
    val scale = 1f + 0.12f * beat
    val pivot = pt(cx, cy)

    withTransform({ scale(scaleX = scale, scaleY = scale, pivot = pivot) }) {
        val heart = Path().apply {
            moveTo(d(cx), d(cy + 42f))
            cubicTo(d(cx - 70f), d(cy - 6f), d(cx - 46f), d(cy - 66f), d(cx), d(cy - 24f))
            cubicTo(d(cx + 46f), d(cy - 66f), d(cx + 70f), d(cy - 6f), d(cx), d(cy + 42f))
            close()
        }
        stroke(heart, colors.ink, 2.6f)
        if (beat > 0.01f) {
            drawPath(heart, color = colors.accent.copy(alpha = 0.18f * beat), style = Fill)
        }
    }

    twinkle(240f, 110f, 3f, t, 0.4f, colors.inkSoft)
    groundLine(244f, colors.inkFaint)
}

// ─── No Bookmarks ─────────────────────────────────────────────────────────────
//   A bookmark ribbon swaying like a real flag, with a floating sparkle.

internal fun DrawScope.drawNoBookmarks(t: Float, colors: SketchyColors) {
    val sway = 4f * wave(t, 0f)
    val pivot = pt(160f, 90f)
    withTransform({ rotate(degrees = sway, pivot = pivot) }) {
        val ribbon = Path().apply {
            moveTo(d(126f), d(90f))
            lineTo(d(194f), d(90f))
            lineTo(d(194f), d(220f))
            lineTo(d(160f), d(190f))
            lineTo(d(126f), d(220f))
            close()
        }
        stroke(ribbon, colors.ink, 2.4f)
        sketchLine(pt(138f, 112f), pt(182f, 112f), colors.inkFaint, 1.6f)
        sketchLine(pt(138f, 130f), pt(182f, 130f), colors.inkFaint, 1.6f)
    }

    twinkle(220f, 76f, 4f, t, 0.3f, colors.accent)
    twinkle(96f, 150f, 3f, t, 0.6f, colors.inkSoft)
    groundLine(250f, colors.inkFaint)
}

// ─── No Downloads ─────────────────────────────────────────────────────────────
//   An arrow drops into an open tray on a loop, like a file about to land.

internal fun DrawScope.drawNoDownloads(t: Float, colors: SketchyColors) {
    val trayY = 200f
    val tray = Path().apply {
        moveTo(d(96f), d(trayY))
        lineTo(d(96f), d(trayY + 26f))
        lineTo(d(224f), d(trayY + 26f))
        lineTo(d(224f), d(trayY))
    }
    stroke(tray, colors.ink, 2.4f)

    val drop = (t % 1f)
    val arrowY = 90f + drop * 90f
    val alpha = 1f - drop
    sketchLine(pt(160f, 70f), pt(160f, arrowY), colors.accent.copy(alpha = alpha), 2.6f)
    sketchLine(pt(148f, arrowY - 14f), pt(160f, arrowY), colors.accent.copy(alpha = alpha), 2.6f)
    sketchLine(pt(172f, arrowY - 14f), pt(160f, arrowY), colors.accent.copy(alpha = alpha), 2.6f)

    sketchLine(pt(118f, trayY + 13f), pt(202f, trayY + 13f), colors.inkFaint, 1.6f)

    twinkle(232f, 130f, 3f, t, 0.4f, colors.inkSoft)
    groundLine(244f, colors.inkFaint)
}
