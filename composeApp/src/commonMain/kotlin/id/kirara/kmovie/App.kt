package id.kirara.kmovie

import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import id.kirara.kmovie.nav.LocalMainNavigator
import id.kirara.kmovie.nav.LoginScreen
import id.kirara.kmovie.ui.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        Navigator(LoginScreen()){
            CompositionLocalProvider(LocalMainNavigator provides it){
                CurrentScreen()
            }
        }

        LaunchedEffect(Unit) {
            log { "App started." }
        }
    }
}