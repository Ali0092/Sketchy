package com.sketchy.library.icons

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import com.sketchy.library.utils.d
import com.sketchy.library.utils.ellipsePath
import com.sketchy.library.utils.pt
import com.sketchy.library.utils.roundRectPath
import com.sketchy.library.utils.roundedPolygonPath

internal fun DrawScope.drawPlusIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    paintLineIcon(listOf(linePath(12f, 5f, 12f, 19f), linePath(5f, 12f, 19f, 12f)), style, tint, strokeWidth)

internal fun DrawScope.drawMinusIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    paintLineIcon(listOf(linePath(5f, 12f, 19f, 12f)), style, tint, strokeWidth)

internal fun DrawScope.drawCloseIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    paintLineIcon(listOf(linePath(6f, 6f, 18f, 18f), linePath(18f, 6f, 6f, 18f)), style, tint, strokeWidth)

internal fun DrawScope.drawCheckIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val tick = Path().apply {
        moveTo(d(4.5f), d(12.5f))
        lineTo(d(9.5f), d(17.5f))
        lineTo(d(19.5f), d(6.5f))
    }
    paintLineIcon(listOf(tick), style, tint, strokeWidth)
}

internal fun DrawScope.drawMenuIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    paintLineIcon(listOf(6f, 12f, 18f).map { y -> linePath(4f, y, 20f, y) }, style, tint, strokeWidth)

internal fun DrawScope.drawMoreIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    listOf(5f, 12f, 19f).forEach { x -> paintIcon(ellipsePath(x, 12f, 2f, 2f), style, tint, strokeWidth) }

internal fun DrawScope.drawFilterIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val funnel = roundedPolygonPath(
        listOf(3.5f to 4.5f, 20.5f to 4.5f, 14f to 12.5f, 14f to 19.5f, 10f to 17.5f, 10f to 12.5f),
        cornerRadius(style, 1.2f)
    )
    paintIcon(funnel, style, tint, strokeWidth)
}

internal fun DrawScope.drawLinkIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    rotate(-45f, pivot = pt(12f, 12f)) {
        val r = cornerRadius(style, 3f)
        paintLineIcon(
            listOf(roundRectPath(2.5f, 9f, 11f, 6f, r), roundRectPath(10.5f, 9f, 11f, 6f, r)),
            style,
            tint,
            strokeWidth
        )
    }

internal fun DrawScope.drawBookmarkIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val ribbon = roundedPolygonPath(
        listOf(6f to 3f, 18f to 3f, 18f to 21f, 12f to 16f, 6f to 21f),
        cornerRadius(style, 1.5f)
    )
    paintIcon(ribbon, style, tint, strokeWidth)
}

internal fun DrawScope.drawFlagIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val banner = Path().apply {
        moveTo(d(5f), d(4f))
        cubicTo(d(9f), d(2f), d(12f), d(6f), d(19f), d(4f))
        lineTo(d(19f), d(13f))
        cubicTo(d(12f), d(15f), d(9f), d(11f), d(5f), d(13f))
        close()
    }
    paintShapeIcon(banner, style, tint, strokeWidth, lines = listOf(linePath(5f, 3f, 5f, 21f)))
}

internal fun DrawScope.drawPinIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val drop = Path().apply {
        moveTo(d(12f), d(21.5f))
        cubicTo(d(7f), d(16f), d(4.5f), d(12.5f), d(4.5f), d(9.5f))
        cubicTo(d(4.5f), d(5.4f), d(7.9f), d(2.5f), d(12f), d(2.5f))
        cubicTo(d(16.1f), d(2.5f), d(19.5f), d(5.4f), d(19.5f), d(9.5f))
        cubicTo(d(19.5f), d(12.5f), d(17f), d(16f), d(12f), d(21.5f))
        close()
    }
    paintShapeIcon(drop, style, tint, strokeWidth, holes = listOf(ellipsePath(12f, 9.5f, 2.8f, 2.8f)))
}

internal fun DrawScope.drawEyeIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    paintShapeIcon(eyePath(), style, tint, strokeWidth, holes = listOf(ellipsePath(12f, 12f, 3.2f, 3.2f)))

internal fun DrawScope.drawEyeOffIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val slash = linePath(4f, 4f, 20f, 20f)
    paintShapeIcon(
        eyePath(),
        style,
        tint,
        strokeWidth,
        holes = listOf(ellipsePath(12f, 12f, 3.2f, 3.2f)),
        cuts = listOf(slash),
        cutWidth = 3.4f
    )
    if (style == IconStyle.Filled) drawPath(sketch(slash), color = tint, style = iconStroke(IconStyle.Default, strokeWidth, 1.6f))
}

private fun DrawScope.eyePath() = Path().apply {
    moveTo(d(2f), d(12f))
    cubicTo(d(5f), d(6.5f), d(8.5f), d(5f), d(12f), d(5f))
    cubicTo(d(15.5f), d(5f), d(19f), d(6.5f), d(22f), d(12f))
    cubicTo(d(19f), d(17.5f), d(15.5f), d(19f), d(12f), d(19f))
    cubicTo(d(8.5f), d(19f), d(5f), d(17.5f), d(2f), d(12f))
    close()
}

internal fun DrawScope.drawCopyIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val r = cornerRadius(style, 2f)
    val back = Path().apply {
        moveTo(d(8.5f), d(15.5f))
        lineTo(d(3.5f + r), d(15.5f))
        quadraticTo(d(3.5f), d(15.5f), d(3.5f), d(15.5f - r))
        lineTo(d(3.5f), d(3.5f + r))
        quadraticTo(d(3.5f), d(3.5f), d(3.5f + r), d(3.5f))
        lineTo(d(15.5f - r), d(3.5f))
        quadraticTo(d(15.5f), d(3.5f), d(15.5f), d(3.5f + r))
        lineTo(d(15.5f), d(8.5f))
    }
    paintLineIcon(listOf(back), style, tint, strokeWidth)
    paintIcon(roundRectPath(8.5f, 8.5f, 12f, 12f, r), style, tint, strokeWidth)
}

internal fun DrawScope.drawAttachIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    rotate(45f, pivot = pt(12f, 12f)) {
        fun arc(cx: Float, cy: Float, r: Float) = Rect(d(cx - r), d(cy - r), d(cx + r), d(cy + r))
        val clip = Path().apply {
            moveTo(d(13.5f), d(8f))
            lineTo(d(13.5f), d(16f))
            arcTo(arc(12f, 16f, 1.5f), 0f, 180f, false)
            lineTo(d(10.5f), d(5.5f))
            arcTo(arc(13.5f, 5.5f, 3f), 180f, 180f, false)
            lineTo(d(16.5f), d(17f))
            arcTo(arc(12f, 17f, 4.5f), 0f, 180f, false)
            lineTo(d(7.5f), d(9f))
        }
        paintLineIcon(listOf(clip), style, tint, strokeWidth)
    }

internal fun DrawScope.drawLogoutIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    paintLineIcon(
        listOf(doorFramePath(style, mirrored = false), linePath(10f, 12f, 20.5f, 12f), arrowHeadPath(20.5f, 12f, 0f, 5.5f)),
        style,
        tint,
        strokeWidth
    )

internal fun DrawScope.drawLoginIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    paintLineIcon(
        listOf(doorFramePath(style, mirrored = true), linePath(3.5f, 12f, 14f, 12f), arrowHeadPath(14f, 12f, 0f, 5.5f)),
        style,
        tint,
        strokeWidth
    )

internal fun DrawScope.drawGridViewIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val r = cornerRadius(style, 1.5f)
    listOf(3.5f to 3.5f, 13.5f to 3.5f, 3.5f to 13.5f, 13.5f to 13.5f).forEach { (x, y) ->
        paintIcon(roundRectPath(x, y, 7f, 7f, r), style, tint, strokeWidth)
    }
}

internal fun DrawScope.drawListViewIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val rows = listOf(6f, 12f, 18f)
    paintLineIcon(rows.map { y -> linePath(9f, y, 20.5f, y) }, style, tint, strokeWidth)
    rows.forEach { y -> solidDot(4.5f, y, 1.3f, tint) }
}

internal fun DrawScope.drawSlidersIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val knobs = listOf(6f to 15f, 12f to 8f, 18f to 13f)
    val knobR = 2.2f
    val tracks = knobs.flatMap { (y, x) -> listOf(linePath(3.5f, y, x - knobR, y), linePath(x + knobR, y, 20.5f, y)) }
    paintLineIcon(tracks, style, tint, strokeWidth)
    knobs.forEach { (y, x) -> paintIcon(ellipsePath(x, y, knobR, knobR), style, tint, strokeWidth) }
}

private fun DrawScope.doorFramePath(style: IconStyle, mirrored: Boolean): Path {
    fun x(v: Float) = d(if (mirrored) 24f - v else v)
    val r = cornerRadius(style, 2f)
    return Path().apply {
        moveTo(x(10f), d(4f))
        lineTo(x(4f + r), d(4f))
        quadraticTo(x(4f), d(4f), x(4f), d(4f + r))
        lineTo(x(4f), d(20f - r))
        quadraticTo(x(4f), d(20f), x(4f + r), d(20f))
        lineTo(x(10f), d(20f))
    }
}
