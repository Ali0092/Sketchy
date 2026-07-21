package com.example.sketchy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import com.example.sketchy.ui.theme.SketchyCream
import com.sketchy.library.illustrations.Sketch
import com.sketchy.library.illustrations.SketchyIllustration

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