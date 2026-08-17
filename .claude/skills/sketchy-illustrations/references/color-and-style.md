# Color & the outline/colorful duality

## The palette: `SketchyColors` (`library/src/commonMain/kotlin/com/sketchy/library/SketchyColors.kt`)

One `data class`, four blocks, in this order:

1. **Ink** — `ink`, `inkSoft` (60% alpha), `inkFaint` (20% alpha). The line every scene is drawn
   with, in *both* modes. This is the only block a monochrome/dark-mode reskin needs to touch.
2. **The four accents** — `accent` (warm amber, the default), `accentGreen`, `accentBlue`, `accentRed`.
   The *only* colors a pure outline sketch is ever allowed to show, and only in small doses: a
   highlighted checkbox, a sparkle, one badge, the outline of one small object. A scene picks
   whichever of the four suits the thing it's drawing (see `accentFor`/`inkOf` below) — never a
   fifth hardcoded hue.
3. **Light & atmosphere** — `paper`, `sun`, `sunDeep`, `glow`, `sky`, `skyDeep`, `shade`, `shadeSoft`.
   Painted mode only. `glow` and `shade`/`shadeSoft` carry alpha in their default values — that alpha
   is part of the color, don't strip it.
4. **Materials** — `wood`/`woodDark`, `leaf`/`leafDark`, `terracotta`/`clay`, `fabric`/`fabricDark`,
   `metal`/`metalDark`, `skin`/`skinDark`, `hair`, `coffee`. Painted mode only — the actual surfaces
   objects are made of.

Overriding a light/material slot while a scene renders outlined is harmless — it's simply never read.

## The wrapper every scene actually sees: `SketchyStyle`

`SketchyIllustration`/`SketchyEmptyState` never hand a scene the raw `SketchyColors` — they wrap it in
`SketchyStyle(colors, outlined = !colorful)`, and scene code only ever calls `colors.<slot>` on that
wrapper. This is the entire mechanism behind "one drawing, two looks":

- **Ink & the four accents** pass through unchanged in both modes.
- **Every light and material slot** collapses to `Color.Transparent` when `outlined == true`
  (`SketchyStyle.material()`). A fill from a transparent color is simply skipped
  (`Painting.kt`'s `fill`/`paint` check `color.isHidden` first), so what survives on screen is just
  the ink outline of the same path — the line drawing, from *unchanged* scene code.

This is why rule #6 in `SKILL.md` matters: any `if (colorful)` written inside a scene is almost
certainly duplicating logic `SketchyStyle` already gives you for free.

### The five helpers for lines that must *change* rather than disappear

Most line work is either ink (always visible) or a material fill (invisible when outlined) — but a
few things need a third behavior. Reach for these instead of hand-rolling the branch:

| Helper | Outlined | Colorful | Use for |
|---|---|---|---|
| `line(color)` | `ink` | `color` | Structural line work that must stay visible outlined but take its material color when painted — a lamp pole, a wire, a cable. |
| `hint(color)` | `inkSoft` | `color` | Secondary line work — seams, folds, steam wisps, rain streaks. |
| `faint(color)` | `inkFaint` | `color` | Texture that should nearly vanish outlined — wood grain, mesh, hatching. |
| `lineOnly` | `ink` | `Color.Transparent` | The opposite case: an edge that exists *only* in the line drawing — the ridge of distant hills a painted scene renders as pure haze (no line), which would otherwise leave a gap in the outline version. |
| `inkOf(hue)` | `accentFor(hue)` | `ink` | The outline of one small object allowed to keep color in the line drawing — a leaf, a flame, a coin. Sparingly: one object, never a whole armchair. |
| `touch(hue, alpha)` | `accentFor(hue)` at `alpha` | `hue` | A small colored mark — sparkle, glint, highlighted bar. |
| `outlineShadow` | `inkFaint` | `Color.Transparent` | The soft duplicate-outline "shadow" behind an outlined hero shape (rule 11 in `SKILL.md`) — pass it to `inkShadow(path, color)` in `Painting.kt`. Invisible once painted, since colorful mode already gets real shading from `shade`/`contactShadow`. |

### `accentFor(hue)` — mapping an arbitrary material color to one of the four accents

Converts `hue` to HSV, and picks by hue angle: near-neutral (low chroma or near-black) → `inkSoft`;
`< 25°` or `≥ 330°` → `accentRed`; `< 72°` → `accent`; `< 170°` → `accentGreen`; else → `accentBlue`.
You will rarely call this directly — `inkOf`/`touch` already route through it — but it's why, e.g., a
painted mug's warm coffee brown reads as the `accent` (amber) outline rather than `accentRed`.

## Reskinning contract (why this ordering matters)

A colorless/outlined consumer only ever exercises the ink + four-accent block. That means:

- If you're asked to make a scene "themeable" or "match app colors," touching `ink` + the four
  accents is a **complete** answer for how it looks outlined; light/material slots only matter once
  `colorful = true` is in play.
- Never invent a new semantic color slot on `SketchyColors` for a single scene. If an existing block
  doesn't fit, that's a signal the shape belongs to a material that already has a slot (reuse `clay`/
  `terracotta` rather than adding `brick`, etc.) — the whole palette is meant to stay small enough that
  overriding "a few slots at a time" (per the README's reskinning table) stays true.
