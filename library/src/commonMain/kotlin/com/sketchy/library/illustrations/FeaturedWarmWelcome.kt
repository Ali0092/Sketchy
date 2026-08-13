package com.sketchy.library.illustrations

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import com.sketchy.library.SketchyStyle
import com.sketchy.library.utils.*

// ─── A Warm Welcome ────────────────────────────────────────────────────────
//   A single standing figure, greeting hands held together, swaying gently in
//   place. Nothing else on the canvas — the scene is the character himself, a
//   contact shadow and dashed ground line to keep him planted, and a couple
//   of sparkles either side of his head for warmth.

internal fun DrawScope.drawWarmWelcomeScene(t: Float, colors: SketchyStyle) {
    val sway = 2f * wave(t, 0f)
    val pivot = pt(160f, 298f)

    contactShadow(160f, 300f, 38f, 8f, colors.shade)

    withTransform({ rotate(degrees = sway, pivot = pivot) }) {
        // ── Right arm, behind the torso ──────────────────────────────────
        val rightArm = Path().apply {
            moveTo(d(197.8f), d(149.9f))
            cubicTo(d(207.6f), d(154.1f), d(214.6f), d(162.5f), d(216.0f), d(172.3f))
            cubicTo(d(217.4f), d(180.7f), d(214.6f), d(187.7f), d(209.0f), d(191.9f))
            cubicTo(d(204.8f), d(194.7f), d(200.6f), d(193.3f), d(199.2f), d(189.1f))
            cubicTo(d(197.8f), d(180.7f), d(196.4f), d(168.1f), d(195.0f), d(156.9f))
            cubicTo(d(194.3f), d(152.7f), d(195.7f), d(149.9f), d(197.8f), d(149.9f))
            close()
        }
        paint(rightArm, colors.skin, colors.ink, 2.2f)
        val rightHand = Path().apply {
            moveTo(d(197.1f), d(197.5f))
            cubicTo(d(195.7f), d(194.0f), d(199.2f), d(191.9f), d(202.0f), d(193.3f))
            cubicTo(d(204.8f), d(190.5f), d(209.0f), d(191.9f), d(209.7f), d(195.4f))
            cubicTo(d(213.2f), d(197.5f), d(213.2f), d(201.7f), d(211.1f), d(205.2f))
            cubicTo(d(211.8f), d(208.0f), d(209.0f), d(211.5f), d(204.8f), d(211.5f))
            cubicTo(d(199.9f), d(212.9f), d(195.7f), d(210.1f), d(195.0f), d(205.9f))
            cubicTo(d(192.2f), d(203.1f), d(193.6f), d(198.2f), d(197.1f), d(197.5f))
            close()
        }
        paint(rightHand, colors.skin, colors.ink, 2f)
        val rightThumb = Path().apply {
            moveTo(d(193.6f), d(194.7f))
            cubicTo(d(190.8f), d(190.5f), d(189.4f), d(184.9f), d(193.6f), d(182.1f))
            cubicTo(d(197.8f), d(180.7f), d(200.6f), d(184.9f), d(199.2f), d(189.1f))
            cubicTo(d(197.8f), d(191.9f), d(195.0f), d(194.7f), d(193.6f), d(194.7f))
            close()
        }
        paint(rightThumb, colors.skin, colors.ink, 1.8f)
        val rightCrease1 = Path().apply {
            moveTo(d(197.1f), d(194.7f))
            cubicTo(d(199.9f), d(196.8f), d(203.4f), d(196.8f), d(206.2f), d(194.7f))
        }
        drawPath(rightCrease1, color = colors.ink.a(0.6f), style = thin(1.3f))
        val rightCrease2 = Path().apply {
            moveTo(d(196.4f), d(205.2f))
            cubicTo(d(200.6f), d(207.3f), d(206.2f), d(206.6f), d(209.7f), d(204.5f))
        }
        drawPath(rightCrease2, color = colors.ink.a(0.4f), style = thin(1.2f))

        // ── Left arm, behind the torso ───────────────────────────────────
        val leftArm = Path().apply {
            moveTo(d(122.2f), d(149.9f))
            cubicTo(d(112.4f), d(154.1f), d(105.4f), d(162.5f), d(104.0f), d(172.3f))
            cubicTo(d(102.6f), d(180.7f), d(105.4f), d(187.7f), d(111.0f), d(191.9f))
            cubicTo(d(115.2f), d(194.7f), d(119.4f), d(193.3f), d(120.8f), d(189.1f))
            cubicTo(d(122.2f), d(180.7f), d(123.6f), d(168.1f), d(125.0f), d(156.9f))
            cubicTo(d(125.7f), d(152.7f), d(124.3f), d(149.9f), d(122.2f), d(149.9f))
            close()
        }
        paint(leftArm, colors.skin, colors.ink, 2.2f)
        val leftHand = Path().apply {
            moveTo(d(122.9f), d(197.5f))
            cubicTo(d(124.3f), d(194.0f), d(120.8f), d(191.9f), d(118.0f), d(193.3f))
            cubicTo(d(115.2f), d(190.5f), d(111.0f), d(191.9f), d(110.3f), d(195.4f))
            cubicTo(d(106.8f), d(197.5f), d(106.8f), d(201.7f), d(108.9f), d(205.2f))
            cubicTo(d(108.2f), d(208.0f), d(111.0f), d(211.5f), d(115.2f), d(211.5f))
            cubicTo(d(120.1f), d(212.9f), d(124.3f), d(210.1f), d(125.0f), d(205.9f))
            cubicTo(d(127.8f), d(203.1f), d(126.4f), d(198.2f), d(122.9f), d(197.5f))
            close()
        }
        paint(leftHand, colors.skin, colors.ink, 2f)
        val leftCrease1 = Path().apply {
            moveTo(d(122.9f), d(194.7f))
            cubicTo(d(120.1f), d(196.8f), d(116.6f), d(196.8f), d(113.8f), d(194.7f))
        }
        drawPath(leftCrease1, color = colors.ink.a(0.6f), style = thin(1.3f))
        val leftCrease2 = Path().apply {
            moveTo(d(123.6f), d(205.2f))
            cubicTo(d(119.4f), d(207.3f), d(113.8f), d(206.6f), d(110.3f), d(204.5f))
        }
        drawPath(leftCrease2, color = colors.ink.a(0.4f), style = thin(1.2f))

        // ── Torso / shirt ─────────────────────────────────────────────────
        val torso = Path().apply {
            moveTo(d(118.0f), d(162.5f))
            cubicTo(d(116.6f), d(151.3f), d(125.0f), d(142.9f), d(134.8f), d(142.2f))
            cubicTo(d(140.4f), d(140.1f), d(147.4f), d(138.7f), d(151.6f), d(138.7f))
            lineTo(d(168.4f), d(138.7f))
            cubicTo(d(172.6f), d(138.7f), d(179.6f), d(140.1f), d(185.2f), d(142.2f))
            cubicTo(d(195.0f), d(142.9f), d(203.4f), d(151.3f), d(202.0f), d(162.5f))
            lineTo(d(193.6f), d(217.1f))
            cubicTo(d(193.6f), d(224.1f), d(188.0f), d(229.0f), d(179.6f), d(229.0f))
            lineTo(d(140.4f), d(229.0f))
            cubicTo(d(132.0f), d(229.0f), d(126.4f), d(224.1f), d(126.4f), d(217.1f))
            close()
        }
        inkShadow(torso, colors.outlineShadow)
        paint(torso, colors.terracotta, colors.ink, 2.4f)
        val torsoShade = Path().apply {
            moveTo(d(170.5f), d(144.3f))
            lineTo(d(186.6f), d(152.0f))
            lineTo(d(190.8f), d(215.7f))
            cubicTo(d(190.8f), d(221.3f), d(186.6f), d(225.5f), d(181.0f), d(225.5f))
            lineTo(d(171.2f), d(225.5f))
            cubicTo(d(169.8f), d(194.7f), d(169.8f), d(169.5f), d(170.5f), d(144.3f))
            close()
        }
        fill(torsoShade, colors.clay)
        val neckline = Path().apply {
            moveTo(d(149.5f), d(143.6f))
            cubicTo(d(153.7f), d(149.9f), d(166.3f), d(149.9f), d(170.5f), d(143.6f))
        }
        drawPath(neckline, color = colors.hint(colors.clay.a(0.7f)), style = thin(1.5f))
        val shoulderR = Path().apply {
            moveTo(d(184.5f), d(154.1f))
            cubicTo(d(189.4f), d(157.6f), d(195.0f), d(157.6f), d(199.2f), d(153.4f))
        }
        drawPath(shoulderR, color = colors.hint(colors.clay.a(0.7f)), style = thin(1.4f))
        val shoulderL = Path().apply {
            moveTo(d(135.5f), d(154.1f))
            cubicTo(d(130.6f), d(157.6f), d(125.0f), d(157.6f), d(120.8f), d(153.4f))
        }
        drawPath(shoulderL, color = colors.hint(colors.clay.a(0.7f)), style = thin(1.4f))

        // ── Trousers ─────────────────────────────────────────────────────
        val trousers = Path().apply {
            moveTo(d(129.2f), d(229.0f))
            lineTo(d(190.8f), d(229.0f))
            lineTo(d(195.0f), d(288.5f))
            cubicTo(d(195.7f), d(294.8f), d(190.8f), d(298.3f), d(183.8f), d(298.3f))
            cubicTo(d(177.5f), d(298.3f), d(173.3f), d(294.8f), d(171.9f), d(287.1f))
            lineTo(d(169.8f), d(254.9f))
            cubicTo(d(168.4f), d(249.3f), d(165.6f), d(245.1f), d(161.4f), d(242.3f))
            lineTo(d(158.6f), d(242.3f))
            cubicTo(d(154.4f), d(245.1f), d(151.6f), d(249.3f), d(150.2f), d(254.9f))
            lineTo(d(148.1f), d(287.1f))
            cubicTo(d(146.7f), d(294.8f), d(142.5f), d(298.3f), d(136.2f), d(298.3f))
            cubicTo(d(129.2f), d(298.3f), d(124.3f), d(294.8f), d(125.0f), d(288.5f))
            close()
        }
        paint(trousers, colors.fabric, colors.ink, 2.4f)
        val trousersShade = Path().apply {
            moveTo(d(171.9f), d(247.9f))
            lineTo(d(193.6f), d(250.7f))
            lineTo(d(190.8f), d(288.5f))
            cubicTo(d(190.1f), d(294.1f), d(185.2f), d(296.9f), d(179.6f), d(296.2f))
            lineTo(d(174.7f), d(285.7f))
            close()
        }
        fill(trousersShade, colors.fabricDark)
        val foldL = Path().apply {
            moveTo(d(139.0f), d(266.1f))
            cubicTo(d(142.5f), d(269.6f), d(147.4f), d(267.5f), d(151.6f), d(271.0f))
        }
        drawPath(foldL, color = colors.ink.a(0.3f), style = thin(1.2f))
        val foldR = Path().apply {
            moveTo(d(169.8f), d(269.6f))
            cubicTo(d(174.0f), d(273.1f), d(179.6f), d(271.0f), d(183.8f), d(274.5f))
        }
        drawPath(foldR, color = colors.ink.a(0.3f), style = thin(1.2f))

        // ── Shoes — the one splash of color an outlined figure keeps ──────
        val shoeL = Path().apply {
            moveTo(d(125.0f), d(289.9f))
            cubicTo(d(125.0f), d(285.7f), d(129.2f), d(282.9f), d(136.2f), d(282.9f))
            lineTo(d(144.6f), d(282.9f))
            cubicTo(d(150.2f), d(282.9f), d(154.4f), d(285.7f), d(154.4f), d(289.9f))
            cubicTo(d(154.4f), d(294.8f), d(149.5f), d(297.6f), d(140.4f), d(297.6f))
            cubicTo(d(130.6f), d(297.6f), d(125.0f), d(294.8f), d(125.0f), d(289.9f))
            close()
        }
        paint(shoeL, colors.accentRed, colors.ink, 2.2f)
        val shoeR = Path().apply {
            moveTo(d(165.6f), d(289.9f))
            cubicTo(d(165.6f), d(285.7f), d(169.8f), d(282.9f), d(176.8f), d(282.9f))
            lineTo(d(185.2f), d(282.9f))
            cubicTo(d(190.8f), d(282.9f), d(195.0f), d(285.7f), d(195.0f), d(289.9f))
            cubicTo(d(195.0f), d(294.8f), d(190.1f), d(297.6f), d(181.0f), d(297.6f))
            cubicTo(d(171.2f), d(297.6f), d(165.6f), d(294.8f), d(165.6f), d(289.9f))
            close()
        }
        paint(shoeR, colors.accentRed, colors.ink, 2.2f)

        // ── Neck ─────────────────────────────────────────────────────────
        val neck = Path().apply {
            moveTo(d(153.0f), d(141.5f))
            cubicTo(d(152.3f), d(146.4f), d(152.3f), d(152.7f), d(153.7f), d(156.9f))
            lineTo(d(166.3f), d(156.9f))
            cubicTo(d(167.7f), d(152.7f), d(167.7f), d(146.4f), d(167.0f), d(141.5f))
            close()
        }
        paint(neck, colors.skin, colors.ink, 2f)
        val neckShade = Path().apply {
            moveTo(d(155.8f), d(144.3f))
            cubicTo(d(158.6f), d(148.5f), d(161.4f), d(148.5f), d(164.2f), d(144.3f))
        }
        drawPath(neckShade, color = colors.hint(colors.skinDark), style = thin(1.4f))

        // ── Ears ─────────────────────────────────────────────────────────
        paint(ellipsePath(122.2f, 113.5f, 5.6f, 9.1f), colors.skin, colors.ink, 1.8f)
        paint(ellipsePath(197.8f, 113.5f, 5.6f, 9.1f), colors.skin, colors.ink, 1.8f)

        // ── Hair, behind the head ────────────────────────────────────────
        val hair = Path().apply {
            moveTo(d(123.6f), d(121.9f))
            cubicTo(d(120.8f), d(100.9f), d(123.6f), d(79.9f), d(137.6f), d(68.7f))
            cubicTo(d(144.6f), d(63.1f), d(151.6f), d(60.3f), d(157.2f), d(58.9f))
            cubicTo(d(160.0f), d(53.3f), d(162.8f), d(45.6f), d(160.0f), d(40.0f))
            cubicTo(d(165.6f), d(44.2f), d(169.8f), d(49.8f), d(167.0f), d(56.8f))
            cubicTo(d(171.2f), d(52.6f), d(176.8f), d(47.0f), d(175.4f), d(41.4f))
            cubicTo(d(181.0f), d(47.0f), d(185.2f), d(54.0f), d(181.0f), d(61.0f))
            cubicTo(d(188.0f), d(66.6f), d(195.0f), d(79.9f), d(196.4f), d(100.9f))
            cubicTo(d(197.8f), d(110.7f), d(196.4f), d(116.3f), d(195.0f), d(121.9f))
            cubicTo(d(192.2f), d(113.5f), d(185.2f), d(103.7f), d(176.8f), d(98.1f))
            cubicTo(d(167.7f), d(92.5f), d(164.2f), d(91.8f), d(160.0f), d(91.8f))
            cubicTo(d(155.8f), d(91.8f), d(150.2f), d(93.2f), d(141.8f), d(98.1f))
            cubicTo(d(132.0f), d(104.4f), d(126.4f), d(113.5f), d(123.6f), d(121.9f))
            close()
        }
        paint(hair, colors.hair, colors.ink, 2.2f)

        // ── Head ─────────────────────────────────────────────────────────
        paint(ellipsePath(160f, 110.7f, 35f, 39.2f), colors.skin, colors.ink, 2.4f)

        // ── Face ─────────────────────────────────────────────────────────
        val eyebrowL = Path().apply {
            moveTo(d(144.6f), d(100.2f))
            quadraticTo(d(151.6f), d(96.0f), d(157.9f), d(99.5f))
        }
        drawPath(eyebrowL, color = colors.ink, style = thin(2.2f))
        val eyebrowR = Path().apply {
            moveTo(d(162.1f), d(99.5f))
            quadraticTo(d(168.4f), d(96.0f), d(175.4f), d(100.2f))
        }
        drawPath(eyebrowR, color = colors.ink, style = thin(2.2f))

        fill(ellipsePath(149.5f, 113.5f, 3.5f, 4.9f), colors.ink)
        fill(ellipsePath(170.5f, 113.5f, 3.5f, 4.9f), colors.ink)
        fill(ellipsePath(150.9f, 111.4f, 0.9f, 0.9f), colors.touch(colors.paper, 0.85f))
        fill(ellipsePath(171.9f, 111.4f, 0.9f, 0.9f), colors.touch(colors.paper, 0.85f))

        val nose = Path().apply {
            moveTo(d(157.9f), d(119.1f))
            quadraticTo(d(160.0f), d(121.9f), d(162.1f), d(119.8f))
        }
        drawPath(nose, color = colors.hint(colors.skinDark), style = thin(1.6f))

        val mouth = Path().apply {
            moveTo(d(148.8f), d(128.9f))
            quadraticTo(d(160.0f), d(135.2f), d(171.2f), d(128.9f))
        }
        drawPath(mouth, color = colors.ink, style = thin(2.4f))
    }

    twinkle(85f, 75f, 4f, t, 0.3f, colors.touch(colors.sun))
    twinkle(238f, 95f, 3f, t, 0.75f, colors.touch(colors.paper, 0.85f))
    groundHint(306f, colors.inkFaint)
}
