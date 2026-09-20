package com.example.sketchy

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.sketchy.ui.theme.SketchyTheme
import com.sketchy.library.emptystates.EmptyState
import com.sketchy.library.icons.Icon
import com.sketchy.library.illustrations.Sketch

/**
 * The whole catalog, theme and scaffold included. Every platform entry point —
 * Android's `MainActivity`, the desktop `Window`, the iOS `UIViewController`
 * and the wasm `ComposeViewport` — does nothing but host this.
 */
@Composable
fun App() {
    SketchyTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SketchyApp(modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
private fun SketchyApp(modifier: Modifier = Modifier) {
    var tab by remember { mutableIntStateOf(TabIllustrations) }
    var query by remember { mutableStateOf("") }
    var selectedSketch by remember { mutableStateOf<Sketch?>(null) }
    var selectedEmptyState by remember { mutableStateOf<EmptyState?>(null) }
    var selectedIcon by remember { mutableStateOf<Icon?>(null) }

    // Hoisted here, above the HomeScreen/DetailScreen `when` branches below, so navigating into a
    // detail screen and back doesn't tear down and recreate each grid's scroll position — only
    // this composable's own remembered state survives that; HomeScreen's own would not.
    val illustrationsGridState = rememberLazyGridState()
    val emptyStatesGridState = rememberLazyGridState()
    val iconsGridState = rememberLazyGridState()

    val sketch = selectedSketch
    val emptyState = selectedEmptyState
    val icon = selectedIcon
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
        icon != null -> IconDetailScreen(
            icon = icon,
            onBack = { selectedIcon = null },
            modifier = modifier.fillMaxSize()
        )
        else -> HomeScreen(
            tab = tab,
            onTabSelected = { tab = it; query = "" },
            query = query,
            onQueryChange = { query = it },
            onSelectSketch = { selectedSketch = it },
            onSelectEmptyState = { selectedEmptyState = it },
            onSelectIcon = { selectedIcon = it },
            illustrationsGridState = illustrationsGridState,
            emptyStatesGridState = emptyStatesGridState,
            iconsGridState = iconsGridState,
            modifier = modifier.fillMaxSize()
        )
    }
}
