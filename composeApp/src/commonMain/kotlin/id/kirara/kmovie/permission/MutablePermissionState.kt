package id.kirara.kmovie.permission

import androidx.compose.runtime.Composable

@Composable
internal expect fun rememberMutablePermissionState(
    permission: Permission,
    onPermissionResult: (Boolean) -> Unit,
) : PermissionState