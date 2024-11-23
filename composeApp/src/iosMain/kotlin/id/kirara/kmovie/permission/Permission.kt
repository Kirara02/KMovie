package id.kirara.kmovie.permission

import id.kirara.kmovie.permission.delegate.AVCapturePermissionDelegate
import id.kirara.kmovie.permission.delegate.AlwaysGrantedPermissionDelegate
import id.kirara.kmovie.permission.delegate.BluetoothPermissionDelegate
import id.kirara.kmovie.permission.delegate.GalleryPermissionDelegate
import id.kirara.kmovie.permission.delegate.LocationManagerDelegate
import id.kirara.kmovie.permission.delegate.LocationPermissionDelegate
import id.kirara.kmovie.permission.delegate.RemoteNotificationPermissionDelegate
import platform.AVFoundation.AVMediaTypeAudio
import platform.AVFoundation.AVMediaTypeVideo

internal fun Permission.getDelegate(): PermissionDelegate {
    return when (this) {
        Permission.LOCATION,
        Permission.COARSE_LOCATION -> LocationPermissionDelegate(LocationManagerDelegate())

        Permission.RECORD_AUDIO -> AVCapturePermissionDelegate(AVMediaTypeAudio)
        Permission.CAMERA -> AVCapturePermissionDelegate(AVMediaTypeVideo)
        Permission.GALLERY -> GalleryPermissionDelegate()

        Permission.STORAGE, Permission.WRITE_STORAGE -> AlwaysGrantedPermissionDelegate()

        Permission.REMOTE_NOTIFICATION -> RemoteNotificationPermissionDelegate()

        Permission.BLUETOOTH_LE,
        Permission.BLUETOOTH_SCAN,
        Permission.BLUETOOTH_ADVERTISE,
        Permission.BLUETOOTH_CONNECT -> BluetoothPermissionDelegate()
    }
}