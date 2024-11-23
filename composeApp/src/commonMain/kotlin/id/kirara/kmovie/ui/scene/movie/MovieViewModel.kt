package id.kirara.kmovie.ui.scene.movie

import id.kirara.kmovie.core.ViewModel
import id.kirara.kmovie.core.viewModelScope
import id.kirara.kmovie.domain.movie.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieViewModel(
    private val repository: MovieRepository
) : ViewModel {

    private val _uiState = MutableStateFlow(MovieUiState())
    val uiState: StateFlow<MovieUiState> = _uiState

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            val popularMovieResult = repository.getPopularMovie()
            val nowPlayingMovieResult = repository.getNowPlayingMovie()

            if(popularMovieResult.isSuccess && nowPlayingMovieResult.isSuccess) {
                _uiState.update { uiState ->
                    uiState.copy(
                        isLoading = false,
                        popularMovieData = popularMovieResult.getOrDefault(listOf()),
                        nowPlayingMovieData = nowPlayingMovieResult.getOrDefault(listOf())
                    )
                }
            } else {
                _uiState.update { uiState ->
                    uiState.copy(
                        isLoading = false,
                        error = "ERROR"
                    )
                }
            }
        }
    }
}