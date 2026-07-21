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
import com.sketchy.library.utils.DesignSize
import com.sketchy.library.utils.wave

/**
 * Signature sketched illustrations, hand-drawn as line-art on Canvas.
 * Warm off-white ink against the dark page backgrounds.
 *
 * Every scene receives a looping phase `t` (0..1 over ~4s) that drives gentle
 * ambient motion — pulsing sparkles, ringing bells, a sweeping stopwatch needle —
 * on top of a soft entrance fade/scale and a slow whole-canvas float.
 *
 * The scenes themselves live one file per category (see `Onboarding*.kt`);
 * shared ink colors and Canvas drawing primitives live in `DrawingPrimitives.kt`.
 */

/** Every sketch currently available in the library, grouped by [category]. */
enum class Sketch(val displayName: String, val category: String) {
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
 */
@Composable
fun SketchyIllustration(
    modifier: Modifier = Modifier.size(DesignSize),
    sketch: Sketch,
    animate: Boolean = true,
    colors: SketchyColors = SketchyColors(),
) {
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
            drawIllustration(sketch, t, colors)
        }
    }
}

private fun DrawScope.drawIllustration(sketch: Sketch, t: Float, colors: SketchyColors) {
    when (sketch) {
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
