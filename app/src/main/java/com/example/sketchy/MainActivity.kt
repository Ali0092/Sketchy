package com.example.sketchy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sketchy.ui.EmptyStateDetailScreen
import com.example.sketchy.ui.EmptyStateGalleryScreen
import com.example.sketchy.ui.SketchyDetailScreen
import com.example.sketchy.ui.SketchyGalleryScreen
import com.example.sketchy.ui.SketchyPageBackground
import com.example.sketchy.ui.theme.SketchyTheme
import com.sketchy.library.EmptyState
import com.sketchy.library.Sketch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SketchyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SketchyApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

private const val TabIllustrations = 0
private const val TabEmptyStates = 1

@Composable
private fun SketchyApp(modifier: Modifier = Modifier) {
    var tab by remember { mutableIntStateOf(TabIllustrations) }
    var selectedSketch by remember { mutableStateOf<Sketch?>(null) }
    var selectedEmptyState by remember { mutableStateOf<EmptyState?>(null) }

    val sketch = selectedSketch
    val emptyState = selectedEmptyState
    when {
        sketch != null -> SketchyDetailScreen(
            sketch = sketch,
            onBack = { selectedSketch = null },
            modifier = modifier.fillMaxSize()
        )
        emptyState != null -> EmptyStateDetailScreen(
            state = emptyState,
            onBack = { selectedEmptyState = null },
            modifier = modifier.fillMaxSize()
        )
        else -> Column(
            modifier = modifier
                .fillMaxSize()
                .background(SketchyPageBackground)
        ) {
            Text(
                text = "Sketchy",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
            )
            SecondaryTabRow(selectedTabIndex = tab, containerColor = SketchyPageBackground) {
                Tab(
                    selected = tab == TabIllustrations,
                    onClick = { tab = TabIllustrations },
                    text = { Text("Illustrations") }
                )
                Tab(
                    selected = tab == TabEmptyStates,
                    onClick = { tab = TabEmptyStates },
                    text = { Text("Empty States") }
                )
            }
            when (tab) {
                TabIllustrations -> SketchyGalleryScreen(
                    onSelect = { selectedSketch = it },
                    modifier = Modifier.weight(1f).fillMaxWidth()
                )
                else -> EmptyStateGalleryScreen(
                    onSelect = { selectedEmptyState = it },
                    modifier = Modifier.weight(1f).fillMaxWidth()
                )
            }
        }
    }
}
