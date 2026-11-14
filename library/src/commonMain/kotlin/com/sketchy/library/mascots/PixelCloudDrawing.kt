package com.sketchy.library.mascots

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.isSpecified
import com.sketchy.library.model.PixelCloudMood

internal const val PixelCloudGridWidth = 53f
internal const val PixelCloudGridHeight = 30f
internal const val PixelCloudRainGridHeight = 38f
internal const val PixelCloudWalkRange = 8f
internal const val PixelCloudStepMillis = 140f
internal const val PixelCloudPauseMillis = 1500L
internal const val PixelCloudThoughtBubbleCount = 3f

private const val BodyLeft = 6
private const val BodyTop = 2
private const val ShadedRightColumns = 2
private const val RainTop = 26
private const val RainFall = 10

private val BodyColumnTops = intArrayOf(
    15, 13, 11, 9, 8, 7, 7, 7, 8, 9,
    6, 5, 4, 3, 3, 2, 2, 2, 2, 2, 2, 3, 3, 4, 5, 6,
    8, 7, 6, 6, 6, 7, 8, 10, 12, 15,
)

private val BodyColumnBottoms = IntArray(BodyColumnTops.size) { 23 }.apply {
    this[0] = 20
    this[1] = 22
    this[lastIndex - 1] = 22
    this[lastIndex] = 20
}

private val BodyColumns = BodyColumnTops.indices.map { i ->
    gridRect(BodyLeft + i, BodyTop + BodyColumnTops[i], 1, BodyColumnBottoms[i] - BodyColumnTops[i] + 1)
}

private val ShadeCells = BodyColumnTops.indices.map { i ->
    val x = BodyLeft + i
    val top = BodyTop + BodyColumnTops[i]
    val bottom = BodyTop + BodyColumnBottoms[i]
    if (i >= BodyColumnTops.size - ShadedRightColumns) gridRect(x, top + 1, 1, bottom - top) else gridRect(x, bottom - 1, 1, 2)
}

private val LeftArm = listOf(gridRect(4, 18, 2, 2), gridRect(3, 20, 2, 2))
private val RightArmResting = listOf(gridRect(42, 18, 2, 2), gridRect(43, 20, 2, 2))
private val RightArmRaised = listOf(gridRect(42, 19, 2, 2), gridRect(43, 17, 2, 2), gridRect(44, 15, 2, 2), gridRect(45, 12, 3, 3))
private val RightShoulder = Offset(42f, 21f)

private val OpenEyes = listOf(gridRect(18, 13, 2, 4), gridRect(28, 13, 2, 4))
private val LookingUpEyes = listOf(gridRect(19, 12, 2, 3), gridRect(29, 12, 2, 3))
private val CrossedEyes = listOf(17, 28).flatMap { x ->
    listOf(gridRect(x, 13, 1, 1), gridRect(x + 2, 13, 1, 1), gridRect(x + 1, 14, 1, 1), gridRect(x, 15, 1, 1), gridRect(x + 2, 15, 1, 1))
}
private val ClosedCrossedEyes = listOf(gridRect(17, 14, 3, 1), gridRect(28, 14, 3, 1))
private val SleepyEyes = listOf(gridRect(18, 15, 2, 1), gridRect(28, 15, 2, 1))
private val SmileMouth = listOf(gridRect(22, 18, 1, 1), gridRect(23, 19, 2, 1), gridRect(25, 18, 1, 1))
private val FlatMouth = listOf(gridRect(24, 19, 2, 1))
private val FrownMouth = listOf(gridRect(22, 20, 1, 1), gridRect(23, 19, 2, 1), gridRect(25, 20, 1, 1))
private val Blush = listOf(gridRect(14, 18, 3, 2), gridRect(31, 18, 3, 2))

private val Sparkles = listOf(gridRect(49, 10, 1, 1), gridRect(50, 9, 1, 1), gridRect(50, 13, 2, 1))
private val ThoughtBubbles = listOf(gridRect(44, 11, 2, 2), gridRect(47, 6, 2, 2), gridRect(51, 1, 3, 3))
private val AlertMark = listOf(gridRect(47, 4, 2, 6), gridRect(47, 11, 2, 2))
private val GroundShadow = listOf(gridRect(12, 28, 30, 1), gridRect(15, 29, 24, 1))
private val Puddle = listOf(gridRect(12, 37, 30, 1))
private val RainColumns = listOf(11, 16, 21, 26, 31, 36)

private fun gridRect(x: Int, y: Int, w: Int, h: Int) = Rect(x.toFloat(), y.toFloat(), (x + w).toFloat(), (y + h).toFloat())

internal fun DrawScope.drawPixelCloudGroundShadow(cell: Float) =
    drawPixelCells(GroundShadow, PixelCloudGroundShadow, cell)

internal fun DrawScope.drawPixelCloudPuddle(cell: Float) =
    drawPixelCells(Puddle, PixelCloudPuddle, cell)

internal fun DrawScope.drawPixelCloudRain(cell: Float, phase: Float) {
    val drops = RainColumns.mapIndexed { i, x ->
        val progress = (phase + i * 0.37f) % 1f
        gridRect(x, RainTop + (progress * RainFall).toInt(), 1, 2)
    }
    drawPixelCells(drops, PixelCloudRain, cell)
}

internal fun DrawScope.drawPixelCloudBodyOutline(cell: Float, offset: Offset) =
    drawPixelCells(BodyColumns.map { it.inflate(1f) }, PixelCloudOutline, cell, offset)

internal fun DrawScope.drawPixelCloudBody(cell: Float, offset: Offset, color: Color) =
    drawPixelCells(BodyColumns, color, cell, offset)

internal fun DrawScope.drawPixelCloudShading(cell: Float, offset: Offset, color: Color) =
    drawPixelCells(ShadeCells, color, cell, offset)

internal fun DrawScope.drawPixelCloudLeftArmOutline(cell: Float, offset: Offset) =
    drawPixelCells(LeftArm.map { it.inflate(1f) }, PixelCloudOutline, cell, offset)

internal fun DrawScope.drawPixelCloudLeftArm(cell: Float, offset: Offset, color: Color) =
    drawPixelCells(LeftArm, color, cell, offset)

internal fun DrawScope.drawPixelCloudRightArmOutline(cell: Float, offset: Offset, raised: Boolean, rotation: Float) =
    rotate(if (raised) rotation else 0f, pivot = (RightShoulder + offset) * cell) {
        drawPixelCells(pixelCloudRightArm(raised).map { it.inflate(1f) }, PixelCloudOutline, cell, offset)
    }

internal fun DrawScope.drawPixelCloudRightArm(cell: Float, offset: Offset, raised: Boolean, rotation: Float, color: Color) =
    rotate(if (raised) rotation else 0f, pivot = (RightShoulder + offset) * cell) {
        drawPixelCells(pixelCloudRightArm(raised), color, cell, offset)
    }

internal fun DrawScope.drawPixelCloudFace(cell: Float, offset: Offset, mood: PixelCloudMood, eyeScale: Float) {
    val eyes = when (mood) {
        PixelCloudMood.Default -> OpenEyes.map { it.scaledVertically(eyeScale) }
        PixelCloudMood.Thinking -> LookingUpEyes.map { it.scaledVertically(eyeScale) }
        PixelCloudMood.Error -> if (eyeScale < 0.5f) ClosedCrossedEyes else CrossedEyes
        PixelCloudMood.Disabled -> SleepyEyes
    }
    val mouth = when (mood) {
        PixelCloudMood.Default -> SmileMouth
        PixelCloudMood.Thinking -> FlatMouth
        PixelCloudMood.Error, PixelCloudMood.Disabled -> FrownMouth
    }
    drawPixelCells(eyes, PixelCloudOutline, cell, offset)
    drawPixelCells(mouth, PixelCloudOutline, cell, offset)
    if (mood.blushColor.isSpecified) drawPixelCells(Blush, mood.blushColor, cell, offset)
}

internal fun DrawScope.drawPixelCloudSparkles(cell: Float, offset: Offset) =
    drawPixelCells(Sparkles, PixelCloudSparkle, cell, offset)

internal fun DrawScope.drawPixelCloudThoughtBubbles(cell: Float, offset: Offset, visible: Int, color: Color) {
    val bubbles = ThoughtBubbles.take(visible.coerceIn(0, ThoughtBubbles.size))
    drawPixelCells(bubbles.map { it.inflate(1f) }, PixelCloudOutline, cell, offset)
    drawPixelCells(bubbles, color, cell, offset)
}

internal fun DrawScope.drawPixelCloudAlertMark(cell: Float, offset: Offset) =
    drawPixelCells(AlertMark, PixelCloudErrorShade, cell, offset)

private fun pixelCloudRightArm(raised: Boolean) = if (raised) RightArmRaised else RightArmResting

private fun Rect.scaledVertically(scale: Float): Rect {
    val half = height * scale.coerceIn(0f, 1f) / 2f
    return Rect(left, center.y - half, right, center.y + half)
}

private fun DrawScope.drawPixelCells(cells: List<Rect>, color: Color, cell: Float, offset: Offset = Offset.Zero) =
    cells.forEach { r ->
        drawRect(
            color = color,
            topLeft = Offset((r.left + offset.x) * cell, (r.top + offset.y) * cell),
            size = Size(r.width * cell, r.height * cell)
        )
    }
