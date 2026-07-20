package com.example.sketchy.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sketchy.ui.theme.SketchyCream
import com.example.sketchy.ui.theme.SketchyGold
import com.example.sketchy.ui.theme.SketchyInk
import com.example.sketchy.ui.theme.SketchyTeal
import com.sketchy.library.EmptyState
import com.sketchy.library.Sketch
import com.sketchy.library.SketchyEmptyState
import com.sketchy.library.SketchyIllustration

@Composable
fun SketchyGalleryScreen(query: String, onSelect: (Sketch) -> Unit, modifier: Modifier = Modifier) {
    val filtered = Sketch.entries.filter { it.matches(query) }
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
            items(items, key = { it.name }) { sketch ->
                SketchyCard(sketch = sketch, onClick = { onSelect(sketch) })
            }
        }
    }
}

private fun Sketch.matches(query: String) =
    query.isBlank() || displayName.contains(query, ignoreCase = true) || category.contains(query, ignoreCase = true)

private fun EmptyState.matches(query: String) =
    query.isBlank() || defaultTitle.contains(query, ignoreCase = true) || category.contains(query, ignoreCase = true)

@Composable
private fun CategoryHeader(category: String, count: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(top = 8.dp, bottom = 2.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = category.uppercase(),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.8.sp,
            color = SketchyTeal
        )
        Text(
            text = "$count",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun NoResultsHint(query: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No matches for \"$query\"",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "Try a different name or category.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun SketchyCard(sketch: Sketch, onClick: () -> Unit, modifier: Modifier = Modifier) {
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
            SketchyIllustration(
                sketch = sketch,
                modifier = Modifier.size(140.dp)
            )
        }
        Text(
            text = sketch.displayName,
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

@Composable
fun SketchyDetailScreen(
    sketch: Sketch,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var animate by remember { mutableStateOf(true) }

    Column(modifier = modifier.fillMaxSize()) {
        DetailHeader(title = sketch.displayName, subtitle = sketch.category, onBack = onBack)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = SketchyCream),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    SketchyIllustration(
                        sketch = sketch,
                        animate = animate,
                        modifier = Modifier.size(260.dp)
                    )
                }
            }
            AnimateToggleRow(animate = animate, onAnimateChange = { animate = it })
            CodeSnippetCard(
                code = "SketchyIllustration(\n    sketch = Sketch.${sketch.name}\n)",
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}

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

@Composable
fun EmptyStateDetailScreen(
    state: EmptyState,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var animate by remember { mutableStateOf(true) }

    Column(modifier = modifier.fillMaxSize()) {
        DetailHeader(title = state.defaultTitle, subtitle = state.category, onBack = onBack)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = SketchyCream),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    SketchyEmptyState(
                        state = state,
                        animate = animate,
                        illustrationSize = 220.dp
                    )
                }
            }
            AnimateToggleRow(animate = animate, onAnimateChange = { animate = it })
            CodeSnippetCard(
                code = "SketchyEmptyState(\n    state = EmptyState.${state.name}\n)",
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}

/** A small hand-drawn chevron, in keeping with the rest of the sketched artwork. */
@Composable
private fun BackArrow(tint: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(20.dp)) {
        val path = Path().apply {
            moveTo(size.width * 0.64f, size.height * 0.1f)
            lineTo(size.width * 0.2f, size.height * 0.5f)
            lineTo(size.width * 0.64f, size.height * 0.9f)
        }
        drawPath(
            path = path,
            color = tint,
            style = Stroke(width = 2.2.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
    }
}

/** A compact back button + title/subtitle header — tighter than Material3's default [androidx.compose.material3.TopAppBar]. */
@Composable
private fun DetailHeader(title: String, subtitle: String, onBack: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(start = 4.dp, end = 20.dp, top = 4.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            BackArrow(tint = MaterialTheme.colorScheme.onBackground)
        }
        Column(modifier = Modifier.padding(start = 4.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Text(
                subtitle,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun AnimateToggleRow(animate: Boolean, onAnimateChange: (Boolean) -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Animate", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
        Switch(
            checked = animate,
            onCheckedChange = onAnimateChange,
            colors = SwitchDefaults.colors(checkedTrackColor = SketchyGold, checkedThumbColor = SketchyInk)
        )
    }
}
