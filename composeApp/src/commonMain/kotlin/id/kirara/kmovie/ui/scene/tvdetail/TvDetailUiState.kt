package id.kirara.kmovie.ui.scene.tvdetail

import id.kirara.kmovie.ui.scene.tvdetail.model.TvDetailUiModel

data class TvDetailUiState(
    val isLoading: Boolean = true,
    val tvDetailData: TvDetailUiModel = TvDetailUiModel(),
    val error: String? = null
)
