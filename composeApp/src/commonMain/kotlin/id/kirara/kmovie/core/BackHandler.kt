package id.kirara.kmovie.core

import androidx.compose.runtime.Composable

@Composable
expect fun BackHandler(isEnabled: Boolean, onBack: () -> Unit)