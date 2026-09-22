package com.sketchy.library.icons

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.sketchy.library.utils.Ink

/**
 * The fixed 24×24 design grid every icon is drawn against — the same convention most icon sets
 * use, so hand-drawn Sketchy icons still land at familiar, recognizable proportions. The caller
 * scales this uniformly to fit, exactly like [com.sketchy.library.utils.DesignSize] does for the
 * illustrations and empty states.
 */
internal val IconDesignSize = 24.dp

/**
 * The rendering families every icon supports — the same hand-drawn geometry throughout; only the
 * corner treatment and fill/stroke handling changes between them:
 *
 * - [Default]: a hollow line at normal weight — the icon's regular, everyday look.
 * - [Outlined]: the same line, drawn thinner.
 * - [Sharp]: the same as [Default], but with square corners and mitred joins instead of rounded ones.
 * - [Filled]: a solid silhouette, with any real internal detail (a gear's hub, a lock's keyhole, a
 *   clock's hands) cut out as a true gap rather than redrawn on top, since it would otherwise
 *   vanish against a same-colour fill.
 * - [TwoTone]: [Filled]'s silhouette at low opacity, with the full-strength [Default] line traced
 *   on top for detail.
 */
enum class IconStyle { Default, Outlined, Sharp, Filled, TwoTone }

/**
 * Every hand-drawn icon currently available in the library, grouped by [category].
 *
 * Unlike [com.sketchy.library.illustrations.Sketch] and [com.sketchy.library.emptystates.EmptyState],
 * icons are monochrome — a single [Color] tint rather than the full [com.sketchy.library.SketchyColors]
 * palette — matching how icon fonts and icon sets actually get used in an app: dropped inline into a
 * button, a list row, or a nav bar, coloured to match whatever text sits beside them.
 */
enum class Icon(val displayName: String, val category: String) {
    Home("Home", "General"),
    Search("Search", "General"),
    Heart("Heart", "General"),
    Star("Star", "General"),
    Bell("Bell", "General"),
    Settings("Settings", "General"),
    User("User", "General"),
    Mail("Mail", "General"),
    Calendar("Calendar", "General"),
    Clock("Clock", "General"),
    Cart("Cart", "General"),
    Edit("Edit", "General"),
    Trash("Trash", "General"),
    Camera("Camera", "General"),
    Image("Image", "General"),
    Folder("Folder", "General"),
    Download("Download", "General"),
    Upload("Upload", "General"),
    Share("Share", "General"),
    Lock("Lock", "General"),
}

/**
 * Renders a single [icon] in the given [style] and [tint].
 *
 * Icons are static by design — no looping ambient motion — since, unlike the illustrations and
 * empty states, they're meant to sit inline in real UI rather than as decoration. A soft one-shot
 * entrance fade/pop still plays on first composition, matching the rest of the library's feel; set
 * [animate] to false to skip it and render at full opacity immediately.
 *
 * [strokeWidth] is a design-space number on the same 24-unit grid the icon itself is drawn on — 1
 * by default, reads as a true 1dp line when the icon renders at its native 24dp size, and scales
 * along with everything else if you display it larger. [IconStyle.Outlined] always draws thinner
 * than this (70% of it) rather than needing a second knob.
 */
@Composable
fun SketchyIcon(
    icon: Icon,
    modifier: Modifier = Modifier.size(IconDesignSize),
    style: IconStyle = IconStyle.Default,
    tint: Color = Ink,
    strokeWidth: Float = 1f,
    animate: Boolean = true,
) {
    val appear = remember { Animatable(if (animate) 0f else 1f) }
    LaunchedEffect(icon, animate) {
        if (animate) {
            appear.snapTo(0f)
            appear.animateTo(1f, tween(280, easing = FastOutSlowInEasing))
        } else {
            appear.snapTo(1f)
        }
    }

    Canvas(
        modifier = modifier.graphicsLayer {
            alpha = appear.value
            val entrance = 0.85f + 0.15f * appear.value
            scaleX = entrance
            scaleY = entrance
        }
    ) {
        val fit = minOf(size.width, size.height) / IconDesignSize.toPx()
        withTransform({ scale(scaleX = fit, scaleY = fit, pivot = Offset.Zero) }) {
            drawIcon(icon, style, tint, strokeWidth)
        }
    }
}

private fun DrawScope.drawIcon(icon: Icon, style: IconStyle, tint: Color, strokeWidth: Float) {
    when (icon) {
        Icon.Home -> drawHomeIcon(style, tint, strokeWidth)
        Icon.Search -> drawSearchIcon(style, tint, strokeWidth)
        Icon.Heart -> drawHeartIcon(style, tint, strokeWidth)
        Icon.Star -> drawStarIcon(style, tint, strokeWidth)
        Icon.Bell -> drawBellIcon(style, tint, strokeWidth)
        Icon.Settings -> drawSettingsIcon(style, tint, strokeWidth)
        Icon.User -> drawUserIcon(style, tint, strokeWidth)
        Icon.Mail -> drawMailIcon(style, tint, strokeWidth)
        Icon.Calendar -> drawCalendarIcon(style, tint, strokeWidth)
        Icon.Clock -> drawClockIcon(style, tint, strokeWidth)
        Icon.Cart -> drawCartIcon(style, tint, strokeWidth)
        Icon.Edit -> drawEditIcon(style, tint, strokeWidth)
        Icon.Trash -> drawTrashIcon(style, tint, strokeWidth)
        Icon.Camera -> drawCameraIcon(style, tint, strokeWidth)
        Icon.Image -> drawImageIcon(style, tint, strokeWidth)
        Icon.Folder -> drawFolderIcon(style, tint, strokeWidth)
        Icon.Download -> drawDownloadIcon(style, tint, strokeWidth)
        Icon.Upload -> drawUploadIcon(style, tint, strokeWidth)
        Icon.Share -> drawShareIcon(style, tint, strokeWidth)
        Icon.Lock -> drawLockIcon(style, tint, strokeWidth)
    }
}
