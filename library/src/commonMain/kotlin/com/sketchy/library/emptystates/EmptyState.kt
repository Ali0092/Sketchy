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
import androidx.compose.ui.text.rememberTextMeasurer
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
 * theming — a cross-cutting theme sets [category] to the theme's name (so it groups and searches
 * exactly like any other category) and [style] to the shared rendering style it was drawn in
 * (e.g. "Cartoony"), so searching the *style* surfaces every theme that shares it. See the
 * `sketchy-illustrations` skill's `references/theming.md` before adding a new theme.
 */
enum class EmptyState(
    val defaultTitle: String,
    val defaultSubtitle: String,
    val category: String,
    val style: String = "Classic",
) {
    // ── Signboards — traffic & road signage: signals, warning/stop signs, cones, boards ──
    SignboardPageNotFound(
        "Page Not Found",
        "The page you're looking for doesn't exist.",
        "Signboards"
    ),
    SignboardNetworkError(
        "Network Error",
        "We couldn't reach the server. Check your connection.",
        "Signboards"
    ),
    SignboardNoData(
        "No Data Available",
        "There's nothing to show here yet.",
        "Signboards"
    ),
    SignboardUnauthorized(
        "Sign In Required",
        "You need to be signed in to view this.",
        "Signboards"
    ),
    SignboardForbidden(
        "Access Denied",
        "You don't have permission to view this page.",
        "Signboards"
    ),
    SignboardMaintenance(
        "Under Maintenance",
        "We're working on it — please check back soon.",
        "Signboards"
    ),
    SignboardAllClear(
        "All Clear",
        "Nothing new to signal right now.",
        "Signboards"
    ),
    SignboardNoWarnings(
        "No Warnings",
        "Nothing here needs your attention.",
        "Signboards"
    ),
    SignboardComingSoon(
        "Coming Soon",
        "This feature is on its way — check back soon.",
        "Signboards"
    ),
    SignboardNoPosts(
        "Nothing Posted Yet",
        "New posts will show up on this board.",
        "Signboards"
    ),
    SignboardEndOfRoad(
        "End of the Road",
        "You've reached the end — nothing more to load.",
        "Signboards"
    ),

    // ── Network — network hardware only: routers, servers, cables, no icons ──
    NetworkNoInternet(
        "No Internet Connection",
        "Check your router and try again.",
        "Network"
    ),
    NetworkPageNotFound(
        "Page Not Found",
        "The page you're looking for doesn't exist.",
        "Network"
    ),
    NetworkBadGateway(
        "Bad Gateway",
        "The connection between servers broke. Please try again.",
        "Network"
    ),
    NetworkServerError(
        "Something Went Wrong",
        "Our servers hit a snag. Please try again shortly.",
        "Network"
    ),
    NetworkUnsecureWifi(
        "Unsecured Connection",
        "This network isn't secure. Connect with caution.",
        "Network"
    ),
    NetworkNoData(
        "No Data Available",
        "There's nothing stored here yet.",
        "Network"
    ),
    NetworkNoList(
        "Nothing Connected",
        "Devices you connect will show up here.",
        "Network"
    ),
    NetworkNoMessages(
        "No Messages Yet",
        "Messages sent over the network will appear here.",
        "Network"
    ),
    NetworkNoComments(
        "No Comments Yet",
        "Be the first to join the conversation.",
        "Network"
    ),
    NetworkNoResults(
        "No Results Found",
        "The scan turned up nothing. Try again.",
        "Network"
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
    val textMeasurer = rememberTextMeasurer()
    val style = remember(colors, colorful, textMeasurer) {
        SketchyStyle(colors, outlined = !colorful, textMeasurer = textMeasurer)
    }
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
        EmptyState.SignboardPageNotFound -> drawSignboardPageNotFound(t, colors)
        EmptyState.SignboardNetworkError -> drawSignboardNetworkError(t, colors)
        EmptyState.SignboardNoData -> drawSignboardNoData(t, colors)
        EmptyState.SignboardUnauthorized -> drawSignboardUnauthorized(t, colors)
        EmptyState.SignboardForbidden -> drawSignboardForbidden(t, colors)
        EmptyState.SignboardMaintenance -> drawSignboardMaintenance(t, colors)
        EmptyState.SignboardAllClear -> drawSignboardAllClear(t, colors)
        EmptyState.SignboardNoWarnings -> drawSignboardNoWarnings(t, colors)
        EmptyState.SignboardComingSoon -> drawSignboardComingSoon(t, colors)
        EmptyState.SignboardNoPosts -> drawSignboardNoPosts(t, colors)
        EmptyState.SignboardEndOfRoad -> drawSignboardEndOfRoad(t, colors)

        EmptyState.NetworkNoInternet -> drawNetworkNoInternet(t, colors)
        EmptyState.NetworkPageNotFound -> drawNetworkPageNotFound(t, colors)
        EmptyState.NetworkBadGateway -> drawNetworkBadGateway(t, colors)
        EmptyState.NetworkServerError -> drawNetworkServerError(t, colors)
        EmptyState.NetworkUnsecureWifi -> drawNetworkUnsecureWifi(t, colors)
        EmptyState.NetworkNoData -> drawNetworkNoData(t, colors)
        EmptyState.NetworkNoList -> drawNetworkNoList(t, colors)
        EmptyState.NetworkNoMessages -> drawNetworkNoMessages(t, colors)
        EmptyState.NetworkNoComments -> drawNetworkNoComments(t, colors)
        EmptyState.NetworkNoResults -> drawNetworkNoResults(t, colors)

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
