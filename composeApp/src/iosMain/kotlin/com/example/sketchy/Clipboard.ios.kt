package com.example.sketchy

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.UIKit.UIPasteboard

@Composable
actual fun rememberClipboardWriter(): (String) -> Unit = remember {
    { text -> UIPasteboard.generalPasteboard.string = text }
}
