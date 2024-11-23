package id.kirara.kmovie.nav

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import id.kirara.kmovie.core.viewModel
import id.kirara.kmovie.ui.scene.login.LoginViewModel
import id.kirara.kmovie.ui.scene.login.LoginScreen

class LoginScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: LoginViewModel = viewModel()

        LoginScreen(
            viewModel = viewModel,
            navigateToWebViewScreen = {
                navigator.push(WebViewScreen(it))
            },
            navigateToMainScreen = {
                navigator.replace(MainScreen())
            }
        )
    }
}