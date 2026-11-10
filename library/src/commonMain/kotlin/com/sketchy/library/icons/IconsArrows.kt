package com.sketchy.library.icons

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.sketchy.library.utils.d
import com.sketchy.library.utils.roundedPolygonPath
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

internal fun DrawScope.drawArrowUpIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    drawStraightArrow(12f, 20f, 12f, 4f, 9.9f, style, tint, strokeWidth)

internal fun DrawScope.drawArrowDownIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    drawStraightArrow(12f, 4f, 12f, 20f, 9.9f, style, tint, strokeWidth)

internal fun DrawScope.drawArrowLeftIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    drawStraightArrow(20f, 12f, 4f, 12f, 9.9f, style, tint, strokeWidth)

internal fun DrawScope.drawArrowRightIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    drawStraightArrow(4f, 12f, 20f, 12f, 9.9f, style, tint, strokeWidth)

internal fun DrawScope.drawArrowUpRightIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    drawStraightArrow(6f, 18f, 18f, 6f, 8.5f, style, tint, strokeWidth)

internal fun DrawScope.drawChevronUpIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    paintLineIcon(listOf(arrowHeadPath(12f, 8.5f, -90f, 8.5f)), style, tint, strokeWidth)

internal fun DrawScope.drawChevronDownIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    paintLineIcon(listOf(arrowHeadPath(12f, 15.5f, 90f, 8.5f)), style, tint, strokeWidth)

internal fun DrawScope.drawChevronLeftIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    paintLineIcon(listOf(arrowHeadPath(8.5f, 12f, 180f, 8.5f)), style, tint, strokeWidth)

internal fun DrawScope.drawChevronRightIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    paintLineIcon(listOf(arrowHeadPath(15.5f, 12f, 0f, 8.5f)), style, tint, strokeWidth)

internal fun DrawScope.drawArrowBigLeftIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    drawBlockArrow(mirrored = true, style, tint, strokeWidth)

internal fun DrawScope.drawArrowBigRightIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    drawBlockArrow(mirrored = false, style, tint, strokeWidth)

internal fun DrawScope.drawUndoIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    drawHookArrow(mirrored = false, style, tint, strokeWidth)

internal fun DrawScope.drawRedoIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    drawHookArrow(mirrored = true, style, tint, strokeWidth)

internal fun DrawScope.drawRefreshIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val endAngle = 320f * (PI / 180.0).toFloat()
    val tipX = 12f + 8f * cos(endAngle)
    val tipY = 12f + 8f * sin(endAngle)
    val arc = Path().apply { arcTo(Rect(d(4f), d(4f), d(20f), d(20f)), 40f, 280f, true) }
    paintLineIcon(listOf(arc, arrowHeadPath(tipX, tipY, 50f, 5f)), style, tint, strokeWidth)
}

internal fun DrawScope.drawSwapIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    paintLineIcon(
        listOf(
            linePath(4f, 8f, 20f, 8f),
            arrowHeadPath(20f, 8f, 0f, 5.5f),
            linePath(20f, 16f, 4f, 16f),
            arrowHeadPath(4f, 16f, 180f, 5.5f),
        ),
        style,
        tint,
        strokeWidth
    )

internal fun DrawScope.drawSortIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    paintLineIcon(
        listOf(
            linePath(8f, 20f, 8f, 4f),
            arrowHeadPath(8f, 4f, -90f, 5.5f),
            linePath(16f, 4f, 16f, 20f),
            arrowHeadPath(16f, 20f, 90f, 5.5f),
        ),
        style,
        tint,
        strokeWidth
    )

internal fun DrawScope.drawExpandIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    paintLineIcon(
        listOf(
            linePath(13.5f, 10.5f, 20f, 4f),
            arrowHeadPath(20f, 4f, -45f, 6f),
            linePath(10.5f, 13.5f, 4f, 20f),
            arrowHeadPath(4f, 20f, 135f, 6f),
        ),
        style,
        tint,
        strokeWidth
    )

internal fun DrawScope.drawCollapseIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    paintLineIcon(
        listOf(
            linePath(20f, 4f, 14f, 10f),
            arrowHeadPath(14f, 10f, 135f, 6f),
            linePath(4f, 20f, 10f, 14f),
            arrowHeadPath(10f, 14f, -45f, 6f),
        ),
        style,
        tint,
        strokeWidth
    )

private fun DrawScope.drawStraightArrow(
    fromX: Float,
    fromY: Float,
    toX: Float,
    toY: Float,
    headSize: Float,
    style: IconStyle,
    tint: Color,
    strokeWidth: Float,
) {
    val angle = atan2(toY - fromY, toX - fromX) * (180.0 / PI).toFloat()
    paintLineIcon(listOf(linePath(fromX, fromY, toX, toY), arrowHeadPath(toX, toY, angle, headSize)), style, tint, strokeWidth)
}

private fun DrawScope.drawBlockArrow(mirrored: Boolean, style: IconStyle, tint: Color, strokeWidth: Float) {
    val points = listOf(3f to 9f, 12f to 9f, 12f to 4f, 21f to 12f, 12f to 20f, 12f to 15f, 3f to 15f)
        .map { (x, y) -> (if (mirrored) 24f - x else x) to y }
    paintIcon(roundedPolygonPath(points, cornerRadius(style, 1.2f)), style, tint, strokeWidth)
}

private fun DrawScope.drawHookArrow(mirrored: Boolean, style: IconStyle, tint: Color, strokeWidth: Float) {
    fun x(v: Float) = if (mirrored) 24f - v else v
    val shaft = Path().apply {
        moveTo(d(x(20f)), d(19f))
        cubicTo(d(x(20f)), d(13.5f), d(x(16.5f)), d(9f), d(x(11f)), d(9f))
        lineTo(d(x(4.5f)), d(9f))
    }
    val head = arrowHeadPath(x(4.5f), 9f, if (mirrored) 0f else 180f, 6f)
    paintLineIcon(listOf(shaft, head), style, tint, strokeWidth)
}
