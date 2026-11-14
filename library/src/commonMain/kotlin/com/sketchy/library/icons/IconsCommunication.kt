package com.sketchy.library.icons

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.sketchy.library.utils.d
import com.sketchy.library.utils.ellipsePath
import com.sketchy.library.utils.roundRectPath
import com.sketchy.library.utils.roundedPolygonPath

internal fun DrawScope.drawPhoneIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val handset = Path().apply {
        moveTo(d(6.5f), d(3f))
        lineTo(d(9.5f), d(3f))
        lineTo(d(10.5f), d(7.5f))
        lineTo(d(8.5f), d(9.5f))
        cubicTo(d(9.5f), d(12f), d(12f), d(14.5f), d(14.5f), d(15.5f))
        lineTo(d(16.5f), d(13.5f))
        lineTo(d(21f), d(14.5f))
        lineTo(d(21f), d(17.5f))
        cubicTo(d(21f), d(19.5f), d(19.5f), d(21f), d(17.5f), d(21f))
        cubicTo(d(9.5f), d(20.5f), d(3.5f), d(14.5f), d(3f), d(6.5f))
        cubicTo(d(3f), d(4.5f), d(4.5f), d(3f), d(6.5f), d(3f))
        close()
    }
    paintIcon(handset, style, tint, strokeWidth)
}

internal fun DrawScope.drawChatIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val bubble = roundedPolygonPath(
        listOf(3f to 4f, 21f to 4f, 21f to 16f, 10f to 16f, 5.5f to 20f, 6f to 16f, 3f to 16f),
        cornerRadius(style, 2f)
    )
    paintShapeIcon(bubble, style, tint, strokeWidth, dots = listOf(8f to 10f, 12f to 10f, 16f to 10f))
}

internal fun DrawScope.drawSendIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val plane = roundedPolygonPath(
        listOf(2.5f to 10.5f, 21.5f to 2.5f, 14.5f to 21.5f, 11f to 13f),
        cornerRadius(style, 1f)
    )
    paintShapeIcon(plane, style, tint, strokeWidth, cuts = listOf(linePath(11f, 13f, 20.5f, 3.5f)))
}

internal fun DrawScope.drawVideoIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    paintIcon(roundRectPath(2.5f, 6f, 13f, 12f, cornerRadius(style, 2f)), style, tint, strokeWidth)
    val lens = roundedPolygonPath(listOf(15.5f to 10.5f, 21.5f to 7f, 21.5f to 17f, 15.5f to 13.5f), cornerRadius(style, 1f))
    paintIcon(lens, style, tint, strokeWidth)
}

internal fun DrawScope.drawGlobeIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    paintShapeIcon(
        ellipsePath(12f, 12f, 9f, 9f),
        style,
        tint,
        strokeWidth,
        cuts = listOf(ellipsePath(12f, 12f, 4f, 9f), linePath(3f, 12f, 21f, 12f))
    )

internal fun DrawScope.drawInboxIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val tray = roundedPolygonPath(
        listOf(3f to 13f, 6f to 4.5f, 18f to 4.5f, 21f to 13f, 21f to 19.5f, 3f to 19.5f),
        cornerRadius(style, 1.5f)
    )
    val slot = Path().apply {
        moveTo(d(3.5f), d(13f))
        lineTo(d(8f), d(13f))
        lineTo(d(9.5f), d(15.5f))
        lineTo(d(14.5f), d(15.5f))
        lineTo(d(16f), d(13f))
        lineTo(d(20.5f), d(13f))
    }
    paintShapeIcon(tray, style, tint, strokeWidth, cuts = listOf(slot))
}

internal fun DrawScope.drawRssIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val waves = listOf(7.5f, 14f).map { r ->
        Path().apply { arcTo(Rect(d(4.5f - r), d(19.5f - r), d(4.5f + r), d(19.5f + r)), 270f, 90f, true) }
    }
    paintLineIcon(waves, style, tint, strokeWidth)
    solidDot(6f, 18f, 1.7f, tint)
}

internal fun DrawScope.drawWifiIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val waves = listOf(4.5f, 9f, 13.5f).map { r ->
        Path().apply { arcTo(Rect(d(12f - r), d(19.5f - r), d(12f + r), d(19.5f + r)), 225f, 90f, true) }
    }
    paintLineIcon(waves, style, tint, strokeWidth)
    solidDot(12f, 19f, 1.5f, tint)
}
