# Sketchy

**Hand-drawn, animated illustrations & empty states for Jetpack Compose.**

Sketchy is a Kotlin/Compose library of sketch-style, line-art artwork — the
kind of warm, human illustration you'd want for onboarding flows and empty
states — drawn entirely on `Canvas`, animated out of the box, and fully
themeable to match your app.

![License](https://img.shields.io/badge/license-MIT-blue.svg)
![Kotlin](https://img.shields.io/badge/kotlin-2.2.10-7F52FF?logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-BOM%202026.02.01-4285F4?logo=jetpackcompose&logoColor=white)
![Min SDK](https://img.shields.io/badge/minSdk-26-brightgreen)
![PRs Welcome](https://img.shields.io/badge/PRs-welcome-ff69b4.svg)

---

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
- **Genuinely reskinnable.** The empty-state catalog takes color, size, and
  copy as plain parameters (`SketchyColors`, `Dp`, `String`, `TextStyle`) —
  no XML theming, no design-system lock-in. It doesn't even depend on
  Material.

## What's inside

| Catalog | Count | Entry point |
|---|---|---|
| **Onboarding illustrations** | 15, across 6 categories | `SketchyIllustration(sketch, modifier, animate)` |
| **Empty states** | 20, across 4 categories | `SketchyEmptyState(state, modifier, animate, colors, illustrationSize, title, subtitle, titleStyle, subtitleStyle)` |

A `:app` module ships alongside the library as a live, searchable catalog of
every illustration and empty state — the fastest way to browse what's
available and copy the exact usage snippet for whatever you pick.

## Installation

Sketchy isn't published to Maven Central yet — for now, pull it in as a
Gradle composite build or module include from a local clone:

```kotlin
// settings.gradle.kts
includeBuild("../Sketchy") {
    dependencySubstitution {
        substitute(module("com.sketchy:library")).using(project(":library"))
    }
}
```

or simply copy the `library/` module into a multi-module project and add:

```kotlin
// settings.gradle.kts
include(":library")
```

```kotlin
// app/build.gradle.kts
dependencies {
    implementation(project(":library"))
}
```

> Maven Central / JitPack publishing is on the roadmap — see
> [Roadmap](#roadmap).

## Usage

### An onboarding illustration

```kotlin
import com.sketchy.library.Sketch
import com.sketchy.library.SketchyIllustration

SketchyIllustration(
    sketch = Sketch.PlanTasks,
    modifier = Modifier.size(280.dp)
)
```

Every `Sketch` scales to whatever size you give it and animates on a loop by
default. Pass `animate = false` to freeze it on its resting frame — handy for
lower-power devices or a still hero image.

### An empty state, fully restyled

```kotlin
import com.sketchy.library.EmptyState
import com.sketchy.library.SketchyColors
import com.sketchy.library.SketchyEmptyState

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
icon-only illustration.

## Catalog

<details>
<summary><strong>Onboarding illustrations (15)</strong></summary>

| Category | Illustrations |
|---|---|
| Productivity | Plan Every Task · Find Your Focus · Never Miss a Meeting · Capture Every Thought · Build Better Habits |
| Finance | Track Every Expense · Watch Your Savings Grow |
| Fitness | Train Anywhere, Anytime · See Your Progress |
| Food Delivery | Order Your Favorites · Fast, Fresh Delivery |
| Travel | Plan Your Perfect Trip · Explore The World |
| Music | Your Soundtrack, Anywhere · Discover New Sounds |

</details>

<details>
<summary><strong>Empty states (20)</strong></summary>

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
│   └── src/main/java/com/sketchy/library/
│       ├── SketchyIllustrations.kt   # Sketch enum, shared drawing primitives
│       ├── OnboardingFinance.kt      # …grouped by category, 2 scenes each
│       ├── EmptyState.kt             # EmptyState enum, SketchyColors, SketchyEmptyState
│       └── EmptyStates*.kt           # …grouped by category, 5 scenes each
└── app/        # Demo app — searchable, categorized gallery of everything above
```

## Running the demo app

```bash
git clone https://github.com/<your-username>/Sketchy.git
cd Sketchy
./gradlew :app:installDebug
```

Or open the project in Android Studio and run the `app` configuration.

## Requirements

- Android `minSdk` 26+
- Kotlin 2.2.10+
- Jetpack Compose BOM 2026.02.01+ (no Material3 dependency required by
  `:library` itself)

## Contributing

New illustrations are very welcome. A new scene is just a private
`DrawScope` function plus one enum entry — no boilerplate beyond that:

1. Pick a category (or propose a new one) and add an entry to the `Sketch`
   or `EmptyState` enum with its display copy and category.
2. Write the scene as `internal fun DrawScope.drawYourScene(t: Float, ...)`,
   reusing the shared primitives (`stroke`, `sketchLine`, `sketchCircle`,
   `twinkle`, `wave`, `groundHint`) already in the library for a consistent
   hand-drawn look.
3. Wire it into the `when` dispatcher and open a PR.

Please keep new scenes framework-agnostic (no Material/Material3 imports
inside `:library`) so the empty-state API stays usable in any design system.

## Roadmap

- [ ] Publish to Maven Central / JitPack
- [ ] More illustration categories (education, real estate, dating, gaming)
- [ ] Optional Material3 color-scheme bridge for one-line theme matching
- [ ] Snapshot/screenshot tests for every scene

## License

Sketchy is available under the [MIT License](LICENSE).
