package com.example.sketchy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.sketchy.ui.theme.SketchyCream
import com.example.sketchy.ui.theme.SketchyGold
import com.example.sketchy.ui.theme.SketchyInk
import com.sketchy.library.emptystates.EmptyState
import com.sketchy.library.emptystates.SketchyEmptyState

/**
 * A single [EmptyState] with an "Animate"/"Colourful" toggle and a code
 * snippet beneath it — but swipeable: every other state in the same
 * [EmptyState.category] is a horizontal-pager page away, so browsing a
 * family (all the Lined Man moods, every Signboard sign, ...) never means
 * going back to the grid and tapping the next card.
 */
@Composable
fun EmptyStateDetailScreen(
    state: EmptyState,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val familyStates = remember(state.category) {
        EmptyState.entries.filter { it.category == state.category }
    }
    val startPage = remember(state) { familyStates.indexOf(state).coerceAtLeast(0) }
    val pagerState = rememberPagerState(initialPage = startPage) { familyStates.size }
    val current = familyStates[pagerState.currentPage]

    var animate by remember { mutableStateOf(true) }
    var colorful by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxSize()) {
        DetailHeader(title = current.defaultTitle, subtitle = current.category, onBack = onBack)
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
                        SketchyEmptyState(
                            state = familyStates[page],
                            animate = animate,
                            colorful = colorful,
                            illustrationSize = 220.dp
                        )
                    }
                }
            }
            if (familyStates.size > 1) {
                PagerDots(
                    count = familyStates.size,
                    current = pagerState.currentPage,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
            SketchyToggleRow(label = "Animate", checked = animate, onCheckedChange = { animate = it })
            SketchyToggleRow(label = "Colourful", checked = colorful, onCheckedChange = { colorful = it })
            CodeSnippetCard(
                code = "SketchyEmptyState(\n    state = EmptyState.${current.name}\n)",
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}

/** A row of small dots marking [current] out of [count] — swipe progress for the pager above. */
@Composable
private fun PagerDots(count: Int, current: Int, modifier: Modifier = Modifier) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        repeat(count) { i ->
            val active = i == current
            Box(
                modifier = Modifier
                    .size(if (active) 8.dp else 6.dp)
                    .clip(CircleShape)
                    .background(if (active) SketchyGold else SketchyInk.copy(alpha = 0.18f))
            )
        }
    }
}