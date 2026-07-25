package com.sketchy.library

import androidx.compose.ui.graphics.Color

/**
 * The palette as the drawing code sees it, in one of the two ways a scene can
 * be rendered.
 *
 * **Outlined** (the default, and what Sketchy is for) is a pure hand-drawn line
 * drawing: every surface, light and material slot reads back as
 * [Color.Transparent], so a fill is simply not painted and what survives is the
 * ink outline of the same shape. Colour is allowed only in small doses, through
 * [touch] and [inkOf], which map a hue to the nearest of the four accents.
 *
 * **Colourful** hands every slot straight back, so the very same drawing code
 * paints the fully coloured version — still on a transparent canvas, since a
 * scene is artwork to drop into your own layout, never a background.
 *
 * That is the whole rule, and it is why a scene never has to branch on the
 * mode: draw the shape, fill it from a material slot, outline it in ink, and it
 * renders correctly both ways. Four helpers cover the cases where a line has to
 * change colour rather than disappear — [line], [hint], [faint] and [inkOf] —
 * and [touch] covers the small marks that stay coloured either way.
 *
 * One caveat when writing a scene: use [com.sketchy.library.utils.a] and never
 * `Color.copy(alpha = …)` on a palette slot. `copy` on an invisible slot would
 * resurrect it as black; `a()` leaves it invisible.
 */
class SketchyStyle internal constructor(
    private val palette: SketchyColors,
    /** True when the scene is drawn as pure line-art with nothing filled in. */
    val outlined: Boolean,
) {
    /** Everything a painted scene fills with collapses to this when outlined. */
    private fun material(color: Color) = if (outlined) Color.Transparent else color

    // ── Ink & accents — the same in both modes ───────────────────────────
    val ink: Color get() = palette.ink
    val inkSoft: Color get() = palette.inkSoft
    val inkFaint: Color get() = palette.inkFaint
    val accent: Color get() = palette.accent
    val accentGreen: Color get() = palette.accentGreen
    val accentBlue: Color get() = palette.accentBlue
    val accentRed: Color get() = palette.accentRed

    // ── Light & atmosphere — invisible when outlined ─────────────────────
    val paper: Color get() = material(palette.paper)
    val sun: Color get() = material(palette.sun)
    val sunDeep: Color get() = material(palette.sunDeep)
    val glow: Color get() = material(palette.glow)
    val sky: Color get() = material(palette.sky)
    val skyDeep: Color get() = material(palette.skyDeep)
    val shade: Color get() = material(palette.shade)
    val shadeSoft: Color get() = material(palette.shadeSoft)

    // ── Materials — invisible when outlined ──────────────────────────────
    val wood: Color get() = material(palette.wood)
    val woodDark: Color get() = material(palette.woodDark)
    val leaf: Color get() = material(palette.leaf)
    val leafDark: Color get() = material(palette.leafDark)
    val terracotta: Color get() = material(palette.terracotta)
    val clay: Color get() = material(palette.clay)
    val fabric: Color get() = material(palette.fabric)
    val fabricDark: Color get() = material(palette.fabricDark)
    val metal: Color get() = material(palette.metal)
    val metalDark: Color get() = material(palette.metalDark)
    val skin: Color get() = material(palette.skin)
    val skinDark: Color get() = material(palette.skinDark)
    val hair: Color get() = material(palette.hair)
    val coffee: Color get() = material(palette.coffee)

    // ── Lines that change colour instead of disappearing ─────────────────

    /**
     * Structural line work drawn in a material colour — a lamp pole, a wire, a
     * cable. [color] when painted, plain ink when outlined, so the object is
     * still there in the line drawing.
     */
    fun line(color: Color): Color = if (outlined) ink else color

    /** Secondary line work — seams, folds, steam, rain. Softer ink when outlined. */
    fun hint(color: Color): Color = if (outlined) inkSoft else color

    /** Texture that should almost disappear in a line drawing — grain, mesh, hatching. */
    fun faint(color: Color): Color = if (outlined) inkFaint else color

    /**
     * An edge that exists only in the line drawing: ink when outlined, nothing
     * at all when painted. For shapes a painted scene renders as a soft
     * silhouette — distant hills, haze, a skyline — which would otherwise leave
     * a hole in the outline.
     */
    val lineOnly: Color get() = if (outlined) ink else Color.Transparent

    /**
     * The outline of a small object that is allowed to keep its colour in a
     * line drawing: ink when painted, the accent nearest [hue] when outlined.
     * Use it sparingly — a leaf, a flame, a coin, not a whole armchair.
     */
    fun inkOf(hue: Color): Color = if (outlined) accentFor(hue) else ink

    /**
     * A small coloured mark: [hue] itself when painted, the accent nearest to
     * it (at [alpha]) when outlined. Sparkles, glints, a highlighted bar.
     */
    fun touch(hue: Color, alpha: Float = 1f): Color =
        if (outlined) accentFor(hue).let { if (alpha == 1f) it else it.copy(alpha = alpha) } else hue

    /**
     * The accent closest in hue to [hue]. Near-neutral colours have no accent
     * worth picking, so they come back as soft ink instead.
     */
    fun accentFor(hue: Color): Color {
        val r = hue.red
        val g = hue.green
        val b = hue.blue
        val max = maxOf(r, g, b)
        val min = minOf(r, g, b)
        val chroma = max - min
        if (max < 0.08f || chroma / max < 0.2f) return inkSoft
        val h = when (max) {
            r -> 60f * (((g - b) / chroma) % 6f)
            g -> 60f * ((b - r) / chroma + 2f)
            else -> 60f * ((r - g) / chroma + 4f)
        }.let { if (it < 0f) it + 360f else it }
        return when {
            h < 25f || h >= 330f -> accentRed
            h < 72f -> accent
            h < 170f -> accentGreen
            else -> accentBlue
        }
    }
}
