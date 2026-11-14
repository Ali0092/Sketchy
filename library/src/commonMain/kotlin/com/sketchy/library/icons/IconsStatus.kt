package com.sketchy.library.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import com.sketchy.library.utils.d
import com.sketchy.library.utils.ellipsePath
import com.sketchy.library.utils.roundRectPath
import com.sketchy.library.utils.roundedPolygonPath

internal fun DrawScope.drawInfoIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    paintShapeIcon(
        ellipsePath(12f, 12f, 9f, 9f),
        style,
        tint,
        strokeWidth,
        cuts = listOf(linePath(12f, 11f, 12f, 16.5f)),
        dots = listOf(12f to 7.8f)
    )

internal fun DrawScope.drawWarningIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val triangle = roundedPolygonPath(listOf(12f to 3.5f, 21.5f to 20f, 2.5f to 20f), cornerRadius(style, 2f))
    paintShapeIcon(
        triangle,
        style,
        tint,
        strokeWidth,
        cuts = listOf(linePath(12f, 9.5f, 12f, 14f)),
        dots = listOf(12f to 17f)
    )
}

internal fun DrawScope.drawHelpIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val hook = Path().apply {
        moveTo(d(9.3f), d(9.5f))
        cubicTo(d(9.3f), d(7.6f), d(10.6f), d(6.6f), d(12f), d(6.6f))
        cubicTo(d(13.6f), d(6.6f), d(14.8f), d(7.7f), d(14.8f), d(9.1f))
        cubicTo(d(14.8f), d(11.3f), d(12f), d(11.5f), d(12f), d(13.8f))
    }
    paintShapeIcon(ellipsePath(12f, 12f, 9f, 9f), style, tint, strokeWidth, cuts = listOf(hook), dots = listOf(12f to 17f))
}

internal fun DrawScope.drawCheckCircleIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val tick = Path().apply {
        moveTo(d(7.8f), d(12.3f))
        lineTo(d(10.8f), d(15.3f))
        lineTo(d(16.3f), d(9.3f))
    }
    paintShapeIcon(ellipsePath(12f, 12f, 9f, 9f), style, tint, strokeWidth, cuts = listOf(tick))
}

internal fun DrawScope.drawCloseCircleIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    paintShapeIcon(
        ellipsePath(12f, 12f, 9f, 9f),
        style,
        tint,
        strokeWidth,
        cuts = listOf(linePath(9f, 9f, 15f, 15f), linePath(15f, 9f, 9f, 15f))
    )

internal fun DrawScope.drawShieldIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val shield = Path().apply {
        moveTo(d(12f), d(2.5f))
        lineTo(d(19.5f), d(5.5f))
        lineTo(d(19.5f), d(11f))
        cubicTo(d(19.5f), d(16f), d(16.5f), d(19.5f), d(12f), d(21.5f))
        cubicTo(d(7.5f), d(19.5f), d(4.5f), d(16f), d(4.5f), d(11f))
        lineTo(d(4.5f), d(5.5f))
        close()
    }
    val tick = Path().apply {
        moveTo(d(8.8f), d(12f))
        lineTo(d(11.2f), d(14.4f))
        lineTo(d(15.4f), d(9.8f))
    }
    paintShapeIcon(shield, style, tint, strokeWidth, cuts = listOf(tick))
}

internal fun DrawScope.drawBatteryIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val holes = if (style == IconStyle.Filled) listOf(roundRectPath(4.5f, 9f, 13f, 6f, cornerRadius(style, 1f))) else emptyList()
    paintShapeIcon(
        roundRectPath(2.5f, 7f, 17f, 10f, cornerRadius(style, 2f)),
        style,
        tint,
        strokeWidth,
        holes = holes,
        lines = listOf(linePath(21.5f, 10.5f, 21.5f, 13.5f))
    )
    drawPath(sketch(roundRectPath(5.5f, 10f, 7f, 4f, cornerRadius(style, 0.8f))), color = tint, style = Fill)
}
