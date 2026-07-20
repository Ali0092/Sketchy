package com.sketchy.library

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A fully reskinnable color palette for a sketch. Override any part to fit
 * your app's own design system without touching any drawing code.
 */
data class SketchyColors(
    val ink: Color = Color(0xFF0D1B2A),
    val inkSoft: Color = Color(0x990D1B2A),
    val inkFaint: Color = Color(0x330D1B2A),
    val accent: Color = Color(0xFFFFBC00),
    val accentSecondary: Color = Color(0xFF008091),
)

/**
 * Every empty state currently available in the library, with sensible
 * default copy that [SketchyEmptyState] uses unless you override it.
 */
enum class EmptyState(val defaultTitle: String, val defaultSubtitle: String) {
    // ── Connectivity & errors ──────────────────────────────────────────
    NoInternet(
        "No Internet Connection",
        "Please check your network settings and try again."
    ),
    ServerError(
        "Something Went Wrong",
        "Our servers are having trouble. Please try again later."
    ),
    SyncFailed(
        "Sync Failed",
        "We couldn't sync your latest changes. Try again."
    ),
    UnderMaintenance(
        "Under Maintenance",
        "We're making some improvements. Check back soon."
    ),
    LocationNotFound(
        "Location Not Found",
        "We couldn't find that place. Try another search."
    ),

    // ── Content & search ───────────────────────────────────────────────
    NoResults(
        "No Results Found",
        "Try adjusting your search or filters."
    ),
    NoData(
        "No Data Available",
        "There's nothing to display right now."
    ),
    NoComments(
        "No Comments Yet",
        "Be the first to share your thoughts."
    ),
    NoMessages(
        "No Messages Yet",
        "Start a conversation to see it here."
    ),
    PageNotFound(
        "Page Not Found",
        "The page you're looking for doesn't exist."
    ),

    // ── Saved & commerce ───────────────────────────────────────────────
    EmptyCart(
        "Your Cart is Empty",
        "Looks like you haven't added anything yet."
    ),
    EmptyWishlist(
        "Your Wishlist is Empty",
        "Tap the star on items you wish for."
    ),
    NoFavorites(
        "No Favorites Yet",
        "Tap the heart icon to save items you love."
    ),
    NoBookmarks(
        "No Bookmarks",
        "Save items to find them here later."
    ),
    NoDownloads(
        "No Downloads",
        "Files you download will show up here."
    ),

    // ── Everyday & productivity ────────────────────────────────────────
    EmptyInbox(
        "Your Inbox is Empty",
        "New messages will show up here."
    ),
    NoNotifications(
        "All Caught Up",
        "You have no new notifications."
    ),
    EmptyCalendar(
        "Nothing Scheduled",
        "Your calendar is wide open."
    ),
    NoPhotos(
        "No Photos Yet",
        "Photos you add will appear here."
    ),
    AllDone(
        "All Done!",
        "You've completed everything on your list."
    ),
}

/**
 * Renders a single [EmptyState] with an optional title and subtitle beneath
 * it — a complete, drop-in empty-state view.
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
                drawEmptyState(state, t, colors)
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

/** A faint dashed baseline shared by every empty-state icon for visual grounding. */
internal fun DrawScope.groundLine(y: Float, color: Color) {
    val path = Path().apply {
        moveTo(d(60f), d(y))
        lineTo(d(260f), d(y))
    }
    drawPath(path = path, color = color, style = dashed())
}

private fun DrawScope.drawEmptyState(state: EmptyState, t: Float, colors: SketchyColors) {
    when (state) {
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
