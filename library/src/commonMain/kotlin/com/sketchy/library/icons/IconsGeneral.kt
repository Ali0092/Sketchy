package com.sketchy.library.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import com.sketchy.library.utils.d
import com.sketchy.library.utils.ellipsePath
import com.sketchy.library.utils.roundRectPath
import com.sketchy.library.utils.roundedPolygonPath
import kotlin.math.cos
import kotlin.math.sin

/**
 * The **General** icon set: 20 everyday glyphs on the standard 24×24 grid. Each is built from a
 * single path wherever a shape's silhouette alone reads correctly stroked or filled (a heart, a
 * star, a folder); where a filled glyph needs a real internal gap to stay legible (a gear's hub, a
 * clock's hands, a lock's keyhole), that gap is cut in via [withHole] rather than redrawn on top —
 * a line drawn in the same tint over a solid fill of that tint would simply vanish.
 *
 * A handful of accents (a filled search icon's bolder handle, an edit icon's finer tip separator)
 * draw at a multiple of the caller's [IconStyle]-relative width rather than the base weight — see
 * [outlineWidth] — so they stay in proportion as [strokeWidth] is turned up or down.
 */

// ─── Home ─────────────────────────────────────────────────────────────────────

internal fun DrawScope.drawHomeIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val r = cornerRadius(style, 2f)
    val body = roundedPolygonPath(listOf(12f to 3f, 20f to 10.5f, 20f to 21f, 4f to 21f, 4f to 10.5f), r)
    val door = roundRectPath(10f, 14.5f, 4f, 6.5f, cornerRadius(style, 1.2f))
    when (style) {
        IconStyle.Filled -> drawPath(body.withHole(door), color = tint, style = Fill)
        IconStyle.TwoTone -> {
            drawPath(body.withHole(door), color = tint.copy(alpha = tint.alpha * 0.28f), style = Fill)
            drawPath(body, color = tint, style = iconStroke(IconStyle.Default, strokeWidth))
            drawPath(door, color = tint, style = iconStroke(IconStyle.Default, strokeWidth))
        }
        else -> {
            drawPath(body, color = tint, style = iconStroke(style, strokeWidth))
            drawPath(door, color = tint, style = iconStroke(style, strokeWidth))
        }
    }
}

// ─── Search ───────────────────────────────────────────────────────────────────

internal fun DrawScope.drawSearchIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val ring = ellipsePath(10.5f, 10.5f, 6.5f, 6.5f)
    val handle = Path().apply {
        moveTo(d(15.2f), d(15.2f))
        lineTo(d(20.5f), d(20.5f))
    }
    when (style) {
        IconStyle.Filled -> {
            val innerHole = ellipsePath(10.5f, 10.5f, 4.2f, 4.2f)
            drawPath(ring.withHole(innerHole), color = tint, style = Fill)
            drawPath(handle, color = tint, style = iconStroke(style, strokeWidth, 1.6f))
        }
        IconStyle.TwoTone -> {
            val innerHole = ellipsePath(10.5f, 10.5f, 4.2f, 4.2f)
            drawPath(ring.withHole(innerHole), color = tint.copy(alpha = tint.alpha * 0.28f), style = Fill)
            drawPath(ring, color = tint, style = iconStroke(IconStyle.Default, strokeWidth))
            drawPath(handle, color = tint, style = iconStroke(IconStyle.Default, strokeWidth, 1.5f))
        }
        else -> {
            drawPath(ring, color = tint, style = iconStroke(style, strokeWidth))
            drawPath(handle, color = tint, style = iconStroke(style, strokeWidth))
        }
    }
}

// ─── Heart ────────────────────────────────────────────────────────────────────

internal fun DrawScope.drawHeartIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val heart = Path().apply {
        moveTo(d(12f), d(20f))
        cubicTo(d(4f), d(13.5f), d(2f), d(8.5f), d(6.5f), d(5.5f))
        cubicTo(d(9.5f), d(3.5f), d(12f), d(6f), d(12f), d(8f))
        cubicTo(d(12f), d(6f), d(14.5f), d(3.5f), d(17.5f), d(5.5f))
        cubicTo(d(22f), d(8.5f), d(20f), d(13.5f), d(12f), d(20f))
        close()
    }
    paintIcon(heart, style, tint, strokeWidth)
}

// ─── Star ─────────────────────────────────────────────────────────────────────

internal fun DrawScope.drawStarIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val r = cornerRadius(style, 1f)
    val cx = 12f
    val cy = 12.5f
    val outerR = 9f
    val innerR = 3.6f
    val points = (0 until 10).map { i ->
        val ang = (-90f + i * 36f) * (kotlin.math.PI / 180.0).toFloat()
        val rad = if (i % 2 == 0) outerR else innerR
        (cx + rad * cos(ang)) to (cy + rad * sin(ang))
    }
    paintIcon(roundedPolygonPath(points, r), style, tint, strokeWidth)
}

// ─── Bell ─────────────────────────────────────────────────────────────────────

internal fun DrawScope.drawBellIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val body = Path().apply {
        moveTo(d(6f), d(17f))
        quadraticTo(d(5f), d(17f), d(6.3f), d(15.3f))
        lineTo(d(7f), d(11f))
        cubicTo(d(7f), d(6.5f), d(9.8f), d(4f), d(12f), d(4f))
        cubicTo(d(14.2f), d(4f), d(17f), d(6.5f), d(17f), d(11f))
        lineTo(d(17.7f), d(15.3f))
        quadraticTo(d(19f), d(17f), d(18f), d(17f))
        close()
    }
    paintIcon(body, style, tint, strokeWidth)
    solidDot(12f, 20f, 1.6f, tint)
}

// ─── Settings ─────────────────────────────────────────────────────────────────

internal fun DrawScope.drawSettingsIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val r = cornerRadius(style, 0.8f)
    val cx = 12f
    val cy = 12f
    val teeth = 8
    val outerR = 9.3f
    val innerR = 7.4f
    val points = (0 until teeth * 2).map { i ->
        val ang = (i * 360f / (teeth * 2)) * (kotlin.math.PI / 180.0).toFloat()
        val rad = if (i % 2 == 0) outerR else innerR
        (cx + rad * cos(ang)) to (cy + rad * sin(ang))
    }
    val gear = roundedPolygonPath(points, r)
    val hub = ellipsePath(cx, cy, 3.2f, 3.2f)
    when (style) {
        IconStyle.Filled -> drawPath(gear.withHole(hub), color = tint, style = Fill)
        IconStyle.TwoTone -> {
            drawPath(gear.withHole(hub), color = tint.copy(alpha = tint.alpha * 0.28f), style = Fill)
            drawPath(gear, color = tint, style = iconStroke(IconStyle.Default, strokeWidth))
            drawPath(hub, color = tint, style = iconStroke(IconStyle.Default, strokeWidth))
        }
        else -> {
            drawPath(gear, color = tint, style = iconStroke(style, strokeWidth))
            drawPath(hub, color = tint, style = iconStroke(style, strokeWidth))
        }
    }
}

// ─── User ─────────────────────────────────────────────────────────────────────

internal fun DrawScope.drawUserIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val head = ellipsePath(12f, 8f, 4f, 4f)
    val shoulders = Path().apply {
        moveTo(d(4.5f), d(21f))
        quadraticTo(d(4.5f), d(14f), d(12f), d(14f))
        quadraticTo(d(19.5f), d(14f), d(19.5f), d(21f))
    }
    fun closedShoulders() = Path().apply {
        addPath(shoulders)
        lineTo(d(4.5f), d(21f))
        close()
    }
    when (style) {
        IconStyle.Filled -> {
            drawPath(head, color = tint, style = Fill)
            drawPath(closedShoulders(), color = tint, style = Fill)
        }
        IconStyle.TwoTone -> {
            val faint = tint.copy(alpha = tint.alpha * 0.28f)
            drawPath(head, color = faint, style = Fill)
            drawPath(closedShoulders(), color = faint, style = Fill)
            drawPath(head, color = tint, style = iconStroke(IconStyle.Default, strokeWidth))
            drawPath(shoulders, color = tint, style = iconStroke(IconStyle.Default, strokeWidth))
        }
        else -> {
            drawPath(head, color = tint, style = iconStroke(style, strokeWidth))
            drawPath(shoulders, color = tint, style = iconStroke(style, strokeWidth))
        }
    }
}

// ─── Mail ─────────────────────────────────────────────────────────────────────

internal fun DrawScope.drawMailIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val r = cornerRadius(style, 2f)
    val body = roundRectPath(3f, 5f, 18f, 14f, r)
    val flap = Path().apply {
        moveTo(d(3.6f), d(6f))
        lineTo(d(12f), d(13f))
        lineTo(d(20.4f), d(6f))
    }
    val flapHole = Path().apply {
        addPath(flap)
        lineTo(d(20.4f), d(6.9f))
        lineTo(d(12f), d(13.9f))
        lineTo(d(3.6f), d(6.9f))
        close()
    }
    when (style) {
        IconStyle.Filled -> drawPath(body.withHole(flapHole), color = tint, style = Fill)
        IconStyle.TwoTone -> {
            drawPath(body.withHole(flapHole), color = tint.copy(alpha = tint.alpha * 0.28f), style = Fill)
            drawPath(body, color = tint, style = iconStroke(IconStyle.Default, strokeWidth))
            drawPath(flap, color = tint, style = iconStroke(IconStyle.Default, strokeWidth))
        }
        else -> {
            drawPath(body, color = tint, style = iconStroke(style, strokeWidth))
            drawPath(flap, color = tint, style = iconStroke(style, strokeWidth))
        }
    }
}

// ─── Calendar ─────────────────────────────────────────────────────────────────

internal fun DrawScope.drawCalendarIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val r = cornerRadius(style, 2f)
    val body = roundRectPath(3.5f, 4.5f, 17f, 16f, r)
    val headerLine = Path().apply {
        moveTo(d(3.5f), d(9.5f))
        lineTo(d(20.5f), d(9.5f))
    }
    val headerHole = Path().apply {
        addPath(headerLine)
        lineTo(d(20.5f), d(10.3f))
        lineTo(d(3.5f), d(10.3f))
        close()
    }
    when (style) {
        IconStyle.Filled -> drawPath(body.withHole(headerHole), color = tint, style = Fill)
        IconStyle.TwoTone -> {
            drawPath(body.withHole(headerHole), color = tint.copy(alpha = tint.alpha * 0.28f), style = Fill)
            drawPath(body, color = tint, style = iconStroke(IconStyle.Default, strokeWidth))
            drawPath(headerLine, color = tint, style = iconStroke(IconStyle.Default, strokeWidth))
        }
        else -> {
            drawPath(body, color = tint, style = iconStroke(style, strokeWidth))
            drawPath(headerLine, color = tint, style = iconStroke(style, strokeWidth))
        }
    }
    solidDot(7.5f, 3.6f, 0.9f, tint)
    solidDot(16.5f, 3.6f, 0.9f, tint)
}

// ─── Clock ────────────────────────────────────────────────────────────────────

internal fun DrawScope.drawClockIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val face = ellipsePath(12f, 12f, 9f, 9f)
    val hourHand = Path().apply { moveTo(d(12f), d(12f)); lineTo(d(12f), d(7.5f)) }
    val minuteHand = Path().apply { moveTo(d(12f), d(12f)); lineTo(d(16f), d(13.5f)) }
    val handsHole = Path().apply {
        addPath(
            Path().apply {
                moveTo(d(12.6f), d(12f)); lineTo(d(12.6f), d(7.5f)); lineTo(d(11.4f), d(7.5f)); lineTo(d(11.4f), d(12f)); close()
            }
        )
        addPath(
            Path().apply {
                moveTo(d(12f), d(11.4f)); lineTo(d(16f), d(12.9f)); lineTo(d(15.7f), d(13.7f)); lineTo(d(12f), d(12.6f)); close()
            }
        )
    }
    when (style) {
        IconStyle.Filled -> drawPath(face.withHole(handsHole), color = tint, style = Fill)
        IconStyle.TwoTone -> {
            drawPath(face.withHole(handsHole), color = tint.copy(alpha = tint.alpha * 0.28f), style = Fill)
            drawPath(face, color = tint, style = iconStroke(IconStyle.Default, strokeWidth))
            drawPath(hourHand, color = tint, style = iconStroke(IconStyle.Default, strokeWidth))
            drawPath(minuteHand, color = tint, style = iconStroke(IconStyle.Default, strokeWidth))
        }
        else -> {
            drawPath(face, color = tint, style = iconStroke(style, strokeWidth))
            drawPath(hourHand, color = tint, style = iconStroke(style, strokeWidth))
            drawPath(minuteHand, color = tint, style = iconStroke(style, strokeWidth))
        }
    }
}

// ─── Cart ─────────────────────────────────────────────────────────────────────

internal fun DrawScope.drawCartIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val r = cornerRadius(style, 1.5f)
    val basket = roundedPolygonPath(listOf(5f to 6f, 21f to 6f, 18.5f to 15f, 8f to 15f), r)
    val handle = Path().apply {
        moveTo(d(2f), d(4f))
        lineTo(d(5f), d(4f))
        lineTo(d(6.5f), d(9f))
    }
    paintIcon(basket, style, tint, strokeWidth)
    drawPath(handle, color = tint, style = iconStroke(style, strokeWidth))
    solidDot(9.5f, 19f, 1.6f, tint)
    solidDot(17f, 19f, 1.6f, tint)
}

// ─── Edit ─────────────────────────────────────────────────────────────────────

internal fun DrawScope.drawEditIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val pencil = roundedPolygonPath(
        listOf(4f to 20f, 4.8f to 15.8f, 15.5f to 5.1f, 18.9f to 8.5f, 8.2f to 19.2f),
        cornerRadius(style, 1f)
    )
    paintIcon(pencil, style, tint, strokeWidth)
    if (style != IconStyle.Filled) {
        drawPath(
            Path().apply { moveTo(d(6.2f), d(17.8f)); lineTo(d(14.6f), d(9.4f)) },
            color = tint,
            style = iconStroke(style, strokeWidth, 0.6f)
        )
    }
}

// ─── Trash ────────────────────────────────────────────────────────────────────

internal fun DrawScope.drawTrashIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val r = cornerRadius(style, 1.5f)
    val bin = roundedPolygonPath(listOf(6f to 8f, 18f to 8f, 16.5f to 21f, 7.5f to 21f), r)
    val lines = Path().apply {
        addPath(Path().apply { moveTo(d(10f), d(11f)); lineTo(d(10f), d(18f)) })
        addPath(Path().apply { moveTo(d(12f), d(11f)); lineTo(d(12f), d(18f)) })
        addPath(Path().apply { moveTo(d(14f), d(11f)); lineTo(d(14f), d(18f)) })
    }
    val slitHoles = Path().apply {
        listOf(10f, 12f, 14f).forEach { x ->
            addPath(
                Path().apply {
                    moveTo(d(x - 0.4f), d(11f)); lineTo(d(x + 0.4f), d(11f))
                    lineTo(d(x + 0.4f), d(18f)); lineTo(d(x - 0.4f), d(18f)); close()
                }
            )
        }
    }
    when (style) {
        IconStyle.Filled -> drawPath(bin.withHole(slitHoles), color = tint, style = Fill)
        IconStyle.TwoTone -> {
            drawPath(bin.withHole(slitHoles), color = tint.copy(alpha = tint.alpha * 0.28f), style = Fill)
            drawPath(bin, color = tint, style = iconStroke(IconStyle.Default, strokeWidth))
            drawPath(lines, color = tint, style = iconStroke(IconStyle.Default, strokeWidth))
        }
        else -> {
            drawPath(bin, color = tint, style = iconStroke(style, strokeWidth))
            drawPath(lines, color = tint, style = iconStroke(style, strokeWidth))
        }
    }
    val lid = roundRectPath(4.5f, 5f, 15f, 2.2f, cornerRadius(style, 1f))
    paintIcon(lid, style, tint, strokeWidth)
    drawPath(
        Path().apply {
            moveTo(d(9.5f), d(5f)); lineTo(d(10.2f), d(3.2f)); lineTo(d(13.8f), d(3.2f)); lineTo(d(14.5f), d(5f))
        },
        color = tint,
        style = iconStroke(style, strokeWidth)
    )
}

// ─── Camera ───────────────────────────────────────────────────────────────────

internal fun DrawScope.drawCameraIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val r = cornerRadius(style, 2f)
    val body = roundRectPath(3f, 7f, 18f, 12.5f, r)
    val lens = ellipsePath(12f, 13f, 3.6f, 3.6f)
    val viewfinder = roundRectPath(9f, 4.5f, 6f, 3f, cornerRadius(style, 1f))
    when (style) {
        IconStyle.Filled -> {
            drawPath(body.withHole(lens), color = tint, style = Fill)
            drawPath(viewfinder, color = tint, style = Fill)
        }
        IconStyle.TwoTone -> {
            val faint = tint.copy(alpha = tint.alpha * 0.28f)
            drawPath(body.withHole(lens), color = faint, style = Fill)
            drawPath(viewfinder, color = faint, style = Fill)
            drawPath(body, color = tint, style = iconStroke(IconStyle.Default, strokeWidth))
            drawPath(lens, color = tint, style = iconStroke(IconStyle.Default, strokeWidth))
            drawPath(viewfinder, color = tint, style = iconStroke(IconStyle.Default, strokeWidth))
        }
        else -> {
            drawPath(body, color = tint, style = iconStroke(style, strokeWidth))
            drawPath(lens, color = tint, style = iconStroke(style, strokeWidth))
            drawPath(viewfinder, color = tint, style = iconStroke(style, strokeWidth))
        }
    }
}

// ─── Image ────────────────────────────────────────────────────────────────────

internal fun DrawScope.drawImageIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val r = cornerRadius(style, 2f)
    val frame = roundRectPath(3f, 4f, 18f, 16f, r)
    val sun = ellipsePath(8.2f, 9f, 1.8f, 1.8f)
    val mountain = Path().apply {
        moveTo(d(4.5f), d(18.5f))
        lineTo(d(10f), d(11f))
        lineTo(d(13.5f), d(15f))
        lineTo(d(16f), d(12f))
        lineTo(d(20.5f), d(18.5f))
        close()
    }
    val innerDetail = Path().apply { addPath(sun); addPath(mountain) }
    when (style) {
        IconStyle.Filled -> drawPath(frame.withHole(innerDetail), color = tint, style = Fill)
        IconStyle.TwoTone -> {
            drawPath(frame.withHole(innerDetail), color = tint.copy(alpha = tint.alpha * 0.28f), style = Fill)
            drawPath(frame, color = tint, style = iconStroke(IconStyle.Default, strokeWidth))
            drawPath(sun, color = tint, style = iconStroke(IconStyle.Default, strokeWidth))
            drawPath(mountain, color = tint, style = iconStroke(IconStyle.Default, strokeWidth))
        }
        else -> {
            drawPath(frame, color = tint, style = iconStroke(style, strokeWidth))
            drawPath(sun, color = tint, style = iconStroke(style, strokeWidth))
            drawPath(mountain, color = tint, style = iconStroke(style, strokeWidth))
        }
    }
}

// ─── Folder ───────────────────────────────────────────────────────────────────

internal fun DrawScope.drawFolderIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val r = cornerRadius(style, 1.5f)
    val folder = roundedPolygonPath(
        listOf(3f to 7f, 3f to 5f, 9f to 5f, 11f to 7f, 21f to 7f, 21f to 19f, 3f to 19f),
        r
    )
    paintIcon(folder, style, tint, strokeWidth)
}

// ─── Download ─────────────────────────────────────────────────────────────────

internal fun DrawScope.drawDownloadIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val r = cornerRadius(style, 1f)
    val arrow = roundedPolygonPath(
        listOf(10.5f to 3.5f, 13.5f to 3.5f, 13.5f to 11.5f, 17f to 11.5f, 12f to 17f, 7f to 11.5f, 10.5f to 11.5f),
        r
    )
    val tray = roundRectPath(4f, 19f, 16f, 2.2f, cornerRadius(style, 1f))
    paintIcon(arrow, style, tint, strokeWidth)
    paintIcon(tray, style, tint, strokeWidth)
}

// ─── Upload ───────────────────────────────────────────────────────────────────

internal fun DrawScope.drawUploadIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val r = cornerRadius(style, 1f)
    val arrow = roundedPolygonPath(
        listOf(10.5f to 17f, 13.5f to 17f, 13.5f to 9f, 17f to 9f, 12f to 3.5f, 7f to 9f, 10.5f to 9f),
        r
    )
    val tray = roundRectPath(4f, 19f, 16f, 2.2f, cornerRadius(style, 1f))
    paintIcon(arrow, style, tint, strokeWidth)
    paintIcon(tray, style, tint, strokeWidth)
}

// ─── Share ────────────────────────────────────────────────────────────────────

internal fun DrawScope.drawShareIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val nodeR = 2.4f
    val left = 6f to 12f
    val topRight = 18f to 6f
    val bottomRight = 18f to 18f
    val lines = Path().apply {
        moveTo(d(left.first), d(left.second)); lineTo(d(topRight.first), d(topRight.second))
        moveTo(d(left.first), d(left.second)); lineTo(d(bottomRight.first), d(bottomRight.second))
    }
    drawPath(lines, color = tint, style = iconStroke(style, strokeWidth))
    solidDot(left.first, left.second, nodeR, tint)
    solidDot(topRight.first, topRight.second, nodeR, tint)
    solidDot(bottomRight.first, bottomRight.second, nodeR, tint)
}

// ─── Lock ─────────────────────────────────────────────────────────────────────

internal fun DrawScope.drawLockIcon(style: IconStyle, tint: Color, strokeWidth: Float) {
    val r = cornerRadius(style, 2f)
    val body = roundRectPath(4f, 10.5f, 16f, 11f, r)
    val shackle = Path().apply {
        moveTo(d(7f), d(10.5f))
        lineTo(d(7f), d(8f))
        cubicTo(d(7f), d(4.5f), d(9.5f), d(2.5f), d(12f), d(2.5f))
        cubicTo(d(14.5f), d(2.5f), d(17f), d(4.5f), d(17f), d(8f))
        lineTo(d(17f), d(10.5f))
    }
    val keyhole = Path().apply {
        addPath(ellipsePath(12f, 14.5f, 1.6f, 1.6f))
        addPath(
            Path().apply {
                moveTo(d(11.2f), d(15.5f)); lineTo(d(12.8f), d(15.5f)); lineTo(d(12.3f), d(19f)); lineTo(d(11.7f), d(19f)); close()
            }
        )
    }
    when (style) {
        IconStyle.Filled -> drawPath(body.withHole(keyhole), color = tint, style = Fill)
        IconStyle.TwoTone -> {
            drawPath(body.withHole(keyhole), color = tint.copy(alpha = tint.alpha * 0.28f), style = Fill)
            drawPath(body, color = tint, style = iconStroke(IconStyle.Default, strokeWidth))
        }
        else -> drawPath(body, color = tint, style = iconStroke(style, strokeWidth))
    }
    drawPath(shackle, color = tint, style = iconStroke(style, strokeWidth))
}
