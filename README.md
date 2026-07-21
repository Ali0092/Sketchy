# Sketchy

**Hand-drawn, animated illustrations & empty states for Jetpack Compose.**

Sketchy is a Kotlin/Compose library of sketch-style, line-art artwork — the
kind of warm, human illustration you'd want for onboarding flows and empty
states — drawn entirely on `Canvas`, animated out of the box, and fully
themeable to match your app.

[![](https://jitpack.io/v/Ali0092/Sketchy.svg)](https://jitpack.io/#Ali0092/Sketchy)
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
- **Genuinely reskinnable.** Both catalogs take color, size, and copy as
  plain parameters (`SketchyColors`, `Dp`, `String`, `TextStyle`) — no XML
  theming, no design-system lock-in. It doesn't even depend on Material.

## What's inside

| Catalog | Count | Entry point |
|---|---|---|
| **Onboarding illustrations** | 15, across 6 categories | `SketchyIllustration(modifier, sketch, animate, colors)` |
| **Empty states** | 20, across 4 categories | `SketchyEmptyState(state, modifier, animate, colors, illustrationSize, title, subtitle, titleStyle, subtitleStyle)` |

A `:app` module ships alongside the library as a live, searchable catalog of
every illustration and empty state — the fastest way to browse what's
available and copy the exact usage snippet for whatever you pick.

## Installation

Sketchy is published on [JitPack](https://jitpack.io/#Ali0092/Sketchy).

Add the JitPack repository:

```groovy
// settings.gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

Then add the dependency:

```groovy
// app/build.gradle
dependencies {
    implementation 'com.github.Ali0092:Sketchy:1.0.1'
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

### An illustration, restyled to match your theme

`colors` is a plain `SketchyColors` parameter — override any subset of it and
every stroke, fill, and sparkle in the scene repaints to match, no XML themes
or design-system lock-in required:

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
        accentSecondary = MaterialTheme.colorScheme.secondary,
    )
)
```

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
│       ├── SketchyColors.kt           # Shared reskinnable palette (both catalogs)
│       ├── illustrations/
│       │   ├── SketchyIllustrations.kt  # Sketch enum, SketchyIllustration composable
│       │   └── Onboarding*.kt           # …grouped by category, 2-5 scenes each
│       ├── emptystates/
│       │   ├── EmptyState.kt            # EmptyState enum, SketchyEmptyState composable
│       │   └── EmptyStates*.kt          # …grouped by category, 5 scenes each
│       └── utils/
│           ├── Extensions.kt            # DrawScope drawing extensions (stroke, sketchLine, …)
│           └── Utils.kt                 # Ink/accent color constants, wave(), DesignSize
└── app/        # Demo app — searchable, categorized gallery of everything above
```

## Contributing

New illustrations are very welcome. A new scene is just an `internal`
`DrawScope` function plus one enum entry — no boilerplate beyond that:

1. Pick a category (or propose a new one) and add an entry to the `Sketch`
   or `EmptyState` enum with its display copy and category.
2. Write the scene as `internal fun DrawScope.drawYourScene(t: Float, colors: SketchyColors)`
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
