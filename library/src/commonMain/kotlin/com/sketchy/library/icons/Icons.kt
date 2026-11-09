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
    ArrowUp("Arrow Up", "Arrows"),
    ArrowDown("Arrow Down", "Arrows"),
    ArrowLeft("Arrow Left", "Arrows"),
    ArrowRight("Arrow Right", "Arrows"),
    ArrowUpRight("Arrow Up Right", "Arrows"),
    ChevronUp("Chevron Up", "Arrows"),
    ChevronDown("Chevron Down", "Arrows"),
    ChevronLeft("Chevron Left", "Arrows"),
    ChevronRight("Chevron Right", "Arrows"),
    ArrowBigLeft("Big Arrow Left", "Arrows"),
    ArrowBigRight("Big Arrow Right", "Arrows"),
    Undo("Undo", "Arrows"),
    Redo("Redo", "Arrows"),
    Refresh("Refresh", "Arrows"),
    Swap("Swap", "Arrows"),
    Sort("Sort", "Arrows"),
    Expand("Expand", "Arrows"),
    Collapse("Collapse", "Arrows"),
    Plus("Plus", "Actions"),
    Minus("Minus", "Actions"),
    Close("Close", "Actions"),
    Check("Check", "Actions"),
    Menu("Menu", "Actions"),
    More("More", "Actions"),
    Eye("Eye", "Actions"),
    EyeOff("Eye Off", "Actions"),
    Filter("Filter", "Actions"),
    Bookmark("Bookmark", "Actions"),
    Link("Link", "Actions"),
    Pin("Pin", "Actions"),
    Flag("Flag", "Actions"),
    Info("Info", "Status"),
    Warning("Warning", "Status"),
    Help("Help", "Status"),
    Phone("Phone", "Communication"),
    Chat("Chat", "Communication"),
    Send("Send", "Communication"),
    Play("Play", "Media"),
    Pause("Pause", "Media"),
    Mic("Mic", "Media"),
    Volume("Volume", "Media"),
    Sun("Sun", "Weather"),
    Moon("Moon", "Weather"),
    Cloud("Cloud", "Weather"),
    Gift("Gift", "Objects"),
    Lightbulb("Lightbulb", "Objects"),
    Coffee("Coffee", "Objects"),
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
        Icon.ArrowUp -> drawArrowUpIcon(style, tint, strokeWidth)
        Icon.ArrowDown -> drawArrowDownIcon(style, tint, strokeWidth)
        Icon.ArrowLeft -> drawArrowLeftIcon(style, tint, strokeWidth)
        Icon.ArrowRight -> drawArrowRightIcon(style, tint, strokeWidth)
        Icon.ArrowUpRight -> drawArrowUpRightIcon(style, tint, strokeWidth)
        Icon.ChevronUp -> drawChevronUpIcon(style, tint, strokeWidth)
        Icon.ChevronDown -> drawChevronDownIcon(style, tint, strokeWidth)
        Icon.ChevronLeft -> drawChevronLeftIcon(style, tint, strokeWidth)
        Icon.ChevronRight -> drawChevronRightIcon(style, tint, strokeWidth)
        Icon.ArrowBigLeft -> drawArrowBigLeftIcon(style, tint, strokeWidth)
        Icon.ArrowBigRight -> drawArrowBigRightIcon(style, tint, strokeWidth)
        Icon.Undo -> drawUndoIcon(style, tint, strokeWidth)
        Icon.Redo -> drawRedoIcon(style, tint, strokeWidth)
        Icon.Refresh -> drawRefreshIcon(style, tint, strokeWidth)
        Icon.Swap -> drawSwapIcon(style, tint, strokeWidth)
        Icon.Sort -> drawSortIcon(style, tint, strokeWidth)
        Icon.Expand -> drawExpandIcon(style, tint, strokeWidth)
        Icon.Collapse -> drawCollapseIcon(style, tint, strokeWidth)
        Icon.Plus -> drawPlusIcon(style, tint, strokeWidth)
        Icon.Minus -> drawMinusIcon(style, tint, strokeWidth)
        Icon.Close -> drawCloseIcon(style, tint, strokeWidth)
        Icon.Check -> drawCheckIcon(style, tint, strokeWidth)
        Icon.Menu -> drawMenuIcon(style, tint, strokeWidth)
        Icon.More -> drawMoreIcon(style, tint, strokeWidth)
        Icon.Eye -> drawEyeIcon(style, tint, strokeWidth)
        Icon.EyeOff -> drawEyeOffIcon(style, tint, strokeWidth)
        Icon.Filter -> drawFilterIcon(style, tint, strokeWidth)
        Icon.Bookmark -> drawBookmarkIcon(style, tint, strokeWidth)
        Icon.Link -> drawLinkIcon(style, tint, strokeWidth)
        Icon.Pin -> drawPinIcon(style, tint, strokeWidth)
        Icon.Flag -> drawFlagIcon(style, tint, strokeWidth)
        Icon.Info -> drawInfoIcon(style, tint, strokeWidth)
        Icon.Warning -> drawWarningIcon(style, tint, strokeWidth)
        Icon.Help -> drawHelpIcon(style, tint, strokeWidth)
        Icon.Phone -> drawPhoneIcon(style, tint, strokeWidth)
        Icon.Chat -> drawChatIcon(style, tint, strokeWidth)
        Icon.Send -> drawSendIcon(style, tint, strokeWidth)
        Icon.Play -> drawPlayIcon(style, tint, strokeWidth)
        Icon.Pause -> drawPauseIcon(style, tint, strokeWidth)
        Icon.Mic -> drawMicIcon(style, tint, strokeWidth)
        Icon.Volume -> drawVolumeIcon(style, tint, strokeWidth)
        Icon.Sun -> drawSunIcon(style, tint, strokeWidth)
        Icon.Moon -> drawMoonIcon(style, tint, strokeWidth)
        Icon.Cloud -> drawCloudIcon(style, tint, strokeWidth)
        Icon.Gift -> drawGiftIcon(style, tint, strokeWidth)
        Icon.Lightbulb -> drawLightbulbIcon(style, tint, strokeWidth)
        Icon.Coffee -> drawCoffeeIcon(style, tint, strokeWidth)
    }
}
