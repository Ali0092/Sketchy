package com.example.sketchy.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sketchy.ui.theme.SketchyGold
import com.example.sketchy.ui.theme.SketchyTeal
import com.sketchy.library.EmptyState
import com.sketchy.library.Sketch

const val TabIllustrations = 0
const val TabEmptyStates = 1

@Composable
fun HomeScreen(
    tab: Int,
    onTabSelected: (Int) -> Unit,
    query: String,
    onQueryChange: (String) -> Unit,
    onSelectSketch: (Sketch) -> Unit,
    onSelectEmptyState: (EmptyState) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        HeroHeader()
        SketchySearchField(
            query = query,
            onQueryChange = onQueryChange,
            placeholder = if (tab == TabIllustrations) "Search illustrations…" else "Search empty states…",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        Spacer(Modifier.height(4.dp))
        SecondaryTabRow(selectedTabIndex = tab) {
            Tab(
                selected = tab == TabIllustrations,
                onClick = { onTabSelected(TabIllustrations) },
                text = { Text("Illustrations · ${Sketch.entries.size}") }
            )
            Tab(
                selected = tab == TabEmptyStates,
                onClick = { onTabSelected(TabEmptyStates) },
                text = { Text("Empty States · ${EmptyState.entries.size}") }
            )
        }
        when (tab) {
            TabIllustrations -> SketchyGalleryScreen(
                query = query,
                onSelect = onSelectSketch,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
            else -> EmptyStateGalleryScreen(
                query = query,
                onSelect = onSelectEmptyState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun HeroHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(SketchyGold)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "OPEN SOURCE · MIT LICENSED",
                style = MaterialTheme.typography.labelSmall,
                letterSpacing = 1.5.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = "Sketchy",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(top = 6.dp)
        )
        Text(
            text = "Hand-drawn, animated illustrations & empty states for Jetpack Compose.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp)
        )
        Row(
            modifier = Modifier.padding(top = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatChip("${Sketch.entries.size}", "Illustrations")
            StatChip("${EmptyState.entries.size}", "Empty States")
            StatChip("100%", "Compose")
        }
    }
}

@Composable
private fun StatChip(value: String, label: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                shape = RoundedCornerShape(50)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = SketchyTeal
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun SketchySearchField(
    query: String,
    onQueryChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
                shape = RoundedCornerShape(14.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        if (query.isEmpty()) {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
        }
        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            singleLine = true,
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                color = MaterialTheme.colorScheme.onSurface
            ),
            cursorBrush = SolidColor(SketchyGold),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
