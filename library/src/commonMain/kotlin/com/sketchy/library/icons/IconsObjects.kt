package com.sketchy.library.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.sketchy.library.utils.d
import com.sketchy.library.utils.roundRectPath

internal fun DrawScope.drawGiftIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val leftBow = Path().apply {
        moveTo(d(12f), d(7.5f))
        cubicTo(d(9.5f), d(2.5f), d(5f), d(3.5f), d(7.5f), d(7.5f))
    }
    val rightBow = Path().apply {
        moveTo(d(12f), d(7.5f))
        cubicTo(d(14.5f), d(2.5f), d(19f), d(3.5f), d(16.5f), d(7.5f))
    }
    paintShapeIcon(
        roundRectPath(4.5f, 12f, 15f, 9f, cornerRadius(style, 1.5f)),
        style,
        tint,
        strokeWidth,
        cuts = listOf(linePath(12f, 12f, 12f, 21f))
    )
    paintShapeIcon(
        roundRectPath(3f, 7.5f, 18f, 3.5f, cornerRadius(style, 1f)),
        style,
        tint,
        strokeWidth,
        cuts = listOf(linePath(12f, 7.5f, 12f, 11f)),
        lines = listOf(leftBow, rightBow)
    )
}

internal fun DrawScope.drawLightbulbIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val bulb = Path().apply {
        moveTo(d(9f), d(17f))
        lineTo(d(9f), d(15.5f))
        cubicTo(d(9f), d(13.8f), d(5.5f), d(12.5f), d(5.5f), d(9f))
        cubicTo(d(5.5f), d(5.4f), d(8.4f), d(2.5f), d(12f), d(2.5f))
        cubicTo(d(15.6f), d(2.5f), d(18.5f), d(5.4f), d(18.5f), d(9f))
        cubicTo(d(18.5f), d(12.5f), d(15f), d(13.8f), d(15f), d(15.5f))
        lineTo(d(15f), d(17f))
        close()
    }
    val glint = Path().apply {
        moveTo(d(9f), d(8.5f))
        quadraticTo(d(9.3f), d(6.2f), d(11.5f), d(5.6f))
    }
    paintShapeIcon(
        bulb,
        style,
        tint,
        strokeWidth,
        cuts = listOf(glint),
        lines = listOf(linePath(9.5f, 19.3f, 14.5f, 19.3f), linePath(10.5f, 21.5f, 13.5f, 21.5f))
    )
}

internal fun DrawScope.drawCoffeeIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val mug = Path().apply {
        moveTo(d(4f), d(8f))
        lineTo(d(16f), d(8f))
        lineTo(d(16f), d(16f))
        cubicTo(d(16f), d(18.5f), d(14f), d(20f), d(12f), d(20f))
        lineTo(d(8f), d(20f))
        cubicTo(d(6f), d(20f), d(4f), d(18.5f), d(4f), d(16f))
        close()
    }
    val handle = Path().apply {
        moveTo(d(16f), d(10f))
        lineTo(d(18f), d(10f))
        cubicTo(d(19.7f), d(10f), d(21f), d(11.2f), d(21f), d(12.8f))
        cubicTo(d(21f), d(14.4f), d(19.7f), d(15.5f), d(18f), d(15.5f))
        lineTo(d(16f), d(15.5f))
    }
    val steam = listOf(8f, 12f).map { x ->
        Path().apply {
            moveTo(d(x), d(6f))
            cubicTo(d(x - 1f), d(4.8f), d(x + 1f), d(4f), d(x), d(2.5f))
        }
    }
    paintShapeIcon(mug, style, tint, strokeWidth, lines = listOf(handle) + steam)
}
