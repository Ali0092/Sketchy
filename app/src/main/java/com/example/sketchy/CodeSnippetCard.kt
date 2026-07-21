package com.example.sketchy

import android.content.ClipData
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sketchy.ui.theme.SketchyGold
import com.example.sketchy.ui.theme.SketchyInk
import com.example.sketchy.ui.theme.SketchyOnDark
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/** A dark "code editor" style card showing a copyable usage snippet — the kind of thing an open-source library demo should always show. */
@Composable
fun CodeSnippetCard(code: String, modifier: Modifier = Modifier) {
    val clipboard = LocalClipboard.current
    val scope = rememberCoroutineScope()
    var copied by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SketchyInk)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "USAGE",
                style = MaterialTheme.typography.labelSmall,
                color = SketchyGold,
                letterSpacing = 1.5.sp,
                fontWeight = FontWeight.SemiBold
            )
            TextButton(onClick = {
                scope.launch {
                    clipboard.setClipEntry(ClipEntry(ClipData.newPlainText("Sketchy usage", code)))
                    copied = true
                }
            }) {
                Text(
                    text = if (copied) "Copied!" else "Copy",
                    color = SketchyGold,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
        Text(
            text = code,
            style = MaterialTheme.typography.bodyMedium.copy(fontFamily = FontFamily.Monospace),
            color = SketchyOnDark,
            modifier = Modifier.padding(top = 4.dp)
        )
    }

    LaunchedEffect(copied) {
        if (copied) {
            delay(1500)
            copied = false
        }
    }
}
