package com.sketchy.library.icons

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.sketchy.library.utils.d
import com.sketchy.library.utils.ellipsePath
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

internal fun DrawScope.drawSunIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val rays = (0 until 8).map { i ->
        val ang = i * 45f * (PI / 180.0).toFloat()
        linePath(12f + 7f * cos(ang), 12f + 7f * sin(ang), 12f + 9.5f * cos(ang), 12f + 9.5f * sin(ang))
    }
    paintShapeIcon(ellipsePath(12f, 12f, 4.2f, 4.2f), style, tint, strokeWidth, lines = rays)
}

internal fun DrawScope.drawMoonIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val innerR = 6.364f
    val crescent = Path().apply {
        arcTo(Rect(d(16.5f - innerR), d(7.5f - innerR), d(16.5f + innerR), d(7.5f + innerR)), 225f, -180f, true)
        arcTo(Rect(d(3f), d(3f), d(21f), d(21f)), 0f, 270f, false)
        close()
    }
    paintIcon(crescent, style, tint, strokeWidth)
}

internal fun DrawScope.drawCloudIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val cloud = Path().apply {
        moveTo(d(7f), d(19f))
        cubicTo(d(4f), d(19f), d(2.5f), d(16.8f), d(2.5f), d(14.8f))
        cubicTo(d(2.5f), d(12.5f), d(4.3f), d(10.8f), d(6.5f), d(10.7f))
        cubicTo(d(7f), d(7.5f), d(9.5f), d(5f), d(12.8f), d(5f))
        cubicTo(d(16f), d(5f), d(18.3f), d(7.3f), d(18.8f), d(10.3f))
        cubicTo(d(21f), d(10.6f), d(22f), d(12.8f), d(22f), d(14.6f))
        cubicTo(d(22f), d(17.1f), d(20.2f), d(19f), d(17.7f), d(19f))
        close()
    }
    paintIcon(cloud, style, tint, strokeWidth)
}
