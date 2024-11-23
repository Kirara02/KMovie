package id.kirara.kmovie.core

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.NoLiveLiterals
import id.kirara.kmovie.domain.location.DeviceLocation

@NoLiveLiterals
actual fun navigateToMap(
    context: PlatformContext,
    deviceLocation: DeviceLocation?,
    destinationName: String
) {
    val destinationUrl = "google.navigation:q=${deviceLocation?.latitude},${deviceLocation?.longitude}"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(destinationUrl))

    if (intent.resolveActivity(context.androidContext.packageManager) != null) {
        context.androidContext.startActivity(intent)
    }
}