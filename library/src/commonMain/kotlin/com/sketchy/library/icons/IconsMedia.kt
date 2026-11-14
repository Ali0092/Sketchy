package com.sketchy.library.icons

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.sketchy.library.utils.d
import com.sketchy.library.utils.ellipsePath
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
    val nearWave = Path().apply {
        moveTo(d(15.5f), d(8.5f))
        quadraticTo(d(18f), d(12f), d(15.5f), d(15.5f))
    }
    val farWave = Path().apply {
        moveTo(d(18f), d(5.5f))
        quadraticTo(d(23f), d(12f), d(18f), d(18.5f))
    }
    paintShapeIcon(speakerPath(style), style, tint, strokeWidth, lines = listOf(nearWave, farWave))
}

internal fun DrawScope.drawStopIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    paintIcon(roundRectPath(5f, 5f, 14f, 14f, cornerRadius(style, 2.5f)), style, tint, strokeWidth)

internal fun DrawScope.drawSkipNextIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    drawSkipIcon(mirrored = false, style, tint, strokeWidth)

internal fun DrawScope.drawSkipPreviousIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    drawSkipIcon(mirrored = true, style, tint, strokeWidth)

internal fun DrawScope.drawMusicIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    paintIcon(ellipsePath(7f, 17.5f, 2.5f, 2.5f), style, tint, strokeWidth)
    paintIcon(ellipsePath(17f, 15.5f, 2.5f, 2.5f), style, tint, strokeWidth)
    val stems = Path().apply {
        moveTo(d(9.5f), d(17.5f))
        lineTo(d(9.5f), d(6f))
        lineTo(d(19.5f), d(4f))
        lineTo(d(19.5f), d(15.5f))
    }
    paintLineIcon(listOf(stems), style, tint, strokeWidth)
}

internal fun DrawScope.drawHeadphonesIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val band = Path().apply {
        moveTo(d(4.5f), d(14f))
        arcTo(Rect(d(4.5f), d(4.5f), d(19.5f), d(19.5f)), 180f, 180f, false)
        lineTo(d(19.5f), d(14f))
    }
    paintLineIcon(listOf(band), style, tint, strokeWidth)
    val r = cornerRadius(style, 1.5f)
    paintIcon(roundRectPath(3f, 13.5f, 4.5f, 7f, r), style, tint, strokeWidth)
    paintIcon(roundRectPath(16.5f, 13.5f, 4.5f, 7f, r), style, tint, strokeWidth)
}

internal fun DrawScope.drawMuteIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    paintShapeIcon(
        speakerPath(style),
        style,
        tint,
        strokeWidth,
        lines = listOf(linePath(16f, 9.5f, 21f, 14.5f), linePath(21f, 9.5f, 16f, 14.5f))
    )

private fun DrawScope.drawSkipIcon(mirrored: Boolean, style: IconStyle, tint: Color, strokeWidth: Float) {
    fun x(v: Float) = if (mirrored) 24f - v else v
    paintIcon(roundedPolygonPath(listOf(x(5f) to 5f, x(15.5f) to 12f, x(5f) to 19f), cornerRadius(style, 1.5f)), style, tint, strokeWidth)
    paintLineIcon(listOf(linePath(x(18.5f), 5f, x(18.5f), 19f)), style, tint, strokeWidth)
}

private fun DrawScope.speakerPath(style: IconStyle) = roundedPolygonPath(
    listOf(3f to 9f, 7f to 9f, 12f to 4.5f, 12f to 19.5f, 7f to 15f, 3f to 15f),
    cornerRadius(style, 1f)
)
