package com.example.sketchy

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

private fun writeToClipboard(text: String): Unit =
    js("navigator.clipboard.writeText(text)")

@Composable
actual fun rememberClipboardWriter(): (String) -> Unit = remember {
    { text -> writeToClipboard(text) }
}
