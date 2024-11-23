package id.kirara.kmovie.ui.scene.movies

import id.kirara.kmovie.domain.movie.NowPlayingMovie

data class MoviesUiState(
    val isLoading: Boolean = true,
    val nowPlayingMovieData: List<NowPlayingMovie> = emptyList(),
    val error: String? = null
)