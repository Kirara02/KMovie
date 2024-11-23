package id.kirara.kmovie.core

import com.seiko.imageloader.Image
import com.seiko.imageloader.model.ImageResult

actual fun ImageResult.OfImage.toImage(): Image = this.image