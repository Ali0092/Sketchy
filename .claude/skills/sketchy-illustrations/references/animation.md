# Animation model

Sketchy scenes are animated by default and static on request — never the other way round, and never
by swapping in a second drawing. Every scene receives one input, `t`, and derives all of its motion
from it with pure math. No `Random`, no per-scene animation state, no coroutines inside a scene.

## Where `t` comes from

`SketchyIllustration`/`SketchyEmptyState` own the only animation state a scene ever sees:

- **The loop**: `rememberInfiniteTransition` animates a `Float` `0f → 1f` over `tween(4000, LinearEasing)`,
  `infiniteRepeatable`. That's `t` — a phase that sweeps once every 4 seconds, forever, linearly.
  When `animate = false`, `t` is simply pinned at `0f` — so **`t = 0` must always be a coherent resting
  pose**, not a mid-gesture glitch (see the `steam` phase-shift note below).
- **Entrance**: an `Animatable` (`appear`) animates `0f → 1f` once, over `tween(700, FastOutSlowInEasing)`,
  on first composition. It drives the `Canvas`'s `graphicsLayer { alpha = appear.value }` and a
  0.94→1.0 scale — a soft fade/pop-in, independent of the looping `t`.
- **Whole-canvas breathing**: every scene's `Canvas` also gets
  `translationY = wave(t) * 3.dp.toPx()` in its `graphicsLayer` — a slow ±3dp float applied uniformly
  on top of whatever the scene draws. A scene never has to add this itself.

A scene function only ever receives `t` post-loop and `colors: SketchyStyle`; it has no knowledge of
`animate`, entrance, or the breathing float.

## The four shaping functions (`utils/Utils.kt` + `utils/Painting.kt`)

| Function | Range | Shape | Use for |
|---|---|---|---|
| `wave(t, offset = 0f)` | −1..1 | `sin((t+offset) · 2π)` | Anything that oscillates: bob, sway, a breathing glow, a needle sweep. This is the one you reach for by default. |
| `pulse(t, offset = 0f)` | 0..1 | `(1 + wave) / 2` | A "swell" — brightness, scale — when you want the oscillation remapped to a positive range instead of doing `(1f + wave(...)) / 2f` inline every time. |
| `loop(t, offset = 0f)` | 0..1 | Sawtooth: `((t+offset) % 1 + 1) % 1` | Anything that falls, rises, or resets rather than oscillates — a raindrop's fall, a search radius that grows and snaps back, `steam`'s climb. |
| `smooth01(x)` | 0..1 | Clamped smoothstep `3x²−2x³` | Easing a raw 0..1 fraction (e.g. a `loop()` output, or `(t - threshold) / window`) so a motion doesn't start/stop with a linear kink. |
| `hash01(i: Int)` | 0..1 | Deterministic pseudo-random from `sin(i · 12.9898) · 43758.547`, fractional part | Per-index jitter — scattered rain, stars of varying size, uneven wood grain — where regular `i * k` spacing would look mechanical, but a scene still can't hold `Random` state. Pure function of the index, so the scene stays a pure function of `t`. |

Always pass a distinct `offset` per repeated element (multiple sparkles, multiple arcs, multiple LEDs)
so they move out of phase — see `drawNoInternet`'s three wifi arcs at `i * 0.22f`, or the two `steam`
calls in the coffee scene at `0f`/`0.4f`. Elements that move in lockstep read as one mechanism, not a
hand-drawn scene.

## Common patterns seen across scenes

- **Threshold reveal within the loop**: `if (t > checkAt) { /* checked state */ } else { /* plain */ }`
  — the tasks-scene checklist reveals its four rows at staggered `checkAt` thresholds
  (`0.08f + row * 0.17f`) each pass through the 4s loop, so the whole list "fills in" once per cycle.
- **Rotation/scale tied to `t`**: `withTransform({ rotate(degrees = ..., pivot = ...) }) { ... }` (a
  slowly turning gear: `360f * t`) or `withTransform({ scale(...) }) { ... }` (a pulsing alert badge:
  `1f + 0.15f * pulse(t, 0.4f)`) around the shape's own center.
- **Alpha flicker for distress/urgency**: `color.copy(alpha = 0.4f + 0.6f * pulse(t, offset))` on a
  small accent element (a red LED, a warning crack, an "x") — cheap and reads as "something's wrong"
  without extra geometry.
- **Struggling vs. free motion**: an oscillation (`wave`) reads as *stuck/struggling* (sync-failed's
  refresh arrows rock back and forth); a monotonic `360f * t` rotation reads as *working normally*
  (maintenance's gear); choose deliberately based on what the empty state means.
- **Phase-shift so `t = 0` isn't empty**: anything that "grows from nothing" over its cycle (`steam`,
  a search-radius ring) should offset its phase so the resting frame (`animate = false`, or the start
  of any loop) shows a formed instance of the effect, not a blank. `steam` uses
  `loop(t, offset + 0.25f)` for exactly this.
- **Drift, not just oscillation, on decorative details**: small secondary motion (leaves swaying
  `2.5f * wave(t, 0.15f)`, a coffee glint drifting `1.5f * wave(t, 0.2f)`) sells "alive" far more than
  scaling up the primary motion would.
