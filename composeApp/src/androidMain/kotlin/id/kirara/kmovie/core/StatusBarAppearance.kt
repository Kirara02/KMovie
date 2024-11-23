package id.kirara.kmovie.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember

@Composable
actual fun StatusBarAppearance(isBackgroundLight: Boolean) {
    val insetsController = findWindow()?.getInsetsControllerCompat()
    val isDefaultAppearanceLight = remember(key1 = Unit) {
        insetsController?.isAppearanceLightStatusBars
    }

    DisposableEffect(isBackgroundLight) {
        insetsController?.isAppearanceLightStatusBars = isBackgroundLight

        onDispose {
            insetsController?.isAppearanceLightStatusBars = isDefaultAppearanceLight ?: false
        }
    }
}
