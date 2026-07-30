package com.sketchy.library.emptystates

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.withTransform
import com.sketchy.library.SketchyStyle
import com.sketchy.library.utils.*
import com.sketchy.library.characters.*

// ─── Panda Empty Cart ──────────────────────────────────────────────────────
//   Panda pushes an empty little cart from behind, peering hopefully into it.

internal fun DrawScope.drawPandaEmptyCart(t: Float, colors: SketchyStyle) {
    val bounce = 5f * wave(t, 0f)
    val cartX = 205f

    val basket = Path().apply {
        moveTo(d(cartX - 46f), d(150f + bounce))
        lineTo(d(cartX + 46f), d(150f + bounce))
        lineTo(d(cartX + 34f), d(202f + bounce))
        lineTo(d(cartX - 34f), d(202f + bounce))
        close()
    }
    contactShadow(cartX, 236f + bounce, 46f, 6f, colors.shade)
    paint(basket, vBrush(150f + bounce, 202f + bounce, colors.metal.lit(0.35f), colors.metalDark), colors.ink, 2.4f)
    shade(basket, hBrush(cartX - 46f, cartX + 46f, colors.shade.a(0f), colors.shade))
    limb(
        Path().apply {
            moveTo(d(cartX - 66f), d(126f + bounce))
            lineTo(d(cartX - 50f), d(126f + bounce))
            lineTo(d(cartX - 46f), d(150f + bounce))
        },
        colors.metalDark, colors.ink, 2.2f, thickness = 5f
    )
    for (i in 0..2) {
        val x = cartX - 24f + i * 24f
        sketchLine(pt(x, 162f + bounce), pt(x - 5f, 190f + bounce), colors.inkFaint, 1.6f)
    }
    paintCircle(pt(cartX - 20f, 216f + bounce), 10f, colors.hair, colors.ink, 2.2f)
    paintCircle(pt(cartX + 20f, 216f + bounce), 10f, colors.hair, colors.ink, 2.2f)

    // panda pushing from behind, torso breathing gently while the feet stay planted
    val bodyBob = 2f * wave(t, 0.15f)
    val pcx = 110f
    contactShadow(pcx, 250f, 40f, 7f, colors.shade)
    pandaLeg(pcx - 14f, 226f, pcx - 20f, 250f, colors, thickness = 13f)
    pandaLeg(pcx + 14f, 226f, pcx + 22f, 250f, colors, thickness = 13f)
    pandaBody(pcx, 168f + bodyBob, 228f + bodyBob, 32f, colors)
    pandaArm(pcx + 26f, 188f + bodyBob, cartX - 60f, 132f + bounce, colors, controlX = pcx + 50f, controlY = 150f + bodyBob, thickness = 10f)
    pandaArm(pcx - 26f, 188f + bodyBob, cartX - 50f, 138f + bounce, colors, controlX = pcx - 4f, controlY = 158f + bodyBob, thickness = 10f)
    pandaHead(pcx, 140f + bodyBob, 34f, colors, tilt = -8f, expression = PandaExpression.Content, blink = pandaAutoBlink(t))

    bambooStalk(52f, 170f, 232f, colors, sway = 3f * wave(t, 0.3f))

    twinkle(250f, 110f, 3f, t, 0.4f, colors.accent)
    twinkle(70f, 140f, 3f, t, 0.7f, colors.inkSoft)
    groundLine(258f, colors.inkFaint)
}

// ─── Panda Empty Wishlist ──────────────────────────────────────────────────
//   Panda holds up an empty star-shaped wish tag by its string, hopeful.

internal fun DrawScope.drawPandaEmptyWishlist(t: Float, colors: SketchyStyle) {
    val k = pulse(t, 0f)
    val starCx = 205f
    val starCy = 118f + 4f * wave(t, 0.1f)
    val scale = 0.95f + 0.08f * k
    val starPivot = pt(starCx, starCy)
    withTransform({ scale(scaleX = scale, scaleY = scale, pivot = starPivot) }) {
        val outerR = 44f
        val innerR = 18f
        val star = Path().apply {
            for (i in 0 until 10) {
                val r = if (i % 2 == 0) outerR else innerR
                val a = (-90.0 + i * 36.0) * kotlin.math.PI / 180.0
                val x = starCx + r * kotlin.math.cos(a).toFloat()
                val y = starCy + r * kotlin.math.sin(a).toFloat()
                if (i == 0) moveTo(d(x), d(y)) else lineTo(d(x), d(y))
            }
            close()
        }
        paint(star, vBrush(starCy - outerR, starCy + outerR, colors.sun, colors.sunDeep), colors.ink, 2.6f)
    }

    val pcx = 118f
    contactShadow(pcx, 248f, 40f, 7f, colors.shade)
    pandaLeg(pcx - 14f, 224f, pcx - 20f, 250f, colors, thickness = 13f)
    pandaLeg(pcx + 14f, 224f, pcx + 22f, 250f, colors, thickness = 13f)
    pandaBody(pcx, 166f, 226f, 32f, colors)
    // a thin string from paw to the tag's lowest point
    val handX = pcx + 30f
    val handY = 170f
    sketchLine(pt(handX, handY), pt(starCx, starCy + 44f), colors.inkSoft, 1.8f)
    pandaArm(pcx + 24f, 186f, handX, handY, colors, controlX = pcx + 44f, controlY = 178f, thickness = 10f)
    pandaArm(pcx - 26f, 186f, pcx - 46f, 224f, colors, controlX = pcx - 40f, controlY = 206f, thickness = 10f)
    pandaHead(pcx, 138f, 34f, colors, tilt = -6f, expression = PandaExpression.Content, blink = pandaAutoBlink(t, 0.3f))

    twinkle(starCx + 8f, starCy - 40f, 3f, t, 0.2f, colors.accent)
    twinkle(70f, 168f, 3f, t, 0.6f, colors.inkSoft)
    groundLine(258f, colors.inkFaint)
}

// ─── Panda No Favorites ────────────────────────────────────────────────────
//   Panda wraps both arms around a big empty heart outline, hugging it close.

internal fun DrawScope.drawPandaNoFavorites(t: Float, colors: SketchyStyle) {
    val beatPhase = t % 1f
    val beat = if (beatPhase < 0.12f) kotlin.math.sin((beatPhase / 0.12f) * kotlin.math.PI).toFloat() else 0f
    val heartScale = 1f + 0.1f * beat
    val hcx = 196f
    val hcy = 168f
    val heartPivot = pt(hcx, hcy)
    withTransform({ scale(scaleX = heartScale, scaleY = heartScale, pivot = heartPivot) }) {
        val heart = Path().apply {
            moveTo(d(hcx), d(hcy + 46f))
            cubicTo(d(hcx - 62f), d(hcy + 2f), d(hcx - 40f), d(hcy - 52f), d(hcx), d(hcy - 14f))
            cubicTo(d(hcx + 40f), d(hcy - 52f), d(hcx + 62f), d(hcy + 2f), d(hcx), d(hcy + 46f))
            close()
        }
        paint(heart, vBrush(hcy - 52f, hcy + 46f, colors.terracotta.lit(0.2f), colors.clay), colors.ink, 2.6f)
        if (beat > 0.01f) {
            drawPath(heart, color = colors.accentRed.copy(alpha = 0.18f * beat), style = Fill)
        }
    }

    val pcx = 122f
    contactShadow(pcx, 252f, 40f, 7f, colors.shade)
    pandaLeg(pcx - 14f, 228f, pcx - 20f, 252f, colors, thickness = 13f)
    pandaLeg(pcx + 14f, 228f, pcx + 22f, 252f, colors, thickness = 13f)
    pandaBody(pcx, 172f, 230f, 32f, colors)
    // arms wrap around the heart's sides, hands tucking in behind it
    pandaArm(pcx + 26f, 188f, hcx + 40f, hcy + 20f, colors, controlX = hcx - 10f, controlY = hcy - 30f, thickness = 10f)
    pandaArm(pcx - 26f, 188f, hcx - 10f, hcy + 40f, colors, controlX = pcx + 4f, controlY = hcy + 30f, thickness = 10f)
    pandaHead(pcx + 6f, 148f, 34f, colors, tilt = 10f, expression = PandaExpression.Content, blink = pandaAutoBlink(t, 0.5f))

    twinkle(250f, 116f, 3f, t, 0.3f, colors.accentRed)
    twinkle(80f, 150f, 3f, t, 0.6f, colors.inkSoft)
    groundLine(258f, colors.inkFaint)
}

// ─── Panda No Bookmarks ────────────────────────────────────────────────────
//   Panda sits beside an open book, its bamboo-green ribbon empty and swaying.

internal fun DrawScope.drawPandaNoBookmarks(t: Float, colors: SketchyStyle) {
    val sway = 4f * wave(t, 0f)
    val bookX = 205f
    val bookTopY = 150f

    contactShadow(bookX, 236f, 46f, 6f, colors.shade)
    val book = roundRectPath(bookX - 42f, bookTopY, 84f, 78f, 6f)
    paint(book, vBrush(bookTopY, bookTopY + 78f, colors.wood.lit(0.3f), colors.woodDark), colors.ink, 2.4f)
    sketchLine(pt(bookX, bookTopY + 6f), pt(bookX, bookTopY + 72f), colors.inkFaint, 1.6f)
    for (i in 0..2) {
        sketchLine(pt(bookX - 30f, bookTopY + 20f + i * 16f), pt(bookX - 8f, bookTopY + 20f + i * 16f), colors.inkFaint, 1.4f)
        sketchLine(pt(bookX + 10f, bookTopY + 20f + i * 16f), pt(bookX + 32f, bookTopY + 20f + i * 16f), colors.inkFaint, 1.4f)
    }
    val ribbonPivot = pt(bookX + 26f, bookTopY)
    withTransform({ rotate(degrees = sway, pivot = ribbonPivot) }) {
        val ribbon = Path().apply {
            moveTo(d(bookX + 18f), d(bookTopY))
            lineTo(d(bookX + 34f), d(bookTopY))
            lineTo(d(bookX + 34f), d(bookTopY + 46f))
            lineTo(d(bookX + 26f), d(bookTopY + 34f))
            lineTo(d(bookX + 18f), d(bookTopY + 46f))
            close()
        }
        paint(ribbon, vBrush(bookTopY, bookTopY + 46f, colors.leaf.lit(0.3f), colors.leafDark), colors.inkOf(colors.leaf), 2f)
    }

    val pcx = 108f
    contactShadow(pcx, 246f, 38f, 7f, colors.shade)
    // seated, legs tucked to the side
    pandaLeg(pcx - 12f, 220f, pcx - 34f, 236f, colors, controlX = pcx - 24f, controlY = 234f, thickness = 12f)
    pandaLeg(pcx + 12f, 220f, pcx + 30f, 238f, colors, controlX = pcx + 22f, controlY = 236f, thickness = 12f)
    pandaBody(pcx, 168f, 222f, 30f, colors)
    pandaArm(pcx + 24f, 182f, bookX - 44f, bookTopY + 30f, colors, controlX = pcx + 40f, controlY = 190f, thickness = 10f)
    pandaArm(pcx - 24f, 182f, pcx - 42f, 208f, colors, controlX = pcx - 38f, controlY = 196f, thickness = 10f)
    pandaHead(pcx, 138f, 34f, colors, tilt = -5f, expression = PandaExpression.Content, blink = pandaAutoBlink(t, 0.15f))

    twinkle(250f, 172f, 3f, t, 0.4f, colors.inkSoft)
    twinkle(72f, 150f, 3f, t, 0.7f, colors.accent)
    groundLine(258f, colors.inkFaint)
}

// ─── Panda No Downloads ────────────────────────────────────────────────────
//   Panda watches over an open tray; the download arrow just hovers, paused.

internal fun DrawScope.drawPandaNoDownloads(t: Float, colors: SketchyStyle) {
    val trayX = 206f
    val trayY = 210f
    val tray = Path().apply {
        moveTo(d(trayX - 48f), d(trayY))
        lineTo(d(trayX - 48f), d(trayY + 24f))
        lineTo(d(trayX + 48f), d(trayY + 24f))
        lineTo(d(trayX + 48f), d(trayY))
    }
    contactShadow(trayX, trayY + 30f, 52f, 6f, colors.shade)
    paint(tray, vBrush(trayY, trayY + 24f, colors.metal.lit(0.3f), colors.metalDark), colors.ink, 2.4f)
    sketchLine(pt(trayX - 30f, trayY + 12f), pt(trayX + 30f, trayY + 12f), colors.inkFaint, 1.6f)

    // the arrow just bobs in place, mid-air — nothing is actually arriving
    val bob = 4f * wave(t, 0f)
    val arrowY = 150f + bob
    val ringK = pulse(t, 0.25f)
    sketchCircle(pt(trayX, arrowY), 20f + 2f * ringK, colors.accentBlue.copy(alpha = 0.35f + 0.3f * ringK), width = 1.8f)
    sketchLine(pt(trayX, arrowY - 16f), pt(trayX, arrowY + 10f), colors.accentBlue, 2.6f)
    sketchLine(pt(trayX - 12f, arrowY - 2f), pt(trayX, arrowY + 10f), colors.accentBlue, 2.6f)
    sketchLine(pt(trayX + 12f, arrowY - 2f), pt(trayX, arrowY + 10f), colors.accentBlue, 2.6f)

    val pcx = 108f
    contactShadow(pcx, 250f, 40f, 7f, colors.shade)
    pandaLeg(pcx - 14f, 228f, pcx - 20f, 252f, colors, thickness = 13f)
    pandaLeg(pcx + 14f, 228f, pcx + 22f, 252f, colors, thickness = 13f)
    pandaBody(pcx, 172f, 230f, 32f, colors)
    pandaArm(pcx + 26f, 190f, pcx + 46f, 214f, colors, controlX = pcx + 42f, controlY = 198f, thickness = 10f)
    pandaArm(pcx - 26f, 190f, pcx - 46f, 214f, colors, controlX = pcx - 42f, controlY = 198f, thickness = 10f)
    pandaHead(pcx, 148f, 34f, colors, tilt = 6f, expression = PandaExpression.Worried, blink = pandaAutoBlink(t, 0.6f))

    twinkle(70f, 150f, 3f, t, 0.4f, colors.inkSoft)
    groundLine(260f, colors.inkFaint)
}
