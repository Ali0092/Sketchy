package com.sketchy.library.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.sketchy.library.utils.d
import com.sketchy.library.utils.ellipsePath
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
