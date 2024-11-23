package id.kirara.kmovie.ui.scene.movies

import id.kirara.kmovie.Logger
import id.kirara.kmovie.core.ViewModel
import id.kirara.kmovie.core.viewModelScope
import id.kirara.kmovie.domain.movie.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val repository: MovieRepository
) : ViewModel {

    private val _uiState = MutableStateFlow(MoviesUiState())
    val uiState: StateFlow<MoviesUiState> = _uiState

    private var currentPage = 1
    private var isLoadingMore = false
    private var hasMorePages = true

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = repository.getNowPlayingMovie(page = currentPage)

            result.onFailure {
                Logger.e(it.message.toString())
            }

            if (result.isSuccess) {
                val newMovies = result.getOrDefault(listOf())
                if (newMovies.isEmpty()) {
                    hasMorePages = false
                } else {
                    Logger.d(result.getOrDefault(listOf()).size.toString())
                    _uiState.update { uiState ->
                        uiState.copy(
                            isLoading = false,
                            nowPlayingMovieData = uiState.nowPlayingMovieData + newMovies
                        )
                    }
                    currentPage++
                }
            } else {
                _uiState.update { it.copy(isLoading = false, error = "ERROR") }
            }
            isLoadingMore = false
        }
    }

    fun loadNextPage() {
        if (!isLoadingMore && hasMorePages) {
            isLoadingMore = true
            fetchData()
        }
    }

}