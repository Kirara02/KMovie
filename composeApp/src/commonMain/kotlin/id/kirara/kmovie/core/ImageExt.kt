package id.kirara.kmovie.core

import androidx.compose.ui.graphics.ImageBitmap
import com.seiko.imageloader.Image

expect fun Image.toComposeImageBitmap() : ImageBitmap