package com.sketchy.library.illustrations

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import com.sketchy.library.utils.*

// ─── Order Your Favorites ────────────────────────────────────────────────────
//   A person on a couch ordering from a phone, a burger floating above with
//   rising steam and a "+" bubble popping in.

internal fun DrawScope.drawOrderFavoritesScene(t: Float) {
    // couch
    val couch = Path().apply {
        moveTo(d(70f), d(232f))
        lineTo(d(250f), d(232f))
        lineTo(d(250f), d(268f))
        lineTo(d(70f), d(268f))
        close()
    }
    stroke(couch, Ink, 2.2f)
    sketchLine(pt(70f, 232f), pt(70f, 200f))
    sketchLine(pt(250f, 232f), pt(250f, 200f))

    // person
    sketchCircle(pt(140f, 158f), 20f, width = 2.4f)
    sketchCircle(pt(146f, 158f), 1.5f, filled = true)
    sketchLine(pt(140f, 178f), pt(140f, 186f))
    val body = Path().apply {
        moveTo(d(140f), d(186f))
        quadraticTo(d(118f), d(190f), d(112f), d(206f))
        lineTo(d(114f), d(232f))
        quadraticTo(d(140f), d(240f), d(168f), d(232f))
        lineTo(d(168f), d(206f))
        quadraticTo(d(162f), d(190f), d(140f), d(186f))
        close()
    }
    stroke(body)

    // phone held up
    val phone = Path().apply {
        moveTo(d(150f), d(150f))
        lineTo(d(184f), d(146f))
        lineTo(d(190f), d(196f))
        lineTo(d(156f), d(200f))
        close()
    }
    stroke(phone, Ink, 2.2f)
    val armR = Path().apply {
        moveTo(d(164f), d(196f))
        quadraticTo(d(168f), d(180f), d(166f), d(160f))
    }
    stroke(armR)

    // "+" bubble popping over the phone
    val popAt = t % 1f
    val popScale = if (popAt < 0.25f) popAt / 0.25f else 1f - ((popAt - 0.25f) / 0.75f).coerceAtMost(0.3f)
    if (popScale > 0.02f) {
        val bubblePivot = pt(206f, 130f)
        withTransform({ scale(scaleX = popScale, scaleY = popScale, pivot = bubblePivot) }) {
            sketchCircle(bubblePivot, 14f, Accent, width = 2.4f)
            sketchLine(pt(206f, 124f), pt(206f, 136f), Accent, 2.2f)
            sketchLine(pt(200f, 130f), pt(212f, 130f), Accent, 2.2f)
        }
    }

    // burger floating with steam
    val bob = 4f * wave(t, 0.3f)
    val burgerY = 92f + bob
    sketchLine(pt(206f, burgerY - 10f), pt(238f, burgerY - 10f), Accent, 3f)
    sketchLine(pt(202f, burgerY), pt(242f, burgerY), Ink, 2.4f)
    sketchLine(pt(204f, burgerY + 8f), pt(240f, burgerY + 8f), AccentTeal, 2.2f)
    sketchLine(pt(206f, burgerY + 16f), pt(238f, burgerY + 16f), Accent, 3f)
    for (i in 0..1) {
        val sx = 216f + i * 12f
        val steamPhase = (t + i * 0.3f) % 1f
        sketchLine(
            pt(sx, burgerY - 20f - steamPhase * 14f),
            pt(sx + 4f, burgerY - 30f - steamPhase * 14f),
            InkSoft.copy(alpha = 1f - steamPhase), 1.6f
        )
    }

    twinkle(64f, 100f, 3f, t, 0.5f, InkSoft)
    groundHint(276f)
}

// ─── Fast, Fresh Delivery ─────────────────────────────────────────────────────
//   A rider on a scooter with a food bag on the back, racing along a dashed
//   route with a pin at the end.

internal fun DrawScope.drawFastDeliveryScene(t: Float) {
    val bounce = 3f * wave(t, 0f)
    val roadY = 240f

    // dashed route with a destination pin
    val route = Path().apply {
        moveTo(d(40f), d(roadY + 10f))
        quadraticTo(d(150f), d(roadY - 30f), d(260f), d(90f))
    }
    drawPath(route, color = InkFaint, style = dashed())
    val pin = Path().apply {
        moveTo(d(260f), d(90f))
        cubicTo(d(244f), d(70f), d(248f), d(50f), d(260f), d(50f))
        cubicTo(d(272f), d(50f), d(276f), d(70f), d(260f), d(90f))
        close()
    }
    stroke(pin, Accent, 2.2f)
    sketchCircle(pt(260f, 62f), 5f, Accent, width = 1.8f)

    // scooter body
    val scooterY = roadY + bounce
    val body = Path().apply {
        moveTo(d(90f), d(scooterY))
        quadraticTo(d(120f), d(scooterY - 6f), d(150f), d(scooterY))
        lineTo(d(156f), d(scooterY - 26f))
        lineTo(d(140f), d(scooterY - 26f))
    }
    stroke(body, Ink, 2.4f)
    sketchCircle(pt(100f, scooterY + 10f), 14f, width = 2.4f)
    sketchCircle(pt(150f, scooterY + 10f), 14f, width = 2.4f)
    sketchLine(pt(150f, scooterY - 8f), pt(164f, scooterY - 34f), Ink, 2.4f)
    sketchLine(pt(158f, scooterY - 34f), pt(170f, scooterY - 34f), Ink, 2.4f)

    // food delivery bag on the back
    val bag = Path().apply {
        moveTo(d(94f), d(scooterY - 44f))
        lineTo(d(122f), d(scooterY - 44f))
        lineTo(d(122f), d(scooterY - 10f))
        lineTo(d(94f), d(scooterY - 10f))
        close()
    }
    stroke(bag, AccentTeal, 2.2f)
    sketchLine(pt(100f, scooterY - 44f), pt(100f, scooterY - 52f), AccentTeal, 2f)
    sketchLine(pt(116f, scooterY - 44f), pt(116f, scooterY - 52f), AccentTeal, 2f)

    // rider
    sketchCircle(pt(140f, scooterY - 58f), 16f, width = 2.4f)
    val torso = Path().apply {
        moveTo(d(140f), d(scooterY - 42f))
        quadraticTo(d(126f), d(scooterY - 34f), d(130f), d(scooterY - 18f))
    }
    stroke(torso)
    val armDrive = Path().apply {
        moveTo(d(136f), d(scooterY - 34f))
        lineTo(d(158f), d(scooterY - 30f))
    }
    stroke(armDrive)

    // speed lines trailing behind
    val drift = 6f * wave(t, 0.2f)
    sketchLine(pt(40f + drift, scooterY - 10f), pt(70f + drift, scooterY - 10f), InkSoft, 1.8f)
    sketchLine(pt(30f - drift, scooterY + 2f), pt(64f - drift, scooterY + 2f), InkSoft, 1.8f)

    groundHint(roadY + 24f)
}
