package id.kirara.kmovie.ui.scene.splashscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import id.kirara.kmovie.data.account.LoginState

@Composable
fun SplashScreen(
    viewModel: SplashViewModel,
    navigateToLogin: () -> Unit,
    navigateToMain: () -> Unit,
) {
    Spacer(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary))
    val uiState by viewModel.isLoggedIn.collectAsState()
    when (uiState) {
        LoginState.LOGGED_OUT -> {
            navigateToLogin()
        }

        LoginState.LOGGED_IN -> {
            navigateToMain()
        }
    }

}