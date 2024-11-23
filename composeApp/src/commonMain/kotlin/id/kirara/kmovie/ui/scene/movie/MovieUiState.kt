package id.kirara.kmovie.ui.scene.movie

import id.kirara.kmovie.domain.movie.NowPlayingMovie
import id.kirara.kmovie.domain.movie.PopularMovie

data class MovieUiState(
    val isLoading: Boolean = true,
    val popularMovieData: List<PopularMovie> = emptyList(),
    val nowPlayingMovieData: List<NowPlayingMovie> = emptyList(),
    val error: String? = null
)