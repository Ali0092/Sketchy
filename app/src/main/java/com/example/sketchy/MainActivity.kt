package com.example.sketchy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.sketchy.ui.EmptyStateDetailScreen
import com.example.sketchy.ui.HomeScreen
import com.example.sketchy.ui.SketchyDetailScreen
import com.example.sketchy.ui.TabIllustrations
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

@Composable
private fun SketchyApp(modifier: Modifier = Modifier) {
    var tab by remember { mutableIntStateOf(TabIllustrations) }
    var query by remember { mutableStateOf("") }
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
        else -> HomeScreen(
            tab = tab,
            onTabSelected = { tab = it; query = "" },
            query = query,
            onQueryChange = { query = it },
            onSelectSketch = { selectedSketch = it },
            onSelectEmptyState = { selectedEmptyState = it },
            modifier = modifier.fillMaxSize()
        )
    }
}
