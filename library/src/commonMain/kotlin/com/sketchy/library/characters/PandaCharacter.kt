package com.sketchy.library.characters

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import com.sketchy.library.SketchyStyle
import com.sketchy.library.utils.*

/**
 * The shared "Panda" character rig every Panda/Cartoony scene is built from, so ten-plus scenes
 * read as one consistent character instead of each freehanding it from scratch — the same instinct
 * behind existing shared motifs like [steam]/[twinkle]/[contactShadow].
 *
 * Cartoony's deltas from Classic live entirely here: rounder/chubbier shapes, a bolder foreground
 * outline weight ([PandaOutline], 2.8f vs Classic's 2.2f-2.4f), bigger features — still built purely
 * from the existing organic quadratic-curve + paint/limb/shade/sheen/contactShadow vocabulary in
 * `utils/Painting.kt`, never a different drawing mechanism. See the sketchy-illustrations skill's
 * `references/theming.md`.
 *
 * Every piece takes plain design-space coordinates (not pre-converted `Offset`s) to match every
 * other primitive in the library, and poses nothing on its own — a scene combines these with its own
 * scene-specific limb placement and props to stage a genuinely different moment each time (sleeping,
 * pedaling, reaching for a router), rather than stamping one rigid full-body pose everywhere.
 */

/** Cartoony's hero-shape outline weight — bolder than Classic's 2.2f-2.4f. */
internal const val PandaOutline = 2.8f

/** The face a scene puts on the panda for this frame. */
internal enum class PandaExpression { Content, Sleepy, Surprised, Worried, Delighted }

/**
 * A natural, occasional blink (0 = open, 1 = fully closed) for [pandaHead]'s `blink` param — a quick
 * closure near the end of each 4s loop, distinct from whatever ambient sway a scene layers on top.
 * Purely optional: pass a fixed `blink` instead when an expression (e.g. [PandaExpression.Surprised])
 * should never close its eyes.
 */
internal fun pandaAutoBlink(t: Float, offset: Float = 0f): Float {
    val phase = loop(t, offset)
    if (phase < 0.90f) return 0f
    return if (phase < 0.95f) smooth01((phase - 0.90f) / 0.05f) else smooth01((1f - phase) / 0.05f)
}

/**
 * One floppy round ear. Called twice by [pandaHead]; exposed separately for poses where an ear
 * peeks out on its own (over a windowsill, behind a box).
 */
internal fun DrawScope.pandaEar(cx: Float, cy: Float, r: Float, colors: SketchyStyle) {
    paintCircle(pt(cx, cy), r, colors.hair, colors.ink, PandaOutline * 0.8f)
}

/**
 * The head: a round face, two ears, two soft hand-inked eye-patches (organic quadratic blobs, not
 * perfect ellipses), pupils that blink, a small nose, and a mouth shaped by [expression]. [tilt]
 * (degrees, about the head's own center) gives a pose its personality — a curious cock of the head,
 * a sleepy droop.
 */
internal fun DrawScope.pandaHead(
    cx: Float,
    cy: Float,
    r: Float,
    colors: SketchyStyle,
    tilt: Float = 0f,
    expression: PandaExpression = PandaExpression.Content,
    blink: Float = 0f,
) {
    val headPivot = pt(cx, cy)
    withTransform({ rotate(degrees = tilt, pivot = headPivot) }) {
        // ears first, so the face overlaps their base
        pandaEar(cx - r * 0.75f, cy - r * 0.78f, r * 0.4f, colors)
        pandaEar(cx + r * 0.75f, cy - r * 0.78f, r * 0.4f, colors)

        val face = ellipsePath(cx, cy, r, r)
        inkShadow(face, colors.outlineShadow)
        paint(face, colors.paper, colors.ink, PandaOutline)
        shade(face, hBrush(cx, cx + r, colors.shade.a(0f), colors.shade))

        val eyeY = cy + r * 0.05f
        val eyeSpread = r * 0.42f
        val openness = 1f - blink.coerceIn(0f, 1f)
        val wide = if (expression == PandaExpression.Surprised) 1.3f else 1f
        listOf(-1f, 1f).forEach { side ->
            val ex = cx + side * eyeSpread
            val patch = Path().apply {
                moveTo(d(ex - r * 0.26f), d(eyeY - r * 0.08f))
                quadraticTo(d(ex - r * 0.30f), d(eyeY - r * 0.36f), d(ex + side * r * 0.02f), d(eyeY - r * 0.40f))
                quadraticTo(d(ex + r * 0.30f), d(eyeY - r * 0.30f), d(ex + r * 0.26f), d(eyeY + r * 0.10f))
                quadraticTo(d(ex + r * 0.18f), d(eyeY + r * 0.36f), d(ex - r * 0.04f), d(eyeY + r * 0.32f))
                quadraticTo(d(ex - r * 0.28f), d(eyeY + r * 0.26f), d(ex - r * 0.26f), d(eyeY - r * 0.08f))
                close()
            }
            paint(patch, colors.hair, colors.ink, PandaOutline * 0.7f)

            if (openness > 0.08f) {
                sketchCircle(pt(ex, eyeY), r * 0.11f * wide, colors.paper, filled = true)
                sketchCircle(pt(ex, eyeY), r * 0.05f * wide, colors.ink, filled = true)
            } else {
                sketchLine(pt(ex - r * 0.1f, eyeY), pt(ex + r * 0.1f, eyeY), colors.ink, 1.8f)
            }
        }

        // nose
        sketchCircle(pt(cx, cy + r * 0.28f), r * 0.06f, colors.ink, filled = true)

        // mouth, shaped by expression
        val my = cy + r * 0.42f
        if (expression == PandaExpression.Surprised) {
            sketchCircle(pt(cx, my), r * 0.09f, colors.ink, width = 1.8f)
        } else {
            val mouth = Path().apply {
                when (expression) {
                    PandaExpression.Delighted -> {
                        moveTo(d(cx - r * 0.22f), d(my))
                        quadraticTo(d(cx), d(my + r * 0.22f), d(cx + r * 0.22f), d(my))
                    }
                    PandaExpression.Worried -> {
                        moveTo(d(cx - r * 0.18f), d(my + r * 0.06f))
                        quadraticTo(d(cx), d(my - r * 0.06f), d(cx + r * 0.18f), d(my + r * 0.06f))
                    }
                    PandaExpression.Sleepy -> {
                        moveTo(d(cx - r * 0.1f), d(my))
                        lineTo(d(cx + r * 0.1f), d(my))
                    }
                    else -> {
                        moveTo(d(cx - r * 0.16f), d(my - r * 0.02f))
                        quadraticTo(d(cx), d(my + r * 0.14f), d(cx + r * 0.16f), d(my - r * 0.02f))
                    }
                }
            }
            stroke(mouth, colors.ink, 1.8f)
        }
    }
}

/**
 * The torso: a chubby rounded blob with a black "saddle" band across the shoulders, echoing a real
 * panda's shoulder-to-arm fur without committing to an arm pose. Returns the body [Path] so a scene
 * can clip decorations to it or anchor a [contactShadow] beneath it.
 */
internal fun DrawScope.pandaBody(
    cx: Float,
    topY: Float,
    bottomY: Float,
    halfWidth: Float,
    colors: SketchyStyle,
): Path {
    val h = bottomY - topY
    val body = Path().apply {
        moveTo(d(cx), d(topY))
        quadraticTo(d(cx - halfWidth * 1.05f), d(topY + h * 0.18f), d(cx - halfWidth), d(topY + h * 0.58f))
        quadraticTo(d(cx - halfWidth * 0.9f), d(bottomY), d(cx), d(bottomY))
        quadraticTo(d(cx + halfWidth * 0.9f), d(bottomY), d(cx + halfWidth), d(topY + h * 0.58f))
        quadraticTo(d(cx + halfWidth * 1.05f), d(topY + h * 0.18f), d(cx), d(topY))
        close()
    }
    inkShadow(body, colors.outlineShadow)
    paint(body, colors.paper, colors.ink, PandaOutline)
    shade(body, hBrush(cx, cx + halfWidth, colors.shade.a(0f), colors.shade))

    val saddle = Path().apply {
        moveTo(d(cx - halfWidth * 0.88f), d(topY + h * 0.14f))
        quadraticTo(d(cx), d(topY - h * 0.05f), d(cx + halfWidth * 0.88f), d(topY + h * 0.14f))
        quadraticTo(d(cx + halfWidth * 0.55f), d(topY + h * 0.32f), d(cx), d(topY + h * 0.26f))
        quadraticTo(d(cx - halfWidth * 0.55f), d(topY + h * 0.32f), d(cx - halfWidth * 0.88f), d(topY + h * 0.14f))
        close()
    }
    fill(saddle, colors.hair)
    stroke(saddle, colors.line(colors.hair), 1.6f)
    return body
}

private fun DrawScope.pandaLimb(
    fromX: Float,
    fromY: Float,
    toX: Float,
    toY: Float,
    colors: SketchyStyle,
    controlX: Float?,
    controlY: Float?,
    thickness: Float,
) {
    val path = Path().apply {
        moveTo(d(fromX), d(fromY))
        if (controlX != null && controlY != null) {
            quadraticTo(d(controlX), d(controlY), d(toX), d(toY))
        } else {
            lineTo(d(toX), d(toY))
        }
    }
    limb(path, colors.hair, colors.ink, PandaOutline * 0.85f, thickness = thickness)
}

/** An arm/paw, black like every panda limb — a plain ink line outlined, a solid rod painted. */
internal fun DrawScope.pandaArm(
    shoulderX: Float,
    shoulderY: Float,
    handX: Float,
    handY: Float,
    colors: SketchyStyle,
    controlX: Float? = null,
    controlY: Float? = null,
    thickness: Float = 11f,
) = pandaLimb(shoulderX, shoulderY, handX, handY, colors, controlX, controlY, thickness)

/** A leg/foot — see [pandaArm]; just a wider default thickness. */
internal fun DrawScope.pandaLeg(
    hipX: Float,
    hipY: Float,
    footX: Float,
    footY: Float,
    colors: SketchyStyle,
    controlX: Float? = null,
    controlY: Float? = null,
    thickness: Float = 13f,
) = pandaLimb(hipX, hipY, footX, footY, colors, controlX, controlY, thickness)

/**
 * Panda's recurring signature prop, playing the same connective role [steam] plays across the
 * coffee-themed scenes. A jointed stalk from ([x], [bottomY]) up to ([x] + [sway], [topY]), with a
 * couple of leaves at the tip. [sway] bends the whole stalk sideways for a breeze/bounce.
 */
internal fun DrawScope.bambooStalk(
    x: Float,
    topY: Float,
    bottomY: Float,
    colors: SketchyStyle,
    sway: Float = 0f,
    segments: Int = 4,
) {
    val stalk = Path().apply {
        moveTo(d(x), d(bottomY))
        for (i in 1..segments) {
            val f = i / segments.toFloat()
            lineTo(d(x + sway * f * f), d(bottomY - (bottomY - topY) * f))
        }
    }
    stroke(stalk, colors.line(colors.leaf), 3f)
    for (i in 1 until segments) {
        val f = i / segments.toFloat()
        val jx = x + sway * f * f
        val jy = bottomY - (bottomY - topY) * f
        sketchLine(pt(jx - 5f, jy), pt(jx + 5f, jy), colors.faint(colors.leafDark.a(0.7f)), 1.6f)
    }

    val tipX = x + sway
    listOf(-1f, 1f).forEach { side ->
        val leaf = Path().apply {
            moveTo(d(tipX), d(topY + 6f))
            quadraticTo(d(tipX + side * 16f), d(topY - 4f), d(tipX + side * 26f), d(topY - 14f))
            quadraticTo(d(tipX + side * 14f), d(topY - 6f), d(tipX), d(topY + 6f))
            close()
        }
        paint(leaf, vBrush(topY - 14f, topY + 6f, colors.leaf, colors.leafDark), colors.inkOf(colors.leaf), 1.6f)
    }
}
