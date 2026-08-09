# Theming: standing up a new cross-cutting theme

A **theme** is a named character or motif (Fox, Astronaut, …) drawn in a named **style** (Cartoony,
Minimal, Retro-Future, …) that ships as a matched set spanning *both* catalogs — its own illustrations
and its own complete run of empty states — rather than a one-off scene or two.

No theme has shipped yet — this file documents the contract to follow when one does. (A restricted
*category*, like the device-only `"Network"` empty states, is a lighter-weight cousin of this: it
doesn't introduce a new `style` or require illustration parity — see "Empty-state categories" in
`SKILL.md`.)

## The tagging contract

Every scene in a theme sets two fields on its `Sketch`/`EmptyState` enum entry:

- **`category = <theme name>`** (e.g. `"Fox"`) — reuses the exact grouping/search machinery every
  other category already uses. This is *why* a theme is cheap to add: `SketchyGalleryScreen.kt` /
  `EmptyStateGalleryScreen.kt` group by `category` and `GalleryComponents.kt`'s `matches()` already
  searches it — a theme is simply a category that happens to appear in both `Sketch` and `EmptyState`.
- **`style = <style name>`** (e.g. `"Cartoony"`) — a second, independent tag, defaulted to `"Classic"`
  for every scene that predates theming. `matches()` checks this too, so searching the *style* surfaces
  every theme sharing it (a second Cartoony character later would show up alongside the first), while
  searching the *theme name* surfaces only that one character's scenes via `category`.

Nothing else changes: no new screens, no new enum type, no sorting logic — see rules 9–10 in
`SKILL.md`. Put the new theme's enum entries **before** the existing catalog (rule 10) so its category
header leads the gallery.

## Minimum content bar

A theme is not a token handful of scenes. Ship **at minimum 10 illustrations and 10 empty states**,
uncapped — and prefer full parity with the existing `EmptyState` catalog (a themed version of every
one of the 20 use cases already modeled: connectivity/errors, content/search, saved/commerce,
everyday/productivity) over an arbitrary subset. Illustrations are "some random and meaningful daily
life" scenes for the character — varied, not just reskins of the existing Featured/Onboarding scenes.

## Build a character rig before authoring scenes

With ten-plus scenes sharing one character, don't let each scene freehand the subject from scratch —
build one small, reusable rig first, the same instinct behind existing shared motifs like `steam()`,
`twinkle()`, or `contactShadow()` in `utils/Painting.kt`/`utils/Extensions.kt`. Put it in a new
`com.sketchy.library.characters` package (`internal`, so both `illustrations/` and `emptystates/` files
can call it across packages within the `:library` module), built entirely from the existing
`utils/Extensions.kt` + `utils/Painting.kt` vocabulary — a character rig is composition, not a new
drawing mechanism. Expose the pieces a scene actually needs to pose (a head with a `tilt`, a body with a
`pose`), not just one rigid "draw the whole character here" call, so scenes can still stage genuinely
different moments (sleeping vs. riding a bike vs. reaching for a router).

A style (Cartoony, etc.) is expressed as proportion/weight/palette deltas from Classic baked into the
rig — rounder shapes, a bolder outline-weight range, bigger features — never as a different
construction technique. Re-read the hand-drawn-identity callout at the top of `SKILL.md` before writing
the rig: the result should still look at home next to `FeaturedMorningCoffee.kt`.

## What a worked example would look like

When the first theme lands, its rig should live in `characters/<Name>Character.kt` and expose posable
pieces rather than one rigid "draw the whole character" call — e.g. a `head(cx, cy, r, colors, tilt)`
and a `body(cx, cy, scale, colors, pose)` with limb anchor points scenes draw scene-specific arms/legs
from via the existing `limb()` helper, so a scene can still stage a genuinely different moment (reaching,
pedaling, waving) instead of every scene freehanding the subject from scratch. A style's deltas from
Classic (rounder/chubbier shapes, a bumped foreground outline-weight range, bigger features, a recurring
connective prop playing the role `steam` plays across the coffee-themed scenes) get baked into the rig,
not written per-scene. Every scene in the theme still follows all 11 non-negotiable rules from `SKILL.md`
— including rule 11, `inkShadow(heroPath, colors.outlineShadow)` on its hero shape before the normal
`paint()` call. Default title/subtitle copy for a themed empty state should be charming *and* still
literally usable in a real app — not just a joke relabeling of the Classic copy.
