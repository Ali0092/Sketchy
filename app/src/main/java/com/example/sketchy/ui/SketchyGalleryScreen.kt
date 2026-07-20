package com.example.sketchy.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.PaddingValues
import com.sketchy.library.EmptyState
import com.sketchy.library.Sketch
import com.sketchy.library.SketchyEmptyState
import com.sketchy.library.SketchyIllustration

/** Warm off-white page background the ink-and-accent sketches were drawn against. */
val SketchyPageBackground = Color(0xFFFCF8F2)

@Composable
fun SketchyGalleryScreen(onSelect: (Sketch) -> Unit, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        items(Sketch.entries, key = { it.name }) { sketch ->
            SketchyCard(sketch = sketch, onClick = { onSelect(sketch) })
        }
    }
}

@Composable
private fun SketchyCard(sketch: Sketch, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            contentAlignment = Alignment.Center
        ) {
            SketchyIllustration(
                sketch = sketch,
                modifier = Modifier.size(150.dp)
            )
        }
        Text(
            text = sketch.displayName,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 10.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SketchyDetailScreen(
    sketch: Sketch,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var animate by remember { mutableStateOf(true) }

    Column(modifier = modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(sketch.displayName) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Text(text = "←", style = MaterialTheme.typography.headlineSmall)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = SketchyPageBackground)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(SketchyPageBackground),
            contentAlignment = Alignment.Center
        ) {
            SketchyIllustration(
                sketch = sketch,
                animate = animate,
                modifier = Modifier.size(320.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SketchyPageBackground)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Animate", style = MaterialTheme.typography.bodyLarge)
            Switch(checked = animate, onCheckedChange = { animate = it })
        }
    }
}

@Composable
fun EmptyStateGalleryScreen(onSelect: (EmptyState) -> Unit, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        items(EmptyState.entries, key = { it.name }) { state ->
            EmptyStateCard(state = state, onClick = { onSelect(state) })
        }
    }
}

@Composable
private fun EmptyStateCard(state: EmptyState, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
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
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 10.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmptyStateDetailScreen(
    state: EmptyState,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var animate by remember { mutableStateOf(true) }

    Column(modifier = modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(state.defaultTitle) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Text(text = "←", style = MaterialTheme.typography.headlineSmall)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = SketchyPageBackground)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(SketchyPageBackground),
            contentAlignment = Alignment.Center
        ) {
            SketchyEmptyState(
                state = state,
                animate = animate,
                illustrationSize = 260.dp
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SketchyPageBackground)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Animate", style = MaterialTheme.typography.bodyLarge)
            Switch(checked = animate, onCheckedChange = { animate = it })
        }
    }
}
