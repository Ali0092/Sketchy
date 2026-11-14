package com.sketchy.library.model

import androidx.compose.ui.graphics.Color
import com.sketchy.library.mascots.PixelCloudBlush
import com.sketchy.library.mascots.PixelCloudBody
import com.sketchy.library.mascots.PixelCloudDisabledBody
import com.sketchy.library.mascots.PixelCloudDisabledShade
import com.sketchy.library.mascots.PixelCloudErrorBody
import com.sketchy.library.mascots.PixelCloudErrorShade
import com.sketchy.library.mascots.PixelCloudShade

enum class PixelCloudMood(val displayName: String, val bodyColor: Color, val shadeColor: Color, val blushColor: Color) {
    Default("Default", PixelCloudBody, PixelCloudShade, PixelCloudBlush),
    Thinking("Thinking", PixelCloudBody, PixelCloudShade, PixelCloudBlush),
    Error("Error", PixelCloudErrorBody, PixelCloudErrorShade, Color.Unspecified),
    Disabled("Disabled", PixelCloudDisabledBody, PixelCloudDisabledShade, Color.Unspecified),
}
