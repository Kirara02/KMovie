package id.kirara.kmovie.nav

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import id.kirara.kmovie.core.BackHandler
import id.kirara.kmovie.core.viewModel
import id.kirara.kmovie.ui.scene.moviedetail.MovieDetailScreen
import id.kirara.kmovie.ui.scene.moviedetail.MovieDetailViewModel

class MovieDetailScreen(
    private val movieId: Int,
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: MovieDetailViewModel = viewModel()

        MovieDetailScreen(
            viewModel = viewModel,
            movieId = movieId,
            navigateToActor = {
                navigator.push(ArtistDetailScreen(it))
            },
            onBackPressed = navigator::pop
        )

        BackHandler(isEnabled = true){
            navigator.pop()
        }
    }
}