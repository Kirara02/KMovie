package id.kirara.kmovie.ui.scene.main

import id.kirara.kmovie.core.ViewModel
import id.kirara.kmovie.ui.tab.TabItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel {
    val tabItems = MutableStateFlow(
        listOf(
            TabItem.MoviesTab(),
            TabItem.TvShowsTab(),
            TabItem.SearchTab(),
            TabItem.AccountTab(),
        )
    ).asStateFlow()
}