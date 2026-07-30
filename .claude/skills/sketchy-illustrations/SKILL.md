---
name: sketchy-illustrations
description: Best practices for drawing hand-sketched illustrations and empty-state scenes in the Sketchy Compose Multiplatform library — the outline/colorful duality, the 320dp design canvas, animation timing, drawing primitives, and composition rules. Load before writing or editing any file under library/src/commonMain/kotlin/com/sketchy/library/illustrations or /emptystates, before adding a new Sketch or EmptyState scene, or when asked to touch SketchyColors/SketchyStyle.
user-invocable: true
---

Sketchy is a Kotlin Multiplatform library (`:library`, published as `io.github.ali0092:sketchy`)
of hand-drawn, animated illustrations and empty states, rendered entirely on Compose `Canvas` —
no image assets, no Lottie. Every one of its ~40 scenes is written **once** and renders two ways:
a pure ink outline by default, or a fully painted scene when the caller passes `colorful = true`.
That duality, not a second set of artwork, is the core trick the whole library is built around —
everything below exists to keep it working as new scenes get added.

## Non-negotiable rules

These are what actually break (silently, and often only in one of the two render modes) if skipped:

1. **One scene = one function.** `internal fun DrawScope.drawXScene(t: Float, colors: SketchyStyle)`
   (illustrations) or `drawXState(t: Float, colors: SketchyStyle)` (empty states). No `@Composable`,
   no side effects — a scene is a pure function of `t` and `colors`.
2. **Draw only in the 320×320dp design space** (`utils.DesignSize`). Every coordinate is a design-space
   number converted with `d()` (lengths) or `pt()` (points) — see `references/drawing-primitives.md`.
   The caller scales the whole canvas to fit; a scene never checks its own render size.
3. **Never paint a background.** Transparent canvas in both modes, always — Sketchy is artwork to
   drop onto a screen the caller already styled, never a background of its own.
4. **Color only ever comes from the `colors: SketchyStyle` parameter.** Never the raw `Ink` /
   `Accent` / `AccentTeal` constants in `utils/Utils.kt` (those back `SketchyColors`' defaults only),
   never a hardcoded `Color(0x...)`. This is both the reskinning contract and the *only* reason the
   outline/colorful duality works with zero `if (colorful)` branches in scene code — see
   `references/color-and-style.md`.
5. **Never call `.copy(alpha = …)` on a style/palette slot that can be hidden.** Use the `.a()`
   extension (`Color.a(alpha)`), or a `SketchyStyle` helper that already accounts for it
   (`line`, `hint`, `faint`, `touch`, …). `copy` on an invisible (fully transparent) slot resurrects
   it as opaque black instead of leaving it invisible. Plain accent colors you know are always real
   (`colors.accentRed` for a badge) are fine with ordinary `.copy`.
6. **No `if (colorful)` / `if (outlined)` branching inside a scene.** If a shape needs to look
   different between the two modes, that difference belongs in a `SketchyStyle`/`Painting.kt`
   primitive (`material()`, `line()`, `lineOnly`, `paintStroke`'s outlined branch, …), not in scene code.
7. **No Material / Material3 imports inside `:library`.** Keep the drawing code framework-agnostic;
   theming is done entirely through `SketchyColors`, `Dp`, `String` and `TextStyle` parameters.
8. **Register the scene twice**: an enum entry (`Sketch` or `EmptyState`, with display copy and
   `category`) and a branch in the `when` dispatcher (`drawIllustration` in `SketchyIllustrations.kt`,
   or `drawEmptyState` in `EmptyState.kt`).

## Reference files (load as needed)

| File | Load it when you need... |
|---|---|
| `references/color-and-style.md` | The full `SketchyColors` palette, what `SketchyStyle` collapses and why, and which helper (`line`/`hint`/`faint`/`inkOf`/`touch`/`lineOnly`) to reach for. |
| `references/drawing-primitives.md` | The catalog of shared drawing functions in `utils/Extensions.kt` and `utils/Painting.kt` — path builders, fill/paint/limb helpers, shading, shadows — with when to use which. |
| `references/animation.md` | The timing model: the looping phase `t`, `wave`/`pulse`/`loop`/`smooth01`/`hash01`, entrance motion, and patterns for per-element desync so nothing looks metronomic. |
| `references/composition.md` | Canvas layout conventions, depth-by-outline-weight, shadow/ground-line grounding, scene anatomy (background → midground → foreground), and a pre-flight checklist for a new scene. |

## Workflow: adding a new scene

1. **Pick the file.** Featured scenes (`illustrations/Featured*.kt`) get one elaborate file each and
   lean hard on `Painting.kt`. Onboarding scenes are grouped 2–5 per category file
   (`illustrations/Onboarding<Category>.kt`). Empty states are grouped 5 per category file
   (`emptystates/EmptyStates<Category>.kt`). Add a new category file only if none fits.
2. **Add the enum entry** in `Sketch` (`illustrations/SketchyIllustrations.kt`) or `EmptyState`
   (`emptystates/EmptyState.kt`) — display name (+ default title/subtitle for empty states) and category.
3. **Write the scene** against the 320dp canvas, reusing `utils/Extensions.kt` and `utils/Painting.kt`
   primitives — see `references/drawing-primitives.md` before reaching for `drawPath`/`drawCircle`
   directly. Compose it background → midground → foreground per `references/composition.md`, drive
   its motion off `t` per `references/animation.md`, and source every color from `colors: SketchyStyle`.
4. **Wire it into the dispatcher** (`drawIllustration` / `drawEmptyState`).
5. **Preview it both ways.** The demo app is a live gallery across all four platforms:
   ```bash
   ./gradlew :composeApp:run                          # desktop — fastest inner loop
   ./gradlew :androidApp:installDebug                 # Android
   ./gradlew :composeApp:wasmJsBrowserDevelopmentRun   # web
   ```
   Check the new scene both as the default outline **and** with `colorful = true` (the gallery screens
   in `:composeApp`, e.g. `SketchyGalleryScreen.kt` / `EmptyStateGalleryScreen.kt`, toggle this) —
   a scene that only looks right in one mode has a rule violation above, almost always #4, #5 or #6.
   Also sanity-check `animate = false`: the frozen resting frame should still read as a complete
   drawing, not a mid-motion glitch (this is why `steam`'s phase is shifted a quarter turn — see
   `references/animation.md`).
