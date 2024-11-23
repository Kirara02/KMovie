package id.kirara.kmovie.nav

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import id.kirara.kmovie.core.viewModel
import id.kirara.kmovie.ui.scene.map.MapScreen
import id.kirara.kmovie.ui.scene.movie.MovieScreen
import id.kirara.kmovie.ui.scene.movie.MovieViewModel

class MovieScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: MovieViewModel = viewModel()
        
        MovieScreen(
            viewModel = viewModel,
            onDetailClick = {
                navigator.push(MovieDetailScreen(it))
            },
            onMapClick = {
                navigator.push(MapScreen())
            },
            onNowPlayingsClick = {
                navigator.push(MoviesScreen())
            }
        )
    }
}