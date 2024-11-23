package id.kirara.kmovie.core

import androidx.compose.runtime.Composable

expect class PlatformContext

@Composable
expect fun getPlatformContext() : PlatformContext