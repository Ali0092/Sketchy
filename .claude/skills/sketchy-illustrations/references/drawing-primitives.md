# Drawing primitives catalog

Two files back every scene: `utils/Extensions.kt` (the low-level basics every scene uses) and
`utils/Painting.kt` (fills, shading, brushes — the machinery that makes painted mode look painted).
Reach for one of these before writing a bare `drawPath`/`drawCircle`/`drawLine` call — they already
handle unit conversion, hidden-color skipping, and the outline/colorful branch where one exists.

## Coordinates (`Extensions.kt`)

- `d(v: Float): Float` — one design-space number → px. Use for lengths/radii/widths.
- `pt(x: Float, y: Float): Offset` — a design-space point → `Offset` in px. Use for centers/endpoints.

Every coordinate in every scene is a plain `Float` in the 320×320 design space; `d`/`pt` are the only
places that convert. Never call `.dp.toPx()` directly in scene code.

## Strokes (`Extensions.kt`)

- `bold(width = 2.4f)` / `thin(width = 1.6f)` — round-cap, round-join `Stroke`s at a design-space
  width. `bold` for outlines and structural lines, `thin` for detail work (grain, ridges, hatching).
- `dashed()` — fixed 1.4f dashed stroke, used by `groundLine`.
- `stroke(path, color = Ink, width = 2.4f)` — `drawPath` with `bold(width)`, one-liner for a plain
  ink line.
- `sketchLine(from, to, color = Ink, width = 2.4f)` — a single round-capped line segment.
- `sketchCircle(center, radius, color = Ink, width = 2.4f, filled = false)` — a stroked **or** filled
  circle. Use this (not `ellipsePath`) for anything drawn face-on: heads, wheels, badges, buttons,
  balloons, dots.
- `twinkle(cx, cy, size, t, offset = 0f, color = Ink)` — the four/eight-point sparkle every scene
  scatters 2–3 of for warmth: swells and brightens with `t`, and flashes diagonal glints only past
  peak brightness (`k > 0.7`). Always pass distinct `offset`s per sparkle so they don't twinkle in
  lockstep.
- `groundHint(y, color = InkFaint)` — illustrations' faint dashed baseline at fixed x 20–300.
- `groundLine(y, color)` — empty states' equivalent, fixed x 60–260, color explicit (no default —
  pass `colors.inkFaint`). Nearly every empty-state scene ends with one call to this; it's the visual
  cue that grounds a floating icon rather than leaving it adrift on the canvas.

## Path builders (`Painting.kt`)

- `roundRectPath(x, y, w, h, r)` / `rectPath(x, y, w, h)` — axis-aligned shapes in design space.
- `ellipsePath(cx, cy, rx, ry)` — an ellipse via `arcTo`. Use this — **not** `sketchCircle` — for
  anything lying flat and seen at an angle: mug rims, cup interiors, plate edges, bowl mouths, saucer
  rims. `ry ≈ 0.3 × rx` reads as a natural table-height viewpoint; flatten further (`ry ≈ 0.2×`) for a
  surface seen closer to eye level.
- `surfacePath(backY, frontY, backInset, frontInset, r = 12f)` — a table/desk/counter/sill in
  perspective: wider at front than back, softened front corners. `backInset`/`frontInset` hold it off
  the canvas edges — a surface is a real object with visible ends, never a band bleeding off-canvas.

## Fill + outline (`Painting.kt`)

- `fill(path, color)` / `fill(path, brush)` — a bare fill; the `Color` overload skips painting
  entirely when `color.isHidden` (fully transparent) — this is the mechanic that makes outlined mode
  "just work."
- **`paint(path, color|brush, ink, width = 2.2f)`** — the workhorse: fill, then trace in ink. Prefer
  this over a bare `fill` for anything whose *silhouette* carries the drawing — outlined, the fill
  drops out and the ink trace alone is exactly the line-art result you want, from unchanged scene code.
- `paintCircle(center, radius, color, ink, width = 2.4f)` — the `paint()` equivalent for circular
  objects (heads, wheels, plates, badges).
- `limb(path, color, ink, width = 2.4f, thickness = 7f)` — an open path meant to read as a rod with
  real girth when painted (an arm, leg, stem, cable) but a single plain ink line when outlined.
  **Don't** use `paintStroke` directly for these — outlined, `paintStroke` renders a *hollow*
  two-edged rod (correct for a mug handle, wrong for a limb that should stay a single stroke).
- `paintStroke(path, color, ink, width, outline = 1.9f)` — an open path as a solid rod outlined on
  **both** edges — mug handles, tails, cables. Outlined, the fill's middle is punched back out of the
  ink bar (via a clipped layer) so only the two edges remain, instead of leaving one fat ink bar.
- `brushStroke(path, brush, width = 2f)` — strokes a path with a gradient brush, for anything that
  should taper or fade along its length (used by `steam`).

## Brushes (`Painting.kt`)

- `vBrush(fromY, toY, from, to, mid = null)` / `hBrush(fromX, toX, ...)` / `dBrush(fromX, fromY, toX, toY, ...)`
  — vertical / horizontal / diagonal gradients between two design-space positions. `dBrush` for raking
  light across a surface at an angle.
- `glowBrush(cx, cy, radius, core)` — radial bloom, `core` fading to fully transparent at the edge.

## Shading & light (`Painting.kt`) — painted mode's vocabulary

- `shade(path, brush)` — paints a brush *clipped to* `path`, for a shadow/highlight gradient that
  must not escape a surface's silhouette. Bring your own transparent stop; a brush that covers the
  whole shape opaquely isn't shading.
- `sheen(path, from, to, color)` — a specular streak clipped to `path`, raking from `from` to `to`.
- `glow(cx, cy, radius, color)` — a radial light bloom (lamps, sunlight through glass, screen spill).
- `contactShadow(cx, cy, rx, ry, color)` — **the** trick for grounding an object: three stacked
  ellipses (widest+faintest first) standing in for a blur. Call it *before* drawing the object it
  belongs to, and — when multiple objects share one surface — clip the whole group of calls to the
  surface path so no shadow can float off its edge (see the coffee-table scene: `clipPath(table) { ... }`
  wrapping three `contactShadow` calls).
- `innerRim(path, dx, dy, color, width = 2f)` — darkens the interior edge of a silhouette by
  re-stroking it offset (away from the light) and clipping to itself. Sells hollow, rounded objects:
  mug rims, pot lips, lamp shades, screen bezels.
- `castShadow(x, y, w, h, skew, color)` — a directional parallelogram shadow raked away from the
  light across a flat surface.
- `steam(x, y, t, offset = 0f, color, height = 46f)` — a rising, curling, fading wisp, phase-shifted
  a quarter turn so `t = 0` (the `animate = false` resting frame) shows a fully formed wisp instead of
  nothing. Copy this phase-shift trick for any other looping effect that must not freeze on "nothing
  drawn yet."

## Color-safety helpers (`Painting.kt` top + `SketchyStyle`)

- `Color.isHidden` — true iff `alpha == 0f`. The check every fill/paint/shadow primitive makes before
  painting anything.
- `Color.a(alpha)` — re-alpha a color, **except** an already-hidden one stays hidden. Always use this
  on a palette/style slot instead of `.copy(alpha = …)` (rule #5 in `SKILL.md`).
- `Color.lit(amount = 0.22f)` / `Color.shaded(amount = 0.22f)` — mix towards white (lit face) or a
  cool near-black `#1A2434` (shadowed face) — the standard way a painted scene shows one surface's two
  faces without a second material slot.

## Timing/pseudo-random primitives that live in `Painting.kt` (see `references/animation.md` for use)

`pulse`, `loop`, `smooth01`, `hash01` are defined at the top of `Painting.kt` alongside these drawing
primitives even though they're timing helpers — see the animation reference for what each does and
when to reach for it.
