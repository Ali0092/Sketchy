package com.example.sketchy

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.sketchy.ui.theme.SketchyCream
import com.example.sketchy.ui.theme.SketchyInk
import com.sketchy.library.emptystates.EmptyState
import com.sketchy.library.emptystates.SketchyEmptyState

@Composable
fun EmptyStateGalleryScreen(query: String, onSelect: (EmptyState) -> Unit, modifier: Modifier = Modifier) {
    val filtered = EmptyState.entries.filter { it.matches(query) }
    val grouped = filtered.groupBy { it.category }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        if (grouped.isEmpty()) {
            item(span = { GridItemSpan(maxLineSpan) }) { NoResultsHint(query) }
        }
        grouped.forEach { (category, items) ->
            item(span = { GridItemSpan(maxLineSpan) }, key = "header_$category") {
                CategoryHeader(category, items.size)
            }
            items(items, key = { it.name }) { state ->
                EmptyStateCard(state = state, onClick = { onSelect(state) })
            }
        }
    }
}

@Composable
private fun EmptyStateCard(state: EmptyState, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SketchyCream),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp, pressedElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            contentAlignment = Alignment.Center
        ) {
            SketchyEmptyState(
                state = state,
                illustrationSize = 130.dp,
                title = null,
                subtitle = null
            )
        }
        Text(
            text = state.defaultTitle,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold,
            color = SketchyInk,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 10.dp)
        )
    }
}
