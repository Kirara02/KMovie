package id.kirara.kmovie.permission.delegate

import id.kirara.kmovie.permission.PermissionDelegate
import id.kirara.kmovie.permission.PermissionStatus
import platform.CoreLocation.CLAuthorizationStatus
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusDenied
import platform.CoreLocation.kCLAuthorizationStatusNotDetermined

internal class LocationPermissionDelegate(
    private val locationManagerDelegate: LocationManagerDelegate
) : PermissionDelegate {

    override fun requestPermission(onPermissionResult: (PermissionStatus) -> Unit) {
        locationManagerDelegate.requestLocationAccess {
            onPermissionResult(it.toCommonStatus())
        }
    }

    override suspend fun getPermissionStatus(): PermissionStatus {
        return CLLocationManager.authorizationStatus().toCommonStatus()
    }

    private fun CLAuthorizationStatus.toCommonStatus(): PermissionStatus {
        return when (this) {
            kCLAuthorizationStatusAuthorizedAlways,
            kCLAuthorizationStatusAuthorizedWhenInUse -> PermissionStatus.Granted

            kCLAuthorizationStatusNotDetermined -> PermissionStatus.NotDetermined
            kCLAuthorizationStatusDenied -> PermissionStatus.Denied(true)
            else -> error("unknown location authorization status $this")
        }
    }
}
