package com.sketchy.library

import androidx.compose.ui.graphics.Color

/**
 * A fully reskinnable color palette shared by every sketch and empty state.
 * Override any part to fit your app's own design system without touching
 * any drawing code.
 *
 * Sketchy is hand-drawn first: **by default every scene is a pure outline
 * sketch** — ink lines on a transparent canvas with nothing filled in, lifted
 * only by a few small accent marks. That is what the first block of slots is
 * for, and it is all an outlined scene ever uses.
 *
 * The rest — surfaces, light and materials — only come into play when a scene
 * is asked for `colorful = true`, where the same drawing is painted in full.
 * Reskinning an outlined app means changing the ink and the four accents;
 * everything below that is for the painted look.
 */
data class SketchyColors(
    // ── Ink — the line every scene is drawn with ─────────────────────────
    val ink: Color = Color(0xFF0D1B2A),
    val inkSoft: Color = Color(0x990D1B2A),
    val inkFaint: Color = Color(0x330D1B2A),

    // ── The four accents ─────────────────────────────────────────────────
    /**
     * The only color an outlined sketch is allowed to show, and only ever in
     * small doses: a highlighted bar, a sparkle, the outline of one small
     * object. Scenes pick whichever of the four suits what they are drawing.
     */
    val accent: Color = Color(0xFFFFBC00),
    val accentGreen: Color = Color(0xFF4E9E62),
    val accentBlue: Color = Color(0xFF2F8FA8),
    val accentRed: Color = Color(0xFFD9553F),

    // ── Light & atmosphere — painted scenes only ─────────────────────────
    /** Ceramic, paper and other near-white surfaces. */
    val paper: Color = Color(0xFFFFFDF8),
    /** Warm key light. */
    val sun: Color = Color(0xFFFFC94D),
    /** The hotter core of the warm light. */
    val sunDeep: Color = Color(0xFFF59331),
    /** Translucent lamp / sunlight bloom. Alpha matters here. */
    val glow: Color = Color(0x66FFD68A),
    val sky: Color = Color(0xFFBFE3F2),
    val skyDeep: Color = Color(0xFF7FC3DE),
    /** Cool shadow overlay painted *on top* of a surface. Alpha matters here. */
    val shade: Color = Color(0x33122033),
    /** A fainter [shade] for contact shadows and ambient occlusion. */
    val shadeSoft: Color = Color(0x18122033),

    // ── Materials — painted scenes only ──────────────────────────────────
    val wood: Color = Color(0xFFC98A54),
    val woodDark: Color = Color(0xFF9A5F35),
    val leaf: Color = Color(0xFF57A05A),
    val leafDark: Color = Color(0xFF35703E),
    val terracotta: Color = Color(0xFFD1694A),
    val clay: Color = Color(0xFFB8503A),
    val fabric: Color = Color(0xFF4C6FA5),
    val fabricDark: Color = Color(0xFF33507E),
    val metal: Color = Color(0xFFB9C3CB),
    val metalDark: Color = Color(0xFF7E8B96),
    val skin: Color = Color(0xFFF0C39B),
    val skinDark: Color = Color(0xFFCE9A70),
    val hair: Color = Color(0xFF3E2A22),
    val coffee: Color = Color(0xFF6B4229),
) {
    @Deprecated(
        "Renamed — the accents are now accent, accentGreen, accentBlue and accentRed.",
        ReplaceWith("accentBlue")
    )
    val accentSecondary: Color get() = accentBlue
}