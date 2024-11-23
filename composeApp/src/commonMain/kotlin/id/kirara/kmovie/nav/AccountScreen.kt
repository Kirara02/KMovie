package id.kirara.kmovie.nav

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import id.kirara.kmovie.core.BackHandler
import id.kirara.kmovie.core.viewModel
import id.kirara.kmovie.ui.scene.account.AccountDetailViewModel
import id.kirara.kmovie.ui.scene.account.AccountScreen

class AccountScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val mainNavigator = LocalMainNavigator.current
        val viewModel: AccountDetailViewModel = viewModel()

        AccountScreen(
            viewModel = viewModel,
            navigateToSplash = {
                mainNavigator.replaceAll(SplashScreen())
            },
            navigateToFavorite = {
                navigator.push(FavouriteScreen(it))
            }
        )

        BackHandler(
            isEnabled = true,
            onBack = {
                navigator.pop()
            }
        )
    }
}