package id.kirara.kmovie.core

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import com.seiko.imageloader.Image

actual fun Image.toComposeImageBitmap(): ImageBitmap {
    return toComposeImageBitmap()
}