package id.kirara.kmovie.core

import id.kirara.kmovie.domain.location.DeviceLocation


expect fun navigateToMap(context: PlatformContext, deviceLocation: DeviceLocation?, destinationName: String = "")