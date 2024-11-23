package id.kirara.kmovie.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.layout.ContentScale.Companion.FillWidth
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import id.kirara.kmovie.core.StatusBarAppearance
import id.kirara.kmovie.core.getDominantColor
import id.kirara.kmovie.core.toComposeImageBitmap
import id.kirara.kmovie.core.toImage
import id.kirara.kmovie.ui.theme.isLight
import id.kirara.kmovie.utils.Constants
import com.seiko.imageloader.LocalImageLoader
import com.seiko.imageloader.asImageBitmap
import com.seiko.imageloader.model.ImageRequest
import com.seiko.imageloader.model.ImageResult
import com.seiko.imageloader.rememberImagePainter
import kmovie.composeapp.generated.resources.Res
import kmovie.composeapp.generated.resources.search_place_holder
import id.kirara.kmovie.Logger
import org.jetbrains.compose.resources.painterResource
import kotlin.math.roundToInt

@Composable
fun PosterImageItem(
    modifier: Modifier = Modifier,
    contentScale: ContentScale = Crop,
    imagePath: String?
) {
    Image(
        modifier = modifier.fillMaxSize(),
        painter = rememberImagePainter(
            Constants.IMAGE_BASE.plus(imagePath)
        ),
        contentDescription = null,
        contentScale = contentScale
    )
}

@Composable
fun DetailPosterImage(
    modifier: Modifier = Modifier,
    imagePath: String?
) {
    val url = Constants.IMAGE_BASE.plus(imagePath)
    val request = remember(url) { ImageRequest(url) }
    val imageLoader = LocalImageLoader.current
    var viewWidth by remember { mutableStateOf(0) }
    val statusBarHeight = WindowInsets.statusBars.getTop(LocalDensity.current)

    var dominantColor by remember { mutableStateOf<Color?>(null) }
    if (dominantColor != null) {
        StatusBarAppearance(isBackgroundLight = dominantColor!!.isLight)
    }

    Image(
        modifier = modifier.fillMaxSize().onGloballyPositioned {
            viewWidth = it.size.width
        },
        painter = rememberImagePainter(request, imageLoader),
        contentDescription = null,
        contentScale = FillWidth
    )

    LaunchedEffect(imagePath) {
        val imageBitmap = when (val imageResult = imageLoader.execute(request)) {
            is ImageResult.OfBitmap -> imageResult.bitmap.asImageBitmap()

            is ImageResult.OfImage -> imageResult.toImage().toComposeImageBitmap()

            else -> null
        }

        Logger.d("${imageBitmap?.width}")

        val height = getLengthPxScaledByView(
            length = statusBarHeight,
            itemWidth = imageBitmap?.width ?: 1,
            viewWidth = viewWidth
        ).coerceAtLeast(1)

        dominantColor = imageBitmap?.getDominantColor(height)
    }
}

private fun getLengthPxScaledByView(
    length: Int,
    itemWidth: Int,
    viewWidth: Int,
): Int {
    return (length * (itemWidth.toFloat() / viewWidth)).roundToInt()
}

@Composable
fun CardImageItem(
    modifier: Modifier = Modifier,
    imagePath: String?,
    contentScale: ContentScale = Crop
) {

    Image(
        painter = if (imagePath.isNullOrEmpty()) {
            painterResource(Res.drawable.search_place_holder)
        } else {
            rememberImagePainter(
                Constants.IMAGE_BASE.plus(imagePath)
            )
        },
        contentDescription = null,
        modifier = modifier.size(width = 100.dp, height = 150.dp),
        contentScale = contentScale,
    )
}