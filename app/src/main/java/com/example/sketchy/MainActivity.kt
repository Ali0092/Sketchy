package com.example.sketchy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.sketchy.ui.SketchyDetailScreen
import com.example.sketchy.ui.SketchyGalleryScreen
import com.example.sketchy.ui.SketchyPageBackground
import com.example.sketchy.ui.theme.SketchyTheme
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
    var selected by remember { mutableStateOf<Sketch?>(null) }

    val current = selected
    if (current == null) {
        SketchyGalleryScreen(
            onSelect = { selected = it },
            modifier = modifier
                .fillMaxSize()
                .background(SketchyPageBackground)
        )
    } else {
        SketchyDetailScreen(
            sketch = current,
            onBack = { selected = null },
            modifier = modifier.fillMaxSize()
        )
    }
}
