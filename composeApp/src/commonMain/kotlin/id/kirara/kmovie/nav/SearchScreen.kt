package id.kirara.kmovie.nav

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import id.kirara.kmovie.core.BackHandler
import id.kirara.kmovie.core.viewModel
import id.kirara.kmovie.ui.scene.search.SearchScreen
import id.kirara.kmovie.ui.scene.search.SearchViewModel

class SearchScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: SearchViewModel = viewModel()

        SearchScreen(
            viewModel = viewModel,
            navigateToMovie = { navigator.push(MovieDetailScreen(it)) },
            navigateToTv = { navigator.push(TvDetailScreen(it)) },
            navigateToActor = { navigator.push(ArtistDetailScreen(it)) }
        )

        BackHandler(isEnabled = true) {
            navigator.pop()
        }
    }
}