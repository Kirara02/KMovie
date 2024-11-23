package id.kirara.kmovie.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import id.kirara.kmovie.store

@Composable
actual fun BackHandler(isEnabled: Boolean, onBack: () -> Unit) {
    LaunchedEffect(isEnabled){
        store.events.collect{
            if(isEnabled){
                onBack()
            }
        }
    }
}