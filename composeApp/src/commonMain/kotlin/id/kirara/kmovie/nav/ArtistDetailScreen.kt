package id.kirara.kmovie.nav

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import id.kirara.kmovie.core.BackHandler
import id.kirara.kmovie.core.viewModel
import id.kirara.kmovie.ui.scene.artisdetail.ArtistDetailScreen
import id.kirara.kmovie.ui.scene.artisdetail.ArtistDetailViewModel

class ArtistDetailScreen(
    private val actorId: Int
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: ArtistDetailViewModel = viewModel()

        ArtistDetailScreen(
            viewModel = viewModel,
            actorId = actorId,
            navigateToMovie = { navigator.push(MovieDetailScreen(movieId = it)) },
            navigateToTv = { navigator.push(TvDetailScreen(tvId = it)) },
            onBackPressed = navigator::pop
        )

        BackHandler(isEnabled = true) {
            navigator.pop()
        }
    }

}