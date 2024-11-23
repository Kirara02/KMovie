package id.kirara.kmovie.nav

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import id.kirara.kmovie.core.BackHandler
import id.kirara.kmovie.core.viewModel
import id.kirara.kmovie.ui.scene.tvdetail.TvDetailScreen
import id.kirara.kmovie.ui.scene.tvdetail.TvDetailViewModel

class TvDetailScreen(
    private val tvId: Int
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: TvDetailViewModel = viewModel()

        TvDetailScreen(
            viewModel = viewModel,
            tvId = tvId,
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