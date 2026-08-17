# Sketchy

**Hand-drawn, animated illustrations & empty states for Compose Multiplatform.**

Sketchy is a Kotlin Multiplatform library of sketch-style, line-art artwork —
the kind of warm, human illustration you'd want for onboarding flows and empty
states — drawn entirely on `Canvas`, animated out of the box, and fully
themeable to match your app. It runs on Android, iOS, desktop and the web from
one `commonMain` source set.

[![Maven Central](https://img.shields.io/maven-central/v/io.github.ali0092/sketchy.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/io.github.ali0092/sketchy)
[![](https://jitpack.io/v/Ali0092/Sketchy.svg)](https://jitpack.io/#Ali0092/Sketchy)
![License](https://img.shields.io/badge/license-MIT-blue.svg)
![Kotlin](https://img.shields.io/badge/kotlin-2.3.21-7F52FF?logo=kotlin&logoColor=white)
![Compose Multiplatform](https://img.shields.io/badge/Compose%20Multiplatform-1.11.1-4285F4?logo=jetpackcompose&logoColor=white)
![Platforms](https://img.shields.io/badge/platforms-Android%20%7C%20iOS%20%7C%20Desktop%20%7C%20Web-orange)
![Min SDK](https://img.shields.io/badge/minSdk-26-brightgreen)
![PRs Welcome](https://img.shields.io/badge/PRs-welcome-ff69b4.svg)

## Supported platforms

| Target | Status |
|---|---|
| **Android** | `minSdk 26` |
| **iOS** | `iosArm64`, `iosSimulatorArm64` |
| **Desktop (JVM)** | Windows, macOS, Linux |
| **Web** | `wasmJs` (browser) |

`iosX64` (the Intel simulator) is not supported — Compose Multiplatform no
longer publishes artifacts for it.

---
## Demo
https://github.com/user-attachments/assets/b5ab7d8a-45b9-43c3-ab53-1d2f6dc9a673

## Why Sketchy

Most illustration libraries ship static SVGs or Lottie files — dependencies
outside Compose's own rendering pipeline, hard to restyle, and rarely
animated in a way that feels alive. Sketchy takes a different approach:

- **Pure Compose, zero image assets.** Every illustration is vector line-art
  drawn with `DrawScope`, so it's crisp at any size and ships as pure Kotlin
  — no drawables, no Lottie JSON, no binary bloat.
- **Animated by default.** Each scene has its own small, purposeful motion —
  a sweeping stopwatch hand, a ringing bell, a heartbeat pulse — not just a
  generic fade-in. Turn it off with a single `animate = false` when you want
  a static frame instead.
- **Hand-drawn by default, painted on request.** Every scene renders as a
  pure outline sketch — ink lines on a transparent canvas, nothing filled in,
  lifted only by small accent marks. Pass `colorful = true` and the same
  scene is painted in full: shaded surfaces, contact shadows, highlights. No
  second set of artwork, and never a background of its own either way.
- **Genuinely reskinnable.** Both catalogs take color, size, and copy as
  plain parameters (`SketchyColors`, `Dp`, `String`, `TextStyle`) — no XML
  theming, no design-system lock-in. It doesn't even depend on Material.

## What's inside

| Catalog | Count | Entry point |
|---|---|---|
| **Featured illustrations** | 6 elaborate full scenes | `SketchyIllustration(modifier, sketch, animate, colorful, colors)` |
| **Onboarding illustrations** | 17, across 7 categories | `SketchyIllustration(modifier, sketch, animate, colorful, colors)` |
| **Empty states** | 52, across 7 categories | `SketchyEmptyState(state, modifier, animate, colorful, colors, illustrationSize, title, subtitle, titleStyle, subtitleStyle, spacing)` |

Every one of the 75 scenes draws two ways from the same code — a colourless
hand-drawn outline by default, or fully painted with `colorful = true`.

A demo ships alongside the library as a live, searchable catalog of every
illustration and empty state — the fastest way to browse what's available and
copy the exact usage snippet for whatever you pick. Its UI lives in
`:composeApp` and is shared across all four platforms:

```bash
./gradlew :composeApp:run                          # desktop
./gradlew :androidApp:installDebug                 # Android
./gradlew :composeApp:wasmJsBrowserDevelopmentRun  # web
open iosApp/iosApp.xcodeproj                       # iOS (run from Xcode)
```

## Two looks, one drawing

Sketchy never ships two sets of artwork. Each scene is written once, and the
`colorful` flag decides how the palette hands its colours back to the drawing
code:

| | `colorful = false` *(default)* | `colorful = true` |
|---|---|---|
| **Look** | Pure line-art — ink outlines, nothing filled in | Gradient-shaded surfaces, contact shadows, highlights, under the same outlines |
| **Palette slots used** | `ink`, `inkSoft`, `inkFaint` + the four accents | All of the above **plus** the light and material slots |
| **Colour** | Only small accent marks — a sparkle, a glint, one highlighted bar | Full colour throughout |
| **Background** | Transparent | Transparent |
| **Best for** | Minimal / monochrome UIs, dark mode, matching a single brand ink | Onboarding heroes, marketing screens, playful apps |

Neither mode ever paints a background of its own, so a scene drops onto
whatever surface your screen already has.

How it works: when a scene is outlined, every surface, light and material slot
of the palette reads back as `Color.Transparent`, so the fill simply isn't
painted and what survives is the ink outline of the same shape. That's why
`colorful` is one boolean and not a second catalog.

## Installation

Sketchy is live on Maven Central as `io.github.ali0092:sketchy` — the version
badge above always reflects the latest published release.

A multiplatform consumer adds it to `commonMain` — `mavenCentral()` is already
declared by default in a Kotlin Multiplatform project's repositories, no extra
repository setup required:

```kotlin
// build.gradle.kts
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("io.github.ali0092:sketchy:1.0.5")
        }
    }
}
```

An Android-only consumer adds it the usual way:

```kotlin
// app/build.gradle.kts
dependencies {
    implementation("io.github.ali0092:sketchy:1.0.5")
}
```

Sketchy is also mirrored on JitPack, kept at the same version and coordinate
as Maven Central, as a fallback in case Maven Central isn't reachable from
your network:

```kotlin
dependencies {
    implementation("io.github.ali0092:sketchy:1.0.5")
}
```

with the JitPack repository declared in `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven("https://jitpack.io")
    }
}
```

## Usage

### An onboarding illustration

```kotlin
import com.sketchy.library.illustrations.Sketch
import com.sketchy.library.illustrations.SketchyIllustration

SketchyIllustration(
    sketch = Sketch.PlanTasks,
    modifier = Modifier.size(280.dp)
)
```

Every `Sketch` scales to whatever size you give it and animates on a loop by
default. Pass `animate = false` to freeze it on its resting frame — handy for
lower-power devices or a still hero image.

### The same illustration, colourless or colourful

```kotlin
// Colourless — the default. Ink outlines, a few accent marks, nothing filled in.
SketchyIllustration(
    sketch = Sketch.MorningCoffee,
    modifier = Modifier.size(280.dp),
)

// Colourful — the same scene, painted: shaded surfaces, shadows, highlights.
SketchyIllustration(
    sketch = Sketch.MorningCoffee,
    modifier = Modifier.size(280.dp),
    colorful = true,
)
```

`colorful` works on every illustration and every empty state, and it's an
ordinary parameter — drive it from a setting, from `isSystemInDarkTheme()`, or
from a single app-wide constant:

```kotlin
SketchyEmptyState(
    state = EmptyState.EmptyCart,
    colorful = !isSystemInDarkTheme(),
)
```

### A featured illustration

The six **Featured** scenes are the elaborate ones — a whole scene rather
than a single motif, one source file each, built on the shading primitives the
painted mode uses. They're the ones worth giving a full-bleed hero slot:

```kotlin
SketchyIllustration(
    sketch = Sketch.ReadingNook,   // or WarmWelcome, MorningCoffee, HomeWorkspace, GroceryRun, RainyWindow
    modifier = Modifier.fillMaxWidth().aspectRatio(1f),
    colorful = true,
)
```

They take exactly the same parameters as every other `Sketch` — `animate`,
`colorful` and `colors` all behave identically — and they render colourless
just as happily if that's your look. Because they carry the most detail, they
also gain the most from `colorful = true`, so they're the best place to start
if you're deciding between the two modes.

### An illustration, restyled to match your theme

`colors` is a plain `SketchyColors` parameter — override any subset of it and
every stroke, fill, and sparkle in the scene repaints to match, no XML themes
or design-system lock-in required.

A colourless scene only ever draws from the first block of the palette — `ink`,
`inkSoft`, `inkFaint` and the four accents (`accent`, `accentGreen`,
`accentBlue`, `accentRed`). The light and material slots below them are what
the colourful version fills with, so reskinning a colourless app means changing
the ink and the accents and nothing else:

```kotlin
import com.sketchy.library.SketchyColors
import com.sketchy.library.illustrations.Sketch
import com.sketchy.library.illustrations.SketchyIllustration

SketchyIllustration(
    sketch = Sketch.BuildBetterHabits,
    modifier = Modifier.size(280.dp),
    colors = SketchyColors(
        ink = MaterialTheme.colorScheme.onSurface,
        accent = MaterialTheme.colorScheme.primary,
        accentBlue = MaterialTheme.colorScheme.secondary,
    )
)
```

If you use `colorful = true`, the rest of the palette is worth a look too —
it's grouped so you can retint a whole painted scene a few slots at a time:

| Block | Slots | What it paints |
|---|---|---|
| **Ink** | `ink`, `inkSoft`, `inkFaint` | Every outline, in both modes |
| **Accents** | `accent`, `accentGreen`, `accentBlue`, `accentRed` | Small coloured marks, in both modes |
| **Light & atmosphere** | `paper`, `sun`, `sunDeep`, `glow`, `sky`, `skyDeep`, `shade`, `shadeSoft` | Key light, bloom, shadows — colourful only |
| **Materials** | `wood`, `woodDark`, `leaf`, `leafDark`, `terracotta`, `clay`, `fabric`, `fabricDark`, `metal`, `metalDark`, `skin`, `skinDark`, `hair`, `coffee` | The surfaces themselves — colourful only |

Overriding a material slot while a scene is colourless is harmless — it's
simply never read.

### An empty state, fully restyled

```kotlin
import com.sketchy.library.SketchyColors
import com.sketchy.library.emptystates.EmptyState
import com.sketchy.library.emptystates.SketchyEmptyState

SketchyEmptyState(
    state = EmptyState.NoInternet,
    colors = SketchyColors(
        ink = MaterialTheme.colorScheme.onSurface,
        accent = MaterialTheme.colorScheme.primary,
    ),
    illustrationSize = 180.dp,
    title = "You're offline",
    subtitle = "We'll sync everything as soon as you're back online.",
    titleStyle = MaterialTheme.typography.titleMedium,
    subtitleStyle = MaterialTheme.typography.bodyMedium,
)
```

Every knob — color, size, copy, typography — is an ordinary parameter with a
sensible default. Omit `title`/`subtitle` entirely (pass `null`) for an
icon-only illustration. `SketchyColors` is the same type for both catalogs, so
one palette restyles your entire onboarding flow and empty-state set at once.

## Same situation, different families

A handful of situations — a network error, an empty result set, "all caught
up" — are drawn in more than one visual family, so you pick whichever fits
your app instead of being stuck with one look:

- **The original catalog** — no prefix (`NoInternet`, `EmptyCart`, `AllDone`,
  …). The default, general-purpose style, covering four categories:
  Connectivity & Errors, Content & Search, Saved & Commerce, and Everyday &
  Productivity.
- **`Signboard*`** — road signage as the scene's setting: signals, warning
  and stop signs, cones, boards.
- **`Network*`** — network hardware only: routers, servers, cables — no
  abstract icon glyphs.
- **`LinedMan*`** — a single minimal outline figure. The body never changes;
  only the eyes and mouth do, to carry the mood.

Every prefixed family names its entries `{Family}{Situation}`, so the family
is right there in the enum: `LinedManLoading`, `NetworkNoInternet`,
`SignboardNetworkError`. Where a situation is covered by more than one
family, here's the cross-reference:

| Situation | Original catalog | Signboards | Network | Lined Man |
|---|---|---|---|---|
| Page not found (404) | `PageNotFound` | `SignboardPageNotFound` | `NetworkPageNotFound` | — |
| Network / connection error | `NoInternet` · `ServerError` | `SignboardNetworkError` | `NetworkNoInternet` · `NetworkBadGateway` · `NetworkServerError` · `NetworkUnsecureWifi` | `LinedManNoConnection` · `LinedManSomethingWrong` |
| No data / no results | `NoResults` · `NoData` | `SignboardNoData` | `NetworkNoData` · `NetworkNoResults` | `LinedManNoResults` |
| Access denied | — | `SignboardUnauthorized` · `SignboardForbidden` | — | `LinedManAccessDenied` |
| Under maintenance / coming soon | `UnderMaintenance` | `SignboardMaintenance` · `SignboardComingSoon` | — | — |
| No messages / comments | `NoMessages` · `NoComments` | — | `NetworkNoMessages` · `NetworkNoComments` | — |
| Empty inbox | `EmptyInbox` | — | — | `LinedManEmptyInbox` |
| All caught up / all clear | `AllDone` · `NoNotifications` | `SignboardAllClear` · `SignboardNoWarnings` | — | `LinedManAllDone` · `LinedManAllCaughtUp` |

Swapping styles is a one-line change — same parameters, same `colors`
override, same optional `title`/`subtitle`:

```kotlin
// The default style
SketchyEmptyState(state = EmptyState.NoInternet)

// The same situation, drawn as network hardware instead
SketchyEmptyState(state = EmptyState.NetworkNoInternet)

// ...or as the minimal Lined Man figure
SketchyEmptyState(state = EmptyState.LinedManNoConnection)
```

Every other entry (`EmptyCart`, `LinedManWelcome`, `NetworkNoList`, …) is
currently exclusive to its family — a good spot to contribute a matching
scene in a different style.

## Catalog

Every entry below draws both ways — colourless by default, painted with
`colorful = true`.

<details>
<summary><strong>Featured illustrations (6)</strong></summary>

| `Sketch` | Scene |
|---|---|
| `Sketch.WarmWelcome` | A Warm Welcome |
| `Sketch.MorningCoffee` | A Slow Morning Coffee |
| `Sketch.HomeWorkspace` | Your Workspace at Home |
| `Sketch.GroceryRun` | The Weekly Grocery Run |
| `Sketch.ReadingNook` | A Quiet Reading Corner |
| `Sketch.RainyWindow` | Rainy Day Indoors |

</details>

<details>
<summary><strong>Onboarding illustrations (17)</strong></summary>

| Category | Illustrations |
|---|---|
| Signboards | Road Work Ahead · Every Path Leads Somewhere |
| Productivity | Plan Every Task · Find Your Focus · Never Miss a Meeting · Capture Every Thought · Build Better Habits |
| Finance | Track Every Expense · Watch Your Savings Grow |
| Fitness | Train Anywhere, Anytime · See Your Progress |
| Food Delivery | Order Your Favorites · Fast, Fresh Delivery |
| Travel | Plan Your Perfect Trip · Explore The World |
| Music | Your Soundtrack, Anywhere · Discover New Sounds |

</details>

<details>
<summary><strong>Empty states (52)</strong></summary>

A minimal single-outline standing figure — the same body every time, only
the eyes and mouth change to carry the mood:

| Category | Empty states |
|---|---|
| Lined Man | All Caught Up · Something Went Wrong · Loading · No Connection · Access Denied · All Done · No Results Found · Your Inbox is Empty · Welcome! · Just a Moment · Taking a Break |

Road signage as the scene's setting — signals, warning/stop signs, cones, boards:

| Category | Empty states |
|---|---|
| Signboards | Page Not Found · Network Error · No Data Available · Sign In Required · Access Denied · Under Maintenance · All Clear · No Warnings · Coming Soon · Nothing Posted Yet · End of the Road |

Network hardware only — routers, servers, cables, no abstract icons:

| Category | Empty states |
|---|---|
| Network | No Internet Connection · Page Not Found · Bad Gateway · Something Went Wrong · Unsecured Connection · No Data Available · Nothing Connected · No Messages Yet · No Comments Yet · No Results Found |

And the original catalog:

| Category | Empty states |
|---|---|
| Connectivity & Errors | No Internet · Server Error · Sync Failed · Under Maintenance · Location Not Found |
| Content & Search | No Results · No Data · No Comments · No Messages · Page Not Found (404) |
| Saved & Commerce | Empty Cart · Empty Wishlist · No Favorites · No Bookmarks · No Downloads |
| Everyday & Productivity | Empty Inbox · No Notifications · Empty Calendar · No Photos · All Done |

</details>

## Project structure

```
Sketchy/
├── library/    # The published artifact — pure Compose, no app dependencies
│   └── src/commonMain/kotlin/com/sketchy/library/
│       ├── SketchyColors.kt           # Shared reskinnable palette (both catalogs)
│       ├── SketchyStyle.kt            # Outlined vs. painted — how one scene renders both ways
│       ├── illustrations/
│       │   ├── SketchyIllustrations.kt  # Sketch enum, SketchyIllustration composable
│       │   ├── Featured*.kt             # …the elaborate full scenes, one file each
│       │   └── Onboarding*.kt           # …grouped by category, 2-5 scenes each
│       ├── emptystates/
│       │   ├── EmptyState.kt            # EmptyState enum, SketchyEmptyState composable
│       │   └── EmptyStates*.kt          # …grouped by category
│       └── utils/
│           ├── Extensions.kt            # DrawScope drawing extensions (stroke, sketchLine, …)
│           ├── Painting.kt              # Fills, shading, brushes, limbs — the painted half
│           └── Utils.kt                 # Ink/accent color constants, wave(), DesignSize
├── composeApp/ # Demo UI — searchable, categorized gallery of everything above,
│   │           # shared across all four platforms
│   ├── src/commonMain/kotlin/   # The catalog screens
│   ├── src/androidMain/kotlin/  # Clipboard + Material You actuals
│   ├── src/jvmMain/kotlin/      # Desktop entry point (main.kt)
│   ├── src/iosMain/kotlin/      # iOS entry point (MainViewController.kt)
│   └── src/wasmJsMain/kotlin/   # Web entry point (main.kt)
├── androidApp/ # Thin Android shell — Activity, manifest, launcher resources.
│               # Separate because AGP 9 won't apply com.android.application
│               # alongside the Kotlin Multiplatform plugin.
└── iosApp/     # Xcode project hosting the shared Compose UI
```

## Contributing

New illustrations are very welcome. A new scene is just an `internal`
`DrawScope` function plus one enum entry — no boilerplate beyond that:

1. Pick a category (or propose a new one) and add an entry to the `Sketch`
   or `EmptyState` enum with its display copy and category.
2. Write the scene as `internal fun DrawScope.drawYourScene(t: Float, colors: SketchyStyle)`
   in the matching `illustrations/Onboarding*.kt` or `emptystates/EmptyStates*.kt`
   file, reusing the shared extensions from `utils/Extensions.kt` (`stroke`,
   `sketchLine`, `sketchCircle`, `twinkle`, `wave`, `groundHint`/`groundLine`)
   for a consistent hand-drawn look — and always paint from `colors.*`
   (never the raw `Ink`/`Accent` constants in `utils/Utils.kt`) so the scene
   stays themeable.
3. Wire it into the `when` dispatcher and open a PR.

Please keep new scenes framework-agnostic (no Material/Material3 imports
inside `:library`) so the empty-state API stays usable in any design system.

## License

Sketchy is available under the [MIT License](LICENSE).

<div align="center">

### Muhammad Ali

<a href="mailto:aliatwork364@gmail.com">
  <img src="https://img.shields.io/badge/Email-D14836?style=for-the-badge&logo=gmail&logoColor=white" alt="Email" />
</a>
<a href="https://muhammadali0092.netlify.app" target="_blank">
  <img src="https://img.shields.io/badge/Portfolio-000000?style=for-the-badge&logo=react&logoColor=61DAFB" alt="Portfolio" />
</a>
<a href="https://www.linkedin.com/in/muhammad-ali-a28422222" target="_blank">
  <img src="https://img.shields.io/badge/LinkedIn-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white" alt="LinkedIn" />
</a>
<a href="https://github.com/Ali0092" target="_blank">
  <img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white" alt="GitHub" />
</a>

</div>

---

<div align="center">

❤️ **Created with love by [Muhammad Ali](https://github.com/Ali0092)**

> *"Empty screens deserve better than a spinner."*

</div>
