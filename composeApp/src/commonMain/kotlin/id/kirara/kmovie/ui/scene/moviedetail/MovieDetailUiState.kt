package id.kirara.kmovie.ui.scene.moviedetail

import id.kirara.kmovie.ui.scene.moviedetail.model.MovieDetailUiModel

data class MovieDetailUiState(
    val isLoading: Boolean = true,
    val movieDetailData: MovieDetailUiModel = MovieDetailUiModel(),
    val error: String? = null
)
