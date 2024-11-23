package id.kirara.kmovie.nav

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.TabNavigator
import id.kirara.kmovie.core.viewModel
import id.kirara.kmovie.ui.scene.main.MainScreen
import id.kirara.kmovie.ui.tab.TabItem

class MainScreen : Screen {
    @Composable
    override fun Content() {
        TabNavigator(TabItem.MoviesTab().tab, true){ navigator ->
            MainScreen(
                viewModel = viewModel(),
                isTabSelected = {
                    navigator.current.key == it.key
                },
                onTabSelected = {
                    navigator.current = it.tab
                }
            )
        }
    }
}