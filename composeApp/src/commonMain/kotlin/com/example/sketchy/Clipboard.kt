package com.example.sketchy

import androidx.compose.runtime.Composable

/**
 * Returns a function that puts [String] on the system clipboard.
 *
 * Compose's own `Clipboard` API needs a platform-specific `ClipEntry` to write
 * plain text, so the demo goes straight to each platform's clipboard instead —
 * one small actual per target beats an expect/actual `ClipEntry` factory.
 */
@Composable
expect fun rememberClipboardWriter(): (String) -> Unit
