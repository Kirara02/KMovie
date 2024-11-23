package id.kirara.kmovie.nav

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import id.kirara.kmovie.core.viewModel
import id.kirara.kmovie.ui.scene.splashscreen.SplashViewModel
import id.kirara.kmovie.ui.scene.splashscreen.SplashScreen

class SplashScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val viewModel: SplashViewModel = viewModel()

        SplashScreen(
            viewModel = viewModel,
            navigateToLogin = { navigator.replace(LoginScreen()) },
            navigateToMain = { navigator.replace(MainScreen()) }
        )
    }
}