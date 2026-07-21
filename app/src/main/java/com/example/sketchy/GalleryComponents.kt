package com.example.sketchy

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sketchy.ui.theme.SketchyGold
import com.example.sketchy.ui.theme.SketchyInk
import com.example.sketchy.ui.theme.SketchyTeal
import com.sketchy.library.emptystates.EmptyState
import com.sketchy.library.illustrations.Sketch

/** Shared building blocks used by the gallery and detail screens. */

internal fun Sketch.matches(query: String) =
    query.isBlank() || displayName.contains(query, ignoreCase = true) || category.contains(query, ignoreCase = true)

internal fun EmptyState.matches(query: String) =
    query.isBlank() || defaultTitle.contains(query, ignoreCase = true) || category.contains(query, ignoreCase = true)

@Composable
internal fun CategoryHeader(category: String, count: Int, modifier: Modifier = Modifier) {
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
internal fun NoResultsHint(query: String, modifier: Modifier = Modifier) {
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

/** A small hand-drawn chevron, in keeping with the rest of the sketched artwork. */
@Composable
internal fun BackArrow(tint: Color, modifier: Modifier = Modifier) {
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
internal fun DetailHeader(title: String, subtitle: String, onBack: () -> Unit, modifier: Modifier = Modifier) {
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
internal fun AnimateToggleRow(animate: Boolean, onAnimateChange: (Boolean) -> Unit, modifier: Modifier = Modifier) {
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