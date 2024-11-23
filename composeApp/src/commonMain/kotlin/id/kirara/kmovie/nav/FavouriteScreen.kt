package id.kirara.kmovie.nav

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import id.kirara.kmovie.core.BackHandler
import id.kirara.kmovie.core.viewModel
import id.kirara.kmovie.domain.MediaType
import id.kirara.kmovie.ui.scene.account.favorite.FavoriteScreen
import id.kirara.kmovie.ui.scene.account.favorite.FavoriteViewModel

class FavouriteScreen(
    private val mediaType: MediaType
) : Screen{
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: FavoriteViewModel = viewModel()

        FavoriteScreen(
            viewModel = viewModel,
            mediaType = mediaType,
            navigateToMovie = { navigator.push(MovieDetailScreen(it)) },
            navigateToTv = { navigator.push(TvDetailScreen(it)) },
            navigateBack = navigator::pop
        )

        BackHandler(isEnabled = true){
            navigator.pop()
        }
    }
}