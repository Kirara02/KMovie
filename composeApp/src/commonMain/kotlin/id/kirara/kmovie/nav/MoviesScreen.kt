package id.kirara.kmovie.nav

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import id.kirara.kmovie.core.BackHandler
import id.kirara.kmovie.core.viewModel
import id.kirara.kmovie.ui.scene.movies.MoviesScreen
import id.kirara.kmovie.ui.scene.movies.MoviesViewModel

class MoviesScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: MoviesViewModel = viewModel()

        MoviesScreen(
            viewModel = viewModel,
            onMovieClick = {
                navigator.push(MovieDetailScreen(it))
            },
            onNavigateBack = navigator::pop
        )

        BackHandler(isEnabled = true){
            navigator.pop()
        }
    }
}