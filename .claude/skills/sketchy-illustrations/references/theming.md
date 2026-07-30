# Theming: standing up a new cross-cutting theme

A **theme** is a named character or motif (Panda, Fox, Astronaut, …) drawn in a named **style**
(Cartoony, Minimal, Retro-Future, …) that ships as a matched set spanning *both* catalogs — its own
illustrations and its own complete run of empty states — rather than a one-off scene or two.

## The tagging contract

Every scene in a theme sets two fields on its `Sketch`/`EmptyState` enum entry:

- **`category = <theme name>`** (e.g. `"Panda"`) — reuses the exact grouping/search machinery every
  other category already uses. This is *why* a theme is cheap to add: `SketchyGalleryScreen.kt` /
  `EmptyStateGalleryScreen.kt` group by `category` and `GalleryComponents.kt`'s `matches()` already
  searches it — a theme is simply a category that happens to appear in both `Sketch` and `EmptyState`.
- **`style = <style name>`** (e.g. `"Cartoony"`) — a second, independent tag, defaulted to `"Classic"`
  for every scene that predates theming. `matches()` checks this too, so searching the *style* surfaces
  every theme sharing it (today just Panda; a second Cartoony character later would show up alongside
  it), while searching the *theme name* surfaces only that one character's scenes via `category`.

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

## Worked example: Panda / Cartoony

The first theme. Rig lives at `characters/PandaCharacter.kt`:

- `pandaHead(cx, cy, r, colors, tilt = 0f)` — round head (`paintCircle`), two rounded ear circles, two
  soft ink eye-patches built as organic quadratic-curve blobs (not perfect ellipses — kept hand-inked),
  dot pupils, a small nose/mouth. `tilt` gives each scene's pose its own personality.
- `pandaBody(cx, cy, scale, colors, pose)` — a chubby oval torso with a lighter belly patch (paper
  fill), plus limb anchor points scenes draw scene-specific arms/legs from using the existing `limb()`
  helper (so a scene can still have the panda reaching, pedaling, waving — whatever the moment needs).
- Cartoony deltas from Classic: rounder/chubbier shapes throughout, foreground outline weight bumped to
  2.6f–3.0f (Classic runs 2.2f–2.4f), bigger eyes. Bamboo (via `colors.leaf`/`accentGreen`) is Panda's
  recurring connective prop, the same role `steam` plays across the coffee-themed scenes.
- Every Panda scene still follows all 8 core non-negotiable rules from `SKILL.md`, plus rule 11 —
  `inkShadow(heroPath, colors.outlineShadow)` on its hero shape before the normal `paint()` call.

Empty states ship one Panda scene per existing `EmptyState` use case (20 total, `PandaNoInternet`
through `PandaAllDone`); illustrations are 10 original daily-life scenes (breakfast, a park walk, nap
time, rainy-day errands, bath time, reading by lantern light, baking, a bike ride, stargazing, tending
the garden). Default title/subtitle copy for a themed empty state should be charming *and* still
literally usable in a real app — not just a joke relabeling of the Classic copy (e.g.
`PandaNoInternet`: "No Signal, No Bamboo" / "Even pandas lose Wi-Fi sometimes. Try again in a bit.").
