# Composition & scene anatomy

## The canvas

Every scene is drawn against a fixed **320×320dp design square** (`utils.DesignSize`), regardless of
what size it's ultimately displayed at — a gallery thumbnail and a full-bleed hero use the exact same
scene function; the caller scales uniformly to fit. Practical consequences:

- Coordinates are plain design-space `Float`s (roughly 0..320 on both axes), converted only at the
  point of drawing via `d()`/`pt()`.
- A scene should use most of that square — illustrations typically run subjects from about x=30 to
  x=290 and ground them somewhere around y=200–260; empty-state icons are usually more centered and
  compact (radius ~40–60 around cx=160).
- Nothing should render right at the very edge (0 or 320) unless it's deliberately meant to bleed off
  (e.g. a table's legs cropped by the bottom) — leave a margin so the scene doesn't look clipped at
  typical display sizes.

## Layering: background → midground → foreground

Elaborate (Featured) scenes make this explicit with comments; even a small onboarding/empty-state
scene follows the same order:

1. **Background** — a window, sky, distant hills, a haze. Often `clipPath`'d into a containing shape
   (a window pane) so it can't escape its frame. Distant elements get faded/hazy treatment
   (`colors.skyDeep.a(0.45f)`) and often use `lineOnly` for their outline so a painted haze doesn't
   leave a gap where a line-drawing needs a ridge.
2. **A surface**, if the scene has one — table, desk, counter (`surfacePath`). Shadows for everything
   sitting on it are drawn **after** the surface but **before** the objects, and clipped to the surface
   path as a group so no shadow can drift off its edge:
   ```kotlin
   clipPath(table) {
       contactShadow(objX, objY, rx, ry, colors.shade)   // repeat per object, back-to-front
   }
   ```
3. **Midground objects** — secondary props (a saucer + croissant beside the hero mug).
4. **Foreground hero** — the one object the scene is *about* (the mug, the seated figure, the icon's
   central motif). Gets the most detail: handle drawn before body so it tucks behind
   (`paintStroke`/`limb` for the handle, then `paint` for the body over it), a `shade` gradient for
   volume, a `sheen` for a highlight, `innerRim` for any hollow opening (a rim, a lid).
5. **Finishing touches** — 2–3 `twinkle` sparkles at staggered offsets, and (empty states especially)
   one `groundLine`/`groundHint` call near the bottom to keep a floating icon visually grounded.

## Depth via outline weight

Line weight is how the drawing signals depth without perspective drawing — keep to this scale:

| Depth | `width` passed to `paint`/`stroke`/`bold` |
|---|---|
| Foreground hero subject | 2.2f–2.4f |
| Midground props | 1.6f |
| Far background | 1.2f, or no outline at all (`lineOnly`/omit) |

Detail lines within a shape (grain, ridges, seams, hatching) use `thin()` (default 1.6f) regardless of
depth — they're always secondary to the shape's own outline.

## Perspective without a 3D engine

- **Face-on circular objects** (heads, wheels, badges, buttons) → `sketchCircle`/`paintCircle`.
- **Anything flat seen at an angle** (mug rims, cup interiors, plate/saucer edges, bowl mouths) →
  `ellipsePath(cx, cy, rx, ry)` with `ry ≈ 0.3 × rx` for a natural tabletop viewpoint (flatten toward
  `0.2×` for something closer to eye level). Never fake this with a squashed `sketchCircle`.
- **A table/desk/sill** → `surfacePath(backY, frontY, backInset, frontInset)`: wider at the front,
  narrower/higher at the back, so it reads as a horizontal plane receding from the viewer without any
  actual perspective transform.
- **Volume on a rounded object** → a `shade()` gradient across the shape (cool tone on the side away
  from the light) plus a `sheen()` highlight streak on the near side, in that order (shade first, sheen
  on top) — see the mug in `FeaturedMorningCoffee.kt`.

## Color placement discipline

- Outlined mode gets color **only** through the four accents, in small doses — one badge, one sparkle
  set, one small object's outline (via `inkOf`). Don't let an outlined scene end up with more than a
  small handful of colored marks; if everything needs to read, it belongs in ink, not an accent.
- Painted mode is where materials and light do the work — don't hesitate to layer `shade`/`sheen`/
  `glow`/`contactShadow` generously there; that generosity is exactly what "colourful" is for.
- Pick the accent to match the *object's real-world hue* via `colors.inkOf(materialColor)` /
  `colors.touch(materialColor)` rather than picking a `Sketchy` accent by eye — that's what keeps an
  outlined leaf green and an outlined flame warm without hardcoding which accent means what.

## Pre-flight checklist for a new scene

- [ ] Renders as a clean pure-ink line drawing with `colorful = false` (nothing looks like a hole or a
      missing shape where a fill silently disappeared).
- [ ] Renders as a fully painted scene with `colorful = true` (no unshaded flat-color blobs where a
      `shade`/`sheen` was skipped).
- [ ] `animate = false` freezes on a coherent, complete-looking pose — nothing mid-fall, mid-fade, or
      "not yet drawn."
- [ ] No two repeated elements (sparkles, arcs, drips) move in exact lockstep — each has its own
      `wave`/`pulse`/`loop` `offset`.
- [ ] Every color reference is `colors.<slot>` (`SketchyStyle`) — grep the new function for `Color(0x`
      and for the raw `Ink`/`Accent`/`AccentTeal` constants; there should be none.
- [ ] No `.copy(alpha = …)` on a slot that can be hidden — should be `.a(...)` instead (or one of the
      `SketchyStyle` line helpers).
- [ ] Contact shadows are drawn before their object, and clipped to the surface when several objects
      share one.
- [ ] Outline widths follow the depth scale (2.2–2.4 / 1.6 / 1.2-or-none).
- [ ] New `Sketch`/`EmptyState` enum entry added with category + copy, and wired into the `when`
      dispatcher.
