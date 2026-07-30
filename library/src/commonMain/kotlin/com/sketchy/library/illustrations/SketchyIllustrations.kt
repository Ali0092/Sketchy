package com.sketchy.library.illustrations

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.sketchy.library.SketchyColors
import com.sketchy.library.SketchyStyle
import com.sketchy.library.utils.DesignSize
import com.sketchy.library.utils.wave

/**
 * Signature sketched illustrations, hand-drawn on Canvas.
 *
 * Every scene is a line drawing by default: ink outlines on a transparent
 * canvas, nothing filled in, with a few small accent marks for warmth. Ask for
 * `colorful = true` and the very same scene is painted in full instead —
 * gradient-shaded surfaces, cast shadows and specular highlights under the same
 * outlines — still with no background of its own. See [SketchyStyle] for how
 * one scene renders both ways.
 *
 * Every scene receives a looping phase `t` (0..1 over ~4s) that drives gentle
 * ambient motion — pulsing sparkles, ringing bells, a sweeping stopwatch needle —
 * on top of a soft entrance fade/scale and a slow whole-canvas float.
 *
 * The scenes live one file per category (see `Onboarding*.kt`), drawn with the
 * stroke primitives in `utils/Extensions.kt`. The **Featured** scenes are the
 * elaborate ones — a scene rather than a motif, one file each (`Featured*.kt`),
 * built from the shading primitives in `utils/Painting.kt`.
 */

/**
 * Every sketch currently available in the library, grouped by [category].
 *
 * [style] is a second, independent tag defaulted to `"Classic"` for every scene that predates
 * theming — a cross-cutting theme (e.g. Panda) sets [category] to the theme's name (so it groups
 * and searches exactly like any other category) and [style] to the shared rendering style it was
 * drawn in (e.g. "Cartoony"), so searching the *style* surfaces every theme that shares it. See
 * the `sketchy-illustrations` skill's `references/theming.md` before adding a new theme.
 */
enum class Sketch(val displayName: String, val category: String, val style: String = "Classic") {
    // ── Panda (Cartoony) · ten original daily-life moments ──────────────
    PandaMorningBamboo("A Panda's Morning Bamboo", "Panda", "Cartoony"),
    PandaParkWalk("A Panda's Park Walk", "Panda", "Cartoony"),
    PandaNapTime("A Panda's Nap Time", "Panda", "Cartoony"),
    PandaRainyDay("A Panda's Rainy Errand", "Panda", "Cartoony"),
    PandaBathTime("A Panda's Bath Time", "Panda", "Cartoony"),
    PandaReadingLantern("An Evening With a Good Book", "Panda", "Cartoony"),
    PandaBaking("Baking Bamboo Cookies", "Panda", "Cartoony"),
    PandaBikeRide("An Afternoon Bike Ride", "Panda", "Cartoony"),
    PandaStargazing("Stargazing on the Blanket", "Panda", "Cartoony"),
    PandaGarden("Tending the Garden", "Panda", "Cartoony"),

    // ── Featured · the elaborate, full-scene drawings ───────────────────
    MorningCoffee("A Slow Morning Coffee", "Featured"),
    HomeWorkspace("Your Workspace at Home", "Featured"),
    GroceryRun("The Weekly Grocery Run", "Featured"),
    ReadingNook("A Quiet Reading Corner", "Featured"),
    RainyWindow("Rainy Day Indoors", "Featured"),

    // ── Productivity ────────────────────────────────────────────────────
    PlanTasks("Plan Every Task", "Productivity"),
    FindFocus("Find Your Focus", "Productivity"),
    NeverMissMeeting("Never Miss a Meeting", "Productivity"),
    CaptureThoughts("Capture Every Thought", "Productivity"),
    BuildBetterHabits("Build Better Habits", "Productivity"),

    // ── Finance & banking ──────────────────────────────────────────────
    TrackSpending("Track Every Expense", "Finance"),
    GrowSavings("Watch Your Savings Grow", "Finance"),

    // ── Fitness & workouts ─────────────────────────────────────────────
    TrainAnywhere("Train Anywhere, Anytime", "Fitness"),
    TrackProgress("See Your Progress", "Fitness"),

    // ── Food delivery ───────────────────────────────────────────────────
    OrderFavorites("Order Your Favorites", "Food Delivery"),
    FastDelivery("Fast, Fresh Delivery", "Food Delivery"),

    // ── Travel ───────────────────────────────────────────────────────────
    PlanTrip("Plan Your Perfect Trip", "Travel"),
    ExploreWorld("Explore The World", "Travel"),

    // ── Music & streaming ───────────────────────────────────────────────
    ListenAnywhere("Your Soundtrack, Anywhere", "Music"),
    DiscoverMusic("Discover New Sounds", "Music"),
}

/**
 * Renders a single [Sketch]. Set [animate] to false to freeze the scene at
 * its resting frame instead of looping its ambient motion.
 *
 * The scene is hand-drawn against a 320dp design canvas and scales uniformly
 * to fit whatever size [modifier] gives it, so it works equally well as a
 * small gallery thumbnail or a full-bleed illustration. [colors] restyles the
 * ink and accent colors to fit your own design system.
 *
 * The scene is a hand-drawn outline by default — ink lines and a few small
 * accent marks, nothing filled in. Set [colorful] to true to have the same
 * scene painted in full colour instead; either way it draws on a transparent
 * canvas, so it sits on whatever background your screen already has.
 */
@Composable
fun SketchyIllustration(
    modifier: Modifier = Modifier.size(DesignSize),
    sketch: Sketch,
    animate: Boolean = true,
    colorful: Boolean = false,
    colors: SketchyColors = SketchyColors(),
) {
    val style = remember(colors, colorful) { SketchyStyle(colors, outlined = !colorful) }

    // Looping phase driving all ambient motion inside the scenes.
    val t: Float = if (animate) {
        val transition = rememberInfiniteTransition(label = "sketchy_art")
        val phase by transition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(tween(4000, easing = LinearEasing)),
            label = "phase"
        )
        phase
    } else {
        0f
    }
    // Soft entrance when the page first composes.
    val appear = remember { Animatable(0f) }
    LaunchedEffect(Unit) { appear.animateTo(1f, tween(700, easing = FastOutSlowInEasing)) }

    Canvas(
        modifier = modifier
            .graphicsLayer {
                alpha = appear.value
                val entrance = 0.94f + 0.06f * appear.value
                scaleX = entrance
                scaleY = entrance
                // slow breathing float of the whole artwork
                translationY = wave(t) * 3.dp.toPx()
            }
    ) {
        // Scale the 320dp design canvas uniformly to fit whatever size we were given.
        val fit = minOf(size.width, size.height) / DesignSize.toPx()
        withTransform({ scale(scaleX = fit, scaleY = fit, pivot = Offset.Zero) }) {
            drawIllustration(sketch, t, style)
        }
    }
}

private fun DrawScope.drawIllustration(sketch: Sketch, t: Float, colors: SketchyStyle) {
    when (sketch) {
        Sketch.PandaMorningBamboo -> drawPandaMorningBambooScene(t, colors)
        Sketch.PandaParkWalk -> drawPandaParkWalkScene(t, colors)
        Sketch.PandaNapTime -> drawPandaNapTimeScene(t, colors)
        Sketch.PandaRainyDay -> drawPandaRainyDayScene(t, colors)
        Sketch.PandaBathTime -> drawPandaBathTimeScene(t, colors)
        Sketch.PandaReadingLantern -> drawPandaReadingLanternScene(t, colors)
        Sketch.PandaBaking -> drawPandaBakingScene(t, colors)
        Sketch.PandaBikeRide -> drawPandaBikeRideScene(t, colors)
        Sketch.PandaStargazing -> drawPandaStargazingScene(t, colors)
        Sketch.PandaGarden -> drawPandaGardenScene(t, colors)

        Sketch.MorningCoffee -> drawMorningCoffeeScene(t, colors)
        Sketch.HomeWorkspace -> drawHomeWorkspaceScene(t, colors)
        Sketch.GroceryRun -> drawGroceryRunScene(t, colors)
        Sketch.ReadingNook -> drawReadingNookScene(t, colors)
        Sketch.RainyWindow -> drawRainyWindowScene(t, colors)

        Sketch.PlanTasks -> drawTasksScene(t, colors)
        Sketch.FindFocus -> drawFocusScene(t, colors)
        Sketch.NeverMissMeeting -> drawMeetingsScene(t, colors)
        Sketch.CaptureThoughts -> drawNotesScene(t, colors)
        Sketch.BuildBetterHabits -> drawHabitsScene(t, colors)

        Sketch.TrackSpending -> drawTrackSpendingScene(t, colors)
        Sketch.GrowSavings -> drawGrowSavingsScene(t, colors)

        Sketch.TrainAnywhere -> drawTrainAnywhereScene(t, colors)
        Sketch.TrackProgress -> drawTrackProgressScene(t, colors)

        Sketch.OrderFavorites -> drawOrderFavoritesScene(t, colors)
        Sketch.FastDelivery -> drawFastDeliveryScene(t, colors)

        Sketch.PlanTrip -> drawPlanTripScene(t, colors)
        Sketch.ExploreWorld -> drawExploreWorldScene(t, colors)

        Sketch.ListenAnywhere -> drawListenAnywhereScene(t, colors)
        Sketch.DiscoverMusic -> drawDiscoverMusicScene(t, colors)
    }
}
