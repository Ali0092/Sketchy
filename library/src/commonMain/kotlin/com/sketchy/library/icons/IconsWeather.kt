package com.sketchy.library.icons

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.sketchy.library.utils.d
import com.sketchy.library.utils.ellipsePath
import com.sketchy.library.utils.roundedPolygonPath
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

internal fun DrawScope.drawCloudIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    paintIcon(cloudPath(), style, tint, strokeWidth)

internal fun DrawScope.drawRainIcon(style: IconStyle, tint: Color, strokeWidth: Float) =
    paintShapeIcon(
        cloudPath(scale = 0.8f, lift = 2f),
        style,
        tint,
        strokeWidth,
        lines = listOf(linePath(8.5f, 17f, 7.5f, 20f), linePath(12.5f, 17f, 11.5f, 21f), linePath(16.5f, 17f, 15.5f, 20f))
    )

internal fun DrawScope.drawSnowIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    paintIcon(cloudPath(scale = 0.8f, lift = 2f), style, tint, strokeWidth)
    listOf(8f to 18f, 12f to 19.5f, 16f to 18f, 10f to 21.8f, 14f to 21.8f).forEach { (x, y) -> solidDot(x, y, 1.1f, tint) }
}

internal fun DrawScope.drawLightningIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val bolt = roundedPolygonPath(
        listOf(13.5f to 2.5f, 5f to 13.5f, 11.5f to 13.5f, 10.5f to 21.5f, 19f to 10.5f, 12.5f to 10.5f),
        cornerRadius(style, 0.8f)
    )
    paintIcon(bolt, style, tint, strokeWidth)
}

internal fun DrawScope.drawWindIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val top = Path().apply {
        moveTo(d(3f), d(7f))
        lineTo(d(13f), d(7f))
        cubicTo(d(15.5f), d(7f), d(16f), d(3.5f), d(13.5f), d(3.3f))
        cubicTo(d(12.5f), d(3.2f), d(11.8f), d(4f), d(11.8f), d(4.8f))
    }
    val middle = Path().apply {
        moveTo(d(3f), d(12f))
        lineTo(d(17.5f), d(12f))
        cubicTo(d(20.5f), d(12f), d(21f), d(8.2f), d(18.5f), d(8f))
        cubicTo(d(17.5f), d(7.9f), d(16.8f), d(8.7f), d(16.8f), d(9.5f))
    }
    val bottom = Path().apply {
        moveTo(d(3f), d(17f))
        lineTo(d(14f), d(17f))
        cubicTo(d(16.5f), d(17f), d(17f), d(20.7f), d(14.5f), d(20.9f))
        cubicTo(d(13.5f), d(21f), d(12.8f), d(20.2f), d(12.8f), d(19.4f))
    }
    paintLineIcon(listOf(top, middle, bottom), style, tint, strokeWidth)
}

internal fun DrawScope.drawUmbrellaIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val canopy = Path().apply {
        moveTo(d(2.5f), d(12f))
        cubicTo(d(2.5f), d(6.5f), d(7f), d(3f), d(12f), d(3f))
        cubicTo(d(17f), d(3f), d(21.5f), d(6.5f), d(21.5f), d(12f))
        quadraticTo(d(19.1f), d(10.2f), d(16.75f), d(12f))
        quadraticTo(d(14.4f), d(10.2f), d(12f), d(12f))
        quadraticTo(d(9.6f), d(10.2f), d(7.25f), d(12f))
        quadraticTo(d(4.9f), d(10.2f), d(2.5f), d(12f))
        close()
    }
    val handle = Path().apply {
        moveTo(d(12f), d(12f))
        lineTo(d(12f), d(19f))
        cubicTo(d(12f), d(21.5f), d(8.5f), d(21.5f), d(8.5f), d(19f))
    }
    paintShapeIcon(canopy, style, tint, strokeWidth, lines = listOf(handle))
}

private fun DrawScope.cloudPath(scale: Float = 1f, lift: Float = 0f): Path {
    fun x(v: Float) = d(12f + (v - 12f) * scale)
    fun y(v: Float) = d(5f - lift + (v - 5f) * scale)
    return Path().apply {
        moveTo(x(7f), y(19f))
        cubicTo(x(4f), y(19f), x(2.5f), y(16.8f), x(2.5f), y(14.8f))
        cubicTo(x(2.5f), y(12.5f), x(4.3f), y(10.8f), x(6.5f), y(10.7f))
        cubicTo(x(7f), y(7.5f), x(9.5f), y(5f), x(12.8f), y(5f))
        cubicTo(x(16f), y(5f), x(18.3f), y(7.3f), x(18.8f), y(10.3f))
        cubicTo(x(21f), y(10.6f), x(22f), y(12.8f), x(22f), y(14.6f))
        cubicTo(x(22f), y(17.1f), x(20.2f), y(19f), x(17.7f), y(19f))
        close()
    }
}
