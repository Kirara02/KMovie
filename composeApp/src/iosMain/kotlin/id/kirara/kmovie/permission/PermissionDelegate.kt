package id.kirara.kmovie.permission

internal interface PermissionDelegate {
    fun requestPermission(onPermissionResult: (PermissionStatus) -> Unit)
    suspend fun getPermissionStatus(): PermissionStatus
}