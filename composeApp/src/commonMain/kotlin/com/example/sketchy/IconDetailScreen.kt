package com.example.sketchy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sketchy.ui.theme.SketchyGold
import com.example.sketchy.ui.theme.SketchyCream
import com.example.sketchy.ui.theme.SketchyInk
import com.sketchy.library.icons.Icon
import com.sketchy.library.icons.IconStyle
import com.sketchy.library.icons.SketchyIcon

/**
 * A single [Icon] with a style picker beneath it — swipeable across every other icon in the same
 * [Icon.category], mirroring [EmptyStateDetailScreen]'s family pager.
 */
@Composable
fun IconDetailScreen(
    icon: Icon,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val familyIcons = remember(icon.category) {
        Icon.entries.filter { it.category == icon.category }
    }
    val startPage = remember(icon) { familyIcons.indexOf(icon).coerceAtLeast(0) }
    val pagerState = rememberPagerState(initialPage = startPage) { familyIcons.size }
    val current = familyIcons[pagerState.currentPage]

    var style by remember { mutableStateOf(IconStyle.Default) }
    var strokeWidth by remember { mutableFloatStateOf(1f) }

    Column(modifier = modifier.fillMaxSize()) {
        DetailHeader(title = current.displayName, subtitle = current.category, onBack = onBack)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { page ->
                Card(
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = SketchyCream),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        SketchyIcon(
                            icon = familyIcons[page],
                            style = style,
                            tint = SketchyInk,
                            strokeWidth = strokeWidth,
                            modifier = Modifier.size(120.dp)
                        )
                    }
                }
            }
            if (familyIcons.size > 1) {
                PagerDots(
                    count = familyIcons.size,
                    current = pagerState.currentPage,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
            IconStylePicker(
                selected = style,
                onSelect = { style = it },
                modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Stroke Width",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "  ${(strokeWidth * 10).toInt() / 10f}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Slider(
                value = strokeWidth,
                onValueChange = { strokeWidth = it },
                valueRange = 0.4f..3f,
                colors = SliderDefaults.colors(thumbColor = SketchyGold, activeTrackColor = SketchyGold)
            )
            CodeSnippetCard(
                code = "SketchyIcon(\n    icon = Icon.${current.name},\n    style = IconStyle.${style.name},\n    strokeWidth = ${(strokeWidth * 10).toInt() / 10f}f\n)",
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}
