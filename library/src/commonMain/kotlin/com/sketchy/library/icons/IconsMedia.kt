package com.sketchy.library.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.sketchy.library.utils.d
import com.sketchy.library.utils.roundRectPath
import com.sketchy.library.utils.roundedPolygonPath

internal fun DrawScope.drawPlayIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    paintIcon(roundedPolygonPath(listOf(7f to 4f, 19.5f to 12f, 7f to 20f), cornerRadius(style, 2f)), style, tint, strokeWidth)

internal fun DrawScope.drawPauseIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val r = cornerRadius(style, 1.5f)
    paintIcon(roundRectPath(6f, 4f, 4f, 16f, r), style, tint, strokeWidth)
    paintIcon(roundRectPath(14f, 4f, 4f, 16f, r), style, tint, strokeWidth)
}

internal fun DrawScope.drawMicIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val stand = Path().apply {
        moveTo(d(5.5f), d(11f))
        cubicTo(d(5.5f), d(15f), d(8.5f), d(17.5f), d(12f), d(17.5f))
        cubicTo(d(15.5f), d(17.5f), d(18.5f), d(15f), d(18.5f), d(11f))
    }
    paintShapeIcon(
        roundRectPath(9f, 2.5f, 6f, 12f, cornerRadius(style, 3f)),
        style,
        tint,
        strokeWidth,
        lines = listOf(stand, linePath(12f, 17.5f, 12f, 21.5f), linePath(8.5f, 21.5f, 15.5f, 21.5f))
    )
}

internal fun DrawScope.drawVolumeIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val speaker = roundedPolygonPath(
        listOf(3f to 9f, 7f to 9f, 12f to 4.5f, 12f to 19.5f, 7f to 15f, 3f to 15f),
        cornerRadius(style, 1f)
    )
    val nearWave = Path().apply {
        moveTo(d(15.5f), d(8.5f))
        quadraticTo(d(18f), d(12f), d(15.5f), d(15.5f))
    }
    val farWave = Path().apply {
        moveTo(d(18f), d(5.5f))
        quadraticTo(d(23f), d(12f), d(18f), d(18.5f))
    }
    paintShapeIcon(speaker, style, tint, strokeWidth, lines = listOf(nearWave, farWave))
}
