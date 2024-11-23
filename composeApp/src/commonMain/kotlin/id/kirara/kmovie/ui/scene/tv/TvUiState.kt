package id.kirara.kmovie.ui.scene.tv

import id.kirara.kmovie.domain.tv.PopularTv
import id.kirara.kmovie.domain.tv.TopRatedTv

data class TvUiState(
    val isLoading: Boolean = true,
    val popularTvData: List<PopularTv> = emptyList(),
    val topRatedTvData: List<TopRatedTv> = emptyList(),
    val error: String? = null
)
