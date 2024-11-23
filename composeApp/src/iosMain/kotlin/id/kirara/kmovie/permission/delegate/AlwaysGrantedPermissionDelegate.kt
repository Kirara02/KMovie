package id.kirara.kmovie.permission.delegate

import id.kirara.kmovie.permission.PermissionDelegate
import id.kirara.kmovie.permission.PermissionStatus


internal class AlwaysGrantedPermissionDelegate : PermissionDelegate {

    override fun requestPermission(onPermissionResult: (PermissionStatus) -> Unit) {
        onPermissionResult(PermissionStatus.Granted)
    }

    override suspend fun getPermissionStatus(): PermissionStatus = PermissionStatus.Granted
}
