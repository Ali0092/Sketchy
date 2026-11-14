package com.sketchy.library.mascots

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.translate
import com.sketchy.library.model.PixelCloudMood
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.roundToInt

@Composable
fun PixelCloudMascot(
    modifier: Modifier = Modifier,
    mood: PixelCloudMood = PixelCloudMood.Default,
    animate: Boolean = true,
    walking: Boolean = true,
    blinking: Boolean = true,
    waving: Boolean = true,
    moodEffects: Boolean = true,
    bodyOffset: Offset = Offset.Zero,
    leftArmOffset: Offset = Offset.Zero,
    rightArmOffset: Offset = Offset.Zero,
    waveRotation: Float = 0f,
    eyeScale: Float = 1f,
    bobOffset: Float = 0f,
    showGroundShadow: Boolean = true,
) {
    val walk = remember { Animatable(0f) }
    val wave = remember { Animatable(0f) }
    val blink = remember { Animatable(1f) }
    val thought = remember { Animatable(PixelCloudThoughtBubbleCount) }
    val shake = remember { Animatable(0f) }
    val rain = remember { Animatable(0f) }

    LaunchedEffect(animate, walking, waving, mood) {
        walk.snapTo(0f)
        wave.snapTo(0f)
        val waves = waving && mood == PixelCloudMood.Default
        if (!animate || (!walking && !waves)) return@LaunchedEffect
        while (true) {
            for (target in listOf(PixelCloudWalkRange, 0f, -PixelCloudWalkRange, 0f)) {
                if (walking) {
                    val duration = (abs(target - walk.value) * PixelCloudStepMillis).toInt()
                    walk.animateTo(target, tween(duration, easing = LinearEasing))
                }
                delay(PixelCloudPauseMillis / 3)
                if (waves) {
                    repeat(2) {
                        wave.animateTo(1f, tween(220, easing = FastOutSlowInEasing))
                        wave.animateTo(0f, tween(220, easing = FastOutSlowInEasing))
                    }
                }
                delay(PixelCloudPauseMillis / 3)
            }
        }
    }
    LaunchedEffect(animate, moodEffects, mood) {
        shake.snapTo(0f)
        thought.snapTo(PixelCloudThoughtBubbleCount)
        rain.snapTo(0.5f)
        if (!animate || !moodEffects) return@LaunchedEffect
        when (mood) {
            PixelCloudMood.Default -> Unit
            PixelCloudMood.Thinking -> while (true) {
                thought.snapTo(0f)
                thought.animateTo(PixelCloudThoughtBubbleCount + 0.99f, tween(1800, easing = LinearEasing))
                delay(900)
            }
            PixelCloudMood.Error -> while (true) {
                delay(1400)
                listOf(-1f, 1f, -1f, 1f, 0f).forEach { x ->
                    shake.snapTo(x)
                    delay(60)
                }
            }
            PixelCloudMood.Disabled -> while (true) {
                rain.snapTo(0f)
                rain.animateTo(1f, tween(900, easing = LinearEasing))
            }
        }
    }
    LaunchedEffect(animate, blinking) {
        blink.snapTo(1f)
        if (!animate || !blinking) return@LaunchedEffect
        while (true) {
            delay(2800)
            blink.animateTo(0.15f, tween(70))
            blink.animateTo(1f, tween(90))
        }
    }

    val raining = mood == PixelCloudMood.Disabled
    val armRaised = mood == PixelCloudMood.Default
    val walkMargin = if (walking) PixelCloudWalkRange else 0f
    val gridWidth = PixelCloudGridWidth + walkMargin * 2f
    val gridHeight = if (raining) PixelCloudRainGridHeight else PixelCloudGridHeight
    val shift = walk.value.roundToInt() + shake.value
    val armRotation = waveRotation + wave.value * 22f

    Canvas(modifier = modifier.aspectRatio(gridWidth / gridHeight)) {
        val exact = minOf(size.width / gridWidth, size.height / gridHeight)
        val cell = if (exact >= 2f) floor(exact) else exact
        val left = floor((size.width - cell * gridWidth) / 2f) + (walkMargin + shift) * cell
        val top = floor((size.height - cell * gridHeight) / 2f)
        translate(left, top) {
            if (raining) {
                drawPixelCloudPuddle(cell)
                drawPixelCloudRain(cell, rain.value)
            } else if (showGroundShadow) {
                drawPixelCloudGroundShadow(cell)
            }
            translate(0f, bobOffset * cell) {
                drawPixelCloudLeftArmOutline(cell, bodyOffset + leftArmOffset)
                drawPixelCloudRightArmOutline(cell, bodyOffset + rightArmOffset, armRaised, armRotation)
                drawPixelCloudBodyOutline(cell, bodyOffset)
                drawPixelCloudBody(cell, bodyOffset, mood.bodyColor)
                drawPixelCloudShading(cell, bodyOffset, mood.shadeColor)
                drawPixelCloudLeftArm(cell, bodyOffset + leftArmOffset, mood.bodyColor)
                drawPixelCloudRightArm(cell, bodyOffset + rightArmOffset, armRaised, armRotation, mood.bodyColor)
                drawPixelCloudFace(cell, bodyOffset, mood, eyeScale * blink.value)
                when (mood) {
                    PixelCloudMood.Default -> drawPixelCloudSparkles(cell, bodyOffset + rightArmOffset)
                    PixelCloudMood.Thinking -> drawPixelCloudThoughtBubbles(cell, bodyOffset, thought.value.toInt(), mood.bodyColor)
                    PixelCloudMood.Error -> drawPixelCloudAlertMark(cell, bodyOffset)
                    PixelCloudMood.Disabled -> Unit
                }
            }
        }
    }
}
