package id.kirara.kmovie.nav

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import id.kirara.kmovie.core.viewModel
import id.kirara.kmovie.ui.scene.tv.TvScreen
import id.kirara.kmovie.ui.scene.tv.TvViewModel

class TvScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: TvViewModel = viewModel()

        TvScreen(
            viewModel = viewModel,
            navigateToDetail = {
                navigator.push(TvDetailScreen(it))
            }
        )
    }
}