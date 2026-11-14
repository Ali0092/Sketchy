package com.example.sketchy

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sketchy.ui.theme.SketchyCream
import com.example.sketchy.ui.theme.SketchyInk
import com.sketchy.library.mascots.PixelCloudMascot
import com.sketchy.library.model.PixelCloudMood

@Composable
fun MascotGalleryScreen(query: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val moods = PixelCloudMood.entries.filter { "$PixelCloudName ${it.displayName}".contains(query.trim(), ignoreCase = true) }
        if (moods.isEmpty()) {
            NoResultsHint(query)
        } else {
            CategoryHeader(PixelCloudName, moods.size)
        }
        moods.forEach { mood ->
            MascotCard(mood = mood, background = SketchyCream, labelColor = SketchyInk)
            CodeSnippetCard(code = "PixelCloudMascot(\n    modifier = Modifier.width(240.dp),\n    mood = PixelCloudMood.${mood.name},\n    walking = true,\n    blinking = true,\n    waving = true,\n    moodEffects = true\n)")
        }
    }
}

@Composable
private fun MascotCard(mood: PixelCloudMood, background: Color, labelColor: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = background),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        PixelCloudMascot(
            mood = mood,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 24.dp)
        )
        Text(
            text = mood.displayName,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold,
            color = labelColor,
            modifier = Modifier.padding(start = 16.dp, bottom = 12.dp)
        )
    }
}
