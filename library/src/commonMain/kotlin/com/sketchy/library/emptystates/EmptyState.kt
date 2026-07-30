package com.sketchy.library.emptystates

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sketchy.library.SketchyColors
import com.sketchy.library.SketchyStyle
import com.sketchy.library.utils.DesignSize
import com.sketchy.library.utils.wave

/**
 * Every empty state currently available in the library, with sensible
 * default copy that [SketchyEmptyState] uses unless you override it, grouped
 * by [category].
 *
 * [style] is a second, independent tag defaulted to `"Classic"` for every scene that predates
 * theming — a cross-cutting theme (e.g. Panda) sets [category] to the theme's name (so it groups
 * and searches exactly like any other category) and [style] to the shared rendering style it was
 * drawn in (e.g. "Cartoony"), so searching the *style* surfaces every theme that shares it. See
 * the `sketchy-illustrations` skill's `references/theming.md` before adding a new theme.
 */
enum class EmptyState(
    val defaultTitle: String,
    val defaultSubtitle: String,
    val category: String,
    val style: String = "Classic",
) {
    // ── Panda (Cartoony) — a complete themed set, one per use case below ──
    PandaNoInternet(
        "No Signal, No Bamboo",
        "Even pandas lose Wi-Fi sometimes. Try again in a bit.",
        "Panda", "Cartoony"
    ),
    PandaServerError(
        "Our Servers Tripped on a Root",
        "Give them a moment to get back on their paws.",
        "Panda", "Cartoony"
    ),
    PandaSyncFailed(
        "Sync Didn't Take",
        "That crank needs one more turn — try syncing again.",
        "Panda", "Cartoony"
    ),
    PandaUnderMaintenance(
        "Bamboo Under Repair",
        "We're tightening a few bolts. Back shortly.",
        "Panda", "Cartoony"
    ),
    PandaLocationNotFound(
        "Lost the Trail",
        "We couldn't find that place. Try another search.",
        "Panda", "Cartoony"
    ),
    PandaNoResults(
        "Nothing Turned Up",
        "Try adjusting your search or filters.",
        "Panda", "Cartoony"
    ),
    PandaNoData(
        "Nothing to Chart Yet",
        "There's nothing to display right now.",
        "Panda", "Cartoony"
    ),
    PandaNoComments(
        "No Comments Yet",
        "Be the first to leave a note.",
        "Panda", "Cartoony"
    ),
    PandaNoMessages(
        "Quiet as a Bamboo Grove",
        "Start a conversation to see it here.",
        "Panda", "Cartoony"
    ),
    PandaPageNotFound(
        "Wrong Trail",
        "The page you're looking for doesn't exist.",
        "Panda", "Cartoony"
    ),
    PandaEmptyCart(
        "Cart's Still Empty",
        "Looks like you haven't added anything yet.",
        "Panda", "Cartoony"
    ),
    PandaEmptyWishlist(
        "Wish Tag's Blank",
        "Tap the star on items you wish for.",
        "Panda", "Cartoony"
    ),
    PandaNoFavorites(
        "Nothing Hugged Yet",
        "Tap the heart on items you love.",
        "Panda", "Cartoony"
    ),
    PandaNoBookmarks(
        "Ribbon's Still Loose",
        "Save items to find them here later.",
        "Panda", "Cartoony"
    ),
    PandaNoDownloads(
        "Nothing's Landed Yet",
        "Files you download will show up here.",
        "Panda", "Cartoony"
    ),
    PandaEmptyInbox(
        "Inbox Is Peaceful",
        "New messages will show up here.",
        "Panda", "Cartoony"
    ),
    PandaNoNotifications(
        "All Caught Up",
        "Not a peep — you're all caught up.",
        "Panda", "Cartoony"
    ),
    PandaEmptyCalendar(
        "Wide Open Day",
        "Your calendar is wide open.",
        "Panda", "Cartoony"
    ),
    PandaNoPhotos(
        "No Photos Yet",
        "Photos you add will appear here.",
        "Panda", "Cartoony"
    ),
    PandaAllDone(
        "All Done!",
        "You've finished everything on your list — well earned.",
        "Panda", "Cartoony"
    ),

    // ── Connectivity & errors ──────────────────────────────────────────
    NoInternet(
        "No Internet Connection",
        "Please check your network settings and try again.",
        "Connectivity & Errors"
    ),
    ServerError(
        "Something Went Wrong",
        "Our servers are having trouble. Please try again later.",
        "Connectivity & Errors"
    ),
    SyncFailed(
        "Sync Failed",
        "We couldn't sync your latest changes. Try again.",
        "Connectivity & Errors"
    ),
    UnderMaintenance(
        "Under Maintenance",
        "We're making some improvements. Check back soon.",
        "Connectivity & Errors"
    ),
    LocationNotFound(
        "Location Not Found",
        "We couldn't find that place. Try another search.",
        "Connectivity & Errors"
    ),

    // ── Content & search ───────────────────────────────────────────────
    NoResults(
        "No Results Found",
        "Try adjusting your search or filters.",
        "Content & Search"
    ),
    NoData(
        "No Data Available",
        "There's nothing to display right now.",
        "Content & Search"
    ),
    NoComments(
        "No Comments Yet",
        "Be the first to share your thoughts.",
        "Content & Search"
    ),
    NoMessages(
        "No Messages Yet",
        "Start a conversation to see it here.",
        "Content & Search"
    ),
    PageNotFound(
        "Page Not Found",
        "The page you're looking for doesn't exist.",
        "Content & Search"
    ),

    // ── Saved & commerce ───────────────────────────────────────────────
    EmptyCart(
        "Your Cart is Empty",
        "Looks like you haven't added anything yet.",
        "Saved & Commerce"
    ),
    EmptyWishlist(
        "Your Wishlist is Empty",
        "Tap the star on items you wish for.",
        "Saved & Commerce"
    ),
    NoFavorites(
        "No Favorites Yet",
        "Tap the heart icon to save items you love.",
        "Saved & Commerce"
    ),
    NoBookmarks(
        "No Bookmarks",
        "Save items to find them here later.",
        "Saved & Commerce"
    ),
    NoDownloads(
        "No Downloads",
        "Files you download will show up here.",
        "Saved & Commerce"
    ),

    // ── Everyday & productivity ────────────────────────────────────────
    EmptyInbox(
        "Your Inbox is Empty",
        "New messages will show up here.",
        "Everyday & Productivity"
    ),
    NoNotifications(
        "All Caught Up",
        "You have no new notifications.",
        "Everyday & Productivity"
    ),
    EmptyCalendar(
        "Nothing Scheduled",
        "Your calendar is wide open.",
        "Everyday & Productivity"
    ),
    NoPhotos(
        "No Photos Yet",
        "Photos you add will appear here.",
        "Everyday & Productivity"
    ),
    AllDone(
        "All Done!",
        "You've completed everything on your list.",
        "Everyday & Productivity"
    ),
}

/**
 * Renders a single [EmptyState] with an optional title and subtitle beneath
 * it — a complete, drop-in empty-state view.
 *
 * The scene is a hand-drawn outline by default — ink lines and a few small
 * accent marks on a transparent canvas — so it sits inside a screen without
 * competing with it. Set [colorful] to true to have the same scene painted in
 * full colour instead.
 *
 * Every visual aspect is generic and overridable: [colors] restyles the ink
 * and accent colors, [illustrationSize] controls how big the artwork is,
 * and [title]/[subtitle] plus [titleStyle]/[subtitleStyle] let you supply
 * your own copy and typography (or pass `title = null` for icon-only use).
 * Set [animate] to false to freeze the scene at its resting frame.
 */
@Composable
fun SketchyEmptyState(
    state: EmptyState,
    modifier: Modifier = Modifier,
    animate: Boolean = true,
    colorful: Boolean = false,
    colors: SketchyColors = SketchyColors(),
    illustrationSize: Dp = 220.dp,
    title: String? = state.defaultTitle,
    subtitle: String? = state.defaultSubtitle,
    titleStyle: TextStyle = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        color = colors.ink,
        textAlign = TextAlign.Center,
    ),
    subtitleStyle: TextStyle = TextStyle(
        fontSize = 14.sp,
        color = colors.inkSoft,
        textAlign = TextAlign.Center,
    ),
    spacing: Dp = 16.dp,
) {
    val style = remember(colors, colorful) { SketchyStyle(colors, outlined = !colorful) }
    val t: Float = if (animate) {
        val transition = rememberInfiniteTransition(label = "sketchy_empty_state")
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
    val appear = remember { Animatable(0f) }
    LaunchedEffect(Unit) { appear.animateTo(1f, tween(700, easing = FastOutSlowInEasing)) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing)
    ) {
        Canvas(
            modifier = Modifier
                .size(illustrationSize)
                .graphicsLayer {
                    alpha = appear.value
                    val entrance = 0.94f + 0.06f * appear.value
                    scaleX = entrance
                    scaleY = entrance
                    translationY = wave(t) * 3.dp.toPx()
                }
        ) {
            val fit = minOf(size.width, size.height) / DesignSize.toPx()
            withTransform({ scale(scaleX = fit, scaleY = fit, pivot = Offset.Zero) }) {
                drawEmptyState(state, t, style)
            }
        }
        if (title != null) {
            BasicText(text = title, style = titleStyle)
        }
        if (subtitle != null) {
            BasicText(text = subtitle, style = subtitleStyle)
        }
    }
}

private fun DrawScope.drawEmptyState(state: EmptyState, t: Float, colors: SketchyStyle) {
    when (state) {
        EmptyState.PandaNoInternet -> drawPandaNoInternet(t, colors)
        EmptyState.PandaServerError -> drawPandaServerError(t, colors)
        EmptyState.PandaSyncFailed -> drawPandaSyncFailed(t, colors)
        EmptyState.PandaUnderMaintenance -> drawPandaUnderMaintenance(t, colors)
        EmptyState.PandaLocationNotFound -> drawPandaLocationNotFound(t, colors)
        EmptyState.PandaNoResults -> drawPandaNoResults(t, colors)
        EmptyState.PandaNoData -> drawPandaNoData(t, colors)
        EmptyState.PandaNoComments -> drawPandaNoComments(t, colors)
        EmptyState.PandaNoMessages -> drawPandaNoMessages(t, colors)
        EmptyState.PandaPageNotFound -> drawPandaPageNotFound(t, colors)
        EmptyState.PandaEmptyCart -> drawPandaEmptyCart(t, colors)
        EmptyState.PandaEmptyWishlist -> drawPandaEmptyWishlist(t, colors)
        EmptyState.PandaNoFavorites -> drawPandaNoFavorites(t, colors)
        EmptyState.PandaNoBookmarks -> drawPandaNoBookmarks(t, colors)
        EmptyState.PandaNoDownloads -> drawPandaNoDownloads(t, colors)
        EmptyState.PandaEmptyInbox -> drawPandaEmptyInbox(t, colors)
        EmptyState.PandaNoNotifications -> drawPandaNoNotifications(t, colors)
        EmptyState.PandaEmptyCalendar -> drawPandaEmptyCalendar(t, colors)
        EmptyState.PandaNoPhotos -> drawPandaNoPhotos(t, colors)
        EmptyState.PandaAllDone -> drawPandaAllDone(t, colors)

        EmptyState.NoInternet -> drawNoInternet(t, colors)
        EmptyState.ServerError -> drawServerError(t, colors)
        EmptyState.SyncFailed -> drawSyncFailed(t, colors)
        EmptyState.UnderMaintenance -> drawUnderMaintenance(t, colors)
        EmptyState.LocationNotFound -> drawLocationNotFound(t, colors)

        EmptyState.NoResults -> drawNoResults(t, colors)
        EmptyState.NoData -> drawNoData(t, colors)
        EmptyState.NoComments -> drawNoComments(t, colors)
        EmptyState.NoMessages -> drawNoMessages(t, colors)
        EmptyState.PageNotFound -> drawPageNotFound(t, colors)

        EmptyState.EmptyCart -> drawEmptyCart(t, colors)
        EmptyState.EmptyWishlist -> drawEmptyWishlist(t, colors)
        EmptyState.NoFavorites -> drawNoFavorites(t, colors)
        EmptyState.NoBookmarks -> drawNoBookmarks(t, colors)
        EmptyState.NoDownloads -> drawNoDownloads(t, colors)

        EmptyState.EmptyInbox -> drawEmptyInbox(t, colors)
        EmptyState.NoNotifications -> drawNoNotifications(t, colors)
        EmptyState.EmptyCalendar -> drawEmptyCalendar(t, colors)
        EmptyState.NoPhotos -> drawNoPhotos(t, colors)
        EmptyState.AllDone -> drawAllDone(t, colors)
    }
}
