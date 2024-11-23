package id.kirara.kmovie.core

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import id.kirara.kmovie.Logger

suspend fun ImageBitmap.getDominantColor(height: Int): Color {
    return withContext(Dispatchers.IO) {
        try {
            val effectiveHeight = height.coerceAtMost(this@getDominantColor.height)
            val effectiveWidth = this@getDominantColor.width

            // Ensure dimensions are non-zero
            if (effectiveWidth <= 0 || effectiveHeight <= 0) {
                Logger.e("Invalid dimensions for ImageBitmap: width=$effectiveWidth, height=$effectiveHeight")
                return@withContext Color.Black
            }

            val pixels = IntArray(effectiveWidth * effectiveHeight)
            readPixels(pixels, width = effectiveWidth, height = effectiveHeight)

            val colorCountMap = mutableMapOf<Int, Int>()
            for (color in pixels) {
                colorCountMap[color] = (colorCountMap[color] ?: 0) + 1
            }

            val dominantColor = colorCountMap.maxByOrNull { it.value }?.key
            return@withContext dominantColor?.let { Color(it) } ?: Color.Black
        } catch (e: Exception) {
            Logger.e("Error in getDominantColor: ${e.message}")
            return@withContext Color.Black
        }
    }
}
